#!/bin/bash -ex

# Move the snapchat.gradle file we haven't deprecated yet
OLDCONFIG=$HOME/.gradle/init.d
BAKCONFIG=$HOME/.gradle/init.d.old
trap "test ! -e $OLDCONFIG && test -e $BAKCONFIG && mv $BAKCONFIG $OLDCONFIG" 0
[ -e "$OLDCONFIG" ] && rm -rf "$BAKCONFIG" && mv "$OLDCONFIG" "$BAKCONFIG"


# Setup the output location, remove stale artifacts
export DISTRIBUTION=$PWD/build/gradle-3.0-snapchat.zip
[ -z "$GRADLE_USER_HOME" ] && GRADLE_USER_HOME=$HOME

# Gradle cache is super aggressive, and shitty - we have to nuke it we'll pick up
# the wrong artifacts
rm -f "$DISTRIBUTION"
rm -rf "$GRADLE_USER_HOME"/.gradle/wrapper/dists/*
find -name .gradle -exec rm -rf {} \; || true

./gradlew outputsZip -Pgradle_installPath=build/distribution
ln -sf $PWD/build/distributions/gradle-3.0-snapshot-*bin.zip "$DISTRIBUTION"

# Build a project that consumes this artifact (from a local url)
# to test before passing the build
ln -sf "$DISTRIBUTION" example/gradle/wrapper
ln -sf "$DISTRIBUTION" example-with-publish/gradle/wrapper
ln -sf "$DISTRIBUTION" example-with-no-fallback/gradle/wrapper

# Run a test that uses GCS and then falls back to artifactory
cd example
rm -rf ./.gradle
./gradlew build -S --info
cd ..

# Run a test that publishes a well-known artifact
cd example-with-publish
rm -rf ./.gradle
./gradlew build publish -S --info
cd ..

# Run a test that uses GCS and does not fall back to artifactory
# (This test needs to be adjusted to do what the comment above says)
cd example-with-no-fallback
rm -rf ./.gradle
./gradlew build -S --info
cd ..
