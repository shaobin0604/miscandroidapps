package com.pekall.mobiletv;

import java.util.Arrays;

import com.pekall.mobiletv.adapter.MockEmergencyBroadcastItemAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class EmergencyBroadcastListActivity extends Activity {
	private static final String TAG = EmergencyBroadcastListActivity.class.getSimpleName();
	
	private static final String[] TITLES = {
		"紧急广播1",
		"紧急广播2",
	};
	
	private TextView mTitle;
	private ListView mListView;
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.emergency_broadcast_list_activity);
		
		mTitle = (TextView) findViewById(R.id.title);
		mListView = (ListView) findViewById(R.id.emergency_broadcast_list);
		
		MockEmergencyBroadcastItemAdapter adapter = new MockEmergencyBroadcastItemAdapter(this, Arrays.asList(TITLES));
		
		mListView.setAdapter(adapter);
	}
}
