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
	
	@Override
	public String toString() {
		return mName + ", " + mVicinity;
	}
	
	public String getID() {
		return mGoogleID;
	}
	
	public String getName() {
		return mName;
	}
	
	public String getVicinity() {
		return mVicinity;
	}

}
