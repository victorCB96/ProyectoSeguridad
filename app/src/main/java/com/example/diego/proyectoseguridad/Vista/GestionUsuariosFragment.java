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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.diego.proyectoseguridad.Modelo.Usuario;
import com.example.diego.proyectoseguridad.Modelo.Variables;
import com.example.diego.proyectoseguridad.Modelo.clsManejoUsuarios;
import com.example.diego.proyectoseguridad.R;


public class GestionUsuariosFragment extends Fragment implements AdaptadorUsuarios.OnclickListener{

    private View view;
    private RecyclerView rvUsuarios;
    private AdaptadorUsuarios adaptadorUsuarios;
    private clsManejoUsuarios manejoUsuarios;
    private ImageButton btnDetalle;
    private FloatingActionButton btn_agregarUsuario;
    private Usuario usuario;

    public static final int ACTIVIDAD_AGREGAR_REQUEST = 1;
    public static final int ACTIVIDAD_DETALLE_REQUEST = 2;

    public GestionUsuariosFragment() {
        // Required empty public constructor
    }

    public static GestionUsuariosFragment newInstance(Usuario usuario){
        GestionUsuariosFragment gestionUsuariosFragment = new GestionUsuariosFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("USER",usuario);
        gestionUsuariosFragment.setArguments(bundle);
        return gestionUsuariosFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        usuario = getArguments().getParcelable("USER");
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_gestion_usuarios, container, false);
        manejoUsuarios= new clsManejoUsuarios(view.getContext());
        this.incializarAdaptador();

        btnDetalle= (ImageButton) view.findViewById(R.id.btn_detalle_usuario);
        btn_agregarUsuario= (FloatingActionButton) view.findViewById(R.id.btn_agregarUsuario);


        btn_agregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Variables.PERMISO_AGREGAR_USUARIOS){
                    iniciarRegistro();
                }else {
                    Snackbar.make(getView(),"Usted no tiene permiso para agregar usuarios",Snackbar.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ACTIVIDAD_AGREGAR_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    adaptadorUsuarios.actualizarCursor(manejoUsuarios.getUsuario());
                    Snackbar.make(getView(), "Usuario Agregado", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case ACTIVIDAD_DETALLE_REQUEST:
                if(resultCode == DetalleUsuarioActivity.DELETE_RESULT) {
                    adaptadorUsuarios.actualizarCursor(manejoUsuarios.getUsuario());
                    Snackbar.make(getView(), "Usuario Eliminado", Snackbar.LENGTH_SHORT).show();
                }
                else if (resultCode == DetalleUsuarioActivity.UPDATE_RESULT) {
                    adaptadorUsuarios.actualizarCursor(manejoUsuarios.getUsuario());
                }
                break;
        }
    }

    private void iniciarRegistro(){
        Intent intent = new Intent(getActivity(),AgregarUsuarioActivity.class);
        intent.putExtra("USER",usuario);
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
        adaptadorUsuarios.setListener(this);
        adaptadorUsuarios.actualizarCursor(usuarios);
        rvUsuarios.setAdapter(adaptadorUsuarios);
    }

    @Override
    public void onClick(int posicion, int idUsuario, View view) {
        Usuario usuario= manejoUsuarios.getUsuarioEspecifico(idUsuario);
        Intent intent = new Intent(getActivity(),DetalleUsuarioActivity.class);
        intent.putExtra("USER",usuario);
        intent.putExtra("CURRENT_USER",this.usuario);
        startActivityForResult(intent,ACTIVIDAD_DETALLE_REQUEST);

    }


}
