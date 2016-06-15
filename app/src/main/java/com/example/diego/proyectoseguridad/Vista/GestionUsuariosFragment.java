package com.example.diego.proyectoseguridad.Vista;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diego.proyectoseguridad.R;


public class GestionUsuariosFragment extends Fragment {

    private View view;
    public GestionUsuariosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_gestion_usuarios, container, false);
        return view;
    }
}
