// iPhone OS Style App
package com.sampleapp;

import java.util.ArrayList;

import com.sampleapp.MainActivity.iTab.OnTabClickListener;
import com.sampleapp.MainActivity.iTab.TabMember;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity 
{
	public static class iTab extends ImageView 
	{
        private Paint					mPaint;
        private Paint					mActiveTextPaint;
        private Paint					mInactiveTextPaint;
        private ArrayList<TabMember>	mTabMembers;
        private int						mActiveTab;
        private OnTabClickListener		mOnTabClickListener = null;
        
		public iTab( Context context, AttributeSet attrs ) 
		{
			super(context, attrs);
			
			mTabMembers = new ArrayList<MainActivity.iTab.TabMember>( );
			
			mPaint = new Paint( );
			mActiveTextPaint = new Paint( );
			mInactiveTextPaint = new Paint( );
			
			mPaint.setStyle( Paint.Style.FILL );
			mPaint.setColor( 0xFFFFFF00 );
			
			mActiveTextPaint.setTextAlign( Align.CENTER );
			mActiveTextPaint.setTextSize( 12 );
			mActiveTextPaint.setColor( 0xFFFFFFFF );
			mActiveTextPaint.setFakeBoldText( true );
			
			mInactiveTextPaint.setTextAlign( Align.CENTER );
			mInactiveTextPaint.setTextSize( 12 );
			mInactiveTextPaint.setColor( 0xFF999999 );
			mInactiveTextPaint.setFakeBoldText( true );
			
			mActiveTab = 0;
			
		}
		
        @Override
        protected void onDraw( Canvas canvas )
        {
        	Log.d( TAG, "iTab onDraw start" );
        	super.onDraw( canvas );
        	
        	Rect r = new Rect( );
        	this.getDrawingRect( r );
        	
        	// Calculate how many pixels each tab can use
        	int singleTabWidth = r.right / ( mTabMembers.size( ) != 0 ? mTabMembers.size( ) : 1 );
        	
        	Log.d( TAG, "iTab: SingleTabWidth: " + singleTabWidth );
        	
        	// Draw the background
        	canvas.drawColor( 0xFF000000 );
        	mPaint.setColor( 0xFF434343 );
        	canvas.drawLine( r.left, r.top + 1, r.right, r.top + 1, mPaint );
        	
        	int color = 46;
        	
        	for( int i = 0; i < 24; i++ )
        	{
        		mPaint.setARGB( 255, color, color, color );
        		canvas.drawRect( r.left, r.top + i + 1, r.right, r.top + i + 2, mPaint );
        		color--;
        	}

        	// Draw each tab
        	for( int i = 0; i < mTabMembers.size( ); i++ )
        	{
        		TabMember tabMember = mTabMembers.get( i );
        		
        		Bitmap icon = BitmapFactory.decodeResource( getResources( ), tabMember.getIconResourceId( ) );
    			Bitmap iconColored = Bitmap.createBitmap( icon.getWidth(), icon.getHeight(), Bitmap.Config.ARGB_8888 );
    			Paint p = new Paint( Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    			Canvas iconCanvas = new Canvas( );
    			iconCanvas.setBitmap( iconColored );
 
    			if( mActiveTab == i )
    			{
    				p.setShader( new LinearGradient( 0, 0, icon.getWidth(), icon.getHeight(), 0xFFFFFFFF, 0xFF54C7E1, Shader.TileMode.CLAMP ) );
    			}
    			else {    
    				p.setShader( new LinearGradient( 0, 0, icon.getWidth(), icon.getHeight(), 0xFFA2A2A2, 0xFF5F5F5F, Shader.TileMode.CLAMP ) );
    			}
    			
    			iconCanvas.drawRect( 0, 0, icon.getWidth( ), icon.getHeight( ), p );
    			
    			for( int x = 0; x < icon.getWidth(); x++ )
    			{
    				for( int y = 0; y < icon.getHeight(); y++ )
    				{
    					if( ( icon.getPixel(x, y) & 0xFF000000 ) == 0 )
    					{
    						iconColored.setPixel( x, y, 0x00000000 );
    					}
    				}
    			}
    			
        		// Calculate Tab Image Position
        		int tabImgX = singleTabWidth * i + ( singleTabWidth / 2 - icon.getWidth( ) / 2 );
        		
        		// Active Tab will be drawn in a different way
        		if( mActiveTab == i )
        		{		
        			mPaint.setARGB( 37, 255, 255, 255 );
        			canvas.drawRoundRect(  new RectF( r.left + singleTabWidth * i + 3, r.top + 3, r.left + singleTabWidth * ( i + 1 ) - 3, r.bottom - 2 ), 5, 5, mPaint );
        			canvas.drawBitmap( iconColored, tabImgX , r.top + 5, null );
        			canvas.drawText( tabMember.getText( ), singleTabWidth * i + ( singleTabWidth / 2), r.bottom - 2, mActiveTextPaint );
        		} else
        		{
        			canvas.drawBitmap( iconColored, tabImgX , r.top + 5, null );
        			canvas.drawText( tabMember.getText( ), singleTabWidth * i + ( singleTabWidth / 2), r.bottom - 2, mInactiveTextPaint );
        		}
        	}

        	Log.d( TAG, "iTab onDraw finished" );
        }
        
        @Override
        public boolean onTouchEvent( MotionEvent motionEvent )
        {
        	Rect r = new Rect( );
        	this.getDrawingRect( r );        	
        	float singleTabWidth = r.right / ( mTabMembers.size( ) != 0 ? mTabMembers.size( ) : 1 );
        	
        	int pressedTab = (int) ( ( motionEvent.getX( ) / singleTabWidth ) - ( motionEvent.getX( ) / singleTabWidth ) % 1 );
        	
        	mActiveTab = pressedTab;
        	
        	if( this.mOnTabClickListener != null)
        	{
        		this.mOnTabClickListener.onTabClick( mTabMembers.get( pressedTab ).getId( ) );        	
        	}
        	
        	this.invalidate();
        	
        	return super.onTouchEvent( motionEvent );
        }
        
        void addTabMember( TabMember tabMember )
        {
        	mTabMembers.add( tabMember );
        }
        
        void setOnTabClickListener( OnTabClickListener onTabClickListener )
        {
        	mOnTabClickListener = onTabClickListener;
        }
        
        public static class TabMember
        {
        	protected int		mId;
        	protected String	mText;
        	protected int 		mIconResourceId;
        	
        	TabMember( int Id, String Text, int iconResourceId )
        	{
        		mId = Id;
        		mIconResourceId = iconResourceId;
        		mText = Text;
        	}
        	
        	public int getId( )
        	{
        		return mId;
        	}
        	
        	public String getText( )
        	{
        		return mText;
        	}
        	
        	public int getIconResourceId( )
        	{
        		return mIconResourceId;
        	}
        	    
        	public void setText( String Text )
        	{
        		mText = Text;
        	}
        	
        	public void setIconResourceId( int iconResourceId )
        	{
        		mIconResourceId = iconResourceId;
        	}
        }
        
        public static interface OnTabClickListener
        {
        	public abstract void onTabClick( int tabId );
        }
	}
	
	public static class iRelativeLayout extends RelativeLayout
	{
		private Paint	mPaint;
		private Rect	mRect;
		
		public iRelativeLayout( Context context, AttributeSet attrs ) 
		{
			super(context, attrs);
			
			mRect = new Rect( );
			mPaint = new Paint( );
			
			mPaint.setStyle( Paint.Style.FILL_AND_STROKE );
			mPaint.setColor( 0xFFCBD2D8 );
		}
		
		@Override
		protected void onDraw( Canvas canvas )
		{
			super.onDraw( canvas );

			canvas.drawColor( 0xFFC5CCD4 );
			
			this.getDrawingRect( mRect );
			
			for( int i = 0; i < mRect.right; i += 7 )
			{
				canvas.drawRect( mRect.left + i, mRect.top, mRect.left + i + 2, mRect.bottom, mPaint );
			}

		}
	}
	
	public static final String TAG = "SAMPLEAPP"; 

	private static final int TAB_HIGHLIGHT = 1;
	private static final int TAB_CHAT = 2;
	private static final int TAB_LOOPBACK = 3;
	private static final int TAB_REDO = 4;
	private iTab			mTabs;
	private LinearLayout 	mTabLayout_One;
	private LinearLayout 	mTabLayout_Two;
	private LinearLayout 	mTabLayout_Three;
	private LinearLayout 	mTabLayout_Four;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
        
        mTabs = (iTab) this.findViewById( R.id.Tabs );
        mTabLayout_One = (LinearLayout) this.findViewById( R.id.TabLayout_One );
        mTabLayout_Two = (LinearLayout) this.findViewById( R.id.TabLayout_Two );
        mTabLayout_Three = (LinearLayout) this.findViewById( R.id.TabLayout_Three );
        mTabLayout_Four = (LinearLayout) this.findViewById( R.id.TabLayout_Four );
        
        mTabs.addTabMember( new TabMember( TAB_HIGHLIGHT, "Loopback", R.drawable.loopback ) );
        mTabs.addTabMember( new TabMember( TAB_CHAT, "Chat", R.drawable.chat ) );
        mTabs.addTabMember( new TabMember( TAB_LOOPBACK, "download", R.drawable.download ) );
        mTabs.addTabMember( new TabMember( TAB_REDO, "redo", R.drawable.redo ) );
        
        
        mTabs.setOnTabClickListener( new OnTabClickListener( ) {
        	@Override
        	public void onTabClick( int tabId )
        	{
        		if( tabId == TAB_HIGHLIGHT )
        		{
        			mTabLayout_One.setVisibility( View.VISIBLE );
        			mTabLayout_Two.setVisibility( View.GONE );
        			mTabLayout_Three.setVisibility( View.GONE );
        			mTabLayout_Four.setVisibility( View.GONE );
        		} else if( tabId == TAB_CHAT )
        		{
        			mTabLayout_One.setVisibility( View.GONE );
        			mTabLayout_Two.setVisibility( View.VISIBLE );
        			mTabLayout_Three.setVisibility( View.GONE );
        			mTabLayout_Four.setVisibility( View.GONE );
        		} else if( tabId == TAB_LOOPBACK )
        		{
        			mTabLayout_One.setVisibility( View.GONE );
        			mTabLayout_Two.setVisibility( View.GONE );
        			mTabLayout_Three.setVisibility( View.VISIBLE );
        			mTabLayout_Four.setVisibility( View.GONE );
        		} else if( tabId == TAB_REDO )
        		{
        			mTabLayout_One.setVisibility( View.GONE );
        			mTabLayout_Two.setVisibility( View.GONE );
        			mTabLayout_Three.setVisibility( View.GONE );
        			mTabLayout_Four.setVisibility( View.VISIBLE );
        		}
        	}
        });
    }
}