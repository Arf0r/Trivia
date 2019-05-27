package com.example.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ScoreActivity extends AppCompatActivity {
    // Initialize variables
    TextView endScore;
    EditText userName;
    private int score;
    private String name;

    // Show the acitivity input score layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Save the intent including the score that was given from last page
        Intent intent = getIntent();
        this.score = (int) intent.getSerializableExtra("score");

        // Show the score in textview
        endScore = findViewById(R.id.endScore);
        endScore.setText("Score:" + String.valueOf(score));


    }

    // If the submit button is clicked
    public void submit(View view) {
        userName = findViewById(R.id.userName);
        this.name = userName.getText().toString();

        // Check if a username was given but the user
        if (name.equals("")) {
            // If not, show toast
            Toast.makeText(this, "Fill in a username first", Toast.LENGTH_SHORT).show();
        } else {
            // If yes, post the score and username to server and start main acitivity
            postScore(this.score, this.name);
            Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // Post score to server
    private void postScore(final int endScore, final String userName) {
        // save URL and make new stringrequest
        String url = "https://ide50-maarten-wijstma.legacy.cs50.io:8080/highscores2";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Success
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Success
                    }
                }) {
            protected Map<String, String> getParams() {
                // Put data in request
                Map<String, String> MyData = new HashMap<>();
                //MyData.put("Points", score + " points earned by: " + userName);
                MyData.put("Score", String.valueOf(endScore));
                MyData.put("Name", userName);
                return MyData;
                }
            };
        // Make request
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
