package com.internetradio.bt.proje;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Salih on 9.12.2017.
 */

public class Radio  {

    private static MediaPlayer player;

    //Bundle sayfalar arası geçiş
    private static String streamUrl = null;
    public static String stream="";
    public static boolean isAlreadyPlaying = false;
    public static int controlButton;


    public void playRadioPlayer(String streamRadio)
    {

        streamUrl = streamRadio;

        if(isAlreadyPlaying)
            stopRadioPlayer();

        initializeMediaPlayer();

        player.prepareAsync();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });
        controlButton=1;
        isAlreadyPlaying=true;

    }

    // initializeMediaPlayer() methodunda urli  stream ediyoruz.

    public void initializeMediaPlayer() {
        player = new MediaPlayer();
        try {

            if (streamUrl == null)
                streamUrl = "http://windows.showradyo.com.tr";
            player.setDataSource(streamUrl);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void stopRadioPlayer() {
        try {
            if (player.isPlaying() == true && player != null) {
                player.stop();
                player.release();
                initializeMediaPlayer();
            }
            isAlreadyPlaying = false;
            controlButton = 0;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
