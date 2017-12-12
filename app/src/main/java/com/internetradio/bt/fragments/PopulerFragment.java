package com.internetradio.bt.fragments;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.internetradio.bt.proje.CustomAdapter;
import com.internetradio.bt.proje.FloatingViewService;
import com.internetradio.bt.proje.R;
import com.internetradio.bt.proje.RadioModel;

import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class PopulerFragment extends Fragment{

    //Widget
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;


    public Button button_fav;
    public Button button_kategori;
    ImageView ppButton;//Play pause button
    private int controlButton=0;//Play_pause kontorolü

    private MediaPlayer player;

    //Bundle sayfalar arası geçiş
    private static String streamUrl = null;
    public static String stream="";
    public static boolean isAlreadyPlaying = false;

    private static String streamUrl1=null;
    private static String radyoAd1=null;
    private static String radyoImg1=null;
    private static String radyoDescription1=null;

    private static View rootView;



    ArrayList<RadioModel> arrayList=new ArrayList<>();

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Radios");




    public PopulerFragment() {
        // Required empty public constructor
    }




    @Override
    public void onResume() {
        super.onResume();
        if(isAlreadyPlaying)
        {
            stopRadioPlayer();//player resetlensin
            initializeMediaPlayer();
            playRadioPlayer();
            ppButton.setImageResource(R.mipmap.ic_pause);
            controlButton=1;

        }else
        {
            ppButton.setImageResource(R.mipmap.ic_play);
            stopRadioPlayer();
            controlButton=0;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.fragment_one,container,false);

        //  Firebaseden veri çekme
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Radios");



        final ListView listView = (ListView) rootView.findViewById(R.id.listView);

        // Burdan devam edilecek. **********************************
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();

                for(DataSnapshot radioSnapshot: dataSnapshot.getChildren()){
                    RadioModel radio=radioSnapshot.getValue(RadioModel.class);
                    arrayList.add(radio);
                }
                //Çekilen verilerin lisview'e aktarımı
                CustomAdapter adapter=new CustomAdapter(getActivity(),arrayList);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                streamUrl=arrayList.get(pos).getRadyoUrl();
                Toast.makeText(getContext().getApplicationContext(), "Playing the radio.", Toast.LENGTH_LONG).show();
                ppButton.setImageResource(R.mipmap.ic_pause);
                controlButton=1;
                isAlreadyPlaying=true;
                playRadioPlayer();
            }
        });
        //Radyo linki kontrol
        initializeMediaPlayer();


        ppButton = (ImageView) rootView.findViewById(R.id.mainpp_btn);

        ppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playing radio
                if(controlButton == 0)
                {
                    Toast.makeText(getContext().getApplicationContext(), "Playing the radio.", Toast.LENGTH_LONG).show();
                    ppButton.setImageResource(R.mipmap.ic_pause);
                    controlButton=1;
                    isAlreadyPlaying=true;
                    playRadioPlayer();
                }
                //Pause radio
                else if(controlButton==1){
                    Toast.makeText(getContext().getApplicationContext(), "Pausing the radio.", Toast.LENGTH_LONG).show();
                    ppButton.setImageResource(R.mipmap.ic_play);
                    controlButton=0;
                    isAlreadyPlaying=false;
                    stopRadioPlayer();
                }

            }
        });



        //Widget
        //Check if the application has draw over other apps permission or not?
        //This permission is by default available for API<23. But for API > 23
        //you have to ask for the permission in runtime.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getContext())) {
            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getContext().getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            initializeView();
        }



        // Inflate the layout for this fragment
        return rootView;

    }

    // initializeMediaPlayer() methodunda urli  stream ediyoruz.

    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        try {

            if (streamUrl == null)
                streamUrl = "http://17753.live.streamtheworld.com/SUPER_FM.mp3";
            player.setDataSource(streamUrl);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void playRadioPlayer()
    {
        initializeMediaPlayer();
       //Başka bir radyo çalışıyorsa
        if(controlButton == 1)
            stopRadioPlayer();

        player.prepareAsync();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });
        controlButton=1;
        isAlreadyPlaying=true;
    }

    public void stopRadioPlayer() {
        isAlreadyPlaying=false;
        controlButton=0;
        if (player.isPlaying()) {
            player.stop();
            player.release();
            initializeMediaPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player.isPlaying()) {
            //player.stop();
            stopRadioPlayer();
        }
    }


    /*
    * widget
    * */
    //Widget başlatıldığı nokta
    private void initializeView() {
        rootView.findViewById(R.id.music_playerlogo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle extras = new Bundle();
                extras.putString(stream,streamUrl);

                // String deneme="";
                //extras.putBoolean(deneme,false);

                Intent intent = new Intent(getContext().getApplicationContext(), FloatingViewService.class);
                intent.putExtras(extras);
                getContext().startService(intent);
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
                Toast.makeText(getContext().getApplicationContext(), "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                getActivity().finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }





}
