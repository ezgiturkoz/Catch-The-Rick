package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivityStart extends AppCompatActivity {

    Button startButton;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);

        Intent intent = new Intent(getApplicationContext(), MainActivityDifficulty.class);
        startButton = findViewById(R.id.startButton);
        imageButton = findViewById(R.id.imageButton);
        startButton.setSoundEffectsEnabled(true);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfoDialog(MainActivityStart.this);
            }
        });


    }
    private void showInfoDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("How to Play");
        builder.setMessage("Your sole mission: catch those soaring Ricks! The more Ricks you nab, the higher your score soars. Top the charts by snagging the most Ricks and etch your name into the hall of high scores! Time to dive in—I say, let the quest begin!");

        builder.setNegativeButton("X", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void start(View view) {
    }

}