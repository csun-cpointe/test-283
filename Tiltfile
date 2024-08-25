allow_k8s_contexts('local')
docker_prune_settings(num_builds=1, keep_recent=1)

aissemble_version = '1.9.0-SNAPSHOT'

build_args = { 'DOCKER_BASELINE_REPO_ID': 'ghcr.io/',
               'VERSION_AISSEMBLE': aissemble_version}

# Kafka
yaml = helm(
    'test-283-deploy/src/main/resources/apps/kafka-cluster',
    values=['test-283-deploy/src/main/resources/apps/kafka-cluster/values.yaml',
        'test-283-deploy/src/main/resources/apps/kafka-cluster/values-dev.yaml']
)
k8s_yaml(yaml)

load('ext://helm_resource', 'helm_resource')
helm_resource(
    name='configuration-store',
    release_name='configuration-store',
    chart='test-283-deploy/src/main/resources/apps/configuration-store',
    namespace='config-store-ns',
    flags=['--values=test-283-deploy/src/main/resources/apps/configuration-store/values.yaml',
           '--values=test-283-deploy/src/main/resources/apps/configuration-store/values-dev.yaml',
           '--set=aissemble-configuration-store-chart.configurationVolume.volumePathOnNode=' + project_path + '/' + configuration_files_path,
           '--create-namespace']
)

k8s_kind('SparkApplication', image_json_path='{.spec.image}')

# spark-worker-image
docker_build(  
    ref='test-283-spark-worker-docker',
    context='test-283-docker/test-283-spark-worker-docker',
    build_args=build_args,
    extra_tag='test-283-spark-worker-docker:latest',
    dockerfile='test-283-docker/test-283-spark-worker-docker/src/main/resources/docker/Dockerfile'
)

k8s_resource('hive-metastore-service', resource_deps=['hive-metastore-db'])

yaml = local('helm template oci://ghcr.io/boozallen/aissemble-spark-application-chart --version %s --values test-283-pipelines/spark-pipeline/src/main/resources/apps/spark-pipeline-base-values.yaml,test-283-pipelines/spark-pipeline/src/main/resources/apps/spark-pipeline-dev-values.yaml' % aissemble_version)
k8s_yaml(yaml)
k8s_resource('spark-pipeline', port_forwards=[port_forward(4747, 4747, 'debug')], auto_init=False, trigger_mode=TRIGGER_MODE_MANUAL)
k8s_yaml('test-283-deploy/src/main/resources/apps/spark-worker-image/spark-worker-image.yaml')


yaml = helm(
   'test-283-deploy/src/main/resources/apps/policy-decision-point',
   name='policy-decision-point',
   values=['test-283-deploy/src/main/resources/apps/policy-decision-point/values.yaml',
       'test-283-deploy/src/main/resources/apps/policy-decision-point/values-dev.yaml']
)
k8s_yaml(yaml)
yaml = helm(
   'test-283-deploy/src/main/resources/apps/metadata',
   name='metadata',
   values=['test-283-deploy/src/main/resources/apps/metadata/values.yaml',
       'test-283-deploy/src/main/resources/apps/metadata/values-dev.yaml']
)
k8s_yaml(yaml)
yaml = helm(
   'test-283-deploy/src/main/resources/apps/spark-operator',
   name='spark-operator',
   values=['test-283-deploy/src/main/resources/apps/spark-operator/values.yaml',
       'test-283-deploy/src/main/resources/apps/spark-operator/values-dev.yaml']
)
k8s_yaml(yaml)
# policy-decision-point
docker_build(  
    ref='test-283-policy-decision-point-docker',
    context='test-283-docker/test-283-policy-decision-point-docker',
    build_args=build_args,
    dockerfile='test-283-docker/test-283-policy-decision-point-docker/src/main/resources/docker/Dockerfile'
)


yaml = helm(
   'test-283-deploy/src/main/resources/apps/hive-metastore-service',
   name='hive-metastore-service',
   values=['test-283-deploy/src/main/resources/apps/hive-metastore-service/values.yaml',
       'test-283-deploy/src/main/resources/apps/hive-metastore-service/values-dev.yaml']
)
k8s_yaml(yaml)
yaml = helm(
   'test-283-deploy/src/main/resources/apps/hive-metastore-db',
   name='hive-metastore-db',
   values=['test-283-deploy/src/main/resources/apps/hive-metastore-db/values.yaml',
       'test-283-deploy/src/main/resources/apps/hive-metastore-db/values-dev.yaml']
)
k8s_yaml(yaml)
yaml = helm(
   'test-283-deploy/src/main/resources/apps/spark-infrastructure',
   name='spark-infrastructure',
   values=['test-283-deploy/src/main/resources/apps/spark-infrastructure/values.yaml',
       'test-283-deploy/src/main/resources/apps/spark-infrastructure/values-dev.yaml']
)
k8s_yaml(yaml)

yaml = helm(
   'test-283-deploy/src/main/resources/apps/s3-local',
   name='s3-local',
   values=['test-283-deploy/src/main/resources/apps/s3-local/values.yaml',
       'test-283-deploy/src/main/resources/apps/s3-local/values-dev.yaml']
)
k8s_yaml(yaml)
yaml = helm(
   'test-283-deploy/src/main/resources/apps/pipeline-invocation-service',
   name='pipeline-invocation-service',
   values=['test-283-deploy/src/main/resources/apps/pipeline-invocation-service/values.yaml',
       'test-283-deploy/src/main/resources/apps/pipeline-invocation-service/values-dev.yaml']
)
k8s_yaml(yaml)
# Add deployment resources here
