package com.internetradio.bt.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.internetradio.bt.proje.ConnectivityReceiver;
import com.internetradio.bt.proje.CustomAdapter;
import com.internetradio.bt.proje.DatabaseRadyoAdapter;
import com.internetradio.bt.proje.MainActivity;
import com.internetradio.bt.proje.NotConnectionActivity;
import com.internetradio.bt.proje.R;
import com.internetradio.bt.proje.Radio;
import com.internetradio.bt.proje.RadyoFavModel;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment{


    private FirebaseAuth fAuth;
    private FirebaseUser firebaseUser;

    private static View rootView;
    ListView listView;
    public static ArrayList<RadyoFavModel> list;
    DatabaseRadyoAdapter adapter=null;

    String streamUrl=null;


    Radio radio = new Radio();
    private String position;

    FragmentData fragmentData;
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


        fAuth = FirebaseAuth.getInstance();
        firebaseUser = fAuth.getCurrentUser(); // authenticate olan kullaniciyi aliyoruz eger var ise

        try {
             veriGetir();
         }catch (NullPointerException e)
         {

             //Sqllite ilk başta hangi cihaz da denenirse denensin kesin null gelicek bundan emin olduğumdan uygulama en başta iki
             //defa başlatılır birince null gelen ikince tam dolacaktır
             Intent intent = new Intent(getActivity(), MainActivity.class);
             getActivity().startActivity(intent);
             getActivity().finish();
             if(ConnectivityReceiver.isConnected() == false) {
                 Intent intent1 = new Intent(getActivity(), NotConnectionActivity.class);
                 getActivity().finish();
                 getActivity().startActivity(intent1);

             }
             return rootView;
         }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            int previousPosition=-1;
            int count=0;
            long previousMil=0;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                //Radyoya iki defa tıklama
                if(previousPosition==pos)
                {
                    count++;
                    if(count==2 && System.currentTimeMillis()-previousMil<=1000)
                    {
                        if(firebaseUser != null) {
                            position = list.get(pos).getDbRadyoAd();
                            fragmentData = (FragmentData) getActivity();
                            fragmentData.subjectData(position);
                            fragmentData.setImage(true);
                            ChatFragment chatFragment = new ChatFragment();
                            android.app.FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                            transaction.replace(R.id.favori_fragment, chatFragment);
                            transaction.addToBackStack(null);

                            // işlerimizi bitirelim
                            transaction.commit();
                            Toast.makeText(getActivity(), list.get(pos).getDbRadyoAd() + " Chat Odasına Hoş Geldiniz", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), list.get(pos).getDbRadyoAd() + " Chat odasına girmek için ilk önce login olunuz", Toast.LENGTH_SHORT).show();

                        }
                        count=1;
                    }
                }
                //Radyo Bir kere tıklama
                else
                {
                    previousPosition=pos;
                    count=1;
                    previousMil=System.currentTimeMillis();
                    fragmentData = (FragmentData) getActivity();
                    fragmentData.setImage(true);
                    // set drawable image like it
                    PopulerFragment.index = pos;
                    streamUrl=list.get(pos).getDbRadyoUrl();
                    Toast.makeText(getActivity().getApplicationContext(), "Playing the radio.", Toast.LENGTH_LONG).show();
                    radio.playRadioPlayer(streamUrl);
                }


            }
        });

        return rootView;
    }

    public void veriGetir() {
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
        cursor.close();
    }
}
