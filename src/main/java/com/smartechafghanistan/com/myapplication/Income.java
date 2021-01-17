package com.smartechafghanistan.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Income extends AppCompatActivity {

    EditText edname,edcount;
    DatabaseHelper dbh;
    TextView tv;
    RadioGroup radioGroup;
    String type=null;
    public void cleardata(){
        radioGroup.clearCheck();
        edcount.setText("");
        edname.setText("");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        edname=(EditText)findViewById(R.id.editTextname);
        edcount=(EditText)findViewById(R.id.editTextcount);
        dbh=new DatabaseHelper(this);
        tv=(TextView)findViewById(R.id.textViewshow);
        radioGroup=(RadioGroup)findViewById(R.id.redioGroup);


        showincome();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 switch (checkedId){
                     case R.id.rdafg:
                         type="افغانی";
                         break;
                     case R.id.rs:
                         type="کلدارې";
                         break;
                     case R.id.us:
                         type="ډالر";
                         break;
                 }
            }
        });

    }

    private void showincome() {
        Cursor cr=dbh.getAllIncome();
        StringBuffer stringBuffer=new StringBuffer();
        if(cr!=null && cr.getCount()>0) {
            while (cr.moveToNext()) {
                stringBuffer.append("آیدی:     " + cr.getString(0) + "\n");
                stringBuffer.append("نوم:     " + cr.getString(1) + "\n");
                stringBuffer.append("مقدار:     " + cr.getString(2) + "\n");
                stringBuffer.append("د پیسی ډول:     " + cr.getString(3) + "\n");
                stringBuffer.append("\n\n");
            }
            tv.setText(stringBuffer.toString());
        }
        else {
            Toast.makeText(this,"No data to retrieve",Toast.LENGTH_LONG).show();
        }
    }



    public void insertintoincome(View view) {
        int count=Integer.parseInt(edcount.getText().toString());
        String name=edname.getText().toString();

        Boolean result=dbh.insertData(name,count,type);
        if(result==true){
            Toast.makeText(this,"Data inserted",Toast.LENGTH_LONG).show();
            showincome();
            cleardata();

        }
        else {
            Toast.makeText(this,"Data connot be inserted",Toast.LENGTH_LONG).show();
        }


    }


}
