package com.goknur.kelimelik.app;

import android.app.Application;
import android.content.Context;

import com.goknur.kelimelik.rest.service.RestClient;

/**
 * Created by goknur on 20.07.2017.
 */

public class App extends Application {

    public static RestClient restClient;

    @Override
    public void onCreate() {
        super.onCreate();
        restClient = new RestClient();
    }

    public static RestClient getRestClient() {
        return restClient;
    }
}
