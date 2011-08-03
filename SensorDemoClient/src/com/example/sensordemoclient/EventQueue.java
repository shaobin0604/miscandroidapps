package com.example.sensordemoclient;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventQueue {
	
	private static final int SIZE = 10;

	public static class Event {
		public static final String KEY_TIME = "time";
		public static final String KEY_TYPE = "type";
		public static final String KEY_VALUES = "values";
		
		private long time;
		private int type;
		private float[] values;
		
		public Event(long time, int type, float[] values) {
			super();
			this.time = time;
			this.type = type;
			this.values = values;
		}
		
		public String toJSON() throws JSONException {
			JSONObject json = new JSONObject();
			json.put(KEY_TIME, time);
			json.put(KEY_TYPE, type);
			
			JSONArray jsonArray = new JSONArray();
			
			for (double value : values) {
				jsonArray.put(value);
			}
			
			json.put(KEY_VALUES, jsonArray);
			
			return json.toString();
		}
	}

	private Queue<Event> mBuffer;
	private int mSize = SIZE;
	
	public EventQueue() {
		super();
		mBuffer = new LinkedList<Event>();
	}

	public synchronized void clear() {
		mBuffer.clear();
	}
	
	public synchronized boolean put(Event event) {
		boolean ret = false;
		
		if (mBuffer.size() < mSize) {
			ret = mBuffer.offer(event);
		} 
		
		notifyAll();
		
		return ret;
	}
	
	public synchronized Event take() {
		while (mBuffer.size() <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Event e = mBuffer.poll();
		notifyAll();
		return e;
	}
}
