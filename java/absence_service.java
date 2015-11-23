package com.register.afmelden.mohamed.afmelden;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed on 16-2-2015.
 */
public class absence_service extends AsyncTask<String, String, String>{

    private String result = "";
    Context context;
    Toast toast;
    ProgressDialog PD = null;

    protected absence_service(Context context1){
        this.context = context1;
    }

    protected void onPreExecute(){
        super.onPreExecute();

        PD = new ProgressDialog(context);
        PD.setMessage("Je wordt afgemeld.... please wait");
        PD.setCancelable(false);
        PD.setIndeterminate(true);

        PD.show();

    }


    protected String doInBackground(String... params){
        // url;
        String link = "http://www.zilvermarkt.org/android/insert_absence.php?";

        // parameters to be sent;
        String Param_id = params[0];
        String Param_absence = params[1];

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(link);
        // add the parameters
        BasicNameValuePair id = new BasicNameValuePair("user_id", Param_id);
        BasicNameValuePair absence = new BasicNameValuePair("reason_absence", Param_absence);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(id);
        nameValuePairs.add(absence);

        try{
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs);
            httpPost.setEntity(urlEncodedFormEntity);

            try{
                HttpResponse response = httpClient.execute(httpPost);
                InputStream inputStream = response.getEntity().getContent();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String bufferedStrChunk = null;

                while((bufferedStrChunk = bufferedReader.readLine()) != null){
                    sb.append(bufferedStrChunk);
                }
                result = sb.toString();
                return result;
            }catch (IOException io){
                io.printStackTrace();
            }
        }catch (UnsupportedEncodingException use){
            use.printStackTrace();
        }
        return null;
    }


    protected void onPostExecute(String result){
        super.onPostExecute(result);
        Log.v("V", "Result is: " + result);
        toast.makeText(context, "U bent afgemeldt..", Toast.LENGTH_LONG).show();
        PD.cancel();
        context.startActivity(new Intent(context, UserData.class));
    }



}
