package com.example.diego.proyectoseguridad.Vista;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.proyectoseguridad.Modelo.Rol;
import com.example.diego.proyectoseguridad.Modelo.Usuario;
import com.example.diego.proyectoseguridad.Modelo.Ventana;
import com.example.diego.proyectoseguridad.Modelo.clsManejadorVentanas;
import com.example.diego.proyectoseguridad.Modelo.clsManejoRoles;
import com.example.diego.proyectoseguridad.Modelo.clsManejoUsuarios;
import com.example.diego.proyectoseguridad.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.ArrayList;
import java.util.List;

public class AgregarUsuarioActivity extends AppCompatActivity implements Validator.ValidationListener, DialogInterface.OnMultiChoiceClickListener, View.OnClickListener,  ExpandableExampleAdapter.CheckPrmisosListener{

    public final static String ROLES_CHECKED_KEY = "ROLES_CHECKED";
    public final static String VENTANAS_LIST_KEY = "VENTANAS_LIST";
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

    private CharSequence[] nombresRoles;
    private boolean[] rolesChecked;
    private ArrayList<Ventana> listVentanas;
    private Usuario usuario;
    private ArrayList<Rol> roles;
    private clsManejoRoles manejoRoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);
        getSupportActionBar().setTitle("Agregar Usuario");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        usuario = getIntent().getParcelableExtra("USER");
        roles = new ArrayList<>();

        clsManejadorVentanas manejadorVentanas = new clsManejadorVentanas(this);
        manejoRoles= new clsManejoRoles(this);
        nombresRoles = manejoRoles.nombreRolesArray();
        rolesChecked = new boolean[nombresRoles.length];
        listVentanas = manejadorVentanas.getListVentanas();

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

        tvRolesUsuario.setOnClickListener(this);
        tvPermisos.setOnClickListener(this);


    }

    @Override
    public void onValidationSucceeded() {
        clsManejoUsuarios manejoUsuarios = new clsManejoUsuarios(this);
        Cursor consulta= manejoUsuarios.getUsuarioEspecifico( etUsername.getText().toString().trim());
        Cursor cursorRoles = manejoRoles.getRol();


        for (int i=0; cursorRoles.moveToNext(); i++){

            if(rolesChecked[i]){
                roles.add(new Rol(cursorRoles.getInt(0),cursorRoles.getString(1)));
            }
        }

        if (consulta.getCount()==0){
            Usuario usuarioNuevo = new Usuario(etUsername.getText().toString().trim(),etPassword.getText().toString());
            if(manejoUsuarios.mAgregarUsuario(usuarioNuevo,listVentanas,roles,usuario)) {
                setResult(RESULT_OK);
                finish();
            }
            else {
                Snackbar.make(this.getCurrentFocus(),"El usuario no se ha podido agregar",Snackbar.LENGTH_SHORT).show();
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

    @Override
    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
        rolesChecked[position] = isChecked;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBooleanArray(ROLES_CHECKED_KEY,rolesChecked);
        outState.putParcelableArrayList(VENTANAS_LIST_KEY,listVentanas);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        rolesChecked = savedInstanceState.getBooleanArray(ROLES_CHECKED_KEY);
        listVentanas = savedInstanceState.getParcelableArrayList(VENTANAS_LIST_KEY);
    }

    @Override
    public void onClick(View view) {
        int itemId = view.getId();
        FragmentManager fm = getSupportFragmentManager();
        DialogFragment alertDialog = null;

        switch (itemId){
            case R.id.tvRolesUsuario:
                 alertDialog = DialogAsignarRol.newInstance(nombresRoles, rolesChecked, true);
                alertDialog.show(fm, "fragment_alert_roles");
                break;
            case R.id.tvPermisos:
                alertDialog = DialogPermisosVentanas.newInstance(listVentanas,true);
                alertDialog.show(fm, "fragment_alert_ventanas");
                break;
        }
    }

    @Override
    public void onCheck(int groupItem, int childItem, boolean isCheck) {
        listVentanas.get(groupItem).getPermiso(childItem).setActivado(isCheck);
    }

}
