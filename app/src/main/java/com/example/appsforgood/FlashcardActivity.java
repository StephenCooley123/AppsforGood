package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FlashcardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        ImageView imageView;
        ImageView imageView1;
        ImageView imageView2;


        imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setImageResource(R.drawable.shark);
        imageView1 = (ImageView) findViewById(R.id.imageView2);
        imageView1.setImageResource(R.drawable.cow);
        imageView2 = (ImageView) findViewById(R.id.imageView3);
        imageView2.setImageResource(R.drawable.shark);
    }
    public void changePage(View v){
        Intent intent = getIntent();
        boolean state = intent.getBooleanExtra("condition", true);

        String word= "Placeholder";

        if(state){
            Intent intent1 = new Intent(this, FlashcardActivity.class);
            intent1.putExtra("condition",true);
            startActivity(intent1);
        }

        else{
            Intent intent2 = new Intent(this, WordPage.class);
            intent2.putExtra("word",word);
            startActivity(intent2);
        }

    }
}