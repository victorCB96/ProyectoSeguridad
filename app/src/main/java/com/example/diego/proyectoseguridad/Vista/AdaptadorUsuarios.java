package com.example.diego.proyectoseguridad.Vista;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diego.proyectoseguridad.R;

/**
 * Created by roy on 18/06/16.
 */
public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.UsuariosViewHolder> {

    private Cursor items;

    public static class UsuariosViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUsuario;
        private TextView tvPassword;
        private TextView tvRol;
        public UsuariosViewHolder(View itemView) {
            super(itemView);
            this.tvUsuario=(TextView) itemView.findViewById(R.id.tvUsuario);
            this.tvPassword=(TextView) itemView.findViewById(R.id.tvContrasena);
            this.tvRol=(TextView) itemView.findViewById(R.id.tvRoles);
        }
    }

    public AdaptadorUsuarios() {
    }

    @Override
    public UsuariosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuarios,parent,false);
        return new UsuariosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsuariosViewHolder holder, int position) {
        String info="";
        items.moveToPosition(position);

        info = items.getString(1);
        holder.tvUsuario.setText(info);

        info = items.getString(2);
        holder.tvPassword.setText(info);

        info= "rol1";
        //items.getString(3);
        holder.tvRol.setText(info);
    }

    @Override
    public int getItemCount() {
        if(items != null)
            return items.getCount();
        return -1;
    }

    public void actualizarCursor(Cursor usuarios) {
        if (usuarios != null) {
            items = usuarios;
            notifyDataSetChanged();
        }
    }


}
