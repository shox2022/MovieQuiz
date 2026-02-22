package com.example.moviequiz;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moviequiz.data.Question;
import com.example.moviequiz.data.QuizDatabase;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    TextView tvQuestion, tvTimer;
    RadioGroup radioGroup;
    Button btnNext;

    List<Question> questions;
    int index = 0;
    int score = 0;

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_quiz);

        tvQuestion = findViewById(R.id.tvQuestion);
        tvTimer = findViewById(R.id.tvTimer);
        radioGroup = findViewById(R.id.radioGroup);
        btnNext = findViewById(R.id.btnNext);

        QuizDatabase db = QuizDatabase.getInstance(this);
        questions = db.questionDao().getAllQuestions();

        if (questions.isEmpty()) {
            insertSampleQuestions(db);
            questions = db.questionDao().getAllQuestions();
        }

        showQuestion();

        btnNext.setOnClickListener(v -> {
            checkAnswer();
            index++;
            if (index < questions.size()) {
                showQuestion();
            } else {
                Intent i = new Intent(this, ResultActivity.class);
                i.putExtra("score", score);
                startActivity(i);
                finish();
            }
        });
    }

    void showQuestion() {
        radioGroup.clearCheck();
        Question q = questions.get(index);

        tvQuestion.setText(q.question);
        ((RadioButton)findViewById(R.id.rb1)).setText(q.option1);
        ((RadioButton)findViewById(R.id.rb2)).setText(q.option2);
        ((RadioButton)findViewById(R.id.rb3)).setText(q.option3);
        ((RadioButton)findViewById(R.id.rb4)).setText(q.option4);

        startTimer();
    }

    void startTimer() {
        if (timer != null) timer.cancel();

        timer = new CountDownTimer(10000, 1000) {
            public void onTick(long ms) {
                tvTimer.setText("Time: " + ms / 1000);
            }
            public void onFinish() {
                btnNext.performClick();
            }
        }.start();
    }

    void checkAnswer() {
        timer.cancel();
        int checkedId = radioGroup.getCheckedRadioButtonId();
        int answer = questions.get(index).answer;

        if (checkedId == R.id.rb1 && answer == 1) score++;
        if (checkedId == R.id.rb2 && answer == 2) score++;
        if (checkedId == R.id.rb3 && answer == 3) score++;
        if (checkedId == R.id.rb4 && answer == 4) score++;
    }

    void insertSampleQuestions(QuizDatabase db) {

        Question q1 = new Question();
        q1.question = "Who directed Inception?";
        q1.option1 = "Christopher Nolan";
        q1.option2 = "Martin Scorsese";
        q1.option3 = "Steven Spielberg";
        q1.option4 = "Quentin Tarantino";
        q1.answer = 1;
        db.questionDao().insert(q1);

        Question q2 = new Question();
        q2.question = "Which movie won Best Picture in 2020?";
        q2.option1 = "1917";
        q2.option2 = "Joker";
        q2.option3 = "Parasite";
        q2.option4 = "Once Upon a Time in Hollywood";
        q2.answer = 3;
        db.questionDao().insert(q2);

        Question q3 = new Question();
        q3.question = "Who played Iron Man in the MCU?";
        q3.option1 = "Chris Evans";
        q3.option2 = "Robert Downey Jr.";
        q3.option3 = "Chris Hemsworth";
        q3.option4 = "Mark Ruffalo";
        q3.answer = 2;
        db.questionDao().insert(q3);

        Question q4 = new Question();
        q4.question = "Which movie is NOT part of The Lord of the Rings trilogy?";
        q4.option1 = "The Two Towers";
        q4.option2 = "The Return of the King";
        q4.option3 = "The Hobbit";
        q4.option4 = "The Fellowship of the Ring";
        q4.answer = 3;
        db.questionDao().insert(q4);

        Question q5 = new Question();
        q5.question = "What year was The Matrix released?";
        q5.option1 = "1995";
        q5.option2 = "1997";
        q5.option3 = "1999";
        q5.option4 = "2001";
        q5.answer = 3;
        db.questionDao().insert(q5);
    }
}