package com.example.diego.proyectoseguridad.Vista;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diego.proyectoseguridad.Modelo.Clasificacion;
import com.example.diego.proyectoseguridad.Modelo.clsManejoPeliculas;
import com.example.diego.proyectoseguridad.R;

public class FragmentPeliculas extends Fragment {

    private RecyclerView recyclerPeliculas;
    private AdaptadorPeliculas adaptadorPeliculas;


    public FragmentPeliculas() {
    }

    public static FragmentPeliculas newInstace(Clasificacion clasificacion){
        FragmentPeliculas fragmentPeliculas = new FragmentPeliculas();
        Bundle args = new Bundle();
        args.putParcelable(FragmentClasificaciones.CLASIFICACIONES, clasificacion);
        fragmentPeliculas.setArguments(args);
        return fragmentPeliculas;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_pelicula_fragment, container, false);
        Clasificacion clasificacion = getArguments().getParcelable(FragmentClasificaciones.CLASIFICACIONES);

        recyclerPeliculas = (RecyclerView) view.findViewById(R.id.recycler_peliculas);
        recyclerPeliculas.setHasFixedSize(true);

        boolean isLand = getActivity().getResources().getBoolean(R.bool.is_landscape);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), isLand ? 3: 2);
        ;



        recyclerPeliculas.setLayoutManager(layoutManager);
        clsManejoPeliculas manejoPeliculas = new clsManejoPeliculas(getActivity());
        adaptadorPeliculas = new AdaptadorPeliculas(manejoPeliculas.getPeliculasClasificacion(clasificacion));
        recyclerPeliculas.setAdapter(adaptadorPeliculas);

        return view;
    }

}
