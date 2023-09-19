package com.redhat.console;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main {
    public static void main(String ...args) {
        Quarkus.run(Server.class, args);
    }
}
