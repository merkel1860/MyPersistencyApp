package com.example.mypersistencyapp.model.utils;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.mypersistencyapp.model.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FilePersistence {

    private FilePersistence(){}
    public static boolean writeToFile(String filename,
                                      Context context, User data){
        FileOutputStream fos = null;
        boolean isStored = false;
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(data.fullname().toString().getBytes());
            isStored = true;
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
                    Log.e("Clearing stream error :",e.getLocalizedMessage());
                }
            }
        }
        return isStored;
    }
}
