#!/bin/bash -ex

# optionally use your own test credentials
# export GOOGLE_APPLICATION_CREDENTIALS_FILE=/var/lib/jenkins/credentials/my-test-credentials.devsnapchat.json

# Build and release Java artifacts from Jenkins
if [ x$USER == xjenkins ]; then
    ./gradlew clean build --info -S
    ./gradlew -Dorg.gradle.parallel=false artifactoryPublish --info -S
else
    echo "Skipping artifactory publish for non-Jenkins user $USER"
fi
