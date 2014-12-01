package com.deemsysinc.gpsmobiletracking;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class PrivacyAndPolicyTab extends Activity {
	TextView useof, storage, weuse, howwe, access, thirdparty, change,
			collection, cop;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().setBackgroundDrawable(
				new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
						R.drawable.actionbarbg)));
		getActionBar().setTitle(
				Html.fromHtml("<font color='#ffffff'>Privacy Policy</font>"));
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.privacytab);
		useof = (TextView) findViewById(R.id.useof);
		cop = (TextView) findViewById(R.id.complaintspro);
		collection = (TextView) findViewById(R.id.collection);
		storage = (TextView) findViewById(R.id.storage);
		weuse = (TextView) findViewById(R.id.weuse);
		howwe = (TextView) findViewById(R.id.howwe);
		access = (TextView) findViewById(R.id.access);
		thirdparty = (TextView) findViewById(R.id.thirdparty);
		change = (TextView) findViewById(R.id.changesto);
		useof.setTypeface(null, Typeface.BOLD);
		storage.setTypeface(null, Typeface.BOLD);
		weuse.setTypeface(null, Typeface.BOLD);
		howwe.setTypeface(null, Typeface.BOLD);
		access.setTypeface(null, Typeface.BOLD);
		thirdparty.setTypeface(null, Typeface.BOLD);
		change.setTypeface(null, Typeface.BOLD);
		collection.setTypeface(null, Typeface.BOLD);
		cop.setTypeface(null, Typeface.BOLD);
	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			Intent myIntent2 = new Intent(PrivacyAndPolicyTab.this,
					AboutActivity.class);

			PrivacyAndPolicyTab.this.startActivity(myIntent2);
			overridePendingTransition(R.anim.pushup, R.anim.pushdown);

		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
