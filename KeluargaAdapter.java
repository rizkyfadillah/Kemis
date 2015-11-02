package com.thousandsunny.kemis;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thousandsunny.kemis.model.Keluarga;

import java.util.List;

/**
 * Created by hallidafykzir on 10/24/2015.
 */
public class KeluargaAdapter extends RecyclerView.Adapter<KeluargaAdapter.KeluargaViewHolder> {

    List<Keluarga> keluargaList;
    Context context;

    public KeluargaAdapter(List<Keluarga> keluargaList, Context context) {
        this.keluargaList = keluargaList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return keluargaList.size();
    }

    public static class KeluargaViewHolder extends RecyclerView.ViewHolder {
        TextView vName;
        TextView vAlamat;
        ImageView vFoto;

        public KeluargaViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.nama);
            vAlamat = (TextView) v.findViewById(R.id.alamat);
            vFoto = (ImageView) v.findViewById(R.id.foto);
        }
    }

    @Override
    public KeluargaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.recyclerview_layout, parent, false);

        return new KeluargaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KeluargaViewHolder holder, int position) {
        Keluarga klrga = keluargaList.get(position);

        Typeface typefaceBold = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface typefaceLight = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        Typeface typefaceMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");

        holder.vName.setTypeface(typefaceBold);
        holder.vAlamat.setTypeface(typefaceLight);

        holder.vName.setText(klrga.getNamaKepala());
        holder.vAlamat.setText(klrga.getAlamat());
        Picasso.with(context).load("http://36.86.177.169/kemis/public/" + klrga.getFotoKeluarga()).into(holder.vFoto);

    }

}
