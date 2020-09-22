package com.example.jmirande.candycrush;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Score extends AppCompatActivity {

    private ListView listView;
    private ScoreArrayAdapter adapter;
    private List<Integer> listeScore;
    private int difficulte;
    private int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Intent intent = getIntent();
        difficulte = intent.getIntExtra("DIFFICULTE", -1);
        score = intent.getIntExtra("SCORE", -1);

        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);

//        SharedPreferences.Editor e = sharedPreferences.edit().clear();
//        e.commit();
        Log.d("-----------INIT--------",sharedPreferences.getAll().toString());



        listeScore = restoreScore();


        ArrayList<ScoreType> scoreList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listesView);

        if (score > listeScore.get(difficulte-1))
        {
            listeScore.set(difficulte-1,score);
        }

        scoreList.add(new ScoreType(R.drawable.face1, "Score: " + listeScore.get(0), "Score max: 250"));
        scoreList.add(new ScoreType(R.drawable.face2, "Score: " + listeScore.get(1), "Score max: 1000"));
        scoreList.add(new ScoreType(R.drawable.face3, "Score: " + listeScore.get(2), "Score max: 2250"));
        scoreList.add(new ScoreType(R.drawable.face4, "Score: " + listeScore.get(3), "Score max: 4000"));
        scoreList.add(new ScoreType(R.drawable.face5, "Score: " + listeScore.get(4), "Score max: 6250"));

        adapter = new ScoreArrayAdapter(this, scoreList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (saveScore())
        {
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean saveScore()
    {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit().clear();

        for(int i=0;i<listeScore.size();i++)
        {
            editor.putInt(String.valueOf(i),listeScore.get(i));
        }

        return editor.commit();
    }

    public List<Integer> restoreScore()
    {

        listeScore = new ArrayList<Integer>();
        Map<String, ?> database = this.getPreferences(Context.MODE_PRIVATE).getAll();

            listeScore.add(0);
            listeScore.add(0);
            listeScore.add(0);
            listeScore.add(0);
            listeScore.add(0);

            for (String monScore : database.keySet()) {
                if ( monScore.equals("0"))
                {
                    listeScore.set(0,(Integer) database.get(monScore));
                }
                if ( monScore.equals("1"))
                {
                    listeScore.set(1,(Integer) database.get(monScore));
                }
                if ( monScore.equals("2"))
                {
                    listeScore.set(2,(Integer) database.get(monScore));
                }
                if ( monScore.equals("3"))
                {
                    listeScore.set(3,(Integer) database.get(monScore));
                }
                if ( monScore.equals("4"))
                {
                    listeScore.set(4,(Integer) database.get(monScore));
                }
                System.out.println(monScore);
            }




        return listeScore;
    }

    public void restartGame(View view) {
        saveScore();
        Intent intentScore = new Intent(this, EnJeu.class);
        intentScore.putExtra("DIFFICULTE",difficulte);
        startActivity(intentScore);
    }


    public void returnMenu(View view) {
        saveScore();
        Intent intentScore = new Intent(this, MenuNiveau.class);
        startActivity(intentScore);
    }
}
