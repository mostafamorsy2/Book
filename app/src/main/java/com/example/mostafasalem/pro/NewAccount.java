package com.example.mostafasalem.pro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class NewAccount extends AppCompatActivity {
    EditText name;
    EditText pass,phone;
    Button create,back;
    DBAdapter db;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_new_account);
        db = new DBAdapter(this);
        t=findViewById(R.id.txt_success_name);
        create =  findViewById(R.id.create);
        name =   findViewById(R.id.name2);
        pass =   findViewById(R.id.password2);
        phone = findViewById(R.id.mobile);
        name.setVisibility(INVISIBLE);
        pass.setVisibility(INVISIBLE);
        phone.setVisibility(INVISIBLE);
        t.setVisibility(INVISIBLE);
        create.setVisibility(INVISIBLE);

     PlayAnimation();




        /////////////////////////////////////////////////
        db.open();
        /////////////////////////////////////////////////

        create.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(name.getText().toString().equals("")||pass.getText().toString().equals("")|| phone.getText().toString().equals(""))
                {
                    Toast.makeText(NewAccount.this, "Empty field found!!", Toast.LENGTH_SHORT).show();
                    create.startAnimation(AnimationUtils.loadAnimation(NewAccount.this, R.anim.bounce));
                }
                else {

                    db.insertStudent(name.getText().toString(), pass.getText().toString(), phone.getText().toString());
                    Toast.makeText(NewAccount.this, "Done", Toast.LENGTH_SHORT).show();
                    Intent starter = getIntent();
                    finish();

                    // starter.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    // startActivity(starter);
                }

            }});
        /////////////////////////////////////////////////

    }
   public void PlayAnimation(){
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               name.startAnimation(AnimationUtils.loadAnimation(NewAccount.this, R.anim.ani));
               name.setVisibility(VISIBLE);
               pass.startAnimation(AnimationUtils.loadAnimation(NewAccount.this, R.anim.ani));
               pass.setVisibility(VISIBLE);
               phone.startAnimation(AnimationUtils.loadAnimation(NewAccount.this, R.anim.ani));
               phone.setVisibility(VISIBLE);
           }
       }, 400);



       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               t.startAnimation(AnimationUtils.loadAnimation(NewAccount.this, R.anim.ani));
               t.setVisibility(VISIBLE);
               create.startAnimation(AnimationUtils.loadAnimation(NewAccount.this, R.anim.ani));
               create.setVisibility(VISIBLE);
           }
       }, 1800);
   }
}
