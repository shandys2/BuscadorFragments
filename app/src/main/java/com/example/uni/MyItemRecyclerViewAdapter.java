package com.example.uni;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.uni.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.uni.databinding.FragmentItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Universidad> mValues;
    FragmentManager fragmentManage;
    Button botonAtras,botonBuscar;

    public MyItemRecyclerViewAdapter(List<Universidad> items ,FragmentManager fragmentManager, Button botonAtras,Button botonBuscar) {
        mValues = items;
        this.fragmentManage=fragmentManager;
        this.botonAtras=botonAtras;
        this.botonBuscar=botonBuscar;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.mIdView.setTooltipText(mValues.get(position).getWebPage()); //guardamos la url en el tooltipText del elemento
        }
        holder.mIdView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            //Al hacer clik en un elemento de la lista es cuando se activa esta funcion
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Log.i("RecliclerViewAdapter", (String) v.getTooltipText());
                    Bundle bundle = new Bundle();
                    bundle.putString("url",(String) v.getTooltipText()); // le pasamos al webfragment la url que hemos guardado en el tooltipText del elemento previamente
                    WebFragment webFragment= new WebFragment();
                    webFragment.setArguments(bundle);

                    FragmentManager fragmentManager = fragmentManage;
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, webFragment);
                    fragmentTransaction.commit();
                    botonAtras.setVisibility(View.VISIBLE);
                    botonBuscar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public Universidad mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemName;

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}