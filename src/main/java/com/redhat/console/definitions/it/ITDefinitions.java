package com.redhat.console.definitions.it;

import com.redhat.console.definitions.rbac.RBACDefinitions;
import jakarta.ws.rs.HttpMethod;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import shaded_package.org.apache.commons.io.IOUtils;

import java.io.IOException;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class ITDefinitions {
    private ITDefinitions() {}

    public static void setDefinitions(final ClientAndServer mockServer) throws IOException {
        final byte[] itUserResponse = IOUtils.toByteArray(RBACDefinitions.class.getClassLoader().getResource("stubs/it/it-user-response.json"));

        mockServer
            .when(
                request()
                    .withMethod(HttpMethod.POST)
                    .withPath("/v2/findUsers")
            )
            .respond(
                response()
                    .withBody(itUserResponse)
                    .withHeader(new Header("Content-Type", "application/json"))
                    .withStatusCode(200)
            );
    }
}
