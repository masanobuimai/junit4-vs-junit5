package com.example;


import org.junit.jupiter.api.Test;

import java.lang.management.ManagementFactory;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class FooTest {
    private static final String PID = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    private static final Logger log = Logger.getLogger("FooTest");

    @Test
    public void testGreeting() {
        Singleton target = Singleton.getInstance();
        log.info("PID:" + PID + ",Test:" + this + ", target:" + target);

        assertEquals(target.greeting(), "Hello, noname");
    }
}
