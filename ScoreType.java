package com.example.jmirande.candycrush;

/**
 * Created by Le bro des top 1 on 17/03/2018.
 */

public class ScoreType {

    private int imageNiveau;
    private String score;
    private String bestScore;

    public ScoreType(int imageNiveau, String score, String bestScore) {
        this.imageNiveau = imageNiveau;
        this.score = score;
        this.bestScore = bestScore;
    }

    public int getImageNiveau() {
        return imageNiveau;
    }

    public void setImageNiveau(int imageNiveau) {
        this.imageNiveau = imageNiveau;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getBestScore() {
        return bestScore;
    }

    public void setBestScore(String bestScore) {
        this.bestScore = bestScore;
    }
}
