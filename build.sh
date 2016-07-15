#!/bin/bash -ex

rm -rf build
./gradlew install -Pgradle_installPath=../build/distribution
cp -v build/distributions/gradle-3.0-*-bin.zip build/gradle-3.0-snapchat.zip
