package com.example.diego.proyectoseguridad.Vista;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diego.proyectoseguridad.Modelo.clsConexion;
import com.example.diego.proyectoseguridad.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener{


    // UI references.
    @NotEmpty(message = "No puede dejar este campo vacío")
    private AutoCompleteTextView email;
    @NotEmpty(message = "Escriba una contraseña")
    private EditText password;
    private Button login;
    private clsConexion conexion;
    private Cursor consulta;
    private String usuario;
    private Validator validator;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    public boolean autenticar(){
        usuario=email.getText().toString();
        String clave=password.getText().toString();
        String usuariodb="";
        String clavedb="";
        boolean estado=false;
        conexion= new clsConexion(this);
        consulta=conexion.consultarUsuario(usuario,clave);

        try {
            for(consulta.moveToFirst(); !consulta.isAfterLast(); consulta.moveToNext()){
                usuariodb=consulta.getString(1);
                clavedb=consulta.getString(2);
            }
            if(consulta.getCount()!=0){
                estado= true;
            }
        }catch (CursorIndexOutOfBoundsException err){
            Toast.makeText(this,"Ocurrió un error fatal",
                    Toast.LENGTH_SHORT).show();
        }//Fin del try catch

        return estado;

    }//fin del metodo autenticar


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AutoCompleteTextView email=null;
        EditText password=null;
        Button login=null;
        clsConexion conexion=null;
        Cursor consulta=null;
        String usuario="";
    }

    @Override
    public void onValidationSucceeded() {

        if(autenticar()){
            Intent mainIntent = new Intent(view.getContext(), MainActivity.class);
            mainIntent.putExtra("correo",usuario);
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

