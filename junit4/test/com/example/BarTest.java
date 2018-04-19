package com.example;

import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class BarTest {

    private static final Logger log = Logger.getLogger("BarTest");

    @Test
    public void testGreeting_Joe() {
        Singleton target = Singleton.getInstance();
        log.info("CL:" + this.getClass().getClassLoader() + ",Test:" + this + ", target:" + target);

        assertEquals(target.greeting(), "Hello, noname");
    }

    @Test
    public void testGreeting_Mike() {
        Singleton target = Singleton.getInstance();
        log.info("CL:" + this.getClass().getClassLoader() + ",Test:" + this + ", target:" + target);

        target.setName("Mike");
        assertEquals(target.greeting(), "Hello, Mike");
    }
}
