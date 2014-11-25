package com.deemsysinc.gpsmobiletracking;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class Settings extends Activity implements OnItemSelectedListener {
	Spinner refreshtiming, alarmtypespin;
	Button savesett;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		savesett = (Button) findViewById(R.id.savesettings);

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
		refreshtiming = (Spinner) findViewById(R.id.timing);
		alarmtypespin = (Spinner) findViewById(R.id.alarmtype);

		SpinnerAdapterCustom myAdapter = new SpinnerAdapterCustom(this,
				android.R.layout.simple_spinner_item, timingarr);
		refreshtiming.setAdapter(myAdapter);
		SpinnerAdapterCustom myAdapter1 = new SpinnerAdapterCustom(this,
				android.R.layout.simple_spinner_item, alarmtype);
		alarmtypespin.setAdapter(myAdapter1);
		savesett.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				Config.refreshtime = refreshtiming.getSelectedItem().toString();
				Config.alarmsoundtype = alarmtypespin.getSelectedItem()
						.toString();
				System.out.println("refresh time" + Config.refreshtime);
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
}