package com.example.moviequiz.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Question.class}, version = 2)
public abstract class QuizDatabase extends RoomDatabase {

    private static QuizDatabase instance;

    public abstract QuestionDao questionDao();

    public static synchronized QuizDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            QuizDatabase.class, "quiz_db")
                           .allowMainThreadQueries()
                           .fallbackToDestructiveMigration()
                           .build();
        }
        return instance;
    }
}