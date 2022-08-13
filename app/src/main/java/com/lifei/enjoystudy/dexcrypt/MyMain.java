package com.lifei.enjoystudy.dexcrypt;

import java.io.File;
import java.io.FileOutputStream;

public class MyMain {

    public static void main(String[] args) throws Exception{
        byte[] mainDexData;
        byte[] aarData;
        byte[] mergeDex;

//        File tempFileApk=new File("src/apk/temp");
//        if (tempFileApk.exists()){
//            File[] files=tempFileApk.listFiles();
//            for (File file : files) {
//                if (file.isFile()){
//                    file.delete();
//                }
//            }
//        }else {
//            tempFileApk.mkdirs();
//        }
//
//        File tempFileAar=new File("src/aar/temp");
//        if (tempFileAar.exists()){
//            File[] files=tempFileAar.listFiles();
//            for (File file : files) {
//                if (file.isFile()){
//                    file.delete();
//                }
//            }
//        }else {
//            tempFileAar.mkdirs();
//        }
        AES.init(AES.DEFAULT_PWD);
        File apkFile=new File("C:\\Users\\admin\\Desktop\\EnjoyStudy\\app\\src\\apk\\app-debug.apk");
        System.out.println("Apk is exist"+apkFile.exists());
        File newApkFile=new File(apkFile.getParent()+File.separator+"temp");
        if (!newApkFile.exists()){
            newApkFile.mkdirs();
        }

        File mainDexFile=AES.encryptApkFile(apkFile,newApkFile);
        if (newApkFile.isDirectory()){
            File[] listFiles=newApkFile.listFiles();
            for (File listFile : listFiles) {
                if (listFile.isFile()){
                    if (listFile.getName().endsWith(".dex")){
                        String name=listFile.getName();
                        System.out.println("rename step1:"+name);
                        int cursor = name.indexOf(".dex");
                        String newName = listFile.getParent()+ File.separator + name.substring(0, cursor) + "_" + ".dex";
                        System.out.println("rename step2:"+newName);
                        listFile.renameTo(new File(newName));
                    }
                }
            }
        }

        File aarFile=new File("C:\\Users\\admin\\Desktop\\EnjoyStudy\\app\\src\\aar\\mylibrary-debug.aar");
        File aarDex=Dx.jar2Dex(aarFile);

        File tempMainDex=new File(newApkFile.getPath()+File.separator+"classes.dex");
        if (!tempMainDex.exists()){
            tempMainDex.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(tempMainDex);
        byte[] fbytes=Utils.getBytes(aarDex);
        fos.write(fbytes);
        fos.flush();
        fos.close();

        File unsignedApk=new File("C:\\Users\\admin\\Desktop\\EnjoyStudy\\app\\src\\result\\apk-unsigned.apk");
        unsignedApk.getParentFile().mkdirs();

        Zip.zip(newApkFile, unsignedApk);

        File signedApk = new File("C:\\Users\\admin\\Desktop\\EnjoyStudy\\app\\src\\result\\apk-signed.apk");
        Signature.signature(unsignedApk, signedApk);
    }
}
