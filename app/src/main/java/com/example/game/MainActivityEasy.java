package com.example.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

public class MainActivityEasy extends AppCompatActivity {

    ImageView imageView18;
    ImageView imageView19;
    ImageView imageView20;
    ImageView imageView21;
    ImageView imageView22;
    ImageView imageView23;
    ImageView imageView24;
    ImageView imageView25;
    TextView textView5;
    TextView textView6;
    Handler handler;
    Runnable runnable;
    int score;
    ImageView[] imageArray;
    Button back;
    String selectedDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_easy);


        back = findViewById(R.id.back);

        imageView18 = findViewById(R.id.imageView18);
        imageView19 = findViewById(R.id.imageView19);
        imageView20 = findViewById(R.id.imageView20);
        imageView21 = findViewById(R.id.imageView21);
        imageView22 = findViewById(R.id.imageView22);
        imageView23 = findViewById(R.id.imageView23);
        imageView24 = findViewById(R.id.imageView24);
        imageView25 = findViewById(R.id.imageView25);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        back = findViewById(R.id.back);
        score = 0;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivityDifficulty.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("selectedDifficulty")) {
            selectedDifficulty = intent.getStringExtra("selectedDifficulty");
        } else {
        }

        imageArray = new ImageView[]{imageView18, imageView19, imageView20, imageView21, imageView22, imageView23, imageView24, imageView25};

        hideImages();

        new CountDownTimer(20000, 1000) {
            @Override
            public void onTick(long l) {
                textView5.setText("Time: " + l / 1000);
            }

            @Override
            public void onFinish() {
                textView5.setText("TIME OFF!");
                handler.removeCallbacks(runnable);
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                SharedPreferences sharedPreferences = getSharedPreferences("userScore", MODE_PRIVATE);
                int sizeEasy = sharedPreferences.getInt("sizeEasy", 0);

                boolean isNewHighScore = true;

                if (selectedDifficulty != null) {
                    for (int i = 0; i < sizeEasy; i++) {
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

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityEasy.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView;

                if (isNewHighScore && score > 0) {
                    int newSize = sizeEasy + 1;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("sizeEasy", newSize);
                    editor.putString("selectedDifficulty_" + sizeEasy, selectedDifficulty);
                    editor.putInt("userScore_" + sizeEasy, score);

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
        textView6.setText("Score: " + score);
    }

    public void hideImages() {

        handler =new  Handler();
        runnable = new Runnable() {

            @Override
            public void run() {
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                Random random = new Random();
                int i = random.nextInt(8);
                imageArray[i].setVisibility(View.VISIBLE);
                handler.postDelayed(this, 400);
            }
        };

        handler.post(runnable);

    }


};