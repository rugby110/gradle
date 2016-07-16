#!/bin/bash -ex
rm -rf build
./gradlew install -Pgradle_installPath=build/distribution
cp -v build/distributions/gradle-3.0-*-bin.zip build/gradle-3.0-snapchat.zip

export DISTIBUTION=$PWD/build/gradle-3.0-snapchat.zip
# TODO: Set gradle properties distirubutionUrl to point to file

# Build a project that consumes this artifact (from a local url)
# to test before passing the build
cd example
./gradlew build
cd ..
