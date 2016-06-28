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

import com.example.diego.proyectoseguridad.Modelo.clsManejoRoles;
import com.example.diego.proyectoseguridad.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView usuarioCorreo;
    private int idUsuario; //este es el id de la consulta del login que sirve para obtener el rol en habilitarVentanas.
    public static boolean PERMISO_EDITAR=false;
    public static boolean PERMISO_ELIMINAR=false;
    public static boolean PERMISO_AGREGAR=false;


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


         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
         navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);
        usuarioCorreo=(TextView)view.findViewById(R.id.usuarioCorreo);
        usuarioCorreo.setText(getIntent().getStringExtra("correo"));
        idUsuario=getIntent().getIntExtra("idUsuario",0);
        this.habilitarVentanas(view,String.valueOf(idUsuario));

    }

    private void habilitarVentanasPermisos(){

    }

    public void habilitarVentanas(View view,String idUsuario){
        clsManejoRoles roles = new clsManejoRoles(view.getContext());
        String idRol="";
        Cursor consulta=null;
        Cursor consultaUsuarioRoles= roles.getRoles(idUsuario);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        MenuItem item = navigationView.getMenu().getItem(1);
        MenuItem item2 = navigationView.getMenu().getItem(2);



        try {
            item.setEnabled(false);
            item2.setEnabled(false);
            for(consultaUsuarioRoles.moveToFirst(); !consultaUsuarioRoles.isAfterLast(); consultaUsuarioRoles.moveToNext()){
                idRol=String.valueOf(consultaUsuarioRoles.getInt(0));
                consulta= roles.consultarRoles(idRol);
                try{
                    for(consulta.moveToFirst(); !consulta.isAfterLast(); consulta.moveToNext()){

                            if(consulta.getString(0).equalsIgnoreCase("Gestion Usuarios")){
                                if(consulta.getInt(1)==1){
                                    item.setEnabled(true);

                                }else if (consulta.getInt(2)==1){//si tiene permiso de modificar

                                }else if (consulta.getInt(3)==1){//si tiene permiso de eliminar

                                }else if (consulta.getInt(4)==1){//si tiene permiso de insertar
                                    this.PERMISO_AGREGAR=true;
                                }

                            }else if(consulta.getString(0).equalsIgnoreCase("Gestion Roles")){
                                //item2.setEnabled(true);
                                if(consulta.getInt(1)==1){
                                    item.setEnabled(true);

                                }else if (consulta.getInt(2)==1){//si tiene permiso de modificar

                                }else if (consulta.getInt(3)==1){//si tiene permiso de eliminar

                                }else if (consulta.getInt(4)==1){//si tiene permiso de insertar
                                    this.PERMISO_AGREGAR=true;
                                }
                            }
                    }//fin del for que recorre los permisos de los roles
                }catch (CursorIndexOutOfBoundsException err){
                    Toast.makeText(this,"Ocurrió un error fatal"+err,
                            Toast.LENGTH_SHORT).show();
                }//Fin del try catch
            }//fin del for que recorre los roles que tiene un usuario








           /* if (consulta!=null){
                // metodo que habilita las ventanas correpondientes
                if(usuario==1) {
                    //MenuView.ItemView item = (MenuView.ItemView) view.findViewById(R.id.nav_prueba);
                    //item.setEnabled(true);
                    Toast.makeText(this,"entro admin",
                            Toast.LENGTH_SHORT).show();
                }else{

                    item.setEnabled(false);
                    item2.setEnabled(false);
                }

            }else {
                Toast.makeText(this,"Ocurrió un error fatal, Cursor vacio!",
                        Toast.LENGTH_SHORT).show();
            }*/
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

            fragmento = new FragmentClasificaciones();
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
