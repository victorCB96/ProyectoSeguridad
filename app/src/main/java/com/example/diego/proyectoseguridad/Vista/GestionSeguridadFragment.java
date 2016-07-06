package com.example.diego.proyectoseguridad.Vista;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.diego.proyectoseguridad.Modelo.Variables;
import com.example.diego.proyectoseguridad.Modelo.clsManejoRoles;
import com.example.diego.proyectoseguridad.R;


public class GestionSeguridadFragment extends Fragment {

    private View view;
    private RecyclerView rvRoles;
    private AdaptadorSeguridad adaptadorSeguridad;
    private clsManejoRoles manejoRoles;
    private ImageButton btnDetalle;
    private FloatingActionButton btn_agregarRol;

    public static final int ACTIVIDAD_AGREGAR_REQUEST = 1;
    public static final int ACTIVIDAD_DETALLE_REQUEST = 2;

    public GestionSeguridadFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_gestion_seguridad, container, false);
        manejoRoles= new clsManejoRoles(view.getContext());
        this.incializarAdaptador();

        btnDetalle= (ImageButton) view.findViewById(R.id.btn_detalle_rol);
        btn_agregarRol= (FloatingActionButton) view.findViewById(R.id.btn_agregarRol);
        btn_agregarRol.setVisibility(Variables.PERMISO_AGREGAR_ROLES?View.VISIBLE:View.INVISIBLE);
       btn_agregarRol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    iniciarRegistro();

            }
        });

        return view;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACTIVIDAD_AGREGAR_REQUEST ) {
            if (resultCode == Activity.RESULT_OK) {
                adaptadorSeguridad.actualizarCursor(manejoRoles.getRol());
                Snackbar.make(getView(), "Rol Agregado Correctamente", Snackbar.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == ACTIVIDAD_DETALLE_REQUEST){
            if(resultCode == DetalleRolActivity.RESULT_EDITAR){
                Snackbar.make(getView(), "Rol Editado Correctamente", Snackbar.LENGTH_SHORT).show();
            }
            else if(resultCode == DetalleRolActivity.RESULT_ELIMINAR){
                Snackbar.make(getView(), "Rol Eliminada Correctamente", Snackbar.LENGTH_SHORT).show();
            }

            adaptadorSeguridad.actualizarCursor(manejoRoles.getRol());
        }
    }

    private void iniciarRegistro(){
        Intent intent = new Intent(getActivity(),AgregarRolActivity.class);
        startActivityForResult(intent,ACTIVIDAD_AGREGAR_REQUEST);
    }


    private void incializarAdaptador(){
        Cursor rol= manejoRoles.getRol();
        rvRoles= (RecyclerView) view.findViewById(R.id.rvRoles);
        rvRoles.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //le estoy diciendo al recycler que se comporte como linearlayout
        rvRoles.setLayoutManager(linearLayoutManager);

        adaptadorSeguridad= new AdaptadorSeguridad(this);
        adaptadorSeguridad.actualizarCursor(rol);
        rvRoles.setAdapter(adaptadorSeguridad);
    }


}
