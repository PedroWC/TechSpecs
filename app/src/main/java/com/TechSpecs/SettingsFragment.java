package com.TechSpecs;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    /**
     * Infla o layout e inicializando os componentes na criação da view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Configurando o spinner de temas
        Spinner spinnerTheme = view.findViewById(R.id.spinner_theme);
        String[] themeOptions = {getString(R.string.claro), getString(R.string.escuro), getString(R.string.system_default)};
        ArrayAdapter<String> adapterTheme = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, themeOptions);
        adapterTheme.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTheme.setAdapter(adapterTheme);

        // Definindo ação para o botão "Aplicar"
        Button btnApply = view.findViewById(R.id.btnApplySettings);
        btnApply.setOnClickListener(v -> {
            int selectedThemeIndex = spinnerTheme.getSelectedItemPosition();

            // Aplicando as configurações selecionadas
            applySettings(selectedThemeIndex);
        });

        return view;
    }

    /**
     * Salva as configurações utilizando SharedPreferences
     */
    private void saveSettings(int themeMode) {
        SharedPreferences prefs = getActivity().getSharedPreferences("AppSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Theme", themeMode);
        editor.apply();
    }

    /**
     * Aplica as configurações de tema e preparando para reinício da atividade
     */
    private void applySettings(int themeMode) {
        // Configurando o modo noturno baseado na seleção do tema
        switch (themeMode) {
            case 0: // Modo Claro
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case 1: // Modo Escuro
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 2: // Sistema
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }

        // Salvando as configurações
        saveSettings(themeMode);

        // Reiniciando a atividade para aplicar as configurações
        if (getActivity() != null) {
            ((MainActivity) getActivity()).restartActivity();
        }
    }
}
