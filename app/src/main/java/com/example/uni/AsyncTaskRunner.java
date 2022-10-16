package com.example.uni;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.List;


public class AsyncTaskRunner extends AsyncTask<String, Universidad, List<Universidad>> {
    Request request = null;
    public AsyncTaskRunner(Context context){

        try {
            request = Request.getInstance(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<Universidad> doInBackground(String... params) {

        try {
            request.nuevaBusqueda(params[0],params[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return request.getListaUniversidades();
    }

    @Override
    protected void onPostExecute(List<Universidad> lista){
        for (Universidad u :lista ) {
           Log.i("ASYNCTASK", u.getName());
        }

    }
}
