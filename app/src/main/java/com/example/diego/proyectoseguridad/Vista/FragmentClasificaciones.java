package com.example.diego.proyectoseguridad.Vista;


import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diego.proyectoseguridad.R;
import com.example.diego.proyectoseguridad.Modelo.clsManejadorVentanas;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentClasificaciones extends Fragment {

    private AppBarLayout appBarLayout;
    private TabLayout pestañas;
    private ViewPager viewPager;

    public FragmentClasificaciones() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clasificaciones, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
//        pestañas.setSelectedTabIndicatorColor(Color.BLUE);

        appBarLayout = (AppBarLayout) ((View)container.getParent()).findViewById(R.id.appBar_layout);
        pestañas = new TabLayout(getActivity());
        pestañas.setTabTextColors(Color.BLACK,Color.GRAY);
        appBarLayout.addView(pestañas);

        poblarTab();
        pestañas.setupWithViewPager(viewPager);

        return view;
    }

    private void poblarTab(){
        clsManejadorVentanas manejadorVentanas=new clsManejadorVentanas(getActivity());
        AdaptadorSecciones adaptadorSecciones = new AdaptadorSecciones(getFragmentManager());
        Cursor cursor=manejadorVentanas.getNombreVentanas();

        while (cursor.moveToNext()) {
            adaptadorSecciones.addFragment(new FragmentPeliculas(), cursor.getString(1));
        }
        viewPager.setAdapter(adaptadorSecciones);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBarLayout.removeView(pestañas);
    }
}
