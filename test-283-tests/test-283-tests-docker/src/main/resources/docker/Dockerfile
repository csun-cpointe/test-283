# Generic Java test Docker image
#
# GENERATED DOCKERFILE - please ***DO*** modify.
#
# Generated from: templates/general-docker/it.java.docker.file.vm
ARG DOCKER_BASELINE_REPO_ID
FROM ${DOCKER_BASELINE_REPO_ID}boozallen/openjdk:11-slim

COPY ./target/specifications/ ./specifications
COPY ./target/test-283-tests-docker.jar ./
