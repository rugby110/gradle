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
import org.gradle.internal.resource.local.LocalResource;
import org.gradle.internal.resource.metadata.DefaultExternalResourceMetaData;
import org.gradle.internal.resource.metadata.ExternalResourceMetaData;
import org.gradle.internal.resource.transfer.ExternalResourceConnector;
import org.gradle.internal.resource.transfer.ExternalResourceReadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

public class GcsResourceConnector implements ExternalResourceConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(GcsResourceConnector.class);
    private final GcsClient gcsClient;

    public GcsResourceConnector(GcsClient gcsClient) {
        this.gcsClient = gcsClient;
    }

    public List<String> list(URI parent) {
        LOGGER.debug("Listing parent resources: {}", parent);
        return gcsClient.list(parent);
    }

    public ExternalResourceReadResponse openResource(URI location) {
        LOGGER.debug("Attempting to get resource: {}", location);
        StorageObject gcsObject = gcsClient.getResource(location);
        if (gcsObject == null) {
            return null;
        }
        return new GcsResource(gcsClient, gcsObject, location);
    }

    public ExternalResourceMetaData getMetaData(URI location) {
        LOGGER.debug("Attempting to get resource metadata: {}", location);
        StorageObject gcsObject = gcsClient.getResource(location);
        if (gcsObject == null) {
            return null;
        }

        DateTime lastModified = gcsObject.getUpdated();

        return new DefaultExternalResourceMetaData(location,
            lastModified.getValue(),
            gcsObject.getSize().longValue(),
            gcsObject.getContentType(),
            gcsObject.getEtag(),
            new HashValue(gcsObject.getMd5Hash()));
    }

    @Override
    public void upload(LocalResource resource, URI destination) throws IOException {
        LOGGER.debug("Attempting to upload stream to : {}", destination);
        InputStream inputStream = resource.open();
        try {
            gcsClient.put(inputStream, resource.getContentLength(), destination);
        } finally {
            inputStream.close();
        }
    }
}
