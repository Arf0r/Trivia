package com.example.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // Show main layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // If play button is clicked, go to play activity
    public void clickPlay(View view) {
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        startActivity(intent);
        finish();
    }

    // If high scores activity is clicked go to highs score acitivity
    public void clickScore(View view) {
        Intent intent = new Intent(MainActivity.this, HighScoreActivity.class);
        startActivity(intent);
        finish();
    }
}
