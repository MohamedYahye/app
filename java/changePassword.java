package com.register.afmelden.mohamed.afmelden;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed on 22-4-2015.
 */
public class changePassword extends AsyncTask<String, String, String> {

    private boolean user_exists;
    private String url;
    private String result;

    Context context;

    changePassword(Context context1){
        this.context = context1;
    }


    @Override
    protected void onPreExecute(){
        Log.v("v", "Log here");
    }


    protected String doInBackground(String... param){
        String newPass = param[1];
        String _id = param[0];
        url = "http://www.zilvermarkt.org/android/changePassword.php";
        BasicNameValuePair id = new BasicNameValuePair("id", _id);
        BasicNameValuePair pass = new BasicNameValuePair("wachtwoord", newPass);


        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(id);
        nameValuePairs.add(pass);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

            try{
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs);
                httpPost.setEntity(urlEncodedFormEntity);

                try{
                    HttpResponse response = httpClient.execute(httpPost);
                    InputStream inputStream = response.getEntity().getContent();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String bufferedChunk = null;
                    while ((bufferedChunk = bufferedReader.readLine()) != null){
                        sb.append(bufferedChunk);
                    }
                    result = sb.toString();
                    return result;

                }catch (Exception e){
                    e.getMessage();
                }
            }catch (UnsupportedEncodingException use){
                use.getMessage();
            }
        return result;
    }


    @Override
    protected void onPostExecute(String re){
        super.onPostExecute(re);
        Log.v("V", re);

    }
}
