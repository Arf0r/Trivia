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

    @Override
    public void onErrorResponse(VolleyError error) {
        String message = callback.gotHighScoreError(error.getMessage());
        Log.d("tag", message);
    }

    @Override
    public void onResponse(JSONArray response) {
        ArrayList arrayList = new ArrayList();
        Log.d("tag", "getHighScore: ");
        try {
            Log.d("tag", "getHighScore: ");
            JSONArray highScoreList = response;
            for (int i = 0; i < highScoreList.length(); i++){
                arrayList.add(highScoreList.getString(i));
            }
            Log.d("tag", String.valueOf(arrayList));
            callback.gotHighScore(arrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface Callback {
        void gotHighScore(ArrayList<String> HighScore);
        String gotHighScoreError(String message);
    }

    public void getHighScore(Callback activity) {
        Log.d("tag", "1");
        this.callback = activity;
        String url = "https://ide50-maarten-wijstma.legacy.cs50.io:8080/highscores2";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,this,this);
        queue.add(jsonArrayRequest);
    }
}
