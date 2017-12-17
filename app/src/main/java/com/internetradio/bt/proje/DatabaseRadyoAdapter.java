package com.internetradio.bt.proje;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HakanKurt on 17.12.2017.
 */

public class DatabaseRadyoAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<RadyoFavModel> radyoList;

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
        TextView radyoAd, radyoUrl, radyoKategori;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row=convertView;
        ViewHolder holder=new ViewHolder();

        if(row==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout,null);

            holder.radyoAd=(TextView)row.findViewById(R.id.textHeaderFav);
            holder.imageView=(ImageView)row.findViewById(R.id.imageRadyoFav);
            holder.radyoKategori=(TextView)row.findViewById(R.id.textDescriptionFav);
            row.setTag(holder);
        }else{
            holder=(ViewHolder)row.getTag();
        }

        RadyoFavModel radyoFavModel=radyoList.get(position);

        holder.radyoAd.setText(radyoFavModel.getDbRadyoAd());
        holder.radyoKategori.setText(radyoFavModel.getDbRadyoKategori());

        byte[] radyoImage=radyoFavModel.getDbRadyoImg();
        Bitmap bitmap= BitmapFactory.decodeByteArray(radyoImage, 0, radyoImage.length);

        holder.imageView.setImageBitmap(bitmap);


        return row;
    }
}
