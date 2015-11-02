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
public class TabKategori1 extends Fragment {

    EditText pendapatanET, listrikET, airET, bahanBakarET, konsumsiET, makanHarianET, bajuET;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_kategori_1, container, false);

        pendapatanET = (EditText) v.findViewById(R.id.pendapatanET);
        listrikET = (EditText) v.findViewById(R.id.listrikET);
        airET = (EditText) v.findViewById(R.id.airET);
        bahanBakarET = (EditText) v.findViewById(R.id.bahanBakarET);
        konsumsiET = (EditText) v.findViewById(R.id.konsumsiET);
        makanHarianET = (EditText) v.findViewById(R.id.makanHarianET);
        bajuET = (EditText) v.findViewById(R.id.bajuET);

        pendapatanET.setText(String.valueOf(getActivity().getIntent().getDoubleExtra("pendapatan", 0)));
        listrikET.setText(getActivity().getIntent().getIntExtra("listrik", 0));
        airET.setText(getActivity().getIntent().getIntExtra("air", 0));
        bahanBakarET.setText(getActivity().getIntent().getIntExtra("bahanBakarMasak", 0));

        return v;
    }
}
