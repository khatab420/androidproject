package com.smartechafghanistan.com.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;

import com.itextpdf.text.pdf.PdfWriter;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;



public class ShowActivity extends AppCompatActivity {
    String vendor;

    TextView textView;
    EditText ed;

    DatabaseHelper dbh;


    private  static final int   REQUEST_CODE_ASK_PERSMISSION=111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        textView=(TextView)findViewById(R.id.tvshow);
        ed=(EditText)findViewById(R.id.edfilename);

        dbh=new DatabaseHelper(this);
       Bundle b1= getIntent().getExtras();
       vendor=b1.getString("vendorname");
        showoutcome();
    }

    private void showoutcome() {
        Cursor cr=dbh.getSpecialOutcome(vendor);
        StringBuffer stringBuffer=new StringBuffer();
        if(cr!=null && cr.getCount()>0) {
            stringBuffer.append("تشریحات" +"\n\n");
            while (cr.moveToNext()) {
                stringBuffer.append("شماره:     " + cr.getString(0) + "\n");
                stringBuffer.append("تشریحات:     " + cr.getString(1) + "\n");
                stringBuffer.append("مقدار:     " + cr.getString(2) + "\n");
                stringBuffer.append("د پیسی ډول:     " + cr.getString(3) + "\n");
                stringBuffer.append("مصرفوونکی:     " + cr.getString(4) + "\n");
                stringBuffer.append("\n\n");
            }
            stringBuffer.append("حصول"+"\n\n");
            Cursor craf=dbh.getSumOfMoneyForShowActivity("income_table","افغانی",vendor);
            if(craf!=null && craf.getCount()>0){
                while (craf.moveToNext()) {
                    stringBuffer.append("افغانی:    " + craf.getString(0) + "\n");
                }
            }

            // calling for rs in income table
            Cursor crrs=dbh.getSumOfMoneyForShowActivity("income_table","کلداری",vendor);
            if(crrs!=null && crrs.getCount()>0) {
                while (crrs.moveToNext()) {
                    stringBuffer.append("کلدارې:    " + crrs.getString(0) + "\n");
                }
            }
            // calling the function for dolor in income table

            Cursor crdo=dbh.getSumOfMoneyForShowActivity("income_table","ډالر",vendor);
            if(crdo!=null && crdo.getCount()>0) {
                while (crdo.moveToNext()) {
                    stringBuffer.append("ډالر:    " + crdo.getString(0) + "\n\n");
                }
            }
            stringBuffer.append("مصرف" +"\n\n");
            Cursor crafo=dbh.getSumOfMoneyForShowActivity("outcome_table","افغانی",vendor);
            if(crafo!=null && crafo.getCount()>0) {
                while (crafo.moveToNext()) {
                    stringBuffer.append("افغانی:    " + crafo.getString(0) + "\n");
                }
            }

            // calling function for afghani in outcome table

            Cursor crrso=dbh.getSumOfMoneyForShowActivity("outcome_table","کلداری",vendor);
            if(crrso!=null && crrso.getCount()>0) {
                while (crrso.moveToNext()) {
                    stringBuffer.append("کلدارې:    " + crrso.getString(0) + "\n");
                }
            }

            // calling function for dolor in outmcome table

            Cursor crdoo=dbh.getSumOfMoneyForShowActivity("outcome_table","ډالر",vendor);
            if(crdoo!=null && crdoo.getCount()>0) {
                while (crdoo.moveToNext()) {
                    stringBuffer.append("ډالر:    " + crdoo.getString(0) + "\n");
                }
            }

            textView.setText(stringBuffer.toString());
        }
        else {
            Toast.makeText(this,"No data to retrieve",Toast.LENGTH_LONG).show();
        }
    }




    public void pdfcreating(View view) {

        if(textView.getText().toString().isEmpty()){
            return;
        }

        try{
            createPdf();
        }catch (Exception e){
            e.printStackTrace();
        }





    }
    private  void showMessage(String message, DialogInterface.OnClickListener onClickListener){

        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("OK",onClickListener)
                .setNegativeButton("cancel",null)
                .create()
                .show();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch (requestCode){
            case REQUEST_CODE_ASK_PERSMISSION:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    try {
                        createPdf();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"WRITE_EXTERNAL_STORAGE_DENIED",Toast.LENGTH_LONG).show();
                }
                break;
                default:
                    super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        }
    }

    private void createPdf() throws  Exception {


        int hasWriteStoragePermission =ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(hasWriteStoragePermission!= PackageManager.PERMISSION_GRANTED){

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if(!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    showMessage("you need to allow permission", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_ASK_PERSMISSION);
                            }
                        }
                    });
                    return;


                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_ASK_PERSMISSION);

            }
            return;
        }
        else {
            File docFolder=new File(Environment.getExternalStorageDirectory()+"/Expense Report");
            if(!docFolder.exists()){
                docFolder.mkdir();
            }
            try {
                FontFactory.registerDirectories();
                Rectangle pagesize = new Rectangle(8.5f * 72, 11 * 72);
                Document document = new Document(pagesize, 72, 72, 72, 72);
                File pdffile = new File(docFolder.getAbsolutePath(), ed.getText().toString() + ".pdf");
                OutputStream outputStream = new FileOutputStream(pdffile);
                PdfWriter writer=PdfWriter.getInstance(document,outputStream);
                document.open();
                Font font = FontFactory.getFont("assets/Tahoma.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                ColumnText column = new ColumnText(writer.getDirectContent());
                column.setSimpleColumn(36, 770, 569, 36);
                column.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
                column.addElement(new Paragraph(textView.getText().toString(), font));
                column.go();
                document.close();
                Toast.makeText(getApplicationContext(), "File Created",Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
            }


        }




    }

    public void resetRecord(View view) {

        Boolean b=dbh.resetAll(vendor);
        if(b){

            Toast.makeText(getApplicationContext(),"د نوموړی شخص ټوله ډاټا پاکه شوله",Toast.LENGTH_LONG).show();
           textView.setText("");


        }
        else {
            Toast.makeText(getApplicationContext(),"cannot be deleted",Toast.LENGTH_LONG).show();

        }



    }
}
