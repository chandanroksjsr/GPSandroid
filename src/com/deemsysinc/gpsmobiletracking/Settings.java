package com.deemsysinc.gpsmobiletracking;

import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class Settings extends Activity implements OnItemSelectedListener {
	Spinner alarmtypespin;
	Button savesett, clearcache;
	MediaPlayer mPlayer;
	String onloadfalse;
	int alreadyselected;
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
				Html.fromHtml("<font color='#ffffff'>Settings</font>"));
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.settings);
		savesett = (Button) findViewById(R.id.savesettings);
		// clearcache = (Button) findViewById(R.id.clearcache);
		sharedpreferences = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		ArrayList<String> timingarr = new ArrayList<String>();
		timingarr.add("30 secs");
		timingarr.add("60 secs");
		timingarr.add("2 mins");
		timingarr.add("3 mins");
		ArrayList<String> alarmtype = new ArrayList<String>();
		alarmtype.add("Nuclear Alert");
		alarmtype.add("Car Alert");
		alarmtype.add("Extreme Alert");
		alarmtype.add("Handy Alert");
		alarmtype.add("Red Alert");
		// refreshtiming = (Spinner) findViewById(R.id.timing);
		alarmtypespin = (Spinner) findViewById(R.id.alarmtype);
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, alarmtype);

		dataAdapter1
				.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

		alarmtypespin.setAdapter(dataAdapter1);
		alreadyselected=alarmtypespin.getSelectedItemPosition();
		alarmtypespin.setOnItemSelectedListener(this);
		if (mPlayer != null && mPlayer.isPlaying()) {
			mPlayer.stop();
		}
		System.out
				.println("value of alarmsound type::" + Config.alarmsoundtype);
		if (Config.alarmsoundtype != null) {

			if (Config.alarmsoundtype.equalsIgnoreCase("Nuclear Alert")) {
				onloadfalse = "nottoplay";
				alarmtypespin.setSelection(dataAdapter1
						.getPosition("Nuclear Alert"));
			} else if (Config.alarmsoundtype.equalsIgnoreCase("Car Alert")) {
				onloadfalse = "nottoplay";
				alarmtypespin.setSelection(dataAdapter1
						.getPosition("Car Alert"));
			} else if (Config.alarmsoundtype.equalsIgnoreCase("Extreme Alert")) {
				onloadfalse = "nottoplay";
				alarmtypespin.setSelection(dataAdapter1
						.getPosition("Extreme Alert"));
			} else if (Config.alarmsoundtype.equalsIgnoreCase("Handy Alert")) {
				onloadfalse = "nottoplay";
				alarmtypespin.setSelection(dataAdapter1
						.getPosition("Handy Alert"));
			} else if (Config.alarmsoundtype.equalsIgnoreCase("Red Alert")) {
				onloadfalse = "nottoplay";
				alarmtypespin.setSelection(dataAdapter1
						.getPosition("Red Alert"));
			}
		} else {
			onloadfalse = "nottoplay";
			alarmtypespin.setSelection(dataAdapter1.getPosition("Red Alert"));
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, timingarr);

		dataAdapter
				.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

		savesett.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				if (mPlayer != null && mPlayer.isPlaying()) {
					mPlayer.stop();
				}

				AlertDialog alertDialog = new AlertDialog.Builder(Settings.this)
						.create();

				alertDialog.setTitle("INFO!");

				alertDialog.setMessage("Settings saved.");

				alertDialog.setIcon(R.drawable.tick);

				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {

							}
						});

				alertDialog.show();

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

	public static boolean deleteDir(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
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
			overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (mPlayer != null && mPlayer.isPlaying()) {
			mPlayer.stop();
		}
		if (!onloadfalse.equalsIgnoreCase("nottoplay")) {
			if (arg2 == 0) {
				onloadfalse="changed";
				mPlayer = MediaPlayer.create(getApplicationContext(),
						R.raw.type1);
				mPlayer.start();
			}
			if (arg2 == 1) {
				onloadfalse="changed";
				mPlayer = MediaPlayer.create(getApplicationContext(),
						R.raw.type2);
				mPlayer.start();
			}
			if (arg2 == 2) {
				onloadfalse="changed";
				mPlayer = MediaPlayer.create(getApplicationContext(),
						R.raw.type3);
				mPlayer.start();
			}
			if (arg2 == 3) {
				onloadfalse="changed";
				mPlayer = MediaPlayer.create(getApplicationContext(),
						R.raw.type4);
				mPlayer.start();
			}
			if (arg2 == 4) {
				onloadfalse="changed";
				mPlayer = MediaPlayer.create(getApplicationContext(),
						R.raw.alarmtone);
				mPlayer.start();
			}

		}

		System.out.println("selected postion::" + arg2);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		System.out.println("item clicked two");
	}
}