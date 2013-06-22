package com.myawesomeplaces;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PlaceSearchResult implements ListAdapter{
	
	private Context mContext;
	private Place[] mPlaces;
	private int mNoOfResults;
	
	public PlaceSearchResult (Context context, String jsonResult) {
		mContext = context;
		if (jsonResult == "") {
				mNoOfResults = 0;
		} else {
			parsePlacesFromJson(jsonResult);
		}
	}
	
	public PlaceSearchResult () {
		mNoOfResults = 0;
	}
	
	public String getFirstName() {
		return mPlaces[0].toString();
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
			mNoOfResults = 0;
			Log.e("AddFrag", "Failed to parse JSON.", je);
		}
		return;
	}

	@Override
	public int getCount() {
		return mNoOfResults;
	}

	@Override
	public Object getItem(int arg0) {
		if (mPlaces[arg0] != null) {
			return mPlaces[arg0];
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public int getItemViewType(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		if (mPlaces[arg0] == null) {
			return null;
		}
		
		View lineView;
		if (arg1 == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			lineView = inflater.inflate(R.layout.view_twolineplace, null);
		} else {
			lineView = arg1;
		}
		
		TextView name = (TextView) lineView.findViewById(R.id.placeName);
		TextView vicinity = (TextView) lineView.findViewById(R.id.placeVicinity);
		name.setText(mPlaces[arg0].getName());
		vicinity.setText(mPlaces[arg0].getVicinity());
		
		return lineView;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return (mNoOfResults == 0);
	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub
		/* leaving blank for now as results won't change.  Could in future implement this,
		   have placesloader result straight away, and then update via this observer then? */
		 
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int arg0) {
		return true;
	}
	

	
	
}
