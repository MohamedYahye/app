package com.register.afmelden.mohamed.afmelden;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed on 8-12-2014.
 */
public class loginUser extends AsyncTask<String, String, String>{
    private String Result;
    private String LOGIN_URL = "http://www.zilvermarkt.org/android/loginUser.php?";
    private String TAG_SUCCESS = "success";
    public String TAG_MESSAGE = "message";
    public boolean failure = true;
    public int Success;
    Context context1;
    ProgressDialog PD = null;
    JsonParser jsonParser = new JsonParser();


    // session values ////
    public static String nameKey = "namekey";
    public static String passwordKey = "passwordkey";

    protected void onPreExecute(){
        super.onPreExecute();
        PD = new ProgressDialog(context1);
        PD.setMessage("Logging in....");
        PD.setCancelable(false);
        PD.setIndeterminate(true);
        PD.show();
    }

    public boolean returnFailure(){
        return failure;
    }

    protected loginUser(Context context){
        this.context1 = context;

    }

    protected String doInBackground(String... arg0){

        String ovnummer =  arg0[0];
        String password =  arg0[1];

        try{
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("ovnummer", ovnummer));
            params.add(new BasicNameValuePair("wachtwoord", password));

            JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST", params);
            System.out.println("login attempt " + json.toString());

            Success = json.getInt(TAG_SUCCESS);

            if(Success == 1){
                System.out.println("success login " + json.toString());
                SharedPreferences pref = context1.getSharedPreferences(LoginService.MyPREFERENCES, 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(nameKey, ovnummer);
                editor.putString(passwordKey, password);
                editor.apply();


                failure = false;


            }else{
                failure = true;
                System.out.println(json.getString(TAG_MESSAGE));

            }

        }catch (Exception e){
            System.out.println("exception caught");
        }

        returnFailure();
        return Result;

    }

    protected void onPostExecute(String Result){
        if(returnFailure()){ // invalid data//
            //PD.dismiss();
            if(PD != null && (PD.isShowing()))
                PD.dismiss();

            Toast.makeText(context1, "invalid username or password", Toast.LENGTH_SHORT).show();
        }else{
            // way to start an activity in AsyncTask //
            context1.startActivity(new Intent(context1, Welcome.class));
            onCancelled();

        }

    }

    protected void onCancelled(){
        if(PD != null) {
          if(PD.isShowing()){
              PD.dismiss();
          }
        }

    }
}
