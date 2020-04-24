package com.example.msapproekt;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingBackend extends AsyncTask<Void,Void,String> {

    private static final String LOG_TAG=PingBackend.class.getSimpleName();

    public PingBackend() {

    }

    @Override
    protected String doInBackground(Void... voids) {
        /**try {
            //Thread.sleep(600000); // spie 10 minuti
        } catch (InterruptedException e) {
            e.printStackTrace();
        }**/
        return NetworkUtils.getBackendInfo(); //<-- ova mi vrakja json u string format
    }

    @Override
    protected void onPostExecute(String s) { //<-- vlezen parametar mi e neisparsiran json vo string format
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray Array = jsonObject.getJSONArray(s);

            for(int i=0;i<Array.length();i++)
            {
                   JSONObject currentItem = Array.getJSONObject(i);
                   Log.d(LOG_TAG,"json = "+currentItem.toString());
                   Log.d(LOG_TAG,"type = "+currentItem.getString("jobType"));
                   Log.d(LOG_TAG,"host = "+currentItem.getString("host"));
                   Log.d(LOG_TAG,"count = "+currentItem.getString("count"));
                   Log.d(LOG_TAG,"packetSize = "+currentItem.getString("packetSize"));
                   Log.d(LOG_TAG,"Period = "+currentItem.getString("jobPeriod"));
                   Log.d(LOG_TAG,"date = "+currentItem.getString("date"));



                String pingCmd = "ping  -c  " + currentItem.getString("count");
                pingCmd = pingCmd + " -s " +currentItem.getString("packetSize");
                pingCmd = pingCmd +" " + currentItem.getString("host");
                String pingResult = "";
                Runtime r = Runtime.getRuntime();
                Process p = r.exec(pingCmd);
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    pingResult += inputLine;
                }
                in.close();
                Log.d(LOG_TAG,"pingResult "+pingResult);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }
}
