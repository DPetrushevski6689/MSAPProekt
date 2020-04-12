package com.example.msapproekt;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class ProcessMainClass {
    public static final String TAG = ProcessMainClass.class.getSimpleName(); //tag za logovi

    public static Intent serviceIntent = null;

    public ProcessMainClass() { //konstruktor
    }

    public void launchService(Context context) {
        if (context == null) {
            return;
        }
        serviceIntent = new Intent(context,Service.class); //intent so koj se pokreva servisot (klasichen pristap)

        // depending on the version of Android we eitehr launch the simple service (version<O)
        // or we start a foreground service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent); //ova e radi restrikcii za novi verzii za android
        } else {
            context.startService(serviceIntent);
        }
        Log.d(TAG, "ProcessMainClass: start service go!!!!");
    }

}
