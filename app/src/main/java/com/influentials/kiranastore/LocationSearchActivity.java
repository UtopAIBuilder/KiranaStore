package com.influentials.kiranastore;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class LocationSearchActivity extends FragmentActivity implements OnConnectionFailedListener{

    private SharedPreferences sharedPreferences;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2;
    private static final int REQUEST_CHECK_SETTINGS=3;
    boolean mLocationPermissionGranted=true;
    private GoogleApiClient mGoogleApiClient;
    private  int status;
    ProgressBar progressoLocati;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);
       progressoLocati= (ProgressBar) findViewById(R.id.progressoLocation);





        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();
        mGoogleApiClient.connect();


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setHint("Search for nearby landmark");

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("Bz", "Place: " + place.getName());

                sharedPreferences = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("first", false);
                // CharSequence ActionBarTitle=sharedPreferences.getString("ActionBar",place.getName().toString());
                editor.putString("ActionBarTitle", place.getName().toString());
                editor.commit();
                Intent intent = new Intent(LocationSearchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Bz", "An error occurred: " + status);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    public void currentLocationClick(View View) {
        Log.d("Bz", "clicked Current location");


        progressoLocati.setVisibility(View.VISIBLE);
        status = NetworkUtil.getConnectivityStatusString(this);


        if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {


            Intent intent = new Intent(this, NoInternetConnection.class);
            startActivity(intent);
            progressoLocati.setVisibility(View.GONE);
            Log.d("Bz","just after the intent");

        } else {

            createLocationRequest();
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {


                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    Toast.makeText(this,"permission required to identify current location",Toast.LENGTH_LONG).show();

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                }

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.

            }
            else
            {
                Log.d("Bz","already had the location permission,called updatelocationUi");
                updateLocationUI();
            }
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        Log.d("Bz","request code: "+requestCode);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                Log.d("Bz","location request code matched");
                // If request is cancelled, the result arrays are empty.
                Log.d("Bz","grantresults: "+grantResults.length+" grantResults value: "+grantResults[0]);
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        Log.d("Bz","in onRequestPermission result");
        updateLocationUI();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


        Log.i("Bz", "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }

    public void updateLocationUI() {
        Log.d("Bz","in updateLocationUi: "+mLocationPermissionGranted);

        if (mLocationPermissionGranted) {

            @SuppressWarnings("MissingPermission")

            PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                    .getCurrentPlace(mGoogleApiClient, null);
            result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                    Log.d("Bz","in Onresult but not in loop");

                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                        Log.i("Bz", String.format("Place '%s' has likelihood: %g",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));


                        sharedPreferences = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("first", false);
                        // CharSequence ActionBarTitle=sharedPreferences.getString("ActionBar",place.getName().toString());
                        editor.putString("ActionBarTitle",placeLikelihood.getPlace().getName().toString());
                        editor.commit();
                        Intent intent = new Intent(LocationSearchActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        progressoLocati.setVisibility(View.GONE);
                        Log.d("Bz","getAddress: "+placeLikelihood.getPlace().getAddress());

                     break;

                    }
                    likelyPlaces.release();
                }
            });
        }
    }

    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);


// ...

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(LocationSearchActivity.this,
                                    REQUEST_CHECK_SETTINGS);


                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
               Log.d("Bz","Walla it's working");
            }
            else
            {
                Log.d("Bz","resultCode: "+resultCode);
            }
        }
        else Log.d("Bz","requestCodeNotMatching: "+resultCode);

    }
}
