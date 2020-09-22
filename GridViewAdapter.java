package com.example.jmirande.candycrush;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Le bro des top 1 on 11/03/2018.
 */

public class GridViewAdapter extends BaseAdapter {

    private Activity mContext;

    private GridView grille;

    // Keep all Images in array
    public Integer[] mThumbIds;

    // Constructor
    public GridViewAdapter(EnJeu enJeu, Integer[] items) {
        this.mContext = enJeu;
        this.mThumbIds = items;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setAdjustViewBounds(true);
        //imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
        return imageView;
    }

}
