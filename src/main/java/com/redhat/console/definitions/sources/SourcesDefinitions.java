package com.redhat.console.definitions.sources;

import jakarta.ws.rs.HttpMethod;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import shaded_package.org.apache.commons.io.IOUtils;

import java.io.IOException;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class SourcesDefinitions {
    private static final String SOURCES_API_PREFIX = "/api/sources/v3.1";
    private static final String SOURCES_PATH_SECRETS = String.format("%s/secrets", SOURCES_API_PREFIX);

    private SourcesDefinitions() {}

    public static void setDefinitions(final ClientAndServer mockServer) throws IOException {
        final byte[] sourcesResponse = IOUtils.toByteArray(SourcesDefinitions.class.getClassLoader().getResource("stubs/sources/sources-response.json"));

        mockServer
            .when(
                request()
                    .withMethod(HttpMethod.POST)
                    .withPath(SOURCES_PATH_SECRETS)
            )
            .respond(
                response()
                    .withBody(sourcesResponse)
                    .withHeader(new Header("Content-Type", "application/json"))
                    .withStatusCode(200)
            );
    }
}
