
package com.example.jmirande.candycrush;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


public class ScoreArrayAdapter extends ArrayAdapter<ScoreType> {

    private Context context;
    private List<ScoreType> scoreList = new ArrayList<>();

    public ScoreArrayAdapter( Context context, ArrayList<ScoreType> list) {
        super(context, 0 , list);
        this.context = context;
        scoreList = list;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.score_list_item,parent,false);

        ScoreType currentScore = scoreList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageViewNiveau);
        image.setImageResource(currentScore.getImageNiveau());

        TextView scoreMax = (TextView) listItem.findViewById(R.id.textViewScoreMax);
        scoreMax.setText(currentScore.getScore());

        TextView score = (TextView) listItem.findViewById(R.id.textViewScore);
        score.setText(currentScore.getBestScore());

        return listItem;
    }
}
