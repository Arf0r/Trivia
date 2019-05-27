package com.example.trivia;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScoreAdapter extends ArrayAdapter {

    ArrayList<String> highscores;

    // Contructor
    public HighScoreAdapter(Context context, int resource, ArrayList<String> highscores) {
        super(context, resource, highscores);
        this.highscores = highscores;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Set the adapter to user highs score adapter layout
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.high_score_adapter, parent, false);
        }

        // Set the textview to show the scores
        String highscore = highscores.get(position);
        TextView score = convertView.findViewById(R.id.score);
        score.setText(highscore);

        return convertView;
    }
}
