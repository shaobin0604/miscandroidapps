package com.example.gallerytab;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabBar extends LinearLayout implements OnClickListener {
	private List<TextView> mBtnList = new ArrayList<TextView>();

	public TabBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TabBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void addTab(TextView textView) {
		addView(textView, 102, 40);
		mBtnList.add(textView);
		
		textView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		for (TextView btn : mBtnList) {
			if (v == btn) 
				btn.setEnabled(false);
			else
				btn.setEnabled(true);
		}
		
		
	}
}
