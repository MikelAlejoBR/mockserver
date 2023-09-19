package com.redhat.console;

import com.redhat.console.definitions.it.ITDefinitions;
import com.redhat.console.definitions.rbac.RBACDefinitions;
import com.redhat.console.definitions.sources.SourcesDefinitions;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import org.mockserver.integration.ClientAndServer;

import java.io.IOException;

public class Server implements QuarkusApplication {
    @Override
    public int run(final String... args) throws IOException {
        try (ClientAndServer mockServer = ClientAndServer.startClientAndServer(9999)) {

            ITDefinitions.setDefinitions(mockServer);
            RBACDefinitions.setDefinitions(mockServer);
            SourcesDefinitions.setDefinitions(mockServer);

            Quarkus.waitForExit();
            return 0;
        }
    }
}
