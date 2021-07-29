package com.sakina.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class StarterApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        //local datastore
        Parse.enableLocalDatastore(this);

        //initializing the connection
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("")
                .clientKey("")
                .server("")
                .build()



        );

        // ParseUser.enableAutomaticUser();
        ParseACL defaultACL =new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);

        ParseACL.setDefaultACL(defaultACL,true);




    }
}