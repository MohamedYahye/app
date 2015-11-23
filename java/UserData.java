package com.register.afmelden.mohamed.afmelden;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class UserData extends Activity {
    public String str;
    public TextView view;
    public String uId;
    public String feed = "http://www.zilvermarkt.org/android/data.php?Student_id=";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        view = (TextView) findViewById(R.id.raw_data);
        new Thread(new Runnable() {
            @Override
            public void run() {
                str = getOnline(feed + uId);
                Log.v("v", "feed is : " + feed + uId);
                System.out.println(str);

            }

        }).start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getOnline(String url){
        URLConnection feedUrl;


        try{
            feedUrl = new URL(url).openConnection();
            InputStream in = feedUrl.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null){
                sb.append(line + "");
            }
            in.close();
            return sb.toString();
        }catch (Exception e){
            e.getMessage();
        }

        return null;
    }


    @Override
    protected void onResume(){
        super.onResume();
        sharedPreferences = getSharedPreferences(LoginService.MyPREFERENCES, Context.MODE_PRIVATE);
        uId = sharedPreferences.getString(LoginService.nameKey, "1940101910");
        System.out.println( "user id: " + uId);

        view.setText(str);

    }
}
