package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.*;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FlashcardActivity extends AppCompatActivity {

    TextToSpeech speaker;
    String word;
    int correctButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);


        Word correctWord = MainActivity.getWords().get((int) (Math.random() * MainActivity.getWords().size()));

        ArrayList<LoadedImage> Images = correctWord.getImages();
        String correctString = correctWord.getWord();
        ArrayList<String> tags = correctWord.getTags();



                speaker=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    speaker.setLanguage(Locale.US);
                }
            }
        });
        correctButton = (int) (Math.random() * 3);// determines the image view with the correct picture
        ImageView imageView = null;
        ImageView imageView1 = null;
        ImageView imageView2 = null;
        Button button = null;
        Button button1 = null;
        Button button2 = null;

        switch (correctButton){
            case 0:
                imageView = (ImageView) findViewById(R.id.imageView1);
                button = (Button) findViewById(R.id.button6);
                imageView1 = (ImageView) findViewById(R.id.imageView2);
                button1 = (Button) findViewById(R.id.button4);
                imageView2 = (ImageView) findViewById(R.id.imageView3);
                button2 = (Button) findViewById(R.id.button5);
                break;
            case 1:
                imageView = (ImageView) findViewById(R.id.imageView2);
                button = (Button) findViewById(R.id.button4);
                imageView1 = (ImageView) findViewById(R.id.imageView1);
                button1 = (Button) findViewById(R.id.button6);
                imageView2 = (ImageView) findViewById(R.id.imageView3);
                button2 = (Button) findViewById(R.id.button5);
                break;
            case 2:
                imageView = (ImageView) findViewById(R.id.imageView3);
                button = (Button) findViewById(R.id.button5);
                imageView1 = (ImageView) findViewById(R.id.imageView1);
                button1 = (Button) findViewById(R.id.button6);
                imageView2 = (ImageView) findViewById(R.id.imageView2);
                button2 = (Button) findViewById(R.id.button4);
                break;
            default:
                break;
        }

        //Sets the the correct imageView to a random bitmap from the list of images for the word.
        imageView.setImageBitmap(correctWord.RandomImage().getImage());
        button.setText(correctWord.getWord());



        ArrayList<String> selectedTags = new ArrayList<String>();

        for(int i = 0; i < 2; i++) {
            if(tags.size() > i) {
                selectedTags.add(tags.get((int) Math.random() * tags.size()));
            } else {
                selectedTags.add("");
            }
        }

        String randTag1 = selectedTags.remove(0);

        String randTag2 = selectedTags.remove(0);

        List<Word> words = MainActivity.getWords();

        Collections.shuffle(words);

        // Finish getting tags
        boolean foundTag = false;
        for(Word s: words){
            if(s.containsTag(randTag1)){
                button1.setText(s.getWord());
                imageView1.setImageBitmap(s.RandomImage().getImage());
                foundTag = true;
                break;
            }
        }
        if(!foundTag) {
            Word s = words.get(0);
            button1.setText(s.getWord());
            imageView1.setImageBitmap(s.RandomImage().getImage());
        }

        foundTag = false;
        for(Word s: words){
            if(s.containsTag(randTag2)){

                button2.setText(s.getWord());
                imageView2.setImageBitmap(s.RandomImage().getImage());
                foundTag = true;
                break;
            }
        }
        if(!foundTag) {
            Word s = words.get(1);
            button2.setText(s.getWord());
            imageView2.setImageBitmap(s.RandomImage().getImage());
        }
    }

    public void topButton(View v) {
        if(correctButton == 0) {
            changePage(v);
        }
    }
    public void midButton(View v) {
        if(correctButton == 1) {
            changePage(v);
        }
    }
    public void bottomButton(View v) {
        if(correctButton == 2) {
            changePage(v);
        }
    }
    public void changePage(View v){
        Intent intent = getIntent();
        boolean state = intent.getBooleanExtra("condition", true);

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