package com.example.radiogrouptab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends Activity {
	
	private RadioGroup mRadioGroup;
	private TextView mContent;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mRadioGroup = (RadioGroup) findViewById(R.id.content_tabs);
        mContent = (TextView) findViewById(R.id.content);
        
        mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.all_channels:
					mContent.setText("All Channels");
					break;
				case R.id.favorite_channels:
					mContent.setText("Favorite Channels");
					break;
				case R.id.program_reminders:
					mContent.setText("Program Reminders");
					break;
				default:
					break;
				}
			}
		});
        
        mRadioGroup.check(R.id.program_reminders);
    }
}