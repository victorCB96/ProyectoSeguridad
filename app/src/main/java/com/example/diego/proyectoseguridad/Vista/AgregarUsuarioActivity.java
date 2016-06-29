package com.example.diego.proyectoseguridad.Vista;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.proyectoseguridad.Modelo.Usuario;
import com.example.diego.proyectoseguridad.Modelo.clsManejoUsuarios;
import com.example.diego.proyectoseguridad.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

public class AgregarUsuarioActivity extends AppCompatActivity implements Validator.ValidationListener{

    @NotEmpty(message = "No puede dejar este campo vacío")
    private EditText etUsername;

    @Password(message = "Contraseña invalida")
    @NotEmpty(message = "No puede dejar este campo vacío")
    private EditText etPassword;

    @ConfirmPassword(message = "Las contraseñas no coinciden")
    private EditText etConfirmPassword;

    private Validator validator;

    private Button btnRegistro;

    private TextView tvPermisos,tvRolesUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);
        getSupportActionBar().setTitle("Agregar Usuario");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


        validator = new Validator(this);
        validator.setValidationListener(this);

        etUsername= (EditText) findViewById(R.id.etUsername);
        etPassword= (EditText) findViewById(R.id.etPassword);
        etConfirmPassword= (EditText) findViewById(R.id.etConfirmPassword);
        btnRegistro= (Button) findViewById(R.id.btnRegistrar);
        tvPermisos= (TextView) findViewById(R.id.tvPermisos);
        tvRolesUsuario= (TextView) findViewById(R.id.tvRolesUsuario);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

        tvRolesUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                AsignacionRolesUsuariosFragment alertDialog = new AsignacionRolesUsuariosFragment();
                alertDialog.show(fm, "fragment_alert");
            }
        });



    }

    @Override
    public void onValidationSucceeded() {
        clsManejoUsuarios manejoUsuarios = new clsManejoUsuarios(this);
        Cursor consulta= manejoUsuarios.getUsuarioEspecifico( etUsername.getText().toString().trim());
        if (consulta.getCount()==0){
            if(manejoUsuarios.mAgregarUsuario(new Usuario( etUsername.getText().toString().trim(), etPassword.getText().toString().trim() ))) {
                setResult(RESULT_OK);
                finish();
            }

        }else{
            Toast.makeText(getApplicationContext(),"Ya existe el nombre de usuario",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError error : errors){
            View view = error.getView();
            String mensaje = error.getCollatedErrorMessage(this);

            if(view instanceof  EditText ){
                ((EditText) view).setError(mensaje);
            }
        }
    }
}
