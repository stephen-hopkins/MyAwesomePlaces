package com.example.myawesomeplaces;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class AddFrag extends SherlockFragment {

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
	        Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
	        return inflater.inflate(R.layout.frag_add, container, false);
	    }
	    
	    public void buttonSearchClicked() {
	    	
	    }
	    


}
