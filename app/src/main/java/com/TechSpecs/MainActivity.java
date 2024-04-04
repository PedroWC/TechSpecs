package com.TechSpecs;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verifica se o app acabou de ser lançado e não está em processo de recriação (como rotação de tela)
        if (savedInstanceState == null) {
            // Inicia o primeiro fragmento (ex.: ComponentTypeFragment)
            fragment_component_type fragment = new fragment_component_type();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }
}
