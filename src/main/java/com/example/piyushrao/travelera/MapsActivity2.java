package com.example.piyushrao.travelera;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps3);
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
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        SQLiteDatabase db;
        db = openOrCreateDatabase("TestingData.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());

        Cursor cursor1 = db.rawQuery("SELECT * FROM TourSites1", null);
        cursor1.moveToFirst();

        double lat,lon;
        String place;
        LatLng liberty = new LatLng(0,0);
        do
        {
            lat = cursor1.getDouble(4);
            lon = cursor1.getDouble(5);
            place = cursor1.getString(1);
            liberty = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(liberty).title(place));
        }while(cursor1.moveToNext());
        cursor1.close();
        db.close();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(liberty));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));//*/
        // TODO http://stackoverflow.com/questions/17061063/map-search-bar-in-google-maps-v2#27243187
    }
}
