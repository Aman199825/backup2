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

import org.jetbrains.annotations.NotNull;

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
         @NotNull
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

        displayDirection(directionsList);



    }

    public void displayDirection(@NotNull String[] directionsList)
    {
      if(directionsList!=null) {
          int count = directionsList.length;
          for (int i = 0; i < count; i++) {
              PolylineOptions options = new PolylineOptions();
              options.color(Color.BLUE);
              options.width(20);
              options.addAll(PolyUtil.decode(directionsList[i]));

              mMap.addPolyline(options);
          }
      }
    }






}






