package com.example.diego.proyectoseguridad.Vista;

import android.database.Cursor;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.diego.proyectoseguridad.Modelo.RolVentana;
import com.example.diego.proyectoseguridad.Modelo.Variables;
import com.example.diego.proyectoseguridad.Modelo.clsManejoRoles;
import com.example.diego.proyectoseguridad.R;

import java.util.ArrayList;

/**
 * Created by roy on 01/07/16.
 */
public class AdaptadorVentanas extends RecyclerView.Adapter<AdaptadorVentanas.VentanasViewHolder>{

    private Cursor items;
    private View view;
    private AgregarRolActivity agregarRolActivity;
    private ArrayList<RolVentana> permisos;
    private DetalleRolActivity detalleRolActivity;
    private String idRol;
    private clsManejoRoles manejoRoles;

    public AdaptadorVentanas(AgregarRolActivity agregarRolActivity) {
        this.agregarRolActivity=agregarRolActivity;
        this.permisos=new ArrayList<RolVentana>();
        Variables.PERMISOS.clear();
        Variables.PERMISOS_CLASIFICACIONES.clear();
    }

    public AdaptadorVentanas(DetalleRolActivity detalleRolActivity, clsManejoRoles manejoRoles, String idRol) {
        this.detalleRolActivity=detalleRolActivity;
        this.permisos=new ArrayList<RolVentana>();
        this.idRol=idRol;
        this.manejoRoles=manejoRoles;
    }

    @Override
    public VentanasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roles_usuarios,parent,false);
        return new VentanasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VentanasViewHolder holder, final int position) {
        items.moveToPosition(position);
        holder.chRoles.setText(items.getString(1));
        //idVentana,ver,insertar,modificar,eliminar
        if(detalleRolActivity!=null){
            Cursor consulta= manejoRoles.getVentanasRoles(idRol);
            for(consulta.moveToFirst(); !consulta.isAfterLast(); consulta.moveToNext()){
                if(consulta.getInt(0)==items.getInt(0)){
                    if(consulta.getInt(1)==0 &&
                            consulta.getInt(4)==0 &&
                            consulta.getInt(3)==0 &&
                            consulta.getInt(2)==0){
                        holder.chRoles.setChecked(false);
                    }else {
                        holder.chRoles.setChecked(true);
                        //int idVentana, int ver, int modificar, int eliminar, int insertar
                        Variables.PERMISOS.add(new RolVentana(consulta.getInt(0),consulta.getInt(1),consulta.getInt(3),consulta.getInt(4),consulta.getInt(2)));
                    }//fin del if/else
                }
            }
        }

        holder.chRoles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PermisosVentanaFragment alertDialog = new PermisosVentanaFragment();
                if(isChecked){
                    items.moveToPosition(position);
                    alertDialog.setIdVentana(items.getInt(0));
                    alertDialog.setCh((AppCompatCheckBox) buttonView);
                    if(agregarRolActivity!=null){
                        alertDialog.show(agregarRolActivity.getSupportFragmentManager(),"dialogo_permisos");
                    }else {
                        alertDialog.show(detalleRolActivity.getSupportFragmentManager(),"dialogo_permisos");
                    }


                }else{
                    items.moveToPosition(position);
                    alertDialog.setIdVentana(items.getInt(0));
                    alertDialog.setCh((AppCompatCheckBox) buttonView);
                    alertDialog.eliminarCheck();
                }

            }
        });



        holder.chRoles.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PermisosVentanaFragment alertDialog = new PermisosVentanaFragment();
                items.moveToPosition(position);
                alertDialog.setIdVentana(items.getInt(0));
                alertDialog.setCh((AppCompatCheckBox) v.findViewById(R.id.chRoles));
                if(agregarRolActivity!=null){
                    alertDialog.show(agregarRolActivity.getSupportFragmentManager(),"dialogo_permisos");
                }else {
                    alertDialog.show(detalleRolActivity.getSupportFragmentManager(),"dialogo_permisos");
                }
                return false;

            }
        });
    }

    @Override
    public int getItemCount() {
        if(items != null)
            return items.getCount();
        return -1;
    }

    public static class VentanasViewHolder extends RecyclerView.ViewHolder{
        private android.support.v7.widget.AppCompatCheckBox chRoles;
        public VentanasViewHolder(View itemView) {
            super(itemView);
            this.chRoles=(AppCompatCheckBox) itemView.findViewById(R.id.chRoles);
        }
    }

    public void actualizarCursor(Cursor roles) {
        if (roles != null) {
            items = roles;
            notifyDataSetChanged();
        }
    }
}
