package com.deemsysinc.gpsmobiletracking;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.text.Html;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	TextView orgname;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		// getActionBar().hide();
		getActionBar().setTitle(
				Html.fromHtml("<font color='#ffffff'>Profile </font>"));
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(
				new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
						R.drawable.actionbarbg)));

		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.profile);
		orgname = (TextView) findViewById(R.id.orgname);
		orgname.setText(Config.username);

	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			Intent myIntent2 = new Intent(ProfileActivity.this,
					DashboardActivity.class);
			ProfileActivity.this.startActivity(myIntent2);
			overridePendingTransition(R.anim.pushup, R.anim.pushdown);

		default:
			return super.onOptionsItemSelected(item);
		}

	}
}