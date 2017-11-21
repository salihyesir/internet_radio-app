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

    int[] IMAGES = {R.drawable.best, R.drawable.ntv, R.drawable.show, R.drawable.superfm, R.drawable.kralfm, R.drawable.fenomen,
            R.drawable.radyo_d, R.drawable.mydonose, R.drawable.trt, R.drawable.virgin};

    String[] NAMES = {"Best FM", "NTV Radyo", "ShowFM", "SuperFM", "KralFM", "Radyo Fenomen", "Radyo D",
            "MydonoseFM", "TrtFM", "VirginRadyo"};

    String[] DESCRIPTIONS = {"deneme1", "deneme2", "deneme3", "deneme4", "deneme5", "deneme6", "deneme7", "deneme8"
            , "deneme9", "deneme10"};

    String[] radyoURL={"http://46.20.7.126:80","http://ntvrdfms.radyotvonline.com:80/dyg/ntvrd64/playlist.m3u8",
    "http://46.20.3.229:80","http://17753.live.streamtheworld.com/SUPER2.mp3",
            "http://kralwmp.radyotvonline.com:80","http://195.142.3.83/fenomen/fenomenweb_mpeg_128_home/icecast.audio",
            "http://188.138.1.203:80/radyod","http://17753.live.streamtheworld.com/RADIO_MYDONOSE.mp3",
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
