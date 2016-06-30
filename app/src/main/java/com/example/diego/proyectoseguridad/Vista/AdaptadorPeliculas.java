package com.example.diego.proyectoseguridad.Vista;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.diego.proyectoseguridad.Modelo.Pelicula;
import com.example.diego.proyectoseguridad.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 09/06/16.
 */
public class AdaptadorPeliculas extends RecyclerView.Adapter<AdaptadorPeliculas.ViewHolder> {

    private ArrayList<Pelicula> peliculas;


    public AdaptadorPeliculas() {
    }

    public AdaptadorPeliculas(ArrayList<Pelicula> peliculas){
        this.peliculas = peliculas;
        notifyDataSetChanged();
    }

    @Override
    public AdaptadorPeliculas.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_pelicula,parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pelicula pelicula = peliculas.get(position);

        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(pelicula.getUrlImagen()))
                .centerCrop()
                .into(holder.imgPelicula);

        Log.i("URL_IMAGEN",pelicula.getUrlImagen());

        holder.txtTitulo.setText(pelicula.getNombre());
        holder.txtGeneros.setText(pelicula.getGeneros());
    }


    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    public void actualizarPeliculas(ArrayList<Pelicula> peliculas)
    {
        this.peliculas = peliculas;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        public ImageView imgPelicula;
        public TextView txtTitulo;
        public TextView txtGeneros;

        public ViewHolder(View itemView) {
            super(itemView);

            imgPelicula = (ImageView) itemView.findViewById(R.id.img_view_pelicula);
            txtTitulo = (TextView) itemView.findViewById(R.id.text_view_nombre);
            txtGeneros = (TextView) itemView.findViewById(R.id.text_view_genero);
        }
    }


}
