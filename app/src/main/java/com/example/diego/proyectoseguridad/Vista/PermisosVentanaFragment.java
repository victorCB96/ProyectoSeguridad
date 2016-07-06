package com.example.diego.proyectoseguridad.Vista;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.example.diego.proyectoseguridad.Modelo.RolVentana;
import com.example.diego.proyectoseguridad.Modelo.Variables;
import com.example.diego.proyectoseguridad.R;

import java.util.ArrayList;

public class PermisosVentanaFragment extends DialogFragment {

    private int indice;
    private int idVentana;

    private android.support.v7.widget.AppCompatCheckBox chVer;
    private android.support.v7.widget.AppCompatCheckBox chEliminar;
    private android.support.v7.widget.AppCompatCheckBox chInsertar;
    private android.support.v7.widget.AppCompatCheckBox chModficar;
    private android.support.v7.widget.AppCompatCheckBox ch;
    Button btnCerrar;

    public PermisosVentanaFragment() {
        // Required empty public constructor
        indice= -1;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogo();
    }



    public AlertDialog createDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_permisos_ventana, null);
        builder.setView(v);

        if(Variables.PERMISOS.isEmpty()){
            Variables.PERMISOS.add(new RolVentana(getIdVentana(),0,0,0,0));
        }else{
            buscarVentanaPermisos();
            if(indice== -1){
                Variables.PERMISOS.add(new RolVentana(getIdVentana(),0,0,0,0));
            }else {
                if(Variables.PERMISOS.get(indice).getIdVentana()!=this.getIdVentana()){

                    Variables.PERMISOS.add(new RolVentana(getIdVentana(),0,0,0,0));
                }
            }

        }

        this.controlador(v);
        return builder.create();
    }//fin del metodo que crea el dialogo

    private void controlador(View v){

        chVer= (AppCompatCheckBox) v.findViewById(R.id.chVer);
        chEliminar= (AppCompatCheckBox) v.findViewById(R.id.chEliminar);
        chInsertar= (AppCompatCheckBox) v.findViewById(R.id.chInsertar);
        chModficar= (AppCompatCheckBox) v.findViewById(R.id.chModificar);
        btnCerrar=(Button) v.findViewById(R.id.btnCerrarDialogoPermisos);
        buscarVentanaPermisos();


        if(Variables.PERMISOS.get(indice).getIdVentana()==7){
            chInsertar.setEnabled(false);
            chModficar.setEnabled(false);
            chEliminar.setEnabled(false);
        }
        marcar(Variables.PERMISOS.get(indice).getVer(), Variables.PERMISOS.get(indice).getEliminar(), Variables.PERMISOS.get(indice).getModificar(), Variables.PERMISOS.get(indice).getInsertar());

            chVer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //buscarVentanaPermisos();
                    if(isChecked){
                        Variables.PERMISOS.get(indice).setVer(1);
                    }else{
                        Variables.PERMISOS.get(indice).setVer(0);
                    }
                }
            });//Fin del metodo que escucha el checkbox

            chEliminar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //buscarVentanaPermisos();
                    if(isChecked){
                            Variables.PERMISOS.get(indice).setEliminar(1);
                            Log.i("indice: ",""+ indice);
                            Log.i("tamano: ",""+ Variables.PERMISOS.size());

                    }else{
                        Variables.PERMISOS.get(indice).setEliminar(0);
                    }
                }
            });//Fin del metodo que escucha el checkbox

            chModficar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //buscarVentanaPermisos();
                    if(isChecked){
                        Variables.PERMISOS.get(indice).setModificar(1);
                    }else{
                        Variables.PERMISOS.get(indice).setModificar(0);
                    }
                }
            });//Fin del metodo que escucha el checkbox

            chInsertar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //buscarVentanaPermisos();
                    if(isChecked){
                        Variables.PERMISOS.get(indice).setInsertar(1);
                    }else{
                        Variables.PERMISOS.get(indice).setInsertar(0);
                    }
                }
            });//Fin del metodo que escucha el checkbox


        btnCerrar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Variables.PERMISOS.get(indice).getVer()==0 &&
                        Variables.PERMISOS.get(indice).getEliminar()==0 &&
                        Variables.PERMISOS.get(indice).getModificar()==0 &&
                        Variables.PERMISOS.get(indice).getInsertar()==0)
                            ch.setChecked(false);
                        dismiss();
                    }
                }
        );
    }//fin del metodo controlador

    public void marcar(int ver,int eliminar,int modificar,int insertar){
        if(ver==1)
            this.chVer.setChecked(true);
        if(eliminar==1)
            this.chEliminar.setChecked(true);
        if(modificar==1)
            this.chModficar.setChecked(true);
        if(insertar==1)
            this.chInsertar.setChecked(true);
    }

    private void buscarVentanaPermisos(){
        if(!Variables.PERMISOS.isEmpty()){
            for (int i=0; i< Variables.PERMISOS.size();i++) {
                if (Variables.PERMISOS.get(i).getIdVentana() == getIdVentana()){
                    indice = i;
                    break;
                }
            }
        }

    }

    public void eliminarCheck(){
        buscarVentanaPermisos();
        Variables.PERMISOS.remove(indice);
    }

    public int getIdVentana() {
        return idVentana;
    }

    public void setIdVentana(int idVentana) {
        this.idVentana = idVentana;
    }

    public void setCh(AppCompatCheckBox ch) {
        this.ch = ch;
    }
}
