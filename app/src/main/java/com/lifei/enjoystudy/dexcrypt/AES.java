package com.lifei.enjoystudy.dexcrypt;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    public static final String DEFAULT_PWD = "abcdefghijklmnop";

    private static final String algorithmStr = "AES/ECB/PKCS5Padding";

    private static Cipher encryptCipher;

    private static Cipher decryptCipher;

    public static void init(String password) {
        try {
            encryptCipher = Cipher.getInstance(algorithmStr);
            decryptCipher = Cipher.getInstance(algorithmStr);

            byte[] keyStr = password.getBytes();
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public static File encryptApkFile(File srcApkFile, File dstApkFile) throws IOException {
        if (srcApkFile == null) {
            System.out.println("encryptAPKFile :srcAPKfile null");
            return null;
        }

        Zip.unZip(srcApkFile,dstApkFile);

        File[] dexFiles=dstApkFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".dex");
            }
        });

        File mainDexFile=null;
        byte[] mainDexData=null;

        for (File dexFile : dexFiles) {
            byte[] buffer=Utils.getBytes(dexFile);

            byte[] encryptBytes=AES.encrypt(buffer);

            if (dexFile.getName().equals("classes.dex")){
                mainDexData=encryptBytes;
                mainDexFile=dexFile;
            }

            try {
                FileOutputStream fos=new FileOutputStream(dexFile);
                fos.write(encryptBytes);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return mainDexFile;
    }

    public static byte[] encrypt(byte[] content) {
        try {
            byte[] result = encryptCipher.doFinal(content);
            return result;
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] content) {
        try {
            byte[] result = decryptCipher.doFinal(content);
            return result;
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
