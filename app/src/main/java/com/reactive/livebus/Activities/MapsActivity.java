package com.reactive.livebus.Activities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.reactive.livebus.R;
import com.reactive.livebus.Utils.Constants;
import com.reactive.livebus.model.BusClass;
import com.reactive.livebus.model.StopClass;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    BusClass busClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (getIntent().getExtras() != null){
            busClass = (BusClass) getIntent().getExtras().getSerializable(Constants.PARAMS);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng startPoint = new LatLng(Double.parseDouble(busClass.getStartLat()),
                Double.parseDouble(busClass.getStartLng()));

        mMap.addMarker(new MarkerOptions().position(startPoint).title("Start Point"+" "+busClass.getStartPoint()));

        LatLng destinationPoint = new LatLng(Double.parseDouble(busClass.getDestinationLat()),
                Double.parseDouble(busClass.getDestinationLng()));
        mMap.addMarker(new MarkerOptions().position(destinationPoint).title("Destination"+" "+busClass.getDestination()));
        addMarkers();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint,10));
    }

    void addMarkers(){
        for (StopClass stopClass:busClass.getList()){
            LatLng latLng = new LatLng(Double.parseDouble(stopClass.getLat()),
                    Double.parseDouble(stopClass.getLng()));
            mMap.addMarker(new MarkerOptions().position(latLng).title(stopClass.getStopName()));
        }
    }
}