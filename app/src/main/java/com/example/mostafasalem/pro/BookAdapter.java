package com.example.mostafasalem.pro;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BookAdapter extends BaseAdapter {

    Context context;
    ArrayList<Books> arrayList;

    public BookAdapter(Context context, ArrayList<Books> arrayList) {
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
            view = inflater.inflate(R.layout.model, null);
        }
        final TextView title = view.findViewById(R.id.title);
        TextView price = view.findViewById(R.id.price);
        TextView sub_title = view.findViewById(R.id.sub_title);
        ImageView imageView = view.findViewById(R.id.img);



        title.setTextColor(Color.WHITE);
        sub_title.setTextColor(Color.WHITE);

        title.setText(arrayList.get(i).getTitle());
        price.setText(arrayList.get(i).getPrice());
        sub_title.setText(arrayList.get(i).getSubtitle());
        Picasso.with(context).load(book.getImgid()).into(imageView);

        return view;


    }

}