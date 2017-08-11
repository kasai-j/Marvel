package com.theappbusines.marvel;

import com.theappbusines.marvel.utils.Utils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kasai Desktop on 10/08/2017.
 * Connects with retrofit and add the various parameters via the interceptor.
 *
 * Note: Private and public keys have been stored here togeother. With more time the private
 * key would be kept on a seperate server for maximum security. We could also use the keystore
 * to store the Apikey. I have called it info rather than an easily understandable name deliberately
 */

public class ComicAPIclient {
    private static Retrofit retrofit = null;
    private static final String BASEURL = "http://gateway.marvel.com/v1/public/";
    static final String INFO = "5de1fabcda2ea08912bd8b09bca4321f5056365554306733de0f5cd1418aa05a85fa062a";
    static final String INFO_K = "54306733de0f5cd1418aa05a85fa062a";

    public static Retrofit getClient(){
        //Checking responses
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(hashTSInterceptor())
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    private static Interceptor hashTSInterceptor() {
        final String currentTime = Utils.currentTimeAsString();
        Interceptor mHashTSInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //get the url from the chain. append the query parameter.
                HttpUrl originalUrl = chain.request().url();
                HttpUrl url = originalUrl.newBuilder()
                        .addQueryParameter("apikey",INFO_K)
                        .addQueryParameter("hash", Utils.produceMD5(currentTime + INFO))
                        .addQueryParameter("ts", currentTime)
                        .build();
                //make a new request based on the added headers.
                Request newRequest = chain.request().newBuilder().url(url).build();
                return chain.proceed(newRequest);
            }
        };
        return mHashTSInterceptor;
    }



}
