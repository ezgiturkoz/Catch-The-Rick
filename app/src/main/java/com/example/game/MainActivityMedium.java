package com.example.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivityMedium extends AppCompatActivity {

    TextView textView7;
    TextView textView8;
    ImageView imageView26;
    ImageView imageView27;
    ImageView imageView28;
    ImageView imageView29;
    ImageView imageView30;
    ImageView imageView31;
    ImageView imageView32;
    Handler handler;
    Runnable runnable;
    int score;
    ImageView[] imageArray;
    Button back3;
    String selectedDifficulty;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_medium);

        imageView26 = findViewById(R.id.imageView26);
        imageView27 = findViewById(R.id.imageView27);
        imageView28 = findViewById(R.id.imageView28);
        imageView29 = findViewById(R.id.imageView29);
        imageView30 = findViewById(R.id.imageView30);
        imageView31 = findViewById(R.id.imageView31);
        imageView32 = findViewById(R.id.imageView32);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        back3 = findViewById(R.id.back3);
        score = 0;

        Intent intent = getIntent();
        if (intent.hasExtra("selectedDifficulty")) {
            selectedDifficulty = intent.getStringExtra("selectedDifficulty");
        } else {
        }

        imageArray = new ImageView[]{imageView26, imageView27, imageView28, imageView29, imageView30, imageView31, imageView32};

        hideImages();

        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivityDifficulty.class);
                startActivity(intent);
            }
        });


        new CountDownTimer(20000, 1000) {

            @Override
            public void onTick(long l) {
                textView7.setText("Time: " + l / 1000);
            }

            @Override
            public void onFinish() {
                textView7.setText("TIME OFF!");
                handler.removeCallbacks(runnable);
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                SharedPreferences sharedPreferences = getSharedPreferences("userScore", MODE_PRIVATE);
                int sizeMedium = sharedPreferences.getInt("sizeMedium", 0);

                boolean isNewHighScore = true;

                if (selectedDifficulty != null) {
                    for (int i = 0; i < sizeMedium; i++) {
                        String difficultyAtIndex = sharedPreferences.getString("selectedDifficulty_" + i, "");
                        int userScoreAtIndex = sharedPreferences.getInt("userScore_" + i, 0);

                        if (selectedDifficulty.equals(difficultyAtIndex) && userScoreAtIndex >= score) {
                            isNewHighScore = false;
                            break;
                        }
                    }
                } else {
                    isNewHighScore = false;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityMedium.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView;

                if (isNewHighScore && score > 0) {
                    int newSize = sizeMedium + 1;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("sizeMedium", newSize);
                    editor.putString("selectedDifficulty_" + sizeMedium, selectedDifficulty);
                    editor.putInt("userScore_" + sizeMedium, score);

                    editor.apply();
                    dialogView = inflater.inflate(R.layout.custom_dialog, null);
                    handleCustomDialog(dialogView);
                } else {
                    dialogView = inflater.inflate(R.layout.custom_dialog_2, null);
                    handleCustomDialog2(dialogView);
                }
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
            private void handleCustomDialog(View dialogView) {
                TextView textViewDialogTitle = dialogView.findViewById(R.id.textViewDialogTitle);
                TextView textViewDialogMessage = dialogView.findViewById(R.id.textViewDialogMessage);
                Button tryagainButton = dialogView.findViewById(R.id.tryagainButton);
                Button homepageButton = dialogView.findViewById(R.id.homepageButton);
                Button savescoreButton = dialogView.findViewById(R.id.savescoreButton);

                textViewDialogTitle.setText("WUBBA LUBBA DUB DUB!");
                textViewDialogMessage.setText("Your Score is: " + score);

                tryagainButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = getIntent();
                        startActivity(intent);
                    }
                });

                homepageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MainActivityDifficulty.class);
                        startActivity(intent);
                    }
                });

                if (savescoreButton != null) {
                    savescoreButton.setVisibility(View.VISIBLE);
                    savescoreButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), MainActivityScore.class);
                            intent.putExtra("userScore", score);
                            intent.putExtra("selectedDifficulty", selectedDifficulty);
                            startActivity(intent);
                        }
                    });
                }
            }

            private void handleCustomDialog2(View dialogView) {
                TextView textViewDialogTitle = dialogView.findViewById(R.id.textViewDialogTitle);
                TextView textViewDialogMessage = dialogView.findViewById(R.id.textViewDialogMessage);
                Button tryagainButton = dialogView.findViewById(R.id.tryagainButton);
                Button homepageButton = dialogView.findViewById(R.id.homepageButton);

                textViewDialogTitle.setText("GAME OVER!");
                textViewDialogMessage.setText("Your Score is: " + score);

                tryagainButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = getIntent();
                        startActivity(intent);
                    }
                });

                homepageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MainActivityDifficulty.class);
                        startActivity(intent);
                    }
                });
            }

        }.start();
    }
    public void increaseScore(View view) {
        score++;
        textView8.setText("Score: " + score);
    }

    public void hideImages() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i = random.nextInt(7);
                imageArray[i].setVisibility(View.VISIBLE);
                handler.postDelayed(this, 360);
            }
        };

        handler.post(runnable);
    }
    private List<String> getScoreListForMediumMode() {
        SharedPreferences preferences = getSharedPreferences("MediumScores", MODE_PRIVATE);
        Set<String> scoreSet = preferences.getStringSet("scores", new HashSet<String>());
        List<String> scores = new ArrayList<>(scoreSet);

        return scores;

    }
};

