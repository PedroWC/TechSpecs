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

import com.TechSpecs.utils.AudioHelper;

public class fragment_component_detail extends Fragment {


    private AudioHelper audioHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioHelper = new AudioHelper(); // Inicializa AudioHelper
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_component_detail, container, false);

        ImageView imageViewComponent = view.findViewById(R.id.imageViewComponent);
        TextView textViewComponentName = view.findViewById(R.id.textViewComponentName);
        TextView textViewComponentDetail = view.findViewById(R.id.textViewComponentDetail);



        Bundle args = getArguments();
        if (args != null) {
            String modelName = args.getString("model", "");
            // Texto do nome
            textViewComponentName.setText(modelName);

            String resourceName = modelName.toLowerCase().replaceAll("[ -]", "_");
            resourceName = resourceName.replaceAll("[!]", "");

            // Busca o ID do recurso de string
            int stringResId = getResources().getIdentifier(resourceName, "string", getActivity().getPackageName());
            if (stringResId != 0) { // Se o recurso de string for encontrado
                textViewComponentDetail.setText(getString(stringResId));
            } else { // Caso em que o recurso de string não é encontrado
                textViewComponentDetail.setText("Descrição não disponível."); // Definir um texto padrão ou deixar vazio
            }

            // Imagem
            int imageResId = getResources().getIdentifier(resourceName, "drawable", getActivity().getPackageName());
            if (imageResId != 0) { // Se a imagem for encontrada
                imageViewComponent.setImageResource(imageResId);
            } else { // Se imagem não for encontrada
                imageViewComponent.setImageResource(R.drawable.ic_default_image);
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        audioHelper.playBackSound(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        audioHelper.releaseMediaPlayer();
    }
}