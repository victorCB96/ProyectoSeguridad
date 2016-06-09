package com.example.diego.proyectoseguridad.Vista;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diego.proyectoseguridad.R;

public class FragmentPeliculas extends Fragment {

    private RecyclerView recyclerPeliculas;
    private AdaptadorPeliculas adaptadorPeliculas;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_pelicula_fragment, container, false);

        recyclerPeliculas = (RecyclerView) view.findViewById(R.id.recycler_peliculas);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerPeliculas.setLayoutManager(layoutManager);

        adaptadorPeliculas = new AdaptadorPeliculas();
        recyclerPeliculas.setAdapter(adaptadorPeliculas);


        return view;
    }
}
