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

/**
 * Created by roy on 01/07/16.
 */
public class AdaptadorClasificaciones extends RecyclerView.Adapter<AdaptadorClasificaciones.ClasificacionesViewHolder> {
    private Cursor items;
    private int indice;
    private clsManejoRoles manejoRoles;
    private String idRol="hola";
    private DetalleRolActivity detalleRolActivity;
    private AgregarRolActivity agregarRolActivity;

    public AdaptadorClasificaciones(DetalleRolActivity detalleRolActivity,clsManejoRoles manejoRoles, String idRol) {
        indice=-1;
        this.manejoRoles=manejoRoles;
        this.detalleRolActivity=detalleRolActivity;
        this.idRol=idRol;
    }

    public AdaptadorClasificaciones(AgregarRolActivity agregarRolActivity) {
        this.agregarRolActivity=agregarRolActivity;
    }


    @Override
    public ClasificacionesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roles_usuarios,parent,false);
        return new ClasificacionesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClasificacionesViewHolder holder, final int position) {
        items.moveToPosition(position);
        holder.chRoles.setText(items.getString(1));
        if(detalleRolActivity!=null){
            Cursor consulta= manejoRoles.getClasificacionesRoles(idRol);
            for(consulta.moveToFirst(); !consulta.isAfterLast(); consulta.moveToNext()){
                if(consulta.getInt(1)==items.getInt(0)){
                    holder.chRoles.setChecked(true);
                    Variables.PERMISOS_CLASIFICACIONES.add(consulta.getInt(1));
                }
            }
        }

        holder.chRoles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    items.moveToPosition(position);
                    buscarClasificacionesPermisos(items.getInt(0));
                    /*if(Variables.PERMISOS_CLASIFICACIONES.isEmpty()){
                        Variables.PERMISOS_CLASIFICACIONES.add(new RolVentana(getIdVentana(),0,0,0,0));
                    }else{
                        ();
                        if(indice== -1){
                            Variables.PERMISOS.add(new RolVentana(getIdVentana(),0,0,0,0));
                        }else {
                            if(Variables.PERMISOS.get(indice).getIdVentana()!=this.getIdVentana()){

                                Variables.PERMISOS.add(new RolVentana(getIdVentana(),0,0,0,0));
                            }
                        }

                    }*/
                    if(Variables.PERMISOS_CLASIFICACIONES.isEmpty()){
                        Variables.PERMISOS_CLASIFICACIONES.add(items.getInt(0));
                        /*if(detalleRolActivity!=null){
                            if(!Variables.PERMISOS_CLASIFICACIONES_ELIMINACION.isEmpty()){
                                Variables.PERMISOS_CLASIFICACIONES_ELIMINACION.remove(indice);
                            }
                        }*/
                    }else{
                        buscarClasificacionesPermisos(items.getInt(0));
                        if(indice== -1){
                            Variables.PERMISOS_CLASIFICACIONES.add(items.getInt(0));
                            /*if(detalleRolActivity!=null){
                                if(!Variables.PERMISOS_CLASIFICACIONES_ELIMINACION.isEmpty()){
                                    Variables.PERMISOS_CLASIFICACIONES_ELIMINACION.remove(indice);
                                }
                            }*/
                        }else {
                        //if(Variables.PERMISOS_CLASIFICACIONES.get(indice)!=items.getInt(0)){
                            Variables.PERMISOS_CLASIFICACIONES.add(items.getInt(0));
                            /*if(detalleRolActivity!=null){
                                if(!Variables.PERMISOS_CLASIFICACIONES_ELIMINACION.isEmpty()){
                                    Variables.PERMISOS_CLASIFICACIONES_ELIMINACION.remove(indice);
                                }
                            }*/
                        //}

                        }
                    }//fin del if/else
                }else {
                    items.moveToPosition(position);
                    buscarClasificacionesPermisos(items.getInt(0));
                    //Log.i("borro: ", Variables.PERMISOS_CLASIFICACIONES.get(indice)+"");
                    Variables.PERMISOS_CLASIFICACIONES.remove(indice);
                    //Variables.PERMISOS_CLASIFICACIONES_ELIMINACION.add(items.getInt(0));
                }
            }
        });
    }

    private void buscarClasificacionesPermisos(int idClasificacion){
        if(!Variables.PERMISOS_CLASIFICACIONES.isEmpty()){
            for (int i=0; i< Variables.PERMISOS_CLASIFICACIONES.size();i++) {
                if (Variables.PERMISOS_CLASIFICACIONES.get(i) == idClasificacion){
                    indice = i;
                    break;
                }

            }
        }

    }

    @Override
    public int getItemCount() {
        if(items != null)
            return items.getCount();
        return -1;
    }

    public static class ClasificacionesViewHolder extends RecyclerView.ViewHolder{
        private android.support.v7.widget.AppCompatCheckBox chRoles;
        public ClasificacionesViewHolder(View itemView) {
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
