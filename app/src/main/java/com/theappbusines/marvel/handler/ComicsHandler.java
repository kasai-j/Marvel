package com.theappbusines.marvel.handler;

import com.theappbusines.marvel.model.Comics;
import com.theappbusines.marvel.model.Data;
import com.theappbusines.marvel.model.Result;

/**
 * Created by Kasai Desktop on 10/08/2017.
 * Due to the complexity of the JSON data this will handle finding specific information
 * required.
 */

public class ComicsHandler {
    private Result comics;

    public ComicsHandler(Result comics){
       this.comics = comics;
    }

    public String getImageFromComic(int position){

        if (comics.getThumbnail() != null){
            return comics.getThumbnail().getPath() + comics.getThumbnail().getExtension();
        }else{
            return comics.getImages().get(0).getPath() + comics.getImages().get(0).getExtension();
        }
    }

    public String getTitleFromComic(int position){
            return comics.getTitle();
    }



}
