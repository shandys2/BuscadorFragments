package com.example.uni;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class AsyncTaskRunner extends AsyncTask<String, Void, List<Universidad>> {
    Context context;
    FragmentManager fragmentManager;
    String pais,nombre;
    View view;
    List<Universidad> lista;

    public AsyncTaskRunner(Context con,FragmentManager fm,View view){
        this.context=con;
        this.fragmentManager=fm;
        this.view=view;
    }


    @Override
    protected List<Universidad> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;
        StringBuffer buffer;
        this.pais=params[0];
        this.nombre=params[1];

        try {

            URL url = new URL("http://universities.hipolabs.com/search?country="+ params[0]+"&name="+params[1]+"");

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            InputStream inputStream = urlConnection.getInputStream();
             buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            forecastJsonStr = buffer.toString();
         ////////////////////////////////////////////////



            lista= new ArrayList<Universidad>(){};
            try {
                JSONArray jsonArray = new JSONArray(forecastJsonStr);
                for (int i = 0; i <jsonArray.length() ; i++) {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                        Universidad universidad = new Universidad();
                        universidad.setCountry(jsonObject.getString("country"));
                        universidad.setName(jsonObject.getString("name"));
                        universidad.setCodPais(jsonObject.getString("alpha_two_code"));
                        String[] webArray= {jsonObject.getString("web_pages")};
                        String webUrl= webArray[0].replace("]","").replace("[","") .replace("\\","").replace("\"","");
                        universidad.setWebPage(webUrl);
                        lista.add(universidad);

                        for (Universidad u:lista) {
                            Log.i("Nombre " , u.getName());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



            /////////////////////////////////////////


        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);

            return null;
        } finally{


            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }

    return lista;
    }


}


