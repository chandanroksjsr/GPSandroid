package com.deemsysinc.gpsmobiletracking;

import android.R.anim;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

@SuppressWarnings("deprecation")
public class Aboutus extends TabActivity implements OnTabChangeListener {
	TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		// getActionBar().hide();
		getActionBar().setBackgroundDrawable(new BitmapDrawable (BitmapFactory.decodeResource(getResources(), R.drawable.actionbarbg)));
		
		getActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>DeemGPS</font>"));
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.aboutus);
		tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);

		TabHost.TabSpec spec;
		Intent intent;
		intent = new Intent().setClass(this, AboutusTab.class);
		spec = tabHost.newTabSpec("First").setIndicator("").setContent(intent);
		tabHost.addTab(spec);
		intent = new Intent().setClass(this, PrivacyAndPolicyTab.class);
		spec = tabHost.newTabSpec("Second").setIndicator("").setContent(intent);
		tabHost.addTab(spec);
		intent = new Intent().setClass(this, TermsAndConditionTab.class);
		spec = tabHost.newTabSpec("Third").setIndicator("").setContent(intent);
		tabHost.addTab(spec);
		TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0)
				.findViewById(android.R.id.title);
		tv.setText("About Us");
		tabHost.getTabWidget().getChildAt(1);
		TextView tv1 = (TextView) tabHost.getTabWidget().getChildAt(1)
				.findViewById(android.R.id.title);
		tv1.setText("Privacy Policy");
		tabHost.getTabWidget().getChildAt(2);
		TextView tv2 = (TextView) tabHost.getTabWidget().getChildAt(2)
				.findViewById(android.R.id.title);
		tv2.setText("Terms And Conditions");
		tabHost.getTabWidget().setCurrentTab(0);
		tabHost.getTabWidget().getChildAt(0);
	}

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			if (i == 0) {
				TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0)
						.findViewById(android.R.id.title);
				tv.setText("About Us");
				tabHost.getTabWidget().getChildAt(i);
			} else if (i == 1) {
				TextView tv1 = (TextView) tabHost.getTabWidget().getChildAt(1)
						.findViewById(android.R.id.title);
				tv1.setText("Privacy Policy");
				tabHost.getTabWidget().getChildAt(i);
			} else if (i == 2) {
				TextView tv2 = (TextView) tabHost.getTabWidget().getChildAt(2)
						.findViewById(android.R.id.title);
				tv2.setText("Terms And Conditions");
				tabHost.getTabWidget().getChildAt(i);
			}
		}

		Log.i("tabs", "CurrentTab: " + tabHost.getCurrentTab());

		if (tabHost.getCurrentTab() == 0) {
			TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0)
					.findViewById(android.R.id.title);
			tv.setText("About Us");
			tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());
		} else if (tabHost.getCurrentTab() == 1) {
			TextView tv1 = (TextView) tabHost.getTabWidget().getChildAt(1)
					.findViewById(android.R.id.title);
			tv1.setText("Privacy Policy");
			tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());
		} else if (tabHost.getCurrentTab() == 2) {
			TextView tv2 = (TextView) tabHost.getTabWidget().getChildAt(2)
					.findViewById(android.R.id.title);
			tv2.setText("Terms And Conditions");
			tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());
		}

	}
	@Override
	public void onBackPressed() {
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		
		case android.R.id.home:
			Intent myIntent2 = new Intent(Aboutus.this,
					DashboardActivity.class);
			Aboutus.this.startActivity(myIntent2);
			overridePendingTransition(R.anim.pushup,
					R.anim.pushdown);
			

		default:
			return super.onOptionsItemSelected(item);
		}

	}
}