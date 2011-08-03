package com.tenny;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SlipTabsAct extends Activity {
	final int SUM = 3;
	TextView[] TVs;
	ImageView[] BGs;

	ImageView b1;
	ImageView b2;
	ImageView b3;

	int preClickID = 0;
	int curClickID = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initView();
	}

	public void initView() {
		TVs = new TextView[SUM];
		TVs[0] = (TextView) this.findViewById(R.id.t1);
		TVs[1] = (TextView) this.findViewById(R.id.t2);
		TVs[2] = (TextView) this.findViewById(R.id.t3);

		BGs = new ImageView[SUM];
		BGs[0] = (ImageView) this.findViewById(R.id.b1);
		BGs[1] = (ImageView) this.findViewById(R.id.b2);
		BGs[2] = (ImageView) this.findViewById(R.id.b3);

		for (int i = 0; i < SUM; i++) {
			TVs[i].setOnClickListener(clickListener);
		}

		TVs[0].setEnabled(false);
		preClickID = 0;
	}

	private void updataCurView(int curClickID) {
		if (0 <= curClickID && SUM > curClickID) {
			TVs[preClickID].setEnabled(true);
			TVs[curClickID].setEnabled(false);

			BGs[preClickID].setVisibility(View.INVISIBLE);
			BGs[curClickID].setVisibility(View.VISIBLE);

			preClickID = curClickID;
		}
	}

	private OnClickListener clickListener = new OnClickListener() {
		public void onClick(final View v) {
			Animation a = new TranslateAnimation(0.0f, v.getLeft()
					- TVs[preClickID].getLeft(), 0.0f, 0.0f);
			a.setDuration(400);
			a.setFillAfter(false);
			a.setFillBefore(false);

			for (int i = 0; i < SUM; i++) {
				if (TVs[i] == v) {
					curClickID = i;
					BGs[preClickID].startAnimation(a);

					break;
				}
			}

			a.setAnimationListener(new AnimationListener() {
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
				}

				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					updataCurView(curClickID);
				}

				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
				}

			});

		}
	};
}
