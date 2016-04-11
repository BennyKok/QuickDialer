package com.benny.QuickDialer;

import android.app.*;
import android.os.*;
import android.content.*;
import android.view.*;
import android.net.*;

public class AboutActivity extends BaseAboutActiviy 
{
	@Override
	public void onClick(View p1)
	{
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse("https://github.com/Benny-Kok/QuickDialer-DarkLauncher-extension-example/blob/master/README.md"));
		startActivity(i);
	}

	@Override
	int getExtensionName()
	{
		return R.string.app_name;
	}

	@Override
	int getDescription()
	{
		return R.string.description;
	}

	@Override
	int getChangelog()
	{
		return R.string.changelog;
	}
}
