package com.example.jangid.androidutilsexample;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    public Retrofit retrofit;
    private OkHttpClient client;

    private OkHttpClient Client(){
        if(client==null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1,TimeUnit.MINUTES)
                    .build();
            return client;
        }
        return client;
    }

    public Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(Client())
                    .build();
        }
        return retrofit;
    }
}
