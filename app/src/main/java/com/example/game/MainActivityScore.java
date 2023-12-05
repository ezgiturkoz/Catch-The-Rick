package com.example.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivityScore extends AppCompatActivity {

    TextView textView9;
    TextView textView10;
    ImageView imageView17;
    EditText editText;
    Button saveButton;
    Button backButton;
    int score;
    String selectedDifficulty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_score);


        initializeViews();
        setupListeners();

        selectedDifficulty = getIntent().getStringExtra("selectedDifficulty");
        if (selectedDifficulty == null) {
            selectedDifficulty = "easy";
        }

        handleIntentData();
    }

    private void initializeViews() {
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        imageView17 = findViewById(R.id.imageView17);
        editText = findViewById(R.id.editText);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveButton);
    }

    private void setupListeners() {
        backButton.setOnClickListener(view -> navigateToDifficulty());
        saveButton.setOnClickListener(view -> saveScoreAndNavigate());
    }
    private void navigateToDifficulty() {
        String userName = editText.getText().toString();

        if (!userName.isEmpty()) {
            saveScoreAndNavigate();
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivityDifficulty.class);
            startActivity(intent);
        }
    }


    private void handleIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            score = intent.getIntExtra("userScore", 0);
            selectedDifficulty = intent.getStringExtra("selectedDifficulty");
            if (score != 0) {
                displayScore();
                if (!editText.getText().toString().isEmpty()) {
                    saveScoreAndNavigate();
                }
            }
        } else {
        }
    }
    private void displayScore() {

        textView10.setText("Your Score: " + String.valueOf(score));
    }


    private void saveScoreAndNavigate() {
        String userName = editText.getText().toString();

        if (!userName.isEmpty()) {
            saveScoreToSharedPreferences(userName, score);
        } else {
            showEmptyNameAlert();
        }
    }

    private void saveScoreToSharedPreferences(String userName, int score) {

        SharedPreferences sharedPreferences = getSharedPreferences("userScore", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int size = sharedPreferences.getInt("size", 0);
        String selectedDifficulty = getIntent().getStringExtra("selectedDifficulty");

        editor.putString("userName_" + size, userName);
        editor.putString("selectedDifficulty_" + size, selectedDifficulty);
        editor.putInt("userScore_" + size, score);
        editor.putInt("size", size + 1);
        editor.apply();

        updateSize(selectedDifficulty);
        navigateToScoreTable(userName, score, selectedDifficulty);
    }

    private void updateSize(String selectedDifficulty) {
        SharedPreferences sharedPreferences = getSharedPreferences("userScores", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int sizeEasy = sharedPreferences.getInt("sizeEasy", 0);
        int sizeMedium = sharedPreferences.getInt("sizeMedium", 0);
        int sizeHard = sharedPreferences.getInt("sizeHard", 0);

        if ("easy".equals(selectedDifficulty)) {
            sizeEasy++;
        } else if ("medium".equals(selectedDifficulty)) {
            sizeMedium++;
        } else if ("hard".equals(selectedDifficulty)) {
            sizeHard++;
        }

        editor.putInt("sizeEasy", sizeEasy);
        editor.putInt("sizeMedium", sizeMedium);
        editor.putInt("sizeHard", sizeHard);
        editor.apply();
    }

    private void showEmptyNameAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please enter a name!")
                .setCancelable(false)
                .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void navigateToScoreTable(String userName, int score, String selectedDifficulty) {
        Intent intent = new Intent(getApplicationContext(), MainActivityHighScores.class);
        intent.putExtra("userName", userName);
        intent.putExtra("userScore", score);
        intent.putExtra("selectedDifficulty", selectedDifficulty);
        startActivity(intent);
    }
}

