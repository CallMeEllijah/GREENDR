package com.example.greendr.CardViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.greendr.R;

import java.util.List;

public class cardAdapter extends ArrayAdapter<cardObject> {

    Context context;

    public cardAdapter(Context context, int resourceID, List<cardObject> items){
        super(context, resourceID, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        cardObject cardObjectItem = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);

        TextView name = (TextView) convertView.findViewById(R.id.helloText);
        ImageView image = (ImageView) convertView.findViewById(R.id.img);
        TextView age = (TextView) convertView.findViewById(R.id.age);

        name.setText(cardObjectItem.getName());
        Glide.with(getContext()).load(cardObjectItem.getDpUrl()).into(image);
        age.setText(cardObjectItem.getAge());

        return convertView;

    }
}
