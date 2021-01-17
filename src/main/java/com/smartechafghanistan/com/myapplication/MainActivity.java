package com.smartechafghanistan.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {


    Button af,afo,dol,dolo,rs,rso;
    TextView ta,tao,td,tdo,tr,tro;
     DatabaseHelper dbh;



    public void showlist(String name, String type) {
        Cursor cr=dbh.getSumOfIncomeTable(name,type);
        StringBuffer stringBuffer=new StringBuffer();
        if(cr!=null && cr.getCount()>0) {
            while (cr.moveToNext()) {
                stringBuffer.append(cr.getString(0) + "\n");

            }
           if(name.equals("income_table")&& type.equals("افغانی")){
               ta.setText(stringBuffer.toString());
           }
            if(name.equals("income_table")&& type.equals("ډالر")){
                td.setText(stringBuffer.toString());
            }
            if(name.equals("income_table")&& type.equals("کلداری")){
                tr.setText(stringBuffer.toString());
            }
            if(name.equals("outcome_table")&& type.equals("افغانی")){
                tao.setText(stringBuffer.toString());
            }
            if(name.equals("outcome_table")&& type.equals("ډالر")){
                tdo.setText(stringBuffer.toString());
            }
            if(name.equals("outcome_table")&& type.equals("کلداری")){
                tro.setText(stringBuffer.toString());
            }

        }
        else {
            Toast.makeText(com.smartechafghanistan.com.myapplication.MainActivity.this,"No data to retrieve",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        filledtextview();
    }

    public void goTochart(View view){
        startActivity(new Intent(MainActivity.this,ChartActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbh=new DatabaseHelper(this);
        intializeallthing();
        filledtextview();

    }

    private void intializeallthing() {
        ta=(TextView)findViewById(R.id.txtafghani);
        tao=(TextView)findViewById(R.id.txtafghanio);
        td=(TextView)findViewById(R.id.txtdolor);
        tdo=(TextView)findViewById(R.id.txtdoloro);
        tr=(TextView)findViewById(R.id.txtrs);
        tro=(TextView)findViewById(R.id.txtrso);
        af=(Button)findViewById(R.id.btnafghani);
        afo=(Button)findViewById(R.id.btnafghanio);
        dol=(Button)findViewById(R.id.btndolor);
        dolo=(Button)findViewById(R.id.btndoloro);
        rs=(Button)findViewById(R.id.btnrs);
        rso=(Button)findViewById(R.id.btnrso);
        af.setOnClickListener(this);
        afo.setOnClickListener(this);
        dol.setOnClickListener(this);
        dolo.setOnClickListener(this);
        rs.setOnClickListener(this);
        rso.setOnClickListener(this);



    }

    public void filledtextview(){
        showlist("income_table","افغانی");
        showlist("income_table","ډالر");
        showlist("income_table","کلداری");
        showlist("outcome_table","افغانی");
        showlist("outcome_table","ډالر");
        showlist("outcome_table","کلداری");

    }

    public void gotoincome(View view) {
        Intent i=new Intent(MainActivity.this,Income.class);
        startActivity(i);
    }

    public void gotooutcome(View view) {
        Intent i=new Intent(MainActivity.this,Outcome.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnafghani:
                Intent intent=new Intent(MainActivity.this,FirstPageInformation.class);
                intent.putExtra("name","افغانی");
                intent.putExtra("table_name","income_table");
                startActivity(intent);
                break;
            case R.id.btndolor:
                Intent intentd=new Intent(MainActivity.this,FirstPageInformation.class);
                intentd.putExtra("name","ډالر");
                intentd.putExtra("table_name","income_table");
                startActivity(intentd);
                break;
            case R.id.btnrs:
                Intent intentr=new Intent(MainActivity.this,FirstPageInformation.class);
                intentr.putExtra("name","کلداری");
                intentr.putExtra("table_name","income_table");
                startActivity(intentr);
                break;
            case R.id.btnafghanio:
                Intent intentao=new Intent(MainActivity.this,FirstPageInformation.class);
                intentao.putExtra("name","افغانی");
                intentao.putExtra("table_name","outcome_table");
                startActivity(intentao);
                break;
            case R.id.btndoloro:
                Intent intentdo=new Intent(MainActivity.this,FirstPageInformation.class);
                intentdo.putExtra("name","ډالر");
                intentdo.putExtra("table_name","outcome_table");
                startActivity(intentdo);
                break;
            case R.id.btnrso:
                Intent intentro=new Intent(MainActivity.this,FirstPageInformation.class);
                intentro.putExtra("name","کلداری");
                intentro.putExtra("table_name","outcome_table");
                startActivity(intentro);
                break;


        }
    }
}
