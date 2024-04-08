package com.TechSpecs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.TechSpecs.utils.AudioHelper;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class fragment_component_type extends Fragment {

    private Map<String, String> typeToResourceMap;
    private ListView listView;
    private AudioHelper audioHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioHelper = new AudioHelper(); // Inicializa AudioHelper
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_component_type, container, false);

        listView = view.findViewById(R.id.listViewComponentTypes);
        // Suponha que este array contém os tipos de componentes (ex: "motherboard", "processor", etc.)
        String[] componentTypes = getResources().getStringArray(R.array.computer_components);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, componentTypes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Pegue o tipo de componente selecionado baseado na posição do clique
                String selectedComponentType = componentTypes[position];
                openComponentModelFragment(selectedComponentType);
                audioHelper.playClickSound(getActivity());
            }
        });

        return view;
    }

    private String normalizer(String word) {
        String wordProcessed = Normalizer.normalize(word, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replace(" ", "_")
                .replace("-", "_");

        return wordProcessed;
    }

    private void openComponentModelFragment(String componentType) {
        fragment_component_model fragment = new fragment_component_model();

        // Passando o tipo de componente como um argumento
        Bundle args = new Bundle();
        args.putString("componentType", this.normalizer(componentType));
        fragment.setArguments(args);

        // Realizando a transação do fragmento
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null); // Permite que o usuário volte para o fragmento anterior
        transaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        audioHelper.releaseMediaPlayer();
    }
}