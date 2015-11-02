package com.thousandsunny.kemis;

/**
 * Created by hallidafykzir on 10/24/2015.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thousandsunny.kemis.activity.DetailKeluargaActivity;
import com.thousandsunny.kemis.model.Keluarga;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab1 extends Fragment {

    RecyclerView recyclerView;

    List<Keluarga> keluargaList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_1, container, false);

        keluargaList = new ArrayList<Keluarga>();
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        WebServiceHelper.getInstance().getServices().getAllKeluarga(new Callback<List<Keluarga>>() {
            @Override
            public void success(List<Keluarga> keluargas, Response response) {
                System.out.println("masuk sukses");
                for (int i = 0; i < keluargas.size(); i++) {
                    keluargaList.add(keluargas.get(i));
                }
                recyclerView.setAdapter(new KeluargaAdapter(keluargaList, getActivity()));
                recyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent i = new Intent(getActivity(), DetailKeluargaActivity.class);
                                i.putExtra("alamat", keluargaList.get(position).getAlamat());
                                i.putExtra("air", keluargaList.get(position).getAir());
                                i.putExtra("bahanBakarMasak", keluargaList.get(position).getBahanBakarMasak());
                                i.putExtra("baju", keluargaList.get(position).getBaju());
                                i.putExtra("bayarObat", keluargaList.get(position).getBayarObat());
                                i.putExtra("dagingSusu", keluargaList.get(position).getDagingSusu());
                                i.putExtra("fotoKeluarga", keluargaList.get(position).getFotoKeluarga());
                                i.putExtra("jenisDinding", keluargaList.get(position).getJenisDinding());
                                i.putExtra("jenisLantai", keluargaList.get(position).getJenisLantai());
                                i.putExtra("latitude", keluargaList.get(position).getLatitude());
                                i.putExtra("longitude", keluargaList.get(position).getLongitude());
                                i.putExtra("namaKepala", keluargaList.get(position).getNamaKepala());
                                i.putExtra("noKk", keluargaList.get(position).getNoKk());
                                i.putExtra("noRt", keluargaList.get(position).getNoRt());
                                i.putExtra("luasLantai", keluargaList.get(position).getLuasLantai());
                                i.putExtra("pendapatan", keluargaList.get(position).getPendapatan());
                                i.putExtra("pendidikan", keluargaList.get(position).getPendidikan());
                                i.putExtra("listrik", keluargaList.get(position).getListrik());
                                startActivity(i);
                            }
                        })
                );
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
            }
        });

        return v;
    }
}