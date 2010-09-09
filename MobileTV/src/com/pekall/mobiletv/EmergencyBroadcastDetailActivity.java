package com.pekall.mobiletv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EmergencyBroadcastDetailActivity extends Activity {
	private static final String TAG = EmergencyBroadcastDetailActivity.class.getSimpleName();
	
	private TextView mTitle;
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.emergency_broadcast_detail_activity);
		
		mTitle = (TextView) findViewById(R.id.title);
		mTitle.setText("紧急广播");
	}
}
