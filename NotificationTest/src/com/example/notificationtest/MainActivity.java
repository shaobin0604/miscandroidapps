package com.example.notificationtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView name = (TextView) findViewById(R.id.who_am_i);
		
		name.setText("Main");
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	
    	App app = (App) getApplication();
    	
    	app.setActivityShown(false);
    }
}