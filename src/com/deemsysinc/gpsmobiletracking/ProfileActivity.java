package com.deemsysinc.gpsmobiletracking;

import java.util.ArrayList;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	TextView orgname;

	ListView lv;
	String a="";
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
		// orgname = (TextView) findViewById(R.id.orgname);
		// orgname.setText(Config.username);

		lv = (ListView) findViewById(R.id.list1);
		ArrayList<String> arr = new ArrayList<String>();
		if (Config.role.equalsIgnoreCase("ROLE_FCLIENT")) {

			a="Fleet";
		} else if (Config.role.equalsIgnoreCase("ROLE_PCLIENT")) {

			a="School";

		} 
		
		
		String[] web = { "Organisation Name:"+Config.username,
				"Type : "+a,
				"Organisation Address : "+"addrs",
				"Telephone : "+"9677881101",

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
			overridePendingTransition(R.anim.pushup, R.anim.pushdown);

		default:
			return super.onOptionsItemSelected(item);
		}

	}
}