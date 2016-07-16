#!/bin/bash -ex
./build.sh
gsutil cp build/gradle-3.0-snapchat.zip gs://snapengine-builder/gradle-3.0-snapchat.zip
gsutil -D setacl public-read gs://snapengine-builder/gradle-3.0-snapchat.zip
