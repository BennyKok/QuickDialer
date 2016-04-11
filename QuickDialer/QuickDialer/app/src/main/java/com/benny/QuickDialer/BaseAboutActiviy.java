package com.benny.QuickDialer;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.support.v7.widget.*;
import android.text.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.Gallery.*;

import android.view.View.OnClickListener;

public abstract class BaseAboutActiviy extends Activity implements OnClickListener
{
	private Toolbar tb;
	private LinearLayout contentView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		tb = new Toolbar(this);
		tb.setElevation(10);
		tb.setTitle("Extension information");
		tb.setTitleTextColor(Color.WHITE);
		tb.setBackgroundColor(Color.rgb(0, 152, 136));
		setActionBar(tb);
		getWindow().setStatusBarColor(Color.parseColor("#00897b"));
		
		contentView = new LinearLayout(this);
		contentView.setOrientation(contentView.VERTICAL);

		TextView t1 = new TextView(this);
		LinearLayout l = new LinearLayout(this);
		l.setOrientation(l.VERTICAL);
		l.addView(t1);
		t1.setTextColor(Color.rgb(0, 152, 136));
		t1.setTextSize(30);
		t1.setText(getExtensionName());
		t1.setPadding(0, 20, 0, 20);

		contentView.addView(warpCardView(l));
		contentView.addView(infoCard("Warning", R.string.warning, Color.RED, new View[]{Button("Download Material DarkLauncher", 0, 0, new OnClickListener(){
												 @Override
												 public void onClick(View p1)
												 {
													 Intent i = new Intent(Intent.ACTION_VIEW);
													 i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.benny.Launcher"));
													 startActivity(i);
												 }
											 })}));
		contentView.addView(infoCard("Description", getDescription()));
		contentView.addView(infoCard("Changelog", getChangelog()));
		LinearLayout lll = new LinearLayout(this);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.END;
		layoutParams.setMargins(0, 30, 0, 0);
		lll.setLayoutParams(layoutParams);
		lll.addView(Button("MORE", Color.rgb(0, 152, 136), 0, this));
		lll.addView(Button("APPLY", Color.rgb(0, 152, 136), 0, new OnClickListener(){
							@Override
							public void onClick(View p1)
							{
								showDialog(123);
							}
						}));
		contentView.addView(lll);

		ScrollView s = new ScrollView(this);
		s.addView(contentView);
		s.setPadding(0, 0, 0, 30);
		s.setClipToPadding(false);

		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(ll.VERTICAL);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ll.addView(tb, lp);
		ll.addView(s);

		setContentView(ll);
		super.onCreate(savedInstanceState);
	}

	private View infoCard(String s1, int s2)
	{
		TextView t3 = new TextView(this),t4 = new TextView(this);
		LinearLayout l2 = new LinearLayout(this);
		l2.setOrientation(l2.VERTICAL);
		l2.addView(t3);
		l2.addView(t4);
		t3.setTextSize(15);
		t3.setTextColor(Color.rgb(0, 152, 136));
		t3.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		t3.setText(s1);
		t3.setPadding(0, 20, 0, 0);
		t4.setTextColor(Color.DKGRAY);
		t4.setText(Html.fromHtml(getResources().getString(s2)));
		t4.setPadding(0, 20, 0, 20);

		return warpCardView(l2);
	}

	@Override
	protected Dialog onCreateDialog(int id)
	{
		switch(id){
			case 123:
				AlertDialog.Builder b = new AlertDialog.Builder(this);
				b.setTitle("Appyling "+getResources().getString(R.string.app_name));
				b.setMessage("Open DarkLauncher and go to Settings>>>Extension>>>On drawer button long press, then choose "+getResources().getString(R.string.app_name)+".");
				b.setPositiveButton("ok",null);
				return b.create();
		}
		return super.onCreateDialog(id);
	}

	private View infoCard(String s1, int s2, int color, View[] vs)
	{
		TextView t3 = new TextView(this),t4 = new TextView(this);
		LinearLayout l2 = new LinearLayout(this);
		l2.setOrientation(l2.VERTICAL);
		l2.addView(t3);
		l2.addView(t4);
		t3.setTextSize(15);
		t3.setTextColor(color);
		t3.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		t3.setText(s1);
		t3.setPadding(0, 20, 0, 0);
		t4.setTextColor(Color.DKGRAY);
		t4.setText(Html.fromHtml(getResources().getString(s2)));
		t4.setPadding(0, 20, 0, 20);
		if (vs != null)
		{
			for (int i = 0; i < vs.length; i++)
			{
				l2.addView(vs[i]);
			}
		}

		return warpCardView(l2);
	}


	private View Button(String s, int color, int g, OnClickListener c)
	{
		CardView b = new CardView(this);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		if (g != 0)
			layoutParams.gravity = g;

		b.setLayoutParams(layoutParams);
		b.setCardElevation(5);
		b.setClickable(true);
		b.setRadius(5);

		TextView bt = new TextView(this);
		bt.setText(s);
		

		b.addView(bt);
		b.setOnClickListener(c);

		if (color == 0)
		{
			bt.setGravity(Gravity.CENTER_VERTICAL);
			b.setCardBackgroundColor(Color.TRANSPARENT);
			b.setCardElevation(0);
			layoutParams.width = layoutParams.MATCH_PARENT;
			bt.setTextColor(Color.rgb(0, 152, 136));
			b.setForeground(new RippleDrawable(ColorStateList.valueOf(Color.argb(50, 0, 0, 0)), null, null));
			bt.setPadding(10, 20, 40, 20);
		}
		else
		{
			bt.setPadding(40, 20, 40, 20);
			bt.setGravity(Gravity.CENTER);
			b.setCardBackgroundColor(color);
			bt.setTextColor(Color.WHITE);
			layoutParams.setMargins(0, 0, 30, 0);
			b.setForeground(new RippleDrawable(ColorStateList.valueOf(Color.WHITE), null, null));
		}


		return b;
	}

	private CardView warpCardView(View l)
	{
		CardView cv = new CardView(this);
		cv.addView(l);
		cv.setContentPadding(20, 20, 20, 20);
		cv.setElevation(8);
		cv.setRadius(5);
		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp2.setMargins(30, 30, 30, 0);
		cv.setLayoutParams(lp2);
		return cv;
	}

	abstract int getExtensionName();

	abstract int getDescription();

	abstract int getChangelog();
}
