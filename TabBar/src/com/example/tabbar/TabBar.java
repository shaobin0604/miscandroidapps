package com.example.tabbar;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabBar extends LinearLayout implements OnClickListener {
	private static final int TAB_HEIGHT = 40;
	private static final int TAB_WIDTH = 100;
	private static final String TAG = TabBar.class.getSimpleName();
	
	private int mOffset;
	private boolean mPacked;
	
	private MyHorizontalScrollView mScroll;
	private LinearLayout mTabHolder;
	
	private LinearLayout mTabTop;
	private View mTabBottom;
	
	private ImageView mLeft;
	private ImageView mRight;
	
	private List<TextView> mTabList = new ArrayList<TextView>();
	private int mCurrentTabIndex;
	
	private Context mContext;
	
	private int mTabWidth;
	private int mTabHeight;
	
	private int mTabDrawable;
	private int mTabTopDrawable;
	private int mTabBottomDrawable;
	private int mTextColor;
	private int mTextSize;
	
	private MyHorizontalScrollView.OnSizeChangedListener mOnSizeChangedListener = new MyHorizontalScrollView.OnSizeChangedListener() {
		
		@Override
		public void onSizeChanged(int w, int h, int oldw, int oldh) {
			mOffset = (w - mTabWidth) / 2;
		}
	}; 
	
	private OnTabSelectedListener mOnTabSelectedListener;

	public TabBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public TabBar(Context context) {
		super(context);
		mContext = context;
	}

	public void addTab(TabSpec tabSpec) {
		if (mPacked)
			throw new IllegalStateException();
		
		TextView textView = new TextView(mContext);
		
		textView.setText(tabSpec.mText);
		textView.setCompoundDrawablesWithIntrinsicBounds(tabSpec.mIcon, null, null, null);
		
		textView.setGravity(Gravity.CENTER);
		textView.setBackgroundResource(R.drawable.tab_btn);
		
		mTabList.add(textView);
	}
	
	public void setOnTabSelectedListener(OnTabSelectedListener l) {
		if (!mPacked)
			throw new IllegalStateException();
		
		mOnTabSelectedListener = l;
	}
	
	public int getCurrentTab() {
		if (!mPacked)
			throw new IllegalStateException();
		
		return mCurrentTabIndex;
	}
	
	public void setCurrentTab(int index) {
		selectTab(mTabList.get(index));
	}

	@Override
	public void onClick(View v) {
		selectTab(v);
	}

	private void selectTab(View v) {
		if (!mPacked)
			throw new IllegalStateException();
		
		final int size = mTabList.size();
		
		for (int i = 0; i < size; i++) {
			TextView btn = mTabList.get(i);
			if (v == btn) {
				int posX = mScroll.getScrollX();
				int posY = mScroll.getScrollY();
				
				int clickLeft = getLeftOfView(btn);
			    
			    Log.d(TAG, "clickLeft " + clickLeft);
			    Log.d(TAG, "scrollX " + posX);
				
				int newPosX = clickLeft - mOffset;
				if (newPosX < 0)
					newPosX = 0;
				
				mScroll.smoothScrollTo(newPosX, posY);
				
				btn.setEnabled(false);
				
				mCurrentTabIndex = i;
				
				if (mOnTabSelectedListener != null)
					mOnTabSelectedListener.onTabSelected(i);
			} else {
				btn.setEnabled(true);
			}
		}
	}
	
	/**
     * @return The left of the given view.
     */
    private static int getLeftOfView(View view) {
        return view.getLeft();
    }
	
	public void pack() {
		if (mPacked)
			throw new IllegalStateException();
		
		setOrientation(LinearLayout.VERTICAL);
		
		mTabTop = new LinearLayout(mContext);
		addView(mTabTop, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		
		mTabBottom = new View(mContext);
		mTabBottom.setBackgroundResource(R.color.tab_bar_seperator);
		addView(mTabBottom, LinearLayout.LayoutParams.FILL_PARENT, 2);
		
//		setBackgroundResource(R.drawable.tab_btn_unselect);
//		mLeft = new ImageView(mContext);
//		mLeft.setImageResource(R.drawable.arrow_left_btn);
//		mTabTop.addView(mLeft, 10, LinearLayout.LayoutParams.WRAP_CONTENT);
				
		mScroll = new MyHorizontalScrollView(mContext);
		mScroll.setSmoothScrollingEnabled(true);
		mScroll.setHorizontalScrollBarEnabled(false);
		mScroll.setOnSizeChangedListener(mOnSizeChangedListener);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.weight = 1.0F;
		mTabTop.addView(mScroll, params);
		
//		mRight = new ImageView(mContext);
//		mRight.setImageResource(R.drawable.arrow_right_btn);
//		mTabTop.addView(mRight, 10, LinearLayout.LayoutParams.WRAP_CONTENT);
		
		
		mTabHolder = new LinearLayout(mContext);
		mScroll.addView(mTabHolder, FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		
		final int size = mTabList.size();
		for (int i = 0; i < size; i++) {
			TextView textView = mTabList.get(i);
			
			mTabHolder.addView(textView, TAB_WIDTH, TAB_HEIGHT);
			textView.setOnClickListener(this);
		}
		
		mPacked = true;
	}
	
	
	public static class TabSpec {
		private final Drawable mIcon;
		private final CharSequence mText;
		
		public TabSpec(CharSequence text, Drawable icon) {
			mText = text;
			mIcon = icon;
		}
	}
	
	public static interface OnTabSelectedListener {
		public void onTabSelected(int index);
	}
	
	private static class MyHorizontalScrollView extends HorizontalScrollView {
		private OnSizeChangedListener mListener;

		public MyHorizontalScrollView(Context context, AttributeSet attrs,
				int defStyle) {
			super(context, attrs, defStyle);
			// TODO Auto-generated constructor stub
		}

		public MyHorizontalScrollView(Context context, AttributeSet attrs) {
			super(context, attrs);
			// TODO Auto-generated constructor stub
		}

		public MyHorizontalScrollView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			
			mListener.onSizeChanged(w, h, oldw, oldh);
		}
		
		public void setOnSizeChangedListener(OnSizeChangedListener l) {
			mListener = l;
		}
		
		private static interface OnSizeChangedListener {
			public void onSizeChanged(int w, int h, int oldw, int oldh);
		}
	}
}
