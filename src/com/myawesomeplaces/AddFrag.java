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

public class AddFrag extends SherlockFragment implements OnClickListener, LoaderCallbacks<RESTLoader.RESTResponse>
{
	
		SherlockFragmentActivity parent;
		View rootView;

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	        Bundle savedInstanceState) {
	        rootView = inflater.inflate(R.layout.frag_add, container, false);
	        
	        // populate spinner	        
	        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinnerDistance);
	        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.distance, android.R.layout.simple_spinner_item);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spinner.setAdapter(adapter);
	        spinner.setSelection(adapter.getPosition("30 kms"));
	        
	        // set click listeners
	        Button b = (Button) rootView.findViewById(R.id.buttonSearch);
	        b.setOnClickListener(this);
	        b = (Button) rootView.findViewById(R.id.buttonSearchNearby);
	        b.setOnClickListener(this);
	        RadioButton rb = (RadioButton) rootView.findViewById(R.id.radioAnywhere);
	        rb.setOnClickListener(this);
	        rb = (RadioButton) rootView.findViewById(R.id.radioWithin);
	        rb.setOnClickListener(this);
	        
	        parent = getSherlockActivity();
	               
	        return rootView;
	    }
	    
   
	    public void onClick(View v) {
	    	if (v instanceof RadioButton) {
		    	RadioButton rb;
		        switch(v.getId()) {
		        case R.id.radioWithin:
		        	rb = (RadioButton) getSherlockActivity().findViewById(R.id.radioAnywhere);
		        	rb.setChecked(false);
		            break;
		        case R.id.radioAnywhere:
		        	rb = (RadioButton) getSherlockActivity().findViewById(R.id.radioWithin);
		        	rb.setChecked(false);
		            break;
		        }
	    	} else {
	    		switch(v.getId()) {
	    		case R.id.buttonSearch:
	    			buttonSearchClicked();
	    			break;
	    		case R.id.buttonSearchNearby:
	    			buttonSearchNearbyClicked();
	    			break;
	    		}
	    	}
	    	return;
	    }
	    
	    private void buttonSearchClicked() {
	    	return;
	    }
	    
	    private void buttonSearchNearbyClicked() {
	        // This is our REST action.
	        Uri placesUri = Uri.parse("https://maps.googleapis.com/maps/api/place/nearbysearch/json");
	        
	        // Here we are going to place our REST call parameters. Note that
	        // we could have just used Uri.Builder and appendQueryParameter()
	        // here, but I wanted to illustrate how to use the Bundle params.
	        Bundle params = new Bundle();
	        params.putString("key", "AIzaSyAowY3puwfLTCI6pjh9oDScEiFIhS1wpLU");
	        params.putString("location", getLocation());
	        params.putString("sensor", "true");
	        params.putString("orderby", "distance");
	        params.putString("radius", Integer.toString(1000));
	        
	        // These are the loader arguments. They are stored in a Bundle because
	        // LoaderManager will maintain the state of our Loaders for us and
	        // reload the Loader if necessary. This is the whole reason why
	        // we have even bothered to implement RESTLoader.
	        Bundle args = new Bundle();
	        args.putParcelable("uri", placesUri);
	        args.putParcelable("params", params);
	        
	        // Initialise the Loader.
	        parent.getSupportLoaderManager().initLoader(0, args, this);
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
	    public Loader<RESTLoader.RESTResponse> onCreateLoader(int id, Bundle args) {
	        if (args != null && args.containsKey("uri") && args.containsKey("params")) {
	            Uri action = args.getParcelable("uri");
	            Bundle params = args.getParcelable("params");
	            
	            return new RESTLoader(rootView.getContext(), RESTLoader.HTTPVerb.GET, action, params);
	        }
	        return null;
	    }

	    @Override
	    public void onLoadFinished(Loader<RESTLoader.RESTResponse> loader, RESTLoader.RESTResponse data) {
	        int code = data.getCode();
	        String json = data.getData();
	        
	        // Check to see if we got an HTTP 200 code and have some data.
	        if (code == 200 && !json.equals("")) {
	            
	            // For really complicated JSON decoding I usually do my heavy lifting
	            // Gson and proper model classes, but for now let's keep it simple
	            // and use a utility method that relies on some of the built in
	            // JSON utilities on Android.
	            String placename = getNameFromJson(json);
	            
	            TextView tv = (TextView) rootView.findViewById(R.id.placeOutput);
	            tv.setText(placename);
	            
	            
	        }
	        else {
	            Toast.makeText(rootView.getContext(), "Failed to load Twitter data. Check your internet settings.", Toast.LENGTH_SHORT).show();
	        }
	    }

	    @Override
	    public void onLoaderReset(Loader<RESTLoader.RESTResponse> loader) {
	    }
	    
	    private static String getNameFromJson(String json) {
	    	
	    	ArrayList<String> names = new ArrayList<String>();
	    	
	    	try {
	    		JSONObject input = (JSONObject) new JSONTokener(json).nextValue();
	    		JSONArray resultsArray = input.getJSONArray("results");
	    		
	    		for (int i = 0 ; i < resultsArray.length(); i++) {
	    			JSONObject thisone = resultsArray.getJSONObject(i);
	    			names.add(thisone.getString("name"));
	    		}
	    	} catch (JSONException e) {
		            Log.e("AddFrag", "Failed to parse JSON.", e);
		        }
	    	
	        if (names.size() > 0) {
	        	return names.get(0);
	        } else {
	        	return "No results";
	        }
	        
	    }
	}


	    	
	    
	    



