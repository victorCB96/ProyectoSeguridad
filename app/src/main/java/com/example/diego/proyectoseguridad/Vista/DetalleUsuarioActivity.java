package com.example.diego.proyectoseguridad.Vista;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DetalleUsuarioActivity extends AppCompatActivity implements Validator.ValidationListener, DialogInterface.OnMultiChoiceClickListener, View.OnClickListener,  ExpandableExampleAdapter.CheckPrmisosListener{

    public final static String ROLES_CHECKED_KEY = "ROLES_CHECKED";
    public final static String VENTANAS_LIST_KEY = "VENTANAS_LIST";
    public final static int UPDATE_RESULT = 10;
    public final static int DELETE_RESULT = 22;


    @NotEmpty(message = "No puede dejar este campo vacío")
    private EditText etUsername;

    @Password(message = "Contraseña invalida")
    private EditText etPassword;

    @ConfirmPassword(message = "Las contraseñas no coinciden")
    private EditText etConfirmPassword;

    private TextView tvCreadoPor;
    private TextView tvFechaCreacion;
    private TextView tvUltimaodificacion;
    private TextView tvModificadopor;

    private Validator validator;

    private Button btnGuardarCambios;

    private TextView tvPermisos,tvRolesUsuario;

    private CharSequence[] nombresRoles;
    private boolean[] rolesChecked;
    private ArrayList<Ventana> listVentanas;
    private Usuario usuarioSelecionado, currentUser;
    private ArrayList<Rol> roles;
    private clsManejoRoles manejoRoles;
    private clsManejadorVentanas manejadorVentanas;
    private boolean editarMode = false;
    private clsManejoUsuarios manejoUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalle_usuario);
        getSupportActionBar().setTitle("Información de Usuario");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usuarioSelecionado = getIntent().getParcelableExtra("USER");
        currentUser = getIntent().getParcelableExtra("CURRENT_USER");
        roles = new ArrayList<>();

        manejadorVentanas = new clsManejadorVentanas(this);
        manejoRoles= new clsManejoRoles(this);
        manejoUsuarios = new clsManejoUsuarios(this);

        nombresRoles = manejoRoles.nombreRolesArray();
        rolesChecked = new boolean[nombresRoles.length];
        listVentanas = manejadorVentanas.getListVentanasUser(usuarioSelecionado.getIdUsuario());

        Cursor cursorRolesUsuario = manejoRoles.getRoles(String.valueOf(usuarioSelecionado.getIdUsuario()));

        while (cursorRolesUsuario.moveToNext()){
            for(int j=0; j<nombresRoles.length; j++){
                if(cursorRolesUsuario.getString(1).equalsIgnoreCase(nombresRoles[j].toString())){
                    rolesChecked[j]=true;
                }
            }
        }

        validator = new Validator(this);
        validator.setValidationListener(this);

        tvCreadoPor = (TextView) findViewById(R.id.tv_creado_por);
        tvFechaCreacion = (TextView) findViewById(R.id.tv_fecha_creacion);
        tvModificadopor = (TextView) findViewById(R.id.tv_mod_por);
        tvUltimaodificacion = (TextView) findViewById(R.id.tv_ultima_mod);

        etUsername= (EditText) findViewById(R.id.etUsername);
        etPassword= (EditText) findViewById(R.id.etPassword);
        etConfirmPassword= (EditText) findViewById(R.id.etConfirmPassword);
        btnGuardarCambios = (Button) findViewById(R.id.btnRegistrar);

        tvPermisos= (TextView) findViewById(R.id.tvPermisos);
        tvRolesUsuario= (TextView) findViewById(R.id.tvRolesUsuario);

        btnGuardarCambios.setOnClickListener(this);
        tvRolesUsuario.setOnClickListener(this);
        tvPermisos.setOnClickListener(this);

        mostrarUsuario();


    }

    @Override
    public void onValidationSucceeded() {
        Cursor cursorRoles = manejoRoles.getRol();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format =  new SimpleDateFormat("MM-d-yyyy", Locale.getDefault());

        usuarioSelecionado.setNombre(etUsername.getText().toString().trim());

        if(!etPassword.getText().toString().trim().isEmpty())
            usuarioSelecionado.setContrasena(etPassword.getText().toString().trim());

        usuarioSelecionado.setModificadoPor(currentUser.getNombre());
        usuarioSelecionado.setFechaModificacion(format.format(calendar.getTime()));


        if(manejoUsuarios.mModificarUsuario(usuarioSelecionado,listVentanas,rolesChecked,cursorRoles)) {
            setResult(UPDATE_RESULT);
            setEditarMode(false);
        }
        else {
            Snackbar.make(this.getCurrentFocus(),"No se pudo actualizar la información",Snackbar.LENGTH_SHORT).show();
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
                alertDialog = DialogAsignarRol.newInstance(nombresRoles, rolesChecked);
                alertDialog.show(fm, "fragment_alert_roles");
                break;

            case R.id.tvPermisos:
                alertDialog = DialogPermisosVentanas.newInstance(listVentanas, editarMode);
                alertDialog.show(fm, "fragment_alert_ventanas");
                break;

            case R.id.btnRegistrar:
                validator.validate();
                break;
        }
    }

    @Override
    public void onCheck(int groupItem, int childItem, boolean isCheck) {
        listVentanas.get(groupItem).getPermiso(childItem).setActivado(isCheck);
        Log.i("mmm","mmm");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_detalle_usuario, menu);
        menu.findItem(R.id.action_editar).setVisible(!editarMode);
        menu.findItem(R.id.action_eliminar).setVisible(!editarMode);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuId = item.getItemId();

        switch (menuId){

            case R.id.action_editar:
                setEditarMode(true);
                //validator.validate();
                //Toast.makeText(this, notasCompuestas.getId()+"", Toast.LENGTH_LONG).show();
                break;

            case R.id.action_eliminar:
                if(manejoUsuarios.mEliminarUsuario(usuarioSelecionado)){
                    setResult(DELETE_RESULT);
                    finish();
                }

                break;

            case android.R.id.home:
                if(editarMode){
                    setEditarMode(false);
                    mostrarUsuario();
                }else {
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setEditarMode(boolean editarMode){
        this.editarMode = editarMode;
        etUsername.setFocusableInTouchMode(editarMode);
        etUsername.setFocusable(editarMode);

        etPassword.setVisibility(editarMode ? View.VISIBLE : View.GONE);
        etConfirmPassword.setVisibility(editarMode ? View.VISIBLE : View.GONE);
        btnGuardarCambios.setVisibility(editarMode ? View.VISIBLE : View.GONE);

        if(editarMode){
            etUsername.selectAll();
        }
        else {
            etUsername.setSelection(0);
        }

        getSupportActionBar().setHomeAsUpIndicator(editarMode ? R.drawable.ic_close : R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle(editarMode ? "Actualizar información": "Información de Usuario");
        invalidateOptionsMenu();

    }

    public void mostrarUsuario(){

        try {
            etUsername.setText(usuarioSelecionado.getNombre());
            etPassword.setText(usuarioSelecionado.getContrasena());
            etConfirmPassword.setText(usuarioSelecionado.getContrasena());
            tvCreadoPor.setText("Fecha de Creación: "+usuarioSelecionado.getCreadoPor());
            tvFechaCreacion.setText("Última modificación el: "+usuarioSelecionado.getFechaCreacion());
            tvUltimaodificacion.setText("Por: "+usuarioSelecionado.getFechaModificacion());
            tvModificadopor.setText("Creado Por: "+usuarioSelecionado.getModificadoPor());
        }catch (Exception e){
            Log.e("ERROR_MOSTRAR_INFO","Algun error ocurrio en la carga de datos", e);
        }

    }

    @Override
    public void onBackPressed() {
        if(editarMode){
            setEditarMode(false);
            mostrarUsuario();
        }else {
            finish();
        }

    }
}
