package com.example.notificationtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ReminderNotificationStarter extends Activity {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		App app = (App) getApplication();
		
		if (!app.isActivityShown()) {
			Intent intent = new Intent(this, SplashActivity.class);
			
			startActivity(intent);
		}
		
		finish();
	}
}
