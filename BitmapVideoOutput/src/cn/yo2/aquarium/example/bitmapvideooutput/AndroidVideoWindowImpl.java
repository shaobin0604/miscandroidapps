package cn.yo2.aquarium.example.bitmapvideooutput;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Surface.OutOfResourcesException;
import android.view.SurfaceHolder.Callback;

public class AndroidVideoWindowImpl {
	private Bitmap mBitmap;
	private SurfaceView mView;
	private Surface mSurface;
	private VideoWindowListener mListener;

	public static interface VideoWindowListener {
		void onSurfaceReady(AndroidVideoWindowImpl v);

		void onSurfaceDestroyed(AndroidVideoWindowImpl v);
	}
	
	public void setListener(VideoWindowListener l) {
		mListener = l;
	}

	public AndroidVideoWindowImpl(SurfaceView view) {
		mView = view;
		mBitmap = null;
		mSurface = null;
		mListener = null;

		view.getHolder().addCallback(new Callback() {
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				Log.i("Surface is being changed.");

				synchronized (AndroidVideoWindowImpl.this) {
					mBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
					mSurface = holder.getSurface();
				}

				if (mListener != null)
					mListener.onSurfaceReady(AndroidVideoWindowImpl.this);
				Log.w("Video display surface changed");
			}

			public void surfaceCreated(SurfaceHolder holder) {
				Log.w("Video display surface created");
			}

			public void surfaceDestroyed(SurfaceHolder holder) {

				synchronized (AndroidVideoWindowImpl.this) {
					mSurface = null;
					mBitmap = null;
				}

				if (mListener != null)
					mListener.onSurfaceDestroyed(AndroidVideoWindowImpl.this);
				Log.d("Video display surface destroyed");
			}
		});
	}

	static final int LANDSCAPE = 0;
	static final int PORTRAIT = 1;

	public void requestOrientation(int orientation) {
		// Surface.setOrientation(0, orientation==LANDSCAPE ? 1 : 0);
		// Log.d("Orientation changed.");
	}

	public Bitmap getBitmap() {
		Log.d("getBitmap enter...");
		return mBitmap;
	}

	// Called by the native code to update SurfaceView
	public synchronized void update() {
		Log.d("update enter...");
		if (mSurface != null) {
			try {
				Canvas canvas = mSurface.lockCanvas(null);
				canvas.drawBitmap(mBitmap, 0, 0, null);
				mSurface.unlockCanvasAndPost(canvas);

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OutOfResourcesException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public native void setViewWindowId(Object oid);
	
	public native void startPlayThread(String path);
}
