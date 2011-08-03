package com.example.sensordemoclient;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;

public class MainActivity extends PreferenceActivity implements OnPreferenceChangeListener {
	
	private CheckBoxPreference mStartListen;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        
        mStartListen = (CheckBoxPreference) findPreference(getString(R.string.prefs_key_listen));
        
        mStartListen.setOnPreferenceChangeListener(this);
    }

	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (preference == mStartListen) {
			Intent intent = new Intent(this, SensorService.class);
			
			boolean listen = (Boolean) newValue;
			
			if (listen) {
				intent.putExtra(getString(R.string.extras_key_cmd), SensorService.CMD_START);
			} else {
				intent.putExtra(getString(R.string.extras_key_cmd), SensorService.CMD_STOP);
			}
			
			startService(intent);
			return true;
		}
		
		return false;
	}
    
    
}