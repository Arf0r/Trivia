package com.example.trivia;

import java.io.Serializable;
import java.util.ArrayList;

public class Trivia implements Serializable {

    // Initialize variables
    private String question;
    private String rightAnswer;
    private ArrayList wrongAnswers;
    private String category;
    private String difficulty;
    private String type;

    // Contructor
    public Trivia(String question, String rightAnswer, ArrayList wrongAnswers, String category, String difficulty, String type) {

        this.question = question;
        this.rightAnswer = rightAnswer;
        this.wrongAnswers = wrongAnswers;
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;

    }

    // Getters and setters for all varaibles of the object
    public String getQuestion() {
        return question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public ArrayList getWrongAnswers() {
        return wrongAnswers;
    }
    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getType() {
        return type;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setWrongAnswers(ArrayList wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setType(String type) {
        this.type = type;
    }
}

