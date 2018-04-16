package com.gowil.zzleep.utils;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by yaguar on 13/07/
 * Ejecuta el sonido asincronamente, pero bloquea hasta haber empezado a sonar
 */

public class MusicTask{
    public static MediaPlayer execute(String url){
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            return mediaPlayer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
