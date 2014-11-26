package com.deemsysinc.gpsmobiletracking;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

public class AboutActivity extends Activity {
	ListView list;
	String[] web = { "Application Name : DeemsysGPS", "Version Number : 1.1",
			"Privacy Policy", "Website Url: www.deemgps.com",
			"Marketing Url : www.deemgpstracker.com",

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().setBackgroundDrawable(
				new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
						R.drawable.actionbarbg)));
		getActionBar().setTitle(
				Html.fromHtml("<font color='#ffffff'>DeemGPS</font>"));
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.aboutactivity);
		CustomList adapter = new CustomList(AboutActivity.this, web);
		list = (ListView) findViewById(R.id.listt);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 2) {
					Intent i = new Intent(AboutActivity.this,
							PrivacyAndPolicyTab.class);

					startActivity(i);
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);

				}
				if (position == 3) {
					String url = "http://www.deemgps.com";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i);
				}
				if (position == 4) {
					String url = "http://www.deemgpstracker.com";
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i);
				}
			}

		});
	}
	@Override
	public void onBackPressed() {
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			Intent myIntent2 = new Intent(AboutActivity.this,
					DashboardActivity.class);
			Config.flag ="alreadyloggedin";
			myIntent2.putExtra("isalreadylogged", Config.flag);
			AboutActivity.this.startActivity(myIntent2);
			overridePendingTransition(R.anim.pushup, R.anim.pushdown);

		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
