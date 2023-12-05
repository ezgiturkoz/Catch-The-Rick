package com.example.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivityHighScores extends AppCompatActivity {

    TextView textView11;
    ImageView imageView33;
    ListView listViewEasy;
    ListView listViewMedium;
    ListView listViewHard;
    Button backToDifficultyButton;
    ImageButton deleteButton;
    Button buttonEasy;
    Button buttonMedium;
    Button buttonHard;
    String userName;
    int userScore;
    String selectedDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_high_scores);

        listViewEasy = findViewById(R.id.listViewEasy);
        listViewMedium = findViewById(R.id.listViewMedium);
        listViewHard = findViewById(R.id.listViewHard);

        if (listViewEasy == null || listViewMedium == null || listViewHard == null) {
            return;
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedDifficulty")) {
            userName = intent.getStringExtra("userName");
            selectedDifficulty = intent.getStringExtra("selectedDifficulty");
            userScore = intent.getIntExtra("userScore", 0);
            Log.d("MyGame", "selectedDifficulty: " + selectedDifficulty);
        } else {
            selectedDifficulty = "easy";
            userScore = 0;
        }


        getScoresAndDisplay(selectedDifficulty);


        backToDifficultyButton = findViewById(R.id.backToDifficultyButton);
        buttonEasy = findViewById(R.id.buttonEasy);
        buttonMedium = findViewById(R.id.buttonMedium);
        buttonHard = findViewById(R.id.buttonHard);
        textView11 = findViewById(R.id.textView11);
        imageView33 = findViewById(R.id.imageView33);
        deleteButton = findViewById(R.id.deleteButton);

        buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDifficulty = "easy";
                getScoresAndDisplay(selectedDifficulty);
            }
        });
        buttonMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDifficulty = "medium";
                getScoresAndDisplay(selectedDifficulty);
            }
        });
        buttonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDifficulty = "hard";
                getScoresAndDisplay(selectedDifficulty);
            }
        });
        backToDifficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivityDifficulty.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListView listView = getVisibleListView();
                Log.d("MainActivityScoreTable", "deleteButton onClick - selectedDifficulty: " + selectedDifficulty);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityHighScores.this);
                builder.setTitle("Are you sure to delete your scores?");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (listView != null) {
                            SharedPreferences sharedPreferences = getSharedPreferences("userScore", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("size", 0);
                            editor.apply();

                            Log.d("MainActivityScoreTable", "deleteButton onClick - selectedDifficulty: " + selectedDifficulty);
                            Log.d("MainActivityScoreTable", "deleteButton onClick - visibleListView: " + listView);

                            deleteScoresForDifficulty(editor, sharedPreferences, listView, selectedDifficulty);
                            listView.setAdapter(null);
                            listView.setVisibility(View.GONE);
                            Toast.makeText(MainActivityHighScores.this, selectedDifficulty + " scores deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.show();
            }
        });

    }
    private ListView getVisibleListView() {
        if (listViewEasy.getVisibility() == View.VISIBLE) {
            return listViewEasy;
        } else if (listViewMedium.getVisibility() == View.VISIBLE) {
            return listViewMedium;
        } else if (listViewHard.getVisibility() == View.VISIBLE) {
            return listViewHard;
        }
        return null;
    }

    private void deleteScoresForDifficulty(SharedPreferences.Editor editor, SharedPreferences sharedPreferences, ListView listView, String difficulty) {
        int sizeEasy = sharedPreferences.getInt("sizeEasy", 0);
        int sizeMedium = sharedPreferences.getInt("sizeMedium", 0);
        int sizeHard = sharedPreferences.getInt("sizeHard", 0);

        int size = sizeEasy + sizeMedium + sizeHard;

        Log.d("MainActivityScoreTable", "deleteScoresForDifficulty - SizeEasy: " + sizeEasy + ", SizeMedium: " + sizeMedium + ", SizeHard: " + sizeHard);

        List<Integer> indicesToRemove = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String difficultyAtIndex = sharedPreferences.getString("selectedDifficulty_" + i, "");
            if (difficulty.equals(difficultyAtIndex)) {
                indicesToRemove.add(i);
            }
        }

        for (int index : indicesToRemove) {
            editor.remove("userName_" + index);
            editor.remove("userScore_" + index);
            editor.remove("selectedDifficulty_" + index);
            if ("easy".equals(difficulty)) {
                sizeEasy--;
            } else if ("medium".equals(difficulty)) {
                sizeMedium--;
            } else if ("hard".equals(difficulty)) {
                sizeHard--;
            }
        }

        Log.d("MainActivityScoreTable", "deleteScoresForDifficulty - Indices to remove: " + indicesToRemove.toString());
        Log.d("MainActivityScoreTable", "deleteScoresForDifficulty - SizeEasy after removal: " + sizeEasy + ", SizeMedium: " + sizeMedium + ", SizeHard: " + sizeHard);

        editor.putInt("sizeEasy", sizeEasy);
        editor.putInt("sizeMedium", sizeMedium);
        editor.putInt("sizeHard", sizeHard);

        editor.apply();

        getScoresAndDisplay(difficulty);
    }

    private void getScoresAndDisplay(String difficulty) {

        listViewEasy.setVisibility(View.GONE);
        listViewMedium.setVisibility(View.GONE);
        listViewHard.setVisibility(View.GONE);

        if (difficulty == null) {
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("userScore", MODE_PRIVATE);
        int sizeEasy = sharedPreferences.getInt("sizeEasy", 0);
        int sizeMedium = sharedPreferences.getInt("sizeMedium", 0);
        int sizeHard = sharedPreferences.getInt("sizeHard", 0);

        Map<String, List<Integer>> userScoresMap = new HashMap<>();

        for (int i = 0; i < sizeEasy + sizeMedium + sizeHard; i++) {
            String name = sharedPreferences.getString("userName_" + i, "");
            int userScore = sharedPreferences.getInt("userScore_" + i, 0);
            String difficulty2 = sharedPreferences.getString("selectedDifficulty_" + i, "");

            if (!userScoresMap.containsKey(name)) {
                userScoresMap.put(name, new ArrayList<>());
            }

            if (difficulty.equals(difficulty2)) {
                List<Integer> scores = userScoresMap.get(name);
                scores.add(userScore);
                userScoresMap.put(name, scores);
            }
        }


        List<String> formattedScores = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : userScoresMap.entrySet()) {
            String name = entry.getKey();
            List<Integer> scores = entry.getValue();
            for (int score : scores) {
                formattedScores.add(String.format("%s - %d", name, score));
            }
        }
        Collections.sort(formattedScores, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int score1 = Integer.parseInt(o1.substring(o1.lastIndexOf("-") + 1).trim());
                int score2 = Integer.parseInt(o2.substring(o2.lastIndexOf("-") + 1).trim());
                return Integer.compare(score2, score1);
            }
        });



        ArrayAdapter<String> adapter;

        if ("easy".equals(difficulty)) {
            adapter = new ArrayAdapter<>(this, R.layout.customlist, formattedScores);
            listViewEasy.setAdapter(adapter);
            listViewEasy.setVisibility(View.VISIBLE);
            listViewMedium.setVisibility(View.GONE);
            listViewHard.setVisibility(View.GONE);
        } else if ("medium".equals(difficulty)) {
            adapter = new ArrayAdapter<>(this, R.layout.customlist, formattedScores);
            listViewMedium.setAdapter(adapter);
            listViewEasy.setVisibility(View.GONE);
            listViewMedium.setVisibility(View.VISIBLE);
            listViewHard.setVisibility(View.GONE);
        } else if ("hard".equals(difficulty)) {
            adapter = new ArrayAdapter<>(this, R.layout.customlist, formattedScores);
            listViewHard.setAdapter(adapter);
            listViewEasy.setVisibility(View.GONE);
            listViewMedium.setVisibility(View.GONE);
            listViewHard.setVisibility(View.VISIBLE);
        } else {
            return;
        }
    }

}







