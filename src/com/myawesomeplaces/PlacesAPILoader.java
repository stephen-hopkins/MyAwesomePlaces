package com.myawesomeplaces;

import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class PlacesAPILoader extends AsyncTaskLoader<PlaceSearchResult> {
    private static final String TAG = PlacesAPILoader.class.getName();
    
    private static final Uri mPlacesURL = Uri.parse("https://maps.googleapis.com/maps/api/place/nearbysearch/json");
    
    // We use this delta to determine if our cached data is
    // old or not. The value we have here is 10 minutes;
    private static final long STALE_DELTA = 600000;
    
    
    private Bundle mParams;
    private PlaceSearchResult mResult;
    
    private long mLastLoad;
    
    public PlacesAPILoader(Context context) {
        super(context);
    }
    
 
    public PlacesAPILoader(Context context, Bundle params) {
        super(context);
        mParams = params;
    }

    @Override
    public PlaceSearchResult loadInBackground() {
        try {
         
            // Here we define our base request object which we will
            // send to our REST service via HttpClient.
            HttpRequestBase request = new HttpGet();
            attachUriWithQuery(request, mPlacesURL, mParams);
            HttpClient client = new DefaultHttpClient();
                
            // Let's send some useful debug information so we can monitor things
            // in LogCat.
            Log.d(TAG, "Executing request: GET: "+ mPlacesURL.toString());
            Log.d(TAG, "Params: " + request.getURI().toASCIIString());

            // Finally, we send our request using HTTP. This is the synchronous
            // long operation that we need to run on this Loader's thread.
            HttpResponse response = client.execute(request);

            HttpEntity responseEntity = response.getEntity();
            StatusLine responseStatus = response.getStatusLine();
            int statusCode = responseStatus != null ? responseStatus.getStatusCode() : 0;
            
            if (statusCode == 200) {
            	PlaceSearchResult result = new PlaceSearchResult(EntityUtils.toString(responseEntity));
                return result;
            } else {
            	return new PlaceSearchResult();
            }

        }
        catch (UnsupportedEncodingException e) {
            Log.e(TAG, "A UrlEncodedFormEntity was created with an unsupported encoding.", e);
            return new PlaceSearchResult();
        }
        catch (ClientProtocolException e) {
            Log.e(TAG, "There was a problem when sending the request.", e);
            return new PlaceSearchResult();
        }
        catch (IOException e) {
            Log.e(TAG, "There was a problem when sending the request.", e);
            return new PlaceSearchResult();
        }
    }
    
    @Override
    public void deliverResult(PlaceSearchResult result) {
        // Here we cache our response.
        mResult = result;
        super.deliverResult(result);
    }
    
    @Override
    protected void onStartLoading() {
        if (mResult != null) {
            // We have a cached result, so we can just
            // return right away.
            super.deliverResult(mResult);
        }
        
        // If our response is null or we have hung onto it for a long time,
        // then we perform a force load.
        if (mResult == null || System.currentTimeMillis() - mLastLoad >= STALE_DELTA) forceLoad();
        mLastLoad = System.currentTimeMillis();
    }
    
    @Override
    protected void onStopLoading() {
        // This prevents the AsyncTask backing this
        // loader from completing if it is currently running.
        cancelLoad();
    }
    
    @Override
    protected void onReset() {
        super.onReset();
        
        // Stop the Loader if it is currently running.
        onStopLoading();
        
        // Get rid of our cache if it exists.
        mResult = null;
        
        // Reset our stale timer.
        mLastLoad = 0;
    }

    private static void attachUriWithQuery(HttpRequestBase request, Uri uri, Bundle params) {
    	try {
    		Uri.Builder uriBuilder = uri.buildUpon();
    		
    		uriBuilder.appendQueryParameter("key", "AIzaSyAowY3puwfLTCI6pjh9oDScEiFIhS1wpLU");
    		uriBuilder.appendQueryParameter("sensor", "true");
    		uriBuilder.appendQueryParameter("orderby", "distance");
    		uriBuilder.appendQueryParameter("types", "establishment");

    		// Loop through our params and append them to the Uri.
    		for (BasicNameValuePair param : paramsToList(params)) {
    			uriBuilder.appendQueryParameter(param.getName(), param.getValue());
    		}

    		uri = uriBuilder.build();
    		request.setURI(new URI(uri.toString()));
    	} catch (URISyntaxException e) {
    		Log.e(TAG, "URI syntax was incorrect: "+ uri.toString());
    	}
    }
    
    
    
    private static List<BasicNameValuePair> paramsToList(Bundle params) {
        ArrayList<BasicNameValuePair> formList = new ArrayList<BasicNameValuePair>(params.size());
        
        for (String key : params.keySet()) {
            Object value = params.get(key);
            
            // We can only put Strings in a form entity, so we call the toString()
            // method to enforce. We also probably don't need to check for null here
            // but we do anyway because Bundle.get() can return null.
            if (value != null) formList.add(new BasicNameValuePair(key, value.toString()));
        }
        
        return formList;
    }
}


