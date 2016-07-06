package com.example.diego.proyectoseguridad.Vista;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.diego.proyectoseguridad.R;

/**
 * Created by roy on 18/06/16.
 */
public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.UsuariosViewHolder> {

    private Cursor items;

    private OnclickListener listener;

    public interface OnclickListener{
        void onClick(int posicion, int idUsuario, View view);
    }

    public  class UsuariosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvUsuario;
        public TextView tvCreadoPor;
        public FrameLayout contenedor;

        public UsuariosViewHolder(View itemView) {
            super(itemView);
            this.tvUsuario=(TextView) itemView.findViewById(R.id.tv_nom_usuario);
            this.tvCreadoPor=(TextView) itemView.findViewById(R.id.tv_nom_creado_por);
            this.contenedor=(FrameLayout)itemView.findViewById(R.id.container_layout);
            contenedor.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(getAdapterPosition(), getIdUsuario(getAdapterPosition()),view);
        }
    }

    public AdaptadorUsuarios() {
    }

    public int getIdUsuario(int posicion){

        if(items !=null){
            if(items.moveToPosition(posicion)) {
                return items.getInt(0);
            }
            else return -1;
        }
        else return -1;
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

        info = items.getString(3);
        holder.tvCreadoPor.setText(info);

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

    public void setListener(OnclickListener listener) {
        this.listener = listener;
    }

}
