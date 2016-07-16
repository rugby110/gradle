#!/bin/bash -ex
export DISTRIBUTION=$PWD/build/gradle-3.0-snapchat.zip
rm -rf $DISTRIBUTION

./gradlew install -Pgradle_installPath=build/distribution
cd build/distribution
zip -r $DISTRIBUTION .

# TODO: Set gradle properties distirubutionUrl to point to file

# Build a project that consumes this artifact (from a local url)
# to test before passing the build
cd example
./gradlew build
cd ..
