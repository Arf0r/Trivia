package com.example.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity implements HighScoreRequest.Callback {

    // Show the high score layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        Intent intent = getIntent();

        // Make request from the IDE server
        HighScoreRequest x = new HighScoreRequest(HighScoreActivity.this);
        x.getHighScore(HighScoreActivity.this);
    }

    // Put data from server into the listview using an adapter
    @Override
    public void gotHighScore(ArrayList<String> HighScore) {
        HighScoreAdapter highScoreAdapter = new HighScoreAdapter(HighScoreActivity.this,R.layout.high_score_adapter, HighScore);
        ListView list = findViewById(R.id.list);
        list.setAdapter(highScoreAdapter);

    }

    // If something goes wrong, return the error message
    @Override
    public String gotHighScoreError(String message) {
        return message;
    }
}





