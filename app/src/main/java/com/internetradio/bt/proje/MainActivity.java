package com.internetradio.bt.proje;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    int[] IMAGES={R.drawable.alem,R.drawable.ntv,R.drawable.show,R.drawable.superfm,R.drawable.kralfm,R.drawable.trafik,
            R.drawable.power,R.drawable.mydonose,R.drawable.trt,R.drawable.virgin};

    String[] NAMES={"Alem FM","NTV Radyo","ShowFM","SuperFM","KralFM","TrafikFM","PowerFM",
                "MydonoseFM","TrtFM","VirginRadyo"};

    String[] DESCRIPTIONS={"deneme1","deneme2","deneme3","deneme4","deneme5","deneme6","deneme7","deneme8"
            ,"deneme9","deneme10"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView=(ListView)findViewById(R.id.listView);

        CustomAdapter customAdapter=new CustomAdapter();

        listView.setAdapter(customAdapter);
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
