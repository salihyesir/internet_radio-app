package com.internetradio.bt.proje;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class Radio extends AppCompatActivity {

    Button b_radioOn;

    MediaPlayer mediaPlayer;

    boolean prepared = false;
    boolean started = false;

    String stream="http://streams.fluxfm.de/klubradio/mp3-320";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        b_radioOn = (Button) findViewById(R.id.b_radioOn);
        b_radioOn.setEnabled(false);
        b_radioOn.setText(" _ ");
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        new PlayerTask().execute(stream);

        b_radioOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started){
                    mediaPlayer.start();
                    b_radioOn.setText(" p ");
                }
                else{
                    started=true;
                    mediaPlayer.start();
                    b_radioOn.setText("pause");
                }
            }
        });

    }
    class PlayerTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings)
        {
            try {

                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.prepare();
                prepared=true;
            }catch (IOException e) {
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean){
            super.onPostExecute(aBoolean);

            b_radioOn.setEnabled(true);
            b_radioOn.setText(" Playing ");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (started)
            mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (started)
            mediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (prepared)
            mediaPlayer.release();
    }
}
