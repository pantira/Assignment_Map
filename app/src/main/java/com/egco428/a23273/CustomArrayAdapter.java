package com.egco428.a23273;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Benz on 11/18/2016.
 */
public class CustomArrayAdapter  extends ArrayAdapter<Data> {
    Context context;
    List<Data> objects;

    public CustomArrayAdapter(Context context, int resource, List<Data> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Data data = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datalist, null);
        view.setBackgroundColor(Color.WHITE);

        TextView txt = (TextView) view.findViewById(R.id.username);
        txt.setText(data.getName());
        ImageView next = (ImageView) view.findViewById(R.id.imageView);
        return view;

    }


}