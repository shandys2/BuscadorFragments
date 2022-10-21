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
    Request r ;
    String pais="";
    String nombre="";
    String url;
    static FragmentManager fm;
    List<Universidad> uList;

    public ItemFragment(Context context ) {
        this.context=context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm= getParentFragmentManager();
        try {
            r = Request.getInstance(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(uList,fm));
        }


     /*   Bundle bundle = new Bundle();
        bundle.putString("pais", pais);
        bundle.putString("nombre", nombre);
        ItemFragment fragment = new ItemFragment(context);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
        Log.i("json",forecastJsonStr);
*/
        return view;

        //ponemos un contador a falta de hacerla sincrona la peticion
    /*    new CountDownTimer(800, 800) {
            @Override
            public void onTick(long l) {
                try {
                    r.nuevaBusqueda(pais , nombre);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onFinish() {

                List<Universidad> lista = r.getListaUniversidades();

                if (lista == null || lista.size() == 0) {
                    lista = new ArrayList<>();
                    lista.add(new Universidad("No hay resultados"));
                }

                for (Universidad u : lista) {
                    Log.i("MAIN", u.getName());
                }

                // Set the adapter
                if (view instanceof RecyclerView) {
                    Context context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view;
                    if (mColumnCount <= 1) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                    }
                    recyclerView.setAdapter(new MyItemRecyclerViewAdapter(lista,fm));
                }
            }
        }.start();*/

    }
}