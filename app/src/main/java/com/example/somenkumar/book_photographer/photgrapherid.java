package com.example.somenkumar.book_photographer;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class photgrapherid extends AppCompatActivity {
    EditText firstnam,lastnam,emaili,passwor,phonen,addres,informatio;
    String strfirstname,strlastname,stremailname,strpassname,straddressname,strphonename,strinformation;
    Button but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photgrapherid);
        but=(Button)findViewById(R.id.signup);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstnam=(EditText)findViewById(R.id.firstname);
                lastnam=(EditText)findViewById(R.id.lastname);
                emaili=(EditText)findViewById(R.id.emailid);
                passwor=(EditText)findViewById(R.id.password);
                informatio=(EditText)findViewById(R.id.info);
                addres=(EditText)findViewById(R.id.Address);
                phonen=(EditText)findViewById(R.id.Phonenumber);
                strfirstname=firstnam.getText().toString();
                strlastname=lastnam.getText().toString();
                stremailname=emaili.getText().toString();
                strpassname=passwor.getText().toString();
                straddressname=addres.getText().toString();
                strphonename=phonen.getText().toString();
                strinformation=informatio.getText().toString();
                try {

                   // String no="8939037308";
                    //String msg="Hello world";
                    String msgs=" Name:-"+strfirstname+" "+strlastname+"\nEmail:-"+stremailname+"\nPhone Number:-"+strphonename+"\nAddress:-"+straddressname+"\nPhotographer he had worked"+strinformation;
                   /* smsManager.sendTextMessage(no, null, msg, null, null);
                    Toast.makeText(getApplicationContext(),"Message send Successfully",Toast.LENGTH_LONG).show();*/
                   sendsmss(msgs);
                    Toast.makeText(getApplicationContext(),"Photographer register Successfully",Toast.LENGTH_LONG).show();
                    Intent sssk=new Intent(photgrapherid.this,MainActivity.class);
                    startActivity(sssk);

                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Message send Failed",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });
    }
    public void sendsmss(String myloc1) {


        SmsManager smsManager = SmsManager.getDefault();


        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> parts = sms.divideMessage("Hi");
        int messageCount = parts.size();

        Log.i("Message Count", "Message Count: " + messageCount);

        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        for (int j = 0; j < messageCount; j++) {
            sentIntents.add(sentPI);
            deliveryIntents.add(deliveredPI);
        }

        // ---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        // ---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {

                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));
        smsManager.sendTextMessage("7550196468", null, "Hi. Your friend u got a new photographer details are."+myloc1, sentPI, deliveredPI);
        // Toast.makeText(this,myloc, Toast.LENGTH_SHORT).show();

    }
}
