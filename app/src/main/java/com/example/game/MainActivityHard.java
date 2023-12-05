package com.example.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivityHard extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    TextView textView2;
    Handler handler;
    Runnable runnable;
    int score;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView imageView10;
    ImageView imageView11;
    ImageView imageView12;
    ImageView imageView13;
    ImageView imageView14;
    ImageView[] imageArray;
    Button back4;
    String selectedDifficulty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hard);

        imageView = findViewById(R.id.imageView);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);
        imageView10 = findViewById(R.id.imageView10);
        imageView11 = findViewById(R.id.imageView11);
        imageView12 = findViewById(R.id.imageView12);
        imageView13 = findViewById(R.id.imageView13);
        imageView14 = findViewById(R.id.imageView14);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        back4 = findViewById(R.id.back4);
        score = 0;

        back4.setOnClickListener(new View.OnClickListener() {
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

        imageArray = new ImageView[] {imageView, imageView2, imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,imageView10,imageView11,imageView12,imageView13,imageView14};

        hideImages();



        new CountDownTimer(20000, 1000) {

            @Override
            public void onTick(long l) {
                textView2.setText("Time: " +l/1000);
            }

            @Override
            public void onFinish() {
                textView2.setText("TIME OFF!");
                handler.removeCallbacks(runnable);
                for (ImageView image : imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                SharedPreferences sharedPreferences = getSharedPreferences("userScore", MODE_PRIVATE);
                int sizeHard = sharedPreferences.getInt("sizeHard", 0);

                boolean isNewHighScore = true;

                if (selectedDifficulty != null) {
                    for (int i = 0; i < sizeHard; i++) {
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

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityHard.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView;

                if (isNewHighScore && score > 0) {
                    int newSize = sizeHard + 1;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("sizeEasy", newSize);
                    editor.putString("selectedDifficulty_" + sizeHard, selectedDifficulty);
                    editor.putInt("userScore_" + sizeHard, score);

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
    public void increaseScore (View view) {

        score++;
        textView.setText("Score: " + score);
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
                int i = random.nextInt(14);
                imageArray[i].setVisibility(View.VISIBLE);
                handler.postDelayed(this, 320);
            }
        };

        handler.post(runnable);
    }

};

