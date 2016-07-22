# Sample Webapp

This sub-project demonstrates a minimal application that's built from a pair of standard Servlets.

## Quick Start
### Running

This section quickly shows a few options for running locally.

To run with INFO level logging enabled:
```
./gradlew webapp-example:snapengineRun
```

To run with DEBUG level logging enabled:
```
DEBUG=true ./gradlew webapp-example:snapengineRun
```

While server is running, use Chrome or Curl to ping the sample servlet
```
curl http://127.0.0.1:8888/webapp-example-1.0/
```

### Deploying (N=1)

This section quickly shows a few options for running on a single instance

To deploy a personal stack to Google cloud (re-using previously provisioned resources as much as possible to speed things up):
```
./gradlew  webapp-example:snapengineDeploy
```

To deploy a personal stack to Google cloud (re-using previously uploaded artifacts as much as possible to speed things up)
This is useful for an automated build process:
```
./gradlew  webapp-example:snapengineDeploy -Pterminate=true
```

To deploy a personal stack to Google cloud, re-provisioning everything (start from a clean slate):
```
./gradlew  webapp-example:snapengineDeploy -Pterminate=true -Poverwrite=true
```

### Imaging

To create an image from a personal stack which can be autoscaled:
```
./gradlew  webapp-example:snapengineImage
```

### Testing

To run unit tests:
```
./gradlew  webapp-example:test
```

To run integration tests:
```
./gradlew  webapp-example:integrationTest
```

To run integration tests against a particular deployment:
```
./gradlew  webapp-example:integrationTest -Pstack=some-deployment
```

### Continuous Integration

A continuous integration workflow would be able to typically run tests, and even do small scale deployments automatically in order to produce and vet an **image**.

### Basic Flow

The basic flow works like this:

First, you'd run the **build** target. SnapEngine packages won't pass (the SnapEngine plugin sets up your gradle target dependencies for you to ensure this). This runs unit tests and will build all your releasable artifacts.

```
./gradlew build
```

Next this **integrationTest** target would run. This is where tests go that require access to real resource - for example, cloud resources because the tests setup and tear-down a real stack, or even access to a Guice context used to construct clients for testing that stack.

```
./gradlew integrationTest
```

You could integrate testing other deployments, this lets you add custom deployment phases so you run your integrationTests on a variety of instance sizes or to update stacks that are connected to other deployment environments. You can set a stack parameter to create different isolated deployments.

```
./gradlew snapengineDeploy -Pstack=ads-integration-test-memcache -Pinstance-size=bigger -Preal-memcache-addr=...
./gradlew integrationTest -Pstack=ads-integration-test-memcache
```

After running several integration test phases, you would update a shared stack, like prod. These deployments would leverage auto-scaling features to allow you to keep N-hosts running or scale that N hosts up or down. The main input the auto-scaling features is an **image**, so the last target you might want to run is.

You might put this through more rigours testing (like 1% of traffic for a while somewhere, etc)
```
./gradlew snapengineImage -Pstack=ads-alpha
```
or

Or you might just call it prod, depending on what level of testing you are after
```
./gradlew snapengineImage -Pstack=ads-prod
```
