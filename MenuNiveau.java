package com.example.jmirande.candycrush;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class MenuNiveau extends AppCompatActivity {

    RatingBar ratingBar;
    TextView tailleGrille;
    int nbCaseLargeur;
    int difficulte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_niveau);
        tailleGrille = (TextView) findViewById(R.id.TVTailleGrille);
        nbCaseLargeur = 5;
        difficulte = 1;
        tailleGrille.setText(getString(R.string.grid) + nbCaseLargeur + " x " + nbCaseLargeur);
        addListenerOnRatingBar();

    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);


        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                int rate = (int) rating;

                switch(rate)
                {
                    case 1:
                        difficulte = 1;
                        nbCaseLargeur = 5;
                        break;
                    case 2:
                        difficulte = 2;
                        nbCaseLargeur = 10;
                        break;
                    case 3:
                        difficulte = 3;
                        nbCaseLargeur = 15;
                        break;
                    case 4:
                        difficulte = 4;
                        nbCaseLargeur = 20;
                        break;
                    case 5:
                        difficulte = 5;
                        nbCaseLargeur = 25;
                        break;
                }

                tailleGrille.setText(getString(R.string.grid) + nbCaseLargeur + " x " + nbCaseLargeur);

            }
        });
    }



    public void commencer(View view) {
        Intent intent = new Intent(this, EnJeu.class);
        intent.putExtra("DIFFICULTE",difficulte);
        startActivity(intent);
    }
}
