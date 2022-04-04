package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("hello");
        System.out.println("hi");
        System.out.println("Hello this is a test!");
        //if you see this the pull thingy worked
        System.out.println("hello this is another test");
        System.out.println("Stephen Test");
    }
}