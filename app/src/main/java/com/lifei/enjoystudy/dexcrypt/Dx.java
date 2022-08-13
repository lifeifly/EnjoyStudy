package com.lifei.enjoystudy.dexcrypt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

public class Dx {

    public static File jar2Dex(File aarFile)throws IOException, InterruptedException{
        File fakeDex=new File(aarFile.getParent()+File.separator+"temp");
        System.out.println("jar2Dex: aarFile.getParent(): " + aarFile.getParent());
        Zip.unZip(aarFile,fakeDex);

        File[] files=fakeDex.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.equals("classes.jar");
            }
        });
        if (files==null||files.length<=0){
            throw new RuntimeException("the aar is invalidate");
        }
        File classes_jar=files[0];

        File aarDex=new File(classes_jar.getParentFile(),"classes.dex");

        Dx.dxCommand(aarDex, classes_jar);

        return aarDex;
    }

    private static void dxCommand(File aarDex,File classes_jar)throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        String[] command={"cmd.exe" ,"/C" ,"C:\\Program Files (x86)\\Android\\android-sdk\\build-tools\\30.0.2\\dx", "--dex", "--output=", aarDex.getAbsolutePath(),
                classes_jar.getAbsolutePath()};
        Process process = runtime.exec(command);

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }
        if (process.exitValue() != 0) {
            InputStream inputStream = process.getErrorStream();
            int len;
            byte[] buffer = new byte[2048];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while((len=inputStream.read(buffer)) != -1){
                bos.write(buffer,0,len);
            }
            System.out.println(new String(bos.toByteArray(),"GBK"));
            throw new RuntimeException("dx run failed");
        }
        process.destroy();
    }
}
