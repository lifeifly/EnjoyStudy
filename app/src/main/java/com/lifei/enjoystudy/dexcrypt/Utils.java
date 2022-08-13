package com.lifei.enjoystudy.dexcrypt;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Utils {

    public static byte[] getBytes(File dexFile) throws IOException {
        RandomAccessFile raf=new RandomAccessFile(dexFile,"r");
        byte[] buffer=new byte[(int)raf.length()];
        raf.readFully(buffer);
        raf.close();
        return buffer;
    }
}
