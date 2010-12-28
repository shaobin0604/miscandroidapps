package com.example.notificationtest;

import android.app.Application;
import android.content.res.Configuration;

public class App extends Application {
	
	private boolean mIsActivityShown;
	
	public boolean isActivityShown() {
		return mIsActivityShown;
	}
	
	public void setActivityShown(boolean show) {
		mIsActivityShown = show;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}
	
}
