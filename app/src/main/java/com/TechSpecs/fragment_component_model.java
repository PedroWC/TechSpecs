package com.TechSpecs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.TechSpecs.utils.AudioHelper;
import com.TechSpecs.utils.adapter.ModelAdapter;


public class fragment_component_model extends Fragment {

    private GridView gridView;
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
        View view = inflater.inflate(R.layout.fragment_component_model, container, false);
        gridView = view.findViewById(R.id.gridViewComponentModels);

        // Configurando o gridView com modelos baseados no tipo de componente selecionado
        setupGridView();

        return view;
    }

    /**
     * Configura e popula o gridView com modelos e imagens.
     */
    private void setupGridView() {
        Bundle args = getArguments();
        String componentType = args != null ? args.getString("componentType", "") : "";

        int resourceId = getResources().getIdentifier(componentType, "array", getActivity().getPackageName());
        String[] models = getResources().getStringArray(resourceId);
        int[] imageIds = mapModelNamesToImageIds(models);

        ModelAdapter adapter = new ModelAdapter(getActivity(), models, imageIds);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedModel = models[position];
            openComponentDetailFragment(selectedModel);
            audioHelper.playClickSound(getActivity()); // Tocando som ao selecionar um item
        });
    }

    /**
     * Mapeia os nomes dos modelos para os IDs de imagem correspondentes.
     */
    private int[] mapModelNamesToImageIds(String[] models) {
        int[] imageIds = new int[models.length];
        for (int i = 0; i < models.length; i++) {
            String modelName = normalizeModelName(models[i]);
            int imageId = getResources().getIdentifier(modelName, "drawable", getActivity().getPackageName());
            imageIds[i] = imageId != 0 ? imageId : R.drawable.ic_default_image;
        }
        return imageIds;
    }

    /**
     * Normaliza o nome do modelo para uso como identificador de recurso.
     */
    private String normalizeModelName(String modelName) {
        return modelName.toLowerCase().replaceAll("[ -]", "_").replaceAll("[!]", "");
    }

    /**
     * Abre o fragmento de detalhes do componente para o modelo selecionado.
     */
    private void openComponentDetailFragment(String model) {
        fragment_component_detail fragment = new fragment_component_detail();
        Bundle args = new Bundle();
        args.putString("model", model);
        fragment.setArguments(args);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment)
                .addToBackStack(null) // Permitindo voltar para o fragmento anterior
                .commit();
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
