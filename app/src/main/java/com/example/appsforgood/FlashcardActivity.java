package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FlashcardActivity extends AppCompatActivity {

    List<Word> words = MainActivity.getWords();

    //Word wordTop;
    //Word wordMid;
    //Word wordBottom;

    final boolean ALLOW_ALL_ANSWERS_FOR_DEBUG = false;

    TextToSpeech speaker;

    String word;
    int correctButton;// determines the image view with the correct picture
    long startTime;

    Interaction interaction = new Interaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("stephen:", "oncreate");
        correctButton = (int) (Math.random() * 3);
        //says the correct word out loud

        speaker = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    speaker.setLanguage(Locale.UK);
                    speaker.speak(word, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        startTime = System.currentTimeMillis();
        setContentView(R.layout.activity_flashcard);


        Word correctWord = MainActivity.getWords().get((int) (Math.random() * MainActivity.getWords().size()));
        word = correctWord.getWord();
        System.out.println("CorrectWord: " + correctWord.toString());
        ArrayList<LoadedImage> Images = correctWord.getImages();
        String correctString = correctWord.getWord();


        Word[] wordArray = new Word[3];
        wordArray[correctButton] = correctWord;
        for (int i = 0; i < wordArray.length; i++) {
            if (i != correctButton) {
                wordArray[i] = getAnotherWord(correctWord, wordArray);
            }
        }


        ImageView imageView0 = (ImageView) findViewById(R.id.imageView1);
        ImageView imageView1 = (ImageView) findViewById(R.id.imageView2);
        ;
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView3);
        ;
        Button button0 = (Button) findViewById(R.id.button1);
        Button button1 = (Button) findViewById(R.id.button2);
        Button button2 = (Button) findViewById(R.id.button3);

        //Sets the the correct imageView to a random bitmap from the list of images for the word.
        imageView0.setImageBitmap(wordArray[0].RandomImage().getImage());
        imageView0.setImageBitmap(wordArray[1].RandomImage().getImage());
        imageView0.setImageBitmap(wordArray[2].RandomImage().getImage());
        button0.setText(wordArray[0].getWord());
        button1.setText(wordArray[1].getWord());
        button2.setText(wordArray[2].getWord());
        // word0 = correctWord;
        //Log.d("stephen:", "Right Before Speak");


        speak("In on create");
    }

    private Word getAnotherWord(Word correctWord, Word[] otherWords) {
        ArrayList<String> tags = correctWord.getTags();
        ArrayList<String> selectedTags = new ArrayList<String>();

        for (int i = 0; i < 2; i++) {
            if (tags.size() > i) {
                selectedTags.add(tags.get((int) Math.random() * tags.size()));
            } else {
                selectedTags.add("");
            }
        }


        Collections.shuffle(words);
        Collections.shuffle(tags);


        // Finish getting tags
        boolean foundTag = false;
        for (String t : tags) {
            for (Word s : words) {

                if (s.containsTag(t)) {
                    return s;

                }
            }
        }
        return words.get(0);
    }

    private boolean containsArray(Object[] objects, Object obj) {
        for (Object o : objects) {
            if (o == obj) {
                return true;
            }
        }
        return false;
    }

    public void topButton(View v) {
        Log.d("stephen:", "In Top Button");
        //speak("top button");

        //speaker.stop();
        //interaction.addWord(word0);
        //System.out.println("Added " + word0.toString() + " to interaction");

        if (correctButton == 0 || ALLOW_ALL_ANSWERS_FOR_DEBUG) {
            interaction.setTime(System.currentTimeMillis() - startTime);
            changePage(v);
        }
    }

    public void midButton(View v) {
        Log.d("stephen:", "In Mid Button");
        //speak("mid button");

        if (correctButton == 1 || ALLOW_ALL_ANSWERS_FOR_DEBUG) {
            interaction.setTime(System.currentTimeMillis() - startTime);
            changePage(v);
        }
    }

    public void bottomButton(View v) {
        Log.d("stephen:", "In Bottom Button");
        //speak("bottom button");

        if (correctButton == 2 || ALLOW_ALL_ANSWERS_FOR_DEBUG) {
            interaction.setTime(System.currentTimeMillis() - startTime);
            changePage(v);
        }
    }


    public void changePage(View v) {
        Log.d("stephen:", "changepage");
        Intent intent = getIntent();
        boolean state = intent.getBooleanExtra("condition", true);

        if (state) {
            Intent intent1 = new Intent(this, FlashcardActivity.class);
            intent1.putExtra("condition", true);
            startActivity(intent1);
        } else {
            Intent intent2 = new Intent(this, WordPage.class);
            intent2.putExtra("word", word);
            startActivity(intent2);
        }

    }

    private void speak(String text) {
        System.out.println("Should have spoken");
        speaker.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}