package com.example.sensordemoclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.sensordemoclient.EventQueue.Event;

public class SensorService extends Service implements SensorEventListener,
		Runnable {

	private static final String TAG = SensorService.class.getSimpleName();

	public static final int CMD_START = 1;
	public static final int CMD_STOP = 0;

	private SensorManager mSensorManager;

	private EventQueue mEventQueue;

	private boolean mIsRunning;

	private Thread mWorkerThread;
	
	private SharedPreferences mSharedPreferences;
	
	private String getServerIpAddress() {
		return mSharedPreferences.getString(getString(R.string.prefs_key_ip), "");
	}
	
	private int getServerPort() {
		return Integer.valueOf(mSharedPreferences.getString(getString(R.string.prefs_key_port), "0"));
	}
	
	private int getSensorDelay() {
		String sensorDelay = mSharedPreferences.getString(getString(R.string.prefs_key_sampling_rate), "SENSOR_DELAY_GAME");
		
		if ("SENSOR_DELAY_FASTEST".equals(sensorDelay)) {
			return SensorManager.SENSOR_DELAY_FASTEST;
		} else if ("SENSOR_DELAY_GAME".equals(sensorDelay)) {
			return SensorManager.SENSOR_DELAY_GAME;
		} else if ("SENSOR_DELAY_NORMAL".equals(sensorDelay)) {
			return SensorManager.SENSOR_DELAY_NORMAL;
		} else if ("SENSOR_DELAY_UI".equals(sensorDelay)) {
			return SensorManager.SENSOR_DELAY_UI;
		} else {
			return SensorManager.SENSOR_DELAY_GAME;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		mEventQueue = new EventQueue();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		mEventQueue.clear();

		Bundle extras = intent.getExtras();

		int cmd = extras.getInt(getString(R.string.extras_key_cmd));

		switch (cmd) {
		case CMD_START:
			mIsRunning = true;
			mWorkerThread = new Thread(this);
			mWorkerThread.start();

			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
					getSensorDelay());
			mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
					getSensorDelay());
			break;
		case CMD_STOP:
			mIsRunning = false;
			if (mWorkerThread != null && mWorkerThread.isAlive()) {
				mWorkerThread.interrupt();
			}
			stopSelf();
			break;
		default:
			break;
		}
		return START_STICKY;
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public void onSensorChanged(SensorEvent event) {
		Sensor sensor = event.sensor;
		int type = sensor.getType();
		long time = System.currentTimeMillis();
		mEventQueue.put(new Event(time, type, event.values));
	}

	public void run() {
		LogUtils.d("EventQueue running...");

		try {
			Socket socket = new Socket(getServerIpAddress(), getServerPort());

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
			while (mIsRunning) {
				Event event = mEventQueue.take();

				String json = event.toJSON();

				LogUtils.d(json);

				out.println(json);
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LogUtils.d("EventQueue exit...");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mSensorManager.unregisterListener(this);
	}
}
