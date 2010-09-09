package com.pekall.mobiletv;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.Menu;

public class SettingsActivity extends PreferenceActivity {
	private static final String TAG = SettingsActivity.class.getSimpleName();
	
	private static final int MENU_RESTORE_TO_DEFAULT = Menu.FIRST;
	
	private EditTextPreference mSgAddress;
	private EditTextPreference mWapGateWayAddress;
	private ListPreference mRemindTime;
	private Preference mApn;
	
	private SharedPreferences mSharedPreferences;
	
	private OnSharedPreferenceChangeListener mOnSharedPreferenceChangeListener = new OnSharedPreferenceChangeListener() {
		
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
				String key) {
			if (getString(R.string.prefs_key_remind_time).equals(key)) {
				
			} else if (getString(R.string.prefs_key_apn).equals(key)) {
				
			} else if (getString(R.string.prefs_key_sg).equals(key)) {
				updateSgDisplay(getSgAddress());
			} else if (getString(R.string.prefs_key_wap_gateway).equals(key)) {
				updateWapGatewayDisplay(getWapGatewayAddress());
			}
		}
	};
	
	private OnPreferenceClickListener mOnPreferenceClickListener = new OnPreferenceClickListener() {
		
		@Override
		public boolean onPreferenceClick(Preference preference) {
			if (preference == mSgAddress) {
				mSgAddress.getEditText().setText(getSgAddress());
			} else if (preference == mWapGateWayAddress) {
				mWapGateWayAddress.getEditText().setText(getWapGatewayAddress());
			}
			return false;
		}
	};
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.settings_activity);
		
		mSharedPreferences = getPreferenceScreen().getSharedPreferences();
		
		mRemindTime = (ListPreference) findPreference(getString(R.string.prefs_key_remind_time));
		mApn = findPreference(getString(R.string.prefs_key_apn));
		
		mWapGateWayAddress = (EditTextPreference) findPreference(getString(R.string.prefs_key_wap_gateway));
		mSgAddress = (EditTextPreference) findPreference(getString(R.string.prefs_key_sg));
		
		mWapGateWayAddress.setOnPreferenceClickListener(mOnPreferenceClickListener);
		mSgAddress.setOnPreferenceClickListener(mOnPreferenceClickListener);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_RESTORE_TO_DEFAULT, Menu.NONE, "Restore");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		updateRemindDisplay(mSharedPreferences.getString(getString(R.string.prefs_key_remind_time), getString(R.string.prefs_default_remind_time)));
		updateSgDisplay(getSgAddress());
		updateWapGatewayDisplay(getWapGatewayAddress());
		
		mSharedPreferences.registerOnSharedPreferenceChangeListener(mOnSharedPreferenceChangeListener);
	}

	private String getWapGatewayAddress() {
		return mSharedPreferences.getString(getString(R.string.prefs_key_wap_gateway), getString(R.string.prefs_default_wap_gateway));
	}

	private String getSgAddress() {
		return mSharedPreferences.getString(getString(R.string.prefs_key_sg), getString(R.string.prefs_default_sg));
	}
	
	private void updateRemindDisplay(String text) {
		mRemindTime.setSummary(mRemindTime.getEntries()[mRemindTime.findIndexOfValue(text)]);
	}

	private void updateSgDisplay(String text) {
		mSgAddress.setSummary(text);
	}

	private void updateWapGatewayDisplay(String text) {
		mWapGateWayAddress.setSummary(text);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		mSharedPreferences.unregisterOnSharedPreferenceChangeListener(mOnSharedPreferenceChangeListener);
	}
}
