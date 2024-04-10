package com.TechSpecs.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.TechSpecs.R;

public class ModelAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] models;
    private final int[] imageIds;

    public ModelAdapter(@NonNull Context context, String[] models, int[] imageIds) {
        super(context, R.layout.grid_item_model, models);
        this.context = context;
        this.models = models;
        this.imageIds = imageIds;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridViewItem = inflater.inflate(R.layout.grid_item_model, null, true);

        ImageView imageView = gridViewItem.findViewById(R.id.imageViewModel);
        TextView textView = gridViewItem.findViewById(R.id.textViewModelName);

        imageView.setImageResource(imageIds[position]);
        textView.setText(models[position]);

        return gridViewItem;
    }
}
