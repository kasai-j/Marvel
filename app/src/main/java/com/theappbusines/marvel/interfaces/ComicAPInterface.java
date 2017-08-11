package com.theappbusines.marvel.interfaces;

import com.theappbusines.marvel.model.Comics;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Kasai Desktop on 10/08/2017.
 */

//get the format, formattype and limit.
public interface ComicAPInterface {
    @GET("comics?")
    Call<Comics> fetchComicList(@Query("format") String format, @Query("formatType") String comic, @Query("limit") int limit);
}
