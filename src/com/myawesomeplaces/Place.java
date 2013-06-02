package com.myawesomeplaces;

import android.location.Location;

public class Place {
	
	private String mName;
	private String mVicinity;
	private Location mLocation;
	private String mGoogleID;
	private String mGoogleRef;
	
	public Place(String name, String vicinity, String googleID, String googleRef, double latitude, double longitude) {
		mName = name;
		mVicinity = vicinity;
		mGoogleID = googleID;
		mGoogleRef = googleRef;
		mLocation = new Location("GPlaces");
		mLocation.setLatitude(latitude);
		mLocation.setLongitude(longitude);
	}
	
	public String getNameNeighbourhood() {
		return mName + ", " + mVicinity;
	}

}
