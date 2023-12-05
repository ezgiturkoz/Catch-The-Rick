package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivityLetsStart extends AppCompatActivity {

    Button letsStartButton;
    ImageView imageView34;
    String selectedDifficulty;
    Button backToDifficulty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lets_start);

        imageView34 = findViewById(R.id.imageView34);
        backToDifficulty = findViewById(R.id.backToDifficulty);
        letsStartButton = findViewById(R.id.letsStartButton);

        selectedDifficulty = getIntent().getStringExtra("selectedDifficulty");
        Log.d("MainActivityLetsStart", "Selected Difficulty: " + selectedDifficulty);
        if (selectedDifficulty == null) {
            selectedDifficulty = "easy";
        }

        Log.d("MainActivityLetsStart", "Selected Difficulty: " + selectedDifficulty);
        


        backToDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivityDifficulty.class);
                startActivity(intent);
            }
        });


        letsStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letsstart(view);

            }
        });
    }


    public void letsstart(View view) {
        Intent intent;
        if (selectedDifficulty.equals("easy")) {
            intent = new Intent(this, MainActivityEasy.class);
        } else if (selectedDifficulty.equals("medium")) {
            intent = new Intent(this, MainActivityMedium.class);
        } else if (selectedDifficulty.equals("hard")) {
            intent = new Intent(this, MainActivityHard.class);
        } else {
            intent = new Intent(this, MainActivityEasy.class);
        }
        intent.putExtra("selectedDifficulty", selectedDifficulty);
        startActivity(intent);
    }

    }
