package com.example.instagramapp;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    // Initialize Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("NgIxfZhJF3ajHzKTKOVmlK1flDNEOhEK685uLjMI")
                .clientKey("X9MiiiIWqeW1Ix1WIXW7UmGPJfVFHZZdPBbJ7Wee")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
