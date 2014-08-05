package com.deemsysinc.gpsmobiletracking;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Sample extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		    getActionBar().hide();
		setContentView(R.layout.sample);
	}
}
