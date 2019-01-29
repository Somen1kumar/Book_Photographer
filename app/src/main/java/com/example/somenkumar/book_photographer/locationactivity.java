package com.example.somenkumar.book_photographer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class locationactivity extends AppCompatActivity implements LocationListener{

    Button currentlocat,useradd;
    TextView locationText;
    LocationManager locationManager;
    EditText getdetails;
   // ProgressBar progress;
   // Intent i=new Intent(locationactivity.this,Mainpage.class);
   // LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationactivity);
        currentlocat=(Button)findViewById(R.id.currentlocation);
        locationText=(TextView)findViewById(R.id.lo);
        useradd=(Button)findViewById(R.id.useraa);
        getdetails=(EditText)findViewById(R.id.useraddress);
        //progress=(ProgressBar)findViewById(R.id.circular);


        currentlocat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                //currentlocat.setEnabled(false);
                //progress.setEnabled(true);
                //Intent intent=new Intent(locationactivity.this,Mainpage.class);


            }
        });
        useradd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ss=getdetails.getText().toString();
                useradd.setEnabled(false);
               // progress.setEnabled(true);
                Intent i=new Intent(locationactivity.this,Mainpage.class);
                i.putExtra("userdetails",ss);
                startActivity(i);
            }
        });

    }
    public void getLocation() {
        try {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 5, this);
           // Toast.makeText(getApplicationContext(),"huhcsdknc",Toast.LENGTH_SHORT).show();
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
       // locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
         String ss="Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude();
        Toast.makeText(getApplicationContext(),ss,Toast.LENGTH_LONG).show();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
            String sk= locationText.getText()+"\t"+addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2);
            //Toast.makeText(getApplicationContext(),sk,Toast.LENGTH_LONG).show();
            Intent is=new Intent(locationactivity.this,Mainpage.class);
            is.putExtra("location",sk);
            startActivity(is);

        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error in Geting Location Plz check ur Internet",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getApplication(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }
}

