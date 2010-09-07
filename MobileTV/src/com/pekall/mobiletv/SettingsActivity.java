package com.pekall.mobiletv;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;

public class SettingsActivity extends PreferenceActivity {
	private static final String TAG = SettingsActivity.class.getSimpleName();
	
	private static final int MENU_RESTORE_TO_DEFAULT = Menu.FIRST;
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.settings_activity);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_RESTORE_TO_DEFAULT, Menu.NONE, "Restore");
		return super.onCreateOptionsMenu(menu);
	}
}
