package com.example.mypersistencyapp.model.utils;

import android.content.Context;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.mypersistencyapp.model.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import androidx.annotation.RequiresApi;

public class FilePersistence {
private static int tuple = 0;
    private FilePersistence() {
    }

    public static boolean writeToFile(String filename,
                                      Context context, User data) {
        FileOutputStream fos = null;
        boolean isStored = false;
        try {
            int offSet = lastPositionFromStream(context,filename);
            if(offSet !=0){
                fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
                fos.write(data.fullname().toString().getBytes()); //StandardC
            }
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(data.fullname().toString().getBytes()); //StandardCharsets.UTF_8

            System.out.println("Data stored : " + data.fullname().toString());
            System.out.print("Filename : "+filename+" / Size of stored data : "+data.fullname().getBytes().length+" Bytes");
            Log.d("Filepersistence.java - ", "Saving data onto device");
            isStored = true;
            tuple++;
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            Log.e("CreateFile", e.getLocalizedMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    Log.e("Clearing stream error :", e.getLocalizedMessage());
                }
            }
        }
        return isStored;
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean readFromFile(String filename,
                                       Context context,
                                       List<User> userList) {
        boolean ioReadingFlag = false;
        FileInputStream fis = null;
        try {
            String temp;
            fis = context.openFileInput(filename);
            byte[] reader = new byte[fis.available()];
            int content;
            while ((content = fis.read()) != -1) {
                System.out.println((char)content);
                ioReadingFlag = true;
            }
        } catch (IOException e) {
            Log.e("Read File error :", e.getLocalizedMessage());
        }
        return ioReadingFlag;
    }

    private static int lastPositionFromStream(Context context, String filename){
        FileInputStream fisLocal = null;
        int value = 0;
        try {
            String temp;
            fisLocal = context.openFileInput(filename);
            value = fisLocal.available();
        } catch (IOException e) {
            Log.e("Read File error :", e.getLocalizedMessage());
        }finally {
            try {
                fisLocal.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
}
