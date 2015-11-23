package com.register.afmelden.mohamed.afmelden;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


public class LoginService extends Activity{

    private String ovnummer,password;
    private Toast toast;
    private String passwordhash = null;
    private String Errmessage = "";
    private Context context = this;

    TextView tv_pass;

    // shared preferences new;
    public static String MyPREFERENCES = "myprefs";
    public static String nameKey = "namekey";
    public static String passwordKey = "passwordkey";
    SharedPreferences sharedPreferences;
    private String message;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_service);

        final EditText ov_edit = (EditText) findViewById(R.id.lovnum);
        final EditText pass_edit = (EditText) findViewById(R.id.lpassword);
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        //final Button registerButton = (Button) findViewById(R.id.buttonRegister);

        tv_pass = (TextView) findViewById(R.id.forgot_pass);
        tv_pass.setClickable(true);     // change pass button;


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ovnummer = ov_edit.getText().toString();
                password = pass_edit.getText().toString();

                LoginUser();
            }
        });


      tv_pass.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              toResetPassword();
          }
      });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    protected void LoginUser(){
        bCrypt passhash = new bCrypt();
        boolean is_go = false;
        if(!ovnummer.equals("") && !password.equals("")){
           passwordhash = passhash.bCrypt(password);

           is_go = true;
        }else{
            is_go = false;
            Errmessage = "Please fill in all fields";

        }

        if (is_go){
            new loginUser(context).execute(ovnummer, passwordhash);
            //createSession();


        }else{
            getToast(Errmessage);
        }
    }

    public Object getToast(String message){
        this.message = message;

        toast.makeText(context, message, toast.LENGTH_SHORT).show();

        return toast;
    }

    @Override
    protected void onResume(){

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(nameKey)){
            if(sharedPreferences.contains(passwordKey)){

               Intent ii = new Intent(context, Welcome.class);
               startActivity(ii);
               overridePendingTransition(R.anim.animation, R.anim.right_to_left);

            }
        }
        super.onResume();
    }


    public void toResetPassword(){

        Intent ii = new Intent(this, VerifyUser.class);

        ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ii);
        overridePendingTransition(R.anim.animation, R.anim.right_to_left);
    }

}