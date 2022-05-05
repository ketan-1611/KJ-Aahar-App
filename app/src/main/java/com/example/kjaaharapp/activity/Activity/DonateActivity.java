package com.example.kjaaharapp.activity.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.kjaaharapp.R;
import com.example.kjaaharapp.databinding.ActivityDonateBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;

public class DonateActivity extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, LocationListener {

    ActivityDonateBinding binding;
    GoogleApiClient mGoogleApiClient;

    Location mLastLocation;
    com.google.android.gms.location.LocationRequest mLocationRequest;
    private int REQUEST_CODE = 1;
    private GoogleMap mMap;
    SupportMapFragment supportMapFragment;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            supportMapFragment.getMapAsync(this);
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE) ;
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        buildGoogleApiClient();
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                supportMapFragment.getMapAsync(this);
            }else{
                Toast.makeText(DonateActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new com.google.android.gms.location.LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this::onLocationChanged);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mLastLocation = location;

        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("You are here");
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        mMap.addMarker(markerOptions).showInfoWindow();


        binding.btnSubmitDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog("Please wait.. ");
                String name = binding.etNameDonate.getText().toString().trim();
                String foodItem = binding.etItemDonate.getText().toString().trim();
                String phone = binding.etPhoneDonate.getText().toString().trim();
                String description = binding.etDescriptionDonate.getText().toString().trim();
                String type = "Donor";


                if (TextUtils.isEmpty(name)){
                    binding.etNameDonate.setError("Name is Required");
                    return;
                }

                if (TextUtils.isEmpty(foodItem)){
                    binding.etItemDonate.setError("Item Details are Required. ");
                    return;
                }

                if (binding.etPhoneDonate.length()<10){
                    binding.etPhoneDonate.setError("Phone number required 10 digits ");
                    return ;
                }

                String userId = auth.getCurrentUser().getUid();



                GeoPoint geoPoint = new GeoPoint(location.getLatitude(),location.getLongitude());

                Map<String,Object>data = new HashMap<>();

                data.put("Timestamp", FieldValue.serverTimestamp());
                data.put("Name",name);
                data.put("Food item",foodItem);
                data.put("Phone",phone);
                data.put("Description",description);
                data.put("Location",geoPoint);
                data.put("Userid",userId);
                data.put("Type",type);

                CollectionReference collectionReference = firestore.collection("User data");

                collectionReference.add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                hideProgressDialog();
                                Toast.makeText(getApplicationContext(), "   Success !", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        Toast.makeText(getApplicationContext(), "Error !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }
}