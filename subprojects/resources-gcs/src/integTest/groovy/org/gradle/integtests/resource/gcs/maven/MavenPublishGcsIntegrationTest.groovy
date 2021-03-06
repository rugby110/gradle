/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.integtests.resource.gcs.maven

import org.gradle.integtests.fixtures.publish.maven.AbstractMavenPublishIntegTest
import org.gradle.integtests.resource.gcs.fixtures.GcsServer
import org.gradle.integtests.resource.gcs.fixtures.MavenGcsRepository
import org.junit.Rule
import spock.lang.Ignore

class MavenPublishGcsIntegrationTest extends AbstractMavenPublishIntegTest {
    @Rule
    public GcsServer server = new GcsServer(temporaryFolder)

    def setup() {
        executer.withArgument("-Dorg.gradle.Gcs.endpoint=${server.getUri()}")
    }

    @Ignore
    def "can publish to a Gcs Maven repository"() {
        given:
        def mavenRepo = new MavenGcsRepository(server, file("repo"), "/maven", "testGcsBucket")
        settingsFile << 'rootProject.name = "publishGcsTest"'
        buildFile << """
apply plugin: 'java'
apply plugin: 'maven-publish'

group = 'org.gradle.test'
version = '1.0'

publishing {
    repositories {
        maven {
            url "${mavenRepo.uri}"
            credentials(AwsCredentials) {
                accessKey "someKey"
                secretKey "someSecret"
            }
        }
    }
    publications {
        maven(MavenPublication) {
            from components.java
        }
    }
}
"""

        when:
        def module = mavenRepo.module('org.gradle.test', 'publishGcsTest', '1.0')
        module.artifact.expectUpload()
        module.artifact.sha1.expectUpload()
        module.artifact.md5.expectUpload()
        module.pom.expectUpload()
        module.pom.sha1.expectUpload()
        module.pom.md5.expectUpload()
        module.mavenRootMetaData.expectDownloadMissing()
        module.mavenRootMetaData.expectUpload()
        module.mavenRootMetaData.sha1.expectUpload()
        module.mavenRootMetaData.md5.expectUpload()

        succeeds 'publish'

        then:
        module.assertPublishedAsJavaModule()
        module.parsedPom.scopes.isEmpty()
    }
}
