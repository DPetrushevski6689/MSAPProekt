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

    private static String depend;

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
            //JSONObject jsonObject = new JSONObject(s);
            JSONArray Array = new JSONArray(s);

            for(int i=0;i<Array.length();i++)
            {

                   JSONObject currentItem = Array.getJSONObject(i);

                   if(currentItem.getString("jobType").contains("PING"))
                   {
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
                       Service.pingResult = pingResult;
                   }
                   else if(currentItem.getString("jobType").contains("STAT"))
                   {
                       String returnString = null;
                       String statResult = "";

                       Process pstat = Runtime.getRuntime().exec("top -n 1");
                       BufferedReader in = new BufferedReader(new InputStreamReader(pstat.getInputStream()));
                       String inputLine;

                       while (returnString==null||returnString.contentEquals("")){
                           returnString = in.readLine();
                       }
                       statResult += returnString +",";
                       while ((inputLine = in.readLine()) != null){
                           inputLine += ";";
                           statResult += inputLine;
                       }
                       in.close();
                       if(pstat!=null){
                           pstat.getOutputStream().close();
                           pstat.getInputStream().close();
                           pstat.getErrorStream().close();
                       }
                       Log.i("STAT TYPE","statResult = "+statResult);
                   }
                   /**
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
                Service.pingResult = pingResult;**/
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }
}
