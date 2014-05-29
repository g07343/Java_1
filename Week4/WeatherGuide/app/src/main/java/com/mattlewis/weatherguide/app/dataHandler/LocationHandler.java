//Matthew Lewis
//term 1405
//Week 3-4 LocationHandler class

package com.mattlewis.weatherguide.app.dataHandler;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mattlewis.weatherguide.app.MainActivity;

import java.io.IOException;
import java.util.List;

public class LocationHandler {

    //debugging tag
    static String TAG = "LOCATION - LocationHandler";

    //use two double variables to hold our latitude and longitude
    public static Double latitude;
    public static Double longitude;

    //this function will attempt to get the user's location determined by location service
    public static String getZip(Context context) {

        //set up string to hold postalCode
        String postalCode;

        //get an instance of location manager
        final android.location.LocationManager locationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //create a location listener
        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.

                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        //need to check if being run within an emulator, since this causes havoc.
        String brand = Build.BRAND;
        if (brand.compareTo("generic") == 0)
        {
            //set location manually to avoid headache
            Toast.makeText(MainActivity.context, "Emulator detected!  Manually setting location so app doesn't crash!",
                    Toast.LENGTH_LONG).show();
            return "63104";
        }

        //set the source we want to pull location from, just network for now...
        //String locationProvider = android.location.LocationManager.NETWORK_PROVIDER;

        Criteria criteria = new Criteria();

        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        String locationProvider = locationManager.getBestProvider(criteria, true);

        //request location updates for the users current whereabouts
        //locationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);


        //get last location from locationManager
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        //check if the last location was null, meaning no data on user's location
        if (lastKnownLocation == null)
        {
            System.out.println("NO LAST LOCATION!");
            //return null to MainActivity class so we can proceed to the next step
            return null;
        } else {
            //last location was found, so get it's longitude and latitude
            latitude = lastKnownLocation.getLatitude();
            longitude = lastKnownLocation.getLongitude();

            //create a Geocoder to get a nearby address, and using that, get postal code for our API's URL
            Geocoder geo = new Geocoder(context);
            try {
                //list of addresses from Geocoder; we only want one for our needs
                List<Address> address = geo.getFromLocation(latitude, longitude, 1);

                //set an address to the one from the list
                Address foundAddress = address.get(0);

                //get the postalCode and return it
                postalCode = foundAddress.getPostalCode();
                return postalCode;

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.toString());
            }
            //stop tracking location, since we either do or don't have what we need (Don't want to have it keep checking constantly)
            locationManager.removeUpdates(locationListener);
        }
        locationManager.removeUpdates(locationListener);
        //not found, return null to MainActivity and proceed to next step in startup flow
        return null;
    }
}
