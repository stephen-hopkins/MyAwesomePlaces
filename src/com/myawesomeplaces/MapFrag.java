package com.myawesomeplaces;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;


public class MapFrag extends SherlockMapFragment implements LocationListener {

	private GoogleMap gMap;
	private LocationManager locationManager;
	private static final long MIN_TIME = 400;
	private static final float MIN_DISTANCE = 1000;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        gMap = getMap();
        locationManager = (LocationManager) getSherlockActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER   
 
        return root;
    }
	
	@Override
	public void onLocationChanged(Location location) {
	    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
	    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
	    gMap.animateCamera(cameraUpdate);
	    locationManager.removeUpdates(this);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) { }

	@Override
	public void onProviderEnabled(String provider) { }

	@Override
	public void onProviderDisabled(String provider) { }
}
