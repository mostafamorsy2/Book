package com.example.mostafasalem.pro;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText pass;
    Button login;
    TextView newAcc;
    ImageView img,img2;
    DBAdapter db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        /////////////////////////////////////////////////
        db = new DBAdapter(this);
        img=findViewById(R.id.img);
        img.setVisibility(View.INVISIBLE);

        img2=findViewById(R.id.img2);
        img2.setVisibility(View.INVISIBLE);
        login =  findViewById(R.id.login);
        newAcc = findViewById(R.id.newAcc);
        name =   findViewById(R.id.name);
        pass =   findViewById(R.id.password);

        login.setVisibility(View.INVISIBLE);
        newAcc.setVisibility(View.INVISIBLE);

        PlayAnimation();

        /////////////////////////////////////////////////

        try {
            String destPath = "/data/data/" + getPackageName() +
                    "/databases/DB_BD";
            File f = new File(destPath);
            if (!f.exists()) {
                CopyDB( getBaseContext().getAssets().open("mydatabase.sqlite"),
                        new FileOutputStream(destPath));


            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /////////////////////////////////////////////////
        db.open();
        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
              //  db.insertStudent(name.getText().toString(),pass.getText().toString());


                Cursor c =db.getUser(name.getText().toString(),pass.getText().toString());
                String user_name =name.getText().toString();
                if(c.getCount()!= 0) {
                    login.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.slide_out_right));

                   // Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,BookActivity.class);
                    i.putExtra("user_name",user_name);
                    startActivity(i);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }
                else {
                    Toast.makeText(MainActivity.this, "Wrong Username or Password !!", Toast.LENGTH_SHORT).show();
                    login.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce));
                }

            }});
        /////////////////////////////////////////////////
        newAcc.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewAccount.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }});
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public void CopyDB(InputStream inputStream,
                       OutputStream outputStream)
            throws IOException {
        //---copy 1K bytes at a time---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }
    public void PlayAnimation(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                name.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.ani));
                name.setVisibility(VISIBLE);
                pass.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.ani));
                pass.setVisibility(VISIBLE);
                img.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.ani));
                img.setVisibility(VISIBLE);
                img2.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.ani));
                img2.setVisibility(VISIBLE);
                login.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.move));
                login.setVisibility(VISIBLE);
                newAcc.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.move));
                newAcc.setVisibility(VISIBLE);
            }
        }, 400);
    }
}
