package com.example.diego.proyectoseguridad.Vista;

import android.database.Cursor;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.diego.proyectoseguridad.Modelo.Rol;
import com.example.diego.proyectoseguridad.R;

import java.util.ArrayList;

/**
 * Created by roy on 29/06/16.
 */
public class AdaptadorRolesUsuario extends RecyclerView.Adapter<AdaptadorRolesUsuario.RolesUsuarioViewHolder>{
    private Cursor items;
    public static ArrayList<Integer> roles;

    public AdaptadorRolesUsuario() {
        roles= new ArrayList<Integer>();
    }

    public static class RolesUsuarioViewHolder extends RecyclerView.ViewHolder{
        //private TextView tvUsuario;
        private android.support.v7.widget.AppCompatCheckBox chRoles;
        public RolesUsuarioViewHolder(View itemView) {
            super(itemView);
            this.chRoles=(AppCompatCheckBox) itemView.findViewById(R.id.chRoles);
        }
    }

    @Override
    public RolesUsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roles_usuarios,parent,false);
        return new RolesUsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RolesUsuarioViewHolder holder, final int position) {
        items.moveToPosition(position);
        holder.chRoles.setText(items.getString(1));
        holder.chRoles.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    items.moveToPosition(position);
                    roles.add(items.getInt(0));
                }else {
                    roles.remove(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(items != null)
            return items.getCount();
        return -1;
    }

    public void actualizarCursor(Cursor roles) {
        if (roles != null) {
            items = roles;
            notifyDataSetChanged();
        }
    }
}
