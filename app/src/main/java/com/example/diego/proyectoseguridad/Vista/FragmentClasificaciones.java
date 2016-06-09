package com.example.diego.proyectoseguridad.Vista;


import android.content.res.Resources;
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
        pestañas.setSelectedTabIndicatorColor(Color.BLUE);

        appBarLayout = (AppBarLayout) ((View)container.getParent()).findViewById(R.id.appBar_layout);
        pestañas = new TabLayout(getActivity());
        pestañas.setTabTextColors(Color.BLACK,Color.GRAY);
        appBarLayout.addView(pestañas);

        poblarTab();
        pestañas.setupWithViewPager(viewPager);

        return view;
    }

    private void poblarTab(){
        AdaptadorSecciones adaptadorSecciones = new AdaptadorSecciones(getFragmentManager());

        adaptadorSecciones.addFragment(new FragmentPeliculas(), "Para todo publico");
        adaptadorSecciones.addFragment(new FragmentPeliculas(), "Supervisión de adulto");
        adaptadorSecciones.addFragment(new FragmentPeliculas(), "+12");
        adaptadorSecciones.addFragment(new FragmentPeliculas(), "+18");
        viewPager.setAdapter(adaptadorSecciones);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBarLayout.removeView(pestañas);
    }
}
