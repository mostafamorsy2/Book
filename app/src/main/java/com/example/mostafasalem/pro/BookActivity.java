package com.example.mostafasalem.pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String GET_BY_ID = "https://api.itbook.store/1.0/search/mongodb";

    int countnum = 0;

    ArrayList<Books> list = new ArrayList<Books>();
    ListView lv;
    //cart titles
    ArrayList<String> ct = new ArrayList<String>();
    //cart prices
    ArrayList<String> cp = new ArrayList<String>();
    ImageView cart;
    TextView count;
    Button myshop;
    String user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_book);
        lv = findViewById(R.id.List_view);
        cart = findViewById(R.id.GoCart);
        count = findViewById(R.id.count);
        myshop =findViewById(R.id.myshop);

        cart.setVisibility(View.INVISIBLE);
        myshop.setVisibility(View.INVISIBLE);
        PlayAnimation();
        getData();
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        user_name = b.getString("user_name");


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Books title = (Books) lv.getItemAtPosition(i);
                if (ct.contains(title.getTitle())) {
                    Toast.makeText(BookActivity.this, "Item Already Added", Toast.LENGTH_SHORT).show();
                } else {
                    countnum++;
                    ct.add(title.getTitle());
                    cp.add(title.getPrice().substring(1));
                    Toast.makeText(BookActivity.this, "Added To Cart !", Toast.LENGTH_SHORT).show();
                    count.setText(String.valueOf(countnum));

                }


            }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("deleted_item", MODE_PRIVATE);
                String string = prefs.getString("test", "default_value_here_if_string_is_missing");

                if (string != "default_value_here_if_string_is_missing") {

                    int i = Integer.parseInt(string);
                    cp.remove(i);
                    ct.remove(i);
                    getSharedPreferences("deleted_item", 0).edit().clear().apply();

                }

                Intent intent = new Intent(BookActivity.this, CartActivity.class);
                intent.putExtra("title", ct);
                intent.putExtra("price", cp);
                intent.putExtra("user", user_name);
                startActivity(intent);

               /* if(!ct.isEmpty() || !cp.isEmpty()) {
                    ct.clear();
                    cp.clear();

                }*/
            }
        });


    }


    public void getData()

    {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, GET_BY_ID, null, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ob = response.getJSONArray("books");
                            Books object;
                            for (int i = 0; i < ob.length(); i++) {
                                object = new Books();
                                object.setTitle(ob.getJSONObject(i).getString("title"));
                                object.setPrice(ob.getJSONObject(i).getString("price"));
                                object.setSubtitle(ob.getJSONObject(i).getString("subtitle"));
                                object.setImgid(ob.getJSONObject(i).getString("image"));
                                list.add(object);
                            }
                            final BookAdapter adapter = new BookAdapter(BookActivity.this, list);
                            lv.setAdapter(adapter);
                            lv.startAnimation(AnimationUtils.loadAnimation(BookActivity.this, R.anim.ani));
//                            int size=object.length();
////                            Toast.makeText(MainActivity.this, size+" ", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookActivity.this, "No Internet !!!!!!!!!!!!!!", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("deleted_item", MODE_PRIVATE);
        String string = prefs.getString("test", "default_value_here_if_string_is_missing");
        if (string != "default_value_here_if_string_is_missing") {

            countnum=countnum-1;
            count.setText(String.valueOf(countnum));

        }
    }

    public void shop(View view) {
        Intent intent=new Intent(BookActivity.this,MyShop.class);
        intent.putExtra("title", ct);
        intent.putExtra("price", cp);
        intent.putExtra("user",user_name);
        startActivity(intent);


    }
    public void PlayAnimation(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cart.startAnimation(AnimationUtils.loadAnimation(BookActivity.this, R.anim.bounce));
                cart.setVisibility(View.VISIBLE);
                myshop.startAnimation(AnimationUtils.loadAnimation(BookActivity.this, R.anim.bounce));
                myshop.setVisibility(View.VISIBLE);
            }},400);


    }
}
