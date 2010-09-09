package com.pekall.mobiletv.adapter;

import java.util.Collections;
import java.util.List;

import com.pekall.mobiletv.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MockEmergencyBroadcastItemAdapter extends BaseAdapter {
	
	private Context mContext;
	private LayoutInflater mInflater;
	private List<String> mItems;
	
	public MockEmergencyBroadcastItemAdapter(Context context, List<String> items) {
		super();
		mContext = context;
		mInflater = LayoutInflater.from(context);
		
		if (items == null)
			items = Collections.emptyList();
		mItems = items;
	}
	
	public MockEmergencyBroadcastItemAdapter(Context context) {
		this(context, null);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		EmergencyBroadcastItemViewHolder holder = null;
		
		if (convertView == null) {
			row = mInflater.inflate(R.layout.content_item, null);
			holder = new EmergencyBroadcastItemViewHolder(row);
			row.setTag(holder);
		} else {
			holder = (EmergencyBroadcastItemViewHolder)convertView.getTag();
		}
		
		holder.getTitle().setText(mItems.get(position));
		
		return row;
	}

}
