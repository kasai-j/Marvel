package com.theappbusines.marvel.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;


import com.theappbusines.marvel.Adapters.RecyclerViewAdapter;
import com.theappbusines.marvel.R;
import com.theappbusines.marvel.ComicAPIclient;
import com.theappbusines.marvel.interfaces.ComicAPInterface;
import com.theappbusines.marvel.model.Comics;
import com.theappbusines.marvel.model.Result;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity representing a list of MarvelComics.
 * Todo: Seperate this with a controller.
 */
public class ComicListActivity extends AppCompatActivity {


    private final String TAG = ComicListActivity.class.getSimpleName();
    private boolean mTwoPane;
    private final String FORMAT = "comic";
    private final String FORMATTYPE = "comic";
    private final int LIMIT = 100;
    private ComicAPInterface comicAPIInterface;
    private Comics comicsData;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marvelcomic_list);
        setupUI();
        fetchComics();
    }

    private void fetchComics() {
        comicAPIInterface = ComicAPIclient.getClient().create(ComicAPInterface.class);
        Call<Comics> comicDataCall = comicAPIInterface.fetchComicList(FORMAT, FORMATTYPE, LIMIT);
        comicDataCall.enqueue(new Callback<Comics>() {
            @Override
            public void onResponse(Call<Comics> call, Response<Comics> response) {
                comicsData = response.body();
                //Log.d(TAG, "onResponse: " + comicsData.toString());
                //RecyclerView
                ArrayList<Result> comics = (ArrayList<Result>) comicsData.getData().getResults();
                Log.e(TAG, "onResponse: " + comics.size());
                mAdapter = new RecyclerViewAdapter(comics);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<Comics> call, Throwable t) {
                call.cancel();
                Log.d("Call failed", t.getMessage());
                Toast.makeText(ComicListActivity.this , "Sorry we were unable to retrieve comics. Are you offline?", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        assert mRecyclerView != null;
        setupRecyclerView(mRecyclerView);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

    }

}
