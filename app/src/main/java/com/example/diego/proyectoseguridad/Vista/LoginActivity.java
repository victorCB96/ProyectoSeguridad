package com.example.diego.proyectoseguridad.Vista;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diego.proyectoseguridad.Modelo.Usuario;
import com.example.diego.proyectoseguridad.Modelo.clsConexion;
import com.example.diego.proyectoseguridad.Modelo.clsManejoUsuarios;
import com.example.diego.proyectoseguridad.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener{


    public static final String USUARIO = "usuario";
    // UI references.
    @NotEmpty(message = "No puede dejar este campo vacío")
    private AutoCompleteTextView email;
    @NotEmpty(message = "Escriba una contraseña")
    private EditText password;
    private Button login;
    private clsManejoUsuarios conexion;
    private Validator validator;
    private View view;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        conexion = new clsManejoUsuarios(this);

        //Inicializacion de los elementos del xml
        email=(AutoCompleteTextView)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.btnLogin);

        validator = new Validator(this);
        validator.setValidationListener(this);

        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                view=v;
                validator.validate();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onValidationSucceeded() {
        usuario = conexion.consultarUsuario(password.getText().toString().trim(), email.getText().toString().trim());

        if(usuario != null){
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.putExtra(USUARIO, usuario );
            startActivity(mainIntent);
        }else{
            Snackbar.make(view,"No existe el usuario", Snackbar.LENGTH_LONG).show();
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

