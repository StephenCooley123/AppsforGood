package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    //TO SAVE CHANGES TO MASTER
    // Commit to save local
    //Push to save to your cloud branch
    //in menu in bottom right: master -> update
    //master -> merge into current
    //VCS -> Git -> Create Pull Request to send to master
    //TO MERGE MASTER INTO CURRENT:
    // Bottom Right Menu -> master -> update
    //          "       -> master -> merge into current
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("hello");
        System.out.println("hi");
        System.out.println("Hello this is a test!");
        //if you see this the pull thingy worked
        //Does this work
        System.out.println("hello this is another test");
        System.out.println("Stephen Test");
        System.out.println("Stephen Test 2");
    }
}