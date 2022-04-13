package com.example.appsforgood.data;

import androidx.room.*;

//@Database(entities = {Word.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WordDAO wordDao();
}
