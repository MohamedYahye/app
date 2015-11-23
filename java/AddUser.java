package com.register.afmelden.mohamed.afmelden;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by mohamed on 8-12-2014.
 */
public class AddUser extends AsyncTask<String, String, String>{

    Context context;
    String result;
    private int ByGetOrPost = 1;
    // flag 0  means get and flag 1 means post(by default it is get);
    public AddUser(Context context1, int flag){
        this.context = context1;
        this.ByGetOrPost = flag;
    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... arg0){
        if(ByGetOrPost == 1){
            try{
                String ov = (String)arg0[0];
                String password = (String)arg0[1];
                String email = (String)arg0[2];
                String leerjaar = (String)arg0[3];

                String link = "http://www.zilvermarkt.org/android/user.php?";

                String data  = URLEncoder.encode("ovnummer", "UTF-8")
                        + "=" + URLEncoder.encode(ov, "UTF-8");
                data += "&" + URLEncoder.encode("wachtwoord", "UTF-8")
                        + "=" + URLEncoder.encode(password, "UTF-8");
                data +="&" + URLEncoder.encode("email", "UTF-8")
                        + "="+URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode("leerjaar", "UTF-8")
                        +"="+URLEncoder.encode(leerjaar, "UTF-8");

                URL url = new URL(link);

                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter((connection.getOutputStream()));
                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                // read server response;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                    break;
                }
                result = sb.toString();
            }catch (Exception e){
                return new String("fail 1 " + e );
            }

        }

        return result;

    }

}
