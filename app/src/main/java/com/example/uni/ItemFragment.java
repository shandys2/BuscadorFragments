package com.example.uni;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.uni.placeholder.PlaceholderContent;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    static Context context;
    String pais="";
    String nombre="";
    static FragmentManager fm;
    List<Universidad> uList;
     Button botonAtras,botonBuscar;

    public ItemFragment(Context context , Button botonAtras, Button botonBuscar) {

        this.context=context;
        this.botonAtras=botonAtras;
        this.botonBuscar=botonBuscar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm= getParentFragmentManager();
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            pais = bundle.getString("pais");
            nombre = bundle.getString("nombre");
        }

        AsyncTaskRunner asyncTask = new AsyncTaskRunner(context,fm,view);
        try {

             uList= asyncTask.execute(pais,nombre).get();  // EJECUTAMOS EL ASYNTASK Y RECOGEMOS LA LISTA
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(uList,fm,botonAtras,botonBuscar));
        }

        return view;

    }
}