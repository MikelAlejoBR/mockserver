package com.redhat.console.definitions.it;

import jakarta.ws.rs.HttpMethod;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import shaded_package.org.apache.commons.io.IOUtils;

import java.io.IOException;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.Parameter.schemaParam;

public class ITDefinitions {
    private static final String IT_API_PREFIX_V2 = "/v2";
    private static final String IT_PATH_FIND_USERS = String.format("%s/findUsers", IT_API_PREFIX_V2);
    private static final String IT_PATH_SERVICE_ACCOUNT = "/auth/realms/redhat-external/apis/service_accounts/v1/{uuid}";
    private static final String IT_PATH_SERVICE_ACCOUNTS = "/auth/realms/redhat-external/apis/service_accounts/v1";

    private ITDefinitions() {}

    public static void setDefinitions(final ClientAndServer mockServer) throws IOException {
        final byte[] itUserResponse = IOUtils.toByteArray(ITDefinitions.class.getClassLoader().getResource("stubs/it/it-user-response.json"));
        final byte[] itServiceAccountResponse = IOUtils.toByteArray(ITDefinitions.class.getClassLoader().getResource("stubs/it/it-service-account-response.json"));
        final byte[] itServiceAccountsResponse = IOUtils.toByteArray(ITDefinitions.class.getClassLoader().getResource("stubs/it/it-service-accounts-response.json"));

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

        mockServer
            .when(
                request()
                    .withMethod(HttpMethod.GET)
                    .withPath(IT_PATH_SERVICE_ACCOUNTS)
            )
            .respond(
                response()
                    .withBody(itServiceAccountsResponse)
                    .withHeader(new Header("Content-Type", "application/json"))
                    .withStatusCode(200)
            );

        mockServer
            .when(
                request()
                    .withMethod(HttpMethod.GET)
                    .withPath(IT_PATH_SERVICE_ACCOUNT)
                    .withPathParameter(schemaParam("uuid", "{\"type\": \"string\"}"))
            ).respond(
                response()
                    .withBody(itServiceAccountResponse)
                    .withHeader(new Header("Content-Type", "application/json"))
                    .withStatusCode(200)
            );
    }
}
