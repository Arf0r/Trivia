package com.example.trivia;

import android.content.Context;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TriviaRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    private Context context;
    private Callback callback;

    public TriviaRequest(Context incomingContext) {
        this.context = incomingContext;
    }

    // Get the error message if something goes wrong
    @Override
    public void onErrorResponse(VolleyError error) {
        callback.gotTriviaError(error.getMessage());
    }

    // If server responds as expected
    @Override
    public void onResponse(JSONObject response) {
        // Make a new arraylist
        ArrayList arrayList = new ArrayList();
        try {
            // Get the arraylist from the server
            JSONArray triviaList = response.getJSONArray("results");

            // Loop over the server data and save all variables in strings
            for (int i = 0; i < triviaList.length(); i++){
                JSONObject triviaItem = triviaList.getJSONObject(i);
                String question = triviaItem.getString("question");
                String rightAnswer = triviaItem.getString("correct_answer");
                ArrayList wrongAnswers = new ArrayList();
                JSONArray incorrectAnswers = triviaItem.getJSONArray("incorrect_answers");
                for (int j = 0; j < incorrectAnswers.length(); j++){
                    wrongAnswers.add(incorrectAnswers.getString(j));
                }
                String category = triviaItem.getString("category");
                String type = triviaItem.getString("type");
                String difficulty = triviaItem.getString("difficulty");

                // Put all data into a new trivia object and add it to the list
                Trivia trivia = new Trivia(question, rightAnswer, wrongAnswers, category, difficulty, type);
                arrayList.add(trivia);
            }
            // Return the list via callback
            callback.gotTrivia(arrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Make the request from the server
    public void getTrivia(Callback playActivity) {
        this.callback = playActivity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://opentdb.com/api.php?amount=10", null, this, this);
        queue.add(jsonObjectRequest);
    }

    // Return variables to request page
    public interface Callback {
        void gotTrivia(ArrayList<Trivia> trivia);
        void gotTriviaError(String message);
    }
}
