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


public class newPassword extends Activity {

    EditText pass,sPass;
    String _pass,secondPass;
    Button bSend;
    String id;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        pass = (EditText) findViewById(R.id.pass);
        sPass = (EditText) findViewById(R.id.pass_second);
        bSend = (Button) findViewById(R.id.button_pass);

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                _pass = pass.getText().toString();
                secondPass = sPass.getText().toString();

                changePass();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_password, menu);
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



    public void setPass(String p){
        this._pass = p;
    }
    public void setSecondPass(String s){
        this.secondPass = s;
    }

    public String get_pass(){
        return this._pass;
    }
    public String getSecondPass(){
        return this.secondPass;
    }

    public void changePass() {
        bCrypt bCrypt = new bCrypt();
        String hashedPass = null;


        if((!get_pass().equals("")) || (!getSecondPass().equals(""))){
            if(get_pass().length() >= 6){
                if(get_pass().equals(getSecondPass())){
                    hashedPass = bCrypt.bCrypt(get_pass());
                    new changePassword(this).execute(get_Pref_id(), hashedPass);

                    Intent ii = new Intent(this, LoginService.class);

                    // close all the activities from the stack
                    ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // add new FLAG to Start a new Activity
                    ii.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(ii);
                    overridePendingTransition(R.anim.animation, R.anim.right_to_left);

                    Toast toast = null;

                    toast.makeText(this,"Password successfully changed", Toast.LENGTH_SHORT).show();


                }
            }
        }
    }

    public String get_Pref_id(){
        sharedPreferences = getSharedPreferences(VerifyUser.PREFERENCE, 0);
        id = sharedPreferences.getString(VerifyUser.USER_ID, "");
        System.out.println("id " + id);
        return id;
    }

    //public void clear_prefrence();
}
