package com.pupasoft.nsc.airmessage;

import android.app.Application;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

/**
 * Created by wong_ on 26-Jan-17.
 */
public class AirMessageApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize here
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
