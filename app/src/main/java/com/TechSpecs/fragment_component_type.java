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
import com.TechSpecs.utils.ModelListItem;
import com.TechSpecs.utils.adapter.TypeAdapter;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;


public class fragment_component_type extends Fragment {

    private ListView listView;
    private AudioHelper audioHelper;

    /**
     * Inicializa o helper de áudio.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioHelper = new AudioHelper(); // Inicializando AudioHelper
    }

    /**
     * Cria e retorna a hierarquia de view associada com o fragmento.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_component_type, container, false);

        // Configurando a lista de tipos de componentes
        setupComponentTypeList(view);

        return view;
    }

    /**
     * Configura e popula a lista de tipos de componentes.
     */
    private void setupComponentTypeList(View view) {
        listView = view.findViewById(R.id.listViewComponentTypes);
        String[] componentTypes = getResources().getStringArray(R.array.computer_components);

        List<ModelListItem> ModelListItem = new ArrayList<>();
        ModelListItem.add(new ModelListItem(R.drawable.motherboard, "Placa-mãe"));
        ModelListItem.add(new ModelListItem(R.drawable.processor, "Processador"));
        ModelListItem.add(new ModelListItem(R.drawable.gpu, "Placa de Vídeo"));
        ModelListItem.add(new ModelListItem(R.drawable.ram_memory, "Memória RAM"));
        ModelListItem.add(new ModelListItem(R.drawable.hard_disk_drive, "Disco Rígido"));
        ModelListItem.add(new ModelListItem(R.drawable.ssd, "SSD"));
        ModelListItem.add(new ModelListItem(R.drawable.power_supply, "Fonte de Alimentação"));
        ModelListItem.add(new ModelListItem(R.drawable.cpu_case, "Gabinete"));
        ModelListItem.add(new ModelListItem(R.drawable.cooler, "Cooler"));
        ModelListItem.add(new ModelListItem(R.drawable.wifi_router, "Placa de Rede"));

        TypeAdapter adapter = new TypeAdapter(getActivity(), ModelListItem);
        listView.setAdapter(adapter);


        // Configurando o evento de clique para abrir o fragmento do modelo do componente
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedComponentType = componentTypes[position];
            openComponentModelFragment(selectedComponentType);
            audioHelper.playClickSound(getActivity()); // Tocando som ao selecionar um item
        });
    }

    /**
     * Normaliza uma string para uso como chave ou identificador.
     */
    private String normalizer(String word) {
        return Normalizer.normalize(word, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replace(" ", "_")
                .replace("-", "_");
    }

    /**
     * Abre o fragmento do modelo do componente baseado no tipo selecionado.
     */
    private void openComponentModelFragment(String componentType) {
        fragment_component_model fragment = new fragment_component_model();

        Bundle args = new Bundle();
        args.putString("componentType", this.normalizer(componentType)); // Normalizando e passando o tipo de componente
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
        audioHelper.releaseMediaPlayer(); // Liberando o MediaPlayer
    }
}
