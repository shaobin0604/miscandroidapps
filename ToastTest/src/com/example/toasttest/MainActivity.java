package com.example.toasttest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = "ToastTest";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
    }
    
    public void showToast1(View view) {
    	Toast toast = Toast.makeText(this, "Length short", Toast.LENGTH_SHORT);
    	
    	toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
    	
        Log.d(TAG, "gravity = " + toast.getGravity());
        Log.d(TAG, "offsetX = " + toast.getXOffset());
        Log.d(TAG, "offsetY = " + toast.getYOffset());
        toast.show();
    }
    
    public void showToast2(View view) {
    	Toast tst = Toast.makeText(this, "text", Toast.LENGTH_LONG);
    	ImageView image = new ImageView(this);
    	image.setImageResource(R.drawable.icon);
    	tst.setView(image);
    	tst.show();

    }
}