package com.example;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FixMethodOrderTest {
    @Test
    public void test4() {
        assertTrue(true);
    }

    @Test
    public void test3() {
        assertTrue(true);
    }

    @Test
    public void test2() {
        fail();
    }

    @Test
    public void test1() {
        assertTrue(true);
    }
}
