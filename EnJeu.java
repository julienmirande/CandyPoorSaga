package com.example.jmirande.candycrush;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class EnJeu extends AppCompatActivity {

    private EnJeu that;
    private int TAILLE;
    private TextView TVscore;
    private int score;
    private int difficulte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        that = this;
        Intent intent = getIntent();


        final GridView grille = (GridView) findViewById(R.id.Grille);

        difficulte = intent.getIntExtra("DIFFICULTE",-1);
        switch (difficulte)
        {
            case 1:
                TAILLE = 5;
                break;
            case 2:
                TAILLE = 10;
                break;
            case 3:
                TAILLE = 15;
                break;
            case 4:
                TAILLE = 20;
                break;
            case 5:
                TAILLE = 25;
                break;
        }

        grille.setNumColumns(TAILLE);

        score = 0;

        TVscore = (TextView) findViewById(R.id.TVScore);
        TVscore.setText(getString(R.string.CurrentScore) + score);

        final Integer[] tableauDeRessources = new Integer[TAILLE*TAILLE];


        for (int i=0 ; i <  tableauDeRessources.length ; i++)
        {
                int randomTemporaire = getRandom(1,5);
                switch (randomTemporaire)
                {
                    case 1:
                        tableauDeRessources[i] = R.drawable.tuile1;
                        break;
                    case 2:
                        tableauDeRessources[i] = R.drawable.tuile2;
                        break;
                    case 3:
                        tableauDeRessources[i] = R.drawable.tuile3;
                        break;
                    case 4:
                        tableauDeRessources[i] = R.drawable.tuile4;
                        break;

                }
        }


        grille.setAdapter(new GridViewAdapter(this, tableauDeRessources));


        grille.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                 if (trouverCouleur(tableauDeRessources[position]) != trouverCouleur(R.drawable.tuile0))
                {
                    supprimerCasesMemeCouleur(position,grille,tableauDeRessources);
                    essayerDecalerBas(position,grille,tableauDeRessources);
                    essayerDecalerGauche(position,grille,tableauDeRessources);
                    if (finDuGame(tableauDeRessources))
                    {
                        Toast.makeText(that, getString(R.string.NoMoreMoves), Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable(){
                            @Override
                            public void run(){
                                Intent intentScore = new Intent(that, Score.class);
                                intentScore.putExtra("DIFFICULTE",that.difficulte);
                                intentScore.putExtra("SCORE",that.score);
                                startActivity(intentScore);
                            }
                        }, 3000);

                    }
                }

            }
            });

    }

    private boolean finDuGame(Integer[] tableauDeRessources)
    {
        boolean finis = true;
        for ( int i= 0 ; i < that.TAILLE *that.TAILLE ; i++)
        {
            if (trouverCouleur(tableauDeRessources[i])!= trouverCouleur(R.drawable.tuile0))
            {
                if (estDansLaGrille(i-that.TAILLE) && trouverCouleur(tableauDeRessources[i]) == trouverCouleur(tableauDeRessources[i-that.TAILLE]))
                {
                    finis = false;
                }

                if (estDansLaGrille(i+1) && trouverCouleur(tableauDeRessources[i]) == trouverCouleur(tableauDeRessources[i+1]))
                {
                    finis = false;
                }

                if (estDansLaGrille(i+that.TAILLE) && trouverCouleur(tableauDeRessources[i]) == trouverCouleur(tableauDeRessources[i+that.TAILLE]))
                {
                    finis = false;
                }

                if (estDansLaGrille(i-1) &&trouverCouleur(tableauDeRessources[i]) == trouverCouleur(tableauDeRessources[i-1]))
                {
                    finis = false;
                }
            }

        }
        return finis;
    }

    private void essayerDecalerBas(int position, GridView grille, Integer[] tableauDeRessources) {
        int taille = that.TAILLE * that.TAILLE - 1;
        int posBlanc = taille;
        int modulo = taille;
            for (int i = 0; i < taille+1; i++) {

                if (trouverCouleur(tableauDeRessources[i]) == trouverCouleur(R.drawable.tuile0))
                {
                    modulo = i%that.TAILLE;

                    for(int j=i;j>=0;j=j-that.TAILLE)
                    {
                        if(estDansLaGrille(j-that.TAILLE))
                            tableauDeRessources[j] = tableauDeRessources[j-that.TAILLE];
                    }
                    tableauDeRessources[modulo] = R.drawable.tuile0;

                }

            }
    }



    private void essayerDecalerGauche(int position, GridView grille, Integer[] tableauDeRessources) {
        boolean trouveBlanc;

        for (int nbDecalagePossible = 0; nbDecalagePossible < that.TAILLE; nbDecalagePossible++) {
            for (int i = 0; i < that.TAILLE; i++) {

                if (colonneVide(i,tableauDeRessources))
                {
                    decalerGauche(i,tableauDeRessources);
                }
            }
        }

    }

    private boolean colonneVide(int position,Integer[] tableauRessources) {
        boolean trouveBlanc = true;
        while (position < that.TAILLE * that.TAILLE && trouveBlanc)
        {
            if (trouverCouleur(tableauRessources[position]) != trouverCouleur(R.drawable.tuile0))
            {
                trouveBlanc = false;
            }
            position += that.TAILLE;
        }
        return trouveBlanc;
    }


    public void decalerGauche(int position, Integer[] tableauDeRessources)
    {

            while (position < that.TAILLE * that.TAILLE )
            {
                if (estDansLaGrille(position+1) && !estBordDroit(position))
                {
                    tableauDeRessources[position] = tableauDeRessources[position+1];
                    tableauDeRessources[position+1] = R.drawable.tuile0;
                }

                position+= that.TAILLE;
            }

    }

    private void supprimerCasesMemeCouleur(int position, GridView grille, Integer[] tableauDeRessources) {


        List<Integer> listPositionCasesASupprimer = new ArrayList<Integer>();

        List<Integer> listCasesAutour = getCaseArround(position,listPositionCasesASupprimer,tableauDeRessources);



        Integer[] CasesASupprimer = listCasesAutour.toArray(new Integer[listCasesAutour.size()]);

        if (CasesASupprimer.length > 1)
        {
            for (int i=0 ; i < CasesASupprimer.length; i++)
            {
                tableauDeRessources[CasesASupprimer[i]] = R.drawable.tuile0;
            }


            TVscore.setText(getString(R.string.CurrentScore) + score);
        }

        grille.setAdapter(new GridViewAdapter(that, tableauDeRessources));
    }

    private int trouverCouleur(int id)
    {
        int value = -1;
        switch (id)
        {
            case R.drawable.tuile0:
                value = 0;
                break;
            case R.drawable.tuile1:
                value = 1;
                break;
            case R.drawable.tuile2:
                value = 2;
                break;
            case R.drawable.tuile3:
                value = 3;
                break;
            case R.drawable.tuile4:
                value = 4;
                break;
        }

        return value;
    }

    private List<Integer> getCaseArround(int position, List<Integer> listPositionCasesASupprimer, Integer[] tableauRessources)
    {


        if (!listPositionCasesASupprimer.contains(position))
        {
            that.score += 10;
            listPositionCasesASupprimer.add(position);
        }


        int Dessus = position - that.TAILLE;
        int Droite = position + 1;
        int Dessous = position + that.TAILLE;
        int Gauche = position -1;


        if (estDansLaGrille(Dessous) && trouverCouleur(tableauRessources[position]) == trouverCouleur(tableauRessources[Dessous]))
        {
            if (!listPositionCasesASupprimer.contains(Dessous))
            {
                that.score += 10;
                listPositionCasesASupprimer.add(Dessous);
                listPositionCasesASupprimer.addAll(getCaseArround(Dessous,listPositionCasesASupprimer,tableauRessources));
            }

        }

        if (estDansLaGrille(Dessus) && trouverCouleur(tableauRessources[position]) == trouverCouleur(tableauRessources[Dessus]))
        {
            if (!listPositionCasesASupprimer.contains(Dessus))
            {
                that.score += 10;
                listPositionCasesASupprimer.add(Dessus);
                listPositionCasesASupprimer.addAll(getCaseArround(Dessus,listPositionCasesASupprimer,tableauRessources));
            }
        }

        if (estDansLaGrille(Droite) && !estBordDroit(position) && trouverCouleur(tableauRessources[position]) == trouverCouleur(tableauRessources[Droite]))
        {
            if (!listPositionCasesASupprimer.contains(Droite))
            {
                that.score += 10;
                listPositionCasesASupprimer.add(Droite);
                listPositionCasesASupprimer.addAll(getCaseArround(Droite,listPositionCasesASupprimer,tableauRessources));
            }
        }

        if (estDansLaGrille(Gauche) && !estBordGauche(position) &&trouverCouleur(tableauRessources[position]) == trouverCouleur(tableauRessources[Gauche]))
        {
            if (!listPositionCasesASupprimer.contains(Gauche)) {
                that.score += 10;
                listPositionCasesASupprimer.add(Gauche);
                listPositionCasesASupprimer.addAll(getCaseArround(Gauche, listPositionCasesASupprimer, tableauRessources));
            }
        }


        return listPositionCasesASupprimer;
    }


    private boolean estEnHaut(int position) {
        return position > 0 || position < that.TAILLE;
    }

    private boolean estBordDroit(int position) {
        return  position%that.TAILLE == that.TAILLE-1;
    }

    private boolean estBordGauche(int position) {
        return  position%that.TAILLE == 0;
    }


    public boolean estDansLaGrille(int position)
    {
        return (position >= 0 && position < that.TAILLE*that.TAILLE);
    }

    public int getRandom(int min ,int max)
    {
        Random r = new Random();
        int random = r.nextInt(max - min) + min;
        return  random;
    }
}
