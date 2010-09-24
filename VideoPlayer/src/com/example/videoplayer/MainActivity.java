package com.example.videoplayer;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements
		OnBufferingUpdateListener, OnCompletionListener,
		MediaPlayer.OnPreparedListener, OnVideoSizeChangedListener,
		SurfaceHolder.Callback {
	private static final LinearLayout.LayoutParams FULL_SCREEN_LAYOUT_PARAMS = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.FILL_PARENT);

	private static final String TAG = MainActivity.class.getSimpleName();

	private int mVideoWidth;
	private int mVideoHeight;
	private MediaPlayer mMediaPlayer;
	private SurfaceView mPreview;
	private SurfaceHolder holder;
	private String path;
	private Bundle extras;
	private static final String MEDIA = "media";
	private static final int LOCAL_AUDIO = 1;
	private static final int STREAM_AUDIO = 2;
	private static final int RESOURCES_AUDIO = 3;
	private static final int LOCAL_VIDEO = 4;
	private static final int STREAM_VIDEO = 5;
	private boolean mIsVideoSizeKnown = false;
	private boolean mIsVideoReadyToBePlayed = false;

	private View mPlaybackLayout;
	private ImageButton mBtnZoom;
	private Gallery mServiceGallery;
	
	private View mTitleBar;

	private View mHoriCtrl;
	private View mPortCtrl;

	private int mScreenWidth;
	private int mScreenHeight;

	private boolean mFull;
	
	private boolean mLandscape;

	private static final String[] SERVICE_NAMES = { "中央一台", "中央二台", "中央三台",
			"中央四台", "中央五台", };

	protected static final int WHAT_DISMISS_HORI_CTRL = 1000;

	private OnItemClickListener mServiceItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			ServiceItemAdapter serviceItemadapter = (ServiceItemAdapter) parent
					.getAdapter();
			serviceItemadapter.setSelectedPosition(position);
		}

	};
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_DISMISS_HORI_CTRL:
				mHoriCtrl.setVisibility(View.GONE);
				break;

			default:
				break;
			}
		}
		
	};

	private View.OnClickListener mClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == mBtnZoom) {
				if (mFull) {
					mFull = false;
					adjustPlaybackSize();
				} else {
					mFull = true;
					fullScreen();
				}
			} else if (v == mPlaybackLayout) {
				if (mLandscape) {
					mHandler.removeMessages(WHAT_DISMISS_HORI_CTRL);
					mHoriCtrl.setVisibility(View.VISIBLE);
					mHandler.sendEmptyMessageDelayed(WHAT_DISMISS_HORI_CTRL, 2000);
				}
			}
		}
	};


	/**
	 * 
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_player);

		mPreview = (SurfaceView) findViewById(R.id.surface_view);
		mPlaybackLayout = findViewById(R.id.playback_layout);
		mPlaybackLayout.setOnClickListener(mClickListener);
		
		mBtnZoom = (ImageButton) findViewById(R.id.zoom);
		mBtnZoom.setOnClickListener(mClickListener);

		mPortCtrl = findViewById(R.id.port_layout);
		mHoriCtrl = findViewById(R.id.hor_layout);

		mServiceGallery = (Gallery) findViewById(R.id.service_gallery);
		mTitleBar = findViewById(R.id.title_bar);

		ServiceItemAdapter serviceAdapter = new ServiceItemAdapter(this, Arrays
				.asList(SERVICE_NAMES));
		mServiceGallery.setAdapter(serviceAdapter);
		mServiceGallery.setOnItemClickListener(mServiceItemClickListener);

		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		extras = getIntent().getExtras();
		
		Resources resources = getResources();
		Configuration configuration = resources.getConfiguration();
		switchOrientation(configuration);
	}
	
	
	
	

	private void getScreenSize() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;
		mScreenHeight = dm.heightPixels;
	}

	private void playVideo(Integer Media) {
		doCleanUp();
		try {

			switch (Media) {
			case LOCAL_VIDEO:
				/*
				 * TODO: Set the path variable to a local media file path.
				 */
				path = "/sdcard/bmwad.3gp";
				if (path == "") {
					// Tell the user to provide a media file URL.
					Toast
							.makeText(
									MainActivity.this,
									"Please edit MediaPlayerDemo_Video Activity, "
											+ "and set the path variable to your media file path."
											+ " Your media file must be stored on sdcard.",
									Toast.LENGTH_LONG).show();

				}
				break;
			case STREAM_VIDEO:
				/*
				 * TODO: Set path variable to progressive streamable mp4 or 3gpp
				 * format URL. Http protocol should be used. Mediaplayer can
				 * only play "progressive streamable contents" which basically
				 * means: 1. the movie atom has to precede all the media data
				 * atoms. 2. The clip has to be reasonably interleaved.
				 */
				path = "";
				if (path == "") {
					// Tell the user to provide a media file URL.
					Toast
							.makeText(
									MainActivity.this,
									"Please edit MediaPlayerDemo_Video Activity,"
											+ " and set the path variable to your media file URL.",
									Toast.LENGTH_LONG).show();

				}

				break;

			}

			// Create a new media player and set the listeners
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.setDisplay(holder);
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setOnVideoSizeChangedListener(this);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.prepareAsync();

		} catch (Exception e) {
			Log.e(TAG, "error: " + e.getMessage(), e);
		}
	}

	public void onBufferingUpdate(MediaPlayer arg0, int percent) {
		Log.d(TAG, "onBufferingUpdate percent:" + percent);

	}

	public void onCompletion(MediaPlayer arg0) {
		Log.d(TAG, "onCompletion called");
	}

	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		Log.v(TAG, "onVideoSizeChanged called");
		if (width == 0 || height == 0) {
			Log.e(TAG, "invalid video width(" + width + ") or height(" + height
					+ ")");
			return;
		}
		mIsVideoSizeKnown = true;
		mVideoWidth = width;
		mVideoHeight = height;

		adjustPlaybackSize();

		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	private void adjustPlaybackSize() {
		getScreenSize();

		int height = mScreenHeight;
		int width = mScreenWidth;
		if (mVideoWidth * mScreenHeight > mScreenWidth * mVideoHeight) {
			// Log.i("@@@", "image too tall, correcting");
			height = mScreenWidth * mVideoHeight / mVideoWidth;
		} else if (mVideoWidth * mScreenHeight < mScreenWidth * mVideoHeight) {
			// Log.i("@@@", "image too wide, correcting");
			width = height * mVideoWidth / mVideoHeight;
		} else {
			// Log.i("@@@", "aspect ratio is correct: " +
			// width+"/"+height+"="+
			// mVideoWidth+"/"+mVideoHeight);
		}

		mPlaybackLayout.setLayoutParams(new LinearLayout.LayoutParams(width,
				height));
		mPlaybackLayout.requestLayout();
	}

	private void fullScreen() {
		mPlaybackLayout.setLayoutParams(FULL_SCREEN_LAYOUT_PARAMS);
		mPlaybackLayout.requestLayout();
	}

	public void onPrepared(MediaPlayer mediaplayer) {
		Log.d(TAG, "onPrepared called");
		mIsVideoReadyToBePlayed = true;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
		Log.d(TAG, "surfaceChanged called");

	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		Log.d(TAG, "surfaceDestroyed called");
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated called");
		playVideo(LOCAL_VIDEO);

	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseMediaPlayer();
		doCleanUp();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseMediaPlayer();
		doCleanUp();
	}

	private void releaseMediaPlayer() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	private void doCleanUp() {
		mVideoWidth = 0;
		mVideoHeight = 0;
		mIsVideoReadyToBePlayed = false;
		mIsVideoSizeKnown = false;
	}

	private void startVideoPlayback() {
		Log.v(TAG, "startVideoPlayback");
		holder.setFixedSize(mVideoWidth, mVideoHeight);
		mMediaPlayer.start();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		switchOrientation(newConfig);
	}

	private void switchOrientation(Configuration newConfig) {
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			mLandscape = true;
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			landscapeLayout();
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			mLandscape = false;
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			portraitLayout();
		}
	}

	private void portraitLayout() {
		mServiceGallery.setVisibility(View.VISIBLE);
		mTitleBar.setVisibility(View.VISIBLE);
		mHoriCtrl.setVisibility(View.GONE);
		mPortCtrl.setVisibility(View.VISIBLE);
		adjustPlaybackSize();
	}

	private void landscapeLayout() {
		mTitleBar.setVisibility(View.GONE);
		mServiceGallery.setVisibility(View.GONE);
		mHoriCtrl.setVisibility(View.INVISIBLE);
		mPortCtrl.setVisibility(View.GONE);
		adjustPlaybackSize();
	}

	private static class ServiceItemAdapter extends BaseAdapter {
		private Context mContext;
		private List<String> mItems;

		private int mSelectedPosition;

		public void setSelectedPosition(int position) {
			mSelectedPosition = position;
			notifyDataSetChanged();
		}

		public ServiceItemAdapter(Context context, List<String> items) {
			mContext = context;
			mItems = items;
		}

		public int getCount() {
			return mItems.size();
		}

		public Object getItem(int position) {
			return mItems.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			TextView text = null;
			if (convertView == null) {
				text = new TextView(mContext);
			} else {
				text = (TextView) convertView;
			}

			text.setText(mItems.get(position));
			text.setTextColor(Color.WHITE);

			text.setLayoutParams(new Gallery.LayoutParams(102, 37));
			text.setGravity(Gravity.CENTER);

			if (position == mSelectedPosition)
				text.setBackgroundResource(R.drawable.tab_button_select);
			else
				text.setBackgroundResource(R.drawable.tab_button_unselect);
			return text;
		}
	}
}