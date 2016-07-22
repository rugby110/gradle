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

package org.gradle.internal.resource.transport.gcs;

import com.google.api.client.util.DateTime;
import com.google.api.services.storage.model.StorageObject;
import org.gradle.internal.hash.HashValue;
import org.gradle.internal.resource.metadata.DefaultExternalResourceMetaData;
import org.gradle.internal.resource.metadata.ExternalResourceMetaData;
import org.gradle.internal.resource.transfer.ExternalResourceReadResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class GcsResource implements ExternalResourceReadResponse {

    private final GcsClient gcsClient;
    private final StorageObject gcsObject;
    private final URI uri;

    public GcsResource(GcsClient gcsClient, StorageObject gcsObject, URI uri) {
        this.gcsClient = gcsClient;
        this.gcsObject = gcsObject;
        this.uri = uri;
    }

    public InputStream openStream() throws IOException {
        return gcsClient.getResourceStream(gcsObject);
    }

    public URI getURI() {
        return uri;
    }

    public long getContentLength() {
        return gcsObject.getSize().longValue();
    }

    public boolean isLocal() {
        return false;
    }

    public ExternalResourceMetaData getMetaData() {
        DateTime lastModified = gcsObject.getUpdated();

        return new DefaultExternalResourceMetaData(uri,
                lastModified.getValue(),
                getContentLength(),
                gcsObject.getContentType(),
                gcsObject.getEtag(),
                new HashValue(gcsObject.getMd5Hash()));
    }

    @Override
    public void close() throws IOException {
        // no-op
    }
}
