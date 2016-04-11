package com.benny.QuickDialer;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.database.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.view.ViewGroup.*;
import android.widget.*;
import java.util.*;

import android.view.View.OnClickListener;

public class CallHistoryActivity extends Activity
{
	ListView historyView;
	ArrayList<HistoryInfo> history = new ArrayList();

	int buttonPadColor;

	private class HistoryInfo
	{
		public String caller,number,type;

		public HistoryInfo(String caller, String number, String type)
		{
			this.caller = caller;
			this.number = number;
			this.type = type;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		buttonPadColor = getIntent().getIntExtra("Color", Color.DKGRAY);
		Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
		managedCursor.moveToLast();
		for (int i = 0;i < 15 ;i++)
		//while(managedCursor.moveToPrevious())
		{
            String phNumber = managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.NUMBER));
            String callType = managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.TYPE));
            String caller = managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode)
			{
				case CallLog.Calls.OUTGOING_TYPE:
					dir = "OUTGOING";
					break;
				case CallLog.Calls.INCOMING_TYPE:
					dir = "INCOMING";
					break;
				case CallLog.Calls.MISSED_TYPE:
					dir = "MISSED";
					break;
            }
			history.add(new HistoryInfo(caller, phNumber, dir));
			if(!managedCursor.moveToPrevious())break;
        }
        managedCursor.close();

		historyView = new ListView(this);
		historyView.setScrollbarFadingEnabled(false);
		historyView.setAdapter(new CustomViewAdapter(this, history));
		RelativeLayout.LayoutParams lp4 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp4.addRule(RelativeLayout.ALIGN_PARENT_END);
		historyView.setDividerHeight(0);
		historyView.setScrollBarSize(15);
		historyView.setLayoutParams(lp4);
		historyView.setSelector(new ColorDrawable(Color.TRANSPARENT));

		View sb = new View(this);
		sb.setBackgroundColor(buttonPadColor);
		RelativeLayout.LayoutParams lp5 = new RelativeLayout.LayoutParams(15, LayoutParams.MATCH_PARENT);
		lp5.addRule(RelativeLayout.ALIGN_PARENT_END);
		sb.setLayoutParams(lp5);
		
		RelativeLayout l = new RelativeLayout(this);
		l.addView(sb);
		l.addView(historyView);
		setContentView(l);
		super.onCreate(savedInstanceState);
	}

	private class CustomViewAdapter extends ArrayAdapter
	{
		List<HistoryInfo> data;
		Context c;
		
		public CustomViewAdapter(Context c, List l)
		{
			super(c, android.R.layout.simple_list_item_1);
			this.data = l;
			this.c = c;
		}

		private class ViewHolder
		{
			FrameLayout cv;
			TextView tv,tv2;
			ImageButton b;
		}

		@Override
		public int getCount()
	    {
			return data.size();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			ViewHolder vh;
			LinearLayout l;
			TextView name = null,type = null;
			if (convertView == null)
			{
			
				convertView = new FrameLayout(c);
				CardView cv = new CardView(c);

				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				lp.setMargins(20, 15, 50, 15);
				cv.setLayoutParams(lp);
				cv.setCardElevation(10);
				cv.setRadius(5);
				cv.setCardBackgroundColor(buttonPadColor);

				l = new LinearLayout(c);
				name = new TextView(c);
				type = new TextView(c);

				l.setOrientation(l.VERTICAL);

				name.setTextSize(30);

				name.setPadding(20, 10, 20, 10);
				type.setPadding(20, 0, 20, 10);

				name.setTextColor(Color.WHITE);
				type.setTextColor(Color.WHITE);
				
				name.setGravity(Gravity.CENTER_VERTICAL);
				type.setGravity(Gravity.CENTER_VERTICAL);

				l.addView(name);
				l.addView(type);

				cv.addView(l);

				FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				lp2.gravity = Gravity.END|Gravity.CENTER_VERTICAL;
				ImageButton b = new ImageButton(c);
				b.setBackground(new RippleDrawable(ColorStateList.valueOf(Color.WHITE), null, null));
				b.setImageResource(R.drawable.ic_phone);
				cv.addView(b,lp2);
				((FrameLayout)convertView).addView(cv);
				
				vh = new ViewHolder();
				vh.cv = (FrameLayout) convertView;
				vh.tv = name;
				vh.tv2 = type;
				vh.b = b;
				convertView.setTag(vh);
			}
			else
			{
				vh = (ViewHolder) convertView.getTag();
			}
			
			vh.tv.setText(data.get(position).caller!=null ? data.get(position).caller:data.get(position).number);
			vh.tv2.setText(data.get(position).type);
			if(data.get(position).type.equals("OUTGOING")){
				vh.tv2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_left,0,0,0);
			}else if(data.get(position).type.equals("INCOMING")){
				vh.tv2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_right,0,0,0);
			}else if(data.get(position).type.equals("MISSED")){
				vh.tv2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_close,0,0,0);
			}
			vh.b.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View p1)
					{
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + data.get(position).number));
						startActivity(intent);
					}
				});
			
			return convertView;
		}

	}

}
