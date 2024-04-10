package com.TechSpecs.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.TechSpecs.R;

public class AudioHelper {

    private MediaPlayer mediaPlayer;

    public void playClickSound(Context context) {
        // Liberando o MediaPlayer se ele já estiver em uso
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // Inicializando o MediaPlayer com o arquivo de som do clique
        mediaPlayer = MediaPlayer.create(context, R.raw.click_sound);

        mediaPlayer.setOnCompletionListener(mp -> {
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        });

        mediaPlayer.start(); // Tocando o som
    }

    public void playBackSound(Context context) {
        // Liberando o MediaPlayer se ele já estiver em uso
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // Inicializando o MediaPlayer com o arquivo de som do voltar
        mediaPlayer = MediaPlayer.create(context, R.raw.back_sound);

        mediaPlayer.setOnCompletionListener(mp -> {
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        });

        mediaPlayer.start(); // Toca o som
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
