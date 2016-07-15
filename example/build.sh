#!/bin/bash -ex

# This is run for PR-builds

# optionally use your own test credentials
# export GOOGLE_APPLICATION_CREDENTIALS_FILE=/var/lib/jenkins/credentials/my-test-credentials.devsnapchat.json

# Pick up Jenkins build info from environment if exists
JENKINS=${BUILD_TAG}

# build and run test targets
./gradlew clean build test integrationTest slowTest -PJENKINS=${JENKINS} --info -S
