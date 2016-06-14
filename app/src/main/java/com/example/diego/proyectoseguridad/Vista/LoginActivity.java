package com.example.diego.proyectoseguridad.Vista;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.CursorIndexOutOfBoundsException;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.proyectoseguridad.Modelo.clsConexion;
import com.example.diego.proyectoseguridad.R;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{


    // UI references.
    private AutoCompleteTextView email;
    private EditText password;
    private View mProgressView;
    private View mLoginFormView;
    private Button login;
    private clsConexion conexion;
    private Cursor consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=(AutoCompleteTextView)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.btnLogin);
        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent().setClass(
                        v.getContext(), MainActivity.class);
                if(autenticar()){
                    startActivity(mainIntent);
                }else{
                    Snackbar.make(v,"No existe el usuario", Snackbar.LENGTH_LONG).show();
                }
            }
        });


    }

    public boolean autenticar(){
        String usuario=email.getText().toString();
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
            if (usuario.equals(usuariodb)&& clave.equals(clavedb)){
                estado= true;
            }else {
                estado= false;
            }


        }catch (CursorIndexOutOfBoundsException err){
            Toast.makeText(this,"Ocurrió un error fatal",
                    Toast.LENGTH_SHORT).show();
        }//Fin del try catch

        return estado;

    }



}

