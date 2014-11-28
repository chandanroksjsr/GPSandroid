package com.deemsysinc.gpsmobiletracking;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.Window;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	TextView orgname;
	ImageView vehicle;
	ListView lv;
	String a = "";

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

		getActionBar().setTitle(
				Html.fromHtml("<font color='#ffffff'>Profile </font>"));

		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(
				new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
						R.drawable.actionbarbg)));

		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.profile);
		vehicle = (ImageView) findViewById(R.id.imageView1);
		lv = (ListView) findViewById(R.id.list1);
		Resources res = getResources();

		if (Config.role.equalsIgnoreCase("ROLE_FCLIENT")||Config.role.equalsIgnoreCase("ROLE_PCLIENT")) {
			Drawable draw = res.getDrawable(R.drawable.green_light);
			vehicle.setImageDrawable(draw);

			a = "Fleet";
		} else if (Config.role.equalsIgnoreCase("ROLE_ADMIN")) {
			Drawable draw = res.getDrawable(R.drawable.usericon);
			vehicle.setImageDrawable(draw);
			a = "School";

		}

		String[] web = { "Organisation Name:" + Config.username, "Type : " + a,
				"Organisation Address : " + Config.address,
				"Telephone : " + Config.telephone,

		};

		try {
			CustomList adapter = new CustomList(ProfileActivity.this, web);

			lv.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
			Config.flag = "alreadyloggedin";
			myIntent2.putExtra("isalreadylogged", Config.flag);
			ProfileActivity.this.startActivity(myIntent2);
			overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
		default:
			return super.onOptionsItemSelected(item);
		}

	}
}