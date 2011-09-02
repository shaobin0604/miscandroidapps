package cn.yo2.aquarium.example.bitmapvideooutput;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.yo2.aquarium.example.bitmapvideooutput.AndroidVideoWindowImpl.VideoWindowListener;

public class Main extends Activity implements VideoWindowListener, OnClickListener {
	
	private AndroidVideoWindowImpl mViewWindow;
	
	private SurfaceView mSurfaceView;
	
	private Button mBtnStart;
	
	static {
		System.loadLibrary("ffmpeg");
		System.loadLibrary("android_display");
	}
		
	public void onSurfaceReady(AndroidVideoWindowImpl v) {
		v.setViewWindowId(v);
	}

	public void onSurfaceDestroyed(AndroidVideoWindowImpl v) {
		v.setViewWindowId(null);
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mSurfaceView = (SurfaceView) findViewById(R.id.video_view);
        
        mViewWindow = new AndroidVideoWindowImpl(mSurfaceView);
        
        mBtnStart = (Button) findViewById(R.id.start_play);
        
        mBtnStart.setOnClickListener(this);
        
        mViewWindow.setListener(this);
    }

	public void onClick(View v) {
		File file = new File(Environment.getExternalStorageDirectory(), "video-2011-08-26-19-14-49.3gp"/*sample_100kbit.mp4*/);
		mViewWindow.startPlayThread(file.getAbsolutePath());
	}
    
    
}
