package com.pekall.mobiletv;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class SearchContentActivity extends Activity {
	
	private static final String TAG = SearchContentActivity.class.getSimpleName();
	
	private TextView mTitleTextView;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		
		setContentView(R.layout.search_content_activity);
		
		mTitleTextView = (TextView) findViewById(R.id.title);
		
		mTitleTextView.setText("搜索节目");
	}
}
