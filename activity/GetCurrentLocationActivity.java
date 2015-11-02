package com.thousandsunny.kemis.activity;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Typeface;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thousandsunny.kemis.Constants;
import com.thousandsunny.kemis.FetchAddressIntentService;
import com.thousandsunny.kemis.R;

public class GetCurrentLocationActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult>, GoogleMap.InfoWindowAdapter {

    private Toolbar toolbar;

    protected static final String TAG = "location-settings";

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private MapFragment mapFragment;

    private Location mCameraPositionLocation;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private TextView namaJalan;
    private TextView label;

    private LatLng myCurrentLocation;

    private Marker marker;

    private AddressResultReceiver mResultReceiver;

    /**
     * Constant used in the location settings dialog.
     */
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    protected final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    protected final static String KEY_LOCATION = "location";
    protected final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    protected LocationSettingsRequest mLocationSettingsRequest;

    protected boolean mAddressRequested;

    /**
     * The formatted location address.
     */
    protected String mAddressOutput;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    protected void startIntentService(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_current_location);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mResultReceiver = new AddressResultReceiver(new Handler());
        // Set defaults, then update using values stored in the Bundle.
        mAddressRequested = false;
        mAddressOutput = "";

        if (checkPlayServices()) {
            System.out.println("masuk sini");
            buildGoogleApiClient();
            createLocationRequest();
            buildLocationSettingsRequest();
        } else {
            System.out.println("gak masuk");
        }
    }

    protected synchronized void buildGoogleApiClient() {
        System.out.println("buildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void buildLocationSettingsRequest() {
        System.out.println("build location settings request");
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Check if the device's location settings are adequate for the app's needs using the
     * {@link com.google.android.gms.location.SettingsApi#checkLocationSettings(GoogleApiClient,
     * LocationSettingsRequest)} method, with the results provided through a {@code PendingResult}.
     */
    protected void checkLocationSettings() {
        System.out.println("check location settings");
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(this,
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_current_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button_background, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button_background
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        System.out.println("onPostCreated");
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
    }

    @Override
    public void onConnected(Bundle bundle) {
        System.out.println("onConnected");
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("onConnecttionSuspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("onConnectionFailed");
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        System.out.println("onMapReady");

        googleMap.setInfoWindowAdapter(this);

        googleMap.setMyLocationEnabled(true);

        if(mLastLocation == null){
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            myCurrentLocation = getCurrentLocation(mLastLocation);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCurrentLocation, 13));
        }

        if (mLastLocation != null) {
            showMarker(googleMap);
        }

        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                System.out.println("onMyLocationButtonClick");
                checkLocationSettings();
                return false;
            }
        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if((namaJalan.getText().toString() == getString(R.string.no_address_found)) || (namaJalan.getText().toString() == getString(R.string.service_not_available)) ||
                        (namaJalan.getText().toString() == getString(R.string.no_location_data_provided)) || (namaJalan.getText().toString() == getString(R.string.invalid_lat_long_used))){
                    System.out.println("lokasi tidak tersedia");
                    Toast.makeText(GetCurrentLocationActivity.this, "Lokasi tidak tersedia.", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("lokasi tersedia");
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("alamat", mAddressOutput);
                    returnIntent.putExtra("latitude", marker.getPosition().latitude);
                    returnIntent.putExtra("longitude", marker.getPosition().longitude);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }
        });

        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                String currentAddress = null, oldAddress = null;

                if (marker != null) {
                    mCameraPositionLocation = new Location("location");
                    mCameraPositionLocation.setLatitude(cameraPosition.target.latitude);
                    mCameraPositionLocation.setLongitude(cameraPosition.target.longitude);
                    // Only start the service to fetch the address if GoogleApiClient is
                    // connected.
                    if (mGoogleApiClient.isConnected() && mCameraPositionLocation != null) {
                        startIntentService(mCameraPositionLocation);
                    }
                    // If GoogleApiClient isn't connected, process the user's request by
                    // setting mAddressRequested to true. Later, when GoogleApiClient connects,
                    // launch the service to fetch the address. As far as the user is
                    // concerned, pressing the Fetch Address button_background
                    // immediately kicks off the process of getting the address.
                    mAddressRequested = true;

                    marker.setPosition(cameraPosition.target);

                    mAddressOutput = null;
                }
            }
        });
    }

    private void showMarker(GoogleMap googleMap) {
        // Determine whether a Geocoder is available.
        if(marker == null){
            if (!Geocoder.isPresent()) {
                Toast.makeText(this, R.string.no_geocoder_available,
                        Toast.LENGTH_LONG).show();
            } else {
                if (mGoogleApiClient.isConnected()) {
                    startIntentService(mLastLocation);
                }
                mAddressRequested = true;
                marker = googleMap.addMarker(new MarkerOptions()
                        .position(myCurrentLocation)
                        .title(mAddressOutput));
                mAddressOutput = null;
            }
        }
    }

    private LatLng getCurrentLocation(Location mLastLocation) {
        if (mLastLocation == null) {
            System.out.println("onConnected - mGoogleLastLocation null");
            myCurrentLocation = new LatLng(-6.921649, 107.611023);
            //Toast.makeText(this, "Can't get location. Make sure you have gps/location services enabled.", Toast.LENGTH_SHORT).show();
            //checkLocationSettings();
            //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            System.out.println("onConnected - mGoogleLastLocation tidak null");
            myCurrentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
        return myCurrentLocation;
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart");
        if (mGoogleApiClient != null) {
            System.out.println("onStart - mGoogleApiClient tidak null");
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("onStop");
        if (mGoogleApiClient != null) {
            System.out.println("onStop - mGoogleApiClient tidak null");
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume");
        if (mGoogleApiClient.isConnected()) {
            System.out.println("onResume - mGoogleApiClient is connected");
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                System.out.println("All location settings are satisfied.");
                mGoogleApiClient.connect();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                System.out.println("Location settings are not satisfied. Show the user a dialog to" +
                        "upgrade location settings ");
                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    Log.i(TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                System.out.println("Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");

                break;
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.info_window_layout, null);

        Typeface typefaceBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        Typeface typefaceLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        Typeface typefaceMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        namaJalan = (TextView) view.findViewById(R.id.alamatTV);
        label = (TextView) view.findViewById(R.id.label);

        namaJalan.setTypeface(typefaceMedium);
        label.setTypeface(typefaceLight);

        namaJalan.setText(mAddressOutput);

        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string
            // or an error message sent from the intent service.
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                //showToast(getString(R.string.address_found));
                marker.showInfoWindow();

            } else {
                marker.setTitle("Loading");
            }

            marker.showInfoWindow();

            // Reset. Enable the Fetch Address button_background and stop showing the progress bar.
            mAddressRequested = false;

        }
    }

}
