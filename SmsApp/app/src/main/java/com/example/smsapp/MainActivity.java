package com.example.smsapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final int My_Permission_Read_sms = 123;
    Context context;
    List<String> contacts_list;

    @Bind(R.id.list_contacts)
    ListView list_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        context = MainActivity.this;
        contacts_list = new ArrayList<String>();

        boolean result = checkPermission();
        if (result) {
          contacts_list =  getMessageContactList();
            ArrayAdapter arrayAdapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,android.R.id.text1,contacts_list);
            list_contacts.setAdapter(arrayAdapter);
        }



    }

    private List<String> getMessageContactList() {
        List<String> smsList = new ArrayList<String>();
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor c = getContentResolver().query(uri, null, null, null, null);


        // Read the sms data and store it in the list
        if (c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                String number = c.getString(c.getColumnIndexOrThrow("address")).toString();
               /* String index_Person = c.getString(c.getColumnIndex("person")).toString();*/
                String index_Body = c.getString(c.getColumnIndex("body")).toString();
                String index_Date = c.getString(c.getColumnIndex("date")).toString();
                String index_Type = c.getString(c.getColumnIndex("type")).toString();

                SMSData smsdata = new SMSData();
                smsdata.setBody(index_Body);
                smsdata.setDate(index_Date);
                smsdata.setNumber(number);
                smsdata.setType(index_Type);
                smsList.add(number);

                c.moveToNext();
            }
        }
        c.close();

        Set<String> toRetain = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        toRetain.addAll(smsList);
        Set<String> set = new LinkedHashSet<String>(smsList);
        set.retainAll(new LinkedHashSet<String>(toRetain));
        smsList = new ArrayList<String>(set);
        return smsList;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission() {
        final int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_SMS)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Write calendar permission is necessary to write event!!!");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_SMS}, My_Permission_Read_sms);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_SMS}, My_Permission_Read_sms);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case My_Permission_Read_sms:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   contacts_list = getMessageContactList();
                    ArrayAdapter arrayAdapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,android.R.id.text1,contacts_list);
                    list_contacts.setAdapter(arrayAdapter);
                } else {
                }
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected  void onStop(){
        super.onStop();
    }

}
