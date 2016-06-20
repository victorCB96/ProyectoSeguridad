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

import com.example.diego.proyectoseguridad.Modelo.clsManejoUsuarios;
import com.example.diego.proyectoseguridad.R;


public class GestionUsuariosFragment extends Fragment {

    private View view;
    private RecyclerView rvUsuarios;
    private AdaptadorUsuarios adaptadorUsuarios;
    private clsManejoUsuarios manejoUsuarios;
    private ImageButton btnDetalle;
    private FloatingActionButton btn_agregarUsuario;

    public static final int ACTIVIDAD_AGREGAR_REQUEST = 1;
    public static final int ACTIVIDAD_DETALLE_REQUEST = 2;

    public GestionUsuariosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_gestion_usuarios, container, false);
        manejoUsuarios= new clsManejoUsuarios(view.getContext());
        this.incializarAdaptador();

        btnDetalle= (ImageButton) view.findViewById(R.id.btn_detalle_usuario);
        btn_agregarUsuario= (FloatingActionButton) view.findViewById(R.id.btn_agregarUsuario);

        btn_agregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarRegistro();
            }
        });

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACTIVIDAD_AGREGAR_REQUEST ) {
            if (resultCode == Activity.RESULT_OK) {
                adaptadorUsuarios.actualizarCursor(manejoUsuarios.getUsuario());
                Snackbar.make(getView(), "Nota Agregada", Snackbar.LENGTH_SHORT).show();
            }
        }/*
        else if(requestCode == ACTIVIDAD_DETALLE_REQUEST){
            if(resultCode == ActivityDetalleRecordatorio.RESULT_EDITAR){

                Snackbar.make(getView(), "Nota Editada Correctamente", Snackbar.LENGTH_SHORT).show();
            }
            else if(resultCode == ActivityDetalleRecordatorio.RESULT_ELIMINAR){
                Snackbar.make(getView(), "Nota Eliminada Correctamente", Snackbar.LENGTH_SHORT).show();
            }

            adaptadorNotas.actualizarCursor(manejoNotas.getNotasCompuestas());
        }*/
    }

    private void iniciarRegistro(){
        Intent intent = new Intent(getActivity(),AgregarUsuarioActivity.class);
        startActivityForResult(intent,ACTIVIDAD_AGREGAR_REQUEST);
    }


    private void incializarAdaptador(){
        Cursor usuarios= manejoUsuarios.getUsuario();
        rvUsuarios= (RecyclerView) view.findViewById(R.id.rvUsuarios);
        rvUsuarios.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //le estoy diciendo al recycler que se comporte como linearlayout
        rvUsuarios.setLayoutManager(linearLayoutManager);

        adaptadorUsuarios= new AdaptadorUsuarios();
        adaptadorUsuarios.actualizarCursor(usuarios);
        rvUsuarios.setAdapter(adaptadorUsuarios);
    }
}
