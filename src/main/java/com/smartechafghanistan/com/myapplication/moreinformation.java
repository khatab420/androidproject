package com.smartechafghanistan.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class moreinformation extends AppCompatActivity {

    ListView lv;

    ArrayList<String>arrayList;
    ArrayAdapter arrayAdapter;
    DatabaseHelper dbh;




    public void fulllistview(){
        Cursor cursor=dbh.getName();
        if(cursor.getCount()==0){
            Toast.makeText(this,"There is nothing for showing on listview",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext()){
                arrayList.add(cursor.getString(0));
            }
            arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
            lv.setAdapter(arrayAdapter);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreinformation);
        lv=(ListView)findViewById(R.id.listview);
        arrayList=new ArrayList<>();
        dbh=new DatabaseHelper(this);
        fulllistview();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String vendorname=lv.getItemAtPosition(position).toString();

                Intent intent=new Intent(getApplicationContext(),ShowActivity.class);
                intent.putExtra("vendorname",vendorname);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Item clicked:"+lv.getItemAtPosition(position),Toast.LENGTH_LONG).show();
            }
        });

    }
}
