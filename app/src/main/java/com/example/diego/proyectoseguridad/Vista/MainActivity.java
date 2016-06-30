package com.example.diego.proyectoseguridad.Vista;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.proyectoseguridad.Modelo.Clasificacion;
import com.example.diego.proyectoseguridad.Modelo.Usuario;
import com.example.diego.proyectoseguridad.Modelo.Variables;
import com.example.diego.proyectoseguridad.Modelo.clsManejoRoles;
import com.example.diego.proyectoseguridad.Modelo.clsManejoUsuarios;
import com.example.diego.proyectoseguridad.Modelo.clsManejoClasificaciones;
import com.example.diego.proyectoseguridad.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView usuarioCorreo;
   // private int idUsuario; //este es el id de la consulta del login que sirve para obtener el rol en habilitarVentanas.

    private Usuario usuario;
    private Cursor usuarioRoles;
    private ArrayList<Clasificacion> clasificacionesUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer_toggle);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        usuario = getIntent().getParcelableExtra(LoginActivity.USUARIO);

        clsManejoRoles roles = new clsManejoRoles(this);
        usuarioRoles = roles.getRoles(String.valueOf(usuario.getIdUsuario()));
        setClasificacionesUsuario();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
            View view = navigationView.getHeaderView(0);

            if (view != null) {
                usuarioCorreo=(TextView) view.findViewById(R.id.usuarioCorreo);
                usuarioCorreo.setText(usuario.getNombre());

                this.habilitarVentanasRoles(navigationView,roles);
                this.habilitarVentanasPermisos(navigationView);

            }
        }

        seleccionarItem(navigationView.getMenu().getItem(0));


    }


    private  void setClasificacionesUsuario(){
        clsManejoClasificaciones manejoClasificaciones = new clsManejoClasificaciones(this);
        clasificacionesUsuario = (ArrayList<Clasificacion>) manejoClasificaciones.getClasificacionesUsuarios(usuarioRoles);

    }

    private void habilitarVentanasPermisos(NavigationView view){
        clsManejoUsuarios usuarios = new clsManejoUsuarios(view.getContext());
        Cursor consulta= usuarios.getPermisosDirectosUsuario(String.valueOf(usuario.getIdUsuario()));

        MenuItem item_usuario = view.getMenu().getItem(1);
        MenuItem item_seguridad = view.getMenu().getItem(2);
        MenuItem item_peliculas = view.getMenu().getItem(0);

        try {
            item_usuario.setEnabled(false);
            item_seguridad.setEnabled(false);
            item_peliculas.setEnabled(false);


            for(consulta.moveToFirst(); !consulta.isAfterLast(); consulta.moveToNext()){

                if(consulta.getString(0).toString().trim().equals( item_usuario.getTitle().toString().trim())){//consulta si tiene acceso a la ventana usuarios
                    if(consulta.getInt(1)==1){
                        item_usuario.setEnabled(true);
                    }

                    if (consulta.getInt(2)==1){//si tiene permiso de modificar
                        item_usuario.setEnabled(true);
                        Variables.PERMISO_EDITAR_USUARIOS=true;
                    }

                    if (consulta.getInt(3)==1){//si tiene permiso de eliminar
                        item_usuario.setEnabled(true);
                        Variables.PERMISO_ELIMINAR_USUARIOS=true;
                    }
                    if (consulta.getInt(4)==1){//si tiene permiso de insertar
                        item_usuario.setEnabled(true);
                        Variables.PERMISO_AGREGAR_USUARIOS=true;
                    }

                }else if(consulta.getString(0).toString().trim().equals( item_seguridad.getTitle().toString().trim())){//consulta si tiene acceso a la ventana roles
                    if(consulta.getInt(1)==1){
                        item_seguridad.setEnabled(true);
                    }

                    if (consulta.getInt(2)==1){//si tiene permiso de modificar
                        item_seguridad.setEnabled(true);
                        Variables.PERMISO_EDITAR_ROLES=true;
                    }

                    if (consulta.getInt(3)==1){//si tiene permiso de eliminar
                        item_seguridad.setEnabled(true);
                        Variables.PERMISO_ELIMINAR_ROLES=true;
                    }

                    if (consulta.getInt(4)==1){//si tiene permiso de insertar
                        item_seguridad.setEnabled(true);
                        Variables.PERMISO_AGREGAR_ROLES=true;
                    }


                }else if(consulta.getString(0).toString().trim().equals( item_peliculas.getTitle().toString().trim())){//consulta si tiene acceso a la ventana peliculas
                    //item2.setEnabled(true);
                    if(consulta.getInt(1)==1){
                        item_peliculas.setEnabled(true);
                    }
                }//fin del if
            }//fin del for que recorre los privilegios del usuario
        }catch (CursorIndexOutOfBoundsException err){
            Toast.makeText(this,"Ocurrió un error fatal"+err,
                    Toast.LENGTH_SHORT).show();
        }//Fin del try catch
    }//fin del metodo



    public void habilitarVentanasRoles(NavigationView view, clsManejoRoles roles){

        String idRol="";
        Cursor consulta=null;

        MenuItem item_usuario = view.getMenu().getItem(1);
        MenuItem item_seguridad = view.getMenu().getItem(2);
        MenuItem item_peliculas = view.getMenu().getItem(0);


        try {
            item_usuario.setEnabled(false);
            item_seguridad.setEnabled(false);
            item_peliculas.setEnabled(false);

            for(usuarioRoles.moveToFirst(); !usuarioRoles.isAfterLast(); usuarioRoles.moveToNext()){
                idRol=String.valueOf(usuarioRoles.getInt(0));
                consulta= roles.consultarRoles(idRol);
                try{
                    for(consulta.moveToFirst(); !consulta.isAfterLast(); consulta.moveToNext()){

                            if(consulta.getString(0).toString().trim().equals( item_usuario.getTitle().toString().trim())){//consulta si tiene acceso a la ventana usuarios
                                if(consulta.getInt(1)==1){
                                    item_usuario.setEnabled(true);
                                }

                                if (consulta.getInt(2)==1){//si tiene permiso de modificar
                                    item_usuario.setEnabled(true);
                                    Variables.PERMISO_EDITAR_USUARIOS=true;
                                }

                                if (consulta.getInt(3)==1){//si tiene permiso de eliminar
                                    item_usuario.setEnabled(true);
                                    Variables.PERMISO_ELIMINAR_USUARIOS=true;
                                }
                                if (consulta.getInt(4)==1){//si tiene permiso de insertar
                                    item_usuario.setEnabled(true);
                                    Variables.PERMISO_AGREGAR_USUARIOS=true;
                                }

                            }else if(consulta.getString(0).toString().trim().equals( item_seguridad.getTitle().toString().trim())){//consulta si tiene acceso a la ventana roles
                                if(consulta.getInt(1)==1){
                                    item_seguridad.setEnabled(true);
                                }

                                if (consulta.getInt(2)==1){//si tiene permiso de modificar
                                    item_seguridad.setEnabled(true);
                                    Variables.PERMISO_EDITAR_ROLES=true;
                                }

                                if (consulta.getInt(3)==1){//si tiene permiso de eliminar
                                    item_seguridad.setEnabled(true);
                                    Variables.PERMISO_ELIMINAR_ROLES=true;
                                }

                                if (consulta.getInt(4)==1){//si tiene permiso de insertar
                                    item_seguridad.setEnabled(true);
                                    Variables.PERMISO_AGREGAR_ROLES=true;
                                }


                            }else if(consulta.getString(0).toString().trim().equals( item_peliculas.getTitle().toString().trim())){//consulta si tiene acceso a la ventana peliculas
                                //item2.setEnabled(true);
                                if(consulta.getInt(1)==1){
                                    item_peliculas.setEnabled(true);
                                }
                            }//fin del if
                    }//fin del for que recorre los permisos de los roles
                }catch (CursorIndexOutOfBoundsException err){
                    Toast.makeText(this,"Ocurrió un error fatal"+err,
                            Toast.LENGTH_SHORT).show();
                }//Fin del try catch
            }//fin del for que recorre los roles que tiene un usuario
        }catch (CursorIndexOutOfBoundsException err){
            Toast.makeText(this,"Ocurrió un error fatal"+err,
                    Toast.LENGTH_SHORT).show();
        }//Fin del try catch


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        item.setChecked(true);
        seleccionarItem(item);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void seleccionarItem(MenuItem itemDrawer) {

        Fragment fragmento = null;

        // Handle navigation view item clicks here.
        int id = itemDrawer.getItemId();

        if (id == R.id.nav_peliculas) {

            fragmento = FragmentClasificaciones.newInstance(clasificacionesUsuario);
            // Handle the camera action
        } else if (id == R.id.nav_usuarios) {
            fragmento= new GestionUsuariosFragment();
        } else if (id == R.id.nav_roles) {
            fragmento= new GestionSeguridadFragment();
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_exit_session) {
            finish();
            Intent intent= new Intent(this,LoginActivity.class);
            startActivity(intent);
        }

        if(fragmento != null){
            getSupportFragmentManager()
                    .beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.conten_main,fragmento)
                    .commit();

            itemDrawer.setChecked(true);
            getSupportActionBar().setTitle(itemDrawer.getTitle());
        }
    }//fin del metodo seleccionarItem

}
