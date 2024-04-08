package com.TechSpecs;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.TechSpecs.utils.AudioHelper;
import com.TechSpecs.utils.model.ModelAdapter;

public class fragment_component_model extends Fragment {

    private GridView gridView;
    private AudioHelper audioHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioHelper = new AudioHelper(); // Inicializa AudioHelper
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_component_model, container, false);

        gridView = view.findViewById(R.id.gridViewComponentModels);

        // Recebe o tipo de componente selecionado do ComponentTypeFragment
        Bundle args = getArguments();
        String componentType = args.getString("componentType", "");


        int resourceId = this.getResources().getIdentifier(componentType, "array", getActivity().getPackageName());
        String[] models = getResources().getStringArray(resourceId);
        int[] imageIds = new int[models.length];

        for (int i = 0; i < models.length; i++) {
            // Supondo que os nomes dos arquivos de imagem seguem a convenção de nomenclatura e são idênticos aos nomes dos modelos
            String modelName = models[i].toLowerCase().replaceAll("[ -]", "_");
            modelName = modelName.replaceAll("[!]", "");
            int imageId = getResources().getIdentifier(modelName, "drawable", getActivity().getPackageName());
            imageIds[i] = imageId != 0 ? imageId : R.drawable.ic_default_image;
        }

        ModelAdapter adapter = new ModelAdapter(getActivity(), models, imageIds);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aqui você pode passar mais detalhes sobre o modelo selecionado
                String selectedModel = models[position];
                openComponentDetailFragment(selectedModel);
                audioHelper.playClickSound(getActivity());
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        audioHelper.releaseMediaPlayer();
    }

    private int getResourceId(String componentType) {
        return getActivity().getResources().getIdentifier(componentType, "array", getActivity().getPackageName());
    }

    private void openComponentDetailFragment(String model) {
        fragment_component_detail fragment = new fragment_component_detail();

        Bundle args = new Bundle();
        args.putString("model", model); // Passando o modelo como um argumento
        fragment.setArguments(args);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}