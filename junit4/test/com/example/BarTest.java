package com.example;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class BarTest {
    private static final String PID = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

    private static final Logger log = Logger.getLogger("BarTest");

    @Test
    public void testGreeting_Joe() {
        Singleton target = Singleton.getInstance();
        log.info("PID:" + PID + ",Test:" + this + ", target:" + target);

        assertEquals(target.greeting(), "Hello, noname");
    }

    @Test
    public void testGreeting_Mike() {
        Singleton target = Singleton.getInstance();
        log.info("PID:" + PID + ",Test:" + this + ", target:" + target);

        target.setName("Mike");
        assertEquals(target.greeting(), "Hello, Mike");
    }
}
