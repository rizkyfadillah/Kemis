package com.thousandsunny.kemis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by hallidafykzir on 10/24/2015.
 */
public class TabDataUmum extends Fragment {

    EditText namaKepala, noKk, noRt, alamat;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_data_umum, container, false);

        namaKepala = (EditText) v.findViewById(R.id.namaET);
        noKk = (EditText) v.findViewById(R.id.noKkET);
        alamat = (EditText) v.findViewById(R.id.alamatET);

        namaKepala.setText(getActivity().getIntent().getStringExtra("namaKepala"));
        noKk.setText(getActivity().getIntent().getStringExtra("noKk"));
        alamat.setText(getActivity().getIntent().getStringExtra("alamat"));

        return v;
    }
}
