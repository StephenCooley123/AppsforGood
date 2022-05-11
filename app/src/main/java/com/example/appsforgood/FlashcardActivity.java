package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.*;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FlashcardActivity extends AppCompatActivity {

    List<Word> words = MainActivity.getWords();

    //Word wordTop;
    //Word wordMid;
    //Word wordBottom;

    final boolean ALLOW_ALL_ANSWERS_FOR_DEBUG = false;

    TextToSpeech speaker;
    Word correctWord;
    String word;
    int correctButton;// determines the image view with the correct picture
    long startTime;

    ArrayList<String> touchedWords = new ArrayList<String>();

    ArrayList<Word> wordList = new ArrayList<Word>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("stephen:", "oncreate");
        correctButton = (int) (Math.random() * 3);

        //says the correct word out loud

        correctWord = MainActivity.getWords().get((int) (Math.random() * MainActivity.getWords().size()));
        word = correctWord.getWord();
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





        //System.out.println("Initializing Word List. Correct word is: " + correctWord);
        for (int i = 0; i < 2; i++) {
            wordList.add(getAnotherWord(correctWord, wordList));
        }
        wordList.add(correctButton, correctWord);
        System.out.println("CORRECT WORD: " + correctWord.toString() + " LOCATION: " + correctButton);

        TextView textView0 = (TextView) findViewById(R.id.textView6);
        textView0.setText(correctWord.toString());
        TextView textView1 = (TextView) findViewById(R.id.textView5);
        textView1.setText(correctWord.toString());

        ImageView imageView0 = (ImageView) findViewById(R.id.imageView1);
        ImageView imageView1 = (ImageView) findViewById(R.id.imageView2);
        ;
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView3);
        ;

        //Sets the the correct imageView to a random bitmap from the list of images for the word.
        imageView0.setImageBitmap(wordList.get(0).RandomImage().getImage());
        imageView1.setImageBitmap(wordList.get(1).RandomImage().getImage());
        imageView2.setImageBitmap(wordList.get(2).RandomImage().getImage());

                // word0 = correctWord;
        //Log.d("stephen:", "Right Before Speak");
    }

    private Word getAnotherWord(Word correctWord, ArrayList<Word> selectedWords) {
       // System.out.print("GETTING ANOTHER WORD. CORRECT IS: " + correctWord.toString());
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

        for (String t : tags) {
            for (Word s : words) {

                if (s.containsTag(t) && !selectedWords.contains(s) && !(s == correctWord)) {
                    //System.out.println("SELECTED: " + s.toString());

                    return s;

                }
            }
        }
        Word toReturn = words.get(0);
        while(selectedWords.contains(toReturn) || toReturn == correctWord) {
            Collections.shuffle(words);
            Collections.shuffle(tags);
            toReturn = words.get(0);
        }
        //System.out.println("SELECTED: " + toReturn.toString());
        return toReturn;
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
        touchedWords.add(wordList.get(0).toString());
        Log.d("stephen:", "In Top Button");
        //speak("top button");

        //speaker.stop();
        //interaction.addWord(word0);
        //System.out.println("Added " + word0.toString() + " to interaction");

        if (correctButton == 0 || ALLOW_ALL_ANSWERS_FOR_DEBUG) {

            changePage(v);
        }
    }

    public void midButton(View v) {
        touchedWords.add(wordList.get(1).toString());
        Log.d("stephen:", "In Mid Button");
        //speak("mid button");

        if (correctButton == 1 || ALLOW_ALL_ANSWERS_FOR_DEBUG) {

            changePage(v);
        }
    }

    public void bottomButton(View v) {
        touchedWords.add(wordList.get(2).toString());

        Log.d("stephen:", "In Bottom Button");
        //speak("bottom button");

        if (correctButton == 2 || ALLOW_ALL_ANSWERS_FOR_DEBUG) {

            changePage(v);
        }
    }


    public void changePage(View v) {

        Log.d("stephen:", "changepage");
        correctWord.addInteraction(new Interaction(System.currentTimeMillis() - startTime, touchedWords));
        writeData();
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

    private void writeData() {
        System.out.println("WRITING DATA IN FLASHCARDACTIVITY");
        File folder = new File(getFilesDir()
                + MainActivity.appFolder);
        //System.out.println("FILE: " + folder.toString());
        //System.out.println("IN WRITE DATA METHOD");


        if (!folder.exists()) {
            folder.mkdir();
            //System.out.println("Made the directory");
            System.out.println(folder.toString());
        }
        System.out.println("FOLDER: " + folder.toString());

        final String wordsFilePath = folder.toString() + "/" + "words.csv";
        final String interactionsFilePath = folder.toString() + "/" + "interactions.csv";


        generateInteractionKeys();

        ArrayList<String> wordsLines = new ArrayList<String>();

        wordsLines.add("Word" + CSVParser.csvSeparatorChar + "Images" + CSVParser.csvSeparatorChar + "InteractionKeys" + CSVParser.csvSeparatorChar + "Tags" + '\n');

        for (Word w : words) {
            wordsLines.add(writeWord(w));
        }

        ArrayList<String> interactionsLines = new ArrayList<String>();
        interactionsLines.add("Key" + CSVParser.csvSeparatorChar + "Time" + CSVParser.csvSeparatorChar + "SelectedWords" + '\n');
        for (Word w : words) {
            for (Interaction i : w.getInteractions()) {
                interactionsLines.add(i.toString() + "\n");
            }
        }

        CSVParser.savePublicly(wordsLines, wordsFilePath, this);
        CSVParser.savePublicly(interactionsLines, interactionsFilePath, this);

        //CSVParser.writeFile(interactionsLines, interactionsFilePath);


    }

    private String writeInteraction(Interaction i) {
        return i.getKey();
    }

    private void generateInteractionKeys() {
        int n = 0;
        for (Word w : words) {
            for (Interaction i : w.getInteractions()) {
                i.setKey(n);
                n++;
            }
        }
    }

    /**
     * Returns the String representing a CSV Line for a Word object
     *
     * @param
     * @return The String representing a CSV Line for a Word object
     */
    private String writeWord(Word w) {
        String s = w.toString() + CSVParser.csvSeparatorChar;

        //write images
        if (w.getImages().size() > 0) {
            for (LoadedImage l : w.getImages()) {
                s = s + l.toString() + CSVParser.listSeparatorChar;
            }
            s = s.substring(0, s.length() - 1);
        }
        s = s + CSVParser.csvSeparatorChar;

        //write interaction keys
        if (w.getInteractions().size() > 0) {
            for (Interaction i : w.getInteractions()) {
                s = s + i.getKey() + CSVParser.listSeparatorChar;
            }
            s = s.substring(0, s.length() - 1);
        }
        s = s + CSVParser.csvSeparatorChar;

        if (w.getTags().size() > 0) {
            for (String tag : w.getTags()) {
                s = s + tag + CSVParser.listSeparatorChar;
                System.out.println(tag);
            }
            s = s.substring(0, s.length() - 1);
        }
        s = s + '\n';

        return s;
    }


}