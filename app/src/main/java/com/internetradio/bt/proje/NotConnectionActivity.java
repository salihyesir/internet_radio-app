package com.internetradio.bt.proje;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.internetradio.bt.fragments.FavoriteFragment;

/**
 * Created by Salih on 18.12.2017.
 */

public class NotConnectionActivity extends AppCompatActivity {

    ImageView refleshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not);
        FavoriteFragment.sans=0;
        refleshButton = (ImageView) findViewById(R.id.imageViewReflesh);


        refleshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(NotConnectionActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();

            }
        });

    }


}
