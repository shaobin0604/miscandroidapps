package org.devtcg.tools.httpdownloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity 
{
	public static final String TAG = "Main";

	private SharedPreferences mPrefs;

	private EditText mSrcView;
	private EditText mDstView;
	private Button mGetBtn;

	private DownloadProcessor mThread;
	private ProgressDialog mProgress;

	private static final String PREF_LAST_SRC_URL = "lastSrcURL";
	private static final String PREF_LAST_DST_PATH = "lastDstPath";

	private final DownloadHandler mHandler = new DownloadHandler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			DownloadProcessor.Download dl = mThread.getDownload();

			switch (msg.what)
			{
			case MSG_FINISHED:
				Log.d(TAG, "MSG_FINISHED");
				
				Editor prefs = mPrefs.edit();
				prefs.putString(PREF_LAST_SRC_URL, dl.url.toString());
				prefs.putString(PREF_LAST_DST_PATH, dl.dst.toString());
				prefs.commit();

				mProgress.dismiss();
				mProgress = null;
				mThread = null;
				break;
			case MSG_SET_LENGTH:
				Log.d(TAG, "MSG_LENGTH: " + (Long)msg.obj);
				mProgress.setIndeterminate(false);
				mProgress.setProgress(0);
				dl.length = (Long)msg.obj;
				break;
			case MSG_ON_RECV:
				if (dl.length >= 0)
				{
					float prog =
					  ((float)((Long)msg.obj) / (float)dl.length) * 100f;

					mProgress.setProgress((int)(prog * 100f));
					mProgress.setMessage("Received " + (int)prog + "%");
				}
				else
				{
					mProgress.setMessage("Received " + (Long)msg.obj + " bytes");
				}
				break;
			case MSG_ERROR:
				Log.d(TAG, "MSG_ERROR: " + msg.obj);
				mProgress.dismiss();
				mProgress = null;
				mThread = null;
				Toast.makeText(Main.this, "Error: " + msg.obj,
				  Toast.LENGTH_LONG).show();
				break;
			default:
				super.handleMessage(msg);
			}
		}
	};

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        
        mPrefs = getSharedPreferences("prefs", 0);
        
        mSrcView = (EditText)findViewById(R.id.src);
        mDstView = (EditText)findViewById(R.id.dst);
      
        mSrcView.setText(mPrefs.getString(PREF_LAST_SRC_URL,
          getResources().getString(R.string.default_src_url)));
        mDstView.setText(mPrefs.getString(PREF_LAST_DST_PATH,
          getResources().getString(R.string.default_dst_path)));
        
        mGetBtn = (Button)findViewById(R.id.get);
        mGetBtn.setOnClickListener(mGetClick);
    }
    
    @Override
    public void onDestroy()
    {
		if (mThread != null)
		{
			mThread.stopDownloadThenJoin();
			mHandler.removeMyMessages();
			mThread = null;
		}

    	super.onDestroy();
    }
    
    private final OnClickListener mGetClick = new OnClickListener()
    {
    	public void onClick(View v)
    	{
    		String src = mSrcView.getText().toString();
    		String dst = mDstView.getText().toString();
    		
    		URL srcUrl;

    		try
    		{
    			srcUrl = new URL(src);
    		}
    		catch (MalformedURLException e)
    		{
    			Toast.makeText(Main.this,
    			  "Invalid source URL: " + e.toString(),
    			  Toast.LENGTH_SHORT).show();

    			return;
    		}

    		mProgress = ProgressDialog.show(Main.this, "Downloading...",
    	    		  "Connecting to server...", true, true, mGetCancelClick);

    		DownloadProcessor.Download dl = 
    		  new DownloadProcessor.Download(srcUrl, new File(dst));
    		
    		mThread = new DownloadProcessor(dl, mHandler);
    		mThread.start();
    	}
    };
    
    private final OnCancelListener mGetCancelClick = new OnCancelListener()
    {
		public void onCancel(DialogInterface di)
		{
			mProgress.dismiss();
			mProgress = null;

			mThread.stopDownloadThenJoin();
			mHandler.removeMyMessages();
			mThread = null;
		}
    };
}
