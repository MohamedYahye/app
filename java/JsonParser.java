package com.register.afmelden.mohamed.afmelden;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.List;

/**
 * Created by mohamed on 9-12-2014.
 */
public class JsonParser {

    InputStream is = null;
    JSONObject jsonObject;
    String json = "";


    public JsonParser(){
        // default no argument constructor for json parser class;
    }

    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params){
        // Make HTTP request
        try{
            // cehck requesting method;
            if(method.equals("POST")){
                // now default httclient object;
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();



            }else if(method.equals("GET")){
                // request method is get;

                DefaultHttpClient dclient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "UTF-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse response = dclient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            }
        }catch (Exception e2){
            e2.printStackTrace();
        }
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String Linet = null;

            while((Linet = reader.readLine()) != null){
                sb.append(Linet + "\n");
                json = sb.toString().substring(0, sb.toString().length() -1);
                break;
            }
            is.close();




        }catch (Exception e3){
            e3.printStackTrace();
        }

        try{
            // parse the json;

            jsonObject = new JSONObject(json);
        }catch (Exception e4){
            e4.printStackTrace();
        }


        return jsonObject;

    }

    public JSONObject getJsonFromUrl(String url){
        try{
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse response = client.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            is = httpEntity.getContent();

        }catch (Exception e){
            System.out.println("error1: " + e);
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));

            StringBuilder sb = new StringBuilder();
            String StringLine = null;
            while((StringLine = reader.readLine()) != null){
                sb.append(StringLine + "\n");
            }
            is.close();
            json = sb.toString();
        }catch (Exception e1){
            e1.printStackTrace();
        }

        try{
            jsonObject = new JSONObject(json);
        }catch (Exception e){

        }

        return jsonObject;
    }
}
