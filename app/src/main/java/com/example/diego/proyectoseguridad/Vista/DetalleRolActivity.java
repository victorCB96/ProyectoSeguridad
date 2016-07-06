package com.example.diego.proyectoseguridad.Vista;

import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diego.proyectoseguridad.Modelo.Rol;
import com.example.diego.proyectoseguridad.Modelo.Variables;
import com.example.diego.proyectoseguridad.Modelo.clsManejadorVentanas;
import com.example.diego.proyectoseguridad.Modelo.clsManejoClasificaciones;
import com.example.diego.proyectoseguridad.Modelo.clsManejoRoles;
import com.example.diego.proyectoseguridad.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class DetalleRolActivity extends AppCompatActivity implements  Validator.ValidationListener{
    public static final int RESULT_ELIMINAR = 10;
    public static final int RESULT_EDITAR = 11;

    private AdaptadorClasificaciones adaptadorClasificaciones;
    private AdaptadorVentanas adaptadorVentanas;
    private RecyclerView rvVentana,rvClasificaciones;
    private com.getbase.floatingactionbutton.FloatingActionButton btnEliminar,btnModificar;
    private clsManejoClasificaciones manejoClasificaciones;
    private clsManejadorVentanas manejadorVentanas;
    private clsManejoRoles manejoRoles;
    private Validator validator;

    @NotEmpty(message = "Por favor digite el nuevo nombre")
    private EditText etRol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_rol);
        getSupportActionBar().setTitle("Detalle del Rol");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


        validator = new Validator(this);
        validator.setValidationListener(this);



        manejoClasificaciones= new clsManejoClasificaciones(getApplicationContext());
        manejadorVentanas= new clsManejadorVentanas(getApplicationContext());
        manejoRoles= new clsManejoRoles(getApplicationContext());
        btnEliminar=(com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_eliminar_rol);
        btnModificar=(com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_editar_rol);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manejoRoles.mEliminarRol(new Rol(getIntent().getExtras().getInt("idRol"), getIntent().getExtras().getString("nombre")))){
                    setResult(RESULT_ELIMINAR);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Error a la hora de eliminar",Toast.LENGTH_LONG).show();
                }

            }
        });

        etRol=(EditText) findViewById(R.id.etRolDetalle);
        etRol.setText(getIntent().getExtras().getString("nombre"));
        this.inicializarAdaptadorClasificaciones();
        this.inicializarAdaptadorVentana();
    }

    //private void eliminarRol

    private void inicializarAdaptadorClasificaciones(){
        Cursor clasificaciones= manejoClasificaciones.getClasificaciones();
        rvClasificaciones= (RecyclerView) findViewById(R.id.rvClasificacionesPeliculas);
        rvClasificaciones.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //le estoy diciendo al recycler que se comporte como linearlayout
        rvClasificaciones.setLayoutManager(linearLayoutManager);

        adaptadorClasificaciones= new AdaptadorClasificaciones(this,new clsManejoRoles(this),String.valueOf(getIntent().getExtras().getInt("idRol")));
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

        adaptadorVentanas= new AdaptadorVentanas(this,new clsManejoRoles(this),String.valueOf(getIntent().getExtras().getInt("idRol")));
        adaptadorVentanas.actualizarCursor(ventanas);
        rvVentana.setAdapter(adaptadorVentanas);
    }


    @Override
    public void onValidationSucceeded() {
        clsManejoRoles manejoRoles= new clsManejoRoles(this);
        if(manejoRoles.mModificarRol(new Rol( getIntent().getExtras().getInt("idRol"),etRol.getText().toString().trim()))) {
            Variables.PERMISOS.clear();
            Variables.PERMISOS_CLASIFICACIONES.clear();
            Variables.PERMISOS_CLASIFICACIONES_ELIMINACION.clear();
            setResult(RESULT_EDITAR);
            finish();
        }else {
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
        }
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
