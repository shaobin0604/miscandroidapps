package com.easymorse.textbutton;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextButtonActivity extends Activity {

	private ArrayList<TextButton> buttons;

	private LinearLayout contentLayout;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.contentLayout = (LinearLayout) this.findViewById(R.id.c1);
		initButtons();
		setTabButton(buttons.get(0));
	}

	private void setTabButton(TextButton button) {
		for (int i = 0; i < buttons.size(); i++) {
			if (button == buttons.get(i)) {
				buttons.get(i).setEnabled(false);
				showTabContent(i);
			} else {
				buttons.get(i).setEnabled(true);
			}
		}
	}

	private void showTabContent(int index) {
		this.contentLayout.removeAllViews();
		TextView textView = new TextView(this);

		switch (index) {
		case 0:
			textView.setText("看电影");
			break;
		case 1:
			textView.setText("看电视");
			break;
		case 2:
			textView.setText("看明星");
			break;
		}

		this.contentLayout.addView(textView);
	}

	private void initButtons() {
		this.buttons = new ArrayList<TextButton>();
		OnClickListener onClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				setTabButton((TextButton) v);
			}
		};

		this.initButton((TextButton) this.findViewById(R.id.b1),
				onClickListener);
		this.initButton((TextButton) this.findViewById(R.id.b2),
				onClickListener);
		this.initButton((TextButton) this.findViewById(R.id.b3),
				onClickListener);
	}

	private void initButton(TextButton button, OnClickListener onClickListener) {
		button.setOnClickListener(onClickListener);
		this.buttons.add(button);
	}
}