#!/bin/bash -ex

# Move the snapchat.gradle file we haven't deprecated yet
OLDCONFIG=$HOME/.gradle/init.d
BAKCONFIG=$HOME/.gradle/init.d.old
trap "test ! -e $OLDCONFIG && test -e $BAKCONFIG && mv $BAKCONFIG $OLDCONFIG" 0
[ -e "$OLDCONFIG" ] && rm -rf "$BAKCONFIG" && mv "$OLDCONFIG" "$BAKCONFIG"


# Setup the output location, remove stale artifacts
export DISTRIBUTION=$PWD/build/gradle-3.0-snapchat.zip
[ -z "$GRADLE_USER_HOME" ] && GRADLE_USER_HOME=$HOME

rm -f "$DISTRIBUTION"
rm -rf "$GRADLE_USER_HOME"/.gradle/wrapper/dists/*

./gradlew outputsZip -Pgradle_installPath=build/distribution
ln -sf $PWD/build/distributions/gradle-3.0-snapshot-*bin.zip "$DISTRIBUTION"

# Build a project that consumes this artifact (from a local url)
# to test before passing the build
ln -sf "$DISTRIBUTION" example/gradle/wrapper
ln -sf "$DISTRIBUTION" example-with-no-fallback/gradle/wrapper

# Run a test that uses GCS and then falls back to artifactory
cd example
./gradlew build -S --info
cd ..

# Run a test that uses GCS and does not fall back to artifactory
cd example-with-no-fallback
# TODO: uncomment to test plugin
# ./gradlew build -S --info
cd ..
