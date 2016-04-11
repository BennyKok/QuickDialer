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
import android.text.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.widget.*;
import android.widget.RelativeLayout.*;
import android.view.View.OnClickListener;
import android.support.v7.widget.*;

public class QuickDialerActivity extends Activity implements OnClickListener,TextWatcher
{
	RelativeLayout contentView;
	FrameLayout buttonPad;
	EditText input,et1;
	LinearLayout suggestionView;
	TextView t1,t2;

	ArrayMap<String,String> contacts = new ArrayMap();

	int buttonPadSize = 150,buttonPadPadding = 60,textSize = 30,ADD_CONTACT = 0x371098, buttonPadColor;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		contentView = new RelativeLayout(this);
		contentView.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		buttonPad = new FrameLayout(this);

		buttonPad.setId(buttonPad.generateViewId());
		buttonPadPadding += buttonPadSize;

		int count = 0;
		int pad = buttonPadSize / 4;
		buttonPadColor = getIntent().getIntExtra("Color", Color.DKGRAY);
		for (int y = 0 ; y < 4 ; y ++)
		{
			for (int x = 0 ; x < 3 ; x ++)
			{
				count ++;
				CardView cv = new CardView(this);
				cv.setCardElevation(10);
				cv.setCardBackgroundColor(buttonPadColor);
				cv.setRadius(buttonPadSize / 2);

				FrameLayout.LayoutParams buttonLayoutParams = new FrameLayout.LayoutParams(buttonPadSize, buttonPadSize);
				buttonLayoutParams.setMargins(x == 0 ?pad: buttonPadPadding * x + pad, y == 0 ?pad: (buttonPadPadding - buttonPadPadding / 5) * y + pad, x == 2 ?pad: 0, y == 3 ?pad: 0);
				cv.setLayoutParams(buttonLayoutParams);



				FrameLayout.LayoutParams buttonTextParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
				buttonTextParams.gravity = Gravity.CENTER;
				switch (count)
				{
					case 10:
						ImageView iv = new ImageView(this);
						iv.setImageResource(R.drawable.ic_phone);
						cv.addView(iv, buttonTextParams);
						break;
					case 11:
						TextView tv = new TextView(this);
						tv.setTextColor(Color.WHITE);
						tv.setTextSize(textSize);
						tv.setText("0");					
						cv.addView(tv, buttonTextParams);
						break;
					case 12:
						ImageView iv2 = new ImageView(this);
						iv2.setImageResource(R.drawable.ic_chevron_left);
						cv.addView(iv2, buttonTextParams);
						break;
					default:
						TextView tv2 = new TextView(this);
						tv2.setTextColor(Color.WHITE);
						tv2.setTextSize(textSize);
						tv2.setText(String.valueOf(count));
						cv.addView(tv2, buttonTextParams);

				}

				cv.setClickable(true);
				cv.setOnClickListener(this);

				cv.setScaleX(0);
				cv.setScaleY(0);
				buttonPad.addView(cv);
			}
		}

		RelativeLayout.LayoutParams buttonPadLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		buttonPadLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		buttonPadLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		buttonPad.setLayoutParams(buttonPadLayoutParams);
		contentView.addView(buttonPad);

		input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_PHONE);
		input.setTextSize(textSize);
		input.setPadding(20, 30, 20, 30);
		input.setTextColor(Color.WHITE);
		input.setFocusableInTouchMode(false);
		input.setBackgroundColor(Color.TRANSPARENT);
		input.addTextChangedListener(this);
		LinearLayout l = new LinearLayout(this);
		l.setWeightSum(12);
		final CardView warpCardView1 = warpCardView(l, buttonPadColor, buttonPadPadding);
		warpCardView1.setId(warpCardView1.generateViewId());
		warpCardView1.setRadius(5);

		warpCardView1.setY(-200);
		ImageButton btn1 = new ImageButton(this);
		ImageButton btn2 = new ImageButton(this);
		btn1.setBackgroundDrawable(new RippleDrawable(ColorStateList.valueOf(Color.WHITE), null, null));
		btn2.setBackgroundDrawable(new RippleDrawable(ColorStateList.valueOf(Color.WHITE), null, null));
		btn1.setImageResource(R.drawable.ic_plus);
		btn2.setImageResource(R.drawable.ic_history);
		btn1.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					showDialog(ADD_CONTACT);
					et1.setText(input.getText().toString());
				}
			});
		btn2.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					Intent i = new Intent(QuickDialerActivity.this,CallHistoryActivity.class);
					i.putExtra("Color",buttonPadColor);
					startActivity(i);
				}
			});


		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
		lp.weight = 8;
		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
		lp2.weight = 2;
		lp2.gravity = Gravity.CENTER_VERTICAL;
		LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
		lp3.weight = 2;
		lp3.gravity = Gravity.CENTER_VERTICAL;

		l.addView(input, lp);
		l.addView(btn1, lp2);
		l.addView(btn2, lp3);

		contentView.addView(warpCardView1);

		suggestionView = new LinearLayout(this);
		
		suggestionView.setOrientation(LinearLayout.VERTICAL);
		t1 = new TextView(this);
		t2 = new TextView(this);
		suggestionView.addView(t1);
		suggestionView.addView(t2);
		suggestionView.setPadding(20, 10, 20, 10);
		t1.setTextColor(Color.WHITE);
		t2.setTextColor(Color.WHITE);
		t1.setTextSize(textSize);
		t2.setTextSize(textSize);
		//suggestionView.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
		//suggestionView.setDividerDrawable(new ColorDrawable(Color.WHITE));
		RelativeLayout.LayoutParams suggestionViewLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		suggestionViewLayoutParams.addRule(RelativeLayout.BELOW, warpCardView1.getId());
		CardView warpCardView = warpCardView(suggestionView, buttonPadColor, buttonPadPadding);
		suggestionViewLayoutParams.setMargins(20, 20, 20, 0);
		FrameLayout.LayoutParams lp21 = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp21.gravity = Gravity.END|Gravity.CENTER_VERTICAL;
		ImageButton b = new ImageButton(this);
		b.setBackground(new RippleDrawable(ColorStateList.valueOf(Color.WHITE), null, null));
		b.setImageResource(R.drawable.ic_phone);
		b.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					if (input.getText().toString().isEmpty())return;
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + t2.getText().toString()));
					startActivity(intent);
				}
			});
		warpCardView.addView(b,lp21);
		warpCardView.setLayoutParams(suggestionViewLayoutParams);
		warpCardView.setRadius(5);
		contentView.addView(warpCardView);

		Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		while (phones.moveToNext())
		{
			String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			contacts.put(String.valueOf(phoneNumber), name);
		}
		phones.close();

        

		((View)suggestionView.getParent()).setVisibility(View.INVISIBLE);
		setContentView(contentView);
		contentView.postDelayed(new Runnable(){
				@Override
				public void run()
				{
					warpCardView1.animate().y(30);
					for (int x = 0 ; x < 12 ; x ++)
					{
						buttonPad.getChildAt(x).animate().scaleX(1).scaleY(1).setDuration(1500 * x / 8).setInterpolator(new OvershootInterpolator());
					}
				}
			}, 500);
		super.onCreate(savedInstanceState);
	}

	CardView warpCardView(View v, int color, int pad)
	{
		CardView cv = new CardView(this);
		cv.addView(v);
		cv.setCardBackgroundColor(color);
		RelativeLayout.LayoutParams marginLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		cv.setCardElevation(10);
		marginLayoutParams.setMargins(20, 20, 20, 0);
		cv.setLayoutParams(marginLayoutParams);
		return cv;
	}

	@Override
	public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
	{}

	@Override
	public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
	{
		if (p1.toString().isEmpty())
		{
			t2.setText("");
			t1.setText("");
			((View)suggestionView.getParent()).setVisibility(View.INVISIBLE);
			return;
		}
		for (int i = 0; i < contacts.size(); i ++)
		{
			if (contacts.keyAt(i).toLowerCase().contains(p1))
			{
				((View)suggestionView.getParent()).setVisibility(View.VISIBLE);
				t2.setText(contacts.keyAt(i));
				t1.setText(contacts.valueAt(i));
				return;
			}
		}
		t2.setText("");
		t1.setText("");
		((View)suggestionView.getParent()).setVisibility(View.INVISIBLE);
	}

	@Override
	public void afterTextChanged(Editable p1)
	{}

	@Override
	protected Dialog onCreateDialog(int id)
	{
		if (id == ADD_CONTACT)
		{
			AlertDialog.Builder d = new AlertDialog.Builder(this);
			d.setTitle("New contact");
			LinearLayout l = new LinearLayout(this);
			l.setOrientation(l.VERTICAL);
			l.setDividerDrawable(new ColorDrawable(Color.DKGRAY));
			l.setDividerPadding(10);
			l.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

			et1 = new EditText(this);
			final EditText et2 = new EditText(this);

			et1.setText(input.getText().toString());
			et2.setText("");

			et1.setHint("Phone");
			et2.setHint("Name");

			et1.setPadding(20, 30, 20, 30);
			et2.setPadding(20, 30, 20, 30);

			et1.setBackgroundColor(Color.TRANSPARENT);
			et2.setBackgroundColor(Color.TRANSPARENT);

			l.addView(et1);
			l.addView(et2);

			d.setView(l);

			d.setNegativeButton("cancel", null);
			d.setPositiveButton("ok", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
						contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
						contactIntent
							.putExtra(ContactsContract.Intents.Insert.NAME, et2.getText().toString())
							.putExtra(ContactsContract.Intents.Insert.PHONE, et1.getText().toString())
							;
						startActivity(contactIntent);
						et1.setText("");
						et2.setText("");
						input.setText("");
					}
				});

			return d.create();
		}
		return super.onCreateDialog(id);
	}

	@Override
	public void onClick(final View p1)
	{
		p1.animate().scaleX(1.2f).scaleY(1.2f).setDuration(500).withEndAction(new Runnable(){
				@Override
				public void run()
				{
					p1.animate().scaleX(1f).scaleY(1f).setDuration(500);
				}
			});
		int indexOfChild = ((ViewGroup)p1.getParent()).indexOfChild(p1);
		switch (indexOfChild)
		{
			case 9:
				if (input.getText().toString().isEmpty())break;
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + input.getText().toString()));
				startActivity(intent);
				input.setText("");
				break;
			case 10:
				input.append("0");
				break;
			case 11:
				if (input.getText().toString().isEmpty())break;
				input.getText().delete(input.getText().length() - 1, input.getText().length());
				break;
			default:
				input.append(String.valueOf(indexOfChild + 1));
		}
	}

	
}
