package com.smartechafghanistan.com.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MyAdapter extends ArrayAdapter {
    int []imageArray;
    String[] titleArray;
    String[] descArray;
    String[] amountArray;

    public MyAdapter(Context context,String[] title1,String[] descreption1,int[]image1,String[] amount1){
        super(context,R.layout.custome_listview,title1);
        this.imageArray=image1;
        this.descArray=descreption1;
        this.titleArray=title1;
        this.amountArray=amount1;

    }
    @Nullable
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.custome_listview,parent,false);
        ImageView imageView=row.findViewById(R.id.imageView_image);
        TextView textView=row.findViewById(R.id.textView_title);
        TextView textView2=row.findViewById(R.id.textView_disc);
        TextView textViewAmount=row.findViewById(R.id.textView_amount);
        imageView.setImageResource(imageArray[position]);
        textView.setText(titleArray[position]);
        textView2.setText(descArray[position]);
        textViewAmount.setText(amountArray[position]);
        return row;

    }
}
