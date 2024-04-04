package com.TechSpecs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.GridView;
import android.widget.ListView;

import java.util.Objects;

public class fragment_component_model extends Fragment {

    private GridView gridView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_component_model, container, false);

        gridView = view.findViewById(R.id.gridViewComponentModels);

        // Recebe o tipo de componente selecionado do ComponentTypeFragment
        Bundle args = getArguments();
        String componentType = args.getString("componentType", "");

        // Substitua este array pelo carregamento dos modelos apropriados com base em componentType
        int resourceId = this.getResources().getIdentifier(componentType, "array", getActivity().getPackageName());
        String[] models = getResources().getStringArray(resourceId);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, models);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aqui você pode passar mais detalhes sobre o modelo selecionado
                String selectedModel = models[position];
                openComponentDetailFragment(selectedModel);
            }
        });

        return view;
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