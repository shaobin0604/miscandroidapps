/**
 * 
 */
package com.pekall.mobiletv.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pekall.mobiletv.R;

public class ContentItemViewHolder {
	private View root;
	private TextView contentName;
	private TextView contentTime;
	private TextView contentDate;
	private TextView serviceName;
	private ImageView remindIndicator;
	
	public ContentItemViewHolder(View root) {
		super();
		this.root = root;
	}
	
	public TextView getServiceName() {
		if (serviceName == null)
			serviceName = (TextView)root.findViewById(R.id.service_name);
		return serviceName;
	}

	public TextView getContentName() {
		if (contentName == null)
			contentName = (TextView) root.findViewById(R.id.content_name);
		return contentName;
	}

	public TextView getContentTime() {
		if (contentTime == null)
			contentTime = (TextView) root.findViewById(R.id.content_time);
		return contentTime;
	}

	public TextView getContentDate() {
		if (contentDate == null)
			contentDate = (TextView) root.findViewById(R.id.content_date);
		return contentDate;
	}

	public ImageView getStateIndicator() {
		if (remindIndicator == null)
			remindIndicator = (ImageView) root.findViewById(R.id.remind_indicator);
		return remindIndicator;
	}
}