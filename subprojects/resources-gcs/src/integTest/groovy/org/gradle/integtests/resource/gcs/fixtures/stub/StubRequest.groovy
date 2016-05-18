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

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
package org.gradle.integtests.resource.gcs.fixtures.stub
=======
package org.gradle.integtests.resource.s3.fixtures.stub
>>>>>>> a1aedec... Add support for gcs backed artifact repository
=======
package org.gradle.integtests.resource.s3.fixtures.stub
>>>>>>> a1aedec... Add support for gcs backed artifact repository
=======
package org.gradle.integtests.resource.s3.fixtures.stub
>>>>>>> a1aedec... Add support for gcs backed artifact repository
=======
package org.gradle.integtests.resource.gcs.fixtures.stub
>>>>>>> fa4d1c8... Fix test failures

import groovy.transform.ToString

@ToString(includeNames = true, includeSuper = true)
class StubRequest extends HttpMessage {
    String method
    String path
    Map<String, List<String>> params = [:]
}
