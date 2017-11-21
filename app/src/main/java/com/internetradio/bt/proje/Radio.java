package com.internetradio.bt.proje;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class Radio extends AppCompatActivity {

    int pos=0;

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    ImageButton b_radiochatbutton;

    /*private String streamUrl = "http://sc.powergroup.com.tr/RadyoFenomen/mpeg/128/tunein";*/
    private String streamUrl = "rtmp://46.20.7.97:80/saran/trafik.stream/trafik.stream";

    ImageView ppButton;//Play pause button
    private int controlButton=0;//Play_pause kontorolü

    private MediaPlayer player;
    public static boolean isAlreadyPlaying = false;
    private SeekBar volumeBar = null;
    private AudioManager audioManager = null;




    @Override
    protected void onResume() {
        super.onResume();

        if(isAlreadyPlaying){

            playRadioPlayer();
        }else{
            stopRadioPlayer();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);



        //Radyo otomatik baslatma-start
        isAlreadyPlaying=true;
        controlButton=1;

        ppButton = (ImageView) findViewById(R.id.radiopp_btn);
        ppButton.setImageResource(R.mipmap.ic_pause);
        //startBtn.setEnabled(false);
        //stopBtn.setEnabled(true);
        //Radyo otomatik baslatma-end

        //Veri geçirme-START

        Intent intent=getIntent();
        pos=intent.getExtras().getInt("Position");

        final CustomAdapter adapter=new CustomAdapter(this);
        final ImageView img=(ImageView)findViewById(R.id.notify_me);
        final TextView kanalAd=(TextView)findViewById(R.id.radio_name) ;
        final TextView kanalDescription=(TextView)findViewById(R.id.radio_description);


        img.setImageResource(adapter.IMAGES[pos]);
        kanalAd.setText(adapter.NAMES[pos]);
        kanalDescription.setText(adapter.DESCRIPTIONS[pos]);
        streamUrl=adapter.radyoURL[pos];




        //Radyo linki kontrol
        initializeMediaPlayer();

        //Ses ayarları
        volumeControl();



        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // uyku modunu kapatma





        ppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playing radio
                if(controlButton == 0)
                {
                    Toast.makeText(Radio.this, "Playing the radio.", Toast.LENGTH_LONG).show();
                    ppButton.setImageResource(R.mipmap.ic_pause);
                    controlButton=1;
                    playRadioPlayer();
                }
                //Pause radio
                else if(controlButton==1){
                    Toast.makeText(Radio.this, "Pausing the radio.", Toast.LENGTH_LONG).show();
                    ppButton.setImageResource(R.mipmap.ic_play);
                    controlButton=0;
                    stopRadioPlayer();
                }

            }
        });




        b_radiochatbutton = (ImageButton) findViewById(R.id.radiochatbutton);
        b_radiochatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intocan = new Intent(Radio.this, Chat.class);
                startActivity(intocan);
            }
        });

    }


    public void playRadioPlayer() {

        //stopBtn.setEnabled(true);
        //startBtn.setEnabled(false);
        player.prepareAsync();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });
    }

    public void stopRadioPlayer() {

        if (player.isPlaying()) {
            player.stop();
            player.release();
            initializeMediaPlayer();
        }

        //startBtn.setEnabled(true);
        //stopBtn.setEnabled(false);
    }
// initializeMediaPlayer() methodunda urli  stream ediyoruz.

    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        try {
            player.setDataSource(streamUrl);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player.isPlaying()) {
            player.stop();
        }
    }

    private void volumeControl() {

        try {
            volumeBar = (SeekBar) findViewById(R.id.radioseekBar2);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

            volumeBar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeBar.setKeyProgressIncrement(10);
            volumeBar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
