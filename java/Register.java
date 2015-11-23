package com.register.afmelden.mohamed.afmelden;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Register extends Activity {
    private String ovnummer, wachtwoord, email, wachtwoord1;
    private String leerjaar;
    EditText ov_edit, ww_edit,ww_edit2, email_edit, leerjaar_edit;
    Button registerButton, loginButton;
    private String ErrMessage = "";
    Toast toast;
    private String Passwordhash = null;

    public static String MyPREFERENCES = "myprefs";
    public static String nameKey = "namekey";
    public static String passwordKey = "passwordkey";
    SharedPreferences sharedPreferences;
    public static String grade = "leerjaar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginButton = (Button) findViewById(R.id.buttonLogin);
        ov_edit = (EditText) findViewById(R.id.ovnummer);
        ww_edit = (EditText) findViewById(R.id.password);
        ww_edit2 = (EditText) findViewById(R.id.password1);
        email_edit = (EditText) findViewById(R.id.email);
        leerjaar_edit = (EditText) findViewById(R.id.leerjaar);
        registerButton = (Button) findViewById(R.id.register);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newUser();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ii = new Intent(Register.this, LoginService.class);
                // close all the activities from the stack
                ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // add new FLAG to Start a new Activity
                ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(ii);
                overridePendingTransition(R.anim.animation, R.anim.right_to_left);

               // finish();


            }
        });
    }

    private void newUser() {
        bCrypt bCryptPass = new bCrypt();

        ovnummer = ov_edit.getText().toString();
        wachtwoord = ww_edit.getText().toString();
        wachtwoord1 = ww_edit2.getText().toString();
        email = email_edit.getText().toString();
        leerjaar = leerjaar_edit.getText().toString();

        boolean is_go = false;
        if (!ovnummer.equals("") && !wachtwoord.equals("") && !wachtwoord1.equals("") && !email.equals(""))
            if (wachtwoord.length() >= 6)
                if (wachtwoord.equals(wachtwoord1)) {
                    is_go = true;
                    Passwordhash =  bCryptPass.bCrypt(wachtwoord);
                }
                else
                    ErrMessage = "passwords do not match";
            else
                ErrMessage = "password minimum length is 6 characters.";
        else
            ErrMessage = "please fill in all fields";

        if (is_go) {
            new AddUser(this,1).execute(ovnummer, Passwordhash, email, leerjaar);
            clearAll(ov_edit, ww_edit, email_edit, leerjaar_edit, ww_edit2);

            // creating login session;
            sharedPreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(nameKey, ovnummer);
            editor.putString(passwordKey, Passwordhash);
            editor.putString(grade, leerjaar);
            editor.commit();
            //login session created;

            Intent intent = new Intent(Register.this, LoginService.class);
            finish();
            startActivity(intent);
            overridePendingTransition(R.anim.animation, R.anim.right_to_left);
            ErrMessage = "account created";
            getToast(ErrMessage);
        } else {
            getToast(ErrMessage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    public Object getToast(String message){
        message = message;

        toast.makeText(Register.this, message, toast.LENGTH_SHORT).show();

        return toast;
    }
    public void clearAll(EditText ov1, EditText pass1, EditText email1, EditText leerjaar1, EditText pass2){
        ov1.setText("");
        pass1.setText("");
        email1.setText("");
        leerjaar1.setText("");
        pass2.setText("");
    }
}
