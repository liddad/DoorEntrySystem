package com.example.lidda.strathdoorsystem;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by lidda on 14/03/2016.
 */
public class FileSaver {

    public static String FILENAME = "UIDfile";

    public static boolean saveLogin(Context c, String UID){
        FileOutputStream outputStream;

        try {
            outputStream = c.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            outputStream.write(UID.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String loadLogin(Context c){
        FileInputStream inputStream;

        try{
            inputStream = c.openFileInput(FILENAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String UIDString = bufferedReader.readLine();
            inputStream.close();
            return UIDString;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static boolean delete(Context c){
        return c.deleteFile(FILENAME);
    }
}
