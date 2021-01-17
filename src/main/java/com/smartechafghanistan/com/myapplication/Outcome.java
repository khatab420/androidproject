package com.smartechafghanistan.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import static android.R.*;

public class Outcome extends AppCompatActivity {

    EditText edname,edcount;
    DatabaseHelper dbh;
    TextView tv;
    RadioGroup radioGroup;
    String type=null;
    Spinner act;
    String inpute[];

    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    ArrayAdapter<CharSequence> arrayAdapterch;

public void cleardata (){
    radioGroup.clearCheck();
    edcount.setText("");
    edname.setText("");

}


    public void fulllautocomplettextview(){

        Cursor cursor=dbh.getName();
        if(cursor.getCount()==0){
            Toast.makeText(this,"There is nothing for showing on listview",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext()){
                arrayList.add(cursor.getString(0));
            }
            arrayAdapter=new ArrayAdapter(this, layout.simple_spinner_item,arrayList);
            arrayAdapter.setDropDownViewResource(layout.simple_spinner_item);
            act.setAdapter(arrayAdapter);
        }


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outcome);
        edname=(EditText)findViewById(R.id.editTextname_out);
        edcount=(EditText)findViewById(R.id.editTextcount_out);
        act=(Spinner)findViewById(R.id.editTextforname_out);
        arrayList=new ArrayList<>();
//        arrayAdapterch=ArrayAdapter.createFromResource(this,R.array.panets_name, layout.simple_spinner_item);
//        arrayAdapterch.setDropDownViewResource(layout.simple_spinner_item);
//        act.setAdapter(arrayAdapterch);






        dbh=new DatabaseHelper(this);

       fulllautocomplettextview();
        tv=(TextView)findViewById(R.id.textViewshow_out);
        radioGroup=(RadioGroup)findViewById(R.id.redioGroup_out);


        showoutcome();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rdafg_out:
                        type="افغانی";
                        break;
                    case R.id.rs_out:
                        type="کلدارې";
                        break;
                    case R.id.us_out:
                        type="ډالر";
                        break;
                }
            }
        });

    }

    public void moreinformation(View view) {
        Intent i=new Intent(this,moreinformation.class);
        startActivity(i);
    }




    private void showoutcome() {
        Cursor cr=dbh.getAllOutcome();
        StringBuffer stringBuffer=new StringBuffer();
        if(cr!=null && cr.getCount()>0) {
            while (cr.moveToNext()) {
                stringBuffer.append("شماره:     " + cr.getString(0) + "\n");
                stringBuffer.append("نوم:     " + cr.getString(1) + "\n");
                stringBuffer.append("مقدار:     " + cr.getString(2) + "\n");
                stringBuffer.append("د پیسی ډول:     " + cr.getString(3) + "\n");
                stringBuffer.append("مصرف کوونکی:     " + cr.getString(4) + "\n");
                stringBuffer.append("\n\n");
            }
            tv.setText(stringBuffer.toString());
        }
        else {
            Toast.makeText(this,"No data to retrieve",Toast.LENGTH_LONG).show();
        }
    }

    public void insertintooutcome(View view) {
        int count=Integer.parseInt(edcount.getText().toString());
        String name=edname.getText().toString();
        String forname=act.getSelectedItem().toString();

        Boolean result=dbh.insertData_out(name,count,type,forname);
        if(result==true){
            Toast.makeText(this,"Data inserted",Toast.LENGTH_LONG).show();
            showoutcome();
            cleardata ();

        }
        else {
            Toast.makeText(this,"Data connot be inserted",Toast.LENGTH_LONG).show();
        }


    }
}
