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
    
    @Override
    protected void onStart() {
    	super.onStart();
    	// hide search results fragment
    	FragmentManager fm = this.getSupportFragmentManager();
    	FragmentTransaction fragtrans = fm.beginTransaction();
    	
    	SearchResultsFrag srf = (SearchResultsFrag) fm.findFragmentById(R.id.searchresultsfrag);
    	fragtrans.hide(srf);
    	fragtrans.commit();
    }
    
    protected void showSearchResults(PlaceSearchResult searchResults) {
    	
    	FragmentManager fm = this.getSupportFragmentManager();
    	SearchResultsFrag srf = (SearchResultsFrag) fm.findFragmentById(R.id.searchresultsfrag);
    	srf.setListAdapter(searchResults);
    	
    	// hide and show appropriate fragments
    	FragmentTransaction fragtrans = fm.beginTransaction();
    	AddFrag af = (AddFrag) fm.findFragmentById(R.id.addfrag);
    	fragtrans.hide(af);
    	fragtrans.show(srf);
    	fragtrans.commit();
    	return;	
    }
}
