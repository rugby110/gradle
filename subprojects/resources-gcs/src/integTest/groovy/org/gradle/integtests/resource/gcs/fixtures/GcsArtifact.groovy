/*
 * Copyright 2015 the original author or authors.
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

package org.gradle.integtests.resource.gcs.fixtures

import org.gradle.test.fixtures.file.TestFile
import org.gradle.test.fixtures.resource.RemoteArtifact

public class GcsArtifact extends GcsResource implements RemoteArtifact {
    public GcsArtifact(GcsServer server, TestFile file, String repositoryPath, String bucket) {
        super(server, file, repositoryPath, bucket)
    }

    @Override
    public org.gradle.integtests.resource.gcs.fixtures.GcsResource getMd5() {
        return new GcsResource(server, file.parentFile.file(file.name + ".md5"), repositoryPath, bucket)
    }

    @Override
    public org.gradle.integtests.resource.gcs.fixtures.GcsResource getSha1() {
        return new GcsResource(server, file.parentFile.file(file.name + ".sha1"), repositoryPath, bucket)
    }
}
