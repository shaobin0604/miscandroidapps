package com.example.sensordemoclient;

import android.util.Log;

public class LogUtils {
	private static final String TAG = LogUtils.class.getSimpleName();
	
	public static StringBuilder getTraceInfo() {
		StringBuilder sb = new StringBuilder();   
        
        StackTraceElement[] stacks = new Throwable().getStackTrace();  
        int stacksLen = stacks.length;
        StackTraceElement elem = stacks[2];
        sb.append("[" )
        .append(elem.getFileName())
        .append("#")
        .append(elem.getMethodName())
        .append("] ")
        .append(elem.getLineNumber())
        .append(", --> ");
        return sb;
	}
	
	
	public static void d(String log) {
		Log.d(TAG, getTraceInfo().append(log).toString());
	}
	
	public static void e(String log) {
		Log.e(TAG, getTraceInfo().append(log).toString());
	}
	
	public static void e(String log, Throwable e) {
		Log.e(TAG, getTraceInfo().append(log).toString(), e);
	}
}
