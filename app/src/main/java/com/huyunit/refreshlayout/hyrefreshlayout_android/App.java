package com.huyunit.refreshlayout.hyrefreshlayout_android;

import android.app.Application;

import com.huyunit.refreshlayout.hyrefreshlayout_android.rest.RestClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author: bobo
 * create time: 2016/10/18 15:25
 * Email: jqbo84@163.com
 */
public class App extends Application {
    private static App sInstance;
    private RestClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        client = new Retrofit.Builder()
                .baseUrl("http://7xk9dj.com1.z0.glb.clouddn.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RestClient.class);
    }

    public static App getInstance() {
        return sInstance;
    }

    public RestClient getClient() {
        return client;
    }
}
