#!/bin/bash -ex
./build.sh
gsutil cp build/gradle-3.0-snapchat.zip gs://snapengine-builder
