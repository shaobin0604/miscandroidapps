package com.pekall.mobiletv;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PlayerActivity extends Activity {
	
	private static final String TAG = PlayerActivity.class.getSimpleName();
	
	private Gallery mServiceGallery;
	
	private int mClickedPos;

	private static final String[] SERVICE_NAMES = {
		"中央一台",
		"中央二台",
		"中央三台",
		"中央四台",
		"中央五台",
	};
	
	private OnItemClickListener mServiceItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			ServiceItemAdapter serviceItemadapter = (ServiceItemAdapter)parent.getAdapter();
			serviceItemadapter.setSelectedPosition(position);
		}
		
	};
	
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.player_activity);
		
		mServiceGallery = (Gallery) findViewById(R.id.service_gallery);
		
		ServiceItemAdapter serviceAdapter = new ServiceItemAdapter(this, Arrays.asList(SERVICE_NAMES));
		mServiceGallery.setAdapter(serviceAdapter);
		mServiceGallery.setOnItemClickListener(mServiceItemClickListener);
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
			if (convertView == null ) {
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
