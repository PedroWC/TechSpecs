package com.TechSpecs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentTransaction;

import com.TechSpecs.utils.AudioHelper;
import com.TechSpecs.utils.ThemeConstants;

public class MainActivity extends AppCompatActivity {

    // Criando instancia do AudioHelper para gerenciar sons
    private AudioHelper audioHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Carregando o tema a partir das preferências e aplicando
        applySavedTheme();

        setContentView(R.layout.activity_main);

        // Configurando a toolbar como a barra de ações da atividade
        setupToolbar();

        audioHelper = new AudioHelper();

        // Carregando o fragmento inicial se a atividade está sendo criada pela primeira vez
        loadInitialFragment(savedInstanceState);
    }

    /**
     * Carrega e aplica o tema salvo nas preferências compartilhadas.
     */
    private void applySavedTheme() {
        SharedPreferences prefs = getSharedPreferences("AppSettings", MODE_PRIVATE);
        int theme = prefs.getInt("Theme", 0);
        switch (theme) {
            case ThemeConstants.LIGHT_MODE:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case ThemeConstants.DARK_MODE:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case ThemeConstants.SYSTEM_DEFAULT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    /**
     * Configura a Toolbar da atividade.
     */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Carrega o fragmento inicial somente se a atividade estiver sendo criada pela primeira vez,
     * evitando recarregar o fragmento durante reinicializações (como rotações de tela).
     */
    private void loadInitialFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragment_component_type fragment = new fragment_component_type();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, fragment);
            transaction.commit();
        }
    }

    /**
     *  Gerencia a seleção de itens de menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflando o menu; adiciona itens à barra de ação se ela estiver presente.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     *  Infla o menu da aplicação.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Manipulando cliques de item do menu de ações
        if (item.getItemId() == R.id.op_conf) {
            openSettingsFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Uma função dedicada para abrir o SettingsFragment, melhorando a clareza.
     */
    private void openSettingsFragment() {
        // Abrindo o fragmento de configurações
        SettingsFragment settingsFragment = new SettingsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, settingsFragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Reinicia a atividade atual.
     * Útil para aplicar mudanças que requerem recarregamento completo da UI, como mudança de tema.
     */
    public void restartActivity() {
        // Reiniciando a atividade para aplicar mudanças (por exemplo, tema)
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    /**
     * Sobrescrita para tocar um som específico quando o usuário pressiona o botão voltar.
     */
    @Override
    public void onBackPressed() {
        // Tocando som de volta e chamando a implementação original
        audioHelper.playBackSound(this);
        super.onBackPressed();
    }
}
