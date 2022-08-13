package com.lifei.enjoystudy;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        File tempFileApk=new File("src/apk/temp");
        if (tempFileApk.exists()){
            File[] files=tempFileApk.listFiles();
            for (File file : files) {
                if (file.isFile()){
                    file.delete();
                }
            }
        }else {
            tempFileApk.mkdirs();
        }

        File tempFileAar=new File("src/aar/temp");
        if (tempFileAar.exists()){
            File[] files=tempFileAar.listFiles();
            for (File file : files) {
                if (file.isFile()){
                    file.delete();
                }
            }
        }else {
            tempFileAar.mkdirs();
        }
    }
}