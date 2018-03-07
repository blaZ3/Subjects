package com.example.subjects.app.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileHelper {
    private static final String TAG = FileHelper.class.getSimpleName();

    public static String root_directory = Environment.getExternalStorageDirectory().getPath() + "/Subjects";

    public static String getFilePathForName(String name){
        String filePath = root_directory + "/" + getMD5Hash(name);
        try{
            final File rootDir = new File(root_directory);
            if(!rootDir.exists())
                rootDir.mkdirs();

            return filePath;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static String savefile(Context context, Uri sourceuri, String time) throws FileNotFoundException {
        String outputFile = getFilePathForName(time+sourceuri.toString());

        InputStream in = context.getContentResolver().openInputStream(sourceuri);

        createFileFromInputStream(in, outputFile);
        return outputFile;
    }

    private static File createFileFromInputStream(InputStream inputStream, String fileName) {
        try{
            File f = new File(fileName);
            f.setWritable(true, false);
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while((length=inputStream.read(buffer)) > 0) {
                outputStream.write(buffer,0,length);
            }

            outputStream.close();
            inputStream.close();

            return f;
        }catch (IOException e) {
            System.out.println("error in creating a file");
            e.printStackTrace();
        }

        return null;

    }


    public static String getMD5Hash(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(),0,s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }

}
