package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.*;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;

public class FlashcardActivity extends AppCompatActivity {

    TextToSpeech speaker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        ImageView imageView;
        ImageView imageView1;
        ImageView imageView2;


        imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setImageResource(R.drawable.dog);
        imageView1 = (ImageView) findViewById(R.id.imageView2);
        imageView1.setImageResource(R.drawable.cow);
        imageView2 = (ImageView) findViewById(R.id.imageView3);
        imageView2.setImageResource(R.drawable.shark);

        speaker=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    speaker.setLanguage(Locale.US);
                }
            }
        });
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