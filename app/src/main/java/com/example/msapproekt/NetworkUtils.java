package com.example.msapproekt;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingResourceException;

public class NetworkUtils {

    public NetworkUtils() {

    }

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    static String getBackendInfo() //<--- funkcija shto kje mi go vrakja json stringot neisparsiran
    {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JSONString = null;
        try{

            Uri builtUri = Uri.parse("http://10.0.2.2:5000/getjobs/emulator")
                    .buildUpon()
                    .build();

            Log.d(LOG_TAG,"Se konektira do http://10.0.2.2:5000/getjobs/emulator"); //<-- promena na url za emulator

            URL requestUrl = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) requestUrl.openConnection(); // <-- otvorena konekcija
            urlConnection.setRequestMethod("GET");
            urlConnection.connect(); //<--- konekcija napravena do backendot za ping
            Log.d(LOG_TAG,"Konektiran");

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null)
            {
                buffer.append(line + "\n");
            }
            JSONString=buffer.toString();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG,"JSON e prevzemen");
        return JSONString;
    }
}
