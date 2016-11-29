package com.egco428.a23273;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String user;
    String pass;
    String lat;
    String longitude_main;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        lat = intent.getStringExtra(MainActivity.Latitude);
        longitude_main = intent.getStringExtra(MainActivity.Longtitude);
        title = intent.getStringExtra(MainActivity.Title);
        setTitle(title+"'s location");


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap(Double.parseDouble(lat),Double.parseDouble(longitude_main));
    }

    private void setUpMap(Double latitude,Double longtitude) {
        LatLng locaion = new LatLng(latitude,longtitude);
        mMap.addMarker(new MarkerOptions().position(locaion));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locaion));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return  true;
    }

}
