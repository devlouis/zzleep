package com.gowil.zzleep.zzleep.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.gowil.zzleep.R;

/**
 * TODO: document your custom view class.
 */
public class MainMenuView extends LinearLayout
{
	public MainMenuView(Context context)
	{
		super(context);
		init(null, 0);
	}

	public MainMenuView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(attrs, 0);
	}

	public MainMenuView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle)
	{

		// Load attributes
		final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MainMenuView, defStyle, 0);
		a.recycle();

		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.sample_main_menu_view, this);
		v.isInEditMode();
	}
}
