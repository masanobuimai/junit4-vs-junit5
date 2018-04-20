package com.example;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class FixMethodOrderTest {
    private static final Logger log = Logger.getLogger("FixMethodOrderTest");

    @Test
    public void test() {
        assertAll(() -> test1(),
                  () -> test2(),
                  () -> test3(),
                  () -> test4());
    }

    public void test4() {
        log.info("enter");
        assertTrue(true);
    }

    public void test3() {
        log.info("enter");
        assertTrue(true);
    }

    public void test2() {
        log.info("enter");
        fail("失敗");
    }

    public void test1() {
        log.info("enter");
        assertTrue(true);
    }
}
