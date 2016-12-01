package com.tproteiri.tp2otimizacao;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tproteiri.tp2otimizacao.models.City;
import com.tproteiri.tp2otimizacao.utils.CacheMemory;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Address mAddress;
    private LatLng mLatLong;
    private Button mSelectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mSelectBtn = (Button) findViewById(R.id.select);
        mSelectBtn.setEnabled(false);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(-19.939764, -43.949337), 2));

        for (City c1 : CacheMemory.getCities()) {
            if (c1.getLinks() != null) {
                for (City c2 : c1.getLinks().keySet()) {
                    PolylineOptions poly = new PolylineOptions().geodesic(true);
                    poly.add(new LatLng(c1.getLatitude(), c1.getLongitude()));
                    poly.add(new LatLng(c2.getLatitude(), c2.getLongitude()));
                    mMap.addPolyline(poly);
                }
            }
        }

    }

    public void onMapSearch(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        Geocoder geocoder = new Geocoder(this);
        try {
            addressList = geocoder.getFromLocationName(location, 1);
            mSelectBtn.setEnabled(true);
        } catch (IOException e) {
            mSelectBtn.setEnabled(false);
            e.printStackTrace();
        }

        Address address = addressList.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        mLatLong = latLng;
        mAddress = address;

        if (address != null && addressList != null && addressList.get(0) != null) {
            mMap.addMarker(new MarkerOptions().position(latLng).title(address.getLocality()));
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    public void onSelectLocation(View view) {
        City city = new City(mAddress.getLocality(), mLatLong.latitude, mLatLong.longitude);
        CacheMemory.addCity(city);
        finish();
    }
}
