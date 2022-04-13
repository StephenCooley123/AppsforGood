package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

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
        setContentView(R.layout.activity_flashcard);
        ImageView imageView;
        ImageView imageView1;
        ImageView imageView2;


        imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setImageResource(R.drawable.cow);
        imageView1 = (ImageView) findViewById(R.id.imageView2);
        imageView1.setImageResource(R.drawable.cow);
        imageView2 = (ImageView) findViewById(R.id.imageView3);
        imageView2.setImageResource(R.drawable.cow);

        }



    }

