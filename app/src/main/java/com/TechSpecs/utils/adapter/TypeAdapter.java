package com.TechSpecs.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.TechSpecs.R;
import com.TechSpecs.utils.ModelListItem;

import java.util.List;

public class TypeAdapter extends ArrayAdapter<ModelListItem> {
    public TypeAdapter(Context context, List<ModelListItem> components) {
        super(context, 0, components);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_item, parent, false);
        }

        ImageView imageViewIcon = convertView.findViewById(R.id.imageViewIcon);
        TextView textViewComponentName = convertView.findViewById(R.id.textViewComponentName);

        ModelListItem item = getItem(position);
        imageViewIcon.setImageResource(item.getIconResourceId());
        textViewComponentName.setText(item.getComponentName());

        return convertView;
    }
}
