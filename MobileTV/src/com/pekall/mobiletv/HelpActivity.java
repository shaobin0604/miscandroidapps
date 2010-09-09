package com.pekall.mobiletv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends Activity {
	
	private TextView mTitle;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.help_activity);
		
		mTitle = (TextView) findViewById(R.id.title);
		
		mTitle.setText("帮助");
	}
}
