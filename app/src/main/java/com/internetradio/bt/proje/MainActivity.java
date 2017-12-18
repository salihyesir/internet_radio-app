package com.internetradio.bt.proje;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.internetradio.bt.fragments.ChatFragment;
import com.internetradio.bt.fragments.FavoriteFragment;
import com.internetradio.bt.fragments.FragmentData;
import com.internetradio.bt.fragments.PopulerFragment;
import com.internetradio.bt.fragments.RadioChatFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentData, ConnectivityReceiver.ConnectivityReceiverListener{




    ArrayList<RadioModel> arrayList=new ArrayList<>();

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Radios");


    private FirebaseAuth fAuth;
    private FirebaseUser firebaseUser;

    private static String stream = "";
    private static String radyoDurum = "";

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    //Resimler
    private int[] tabIcons = {
            R.drawable.ic_tab_radio,
            R.drawable.ic_tab_favourite,
            R.drawable.ic_tab_call

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //İnternet yok ise kullanıcının uygulamaya girmesinin bir anlamı yok
        if(ConnectivityReceiver.isConnected() == false) {
            Intent intent1 = new Intent(MainActivity.this, NotConnectionActivity.class);
            startActivity(intent1);
            finish();
        }
        fAuth = FirebaseAuth.getInstance();
        firebaseUser = fAuth.getCurrentUser(); // authenticate olan kullaniciyi aliyoruz eger var ise

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // uyku modunu kapatma
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PopulerFragment(), "");
        adapter.addFrag(new FavoriteFragment(), "");
        adapter.addFrag(new RadioChatFragment(),"");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void subjectData(String pos) {
        String position =pos;
        //Metodu çağırıcağımız yerde yani veriyi aktarcağımız fragmentı oluşturuyoruz<
        ChatFragment radioPos = new ChatFragment();
        radioPos.setSubject(position);

    }




    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }


        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);

        if (firebaseUser == null)
        {
            MenuItem menuItem = menu.findItem(R.id.chatOut);
            menuItem.setVisible(false);
        }
        else{
            MenuItem menuItem = menu.findItem(R.id.chatOut);
            menuItem.setVisible(true);
            menuItem.setTitle("LogOut");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Yenile
        if (item.getItemId() == R.id.yenile) {
            Toast.makeText(getApplicationContext(), "Radyo Linkleri Yenileniyor!", Toast.LENGTH_SHORT).show();
            if(firebaseUser == null){
                Toast.makeText(getApplicationContext(), "Chat için üye girişi yapmanızı hatırlatırız!", Toast.LENGTH_SHORT).show();
            }
            if(firebaseUser != null){
                Toast.makeText(getApplicationContext(), "Chat yapma durumunuz aktiftir!", Toast.LENGTH_SHORT).show();
            }
            Radio radio = new Radio();
            radio.stopRadioPlayer();
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            this.startActivity(intent);


        }
        if (item.getItemId() == R.id.chatOut)
        {
                fAuth.signOut();
                //burda chat menüden giriş ekranına geri dönüş sağlıyoruz.
                RadioChatFragment radioChatFragment = new RadioChatFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_home, radioChatFragment);
                transaction.addToBackStack(null);

                Intent intent = new Intent(this, MainActivity.class);
                finish();
                this.startActivity(intent);
        }
        if(item.getItemId()== R.id.exit)
        {
            android.os.Process.killProcess(android.os.Process.myPid());
            super.onDestroy();
        }



        return super.onOptionsItemSelected(item);
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;

        if (isConnected) {
            message = "İnternet Bağlantınız Sağlandı!";
            color = Color.WHITE;
        } else {
            message = "İnternet Bağlantınızı Kontrol ediniz";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.fabInternet), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

}
