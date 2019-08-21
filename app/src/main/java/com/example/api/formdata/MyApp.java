package com.example.api.formdata;

import android.app.Application;

class MyApp extends Application {
    public MyApp myApp;
    @Override
    public void onCreate() {
        super.onCreate();
        myApp=this;
    }
}
