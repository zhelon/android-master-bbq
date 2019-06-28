package com.example.masterbbq;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static  App instance;

    public App(){
        super();
        instance = this;
    }

    public static App getApp(){
        return instance;
    }

    public static Context getContext(){
        return  instance;
    }

}
