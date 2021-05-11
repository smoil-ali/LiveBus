package com.reactive.livebus.Activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.reactive.livebus.R;
import com.reactive.livebus.Utils.Constants;
import com.reactive.livebus.model.BusClass;
import com.reactive.livebus.model.LocationClass;
import com.reactive.livebus.model.StopClass;

public class BusLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    final String TAG = BusLocationActivity.class.getSimpleName();
    private GoogleMap mMap;
    BusClass busClass;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.LOCATION);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_location);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint,10));
        addMarkers();
        getData();
    }

    void addMarkers(){
        for (StopClass stopClass:busClass.getList()){
            LatLng latLng = new LatLng(Double.parseDouble(stopClass.getLat()),
                    Double.parseDouble(stopClass.getLng()));
            mMap.addMarker(new MarkerOptions().position(latLng).title(stopClass.getStopName()));
        }
    }

    void getData(){
        databaseReference.child(busClass.getBusNumber())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            LocationClass locationClass = snapshot.getValue(LocationClass.class);
                            LatLng startPoint = new LatLng(Double.parseDouble(locationClass.getLat()),
                                    Double.parseDouble(locationClass.getLng()));
                            mMap.addMarker(new MarkerOptions().position(startPoint).title("Current Location").
                                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint,10));
                        }else {
                            Log.i(TAG,"Data dont exist");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.i(TAG,error.getMessage());
                    }
                });
    }
}