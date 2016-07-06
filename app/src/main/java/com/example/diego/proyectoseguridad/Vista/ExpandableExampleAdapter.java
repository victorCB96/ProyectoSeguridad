/*
 *    Copyright (C) 2015 Haruki Hasegawa
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.example.diego.proyectoseguridad.Vista;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.diego.proyectoseguridad.Modelo.Ventana;
import com.example.diego.proyectoseguridad.R;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

import java.util.ArrayList;

class ExpandableExampleAdapter
        extends AbstractExpandableItemAdapter<ExpandableExampleAdapter.MyGroupViewHolder, ExpandableExampleAdapter.MyChildViewHolder> {

    private static final String TAG = "MyExpandableItemAdapter";
    private ArrayList<Ventana> ventanas;
    private ExpandableExampleAdapter.CheckPrmisosListener listener;
    private boolean editableMode = false;


    public interface CheckPrmisosListener{
        void onCheck(int groupItem, int childItem, boolean isCheck);
    }

    // NOTE: Make accessible with short name
    private interface Expandable extends ExpandableItemConstants {

    }


    public static abstract class MyBaseViewHolder extends AbstractExpandableItemViewHolder {
        public FrameLayout mContainer;
        public TextView mTextView;

        public MyBaseViewHolder(View v) {
            super(v);
            mContainer = (FrameLayout) v.findViewById(R.id.container);
            mTextView = (TextView) v.findViewById(android.R.id.text1);

        }
    }

    public static class MyGroupViewHolder extends MyBaseViewHolder {
        public ExpandableItemIndicator mIndicator;

        public MyGroupViewHolder(View v) {
            super(v);
            mIndicator = (ExpandableItemIndicator) v.findViewById(R.id.indicator);
        }
    }

    public   class MyChildViewHolder extends MyBaseViewHolder implements CompoundButton.OnCheckedChangeListener{
        public CheckBox checkedTextView;
        public int position;
        public int parentPosition;


        public MyChildViewHolder(View v) {
            super(v);
            checkedTextView = (CheckBox) v.findViewById(R.id.checkTextView);
            checkedTextView.setOnCheckedChangeListener(this);
            checkedTextView.setChecked(false);
            checkedTextView.setEnabled(editableMode);
        }


        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            listener.onCheck(parentPosition,position,b);
            ventanas.get(parentPosition).getPermiso(position).setActivado(b);
        }
    }

    public ExpandableExampleAdapter(ArrayList<Ventana> ventanas) {
        this.ventanas = ventanas;
        // ExpandableItemAdapter requires stable ID, and also
        // have to implement the getGroupItemId()/getChildItemId() methods appropriately.
        setHasStableIds(true);
    }

    public ExpandableExampleAdapter(ArrayList<Ventana> ventanas,boolean editableMode) {
        this.ventanas = ventanas;
        this.editableMode = editableMode;
        // ExpandableItemAdapter requires stable ID, and also
        // have to implement the getGroupItemId()/getChildItemId() methods appropriately.
        setHasStableIds(true);
    }

    @Override
    public int getGroupCount() {
        return ventanas.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return ventanas.get(groupPosition).cantPermisosAsignables();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return ventanas.get(groupPosition).getIdVentana();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
        return 0;
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public MyGroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_group_item, parent, false);
        return new MyGroupViewHolder(v);
    }

    @Override
    public MyChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_item, parent, false);
        return new MyChildViewHolder(v);
    }

    @Override
    public void onBindGroupViewHolder(MyGroupViewHolder holder, int groupPosition, int viewType) {
        // child item
        //final AbstractExpandableDataProvider.BaseData item = mProvider.getGroupItem(groupPosition);
        Ventana  ventana = ventanas.get(groupPosition);
        // set text
        holder.mTextView.setText(ventana.getNombre());

        // mark as clickable
        holder.itemView.setClickable(true);

        // set background resource (target view ID: container)
        final int expandState = holder.getExpandStateFlags();

        if ((expandState & ExpandableItemConstants.STATE_FLAG_IS_UPDATED) != 0) {
            int bgResId;
            boolean isExpanded;
            boolean animateIndicator = ((expandState & Expandable.STATE_FLAG_HAS_EXPANDED_STATE_CHANGED) != 0);

            if ((expandState & Expandable.STATE_FLAG_IS_EXPANDED) != 0) {
                bgResId = R.drawable.bg_group_item_expanded_state;
                isExpanded = true;

            } else {
                bgResId = R.drawable.bg_group_item_normal_state;
                isExpanded = false;
            }

            holder.mContainer.setBackgroundResource(bgResId);
            holder.mIndicator.setExpandedState(isExpanded, animateIndicator);
        }
    }

    @Override
    public void onBindChildViewHolder(MyChildViewHolder holder, int groupPosition, int childPosition, int viewType) {
        // group item
        Ventana.Permiso permisoVentana = ventanas.get(groupPosition).getPermiso(childPosition);
        holder.position=childPosition;
        holder.parentPosition=groupPosition;
        // set text
        holder.checkedTextView.setText(permisoVentana.getNombre());
        holder.checkedTextView.setChecked(permisoVentana.isActivado());

        // set background resource (target view ID: container)
        int bgResId;
        bgResId = R.drawable.bg_item_normal_state;
        holder.mContainer.setBackgroundResource(bgResId);
    }


    @Override
    public boolean onCheckCanExpandOrCollapseGroup(MyGroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
//
//          check the item is *not* pinned
//        if (mProvider.getGroupItem(groupPosition).isPinned()) {
//            return false to raise View.OnClickListener#onClick() event
//            return false;

        // check is enabled
        if (!(holder.itemView.isEnabled() && holder.itemView.isClickable())) {
            return false;
        }

        return true;
    }

    public void setOnCheckPermisosListener(CheckPrmisosListener listener){
        this.listener = listener;
    }

    public void actulizarDatos(ArrayList<Ventana> ventanas){
        this.ventanas = ventanas;
        notifyDataSetChanged();
    }

    public void setEditableMode(boolean editableMode){
        this.editableMode = editableMode;
    }


}
