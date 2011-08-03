package com.example.vibratortest;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
    
	private Button mPattern1;
	private Button mPattern2;
	private Button mPattern3;
	private Button mPattern4;
	private Button mPattern5;
	private Button mPattern6;
	
	private Vibrator mVibrator;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        
        mPattern1 = (Button) findViewById(R.id.pattern1);
        mPattern2 = (Button) findViewById(R.id.pattern2);
        mPattern3 = (Button) findViewById(R.id.pattern3);
        mPattern4 = (Button) findViewById(R.id.pattern4);
        mPattern5 = (Button) findViewById(R.id.pattern5);
        mPattern6 = (Button) findViewById(R.id.pattern6);
        
        mPattern1.setOnClickListener(this);
        mPattern2.setOnClickListener(this);
        mPattern3.setOnClickListener(this);
        mPattern4.setOnClickListener(this);
        mPattern5.setOnClickListener(this);
        mPattern6.setOnClickListener(this);
    }

	public void onClick(View v) {
		long[] pattern = DEFAULT_VIBE_PATTERN;
		if (v == mPattern1) {
			pattern = loadVibratePattern(R.array.config_keyboardTapVibePattern);
		} else if (v == mPattern2) {
			pattern = loadVibratePattern(R.array.config_longPressVibePattern);
		} else if (v == mPattern3) {
			pattern = loadVibratePattern(R.array.config_safeModeDisabledVibePattern);
		} else if (v == mPattern4) {
			pattern = loadVibratePattern(R.array.config_safeModeEnabledVibePattern);
		} else if (v == mPattern5) {
			pattern = loadVibratePattern(R.array.config_scrollBarrierVibePattern);
		} else if (v == mPattern6) {
			pattern = loadVibratePattern(R.array.config_virtualKeyVibePattern);
		}
		
		if (pattern.length == 1) {
			mVibrator.vibrate(pattern[0]);
		} else {
			mVibrator.vibrate(pattern, -1);
		}
	}
	
	// Vibrator pattern for creating a tactile bump
    private static final long[] DEFAULT_VIBE_PATTERN = {0, 1, 40, 41};
    
	private long[] loadVibratePattern(int id) {
        int[] pattern = null;
        try {
            pattern = getResources().getIntArray(id);
        } catch (Resources.NotFoundException e) {
            Log.e("LockPatternView", "Vibrate pattern missing, using default", e);
        }
        if (pattern == null) {
            return DEFAULT_VIBE_PATTERN;
        }

        long[] tmpPattern = new long[pattern.length];
        for (int i = 0; i < pattern.length; i++) {
            tmpPattern[i] = pattern[i];
        }
        return tmpPattern;
    }
}