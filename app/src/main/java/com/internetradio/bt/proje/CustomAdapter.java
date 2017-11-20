package com.internetradio.bt.proje;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by HakanKurt on 20.11.2017.
 */

public class CustomAdapter extends BaseAdapter {

    Context c;

    int[] IMAGES = {R.drawable.alem, R.drawable.ntv, R.drawable.show, R.drawable.superfm, R.drawable.kralfm, R.drawable.trafik,
            R.drawable.power, R.drawable.mydonose, R.drawable.trt, R.drawable.virgin};

    String[] NAMES = {"Alem FM", "NTV Radyo", "ShowFM", "SuperFM", "KralFM", "Radyo Fenomen", "PowerFM",
            "MydonoseFM", "TrtFM", "VirginRadyo"};

    String[] DESCRIPTIONS = {"deneme1", "deneme2", "deneme3", "deneme4", "deneme5", "deneme6", "deneme7", "deneme8"
            , "deneme9", "deneme10"};

    String[] radyoURL={"http://mn-l.mncdn.com/alemfm/alemfm1/playlist.m3u8","http://ntvrdfms.radyotvonline.com:80/dyg/ntvrd64/playlist.m3u8",
    "rtmp://37.247.100.100:80/show/show64","http://17753.live.streamtheworld.com/SUPER_FM.mp3",
            "http://kralfms.radyotvonline.com:80/dyg/kralfm64/playlist.m3u8 ","http://195.142.3.83/fenomen/fenomenweb_mpeg_128_home/icecast.audio",
            "http://sc.powergroup.com.tr:80/PowerFM/mpeg/128/home","http://17753.live.streamtheworld.com/RADIO_MYDONOSE.mp3",
            "http://trtcanlifm-lh.akamaihd.net/i/TRTFM_1@181846/master.m3u8","http://17733.live.streamtheworld.com/VIRGIN_RADIO.mp3"

    };

    public CustomAdapter(Context ctx){
        this.c=ctx;
    }

    @Override
    public int getCount() {
        return NAMES.length;
    }

    @Override
    public Object getItem(int pos) {
        return NAMES[pos];
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.kanal_layout,null);
        }


        ImageView imageView=(ImageView) convertView.findViewById(R.id.imageKanal);
        TextView textView_header=(TextView) convertView.findViewById(R.id.textHeader);
        TextView textView_desc=(TextView) convertView.findViewById(R.id.textDescription);


        imageView.setImageResource(IMAGES[pos]);
        textView_header.setText(NAMES[pos]);
        textView_desc.setText(DESCRIPTIONS[pos]);


        return convertView;
    }
}
