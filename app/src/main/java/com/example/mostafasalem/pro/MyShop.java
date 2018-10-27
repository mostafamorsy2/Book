package com.example.mostafasalem.pro;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyShop extends AppCompatActivity {

    TextView user;
    TextView tit,pri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_shop);
        final ArrayList<String> title = (ArrayList<String>) getIntent().getSerializableExtra("title");
        final ArrayList<String> price = (ArrayList<String>) getIntent().getSerializableExtra("price");
        final String user_name = getIntent().getExtras().getString("user");
        user = findViewById(R.id.textView);
        user.setVisibility(View.INVISIBLE);
        user.setText(user_name);
        tit=findViewById(R.id.t1);
        pri=findViewById(R.id.t2);
        tit.setVisibility(View.INVISIBLE);
        pri.setVisibility(View.INVISIBLE);
        PlayAnimation();
        String s1;
        String result1="";

        for(int i = 0; i <title.size(); i++) {
            s1=title.get(i);
            s1=s1+"\n \n \n";
            result1=result1+s1;
        }

        tit.setText(result1);


        String s2;
        String result2="";

        for(int i = 0; i <price.size(); i++) {
            s2=price.get(i);
            s2=s2+" $"+"\n \n \n";
            result2=result2+s2;
        }

        pri.setText(result2);

    }

    public void PlayAnimation(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                user.startAnimation(AnimationUtils.loadAnimation(MyShop.this, R.anim.bounce));
                user.setVisibility(View.VISIBLE);
                tit.startAnimation(AnimationUtils.loadAnimation(MyShop.this, android.R.anim.slide_in_left));
                tit.setVisibility(View.VISIBLE);
            }},400);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pri.startAnimation(AnimationUtils.loadAnimation(MyShop.this, R.anim.ani));
                pri.setVisibility(View.VISIBLE);

            }},1200);

    }
}
