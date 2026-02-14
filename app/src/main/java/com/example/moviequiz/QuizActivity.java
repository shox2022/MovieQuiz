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
        Question q = new Question();
        q.question = "Who directed Inception?";
        q.option1 = "Christopher Nolan";
        q.option2 = "Scorsese";
        q.option3 = "Spielberg";
        q.option4 = "Tarantino";
        q.answer = 1;
        db.questionDao().insert(q);
    }
}