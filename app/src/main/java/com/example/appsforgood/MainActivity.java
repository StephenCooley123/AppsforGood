package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.view.View;
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
    //to merge changes from someone else, fetch first
    static List<Word> words = new ArrayList<Word>();
    public static final String appFolder = "/VocabliData";
    public static final String assetsReferenceKey = "/assets/";

    @Override
    protected void onStart() {
        super.onStart();
        installImagesIfNecessary();
        generateTestWord(5);
        writeData();
        readWords();
    }

    private void installImagesIfNecessary() {
        File folder = new File(getFilesDir()
                + "/Images");

        if (!folder.exists()) {
            folder.mkdir();
        }
        try {

            Bitmap b = BitmapFactory.decodeStream(getAssets().open("dog.jpg"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //readWords();

        //generate 5 random words just to test the file system
        //to access device files, go to view > tool windows > device file explorer
        //folder with data is data > user > 0 > com.example.appsforgood > files > VocabliData


    }

    /**
     * Interacts with the database and reads the words on startup
     */
    private void readWords() {
        File folder = new File(getFilesDir()
                + appFolder);

        final String wordsFilePath = folder.toString() + "/" + "words.csv";
        final String interactionsFilePath = folder.toString() + "/" + "interactions.csv";

        if (!folder.exists()) {
            folder.mkdir();
        }
        ArrayList<String> unparsedWords = CSVParser.readFile(wordsFilePath, this);
        unparsedWords.remove(0);
        for (String s : unparsedWords) {
            System.out.println("WORDS LINE: " + s);
            parseWord(s);
        }
    }

    private void parseWord(String s) {
        String w = s.substring(0, s.indexOf(","));
        System.out.println("WORD: " + w);
        s = s.substring(s.indexOf(",") + 1);
        String images = s.substring(0, s.indexOf(","));
        System.out.println("IMAGES: " + images);
        s = s.substring(s.indexOf(",") + 1);
        String interactionKeys = s.substring(0, s.indexOf(","));
        System.out.println("Interaction Keys: " + interactionKeys);
        s = s.substring(s.indexOf(",") + 1);
        String tags = s;
        System.out.println("TAGS: " + tags);
        Word word = new Word(w);
        ArrayList<LoadedImage> wordImages = parseImages(images);
        word.setImages(wordImages);
    }

    private ArrayList<LoadedImage> parseImages(String images) {
        ArrayList<LoadedImage> loadedImages = new ArrayList<LoadedImage>();
        while (images.contains("|")) {
            String reference = images.substring(0, images.indexOf("|"));
            images = images.substring(images.indexOf("|") + 1);

            if (reference.contains(assetsReferenceKey)) {
                reference = reference.substring(assetsReferenceKey.length());
                loadedImages.add(new LoadedImage(getImageAsset(reference), reference));
            } else {
                loadedImages.add(new LoadedImage(BitmapFactory.decodeFile(reference), reference));
            }


        }
        if (!images.equals("")) {

            String reference = images;
            if (reference.contains(assetsReferenceKey)) {
                reference = reference.substring(assetsReferenceKey.length());
                loadedImages.add(new LoadedImage(getImageAsset(reference), reference));
            } else {
                loadedImages.add(new LoadedImage(BitmapFactory.decodeFile(reference), reference));
            }

        }
        return loadedImages;
    }

    public static List<Word> getWords() {
        return words;
    }

    private void writeData() {
        File folder = new File(getFilesDir()
                + appFolder);
        //System.out.println("FILE: " + folder.toString());
        //System.out.println("IN WRITE DATA METHOD");

        boolean var = false;
        if (folder.exists()) {
            System.out.println("Folder Exists, I just can't see it");
        }
        if (!folder.exists()) {
            var = folder.mkdir();
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
        interactionsLines.add("Key" + CSVParser.csvSeparatorChar + "Timeout" + CSVParser.csvSeparatorChar + "Time" + CSVParser.csvSeparatorChar + "TapsX" + CSVParser.csvSeparatorChar + "TapsY" + CSVParser.csvSeparatorChar + "Answers" + CSVParser.csvSeparatorChar + "CorrectAnswer" + CSVParser.csvSeparatorChar + "SelectedAnswer" + '\n');
        for (Word w : words) {
            for (Interaction i : w.getInteractions()) {
                interactionsLines.add(writeInteraction(i));
            }
        }

        CSVParser.savePublicly(wordsLines, wordsFilePath, this);
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
        if(w.getImages().size() > 0) {
            for (LoadedImage l : w.getImages()) {
                s = s + l.toString() + CSVParser.listSeparatorChar;
            }
            s = s.substring(0, s.length() - 1);
        }
        s = s + CSVParser.csvSeparatorChar;

        //write interaction keys
        if(w.getInteractions().size() > 0) {
            for (Interaction i : w.getInteractions()) {
                s = s + i.getKey() + CSVParser.listSeparatorChar;
            }
            s = s.substring(0, s.length() - 1);
        }
        s = s + CSVParser.csvSeparatorChar;

        if(w.getTags().size() > 0) {
            for (String tag : w.getTags()) {
                s = s + tag + CSVParser.listSeparatorChar;
                System.out.println(tag);
            }
            s = s.substring(0, s.length() - 1);
        }
        s = s + '\n';

        return s;
    }

    private void generateTestWord(int numWords) {
        for (int j = 0; j < numWords; j++) {
            String s = "";
            for (int i = 0; i < 5; i++) {
                s = s + (char) (((int) (Math.random() * 26)) + 'a');
            }
            Word w = new Word(s);
            ArrayList<LoadedImage> temp = new ArrayList<LoadedImage>();
            temp.add(new LoadedImage(getImageAsset("dog.jpg"), assetsReferenceKey + "dog.jpg"));
            temp.add(new LoadedImage(getImageAsset("cow.jpg"), assetsReferenceKey + "cow.jpg"));
            w.setImages(temp);
            w.addTag("animal");
            w.addTag("tag2");
            w.addTag("tag3");
            words.add(w);
        }
    }


    public void Description(View v) {
        Intent intent = new Intent(this, FlashcardActivity.class);
        intent.putExtra("condition", false);
        startActivity(intent);
    }

    public void nextWord(View v){
        Intent intent = new Intent(this,FlashcardActivity.class);
        intent.putExtra("condition",true);
        startActivity(intent);
    }

    public void Parental(View v){
        Intent intent = new Intent(this,Settings.class);
        startActivity(intent);
        }

    private Bitmap getImageAsset(String name) {

        try {
            System.out.println("Getting Image Asset");
            return BitmapFactory.decodeStream(getAssets().open(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    }

