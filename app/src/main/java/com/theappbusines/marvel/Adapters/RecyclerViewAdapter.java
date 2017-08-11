package com.theappbusines.marvel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.theappbusines.marvel.R;
import com.theappbusines.marvel.activities.ComicDetailActivity;
import com.theappbusines.marvel.handler.ComicsHandler;
import com.theappbusines.marvel.model.Result;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Kasai Desktop on 10/08/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ComicHolder> {

private ArrayList<Result> mComicData;
    ComicsHandler comicsHandler;

public RecyclerViewAdapter(ArrayList<Result> mComicData){
        this.mComicData = mComicData;
        }


@Override
public ComicHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflatedView = LayoutInflater.from(parent.getContext());
        return  new ComicHolder(inflatedView.inflate(R.layout.marvelcomic_list_content, parent,false));
        }

@Override
public void onBindViewHolder(ComicHolder holder, int position) {
        holder.bindData(position);
        }

@Override
public int getItemCount() {
        return mComicData.size();
        }


public class ComicHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mComicCover;
    private TextView mTitle;
    private TextView mBlurb;


    public ComicHolder(View v){
        super(v);
        mComicCover = (ImageView) v.findViewById(R.id.imageViewCoverArt);
        mTitle = (TextView) v.findViewById(R.id.textViewTitle);
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Context context = itemView.getContext();
        Intent showComicDetailIntent = new Intent(context, ComicDetailActivity.class);
        //showComicDetailIntent.putExtra(PHOTO_KEY, mPhoto);
        context.startActivity(showComicDetailIntent);
    }

    public void bindData(int position) {
        comicsHandler = new ComicsHandler(mComicData.get(position));
        String imageURLString = comicsHandler.getImageFromComic(position);
        if (!imageURLString.equalsIgnoreCase("")) {
            Picasso.with(mComicCover.getContext())
                    .load(imageURLString)
                    .placeholder(R.drawable.placeholder)
                    .resize(75,100)
                    .into(mComicCover);
        }

        mTitle.setText(comicsHandler.getTitleFromComic(position));
    }
}
}
