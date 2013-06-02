package com.myawesomeplaces;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;
import android.widget.Toast;

public class PlaceSearchResult {
	
	private Place[] mPlaces;
	private boolean mHaveResults;
	private int mNoOfResults;
	private boolean mJsonError;
	
	public PlaceSearchResult (String jsonResult) {
		
		if (jsonResult == "") {
				mHaveResults = false;
		} else {
			mHaveResults = true;
			parsePlacesFromJson(jsonResult);
		}
	}
	
	public PlaceSearchResult () {
		mHaveResults = false;
	}
	
	public String getFirstName() {
		return mPlaces[0].getNameNeighbourhood();
	}
	
	public boolean hasResults() {
		return mHaveResults;
	}
	
	private void parsePlacesFromJson(String json) {
		
		try {
			JSONObject input = (JSONObject) new JSONTokener(json).nextValue();
			JSONArray resultsArray = input.getJSONArray("results");
			
			mNoOfResults = resultsArray.length();
			mPlaces = new Place[mNoOfResults];
			
			for (int n = 0 ; n < mNoOfResults ; n++) {
				JSONObject thisone = resultsArray.getJSONObject(n);
				String id = thisone.getString("id");
				String name = thisone.getString("name");
				String vicinity = thisone.getString("vicinity");
				String ref = thisone.getString("reference");
				JSONObject location = thisone.getJSONObject("geometry").getJSONObject("location");
				double latitude = location.getDouble("lat");
				double longitude = location.getDouble("lng");
				
				mPlaces[n] = new Place(name, vicinity, id, ref, latitude, longitude);
			}
		} catch (JSONException je) {
			mHaveResults = false;
			Log.e("AddFrag", "Failed to parse JSON.", je);
		}
		return;
	}
	

	
	
}
