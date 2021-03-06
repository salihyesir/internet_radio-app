package com.internetradio.bt.proje;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by HakanKurt on 17.12.2017.
 */

public class DatabaseRadyoAdapter extends BaseAdapter {

    private static int temp=-1;
    private Context context;
    private int layout;
    private ArrayList<RadyoFavModel> radyoList;

    Radio radio = new Radio();

    //SQLite
    public static SQLiteHelper sqLiteHelper;

    public DatabaseRadyoAdapter(Context context, int layout, ArrayList<RadyoFavModel> radyoList) {
        this.context = context;
        this.layout = layout;
        this.radyoList = radyoList;
    }

    @Override
    public int getCount() {
        return radyoList.size();
    }

    @Override
    public Object getItem(int position) {
        return radyoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        ImageView radyoDurum;
        TextView radyoAd, radyoUrl, radyoKategori;
        ImageButton deleteButton;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row=convertView;
        ViewHolder holder=new ViewHolder();

        if(row==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout,null);

            //Delete button
            holder.deleteButton=(ImageButton)row.findViewById(R.id.imageDelButton);
            holder.radyoDurum=(ImageView) row.findViewById(R.id.favpp_btn);

            holder.radyoAd=(TextView)row.findViewById(R.id.textHeaderFav);
            holder.imageView=(ImageView)row.findViewById(R.id.imageRadyoFav);
            holder.radyoKategori=(TextView)row.findViewById(R.id.textDescriptionFav);

            row.setTag(holder);
        }else{
            holder=(ViewHolder)row.getTag();
        }

        final RadyoFavModel radyoFavModel=radyoList.get(position);

        holder.radyoAd.setText(radyoFavModel.getDbRadyoAd());
        holder.radyoKategori.setText(radyoFavModel.getDbRadyoKategori());

        byte[] radyoImage=radyoFavModel.getDbRadyoImg();
        Bitmap bitmap= BitmapFactory.decodeByteArray(radyoImage, 0, radyoImage.length);

        holder.imageView.setImageBitmap(bitmap);

        holder.deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    CustomAdapter.sqLiteHelper.deleteData(radyoFavModel.getId());

                    radyoList.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "İşlem tamam!", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Log.e("error",e.getMessage());
                }
            }

        });

// Bu kısın yarım kaldı.
        final ViewHolder finalHolder = holder;
        holder.radyoDurum.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if(position != temp) {
                    radio.playRadioPlayer(radyoList.get(position).getDbRadyoUrl());
                    finalHolder.radyoDurum.setVisibility(position);
                    finalHolder.radyoDurum.setImageResource(R.mipmap.ic_pause);
                }
                else if(position == temp) {
                    radio.stopRadioPlayer();
                    finalHolder.radyoDurum.setImageResource(R.mipmap.ic_play);
                }
                temp = position;

                for(int i=0; i<getCount();i++ ) {

                    if (position==i)
                        continue;

                    finalHolder.radyoDurum.setVisibility(position);
                }


            }
        });
        return row;
    }
}
