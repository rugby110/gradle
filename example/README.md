## Project Setup

* [Intro](#intro)
* [Gradle](#gradle)
* [Source](#source-code)
* [Jenkins](#jenkins)
    * [Release Builds](#release-builds)
    * [Pull Request Builds](#pull-request-builds)
    * [Pipeline Triggers](#pipeline-triggers)
* [Support](#support)

## Intro

* The example project contains everything you need to start building a new Snapchat/snapchat project with Jenkins
* Start by copying the example project to your new project:
```
cp -r example <your-project-name>
```

## Gradle
This gradlew differs from the standard gradle wrapper in two important ways:

1. gradle/init.d/snapchat.gradle
  * Tells gradle how and when to use maven/artifactory
2. gradlew
  * Modified to source the above snapchat.gradle file

## Source Code
* The example source lives in a folder called __webapp-example__
* When you rename it be sure to also make the corresponding change in __settings.gradle__

## Jenkins
**_Before setting up Jenkins, commit the code you copied over from example above_**
#### Creating your project
1. Navigate to [Jenkins](https://snapengine-builder.sc-corp.net/jenkins/) and click __New Item__
![New Item](./documentation/images/new_item.png?raw=true)
2. Enter your project name as you would like it to appear in Jenkins
3. At the bottom of the page, enter __example__ for the item you'd like to create this item from
![New Item](./documentation/images/example.png?raw=true)
4. Click Ok

#### Configuring your project
1. Update the __Path to Project__ to point to your project
2. Update the __Email Alias__ and __GitHub Team__ (if appropriate)
![New Item](./documentation/images/project_info.png?raw=true)
3. Update the Test report XMLs path as appropriate
4. Remove the Pipeline Trigger by clicking on its red X
![New Item](./documentation/images/post_build.png?raw=true)
5. Click Save
6. Navigate to the snapengine_webhook Jenkins project and click Build Now
![New Item](./documentation/images/webhook.png?raw=true)
  * Once the build completes the Jenkins configuration will be updated to recognize your project

### Release Builds
* Release builds run your release.sh script to publish and/or deploy artifacts
* Release builds are triggered when a commit is pushed to master under your project path
* To test, navigate to your Jenkins project and click Build Now

### Pull Request Builds
* PR builds run your build.sh script. _Do not publish and/or deploy artifacts from here_
* PR builds are triggered when a PR is created under your project path to validate prior to merging
* To test, navigate to your Jenkins project and click Pull Request

### Pipeline Triggers
![New Item](./documentation/images/pipeline.png?raw=true)
* You can optionally configure your project to trigger another project using the __Pipeline Trigger__ build step
* This will cause Release builds to trigger the project you specify upon a successful build
* Useful to mimic a test pipeline or to separate out steps such as deployments to different stages

## Support
* SnapEngineBuilder hipchat
* SnapEngine-Team@ email