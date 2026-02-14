package com.example.moviequiz;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        Button start = findViewById(R.id.btnStart);

        start.setOnClickListener(v ->
                startActivity(new Intent(this, QuizActivity.class))
        );
    }
}