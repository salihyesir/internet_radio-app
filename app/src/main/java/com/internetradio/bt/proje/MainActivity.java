package com.internetradio.bt.proje;

import android.content.Intent;
import android.os.Bundle;
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
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.internetradio.bt.fragments.CategoryFragment;
import com.internetradio.bt.fragments.ChatFragment;
import com.internetradio.bt.fragments.FavoriteFragment;
import com.internetradio.bt.fragments.FragmentData;
import com.internetradio.bt.fragments.PopulerFragment;
import com.internetradio.bt.fragments.RadioChatFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentData{


    ChatFragment chatFragment;

    ArrayList<RadioModel> arrayList=new ArrayList<>();

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Radios");


    private FirebaseAuth fAuth;


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    //Resimler
    private int[] tabIcons = {
            R.drawable.ic_tab_radio,
            R.drawable.ic_tab_category,
            R.drawable.ic_tab_favourite,
            R.drawable.ic_tab_call

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();

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
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PopulerFragment(), "");
        adapter.addFrag(new CategoryFragment(), "");
        adapter.addFrag(new FavoriteFragment(), "");
        adapter.addFrag(new RadioChatFragment(),"");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void subjectData(String pos) {
        String position =pos;
        //Fragment2 içindeki textDegistir methodunu çağıracağımız için Fragment2 den obje oluşturuyoruz
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.chatOut)
        {
            fAuth.signOut();
            RadioChatFragment radioChatFragment = new RadioChatFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_home, radioChatFragment);
            transaction.addToBackStack(null);

            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);

        }
        if(item.getItemId()== R.id.exit)
        {
            android.os.Process.killProcess(android.os.Process.myPid());
            super.onDestroy();
        }

        return super.onOptionsItemSelected(item);
    }

}
