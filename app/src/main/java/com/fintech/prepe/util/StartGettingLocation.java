package com.fintech.prepe.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class StartGettingLocation {

    public static void setAllTheLocations(Activity activity){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(300);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ViewUtils.showToast(activity,"No Location Permission has given");
            return;
        }

        LocationServices.getFusedLocationProviderClient(activity)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(activity)
                                .removeLocationUpdates(this);
                        if(locationResult.getLocations().size() > 0){
                            int latestLocationIndex = locationResult.getLocations().size()-1;
                            UtilHolder.setLatitude(locationResult.getLocations().get(latestLocationIndex).getLatitude());
                            UtilHolder.setLongitude(locationResult.getLocations().get(latestLocationIndex).getLongitude());
                        }
                        else{
                            ViewUtils.showToast(activity,"Location issue found..");
                        }
                    }
                }, Looper.getMainLooper());
    }
}
