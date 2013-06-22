package com.myawesomeplaces;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockFragment;
import com.myawesomeplaces.R;

public class AddFrag extends SherlockFragment implements OnClickListener, LoaderCallbacks<PlaceSearchResult>
{
	
		View rootView;

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	        Bundle savedInstanceState) {
	        rootView = inflater.inflate(R.layout.frag_add, container, false);
	        
        
	        // set click listeners
	        Button b = (Button) rootView.findViewById(R.id.buttonSearch);
	        b.setOnClickListener(this);
	               
	        return rootView;
	    }
	    
   
	    public void onClick(View v) {
	    	switch(v.getId()) {
	    		case R.id.buttonSearch:
	    			buttonSearchClicked();
	    			break;
	    		}
	    	return;
	    }
	    
	    private void buttonSearchClicked() {
	        // Here we are going to place our REST call parameters. Note that
	        // we could have just used Uri.Builder and appendQueryParameter()
	        // here, but I wanted to illustrate how to use the Bundle params.
	        Bundle params = new Bundle();
	        params.putString("location", getLocation());
	        params.putString("radius", Integer.toString(1000));
	        
	        // Initialise the Loader.
	        getSherlockActivity().getSupportLoaderManager().initLoader(0, params, this);
	    }
	    	    
	    private String getLocation() {
	    	LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
	    	String locationProvider = LocationManager.NETWORK_PROVIDER;
	    	Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
	    	String lat = Double.toString(lastKnownLocation.getLatitude());
	    	String longi = Double.toString(lastKnownLocation.getLongitude());
	    	return lat + "," + longi;
	    }
	    
	    @Override
	    public Loader<PlaceSearchResult> onCreateLoader(int id, Bundle params) {
	        return new PlacesAPILoader(rootView.getContext(), params);
	    }

	    @Override
	    public void onLoadFinished(Loader<PlaceSearchResult> loader, PlaceSearchResult result) {
	    	AddActivity parent = (AddActivity) getSherlockActivity();
	    	parent.showSearchResults(result);
	    	/*
	        String placeName = result.getFirstName();
	        
	        if (placeName != "") {
	        	TextView tv = (TextView) rootView.findViewById(R.id.placeOutput);
	        	tv.setText(placeName);
	        } else {
	        	Toast.makeText(rootView.getContext(), "Failed to load data. Check your internet settings.", Toast.LENGTH_SHORT).show();
	        } */
	    }

	    @Override
	    public void onLoaderReset(Loader<PlaceSearchResult> loader) {
	    }
	
	}


	    	
	    
	    



