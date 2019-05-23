package com.example.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity implements HighScoreRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        Intent intent = getIntent();

        HighScoreRequest x = new HighScoreRequest(HighScoreActivity.this);
        x.getHighScore(HighScoreActivity.this);
    }

    @Override
    public void gotHighScore(ArrayList<String> HighScore) {
        HighScoreAdapter highScoreAdapter = new HighScoreAdapter(HighScoreActivity.this,R.layout.high_score_adapter, HighScore);
        ListView list = findViewById(R.id.list);
        list.setAdapter(highScoreAdapter);

    }

    @Override
    public String gotHighScoreError(String message) {

        return message;
    }
}





