package com.internetradio.bt.proje;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.internetradio.bt.fragments.FragmentData;
import com.internetradio.bt.fragments.PopulerFragment;

import static com.internetradio.bt.fragments.PopulerFragment.index;

public class FloatingViewService extends Service
{

    FragmentData fragmentData; //Interface referansı


    ImageView nextButton;
    ImageView prevButton;

    public static String stream ="";
    private static String streamUrl = "";
// PopulerFragmentan gelen veri
    private static String radioDurum = "";
    // MainActivity'e gönderceğimiz veri
    public static String widgetRadioDurum ="";
    private WindowManager mWindowManager;
    private View mFloatingView;

    ImageView ppButton;
    private Bundle extras = null;


    Radio radio = new Radio();

    public FloatingViewService() {

    }



    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getExtras() != null){
            extras=intent.getExtras();
            streamUrl= extras.getString(PopulerFragment.stream,"http://sc.powergroup.com.tr/RadyoFenomen/mpeg/128/tunein");
            radioDurum = extras.getString(PopulerFragment.widgetDurum,"pause");
            if (radioDurum.equals("play"))
            {
                ppButton.setImageResource(R.mipmap.ic_pause);
                radio.playRadioPlayer(streamUrl);
            }
        }
        return START_STICKY;
    }
    @Override
    public void onCreate( ) {
        super.onCreate();

        //Inflate the floating view layout we created
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);

        //Add the view to the window.
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;
        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        //The root element of the collapsed view layout
        final View collapsedView = mFloatingView.findViewById(R.id.collapse_view);
        //The root element of the expanded view layout
        final View expandedView = mFloatingView.findViewById(R.id.expanded_container);

        //Pause butonu ilk başta gözükmesin
//        pause_btn.setVisibility(View.INVISIBLE);



        //Set the close button
        ImageView closeButtonCollapsed = (ImageView) mFloatingView.findViewById(R.id.close_btn);
        closeButtonCollapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio.stopRadioPlayer();
                //close the service and remove the from from the window
                stopSelf();
            }
        });

        //Set the view while floating view is expanded.
        //Set the play button.
        ppButton = (ImageView) mFloatingView.findViewById(R.id.play_btn);
        ppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playing radio
                if(radio.controlButton == 0)
                {
                    Toast.makeText(FloatingViewService.this, "Playing the radio.", Toast.LENGTH_LONG).show();
                    ppButton.setImageResource(R.mipmap.ic_pause);
                    radio.playRadioPlayer(streamUrl);
                }
                //Pause radio
                else if(radio.controlButton==1){
                    Toast.makeText(FloatingViewService.this, "Pausing the radio.", Toast.LENGTH_LONG).show();
                    ppButton.setImageResource(R.mipmap.ic_play);
                    radio.stopRadioPlayer();
                }

            }
        });

        nextButton = (ImageView) mFloatingView.findViewById(R.id.next_btn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Radyo sonda ise
                index = index +1;
                int temp =PopulerFragment.arrayList.size();
                if (temp == index)
                {
                    index=0;
                    streamUrl = PopulerFragment.arrayList.get(index).getRadyoUrl();
                    radio.playRadioPlayer(streamUrl);
                    ppButton.setImageResource(R.mipmap.ic_pause);
                    Toast.makeText(getApplicationContext(), "Playing the " + PopulerFragment.arrayList.get(index).getRadyoAd() + ". ", Toast.LENGTH_LONG).show();
                }
                //normal durum
                else {
                    streamUrl = PopulerFragment.arrayList.get(index).getRadyoUrl();
                    radio.playRadioPlayer(streamUrl);
                    ppButton.setImageResource(R.mipmap.ic_pause);
                    Toast.makeText(getApplicationContext(), "Playing the " + PopulerFragment.arrayList.get(index).getRadyoAd() + ". ", Toast.LENGTH_LONG).show();
                }
            }
        });

        prevButton = (ImageView) mFloatingView.findViewById(R.id.prev_btn);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //radyo başta ise
                if (index == 0)
                {
                    index =PopulerFragment.arrayList.size()-1;
                    streamUrl = PopulerFragment.arrayList.get(index).getRadyoUrl();
                    radio.playRadioPlayer(streamUrl);
                    ppButton.setImageResource(R.mipmap.ic_pause);
                    Toast.makeText(getApplicationContext(), "Playing the " + PopulerFragment.arrayList.get(index).getRadyoAd() + ". ", Toast.LENGTH_LONG).show();
                }
                //Normal durum
                else{
                    index = index - 1;
                    streamUrl = PopulerFragment.arrayList.get(index).getRadyoUrl();
                    radio.playRadioPlayer(streamUrl);
                    ppButton.setImageResource(R.mipmap.ic_pause);
                    Toast.makeText(getApplicationContext(), "Playing the " + PopulerFragment.arrayList.get(index).getRadyoAd() + ". ", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Bu expanded kısmındaki button baloncuğun açılmış hali
        //Set the close button
        ImageView closeButton = (ImageView) mFloatingView.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
                //isAlreadyPlaying = false;
                //stopRadioPlayer();
            }
        });

        //Open the application on thi button click
        ImageView openButton = (ImageView) mFloatingView.findViewById(R.id.open_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open the application  click.


/*
                radio.stopRadioPlayer();
                controlButton = 0;
                isAlreadyPlaying = false;
*/

                Intent intent = new Intent(FloatingViewService.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(extras);
                startActivity(intent);
                //close the service and remove view from the view hierarchy
                stopSelf();
            }
        });

        //Drag and move floating view using user's touch action.
        mFloatingView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);

                        //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (Xdiff < 10 && Ydiff < 10) {
                            if (isViewCollapsed()) {
                                //When user clicks on the image view of the collapsed layout,
                                //visibility of the collapsed layout will be changed to "View.GONE"
                                //and expanded view will become visible.
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);

                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });

    }

    /**
     * Detect if the floating view is collapsed or expanded.
     *
     * @return true if the floating view is collapsed.
     */
    private boolean isViewCollapsed() {
        return mFloatingView == null || mFloatingView.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }
}
