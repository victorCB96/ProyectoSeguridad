package com.example.diego.proyectoseguridad.Vista;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.view.menu.MenuView;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.proyectoseguridad.Modelo.clsManejoRoles;
import com.example.diego.proyectoseguridad.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView usuarioCorreo;
    private int idUsuario; //este es el id de la consulta del login que sirve para obtener el rol en habilitarVentanas.
    private Cursor consulta;

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

    public void habilitarVentanas(View view,String idUsuario){
        clsManejoRoles roles = new clsManejoRoles(view.getContext());
        int idRol=0,idVentana=0,usuario=0;
        consulta= roles.consultarRoles(idUsuario);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        MenuItem item = navigationView.getMenu().getItem(1);


        try {
            for(consulta.moveToFirst(); !consulta.isAfterLast(); consulta.moveToNext()){
                usuario=consulta.getInt(0);
                idRol=consulta.getInt(1);
                idVentana=consulta.getInt(2);
            }
            if (consulta!=null){
                // metodo que habilita las ventanas correpondientes
                if(usuario==1) {
                    //MenuView.ItemView item = (MenuView.ItemView) view.findViewById(R.id.nav_prueba);
                    //item.setEnabled(true);
                    Toast.makeText(this,"entro admin",
                            Toast.LENGTH_SHORT).show();
                }else{

                    item.setEnabled(false);
                }

            }else {
                Toast.makeText(this,"Ocurrió un error fatal, Cursor vacio!",
                        Toast.LENGTH_SHORT).show();
            }
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
        } else if (id == R.id.nav_slideshow) {

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
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }*/
}
