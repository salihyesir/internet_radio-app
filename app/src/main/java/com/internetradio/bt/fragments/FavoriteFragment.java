package com.internetradio.bt.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.internetradio.bt.proje.CustomAdapter;
import com.internetradio.bt.proje.DatabaseRadyoAdapter;
import com.internetradio.bt.proje.MainActivity;
import com.internetradio.bt.proje.NotConnectionActivity;
import com.internetradio.bt.proje.R;
import com.internetradio.bt.proje.RadyoFavModel;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment{



    private static View rootView;
    ListView listView;
    public static ArrayList<RadyoFavModel> list;
    DatabaseRadyoAdapter adapter=null;

    public static int sans=0;



    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView = inflater.inflate(R.layout.fragment_favori, container, false);
         listView=(ListView)rootView.findViewById(R.id.listViewFav);
         list=new ArrayList<>();
         adapter=new DatabaseRadyoAdapter(getActivity(), R.layout.fav_radyo_item,list);
         listView.setAdapter(adapter);
         try {
             veriGetir();
             return rootView;
         }catch (NullPointerException e)
         {
             sans ++;
             //Sqllite ilk başta hangi cihaz da denenirse denensin kesin null gelicek bundan emin olduğumdan uygulama en başta iki
             //defa başlatılır birince null gelen ikince tam dolacaktır
             Intent intent = new Intent(getActivity(), MainActivity.class);
             getActivity().startActivity(intent);
             getActivity().finish();
             if(sans >2) {
                 Intent intent1 = new Intent(getActivity(), NotConnectionActivity.class);
                 getActivity().startActivity(intent1);
                 getActivity().finish();
             }
             return rootView;

         }

    }

    private void veriGetir() {

        //Veritabanından tüm verileri getir.
        Cursor cursor= CustomAdapter.sqLiteHelper.getData("SELECT * FROM RADYO");
        list.clear();

        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String radyoAd=cursor.getString(1);
            String radyoUrl=cursor.getString(2);
            byte[] image=cursor.getBlob(3);
            String radyoKategori=cursor.getString(4);

            list.add(new RadyoFavModel(id,radyoAd,radyoUrl,image,radyoKategori));
        }

        adapter.notifyDataSetChanged();

    }

}
