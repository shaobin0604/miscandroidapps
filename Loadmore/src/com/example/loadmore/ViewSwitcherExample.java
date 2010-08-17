package com.example.loadmore;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ViewSwitcher;

public class ViewSwitcherExample extends ListActivity
				 implements OnClickListener {
    
	//sample list items
	private List<String> mItems;
	
	//the ViewSwitcher
	private ViewSwitcher mSwitcher;

	private ArrayAdapter<String> mArrayAdapter;
	
	private int mTimes;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  //no window title
	  requestWindowFeature(Window.FEATURE_NO_TITLE);
	  
	  //create the ViewSwitcher in the current context
	  mSwitcher = new ViewSwitcher(this);
	  
	  //footer Button: see XML1
	  Button footer = (Button)View.inflate(this, R.layout.btn_loadmore, null);
	  
	  //progress View: see XML2
	  View progress = View.inflate(this, R.layout.loading_footer, null);
	  
	  //add the views (first added will show first)
	  mSwitcher.addView(footer);
	  mSwitcher.addView(progress);
	  
	  //add the ViewSwitcher to the footer
	  getListView().addFooterView(mSwitcher);
	  
	  mItems = new ArrayList<String>();
	  mItems.add("List Item 0");
	  
	  mArrayAdapter = new ArrayAdapter<String>(this,
	          android.R.layout.simple_list_item_1, mItems);
	  setListAdapter(mArrayAdapter);
	}

	@Override /* Load More Button Was Clicked */
	public void onClick(View arg0) {
		
		//and start background work
		new getMoreItems().execute(++mTimes);
	}
	
	/** Background Task To Get More Items**/
	private class getMoreItems extends AsyncTask<Integer, Void, String> {
		
		@Override
		protected void onPreExecute() {
			//first view is showing, show the second progress view
			mSwitcher.showNext();
		}

		@Override
		protected String doInBackground(Integer... params) {
			//code to add more items
			//...
			try {
				Thread.sleep(3000); 
				//only to demonstrate
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "List Item " + params[0];
		}

		@Override /* Background Task is Done */
		protected void onPostExecute(String result) {
			//go back to the first view
			mSwitcher.showPrevious();
            //update the ListView
			mArrayAdapter.add(result);
		}
	}
}