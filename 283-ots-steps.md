## Description
Follow on the #209 and #266, now we can have configuration store embedded with webhook service, which can inject the properties value to the request resources. However, to be able to intercept the resource creation request sent to api server, the configuration-store must start up first so the webhook will be ready before all other services start. 

For the cloud deployment, we can deploy the configuration store using argocd. For local deployment, currently, when tilt up, the configmap will be created/registered before the configuration-store service despite that we have put a resource wait on it or that we only tilt up configuration store service first. With current setup, the only way to achieve that is to create a separated tiltfile to start the configuration-store service first; then, to start up all other pods. However, this is not very user friendly. This ticket is to make sure the configuration-store deploy first on remote deployment.


## DOD
Acceptance criteria required to realize the requested feature
 - [ ] Ensure the configuration store argocd app deployed during pre-sync phase

**------------------------------------Follow On------------------------------------**
 - [ ] Implement the helm_resources tiltfile solution
    - [ ] The user should be prompt to implement the deployment solution
 - [ ] Inclusion of the configuration store is optional.
 - [ ] Add the migration script to change the existing Tilt format
 

## Test Strategy/Script
How will this feature be verified?
- Create a downstream project with baseline version 1.9.0-SNAPSHOT
```
mvn archetype:generate -U -DarchetypeGroupId=com.boozallen.aissemble \
  -DarchetypeArtifactId=foundation-archetype \
  -DarchetypeVersion=1.9.0-SNAPSHOT \
  -DgroupId=com.test \
  -DartifactId=test-283 \
  -DprojectGitUrl=test.url \
  -DprojectName=test-283 \
  && cd test-283
```
- Add the [SparkPipeline.json](https://github.com/user-attachments/files/16781488/SparkPipeline.json) file to the test-283-pipeline-models/src/main/resources/pipelines directory
- Add to the fermenter-mda plugin executions in test-283-deploy/pom.xml
```
<execution>
    <id>configuration-store</id>
    <phase>generate-sources</phase>
    <goals>
        <goal>generate-sources</goal>
    </goals>
    <configuration>
        <basePackage>com.boozallen.aissemble.test</basePackage>
        <profile>configuration-store-deploy-v2</profile>
        <!-- The property variables below are passed to the Generation Context and utilized
             to customize the deployment artifacts. -->
        <propertyVariables>
            <appName>configuration-store</appName>
        </propertyVariables>
    </configuration>
</execution>
```
- Run mvn clean install until all the manual actions are complete
- Once the manual actions are complete, run mvn clean install -Dmaven.build.cache.skipCache=true once to get any remaining manual actions
- In the test-283-deploy/src/main/resources/app/hive-metastore-service/templates/configmap.yaml file:
add below content to the metadata:
```
    labels:
      aissemble-configuration-store: enabled
```
- In the test-283/test-283-deploy/src/main/resources/apps/hive-metastore-service/values.yaml file
```
replace fs.s3a.access.key value 123 with $getConfigValue(groupName=aws-credentials;propertyName=AWS_ACCESS_KEY_ID)
replace fs.s3a.secret.key value 456 with $getConfigValue(groupName=aws-credentials;propertyName=AWS_SECRET_ACCESS_KEY)
```
- Download and unzip the attached [283-helper.zip](https://github.com/user-attachments/files/16781695/283-helper.zip)
- Modify the helm templates for Argocd deployment:
   - In the `test-283-deploy/src/main/resources/values.yaml` file, update the `targetRevision` and `repo` to reflect the correct value. e.g.:
   ```
    targetRevision: main
    repo: https://github.com/csun-cpointe/test-283
   ```
   - In the `` file, remove the `global` attribute and add `syncPolicy` as following:
   ```
    -  global:
    -    imagePullPolicy: Always
    -    dockerRepo: ghcr.io/
    +    syncPolicy:
    +        syncOptions:
    +            - CreateNamespace=true
   ```
   - Mac User: In the `-deploy/src/main/resources/apps/configuration-store/values-ci.yaml file` update the `volumePathOnNode` to be `/<pathToProject>/test-283`, e.g.: `volumePathOnNode: "/Users/csun/bah/tests/test-283"`
   - Window User: In the `-deploy/src/main/resources/values-ci.yaml file` update the `volumePathOnNode` to be `/mnt/c/Users/YOUR_USER/PATH/TO/283-helper/apps/configuration-store/src/main/resources/configurations`
- Create a repo for the project created 
   - create a new test-283 repository
   - at the test-283 root directory run below commands
   -  `git init --bare`
   -  `git add .`
   -  `git commit -m "init test-283 base"
   -  `git branch -M main`
   -  `git remote add origin https://github.com/<username>/<repo_name>.git`
   -  `git push -u origina main`
- Start a local argocd server
   -  `helm install aissemble-infrastructure oci://ghcr.io/boozallen/aissemble-infrastructure-chart --version 1.9.0-SNAPSHOT --set jenkins.enabled=false`
   -  `argocd admin initial-password -n argocd` (keep the password to login to argocd ui and cli console)
   -  Run `kubectl port-forward --namespace=ingress-nginx service/ingress-nginx-controller 8080:80` in another terminal
   -  Open the argocd UI `http://argocd.localdev.me:8080/` and login as (admin/previous generated password)
   - In the test-283 root directory run `argocd login grpc.argocd.localdev.me:8080` (Use username: admin/password: previous generated password to login)
   - Run below command to deploy the test-283 app
   ```
         argocd app create test-283 \
          --dest-namespace argocd \
          --dest-server https://kubernetes.default.svc \
          --repo https://github.com/csun-cpointe/test-283 \
          --path test-283-deploy/src/main/resources \
          --revision main \
          --values values.yaml \
          --values values-ci.yaml
   ```
- Make sure `hive-metastore-service` app has the properties as expected


## References/Additional Context
A clear and concise description of any alternative solutions or features you've considered.
Add any other context, links, or screenshots about the feature request here.
