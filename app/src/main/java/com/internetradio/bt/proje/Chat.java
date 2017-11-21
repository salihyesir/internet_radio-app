package com.internetradio.bt.proje;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import java.io.IOException;

public class Chat extends FragmentActivity {

    Radio radio = new Radio();
    String stream ="";
    private  Bundle extras = null;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);

        extras = getIntent().getExtras();
        stream = extras.getString(Radio.STREAM);

        MainFragment mainFragment=new MainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.realtabcontent, mainFragment);
        transaction.commit();

    }



}