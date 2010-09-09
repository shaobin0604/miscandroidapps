package com.pekall.mobiletv.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pekall.mobiletv.R;

public class PurchaseItemViewHolder {
	private View root;
	private TextView title;
	private TextView notice;
	private TextView detail;
	private TextView subscribeStateText;
	private ImageView subscribeStateImage;
	
	public PurchaseItemViewHolder(View root) {
		this.root = root;
	}

	public TextView getTitle() {
		if (title == null)
			title = (TextView) root.findViewById(R.id.purchase_item_title);
		return title;
	}

	public TextView getNotice() {
		if (notice == null)
			notice = (TextView) root.findViewById(R.id.notice);
		return notice;
	}


	public TextView getDetail() {
		if (detail == null)
			detail = (TextView) root.findViewById(R.id.purchase_item_detail);
		return detail;
	}

	public TextView getSubscribeStateText() {
		if (subscribeStateText == null)
			subscribeStateText = (TextView) root.findViewById(R.id.subscribe_state_text);
		return subscribeStateText;
	}

	public ImageView getSubscribeStateImage() {
		if (subscribeStateImage == null) 
			subscribeStateImage = (ImageView) root.findViewById(R.id.subscribe_state_indicator);
		return subscribeStateImage;
	}
	
	
}
