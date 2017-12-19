package com.internetradio.bt.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.internetradio.bt.proje.CustomAdapter;
import com.internetradio.bt.proje.FloatingViewService;
import com.internetradio.bt.proje.R;
import com.internetradio.bt.proje.Radio;
import com.internetradio.bt.proje.RadioModel;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class PopulerFragment extends Fragment{

    //Widget
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;


    private FirebaseAuth fAuth;
    private FirebaseUser firebaseUser;
    FragmentData fragmentData;
    String position;
    ImageView ppButton;//Play pause button
    ImageView nextButton;
    ImageView prevButton;

    public static int index=0;

    //Bundle sayfalar arası geçiş
    public static String streamUrl = null;
    public static String stream="";

    public static String widgetDurum = null;
    public static String durum="";


    Radio radio = new Radio();

    private static View rootView;

    public ListView listView;

    public static ArrayList<RadioModel> arrayList=new ArrayList<>();

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Radios");

    public PopulerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        if(Radio.isAlreadyPlaying) {
            ppButton.setImageResource(R.mipmap.ic_pause);
            radio.controlButton=1;

        }else
        {
            ppButton.setImageResource(R.mipmap.ic_play);
            radio.controlButton=0;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_populer,container,false);
        listView = (ListView) rootView.findViewById(R.id.listView);

        fAuth = FirebaseAuth.getInstance();
        firebaseUser = fAuth.getCurrentUser(); // authenticate olan kullaniciyi aliyoruz eger var ise


        //  Firebaseden veri çekme
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Radios");


        // Burdan devam edilecek. **********************************
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();

                for(DataSnapshot radioSnapshot: dataSnapshot.getChildren())
                {
                    RadioModel radio=radioSnapshot.getValue(RadioModel.class);
                    arrayList.add(radio);

                }
                //Çekilen verilerin lisview'e aktarımı
                try {
                    CustomAdapter adapter=new CustomAdapter(getActivity(),arrayList);
                    listView.setAdapter(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




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
                            position = arrayList.get(pos).getRadyoAd();
                            fragmentData = (FragmentData) getActivity();
                            fragmentData.subjectData(position);
                            ChatFragment chatFragment = new ChatFragment();
                            android.app.FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_pop, chatFragment);
                            transaction.addToBackStack(null);
                            // işlerimizi bitirelim
                            transaction.commit();
                            Toast.makeText(getActivity(), arrayList.get(pos).getRadyoAd() + " Chat Odasına Hoş Geldiniz", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), arrayList.get(pos).getRadyoAd() + " Chat odasına girmek için ilk önce login olunuz", Toast.LENGTH_SHORT).show();

                        }
                        count = 1;
                    }
                }
                //Radyo Bir kere tıklama
                else
                {
                    previousPosition=pos;
                    count=1;
                    previousMil=System.currentTimeMillis();

                    index = pos;
                    streamUrl=arrayList.get(pos).getRadyoUrl();
                    Toast.makeText(getActivity().getApplicationContext(), "Playing the radio.", Toast.LENGTH_LONG).show();
                    ppButton.setImageResource(R.mipmap.ic_pause);
                    radio.playRadioPlayer(streamUrl);
                }


            }
        });

        ppButton = (ImageView) rootView.findViewById(R.id.mainpp_btn);

        ppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playing radio
                if(radio.controlButton == 0)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Playing the radio.", Toast.LENGTH_LONG).show();
                    ppButton.setImageResource(R.mipmap.ic_pause);
                    radio.playRadioPlayer(streamUrl);
                }
                //Pause radio
                else if(radio.controlButton==1){
                    Toast.makeText(getActivity().getApplicationContext(), "Pausing the radio.", Toast.LENGTH_LONG).show();
                    ppButton.setImageResource(R.mipmap.ic_play);
                    radio.stopRadioPlayer();
                }

            }
        });


        if (Radio.isAlreadyPlaying == false)
            radio.initializeMediaPlayer();

        nextButton = (ImageView) rootView.findViewById(R.id.mainNext_btn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Radyo sonda ise
                index = index +1;
                int temp =arrayList.size();
                if (temp == index)
                {
                    index=0;
                    streamUrl = arrayList.get(index).getRadyoUrl();
                    radio.playRadioPlayer(streamUrl);
                    ppButton.setImageResource(R.mipmap.ic_pause);
                    Toast.makeText(getActivity().getApplicationContext(), "Playing the " + arrayList.get(index).getRadyoAd() + ". ", Toast.LENGTH_LONG).show();
                }
                //normal durum
                else {
                    streamUrl = arrayList.get(index).getRadyoUrl();
                    radio.playRadioPlayer(streamUrl);
                    ppButton.setImageResource(R.mipmap.ic_pause);
                    Toast.makeText(getActivity().getApplicationContext(), "Playing the " + arrayList.get(index).getRadyoAd() + ". ", Toast.LENGTH_LONG).show();
                }
            }
        });

        prevButton = (ImageView) rootView.findViewById(R.id.mainPrev_btn);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //radyo başta ise
                if (index == 0)
                {
                    index =arrayList.size()-1;
                  streamUrl = arrayList.get(index).getRadyoUrl();
                  radio.playRadioPlayer(streamUrl);
                  ppButton.setImageResource(R.mipmap.ic_pause);
                    Toast.makeText(getActivity().getApplicationContext(), "Playing the " + arrayList.get(index).getRadyoAd() + ". ", Toast.LENGTH_LONG).show();
                }
                //Normal durum
                else{
                    index = index - 1;

                    streamUrl = arrayList.get(index).getRadyoUrl();
                    radio.playRadioPlayer(streamUrl);
                    ppButton.setImageResource(R.mipmap.ic_pause);
                    Toast.makeText(getActivity().getApplicationContext(), "Playing the " + arrayList.get(index).getRadyoAd() + ". ", Toast.LENGTH_LONG).show();
                }
            }
        });


        //Widget
        //Check if the application has draw over other apps permission or not?
        //This permission is by default available for API<23. But for API > 23
        //you have to ask for the permission in runtime.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getActivity())) {
            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getActivity().getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            initializeView();
        }

        // Inflate the layout for this fragment
        return rootView;

    }


    @Override
    public void onPause() {
        super.onPause();
//        if (player.isPlaying()) {
            //player.stop();
            //stopRadioPlayer();
        //       }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
    /*
        * widget
        * */
    //Widget başlatıldığı nokta
    private void initializeView() {
        rootView.findViewById(R.id.music_playerlogo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Radio.isAlreadyPlaying == true){
                    durum="play";
                }
                else
                    durum = "pause";

                Bundle extras = new Bundle();
                extras.putString(stream,streamUrl);
                extras.putString(widgetDurum,durum);
                // String deneme="";
                //extras.putBoolean(deneme,false);
                //stream baştan çalışmasın diye
                radio.stopRadioPlayer();
                Intent intent = new Intent(getActivity().getApplicationContext(), FloatingViewService.class);
                intent.putExtras(extras);
                getActivity().finish();
                getActivity().startService(intent);
                getActivity().finish();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == 1453){
            streamUrl = data.getExtras().get("stream").toString();
        }
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                initializeView();
            } else { //Permission is not available
                Toast.makeText(getActivity().getApplicationContext(), "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                getActivity().finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setImage(boolean ppBut){
        //if (ppBut)
            //ppButton.setImageResource(R.mipmap.ic_pause);

    }


}