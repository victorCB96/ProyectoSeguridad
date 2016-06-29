package com.example.diego.proyectoseguridad.Vista;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.diego.proyectoseguridad.Modelo.clsManejoRoles;
import com.example.diego.proyectoseguridad.R;

public class AsignacionRolesUsuariosFragment extends DialogFragment {

    private View view;
    private RecyclerView rvRolesUsuarios;
    private AdaptadorRolesUsuario adaptadorRolesUsuario;


    public AsignacionRolesUsuariosFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogo();
    }



    public AlertDialog createDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.fragment_asignacion_roles_usuarios, null);

        builder.setView(v);

        clsManejoRoles manejoRoles= new clsManejoRoles(v.getContext());
        Cursor rolesUsuario= manejoRoles.getRol();

        rvRolesUsuarios = (RecyclerView) v.findViewById(R.id.rvRolesUsuario);
        rvRolesUsuarios.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(v.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //le estoy diciendo al recycler que se comporte como linearlayout
        rvRolesUsuarios.setLayoutManager(linearLayoutManager);

        adaptadorRolesUsuario= new AdaptadorRolesUsuario();
        adaptadorRolesUsuario.actualizarCursor(rolesUsuario);
        rvRolesUsuarios.setAdapter(adaptadorRolesUsuario);

        Button btnCerrar=(Button) v.findViewById(R.id.btnCerrarDialogo);


        btnCerrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i:AdaptadorRolesUsuario.roles) {
                            Log.i("hola","Hola= "+ i);
                        }
                        dismiss();

                    }
                }
        );











        return builder.create();
    }



}
