package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivityDifficulty extends AppCompatActivity {

    Button easy;
    Button medium;
    Button hard;
    Button showScores;
    TextView textView3;
    String selectedDifficulty;
    Button backToStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_difficulty);

        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        hard = findViewById(R.id.hard);
        backToStart = findViewById(R.id.backToStart);
        showScores = findViewById(R.id.showScores);
        textView3 = findViewById(R.id.textView3);


        backToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivityStart.class);
                startActivity(intent);
            }
        });
        showScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(), MainActivityHighScores.class);
                startActivity(intent);
            }
        });

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDifficulty = "easy";
                openNextActivity("easy");

            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDifficulty = "medium";
                openNextActivity("medium");
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDifficulty = "hard";
                openNextActivity("hard");
            }
        });

    }
    private void openScoreTableFromDifficulty() {
        Intent intent = new Intent(MainActivityDifficulty.this, MainActivityHighScores.class);
        intent.putExtra("selectedDifficulty", getSelectedDifficulty());
        startActivity(intent);
    }

    private void openNextActivity(String difficulty) {
        selectedDifficulty = difficulty;

        Intent intent = new Intent(MainActivityDifficulty.this, MainActivityLetsStart.class);
        intent.putExtra("selectedDifficulty", difficulty);
        startActivity(intent);
    }
    private String getSelectedDifficulty() {

        return selectedDifficulty;
    }

}