package org.devtcg.demo.cancelhttpget;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity
{
	private EditText mURLText;
	private TextView mStatus;
	private ProgressBar mProgress;
	private Button mGo;

	private StoppableDownloadThread mThread = null;

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        
        mURLText = (EditText)findViewById(R.id.url);
        mURLText.setText(getResources().getString(R.string.url));

        mStatus = (TextView)findViewById(R.id.status);
        mProgress = (ProgressBar)findViewById(R.id.progress);
        mGo = (Button)findViewById(R.id.startstop);
        
        mGo.setOnClickListener(mGoClick);
    }

    private final OnClickListener mGoClick = new OnClickListener()
    {
    	public void onClick(View v)
    	{
    		if (mThread == null)
    		{
    			mStatus.setText("Connecting...");
    			mProgress.setIndeterminate(true);
    			mGo.setText("Stop");

    			mThread = new StoppableDownloadThread(mURLText.getText().toString(),
    			  mHandler);    			
    			mThread.start();
    		}
    		else
    		{
    			mStatus.setText("Cancelled!");
    			mProgress.setIndeterminate(false);
    			mProgress.setProgress(0);
    			mGo.setText("Start");

    			mThread.stopDownload();
    			
				/* We can safely wait for the thread to terminate if we want.
				 * This would only be necessary if you must wait for resources
				 * to release from your download thread before your program can
				 * progress.  We are using it here just to demonstrate that the
				 * thread does in fact terminate abruptly after our call to
				 * stopDownload().  If it did not, the UI would hang. */
    			while (true)
    			{
    				try {
    					mThread.join();
    					mThread = null;
    					break;
    				} catch (InterruptedException e) {}
    			}
    		}
    	}
    };
    
	private final DownloadHandler mHandler = new DownloadHandler()
	{
		private long length = -1;
		
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case MSG_SET_LENGTH:
				mStatus.setText("Receiving...");
				mProgress.setIndeterminate(false);
				length = (Long)msg.obj;
				break;

			case MSG_ON_RECV:
				long recvd = (Long)msg.obj;
				
				if (length < 0)
					mStatus.setText("Received " + recvd + " bytes");
				else
				{
					float pct = ((float)recvd / (float)length);
					mProgress.setProgress((int)(pct * 100f));
				}
				
				break;

			case MSG_ERROR:
				String errmsg = (String)msg.obj;

				mStatus.setText("Error!");
				mProgress.setProgress(0);
				mProgress.setIndeterminate(false);
				
				/* We could join it, but we don't care. */
				mThread = null;

				Toast.makeText(Main.this, "Error: " + errmsg,
				  Toast.LENGTH_LONG).show();
				break;

			case MSG_FINISHED:
				mStatus.setText("Finished!");
				mProgress.setProgress(0);
				mProgress.setIndeterminate(false);
				break;

			case MSG_ABORTED:
				/* We ignore this, since we synchronize immediately after
				 * stopThread(). */
				break;
			}
		}
	};
}
