package com.example.myapplication;

import com.example.myapplication.Services.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;

import android.os.Bundle;

import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.graphics.Bitmap.*;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener
{
    //StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    int PROXIMITY_RADIUS = 10000;
    double latitude, longitude;
   double end_latitude, end_longitude;
    static double count=0;
    String spokenText="";
    //FirebaseDatabase database=FirebaseDatabase.getInstance();
    //DatabaseReference myref=database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            finish();
        }
        else {
            Log.d("onCreate","Google Play Services available.");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMarkerDragListener(this);
        mMap.setOnMarkerClickListener(this);


    }

   void addtrafficlights()

   {
       List<CustomLocation> arr=new ArrayList<>();
       arr.add(new CustomLocation(26.8488802,75.80313030000002));
       arr.add(new CustomLocation(26.841994,75.80136849999997));
       arr.add(new CustomLocation(26.8677536,75.80818939999995));
       arr.add(new CustomLocation(26.8753149,75.81009799999993));
       arr.add(new CustomLocation(26.8895834,75.81373540000004));
       arr.add(new CustomLocation(26.8920617,75.8143844));
       arr.add(new CustomLocation(26.894823,75.8150703));
       arr.add(new CustomLocation(26.7853325,75.8223634));
       arr.add(new CustomLocation(26.8036951,75.80850399999997));
       arr.add(new CustomLocation(26.8107868,75.80316770000002));
       arr.add(new CustomLocation(26.79664, 75.81378));
       arr.add(new CustomLocation(26.80369, 75.8085));
       arr.add(new CustomLocation(26.81078, 75.80316));
       arr.add(new CustomLocation(26.83897, 75.79383));
       arr.add(new CustomLocation(26.84083, 75.79369));
       arr.add(new CustomLocation(26.85077, 75.79418));
       arr.add(new CustomLocation(26.85678, 75.79518));
       arr.add(new CustomLocation(26.86564, 75.79639));
       arr.add(new CustomLocation(26.8398, 75.77708));
       arr.add(new CustomLocation(26.8405, 75.77028));
       arr.add(new CustomLocation(26.83948, 75.76772));
       arr.add(new CustomLocation(26.86244, 75.79599));
       arr.add(new CustomLocation(26.86288, 75.79278));
       arr.add(new CustomLocation(26.86864, 75.78389));
       arr.add(new CustomLocation(26.87113, 75.78064));
       arr.add(new CustomLocation(26.87366, 75.77656));
       arr.add(new CustomLocation(26.87746, 75.77265));
       arr.add(new CustomLocation(26.88068, 75.76682));
       arr.add(new CustomLocation(26.87276, 75.79796));
       arr.add(new CustomLocation(26.87242, 75.79735));
       arr.add(new CustomLocation(26.88076, 75.79947));
       arr.add(new CustomLocation(26.88666, 75.80247));
       arr.add(new CustomLocation(26.89169, 75.80609));
       arr.add(new CustomLocation(26.89483, 75.80871));
       arr.add(new CustomLocation(26.89705, 75.81047));
       arr.add(new CustomLocation(26.89953, 75.81252));
       arr.add(new CustomLocation(26.90386, 75.8144));



       //arr.stream().forEach(i->setCustomMarker(i));
       //arr.addAll(new []CustomLocation{(26.78533, 75.82236)

       for(CustomLocation cus:arr)
       {
           //setCustomMarker(new LatLng(cus.getLat(),cus.getLongitude()));
           LatLng trafficLocation = new LatLng(cus.lat, cus.longitude);
           //mMap.addMarker(new MarkerOptions().draggable(false).position(trafficLocation).title("u1").icon(BitmapDescriptorFactory.fromResource(R.drawable.t)));

          //mMap.addMarker(new MarkerOptions().draggable(false).position(trafficLocation).title("u1").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

           mMap.addMarker(new MarkerOptions().draggable(false).position(trafficLocation).title("u1").icon(BitmapDescriptorFactory.fromAsset("c.png")));

       }
   }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void onClick3(View v)
    {
        EditText tf_location = (EditText) findViewById(R.id.TF_location);
        String location = tf_location.getText().toString();
        List<Address> addressList = null;
        MarkerOptions markerOptions = new MarkerOptions();
        Log.d("location = ", location);

        if (!location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 5);

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addressList != null) {
                for (int i = 0; i < addressList.size(); i++) {
                    Address myAddress = addressList.get(i);
                    LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
                    double end_latitude2=latLng.latitude;
                    double end_longitude2=latLng.longitude;
                    end_latitude=latLng.latitude;
                    end_longitude=latLng.longitude;
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef=database.getReference("cordinates");
                    List<Double> arr=new ArrayList<>();
                    arr.add(latitude);
                    arr.add(longitude);
                    arr.add(end_latitude2);
                    arr.add(end_longitude2);
                    myRef.setValue(arr);
                    markerOptions.position(latLng);
                    mMap.addMarker(markerOptions);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }

        }
    }
    public void onClick4()
    {
        List<Address> addressList = null;
        MarkerOptions markerOptions = new MarkerOptions();
        Log.d("location = ", spokenText);

        if (!spokenText.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(spokenText, 5);

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addressList != null) {
                for (int i = 0; i < addressList.size(); i++) {
                    Address myAddress = addressList.get(i);
                    LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
                    double end_latitude2=latLng.latitude;
                    double end_longitude2=latLng.longitude;
                    end_latitude=latLng.latitude;
                    end_longitude=latLng.longitude;
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef=database.getReference("cordinates");
                    List<Double> arr=new ArrayList<>();
                    arr.add(latitude);
                    arr.add(longitude);
                    arr.add(end_latitude2);
                    arr.add(end_longitude2);
                    myRef.setValue(arr);
                    markerOptions.position(latLng);
                    mMap.addMarker(markerOptions);
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }

        }
    }
    public void onClick(View v)
    {
        Object dataTransfer[] = new Object[3];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();


        switch(v.getId()) {

            case R.id.hospitals:
                //mMap.clear();
                String hospital = "hospital";
                String url = getUrl(latitude, longitude, hospital);

                dataTransfer[0] = mMap;
                dataTransfer[1] = url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Showing Nearby Hospitals", Toast.LENGTH_LONG).show();

                break;
            case R.id.B_to:
                  request();
                addtrafficlights();
                // Get a reference to our posts
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("count");

// Attach a listener to read the data at our posts reference
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Post post = dataSnapshot.getValue(Post.class);
                        //System.out.println(post);
                        if(dataSnapshot.exists() && dataSnapshot.getValue()!= null){
                             count= ((Long) dataSnapshot.getValue()).intValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
                count+=1;
                ref.setValue(count);
                break;


            /*case R.id.button2:
                mMap.clear();
               onMapReady(mMap);
                String hospital2 = "hospital";
                String url2 = getUrl(latitude, longitude, hospital2);

                dataTransfer[0] = mMap;
                dataTransfer[1] = url2;

                getNearbyPlacesData.execute(dataTransfer);

               break;*/
            case R.id.button3:
                mMap.clear();
                onMapReady(mMap);
                break;


                                                                        }
    }

    private String getDirectionsUrl(StringBuilder googleDirectionsUrl)
    {


           Log.i("service used",googleDirectionsUrl.toString());
        return googleDirectionsUrl.toString();
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace)
    {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyAxxUpRPzUNb16HdMLxsk4pfnCBO74WkWU");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }




    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "entered");

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();


        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //LatLng latLng2=latLng;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("currentlocation");

// Attach a listener to read the data at our posts reference
        ref.setValue(latLng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.draggable(true);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


        Toast.makeText(MapsActivity.this,"Your Current Location", Toast.LENGTH_LONG).show();


        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            Log.d("onLocationChanged", "Removing Location Updates");
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.setDraggable(true);
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
         end_latitude = marker.getPosition().latitude;
         end_longitude =  marker.getPosition().longitude;

        Log.i("end_lat",""+end_latitude);
        Log.i("end_lng",""+end_longitude);
        //request();
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("cordinates");
        List<Double> arr=new ArrayList<>();
        arr.add(latitude);
        arr.add(longitude);
        arr.add(end_latitude);
        arr.add(end_longitude);
        myRef.setValue(arr);*/
    }

    /*public void onClick2(View view)
    {
        addtrafficlights();
    }*/
    public void request()
    {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin="+latitude+","+longitude);
        googleDirectionsUrl.append("&destination="+end_latitude+","+end_longitude);
        googleDirectionsUrl.append("&key="+"AIzaSyAxxUpRPzUNb16HdMLxsk4pfnCBO74WkWU");
        Log.i("service used",googleDirectionsUrl.toString());
         FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("cordinates");
        List<Double> arr=new ArrayList<>();
        arr.add(latitude);
        arr.add(longitude);
        arr.add(end_latitude);
        arr.add(end_longitude);
        myRef.setValue(arr);
        polylineDrawer(googleDirectionsUrl);
    }
    public void polylineDrawer(StringBuilder googleDirectionsUrl)
    {

        String url=getDirectionsUrl(googleDirectionsUrl);

        Object [] dataTransfer=new Object[3];
        GetDirectionsData getDirectionsData = new GetDirectionsData();
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
        dataTransfer[2] = new LatLng(end_latitude, end_longitude);
        getDirectionsData.execute(dataTransfer);
    }
    private static final int SPEECH_REQUEST_CODE = 0;

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
             spokenText = results.get(0);
             onClick4();
            // Do something with spokenText
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}


