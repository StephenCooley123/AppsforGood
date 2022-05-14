package com.example.appsforgood;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.speech.tts.TextToSpeech;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import android.view.View;
import android.widget.ImageView;

import org.w3c.dom.Text;

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

    //*******STUFF TO WATCH FOR IN YOUR TESTING:*******
    //If multiple of the same word ever appear, set both FORCE_FILESYSTEM_REBUILD and FLUSH_INTERACTIONS to true,
    // run it, complete one flashcard, exit, and then relaunch with both set to false.
    // To salvage after this, set FORCE_FILESYSTEM_REBUILD and FLUSH_INTERACTIONS to true and go through one flashcard
    //Then set them both to false and reinstall
    // Some functionality with adding words may be limited
    // The text to speech runs early in the guided mode. We are working on a fix.

    //Garyth is currently working on adding/deleting words and has changed many UI elements to be color and text consistent.
    //Donny is working on playing animations after each correct answer.
    //The main criteria for our app is complete, but these are important nice-to-haves as well.
    //more words will be added in to be packaged with the app by default as well by editing DefaultWords.csv and adding the associated images to the assets folder.
    static List<Word> words = new ArrayList<Word>();

    //this forces a rebuild of the file system
    final boolean FORCE_FILESYSTEM_REBUILD = false;

    //this flushes the existing interactions.
    final boolean FLUSH_INTERACTIONS = false;

    //keys for locations of files and folders
    public static final String appFolder = "/VocabliData";
    public static final String imageFolder = "/Images";
    public static final String assetsReferenceKey = "/assets/";

    String interactionsFilePath;
    String wordsFilePath;


    @Override
    protected void onStart() {
        super.onStart();
        readWords();
        deletefromMainWords(SettingsModel.prevdeletingwords);
        //writeData();
        int num = 0;
        for (Word w : words) {
            num += w.getInteractions().size();
        }
        System.out.println("STARTING NUM OF INTERACTIONS: " + num);
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
    public static void addtoMainWords(Word word){
        words.add(word);
    }

    public void deletefromMainWords(ArrayList<Word> wordobjects){
        for(Word toDelete : wordobjects) {
            words.remove(toDelete);
        }
        writeData();
    }

    /**
     * Interacts with the file system and reads the words on startup
     */
    private void readWords() {
        try {

            File folder = new File(getFilesDir()
                    + appFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }
            wordsFilePath = folder.toString() + "/" + "words.csv";
            System.out.println("Words File Path: " + wordsFilePath);
            if (!new File(wordsFilePath).exists() || FORCE_FILESYSTEM_REBUILD) {
                File wordsFile = new File(wordsFilePath);
                BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("DefaultWords.csv")));
                String line = reader.readLine();
                //System.out.println("First line incoming");
                System.out.println("FIRST LINE:" + line);
                BufferedWriter wr = new BufferedWriter(new FileWriter(wordsFile));
                while (line != null) {
                    wr.write(line + "\n");
                    System.out.println("FILE LINE: " + line);
                    line = reader.readLine();

                }
                reader.close();
                wr.flush();
                wr.close();
            }

            interactionsFilePath = folder.toString() + "/" + "interactions.csv";
            if (!new File(interactionsFilePath).exists() || FLUSH_INTERACTIONS) {
                File interactionsFile = new File(interactionsFilePath);
                if(interactionsFile.exists()) {
                    interactionsFile.delete();
                }
                interactionsFile.createNewFile();
            }

            File imagesFolder = new File(getFilesDir() + appFolder + imageFolder);
            if (!imagesFolder.exists()) {
                imagesFolder.mkdir();
            }

            //System.out.println("Parsing words");
            ArrayList<String> unparsedWords = CSVParser.readFile(wordsFilePath, this);
            unparsedWords.remove(0);

            for (String s : unparsedWords) {
                //System.out.println("WORDS LINE: " + s);
                parseWord(s);
            }

            //System.out.println("Wrote Directories");
        } catch (IOException e) {
            //System.out.println("FAILED TO WRITE DIRECTORIES");
            e.printStackTrace();
        }
    }

    //parses a line from the CSV into a word object and adds it to the list
    private void parseWord(String s) {
        System.out.println("WORD LINE: " + s);
        String w = s.substring(0, s.indexOf(","));
        System.out.println("WORD: " + w);
        s = s.substring(s.indexOf(",") + 1);
        String images = s.substring(0, s.indexOf(","));
        System.out.println("IMAGES: " + images);
        s = s.substring(s.indexOf(",") + 1);
        String interactionKeys = s.substring(0, s.indexOf(","));
        System.out.println("Interaction Keys: " + interactionKeys);
        s = s.substring(s.indexOf(",") + 1);
        String tags = s.substring(0, s.indexOf(","));;
        System.out.println("TAGS: " + tags);
        s = s.substring(s.indexOf(",") + 1);
        String questions = s;
        System.out.println("QUESTIONS: " + questions);


        Word word = new Word(w);
        ArrayList<LoadedImage> wordImages = parseImages(images);
        word.setImages(wordImages);
        word.setInteractions(parseInteractions(interactionKeys));
        word.setTags(parseTags(tags));
        word.setQuestions(parseQuestions(questions));

        boolean addWord = true;
        for(Word checkedWord : words) {
            if(checkedWord.toString().equals(word.toString())) {
                addWord = false;
            }
        }
        if(addWord) {
            words.add(word);
        }
    }

    //parses the pipe-separated list of questions into an arraylist of strings.
    private ArrayList<String> parseQuestions(String questionsLine) {
        ArrayList<String> questions = new ArrayList<String>();
        while (questionsLine.contains(((Character) CSVParser.listSeparatorChar).toString())) {
            String question = questionsLine.substring(0, questionsLine.indexOf(CSVParser.listSeparatorChar));
            questionsLine = questionsLine.substring(questionsLine.indexOf(CSVParser.listSeparatorChar) + 1);
            questions.add(question);


        }
        if (!questionsLine.equals("")) {
            String question = questionsLine;
            questions.add(question);
        }

        return questions;
    }

    //parses the interactions from the string of a bunch of interactions
    private ArrayList<Interaction> parseInteractions(String interactionKeys) {

        //parses which keys the word has
        ArrayList<Integer> keysToMatch = new ArrayList<Integer>();
        while (interactionKeys.contains(((Character) CSVParser.listSeparatorChar).toString())) {
            String key = interactionKeys.substring(0, interactionKeys.indexOf(CSVParser.listSeparatorChar));
            interactionKeys = interactionKeys.substring(interactionKeys.indexOf(CSVParser.listSeparatorChar) + 1);
            keysToMatch.add(Integer.parseInt(key));


        }
        if (!interactionKeys.equals("")) {
            String key = interactionKeys;
            keysToMatch.add(Integer.parseInt(key));
        }

        ArrayList<Interaction> interactions = new ArrayList<Interaction>();
        ArrayList<String> interactionsLines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(interactionsFilePath));
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                if (line != null) {
                    interactionsLines.add(line);
                }
            }
            reader.close();

            //makes a list of keys from the string where they are all in a list
            ArrayList<String> keys = new ArrayList<String>();
            while (interactionKeys.contains(((Character) CSVParser.csvSeparatorChar).toString())) {
                String key = interactionKeys.substring(0, interactionKeys.indexOf(CSVParser.csvSeparatorChar));
                interactionKeys = interactionKeys.substring(interactionKeys.indexOf(CSVParser.csvSeparatorChar) + 1);
                keys.add(key);


            }
            if (!interactionKeys.equals("")) {
                String tag = interactionKeys;
                keys.add(tag);
            }

            System.out.println("INTERACTIONS LINES SIZE: " + interactionsLines.size());

            //checks whether a key matches the list of keys
            for (String interactionLine : interactionsLines) {
                //if the key matches, it parses the line into an interaction

                Interaction i;
                String key = interactionLine.substring(0, interactionLine.indexOf(CSVParser.csvSeparatorChar));
                int keyInt = Integer.parseInt(key);
                //does the checking to make sure only the interactions for this word are parsed
                if(keysToMatch.contains(keyInt)) {
                    interactionLine = interactionLine.substring(interactionLine.indexOf(CSVParser.csvSeparatorChar) + 1);
                    Long time = Long.parseLong(interactionLine.substring(0, interactionLine.indexOf(CSVParser.csvSeparatorChar)));
                    interactionLine = interactionLine.substring(interactionLine.indexOf(CSVParser.csvSeparatorChar) + 1);

                    //parses it into individual button presses
                    ArrayList<String> interactionsList = new ArrayList<String>();
                    while (interactionLine.contains(((Character) CSVParser.listSeparatorChar).toString())) {
                        String interaction = interactionLine.substring(0, interactionLine.indexOf(CSVParser.listSeparatorChar));
                        interactionLine = interactionLine.substring(interactionLine.indexOf(CSVParser.listSeparatorChar) + 1);
                        interactionsList.add(interaction);


                    }
                    if (!interactionLine.equals("")) {
                        String interaction = interactionLine;
                        interactionsList.add(interaction);
                    }

                    i = new Interaction(time, interactionsList);
                    interactions.add(i);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("PARSED " + interactions.size() + " INTERACTIONS");
        return interactions;

    }

    //parses the tags from a bunch of tags in a string
    private ArrayList<String> parseTags(String tags) {
        ArrayList<String> tagsList = new ArrayList<String>();
        while (tags.contains(((Character) CSVParser.csvSeparatorChar).toString())) {
            String tag = tags.substring(0, tags.indexOf(CSVParser.csvSeparatorChar));
            tags = tags.substring(tags.indexOf(CSVParser.csvSeparatorChar) + 1);
            tagsList.add(tag);


        }
        if (!tags.equals("")) {
            String tag = tags;
            tagsList.add(tag);
        }
        return tagsList;
    }

    //parses the images from a bunch of images in a string and returns some LoadedImage objects (essentially a bitmap and a reference to where it is stored).
    private ArrayList<LoadedImage> parseImages(String images) {
        System.out.println("parsing images: " + images);
        ArrayList<LoadedImage> loadedImages = new ArrayList<LoadedImage>();
        while (images.contains("|")) {
            String reference = images.substring(0, images.indexOf("|"));
            images = images.substring(images.indexOf("|") + 1);

            if (reference.contains(assetsReferenceKey)) {
                reference = reference.substring(assetsReferenceKey.length());
                System.out.println("DECODING ASSET: " + reference);
                try {

                    loadedImages.add(new LoadedImage(BitmapFactory.decodeStream(getAssets().open(reference)), assetsReferenceKey + reference));
                    // System.out.println("APPLIED REFERENCE: " + loadedImages.get(loadedImages.size() - 1).toString());
                } catch (IOException e) {
                    System.out.println("Failed decoding asset");
                    e.printStackTrace();
                }
            } else {
                System.out.println("DECODING FILESYSTEM FILE" + reference);
                loadedImages.add(new LoadedImage(BitmapFactory.decodeFile(reference), reference));
            }


        }
        if (!images.equals("")) {

            String reference = images;
            if (reference.contains(assetsReferenceKey)) {
                reference = reference.substring(assetsReferenceKey.length());
                System.out.println("DECODING ASSET: " + reference);
                try {
                    loadedImages.add(new LoadedImage(BitmapFactory.decodeStream(getAssets().open(reference)), assetsReferenceKey + reference));
                    //System.out.println("APPLIED REFERENCE: " + loadedImages.get(loadedImages.size() - 1).toString());
                } catch (IOException e) {
                    System.out.println("Failed decoding asset");
                    e.printStackTrace();
                }
            } else {
                System.out.println("DECODING FILESYSTEM FILE" + reference);
                loadedImages.add(new LoadedImage(BitmapFactory.decodeFile(reference), reference));
            }

        }
        return loadedImages;
    }

    public static List<Word> getWords() {
        return words;
    }

    //testing method which makes random testing words
    private void generateTestWord(int numWords) {
        for (int j = 0; j < numWords; j++) {
            String s = "Testing Word";

            //String s = "";
            //for (int i = 0; i < 5; i++) {
            //    s = s + (char) (((int) (Math.random() * 26)) + 'a');
            //}
            Word w = new Word(s);
            ArrayList<LoadedImage> temp = new ArrayList<LoadedImage>();
            temp.add(new LoadedImage(getImageAsset("cow.jpg"), assetsReferenceKey + "cow.jpg"));
            temp.add(new LoadedImage(getImageAsset("dog.jpg"), assetsReferenceKey + "dog.jpg"));
            //temp.add(new LoadedImage(getImageFromAppData(getFilesDir() + imageFolder + "/dog.jpg"), getFilesDir() + imageFolder + "/dog.jpg"));
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

    public void nextWord(View v) {
        Intent intent = new Intent(this, FlashcardActivity.class);
        intent.putExtra("condition", true);
        startActivity(intent);
    }

    public void Parental(View v) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    private Bitmap getImageAsset(String name) {

        try {
            //System.out.println("Getting Image Asset");
            return BitmapFactory.decodeStream(getAssets().open(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //writes the data to the disk
    private void writeData() {
        //System.out.println("WRITING DATA IN FLASHCARDACTIVITY");
        int numInteractions = 0;
        for (Word w : words) {
            numInteractions += w.getInteractions().size();
        }
        //System.out.println("THERE ARE " + numInteractions + " INTERACTIONS");

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

        wordsLines.add("Word" + CSVParser.csvSeparatorChar + "Images" + CSVParser.csvSeparatorChar + "InteractionKeys" + CSVParser.csvSeparatorChar + "Tags" + CSVParser.csvSeparatorChar + "Questions" + '\n');

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
        //CSVParser.savePublicly(interactionsLines, interactionsFilePath, this);

        CSVParser.writeFile(interactionsLines, interactionsFilePath);


    }

    //makes sure each interact
    private void generateInteractionKeys() {
        int n = 0;
        for (Word w : words) {
            for (Interaction i : w.getInteractions()) {
                i.setKey(n);
                n++;
            }
        }
    }

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

        //write tags
        if (w.getTags().size() > 0) {
            for (String tag : w.getTags()) {
                s = s + tag + CSVParser.listSeparatorChar;
                System.out.println(tag);
            }
            s = s.substring(0, s.length() - 1);
        }
        s = s + CSVParser.csvSeparatorChar;

        //write questions
        if (w.getQuestions().size() > 0) {
            for (String question : w.getQuestions()) {
                s = s + question + CSVParser.listSeparatorChar;
                System.out.println(question);
            }
            s = s.substring(0, s.length() - 1);
        }


        s = s + '\n';

        return s;
    }





}

