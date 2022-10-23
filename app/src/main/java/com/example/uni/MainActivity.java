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
    Button botonBuscar, botonAtras;
    TextView textViewPais,textViewName;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        sharedPreferences =getSharedPreferences("datos",Context.MODE_PRIVATE);
        botonBuscar = findViewById(R.id.botonBuscar);
        botonAtras = findViewById(R.id.botonAtras);
        botonAtras.setVisibility(View.GONE); // hasta que no haya web visible no hay boton atras visible

        textViewPais = findViewById(R.id.textViewPais);
        textViewName = findViewById(R.id.textViewName);
        //Al iniciar la app muestro los ultimos valores guardados en el objeto sharedPreferences, si no hay nada pues cadenas vacias.
        textViewPais.setText(sharedPreferences.getString("pais",""));
        textViewName.setText(sharedPreferences.getString("name",""));


        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pais="";
                String nombre="";
                sharedPreferences =getSharedPreferences("datos",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                pais= String.valueOf(textViewPais.getText());
                nombre= String.valueOf(textViewName.getText());
                //guardamos los valores de busqueda antes de activar el fragmento
                editor.putString("pais",pais);
                editor.putString("name",nombre);
                editor.commit();
                Bundle bundle = new Bundle();
                bundle.putString("pais", pais);
                bundle.putString("nombre", nombre);

                ItemFragment fragment =new ItemFragment(context,botonAtras,botonBuscar); // Le paso los botones para poder configurar la visibilidad desde otro fragmento
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();

            }
        });

        //Este boton aparece solo cuando el fragment de la WebView está visible
        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cuando salgamos de la webView mostramos de nuevo la lista anterior. con la ultima busqueda y con los textViews como estaban
                Bundle bundle = new Bundle();
                bundle.putString("pais", sharedPreferences.getString("pais",""));
                bundle.putString("nombre", sharedPreferences.getString("name",""));
                ItemFragment fragment =new ItemFragment(context,botonAtras,botonBuscar);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();
                botonAtras.setVisibility(View.GONE);
                botonBuscar.setVisibility(View.VISIBLE);

            }
        });
    }
}