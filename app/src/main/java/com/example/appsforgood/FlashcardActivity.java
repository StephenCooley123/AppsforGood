package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FlashcardActivity extends AppCompatActivity {

    String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        ImageView imageView = null;

        Word w = MainActivity.getWords().get((int) (Math.random() * MainActivity.getWords().size()));

        ArrayList<LoadedImage> Images = w.getImages();
        String word = w.getWord();

        int CorrectRand = (int) (Math.random()*3);// determines the image view with the correct picture

        ImageView imageView1 = null;
        ImageView imageView2 = null;

        switch (CorrectRand){
            case 0:
                imageView = (ImageView) findViewById(R.id.imageView1);
                imageView1 = (ImageView) findViewById(R.id.imageView2);
                imageView2 = (ImageView) findViewById(R.id.imageView3);
            case 1:
                imageView = (ImageView) findViewById(R.id.imageView2);
                imageView1 = (ImageView) findViewById(R.id.imageView1);
                imageView2 = (ImageView) findViewById(R.id.imageView3);
            case 2:
                imageView = (ImageView) findViewById(R.id.imageView3);
                imageView1 = (ImageView) findViewById(R.id.imageView1);
                imageView2 = (ImageView) findViewById(R.id.imageView2);
            default:
        }

        //Sets the the correct imageView to a random bitmap from the list of images for the word.
        imageView.setImageBitmap(w.RandomImage().getImage());

        ArrayList<String> tags = w.getTags();

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
                imageView1.setImageBitmap(s.RandomImage().getImage());
                foundTag = true;
                break;
            }
        }
        if(!foundTag) {
            imageView1.setImageBitmap(words.get(0).RandomImage().getImage());
        }

        foundTag = false;
        for(Word s: words){
            if(s.containsTag(randTag2)){
                imageView2.setImageBitmap(s.RandomImage().getImage());
                foundTag = true;
                break;
            }
        }
        if(!foundTag) {
            imageView2.setImageBitmap(words.get(1).RandomImage().getImage());
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