package com.pekall.mobiletv.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pekall.mobiletv.R;

public class TabBar extends LinearLayout implements OnClickListener {
	private static final int TAB_BOTTOM_RES = R.color.tab_bar_seperator;
	private static final int TAB_BOTTOM_HEIGHT = 0;
	
	private static final int TAB_TOP_RES = 0;
	private static final int TAB_BUTTON_RES = R.drawable.tab_button;
	private static final int TEXT_SIZE = 14;
	private static final int TEXT_COLOR = Color.WHITE;
	private static final int TAB_HEIGHT = 40;
	private static final int TAB_WIDTH = 100;
	private static final int TAB_SPACING = 0;
	private static final int TAB_TOP_PADDING_LR = 0;
	
	
	
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
	
	private int mTabWidth = TAB_WIDTH;
	private int mTabHeight = TAB_HEIGHT;
	private int mTabSpacing = TAB_SPACING;
	private int mTabTopPaddingLR = TAB_TOP_PADDING_LR;
	private int mTabBottomHeight = TAB_BOTTOM_HEIGHT;
	
	private int mTabDrawable = TAB_BUTTON_RES;
	private int mTabTopDrawable = TAB_TOP_RES;
	private int mTabBottomDrawable = TAB_BOTTOM_RES;
	
	private int mTextColor = TEXT_COLOR;
	private int mTextSize = TEXT_SIZE;
	
	
	
	private MyHorizontalScrollView.SizeChangedListener mSizeChangedListener = new MyHorizontalScrollView.SizeChangedListener() {
		
		@Override
		public void onSizeChanged(int w, int h, int oldw, int oldh) {
			mOffset = (w - mTabWidth) / 2;
		}
	}; 
	
	private MyHorizontalScrollView.FinalPostionListener mFinalPostionListener = new MyHorizontalScrollView.FinalPostionListener() {
		
		@Override
		public void onFinalPosition(int finalX, int finalY) {
			int x = mScroll.getScrollX();
			
			int cellWidth = 2 * mTabSpacing + mTabWidth;
			
			int count = x / cellWidth;
			int remainder = x % cellWidth;
			
			if (remainder >= cellWidth / 2) {
				count++;
			}
			
			int newPosX = count * cellWidth;
			
			Log.d(TAG, "final X -- " + x);
			Log.d(TAG, "fling new X -- " + newPosX);
			
			
			
			mScroll.smoothScrollBy(newPosX - x, 0);
		}
	};
	
	private OnTabSelectedListener mOnTabSelectedListener;
	

	public TabBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		
		setupLayoutParams(context, attrs);
	}

	private void setupLayoutParams(Context context, AttributeSet attrs) {
		TypedArray params = context.obtainStyledAttributes(attrs,
				R.styleable.TabBar);
		
		mTextColor = params.getColor(R.styleable.TabBar_textColor, TEXT_COLOR);
		mTextSize = (int) params.getDimension(R.styleable.TabBar_textSize, TEXT_SIZE);
		
		mTabWidth = (int) params.getDimension(R.styleable.TabBar_tabWidth, TAB_WIDTH);
		mTabHeight = (int) params.getDimension(R.styleable.TabBar_tabHeight, TAB_HEIGHT);
		mTabSpacing = (int) params.getDimension(R.styleable.TabBar_tabSpacing, TAB_SPACING);
		
		mTabDrawable = params.getResourceId(R.styleable.TabBar_tabDrawable, TAB_BUTTON_RES);
		mTabTopDrawable = params.getResourceId(R.styleable.TabBar_tabTopDrawable, TAB_TOP_RES);
		mTabBottomDrawable = params.getResourceId(R.styleable.TabBar_tabBottomDrawable, TAB_BOTTOM_RES);
		mTabTopPaddingLR = (int) params.getDimension(R.styleable.TabBar_tabTopPaddingLR, TAB_TOP_PADDING_LR);
		mTabBottomHeight = (int) params.getDimension(R.styleable.TabBar_tabBottomHeight, TAB_BOTTOM_HEIGHT);
		
		params.recycle();
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
		textView.setBackgroundResource(TAB_BUTTON_RES);
		
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
				Log.d(TAG, "click new pos -- " + newPosX);
				
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
		mTabTop.setPadding(mTabTopPaddingLR, 0, mTabTopPaddingLR, 0);
		addView(mTabTop, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		
		mTabBottom = new View(mContext);
		mTabBottom.setBackgroundResource(mTabBottomDrawable);
		addView(mTabBottom, LinearLayout.LayoutParams.FILL_PARENT, mTabBottomHeight);
		
//		setBackgroundResource(R.drawable.tab_btn_unselect);
//		mLeft = new ImageView(mContext);
//		mLeft.setImageResource(R.drawable.arrow_left_btn);
//		mTabTop.addView(mLeft, 10, LinearLayout.LayoutParams.WRAP_CONTENT);
				
		mScroll = new MyHorizontalScrollView(mContext);
		mScroll.setSmoothScrollingEnabled(true);
		mScroll.setHorizontalScrollBarEnabled(false);
		mScroll.setSizeChangedListener(mSizeChangedListener);
		mScroll.setFinalPositionListener(mFinalPostionListener);
		
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
			textView.setTextSize(mTextSize);
			textView.setTextColor(mTextColor);
			textView.setBackgroundResource(mTabDrawable);
			LinearLayout.LayoutParams tabLayoutParams = new LinearLayout.LayoutParams(mTabWidth, mTabHeight);
			tabLayoutParams.setMargins(mTabSpacing, 0, mTabSpacing, 0);
			mTabHolder.addView(textView, tabLayoutParams);
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
		private SizeChangedListener mSizeChangedListener;
		private FinalPostionListener mFinalPositionListener;
		
		private  Handler mPositionHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (mFinalPositionListener != null) {
					mFinalPositionListener.onFinalPosition(msg.arg1, msg.arg2);
				}
			}
			
		};
		
		private int mMode;

	    private int mStartX;
	    private int mStartY;
	    private int mFinalX;
	    private int mFinalY;

	    private int mMinX;
	    private int mMaxX;
	    private int mMinY;
	    private int mMaxY;

	    private int mCurrX;
	    private int mCurrY;
	    private long mStartTime;
	    private int mDuration;
	    private float mDurationReciprocal;
	    private float mDeltaX;
	    private float mDeltaY;
	    private float mViscousFluidScale;
	    private float mViscousFluidNormalize;
	    private boolean mFinished;
	    private Interpolator mInterpolator;

	    private float mCoeffX = 0.0f;
	    private float mCoeffY = 1.0f;
	    private float mVelocity;

	    private static final int DEFAULT_DURATION = 250;
	    private static final int SCROLL_MODE = 0;
	    private static final int FLING_MODE = 1;

	    private float mDeceleration;

		public MyHorizontalScrollView(Context context, AttributeSet attrs,
				int defStyle) {
			super(context, attrs, defStyle);
			setupDeceleration(context);
			
		}

		public MyHorizontalScrollView(Context context, AttributeSet attrs) {
			super(context, attrs);
			setupDeceleration(context);
		}

		public MyHorizontalScrollView(Context context) {
			super(context);
			
			setupDeceleration(context);
		}
		
		private void setupDeceleration(Context context) {
			float ppi = context.getResources().getDisplayMetrics().density * 160.0f;
			mDeceleration = SensorManager.GRAVITY_EARTH   // g (m/s^2)
	        	* 39.37f                        // inch/meter
	        	* ppi                           // pixels per inch
	        	* ViewConfiguration.getScrollFriction();
		}
		
		@Override
		public void fling(int velocityX) {
			int velocityY = 0;
			int startX = getScrollX();
			int startY = getScrollY();
			int minX = 0;
			int maxX = Math.max(0, getChildAt(0).getWidth() - getWidth() - getPaddingRight() - getPaddingLeft());
			int minY = 0;
			int maxY = 0;
			
			float velocity = (float)Math.hypot(velocityX, velocityY);
		     
	        mVelocity = velocity;
	        mDuration = (int) (1000 * velocity / mDeceleration); // Duration is in
	                                                            // milliseconds
	        mStartTime = AnimationUtils.currentAnimationTimeMillis();
	        mStartX = startX;
	        mStartY = startY;

	        mCoeffX = velocity == 0 ? 1.0f : velocityX / velocity; 
	        mCoeffY = velocity == 0 ? 1.0f : velocityY / velocity;

	        int totalDistance = (int) ((velocity * velocity) / (2 * mDeceleration));
	        
	        mMinX = minX;
	        mMaxX = maxX;
	        mMinY = minY;
	        mMaxY = maxY;
	        
	        
	        mFinalX = startX + Math.round(totalDistance * mCoeffX);
	        // Pin to mMinX <= mFinalX <= mMaxX
	        mFinalX = Math.min(mFinalX, mMaxX);
	        mFinalX = Math.max(mFinalX, mMinX);
	        
	        mFinalY = startY + Math.round(totalDistance * mCoeffY);
	        // Pin to mMinY <= mFinalY <= mMaxY
	        mFinalY = Math.min(mFinalY, mMaxY);
	        mFinalY = Math.max(mFinalY, mMinY);
			
	        Message message = Message.obtain(mPositionHandler);
	        message.arg1 = mFinalX;
	        message.arg2 = mFinalY;
	        
	        mPositionHandler.sendMessageDelayed(message, mDuration);
			
			super.fling(velocityX);
		}
		
		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			
			mSizeChangedListener.onSizeChanged(w, h, oldw, oldh);
		}
		
		public void setSizeChangedListener(SizeChangedListener l) {
			mSizeChangedListener = l;
		}
		
		public void setFinalPositionListener(FinalPostionListener l) {
			mFinalPositionListener = l;
		}
		
		private static interface SizeChangedListener {
			public void onSizeChanged(int w, int h, int oldw, int oldh);
		}
		
		private static interface FinalPostionListener {
			public void onFinalPosition(int finalX, int finalY);
		}
	}
}
