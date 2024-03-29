package com.myawesomeplaces;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.myawesomeplaces.R;

public class AddActivity extends SherlockFragmentActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.add, menu);
        return true;
    }
    
   
    protected void showSearchResults(PlaceSearchResult searchResults) {
    	
    	FragmentManager fm = this.getSupportFragmentManager();
    	SearchResultsFrag srf = (SearchResultsFrag) fm.findFragmentById(R.id.searchresultsfrag);
    	srf.setListAdapter(searchResults);
    	
    	return;	
    }
}
