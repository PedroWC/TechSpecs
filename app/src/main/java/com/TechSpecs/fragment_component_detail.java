package com.TechSpecs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.TechSpecs.model.ComponentModel;

public class fragment_component_detail extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_component_detail, container, false);

        ImageView imageViewComponent = view.findViewById(R.id.imageViewComponent);
        TextView textViewComponentDetail = view.findViewById(R.id.textViewComponentDetail);

        Bundle args = getArguments();
        if (args != null) {
            String modelName = args.getString("model", "");
            textViewComponentDetail.setText(modelName); // Define o texto com o nome do modelo

            // O nome da imagem é o mesmo que o nome do modelo
            int imageResId = getResources().getIdentifier(modelName.toLowerCase().replaceAll("[ -]", "_"), "drawable", getActivity().getPackageName());
            if (imageResId != 0) { // Se a imagem for encontrada
                imageViewComponent.setImageResource(imageResId);
            } else {
                // Caso o recurso não seja encontrado, você pode definir uma imagem padrão
                imageViewComponent.setImageResource(R.drawable.ic_default_image); // Exemplo de imagem padrão
            }
        }

        return view;
    }

}