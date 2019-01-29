package com.example.somenkumar.book_photographer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Mainpage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Button bookshoots,submits,ratecards,locations,mainbut;
Spinner timer;
public DatePickerDialog.OnDateSetListener mDateSetListener;
b bc=new b();
c ck=new c();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        timer=(Spinner) findViewById(R.id.duration);
        bookshoots=(Button)findViewById(R.id.bookshoot);
        submits =(Button)findViewById(R.id.aboutus);
        ratecards=(Button)findViewById(R.id.ratecard);
        locations=(Button)findViewById(R.id.location);
        mainbut=(Button)findViewById(R.id.mainbutton);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Duration_in_hrs,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        timer.setAdapter(adapter);
        timer.setOnItemSelectedListener(this);
        bookshoots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();

                int year = cal.get(Calendar.YEAR);

                int month = cal.get(Calendar.MONTH);

                int day = cal.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog dialog = new DatePickerDialog(

                        Mainpage.this,

                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,

                        mDateSetListener,

                        year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override

            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1;

               // Log.d(Tag,"onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);



                String date = day + "/" + month + "/" + year;
                Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();
               // bc.bcc(date);
              //  sendsms(date);
                ck.cs(date);

            }

        };

        ratecards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Rate not avalable",Toast.LENGTH_LONG).show();

            }
        });
        locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(Mainpage.this,MapsActivity.class);
            startActivity(intent);


            }
        });
        mainbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String getdate=ck.setvalue();
               // Toast.makeText(getApplicationContext(),getdate,Toast.LENGTH_LONG).show();
                Intent is=getIntent();
                String Location=is.getStringExtra("location");
                String userlocation=is.getStringExtra("userdetails");
                String settimer=bc.settime();
                String getdate=ck.setvalue();
                if(settimer==null||getdate==null)
                    Toast.makeText(getApplicationContext(),"Plz fill date and hours ",Toast.LENGTH_SHORT).show();

                if(Location!=null&&settimer!=null&&getdate!=null) {
                   // Toast.makeText(getApplicationContext(), Location, Toast.LENGTH_LONG).show();
                    //String getdates=ck.setvalue();
                    String finalstr=getdate+" Location="+Location+"\nDuration="+settimer;
                    sendsms(finalstr);

                }
                else if(userlocation!=null&&settimer!=null&&getdate!=null) {
                   // Toast.makeText(getApplicationContext(),userlocation,Toast.LENGTH_LONG).show();
                    //String getdatess=ck.setvalue();
                    String finalstr=getdate+" Location="+userlocation+"\nDuration="+settimer;
                    //Toast.makeText(getApplicationContext(),finalstr,Toast.LENGTH_LONG).show();

                      sendsms(finalstr);

                }
               // String settimer=bc.settime();
               // Toast.makeText(getApplicationContext(),settimer,Toast.LENGTH_SHORT).show();
               // String finalstr="Time="+getdate+" Location="+Location+"\nDuration="+settimer;
            }
        });

        submits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getdate=bc.setdates();
                Toast.makeText(getApplicationContext(),"detailes not avalable",Toast.LENGTH_LONG).show();


            }
        });
    }
    public void sendsms(String myloc1) {


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
        smsManager.sendTextMessage("8939716925", null, "Hi friend u got a new customer Booking date is."+myloc1, sentPI, deliveredPI);
        // Toast.makeText(this,myloc, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    String txt=parent.getItemAtPosition(position).toString();
    //Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_SHORT).show();
    bc.cd(txt);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
class b
{
    String datess;
    String duration;
    public void bcc(String dates)
    {
        this.datess=dates;

    }
    public void cd(String set)
    {
        this.duration=set;
    }

    public String setdates()
    {
        return datess;
    }
    public String settime(){return duration;}

}
class c{
    String datesss;
    public void cs(String dat){
    datesss=dat;
    }
    public String setvalue(){return datesss;}


}
