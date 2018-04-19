package com.example;

import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class FooTest {
    private static final Logger log = Logger.getLogger("BarTest");

    @Test
    public void testGreeting() {
        Singleton target = Singleton.getInstance();
        log.info("CL:" + this.getClass().getClassLoader() + ",Test:" + this + ", target:" + target);

        assertEquals(target.greeting(), "Hello, noname");
    }
}
