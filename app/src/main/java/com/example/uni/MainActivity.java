package com.example.uni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Context context;
    Request r ;
    static Button botonBuscar, botonAtras;
    TextView textViewPais,textViewName;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        try {
            r = Request.getInstance(context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sharedPreferences =getSharedPreferences("datos",Context.MODE_PRIVATE);

        textViewPais = findViewById(R.id.textViewPais);
        textViewName = findViewById(R.id.textViewName);
        textViewPais.setText(sharedPreferences.getString("pais",""));
        textViewName.setText(sharedPreferences.getString("name",""));

        botonBuscar = findViewById(R.id.botonBuscar);
        botonAtras = findViewById(R.id.botonAtras);
        botonAtras.setVisibility(View.GONE);

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pais="";
                String nombre="";
                sharedPreferences =getSharedPreferences("datos",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                pais= String.valueOf(textViewPais.getText());
                nombre= String.valueOf(textViewName.getText());

                editor.putString("pais",pais);
                editor.putString("name",nombre);
                editor.commit();
                Bundle bundle = new Bundle();
                bundle.putString("pais", pais);
                bundle.putString("nombre", nombre);


                ItemFragment fragment =new ItemFragment(context,botonAtras);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();

            }
        });

        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("pais", sharedPreferences.getString("pais",""));
                bundle.putString("nombre", sharedPreferences.getString("name",""));
                ItemFragment fragment =new ItemFragment(context,botonAtras);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();
                botonAtras.setVisibility(View.GONE);

            }
        });
    }
}