package com.internetradio.bt.proje;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    ImageButton b_mainplaybutton;

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

/*

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Intent intocan = new Intent(MainActivity.this, Radio.class);
                //startActivity(intocan);



                AlertDialog.Builder diyalogOlusturucu =
                        new AlertDialog.Builder(MainActivity.this);

                diyalogOlusturucu.setMessage(NAMES[position])
                        .setCancelable(false)
                        .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                diyalogOlusturucu.create().show();

            }

        });
  */
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

