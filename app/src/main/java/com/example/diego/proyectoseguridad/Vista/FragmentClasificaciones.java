package com.example.diego.proyectoseguridad.Vista;


import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diego.proyectoseguridad.Modelo.Clasificacion;
import com.example.diego.proyectoseguridad.Modelo.Usuario;
import com.example.diego.proyectoseguridad.R;
import com.example.diego.proyectoseguridad.Modelo.clsManejadorVentanas;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentClasificaciones extends Fragment {

    public static final String CLASIFICACIONES = "CLASIFICACIONES";
    private AppBarLayout appBarLayout;
    private TabLayout pestañas;
    private ViewPager viewPager;
    private ArrayList<Clasificacion> clasificacions;

    public FragmentClasificaciones() {
        // Required empty public constructor
    }

    public static FragmentClasificaciones newInstance(ArrayList<Clasificacion> clasificacions){
        FragmentClasificaciones fragment = new FragmentClasificaciones();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CLASIFICACIONES, clasificacions);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clasificaciones, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        clasificacions = getArguments().getParcelableArrayList(CLASIFICACIONES);

        appBarLayout = (AppBarLayout) ((View)container.getParent()).findViewById(R.id.appBar_layout);
        pestañas = new TabLayout(getActivity());
        pestañas.setTabTextColors(Color.BLACK,Color.GRAY);
        pestañas.setSelectedTabIndicatorColor(Color.BLUE);
        pestañas.setTabMode(TabLayout.MODE_SCROLLABLE);
        appBarLayout.addView(pestañas);

        poblarTab();
        pestañas.setupWithViewPager(viewPager);


        return view;
    }

    private void poblarTab(){
        AdaptadorSecciones adaptadorSecciones = new AdaptadorSecciones(getFragmentManager());

        for ( Clasificacion clasificacion : clasificacions) {
            adaptadorSecciones.addFragment(FragmentPeliculas.newInstace(clasificacion), clasificacion.getTipo());
        }

        viewPager.setAdapter(adaptadorSecciones);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBarLayout.removeView(pestañas);
    }
}
