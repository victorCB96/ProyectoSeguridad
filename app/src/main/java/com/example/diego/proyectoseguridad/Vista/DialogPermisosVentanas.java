package com.example.diego.proyectoseguridad.Vista;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diego.proyectoseguridad.Modelo.Ventana;
import com.example.diego.proyectoseguridad.R;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

import java.util.ArrayList;

/**
 * Created by chris on 03/07/16.
 */
public class DialogPermisosVentanas  extends DialogFragment implements RecyclerViewExpandableItemManager.OnGroupCollapseListener,
        RecyclerViewExpandableItemManager.OnGroupExpandListener{

    private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";
    private RecyclerView recyclerViewVentanas;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerViewExpandableItemManager recyclerViewExpandableItemManager;
    private ArrayList<Ventana> listVentanas;
    private ExpandableExampleAdapter.CheckPrmisosListener listner;
    private boolean editMode = false;

    public DialogPermisosVentanas(){
        super();
    }

    public static DialogPermisosVentanas newInstance(ArrayList<Ventana> listVentanas){
        DialogPermisosVentanas dialogPermisosVentanas = new DialogPermisosVentanas();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("VENTANAS",listVentanas);
        dialogPermisosVentanas.setArguments(bundle);
        return dialogPermisosVentanas;
    }

    public static DialogPermisosVentanas newInstance(ArrayList<Ventana> listVentanas, boolean editMode){
        DialogPermisosVentanas dialogPermisosVentanas = new DialogPermisosVentanas();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("VENTANAS",listVentanas);
        bundle.putBoolean("EDIT_MODE",editMode);
        dialogPermisosVentanas.setArguments(bundle);
        return dialogPermisosVentanas;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        listVentanas = getArguments().getParcelableArrayList("VENTANAS");
        editMode = getArguments().getBoolean("EDIT_MODE");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle("Elegir permisos a ventanas");
        View v = inflater.inflate(R.layout.fragment_dialog_permiso_ventana, null);
        builder.setView(v);

        //noinspection ConstantConditions
        recyclerViewVentanas = (RecyclerView) v.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());

        final Parcelable eimSavedState = (savedInstanceState != null) ? savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;
        recyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);
        recyclerViewExpandableItemManager.setOnGroupExpandListener(this);
        recyclerViewExpandableItemManager.setOnGroupCollapseListener(this);

        //adapter

        final ExpandableExampleAdapter myItemAdapter = new ExpandableExampleAdapter(listVentanas, editMode);
        myItemAdapter.setOnCheckPermisosListener(listner);

        adapter = recyclerViewExpandableItemManager.createWrappedAdapter(myItemAdapter);       // wrap for expanding
        final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();

        // Change animations are enabled by default since support-v7-recyclerview v22.
        // Need to disable them when using animation indicator.
        animator.setSupportsChangeAnimations(false);

        recyclerViewVentanas.setLayoutManager(layoutManager);
        recyclerViewVentanas.setAdapter(adapter);  // requires *wrapped* adapter
        recyclerViewVentanas.setItemAnimator(animator);
        recyclerViewVentanas.setHasFixedSize(false);

        recyclerViewVentanas.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(getContext(), R.drawable.list_divider_h), true));
        recyclerViewExpandableItemManager.attachRecyclerView(recyclerViewVentanas);

        return builder.create();

    }


    @Override
    public void onGroupCollapse(int groupPosition, boolean fromUser) {

    }

    @Override
    public void onGroupExpand(int groupPosition, boolean fromUser) {
        if (fromUser) {
            adjustScrollPositionOnGroupExpanded(groupPosition);
            recyclerViewExpandableItemManager.collapseAll();
            recyclerViewExpandableItemManager.expandGroup(groupPosition);
        }
    }


    private void adjustScrollPositionOnGroupExpanded(int groupPosition) {
        int childItemHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.list_item_height);
        int topMargin = (int) (getActivity().getResources().getDisplayMetrics().density * 16); // top-spacing: 16dp
        int bottomMargin = topMargin; // bottom-spacing: 16dp

        recyclerViewExpandableItemManager.scrollToGroup(groupPosition, childItemHeight, topMargin, bottomMargin);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save current state to support screen rotation, etc...
        if (recyclerViewExpandableItemManager != null) {
            outState.putParcelable(
                    SAVED_STATE_EXPANDABLE_ITEM_MANAGER,
                    recyclerViewExpandableItemManager.getSavedState());
        }
    }

    @Override
    public void onDestroyView() {
        if (recyclerViewExpandableItemManager != null) {
            recyclerViewExpandableItemManager.release();
            recyclerViewExpandableItemManager = null;
        }

        if (recyclerViewVentanas != null) {
            recyclerViewVentanas.setItemAnimator(null);
            recyclerViewVentanas.setAdapter(null);
            recyclerViewVentanas = null;
        }

        if (adapter != null) {
            WrapperAdapterUtils.releaseAll(adapter);
            adapter = null;
        }
        layoutManager = null;

        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            listner = (ExpandableExampleAdapter.CheckPrmisosListener) activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(
                    activity.toString() +
                            " no implementó OnSimpleDialogListener");
        }
    }
}
