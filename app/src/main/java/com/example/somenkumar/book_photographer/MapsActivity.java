package com.example.somenkumar.book_photographer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    EditText editText;
    Button registeration;
  //  List<Address> addressnew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       editText=(EditText)findViewById(R.id.addressofmap);
        registeration=(Button)findViewById(R.id.register);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        registeration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String locationSearch=editText.getText().toString();
                if(locationSearch!=null||locationSearch!="")
                {
                onMapSearch(locationSearch);}
                else {
                   // automaticlocationtrace();
                }
            }

        });



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //-----------------------------------------------------------------------------------------------
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng=new LatLng(latitude,longitude);
                    Geocoder geocoder=new Geocoder(getApplicationContext());
                    try{
                        List<Address> addressList=geocoder.getFromLocation(latitude,longitude,1);
                        String string=addressList.get(0).getLocality()+"\n";
                        string+=addressList.get(0).getCountryName();
                        String finaladdr=addressList.get(0).getSubAdminArea()+"\n"+addressList.get(0).getSubLocality()+"\t"+addressList.get(0).getAdminArea()+"\n"+addressList.get(0).getPostalCode()+"\t"+string;
                        mMap.addMarker(new MarkerOptions().position(latLng).title(string));
                         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15.2f));
                        Toast.makeText(getApplicationContext(),finaladdr,Toast.LENGTH_LONG).show();



                    }catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }

            });

        }else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng latLng=new LatLng(latitude,longitude);
                    Geocoder geocoder=new Geocoder(getApplicationContext());
                    try{
                        List<Address> addressList=geocoder.getFromLocation(latitude,longitude,1);
                        String string=addressList.get(0).getLocality()+"\n";
                        string+=addressList.get(0).getCountryName();
                        String finaladdr=addressList.get(0).getSubAdminArea()+"\n"+addressList.get(0).getSubLocality()+"\t"+addressList.get(0).getAdminArea()+"\n"+addressList.get(0).getPostalCode()+"\t"+string;
                        Toast.makeText(getApplicationContext(),finaladdr,Toast.LENGTH_LONG).show();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(string));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15.2f));

                    }catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
        //-----------------------------------------------------------------------------------------------
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10.2f));
    }
    public void onMapSearch(String location) {
       // EditText locationSearch = (EditText) findViewById(R.id.addressofmap);
       // String location = locationSearch.getText().toString();
        List<Address>addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
              //  LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());



            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

            String string=addressList.get(0).getLocality()+"\n";
            string+=addressList.get(0).getCountryName();
            String finaladdr=addressList.get(0).getSubAdminArea()+"\n"+addressList.get(0).getSubLocality()+"\t"+addressList.get(0).getAdminArea()+"\n"+addressList.get(0).getPostalCode()+"\t"+string;
            Toast.makeText(getApplicationContext(),finaladdr,Toast.LENGTH_LONG).show();
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }
}
