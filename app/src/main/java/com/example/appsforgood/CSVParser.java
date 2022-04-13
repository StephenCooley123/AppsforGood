package com.example.appsforgood;

import android.Manifest;
import android.app.Activity;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public static final char csvSeparatorChar = ',';
    public static final char listSeparatorChar = '|';

    private static int EXTERNAL_STORAGE_PERMISSION_CODE = 23;


    public static String wordsToList(List<Object> objects) {
        if (objects.size() == 0) {
            return "";
        }
        String str = "";
        for (Object o : objects) {
            str = str + o.toString() + listSeparatorChar;
        }
        str = str.substring(0, str.length() - 1);
        str = str + csvSeparatorChar;
        return str;

    }

    public static void savePublicly(ArrayList<String> lines, String filePath, Activity activity) {
        // Requesting Permission to access External Storage
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);
        ActionBar.Tab editText;

        // getExternalStoragePublicDirectory() represents root of external storage, we are using DOWNLOADS
        // We can use following directories: MUSIC, PODCASTS, ALARMS, RINGTONES, NOTIFICATIONS, PICTURES, MOVIES
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // Storing the data in file with name as geeksData.txt
        File file = new File(filePath);
        String entireFile = "";
        for(String s : lines) {
            entireFile += lines;
        }

        writeTextData(file, entireFile);
    }
    public static void writeFile(ArrayList<String> lines, String filePath) {
        try {
            File f = new File(filePath);
            if(!f.exists()) {
                f.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            for(String l : lines) {
                bw.write(l);
                System.out.print(l);
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("THIS WAS MY ERROR");

        }
    }

    private static void writeTextData(File file, String data) {
        FileOutputStream fileOutputStream = null;
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            //Toast.makeText(this, "Done" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
