package com.example.appsforgood;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public static final char csvSeparatorChar = ',';
    public static final char listSeparatorChar = '|';

    private static int EXTERNAL_STORAGE_PERMISSION_CODE = 23;


    //reads a file and returns it line by line
    public static ArrayList<String> readFile(String filePath, Context c){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
            ArrayList<String> lines = new ArrayList<String>();
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }

            return lines;
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        }
        return null;


    }

    //saves the lines to a file at filepath. Always throw in "this" for activity.
    public static void savePublicly(ArrayList<String> lines, String filePath, Activity activity) {
        // Requesting Permission to access External Storage
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);


        // Storing the data in file with name as geeksData.txt
        File file = new File(filePath);

        String entireFile = "";
        for (String s : lines) {
            entireFile += s;
        }

        writeTextData(file, entireFile);
    }

    //helper method for savePublicly
    public static void writeFile(ArrayList<String> lines, String filePath) {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                f.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            for (String l : lines) {
                bw.write(l);
                System.out.print(l);
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            // System.out.println("THIS WAS MY ERROR");

        }
    }

    //more helper methods to allow file writing
    private static void writeTextData(File file, String data) {
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {
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
