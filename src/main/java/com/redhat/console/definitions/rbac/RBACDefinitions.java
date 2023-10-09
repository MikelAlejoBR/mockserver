package com.redhat.console.definitions.rbac;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.Parameter;
import shaded_package.org.apache.commons.io.IOUtils;

import java.io.IOException;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.Parameter.schemaParam;

public class RBACDefinitions {
    private static final String RBAC_PATH_PREFIX_V1 = "/api/rbac/v1";
    private static final String RBAC_PATH_GROUPS_PRINCIPAL = String.format("%s/groups/{uuid}/principals/", RBAC_PATH_PREFIX_V1);
    private static final String RBAC_PATH_GROUPS_UUID = String.format("%s/groups/{uuid}/", RBAC_PATH_PREFIX_V1);
    private static final String RBAC_PATH_PRINCIPALS = String.format("%s/principals/", RBAC_PATH_PREFIX_V1);

    private RBACDefinitions() {}

    public static void setDefinitions(final ClientAndServer mockServer) throws IOException {
        final byte[] rbacUserResponse = IOUtils.toByteArray(RBACDefinitions.class.getClassLoader().getResource("stubs/rbac/rbac-user-response.json"));
        final byte[] rbacUserResponsePage2 = IOUtils.toByteArray(RBACDefinitions.class.getClassLoader().getResource("stubs/rbac/rbac-user-response-page-2.json"));
        final byte[] rbacUserResponsePage3 = IOUtils.toByteArray(RBACDefinitions.class.getClassLoader().getResource("stubs/rbac/rbac-user-response-page-3.json"));
        final byte[] rbacGroupResponse = IOUtils.toByteArray(RBACDefinitions.class.getClassLoader().getResource("stubs/rbac/rbac-group-response.json"));

        mockServer
            .when(
                request()
                    .withPath(RBAC_PATH_PRINCIPALS)
                    .withQueryStringParameters(
                        new Parameter("offset", "0"),
                        schemaParam("limit", "{\"type\": \"integer\"}"),
                        schemaParam("admin_only", "{\"type\": \"boolean\"}")
                    )
            )
            .respond(
                response()
                    .withHeader(new Header("Content-Type", "application/json"))
                    .withStatusCode(200)
                    .withBody(rbacUserResponse)
            );

        mockServer
            .when(
                request()
                    .withPath(RBAC_PATH_PRINCIPALS)
                    .withQueryStringParameters(
                        new Parameter("offset", "5"),
                        schemaParam("limit", "{\"type\": \"integer\"}"),
                        schemaParam("admin_only", "{\"type\": \"boolean\"}")
                    )
            )
            .respond(
                response()
                    .withHeader(new Header("Content-Type", "application/json"))
                    .withStatusCode(200)
                    .withBody(rbacUserResponsePage2)
            );
        mockServer
            .when(
                request()
                    .withPath(RBAC_PATH_PRINCIPALS)
                    .withQueryStringParameters(
                        new Parameter("offset", "10"),
                        schemaParam("limit", "{\"type\": \"integer\"}"),
                        schemaParam("admin_only", "{\"type\": \"boolean\"}")
                    )
            )
            .respond(
                response()
                    .withHeader(new Header("Content-Type", "application/json"))
                    .withStatusCode(200)
                    .withBody(rbacUserResponsePage3)
            );

        mockServer
            .when(
                request()
                    .withPath(RBAC_PATH_GROUPS_UUID)
                    .withPathParameter("uuid", "ff628f79-48ac-4113-b1a4-562f8d55f0a8")
            )
            .respond(
                response()
                    .withHeader(new Header("Content-Type", "application/json"))
                    .withStatusCode(404)
                    .withBody(rbacGroupResponse)
            );
    }
}
