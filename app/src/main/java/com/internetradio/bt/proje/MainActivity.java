package com.internetradio.bt.proje;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    ImageButton b_mainplaybutton;
    public Button button_fav;
    public Button button_kategori;

    int[] IMAGES = {R.drawable.alem, R.drawable.ntv, R.drawable.show, R.drawable.superfm, R.drawable.kralfm, R.drawable.trafik,
            R.drawable.power, R.drawable.mydonose, R.drawable.trt, R.drawable.virgin};

    String[] NAMES = {"Alem FM", "NTV Radyo", "ShowFM", "SuperFM", "KralFM", "TrafikFM", "PowerFM",
            "MydonoseFM", "TrtFM", "VirginRadyo"};

    String[] DESCRIPTIONS = {"deneme1", "deneme2", "deneme3", "deneme4", "deneme5", "deneme6", "deneme7", "deneme8"
            , "deneme9", "deneme10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if the application has draw over other apps permission or not?
        //This permission is by default available for API<23. But for API > 23
        //you have to ask for the permission in runtime.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {


            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            initializeView();
        }

        //FAVORI
        button_fav=(Button)findViewById(R.id.mainfavorilerbutton);

        button_fav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent favIntent=new Intent(MainActivity.this,Favourites.class);
                startActivity(favIntent);
            }
        });

        //KATEGORİ
        button_kategori=(Button)findViewById(R.id.mainkategorilerbutton);

        button_kategori.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent kategoriIntent=new Intent(MainActivity.this,Category.class);
                startActivity(kategoriIntent);
            }
        });


        ListView listView = (ListView) findViewById(R.id.listView);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);
        b_mainplaybutton = (ImageButton) findViewById(R.id.mainplaybutton);
        b_mainplaybutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intocan = new Intent(MainActivity.this, Radio.class);
                    startActivity(intocan);
                }
        });

    }/*
* widget
* */

    private void initializeView() {
        findViewById(R.id.notify_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(MainActivity.this, FloatingViewService.class));
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                initializeView();
            } else { //Permission is not available
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView= getLayoutInflater().inflate(R.layout.kanal_layout,null);


            ImageView imageView=(ImageView) convertView.findViewById(R.id.imageKanal);
            TextView textView_header=(TextView) convertView.findViewById(R.id.textHeader);
            TextView textView_desc=(TextView) convertView.findViewById(R.id.textDescription);

            imageView.setImageResource(IMAGES[position]);
            textView_header.setText(NAMES[position]);
            textView_desc.setText(DESCRIPTIONS[position]);

            return convertView;
        }
    }

}

