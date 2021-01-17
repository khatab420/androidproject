package com.smartechafghanistan.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FirstPageInformation extends AppCompatActivity {
   private ListView lv;

   private ArrayList<String> arrayList;
   private ArrayAdapter arrayAdapter;
     private DatabaseHelper dbh;
    String table_name;
    String newvalue;
    TextView tvinfristpage;




    private void showoutcomeoffirstpage() {
        Cursor cr=dbh.getSpecialOutcomeoffirstpage(table_name,newvalue);
        StringBuffer stringBuffer=new StringBuffer();
        if(cr!=null && cr.getCount()>0) {
            while (cr.moveToNext()) {
                stringBuffer.append("بودیجی واله نوم:     " + cr.getString(0) + "\n");
                stringBuffer.append("مجموعه:     " + cr.getString(1) + "\n");
                stringBuffer.append("\n\n");
            }
            tvinfristpage.setText(stringBuffer.toString());
        }
        else {
            Toast.makeText(this,"No data to retrieve",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page_information);
        tvinfristpage=(TextView)findViewById(R.id.firstpagetextview);
        arrayList=new ArrayList<>();
        dbh=new DatabaseHelper(this);
        newvalue=getIntent().getExtras().getString("name");
        table_name=getIntent().getExtras().getString("table_name");
        Toast.makeText(getApplicationContext(),newvalue+"//////"+table_name,Toast.LENGTH_LONG).show();
        showoutcomeoffirstpage();

    }
}
