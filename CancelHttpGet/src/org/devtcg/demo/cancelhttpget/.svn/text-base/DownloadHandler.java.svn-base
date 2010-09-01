package org.devtcg.demo.cancelhttpget;

import android.os.Handler;

/**
 * Simple handler including constants and helpers to facilitate communicating
 * state about a file download.
 */
public abstract class DownloadHandler extends Handler
{
	public static final int MSG_SET_LENGTH = 0;
	public static final int MSG_ON_RECV = 1;
	public static final int MSG_FINISHED = 2;
	public static final int MSG_ERROR = 3;
	public static final int MSG_ABORTED = 4;
	
	public void sendSetLength(long length)
	{
		sendMessage(obtainMessage(MSG_SET_LENGTH, (Long)length));	
	}

	public void sendOnRecv(long recvd)
	{
		sendMessage(obtainMessage(MSG_ON_RECV, (Long)recvd));
	}

	public void sendFinished()
	{
		sendMessage(obtainMessage(MSG_FINISHED));
	}

	public void sendError(String errmsg)
	{
		sendMessage(obtainMessage(MSG_ERROR, errmsg));
	}

	public void sendAborted()
	{
		sendMessage(obtainMessage(MSG_ABORTED));
	}
}
