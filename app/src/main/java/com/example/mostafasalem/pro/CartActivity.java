package com.example.mostafasalem.pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ArrayList<Books> cart_list=new ArrayList<Books>();
    ListView lv;
    Button buy;
    DBAdapter db;
    EditText visa;
    TextView total,count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cart);
        db = new DBAdapter(this);
        lv = findViewById(R.id.recycler_cart);
        buy = findViewById(R.id.btn_confirm);
        visa = findViewById(R.id.visa);
        total = findViewById(R.id.tv_total);

        /////Getting data from previous activity
        final ArrayList<String> title = (ArrayList<String>) getIntent().getSerializableExtra("title");
        final ArrayList<String> price = (ArrayList<String>) getIntent().getSerializableExtra("price");
        final String user_name = getIntent().getExtras().getString("user");

        ///// putting the titles and prices together on Books type arraylist to use on Adapter
        final ArrayList<Books> cart_items=new ArrayList<Books>();
        for(int i=0;i<title.size();i++)
        {
            Books object= new Books();
            object.setTitle(title.get(i));
            object.setPrice(price.get(i));
            cart_items.add(object);
        }
        /////////Attatching the Adapter
        final CartAdapter adapter = new CartAdapter(CartActivity.this,cart_items);
        lv.setAdapter(adapter);


        //////Display total price
        display_total(cart_items);

        db.open();

        buy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(visa.getText().toString().equals("")) {
                    Toast.makeText(CartActivity.this, "Enter your visa number !! ", Toast.LENGTH_SHORT).show();
                    buy.startAnimation(AnimationUtils.loadAnimation(CartActivity.this, R.anim.bounce));
                }
                else {

                    db.updateContact(user_name, visa.getText().toString(), cart_items.size()+"");

                    Toast.makeText(CartActivity.this, "Done !!", Toast.LENGTH_SHORT).show();
                    buy.startAnimation(AnimationUtils.loadAnimation(CartActivity.this,android.R.anim.slide_in_left));
                    buy.setBackgroundResource(R.drawable.confirm);
                    finish();
                }
            }});

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences prefs=getSharedPreferences("deleted_item",MODE_PRIVATE);
                SharedPreferences.Editor editor=prefs.edit();
                editor.putString("test",String.valueOf(i));


                editor.commit();
                cart_items.remove(i);
                adapter.notifyDataSetChanged();
                display_total(cart_items);
            }
        });

    }
    public void display_total(ArrayList<Books> item)
    {
        double sum = 0;
        for(int i = 0; i < item.size(); i++) {

            Books o = item.get(i);
            sum += Double.parseDouble(o.getPrice());
        }
        total.setText(sum+"");

    }
}
