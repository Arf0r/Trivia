package com.example.trivia;

import android.content.Intent;
import android.support.v4.app.NotificationCompatExtras;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class PlayActivity extends AppCompatActivity implements TriviaRequest.Callback {

    // Initialize variables
    private ArrayList trivia;
    private int counter;
    private String correctAnswer;
    private String difficulty;
    private int score = 0;
    TextView scoreView;
    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;

    // Show play layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Get the intent and start request new trivia questions from server
        Intent intent = getIntent();
        TriviaRequest x = new TriviaRequest(PlayActivity.this);
        x.getTrivia(this);
    }

    // Save the trivia objects with info from server in a list
    @Override
    public void gotTrivia(ArrayList<Trivia> trivia) {
        this.trivia = trivia;
        this.counter = 0;
    }

    // If error was returned, do nothing
    @Override
    public void gotTriviaError(String message) {
    }

    // If the "start/next" button is clicked...
    public void next(View view) {

        // If the game is over, start score activity
        if (this.counter  == this.trivia.size()) {
            Intent intent = new Intent(PlayActivity.this, ScoreActivity.class);
            intent.putExtra("score", this.score);
            startActivity(intent);
            finish();
        }

        // If there are still questions left...
        else {

            // Get the current question and save its data from the object into strings
            Trivia triviaquestion = (Trivia) this.trivia.get(this.counter);
            String type = (String) triviaquestion.getType();
            String questionString = (String) triviaquestion.getQuestion();
            this.correctAnswer = (String) triviaquestion.getRightAnswer();
            this.difficulty = (String) triviaquestion.getDifficulty();

            // set the views with the data
            TextView diff = findViewById(R.id.difficulty);
            diff.setText(triviaquestion.getDifficulty());
            TextView question = findViewById(R.id.question);
            question.setText(questionString);
            defrost();

            // if the question is of type boolean, run boolean method
            if (type.equals("boolean")) {
                Boolean(triviaquestion);

            // else run the mutiple choice method
            } else {
                Mutiple(triviaquestion);
            }

            // increment the question counter
            this.counter++;
        }

    }

    private void Mutiple(Trivia triviaquestion) {
        // Locate the buttons
        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4 );

        // Set the 'start/next' button to next
        Button next = (Button) findViewById(R.id.next);
        next.setText("Next");

        // make all 4 answer buttons visible
        answer1.setVisibility(View.VISIBLE);
        answer2.setVisibility(View.VISIBLE);
        answer3.setVisibility(View.VISIBLE);
        answer4.setVisibility(View.VISIBLE);

        // save the answers from the trivia object
        ArrayList wrongAnswers = triviaquestion.getWrongAnswers();
        String rightAnswer = triviaquestion.getRightAnswer();
        String wrongAnswer1 = (String) wrongAnswers.get(0);
        String wrongAnswer2 = (String) wrongAnswers.get(1);
        String wrongAnswer3 = (String) wrongAnswers.get(2);

        // choose a random int between 0 and 3
        Random rand = new Random();
        int random = rand.nextInt(4);

        // assign the answers to a button "randomly"
        switch(random){
            case 0:
                answer1.setText(rightAnswer);
                answer2.setText(wrongAnswer1);
                answer3.setText(wrongAnswer2);
                answer4.setText(wrongAnswer3);
                break;
            case 1:
                answer4.setText(rightAnswer);
                answer1.setText(wrongAnswer1);
                answer2.setText(wrongAnswer2);
                answer3.setText(wrongAnswer3);
                break;
            case 2:
                answer3.setText(rightAnswer);
                answer4.setText(wrongAnswer1);
                answer1.setText(wrongAnswer2);
                answer2.setText(wrongAnswer3);
                break;
            case 3:
                answer2.setText(rightAnswer);
                answer3.setText(wrongAnswer1);
                answer4.setText(wrongAnswer2);
                answer1.setText(wrongAnswer3);
                break;
        }
    }

    public void Boolean(Trivia triviaquestion){

        // Locate the buttons
        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4 );

        // Set the 'start/next' button to next
        Button next = (Button) findViewById(R.id.next);
        next.setText("Next");

        // Remove the upper and lower button
        answer1.setVisibility(View.GONE);
        answer2.setVisibility(View.VISIBLE);
        answer3.setVisibility(View.VISIBLE);
        answer4.setVisibility(View.GONE);

        // Get the answers from the trivia object
        ArrayList wrongAnswers = triviaquestion.getWrongAnswers();
        String rightAnswer = triviaquestion.getRightAnswer();
        String wrongAnswer = (String) wrongAnswers.get(0);

        // Generate a random in between 0 and 1
        Random rand = new Random();
        int random = rand.nextInt(1);

        // Assign the answers to the buttons "randomly"
        switch(random) {
            case 0:
                answer2.setText(wrongAnswer);
                answer3.setText(rightAnswer);

                break;
            case 1:
                answer2.setText(rightAnswer);
                answer3.setText(wrongAnswer);
                break;
        }
    }


    public void addScore(){
        // Show Toast saying the answer was correct
        Toast.makeText(this,"Correct!", Toast.LENGTH_SHORT).show();

        // Based on the difficulty of the question add 1,2 or 3 points to score
        if (this.difficulty.equals("easy")){
            this.score += 1;
        }
        else if (this.difficulty.equals("medium")){
            this.score += 2;
        }
        else {
            this.score += 3;
        }

        // Show score in textview
        scoreView = findViewById(R.id.score);
        scoreView.setText("Score :" + String.valueOf(this.score));
    }

    // If answer button is clicked, check if answer was correct, and add to score if so, then
    // freeze all button from being clicked
    public void clickanswer1(View view) {
        Button button = (Button) view;
        String text = (String) button.getText();
        if (text.equals(this.correctAnswer)){
            addScore();
        }
        else{
            Toast.makeText(this,"Incorrect :(", Toast.LENGTH_SHORT).show();
        }
        freeze();
    }

    // If answer button is clicked, check if answer was correct, and add to score if so, then
    // freeze all button from being clicked
    public void clickanswer2(View view) {
        Button button = (Button) view;
        String text = (String) button.getText();
        if (text.equals(this.correctAnswer)) {
            addScore();
        }
        else{
            Toast.makeText(this,"Incorrect :(", Toast.LENGTH_SHORT).show();
        }
        freeze();
    }

    // If answer button is clicked, check if answer was correct, and add to score if so, then
    // freeze all button from being clicked
    public void clickanswer3(View view) {
        Button button = (Button) view;
        String text = (String) button.getText();
        if (text.equals(this.correctAnswer)) {
            addScore();
        }
        else{
            Toast.makeText(this,"Incorrect :(", Toast.LENGTH_SHORT).show();
        }
        freeze();
    }

    // If answer button is clicked, check if answer was correct, and add to score if so, then
    // freeze all button from being clicked
    public void clickanswer4(View view) {
        Button button = (Button) view;
        String text = (String) button.getText();
        if (text.equals(this.correctAnswer)) {
            addScore();
        }
        else{
            Toast.makeText(this,"Incorrect :(", Toast.LENGTH_SHORT).show();
        }
        freeze();
    }

    // Make all answer buttons unclickable
    public void freeze(){
        Button answer1 = (Button) findViewById(R.id.answer1);
        Button answer2 = (Button) findViewById(R.id.answer2);
        Button answer3 = (Button) findViewById(R.id.answer3);
        Button answer4 = (Button) findViewById(R.id.answer4);

        answer1.setClickable(false);
        answer2.setClickable(false);
        answer3.setClickable(false);
        answer4.setClickable(false);
    }

    // Make answer buttons clickable again
    public void defrost(){
        Button answer1 = (Button) findViewById(R.id.answer1);
        Button answer2 = (Button) findViewById(R.id.answer2);
        Button answer3 = (Button) findViewById(R.id.answer3);
        Button answer4 = (Button) findViewById(R.id.answer4);

        answer1.setClickable(true);
        answer2.setClickable(true);
        answer3.setClickable(true);
        answer4.setClickable(true);
    }
}



