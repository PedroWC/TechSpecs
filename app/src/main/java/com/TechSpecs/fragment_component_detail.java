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

    /**
     * Inicializa o helper de áudio.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioHelper = new AudioHelper();
    }

    /**
     * Cria e retorna a hierarquia de view associada com o fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_component_detail, container, false);

        // Configurando a exibição dos detalhes do componente
        setupComponentDetails(view);

        return view;
    }

    /**
     * Configura e exibe os detalhes do componente, incluindo imagem e descrição.
     */
    private void setupComponentDetails(View view) {
        ImageView imageViewComponent = view.findViewById(R.id.imageViewComponent);
        TextView textViewComponentName = view.findViewById(R.id.textViewComponentName);
        TextView textViewComponentDetail = view.findViewById(R.id.textViewComponentDetail);

        Bundle args = getArguments();
        if (args != null) {
            String modelName = args.getString("model", "");
            textViewComponentName.setText(modelName);

            String resourceName = normalizeResourceName(modelName);

            // Exibindo a descrição do componente
            displayComponentDescription(textViewComponentDetail, resourceName);

            // Exibindo a imagem do componente
            displayComponentImage(imageViewComponent, resourceName);
        }
    }

    /**
     * Normaliza o nome do recurso para uso como identificador.
     */
    private String normalizeResourceName(String resourceName) {
        return resourceName.toLowerCase().replaceAll("[ -]", "_").replaceAll("[!]", "");
    }

    /**
     * Exibe a descrição do componente baseado no identificador do recurso.
     */
    private void displayComponentDescription(TextView textView, String resourceName) {
        int stringResId = getResources().getIdentifier(resourceName, "string", getActivity().getPackageName());
        if (stringResId != 0) {
            textView.setText(getString(stringResId));
        } else {
            textView.setText("Descrição não disponível.");
        }
    }

    /**
     * Exibe a imagem do componente baseado no identificador do recurso.
     */
    private void displayComponentImage(ImageView imageView, String resourceName) {
        int imageResId = getResources().getIdentifier(resourceName, "drawable", getActivity().getPackageName());
        imageView.setImageResource(imageResId != 0 ? imageResId : R.drawable.ic_default_image);
    }

    /**
     * Libera recursos do player de mídia ao destruir o fragmento.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        audioHelper.releaseMediaPlayer();
    }
}
