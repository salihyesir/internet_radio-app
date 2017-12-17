package com.internetradio.bt.proje;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;



public class CustomAdapter extends ArrayAdapter<RadioModel> {

    private Context context;
    private List<RadioModel> radioList;




    public CustomAdapter(@NonNull Context context, List<RadioModel> radioList){
        super(context,R.layout.kanal_layout,radioList);
        this.context=context;
        this.radioList=radioList;
    }


    /*int[] IMAGES = {R.drawable.best, R.drawable.ntv, R.drawable.show, R.drawable.superfm, R.drawable.kralfm, R.drawable.fenomen,
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

    };*/

    //SQLite
    public static SQLiteHelper sqLiteHelper;

    public ImageView image;
    public String imageUrl;

    public ImageButton button_fav;





    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.kanal_layout,null);
        }

        final RadioModel radioByPosition=radioList.get(pos);


        final ImageView imageView=(ImageView) convertView.findViewById(R.id.imageKanal);

        final TextView textView_header=(TextView) convertView.findViewById(R.id.textHeader);
        TextView textView_desc=(TextView) convertView.findViewById(R.id.textDescription);
        button_fav=(ImageButton)convertView.findViewById(R.id.imageFavButton);


        Picasso.with(context).load(radioByPosition.getRadyoImg()).into(imageView);
        /*imageView.setImageResource(IMAGES[pos]);*/
        textView_header.setText(radioByPosition.getRadyoAd());

        textView_desc.setText(radioByPosition.getRadyoDescription());
        /*textView_desc.setText(radioByPosition.getRadyoUrl());*/

        //SQLite işlemleri
        sqLiteHelper=new SQLiteHelper(getContext(),"RadyoDB.sqlite",null,1);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS RADYO(Id INTEGER PRIMARY KEY AUTOINCREMENT,radyoAd VARCHAR,radyoUrl VARCHAR,radyoImg BLOB,radyoKategori VARCHAR)");


        //final int position=listView.getPositionForView(rootView);
        // Favori butonuna tıklandığında
        button_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                try {
                    sqLiteHelper.insertData(
                            radioByPosition.getRadyoAd(),
                            radioByPosition.getRadyoUrl(),
                            ImageViewToByte(imageView),
                            radioByPosition.getRadyoKategori()
                    );
                    Toast.makeText(getContext(), "Favorilere eklendi!",Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });


        return convertView;
    }

    public static byte[] ImageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
