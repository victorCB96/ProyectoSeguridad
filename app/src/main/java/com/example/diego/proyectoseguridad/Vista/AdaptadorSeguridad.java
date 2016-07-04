package com.example.diego.proyectoseguridad.Vista;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.diego.proyectoseguridad.R;

/**
 * Created by diego on 21/6/2016.
 */
public class AdaptadorSeguridad  extends RecyclerView.Adapter<AdaptadorSeguridad.SeguridadViewHolder> {

    private Cursor items;
    private View view;
    private GestionSeguridadFragment seguridadFragment;

    public static class SeguridadViewHolder extends RecyclerView.ViewHolder{
        //private TextView tvUsuario;
        private ImageButton detalle;
        private TextView tvRol;
        public SeguridadViewHolder(View itemView) {
            super(itemView);
            this.detalle=(ImageButton)itemView.findViewById(R.id.btn_detalle_rol);
            //this.tvUsuario=(TextView) itemView.findViewById(R.id.tvUsuario);
            //this.tvPassword=(TextView) itemView.findViewById(R.id.tvContrasena);
            this.tvRol=(TextView) itemView.findViewById(R.id.tvRol);
        }
    }

    public AdaptadorSeguridad(GestionSeguridadFragment seguridadFragment ) {
        this.seguridadFragment=seguridadFragment;
    }

    @Override
    public SeguridadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_roles,parent,false);
        return new SeguridadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SeguridadViewHolder holder, int position) {
        String info="";
        items.moveToPosition(position);

        info = items.getString(1);
        holder.tvRol.setText(info);
        holder.detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(seguridadFragment.getActivity(),DetalleRolActivity.class);
                seguridadFragment.startActivity(intent);
            }
        });
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
