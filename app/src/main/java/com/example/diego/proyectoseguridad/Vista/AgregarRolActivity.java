package com.example.diego.proyectoseguridad.Vista;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diego.proyectoseguridad.Modelo.Usuario;
import com.example.diego.proyectoseguridad.Modelo.clsConexion;
import com.example.diego.proyectoseguridad.Modelo.clsManejoUsuarios;
import com.example.diego.proyectoseguridad.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

public class AgregarRolActivity extends AppCompatActivity implements Validator.ValidationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_rol);
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
