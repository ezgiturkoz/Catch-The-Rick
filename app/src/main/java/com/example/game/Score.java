package com.example.game;

public class Score {
    private String userName;
    private int score;
    private String difficulty;

    public Score(String userName, int score, String difficulty) {
        this.userName = userName;
        this.score = score;
        this.difficulty = difficulty;
    }

    public String getUserName() {
        return userName;
    }

    public int getScore() {
        return score; // Return the score variable
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
