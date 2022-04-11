package com.example.appsforgood;

import androidx.room.Dao;

@Dao
public interface WordDAO {

    public abstract String startKey();
    public abstract String endKey();
}
