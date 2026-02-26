package com.example.moviequiz.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionDao {

    @Query("SELECT * FROM questions")
    List<Question> getAllQuestions();

    @Insert
    void insert(Question question);
}