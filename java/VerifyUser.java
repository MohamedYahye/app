package com.register.afmelden.mohamed.afmelden;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class VerifyUser extends Activity {

    private EditText ov, em;
    TextView tv_status;
    Button verify_button;
    private String _ov,_em;
    String result;
    String status_message;
    private boolean user_exists;
    private String url;




    public static String PREFERENCE = "MY_PREF";
    public static String USER_ID = "USER_ID";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user);


        ov = (EditText) findViewById(R.id.ov_Num);
        em = (EditText) findViewById(R.id.email_tv);
        tv_status = (TextView) findViewById(R.id.user_verified);

        verify_button = (Button) findViewById(R.id.ver_button);

        verify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOv(ov.getText().toString());
                setEm(em.getText().toString());

                url = "http://zilvermarkt.org/android/verify.php?username=" + get_ov() + "&email=" + get_em() + "";
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        setResult(getOnline(url));
                        System.out.println("Actual result from web : " + getResult() + "\n");
                        user_verified();

                    }

                }).start();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_verify_user, menu);
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

    public void setOv(String ov){
        this._ov = ov;
    }

    public String get_ov(){
        return this._ov;
    }

    public void setEm(String em){
        this._em = em;
    }

    public String get_em(){
        return this._em;
    }

    public void setUser_exists(boolean u){
        this.user_exists = u;
    }

    public boolean does_user_exist(){
        return this.user_exists;
    }

    public void setResult(String r){
        this.result = r;
    }

    public String getResult(){
        return this.result;
    }

    public void setStatus_message(String status){
        this.status_message = status;
    }

    public String getStatus_message(){
        return this.status_message;
    }

    private boolean user_verified(){
        String r  = getResult();
        boolean U = user_exists;
        setUser_exists(U);




        String test = "{\"user\":\"false\"}";
        if((!get_em().equals("")) && (!get_ov().equals(""))){
            if(test.equals(r)) {
                U = false;


            } else {
                Intent ii = new Intent(this, newPassword.class);
                startActivity(ii);
                save_id(get_ov());
                U = true;

            }
        }


            if(!U){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_status.setText("Status: user not found!");
                        tv_status.setTextColor(Color.parseColor("#FF0000"));

                    }
                });
            }
        return U;
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

    public void save_id(String id){
        sharedPreferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, id);
        editor.apply();

    }




}
