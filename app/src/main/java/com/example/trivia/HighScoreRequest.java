package com.example.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HighScoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener {
    private Context context;
    private Callback callback;

    public HighScoreRequest(Context incomingContext){
        this.context = incomingContext;
    }

    // If server responds with error, save the error message
    @Override
    public void onErrorResponse(VolleyError error) {
        String message = callback.gotHighScoreError(error.getMessage());
    }

    // If server responds as expected...
    @Override
    public void onResponse(JSONArray response) {
        // Make a new arraylist
        ArrayList arrayList = new ArrayList();
        try {
            // Save the response as JSONARRAY
            JSONArray highScoreList = response;

            // Loop over JSONARRAY and add the highscore data to list
            for (int i = 0; i < highScoreList.length(); i++){
                arrayList.add(highScoreList.getString(i));
            }
            // Return list via callback
            callback.gotHighScore(arrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Return variables to request page
    public interface Callback {
        void gotHighScore(ArrayList<String> HighScore);
        String gotHighScoreError(String message);
    }

    // Make the request from the server
    public void getHighScore(Callback activity) {
        this.callback = activity;
        String url = "https://ide50-maarten-wijstma.legacy.cs50.io:8080/highscores2";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,this,this);
        queue.add(jsonArrayRequest);
    }
}
