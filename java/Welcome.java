package com.register.afmelden.mohamed.afmelden;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;


public class Welcome extends Activity {
    // variables
    public SharedPreferences sharedPreferences;
    Student student;
    EditText userId, dateInput, absence;
    TextView welcome;
    Button afmelden;
    String _student_id,_absence,_date;
    Toast toast;
    String ErrMessage = "";
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        afmelden = (Button)findViewById(R.id.afmelden_button);

        final Button logout = (Button) findViewById(R.id.logout);
        welcome = (TextView) findViewById(R.id.username);
        userId = (EditText) findViewById(R.id.userId);
        dateInput = (EditText) findViewById(R.id.date);
        absence = (EditText)findViewById(R.id.textarea);
        //final EditText leerjaar = (EditText) findViewById(R.id.leerjaar);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut(v);
            }
        });

        afmelden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendAbsenceData();

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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
            String url = "http://www.slamfm.nl/live/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logOut(View view){
        SharedPreferences sharedPreferences = getSharedPreferences(LoginService.MyPREFERENCES, 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.apply();
        Intent ii = new Intent(this, LoginService.class);
        ii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ii);
        overridePendingTransition(R.anim.animation, R.anim.right_to_left);
        exit(view);
    }

    public void exit(View view){
        moveTaskToBack(false);
        Welcome.this.finish();
    }

    private String getDate(){
        String Date;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Calendar cal = Calendar.getInstance();
        Date = dateFormat.format(cal.getTime());

        return Date;
    }

    public void getStudent(){

        sharedPreferences = getSharedPreferences(LoginService.MyPREFERENCES,0 );
        _student_id = sharedPreferences.getString(loginUser.nameKey, "nothing found!!");
        welcome.setText("welcome : " + _student_id);
        userId.setText("Ov-Nummer is: " + _student_id);

        _date = getDate();
        dateInput.setText(_date);


    }


    public boolean is_absence(){
        boolean is_go;
        _absence = absence.getText().toString();

        if(_absence.length() > 0){
            is_go = true;
        }else{
            is_go = false;
        }

        return is_go;
    }

    public String get_absence(){
       return _absence;
    }

    @Override
    protected void onResume(){
        getStudent();

        super.onResume();
    }

    public Object getToast(String message){
        this.ErrMessage = message;

        toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        return toast;
    }


    public void sendAbsenceData(){
        if(is_absence()){
            student = new Student(_student_id, get_absence(), _date);



            new absence_service(this).execute(_student_id, get_absence());

            System.out.println("done");
        }else{
            getToast("please fill in all fields");
        }
    }


}
