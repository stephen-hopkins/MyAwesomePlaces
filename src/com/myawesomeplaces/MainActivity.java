package com.myawesomeplaces;


import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuInflater;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.myawesomeplaces.R;

import android.content.Intent;
import android.os.Bundle;



public class MainActivity extends SherlockFragmentActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addPlaceButton:
            	startAddActivity();
                return true;
            case R.id.settingsButton:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void startAddActivity() {
    	Intent intent = new Intent(this, AddActivity.class);
    	startActivity(intent);
    }
    
    
}
