package com.myawesomeplaces;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockListFragment;

public class SearchResultsFrag extends SherlockListFragment {

	View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_searchresults, container, false);
             
        return rootView;
        
    }
    

}
