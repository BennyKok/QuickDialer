# QuickDialer-DarkLauncher-extension-example
A guide for DarkLauncher extension.

If you want to make an extension for DarkLauncher you need to have this intent filter in your activity    
```
<intent-filter>
	<action android:name="com.benny.intent.action.DRAWER_BUTTON_LONG_PRESS"/>
</intent-filter>
```
and it is recommended to have a translucent activity
```
<style name="TransparentTheme" parent="android:Theme.Material.Light">
    •••
	<item name="android:colorPrimaryDark">#00000000</item>
	<item name="android:navigationBarColor">#00000000</item>
	<item name="android:windowBackground">@android:color/transparent</item>
	<item name="android:windowNoTitle">true</item>
	<item name="android:windowShowWallpaper">true</item>
	•••
</style>
```
If you want to have a similar about page like the QuickDialer, you can extend the BaseAboutActivity, but remember you have to use a theme with no default action bar, or else there will be some problem.

For further information you can refer to the QuickDialer project or contact me via email.

And the code might be ugly or not well formated because it was in a hurry, so I Apologise for that.
