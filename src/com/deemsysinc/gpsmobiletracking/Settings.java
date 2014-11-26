package com.deemsysinc.gpsmobiletracking;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class Settings extends Activity implements OnItemSelectedListener {
	Spinner alarmtypespin;
	Button savesett;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs0";

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
		setContentView(R.layout.settings);
		savesett = (Button) findViewById(R.id.savesettings);
		sharedpreferences = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		ArrayList<String> timingarr = new ArrayList<String>();
		timingarr.add("30 secs");
		timingarr.add("60 secs");
		timingarr.add("2 mins");
		timingarr.add("3 mins");
		ArrayList<String> alarmtype = new ArrayList<String>();
		alarmtype.add("type 1");
		alarmtype.add("type 2");
		alarmtype.add("type 3");
		alarmtype.add("type 4");
		alarmtype.add("default");
		// refreshtiming = (Spinner) findViewById(R.id.timing);
		alarmtypespin = (Spinner) findViewById(R.id.alarmtype);
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, alarmtype);

		dataAdapter1
				.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

		alarmtypespin.setAdapter(dataAdapter1);
		System.out
				.println("value of alarmsound type::" + Config.alarmsoundtype);
		if (Config.alarmsoundtype != null) {

			if (Config.alarmsoundtype.equalsIgnoreCase("type 1")) {

				alarmtypespin.setSelection(dataAdapter1.getPosition("type 1"));
			} else if (Config.alarmsoundtype.equalsIgnoreCase("type 2")) {

				alarmtypespin.setSelection(dataAdapter1.getPosition("type 2"));
			} else if (Config.alarmsoundtype.equalsIgnoreCase("type 3")) {
				alarmtypespin.setSelection(dataAdapter1.getPosition("type 3"));
			} else if (Config.alarmsoundtype.equalsIgnoreCase("type 4")) {
				alarmtypespin.setSelection(dataAdapter1.getPosition("type 4"));
			} else if (Config.alarmsoundtype.equalsIgnoreCase("default")) {
				alarmtypespin.setSelection(dataAdapter1.getPosition("default"));
			}
		} else {
			alarmtypespin.setSelection(dataAdapter1.getPosition("default"));
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, timingarr);

		dataAdapter
				.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

		// refreshtiming.setAdapter(dataAdapter);
		// System.out.println("value of refresh time type::" +
		// Config.refreshtime);
		// if (Config.refreshtime != null) {
		//
		// if (Config.refreshtime.equalsIgnoreCase("30 secs")) {
		//
		// refreshtiming.setSelection(dataAdapter.getPosition("30 secs"));
		// } else if (Config.refreshtime.equalsIgnoreCase("60 secs")) {
		//
		// refreshtiming.setSelection(dataAdapter.getPosition("60 secs"));
		// } else if (Config.refreshtime.equalsIgnoreCase("2 mins")) {
		// refreshtiming.setSelection(dataAdapter.getPosition("2 mins"));
		// } else if (Config.refreshtime.equalsIgnoreCase("3 mins")) {
		// refreshtiming.setSelection(dataAdapter.getPosition("3 mins"));
		// }
		// } else {
		// refreshtiming.setSelection(dataAdapter.getPosition("30 secs"));
		// }

		savesett.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// Config.refreshtime =
				// refreshtiming.getSelectedItem().toString();
				Config.alarmsoundtype = alarmtypespin.getSelectedItem()
						.toString();
				Editor editor = sharedpreferences.edit();
				// editor.putString("refreshtime", Config.refreshtime);
				editor.putString("alarmsoundtype", Config.alarmsoundtype);
				editor.commit();
				// System.out.println("refresh time" + Config.refreshtime);
				System.out.println("alarm time" + Config.alarmsoundtype);

			}
		});
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	public void onBackPressed() {
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			Intent myIntent2 = new Intent(Settings.this,
					DashboardActivity.class);
			Config.flag = "alreadyloggedin";
			myIntent2.putExtra("isalreadylogged", Config.flag);
			Settings.this.startActivity(myIntent2);
			overridePendingTransition(R.anim.pushup, R.anim.pushdown);

		default:
			return super.onOptionsItemSelected(item);
		}

	}
}