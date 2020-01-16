package com.example.myapplication.Services;


import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
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
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;


public class GetDirectionsData extends AsyncTask<Object,String,String> {

    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    String duration, distance;
    LatLng latLng;
    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];
        latLng = (LatLng)objects[2];



        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleDirectionsData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s) {

        String[] directionsList;
        DataParser parser = new DataParser();
       directionsList = parser.parseDirections(s);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
       //DatabaseReference[] myRef=new DatabaseReference[directionsList.length];
         List<String> arr=new ArrayList<>();
         for(int i=0;i<directionsList.length;i++)
         {
             arr.add(directionsList[i]);
         }
         DatabaseReference myRef=database.getReference("location");
         myRef.setValue(arr);
        /*for(int i=0;i<directionsList.length;i++) {
            //myRef.setValue(directionsList[i]);
            myRef[i] = database.getReference("location");
            myRef[i].setValue(directionsList[i]);
        }
       // List<String> arr=new ArrayList<>();
        for(int i=0;i<directionsList.length;i++)
        {
           // arr.add(directionsList[i].toString());
            Log.i("directions",directionsList[i]);
        }

       /* myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/
        displayDirection(directionsList);

           // Arrays.stream(directionsList).forEach(i-> Log.i("tag2",i));

    }

    public void displayDirection(String[] directionsList)
    {

        int count = directionsList.length;
        for(int i = 0;i<count;i++)
        {
            PolylineOptions options = new PolylineOptions();
            options.color(Color.RED);
            options.width(10);
            options.addAll(PolyUtil.decode(directionsList[i]));

            mMap.addPolyline(options);
        }
    }






}






