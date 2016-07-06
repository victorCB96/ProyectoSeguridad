package com.example.diego.proyectoseguridad.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diego.proyectoseguridad.Modelo.Bitacora;
import com.example.diego.proyectoseguridad.Modelo.EncriptarContra;
import com.example.diego.proyectoseguridad.Modelo.Usuario;
import com.example.diego.proyectoseguridad.Modelo.clsManejoBitacoras;
import com.example.diego.proyectoseguridad.Modelo.clsManejoUsuarios;
import com.example.diego.proyectoseguridad.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
    private clsManejoBitacoras clsBitacoras;
    private Validator validator;
    private View view;
    private Usuario usuario;
    private Bitacora bitacora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        conexion = new clsManejoUsuarios(this);
        clsBitacoras = new clsManejoBitacoras(this);
        bitacora= new Bitacora();
        Toast.makeText(this,"hora------"+getDateCurrentTimeZone(),
                Toast.LENGTH_SHORT).show();

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


        //validator.validate();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public  String getDateCurrentTimeZone() {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.getTimeInMillis();
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }

    public Bitacora llenarObjeto(Usuario usuario){
       try
       {
           bitacora.setIdUsuario(usuario.getIdUsuario());
           //bitacora.setIdBitacora(1);
           bitacora.setAccion("Logueo");
           bitacora.setDescripcion("Se ha conectado a la app");
           bitacora.setHora(Time.valueOf(getDateCurrentTimeZone()));
       }
       catch (Exception e){
           Snackbar.make(view,"ocurrio un problema"+e, Snackbar.LENGTH_LONG).show();
       }

        return bitacora;
    }

    @Override
    public void onValidationSucceeded() {
        usuario = conexion.consultarUsuario(new EncriptarContra().md5(password.getText().toString().trim()), email.getText().toString().trim());

        if(usuario != null){
           if(clsBitacoras.mAgregarBitacora(this.llenarObjeto(usuario))){
               Toast.makeText(this,"Registrado Correctamente en la Bitacora",Toast.LENGTH_SHORT).show();
           }
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.putExtra(USUARIO, usuario );
            startActivity(mainIntent);
        }else{
            Snackbar.make(view,"No existe el usuario", Snackbar.LENGTH_LONG).show();
        }

    }

    //metodo que devuelve los errores en caso de que no se cumplan los requerimientos
    //establecido en los edit text
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError error : errors){
            View view = error.getView();
            String mensaje = error.getCollatedErrorMessage(this);

            if(view instanceof  EditText ){
                ((EditText) view).setError(mensaje);
            }
        }

    }//fin del metodo onValidationFailed
}

