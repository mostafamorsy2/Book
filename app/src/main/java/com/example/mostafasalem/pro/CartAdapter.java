package com.example.mostafasalem.pro;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Books> arrayList;

    public CartAdapter(Context context, ArrayList<Books> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return arrayList.indexOf(getItem(i));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Books book = arrayList.get(i);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.cart_model, null);
        }
        TextView title = view.findViewById(R.id.c_title);
        TextView price = view.findViewById(R.id.c_price);
        TextView sub_title = view.findViewById(R.id.c_sub_title);

        title.setTextColor(Color.WHITE);
        sub_title.setTextColor(Color.WHITE);

        title.setText(arrayList.get(i).getTitle());
        price.setText(arrayList.get(i).getPrice());
        sub_title.setText(arrayList.get(i).getSubtitle());



        return view;


    }


}
