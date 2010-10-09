package com.example.testjni;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText mInput;
	private Button mGreet;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mInput = (EditText) findViewById(R.id.input);
        mGreet = (Button) findViewById(R.id.greet);
        
        mGreet.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) {

				String result = NativeGreet.greet();
				
				Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
			}
		});
    }
}

class NativeGreet {
	static {
		System.loadLibrary("native_greet");
	}
	
	native static String greet();
}