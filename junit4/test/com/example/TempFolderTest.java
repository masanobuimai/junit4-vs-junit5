package com.example;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.*;

public class TempFolderTest {
    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void testTempFileExist() throws Exception {
        temp.newFile("hoge");
        assertTrue(new File(temp.getRoot(), "hoge").exists());
    }

    @Test
    public void testTempFileNotExist() throws Exception {
        assertFalse(new File(temp.getRoot(), "hoge").exists());
    }

}
