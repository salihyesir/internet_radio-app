package com.internetradio.bt.proje;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class Radio extends AppCompatActivity {

    int pos=0;

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    ImageButton b_radiochatbutton;
    pl.droidsonroids.gif.GifImageView ritim;

    /*private String streamUrl = "http://sc.powergroup.com.tr/RadyoFenomen/mpeg/128/tunein";*/
    private static String streamUrl = "";
    public static String STREAM="";
    ImageView ppButton;//Play pause button
    private int controlButton=0;//Play_pause kontorolü

    private static String radyoAd=null;
    private static String radyoImg=null;

    private MediaPlayer player = null;
    public static boolean isAlreadyPlaying = false;
    public static boolean ChatRadio = false;
    private SeekBar volumeBar = null;
    private AudioManager audioManager = null;


    pl.droidsonroids.gif.GifImageView equ;


    @Override
    protected void onResume() {
        super.onResume();

         if(isAlreadyPlaying)
        {
            //Buraya gelirse on pausedan çıkmıştır radyo ekranındaysa chat de değildir
            ChatRadio=false;
            stopRadioPlayer();//player bir resetlensin sebebide chatden çıkınça radyo baştan başlar release edilmediğinden.
            playRadioPlayer();
        }
        else{
            stopRadioPlayer();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);



        //Veri geçirme-START
        datapassing();




        //Radyo otomatik baslatma-start
        isAlreadyPlaying=true;
        controlButton=1;
        //Radyo linki kontrol
        initializeMediaPlayer();

        //onstop dan geri dönüşte radio çalışmıyor olabilir.

        ppButton = (ImageView) findViewById(R.id.radiopp_btn);
        ppButton.setImageResource(R.mipmap.ic_pause);


        equ = (pl.droidsonroids.gif.GifImageView) findViewById(R.id.gifImageView3);


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
                    isAlreadyPlaying=true;
                    playRadioPlayer();
                    equ.setVisibility(View.VISIBLE);
                }
                //Pause radio
                else if(controlButton==1){
                    Toast.makeText(Radio.this, "Pausing the radio.", Toast.LENGTH_LONG).show();
                    ppButton.setImageResource(R.mipmap.ic_play);
                    controlButton=0;
                    isAlreadyPlaying=false;
                    stopRadioPlayer();
                    equ.setVisibility(View.INVISIBLE);
                }

            }
        });

        b_radiochatbutton = (ImageButton) findViewById(R.id.radiochatbutton);
        b_radiochatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString(STREAM,streamUrl);
                Intent intocan = new Intent(Radio.this, Chat.class);
                ChatRadio=true;
                intocan.putExtras(extras);
                startActivity(intocan);

            }
        });

        Intent i = new Intent();
        i.putExtra("stream", streamUrl);
        setResult(1453,i);

    }

    private void datapassing() {

        /*final CustomAdapter adapter=new CustomAdapter(this,arrayList);*/
        final ImageView img=(ImageView)findViewById(R.id.notify_me);
        final TextView kanalAd=(TextView)findViewById(R.id.radio_name) ;
        final TextView kanalDescription=(TextView)findViewById(R.id.radio_description);

        Intent intent=getIntent();
        //pos=intent.getExtras().getInt("Position");
        streamUrl=intent.getStringExtra("streamUrl");
        radyoAd=intent.getStringExtra("radyoAd");
        radyoImg=intent.getStringExtra("radyoImg");

        kanalAd.setText(radyoAd);
        Picasso.with(getApplicationContext()).load(radyoImg).into(img);
        /*img.setImageResource();*//*
        kanalAd.setText(adapter.getItem(pos).getRadyoAd());
        *//*kanalDescription.setText(adapter.DESCRIPTIONS[pos]);*//*
        streamUrl=adapter.getItem(pos).getRadyoUrl();*/
    }


    public void playRadioPlayer() {

        if (player.isPlaying())
            player.stop();

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
        //Chatde değil ise durdur
        if (ChatRadio == false)
        {
            if (player.isPlaying()) {
                player.stop();
            }
        }
        /*
         if (player.isPlaying()) {
            player.stop();
        }
        */
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
