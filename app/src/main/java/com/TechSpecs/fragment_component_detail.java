package com.TechSpecs;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.speech.tts.TextToSpeech.OnInitListener;
import com.TechSpecs.model.ComponentModel;

import java.util.Locale;

public class fragment_component_detail extends Fragment implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;
    private CharSequence descriptionText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicializar Text-to-Speech
        textToSpeech = new TextToSpeech((Context) getActivity(), (TextToSpeech.OnInitListener) this);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_component_detail, container, false);

        ImageView imageViewComponent = view.findViewById(R.id.imageViewComponent);
        TextView textViewComponentName = view.findViewById(R.id.textViewComponentName);
        TextView textViewComponentDetail = view.findViewById(R.id.textViewComponentDetail);
        Button buttonSpeak = view.findViewById(R.id.buttonSpeak);

        buttonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });

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
                this.descriptionText = getString(stringResId);
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
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale Locale = null;
            int result = textToSpeech.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Lidar com a situação onde o idioma não é suportado.
            } else {
                // O idioma é suportado e os dados estão disponíveis.
            }
        } else {
            // Inicialização falhou.
        }
    }

    private void speakOut() {
        textToSpeech.speak(this.descriptionText, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    public void onDestroy() {
        // Fechar o Text-to-Speech quando o Fragment for destruído
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}