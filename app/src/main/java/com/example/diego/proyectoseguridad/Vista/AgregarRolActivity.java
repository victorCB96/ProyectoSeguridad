package com.example.diego.proyectoseguridad.Vista;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.diego.proyectoseguridad.Modelo.clsManejadorVentanas;
import com.example.diego.proyectoseguridad.Modelo.clsManejoClasificaciones;
import com.example.diego.proyectoseguridad.Modelo.clsManejoRoles;
import com.example.diego.proyectoseguridad.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

public class AgregarRolActivity extends AppCompatActivity implements Validator.ValidationListener {
    private AdaptadorClasificaciones adaptadorClasificaciones;
    private AdaptadorVentanas adaptadorVentanas;
    private RecyclerView rvVentana,rvClasificaciones;
    private clsManejoClasificaciones manejoClasificaciones;
    private clsManejadorVentanas manejadorVentanas;
    private clsManejoRoles manejoRoles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_rol);

        manejoClasificaciones= new clsManejoClasificaciones(getApplicationContext());
        manejadorVentanas= new clsManejadorVentanas(getApplicationContext());
        manejoRoles= new clsManejoRoles(getApplicationContext());

        this.inicializarAdaptadorClasificaciones();
        this.inicializarAdaptadorVentana();
    }

    private void inicializarAdaptadorClasificaciones(){
        Cursor clasificaciones= manejoClasificaciones.getClasificaciones();
        rvClasificaciones= (RecyclerView) findViewById(R.id.rvClasificacionesPeliculas);
        rvClasificaciones.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //le estoy diciendo al recycler que se comporte como linearlayout
        rvClasificaciones.setLayoutManager(linearLayoutManager);

        adaptadorClasificaciones= new AdaptadorClasificaciones();
        adaptadorClasificaciones.actualizarCursor(clasificaciones);
        rvClasificaciones.setAdapter(adaptadorClasificaciones);
    }

    private void inicializarAdaptadorVentana(){
        Cursor ventanas= manejadorVentanas.getVentanas();
        rvVentana= (RecyclerView) findViewById(R.id.rvVentanasRoles);
        rvVentana.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //le estoy diciendo al recycler que se comporte como linearlayout
        rvVentana.setLayoutManager(linearLayoutManager);


        adaptadorVentanas= new AdaptadorVentanas(this);
        adaptadorVentanas.actualizarCursor(ventanas);
        rvVentana.setAdapter(adaptadorVentanas);
    }

    @Override
    public void onValidationSucceeded() {
        /*
        clsManejoUsuarios manejoUsuarios = new clsManejoUsuarios(this);
        clsConexion conexion= new clsConexion(getApplicationContext());
        Cursor consulta= conexion.getUsuarioEspecifico( etUsername.getText().toString().trim());
        if (consulta.getCount()==0){
            if(manejoUsuarios.mAgregarUsuario(new Usuario( etUsername.getText().toString().trim(), etPassword.getText().toString().trim() ))) {
                setResult(RESULT_OK);
                finish();
            }

        }else{
            Toast.makeText(getApplicationContext(),"Ya existe el nombre de usuario",Toast.LENGTH_LONG).show();
        }
        */
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError error : errors){
            View view = error.getView();
            String mensaje = error.getCollatedErrorMessage(this);

            if(view instanceof EditText){
                ((EditText) view).setError(mensaje);
            }
        }
    }
}
