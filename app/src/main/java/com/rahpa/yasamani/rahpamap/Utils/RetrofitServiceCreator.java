package com.rahpa.yasamani.rahpamap.Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Saeed on 4/4/2018.
 */

public class RetrofitServiceCreator {
    public static  <S> S CreateService(Class<S> service){

        //create new okhttp logging interceptor instance for monitoring data transition by okhttp client:
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        //create new okhttp client
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();

        //create new retrofit instance:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //pass argument to retrofit instance and return result:
        return retrofit.create(service);
    }
}
