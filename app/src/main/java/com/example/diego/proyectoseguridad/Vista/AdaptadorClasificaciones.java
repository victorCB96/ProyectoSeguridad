package com.example.diego.proyectoseguridad.Vista;

import android.database.Cursor;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.diego.proyectoseguridad.Modelo.RolVentana;
import com.example.diego.proyectoseguridad.Modelo.Variables;
import com.example.diego.proyectoseguridad.R;

/**
 * Created by roy on 01/07/16.
 */
public class AdaptadorClasificaciones extends RecyclerView.Adapter<AdaptadorClasificaciones.ClasificacionesViewHolder> {
    private Cursor items;
    private int indice;

    public AdaptadorClasificaciones() {
        indice=-1;
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

        holder.chRoles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    items.moveToPosition(position);
                    buscarClasificacionesPermisos(items.getInt(0));
                    if(Variables.PERMISOS_CLASIFICACIONES.isEmpty()){
                        Variables.PERMISOS_CLASIFICACIONES.add(items.getInt(0));
                    }else{
                        if(indice== -1){
                            Variables.PERMISOS_CLASIFICACIONES.add(items.getInt(0));
                        }else {
                            if(Variables.PERMISOS_CLASIFICACIONES.get(indice)!=items.getInt(0)){

                                Variables.PERMISOS_CLASIFICACIONES.add(items.getInt(0));
                            }
                        }
                    }//fin del if/else
                }else {
                    items.moveToPosition(position);
                    buscarClasificacionesPermisos(items.getInt(0));
                    Variables.PERMISOS_CLASIFICACIONES.remove(indice);
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
