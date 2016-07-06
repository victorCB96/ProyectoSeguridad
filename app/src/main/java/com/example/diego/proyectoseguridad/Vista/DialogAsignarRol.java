package com.example.diego.proyectoseguridad.Vista;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.diego.proyectoseguridad.R;


/**
 * Created by chris on 02/07/16.
 */
public class DialogAsignarRol extends DialogFragment {

    private CharSequence[] roles;
    private boolean [] rolesChecked;
    private DialogInterface.OnMultiChoiceClickListener listener;

    public DialogAsignarRol(){
    }

    public static DialogAsignarRol newInstance(CharSequence[] nombreRoles, boolean[] rolesChecked){
        DialogAsignarRol dialog = new DialogAsignarRol();

        Bundle bundle = new Bundle();
        bundle.putCharSequenceArray("ROLES", nombreRoles);
        bundle.putBooleanArray("ROLES_CHECKED", rolesChecked);
        dialog.setArguments(bundle);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        rolesChecked = getArguments().getBooleanArray("ROLES_CHECKED");
        roles = getArguments().getCharSequenceArray("ROLES");

        return createDialog();
    }

    public AlertDialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        builder.setTitle("Tipo de usuario/Rol").setMultiChoiceItems(roles, rolesChecked, listener);

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            listener = (DialogInterface.OnMultiChoiceClickListener) activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(
                    activity.toString() +
                            " no implementó OnSimpleDialogListener");
        }
    }
}
