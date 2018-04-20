package com.example;

import com.github.ralfstuckert.junit.jupiter.extension.tempfolder.TempFolder;
import com.github.ralfstuckert.junit.jupiter.extension.tempfolder.TemporaryFolderExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TemporaryFolderExtension.class)
public class TempFolderTest {
    private static final Logger log = Logger.getLogger("TempFolderTest");

    @Test
    public void testTempFileExist(@TempFolder File temp) throws Exception {
        new File(temp, "hoge").createNewFile();
        assertTrue(new File(temp, "hoge").exists());
        log.info("temp=" + temp.getPath());
    }

    @Test
    public void testTempFileNotExist(@TempFolder File temp) throws Exception {
        assertFalse(new File(temp, "hoge").exists());
        log.info("temp=" + temp.getPath());
    }
}
