package com.redhat.console.definitions.it;

import jakarta.ws.rs.HttpMethod;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import shaded_package.org.apache.commons.io.IOUtils;

import java.io.IOException;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class ITDefinitions {
    private static final String IT_API_PREFIX_V2 = "/v2";
    private static final String IT_PATH_FIND_USERS = String.format("%s/findUsers", IT_API_PREFIX_V2);

    private ITDefinitions() {}

    public static void setDefinitions(final ClientAndServer mockServer) throws IOException {
        final byte[] itUserResponse = IOUtils.toByteArray(ITDefinitions.class.getClassLoader().getResource("stubs/it/it-user-response.json"));

        mockServer
            .when(
                request()
                    .withMethod(HttpMethod.POST)
                    .withPath(IT_PATH_FIND_USERS)
            )
            .respond(
                response()
                    .withBody(itUserResponse)
                    .withHeader(new Header("Content-Type", "application/json"))
                    .withStatusCode(200)
            );
    }
}
