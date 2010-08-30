package com.easymorse.textbutton;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextButton extends TextView {

	private int regularColor = 0xffffffff;

	private int selectedColor = 0xffffffff;

	public TextButton(Context context) {
		super(context);
	}

	public TextButton(final Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TextButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			this.selected();
		} else {
			this.unSelected();
		}
	}

	private void selected() {
		this.setTextColor(this.selectedColor);
	}

	private void unSelected() {
		this.setTextColor(this.regularColor);
	}

}
