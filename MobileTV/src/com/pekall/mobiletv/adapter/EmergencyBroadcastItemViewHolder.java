package com.pekall.mobiletv.adapter;

import com.pekall.mobiletv.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EmergencyBroadcastItemViewHolder {
	private View root;
	
	private TextView title;
	private TextView date;
	private ImageView indicator;
	
	public EmergencyBroadcastItemViewHolder(View root) {
		this.root = root;
	}
	
	public TextView getTitle() {
		if (title == null)
			title = (TextView) root.findViewById(R.id.title); 
		return title;
	}
	public TextView getDate() {
		if (date == null)
			date = (TextView) root.findViewById(R.id.date);
		return date;
	}
	public ImageView getIndicator() {
		if (indicator == null) 
			indicator = (ImageView) root.findViewById(R.id.indicator);
		return indicator;
	}
	
	
}
