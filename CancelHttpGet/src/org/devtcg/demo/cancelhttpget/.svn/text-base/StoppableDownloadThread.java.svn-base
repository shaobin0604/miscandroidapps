package org.devtcg.demo.cancelhttpget;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.PlainSocketFactory;
import org.apache.http.conn.Scheme;
import org.apache.http.conn.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class StoppableDownloadThread extends Thread 
{
	public static final String TAG = "StoppableDownloadThread";

	private String mURL;

	private HttpGet mMethod = null;
	private DownloadHandler mHandler;

	/**
	 * Volatile stop flag used to coordinate state between the two threads
	 * involved in this example.
	 */
	protected volatile boolean mStopped = false;
	
	/**
	 * Synchronizes access to mMethod to prevent an unlikely race condition
	 * when stopDownload() is called before mMethod has been committed.
	 */
	private Object lock = new Object();

	public StoppableDownloadThread(String url, DownloadHandler handler)
	{
		mURL = url;
		mHandler = handler;
	}

	private HttpClient getClient()
	{
		/* Set the connection timeout to 10s for our test. */
		HttpParams params = new BasicHttpParams();
		params.setParameter(ClientPNames.CONNECTION_MANAGER_TIMEOUT, 10000L);

		/* Avoid registering the https scheme, and thus initializing
		 * SSLSocketFactory on Android. */
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http",
		  PlainSocketFactory.getSocketFactory(), 80));

		SingleClientConnManager cm =
		  new SingleClientConnManager(params, schemeRegistry);

		return new DefaultHttpClient(cm, params);
	}

	public void run()
	{
		Log.d(TAG, "Connecting...");

		HttpClient cli = getClient();
		HttpGet method;
		
		try {
			method = new HttpGet(mURL);
		} catch (URISyntaxException e) {
			mHandler.sendError(e.toString());
			return;
		}

		/* It's important that we pause here to check if we've been stopped
		 * already.  Otherwise, we would happily progress, seemingly ignoring
		 * the stop request. */
		if (mStopped == true)
			return;

		synchronized(lock) {
			mMethod = method;
		}

		HttpResponse resp = null;
		HttpEntity ent = null;
		InputStream in = null;
		
		try {
			/* CAVEAT: HttpClient 3.x encapsulates the platform's connect()
			 * call in such a way that prevents interruption.  This has been
			 * corrected in HttpClient 4 alpha 4, however Android distributes
			 * an older version which does not work as expected.  My sources at
			 * Google have clarified that the final 1.0 release will include
			 * the newest version so this should hopefully be a non-issue for
			 * production.
			 *
			 * For a discussion of this problem see the following thread
			 * concerning HttpClient 3.x:
			 *
			 * http://mail-archives.apache.org/mod_mbox/hc-httpclient-users/200506.mbox/%3c20050615164118.GA4936@uml24.umlhosting.ch%3e
			 */
			resp = cli.execute(mMethod);

			if (mStopped == true)
				return;

			StatusLine status = resp.getStatusLine();

			if (status.getStatusCode() != HttpStatus.SC_OK)
				Log.d(TAG, "GET not OK: " + status);

			if ((ent = resp.getEntity()) != null)
			{
				long len;
				if ((len = ent.getContentLength()) >= 0)
					mHandler.sendSetLength(len);

				in = ent.getContent();

				byte[] b = new byte[2048];
				int n;
				long bytes = 0;
				
				/* Note that for most applications, sending a handler message
				 * after each read() would be unnecessary.  Instead, a timed
				 * approach should be utilized to send a message at most every
				 * x seconds. */
				while ((n = in.read(b)) >= 0)
				{
					bytes += n;
					mHandler.sendOnRecv(bytes);	
				}
			}
		} catch (Exception e) {
			/* We expect a SocketException on cancellation.  Any other type of
			 * exception that occurs during cancellation is ignored regardless
			 * as there would be no need to handle it. */
			if (mStopped == false)
				mHandler.sendError(e.toString());
		} finally {
			if (in != null)
				try { in.close(); } catch (IOException e) {}

			synchronized(lock) {
				mMethod = null;
			}

			/* Close the socket (if it's still open) and cleanup. */
			cli.getConnectionManager().shutdown();

			if (mStopped == false)
				mHandler.sendFinished();

			Log.d(TAG, "Exiting!");
		}
	}

	/**
	 * This method is to be called from a separate thread.  That is, not the
	 * one executing run().  When it exits, the download thread should be on
	 * its way out (failing a connect or read call and cleaning up).
	 */
	public void stopDownload()
	{
		/* As we've written this method, calling it from multiple threads would
		 * be problematic. */
		if (mStopped == true)
			return;

		/* Too late! */
		if (isAlive() == false)
			return;

		Log.d(TAG, "Stopping download...");

		/* Flag to instruct the downloading thread to halt at the next
		 * opportunity. */
		mStopped = true;

		/* Interrupt the blocking thread.  This won't break out of a blocking
		 * I/O request, but will break out of a wait or sleep call.  While in
		 * this case we know that no such condition is possible, it is always a
		 * good idea to include an interrupt to avoid assumptions about the
		 * thread in question. */
		interrupt();

		/* A synchronized lock is necessary to avoid catching mMethod in
		 * an uncommitted state from the download thread. */
		synchronized(lock) {
			/* This closes the socket handling our blocking I/O, which will
			 * interrupt the request immediately.  This is not the same as
			 * closing the InputStream yieled by HttpEntity#getContent, as the
			 * stream is synchronized in such a way that would starve our main
			 * thread. */
			if (mMethod != null)
				mMethod.abort();
		}

		mHandler.sendAborted();

		Log.d(TAG, "Download stopped.");
	}
}
