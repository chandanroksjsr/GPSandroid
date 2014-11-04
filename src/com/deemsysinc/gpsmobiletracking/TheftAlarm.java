package com.deemsysinc.gpsmobiletracking;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.app.ActionBar.OnNavigationListener;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.SpinnerAdapter;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class TheftAlarm extends Activity {

	/** Called when the activity is first created. */

	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	public ProgressDialog pDialog;
	JsonParser jsonParser = new JsonParser();
	JSONObject jobject;
	Context context = this;
	String status;
	JSONArray number = null;
	JSONArray mobile = null;
	RadioButton on, off;

	private RadioGroup radioGroup1;
	String succ;
	private static final String TAG_SRES = "serviceresponse";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_STATUS = "status";
	static final String TAG_Vechicle_REG = "vechicle_reg_no";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theftalarm);
		ActionBar actions = getActionBar();
		actions.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#93aac3")));
		actions.setIcon(R.drawable.historyicon);
		actions.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actions.setDisplayShowTitleEnabled(false);
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			new CheckTheftAlarm().execute();
		}

		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		on = (RadioButton) findViewById(R.id.radiosubject);
		off = (RadioButton) findViewById(R.id.radiocourse);

		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioButton rb = (RadioButton) v;
				String selectedid = rb.getText().toString();
				if (selectedid.equalsIgnoreCase("on")) {
					on.setEnabled(false);
					off.setEnabled(true);
					if (isInternetPresent) {
						new insertTheftAlarm().execute();
					}
				} else {
					on.setEnabled(true);
					off.setEnabled(false);
					if (isInternetPresent) {
						new updateTheftAlarm().execute();
					}
				}

			}
		};
		on.setOnClickListener(listener);
		off.setOnClickListener(listener);

		if (LoginActivity.role.equalsIgnoreCase("ROLE_FCLIENT")) {
			SpinnerAdapter adapter1 = ArrayAdapter.createFromResource(
					getActionBar().getThemedContext(),
					R.array.nav_drawer_items2_withoutalert,
					android.R.layout.simple_spinner_dropdown_item);

			OnNavigationListener callback = new OnNavigationListener() {

				@Override
				public boolean onNavigationItemSelected(int itemPosition,
						long id) {

					Intent myIntent;
					if (itemPosition != 0) {
						if (itemPosition == 0) { // Activity#1 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(TheftAlarm.this,
									TheftAlarm.class);
							TheftAlarm.this.startActivity(myIntent);
						} else if (itemPosition == 1) { // Activity#2 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(TheftAlarm.this,
									LiveTrack.class);
							myIntent.putExtra("vehicleregnum",
									LiveTrack.vehicle_reg_no);
							myIntent.putExtra("routenum", LiveTrack.routeno);
							TheftAlarm.this.startActivity(myIntent);
						} else if (itemPosition == 2) { // Activity#3 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(TheftAlarm.this,
									HistoryTrack.class);
							TheftAlarm.this.startActivity(myIntent);
						} else if (itemPosition == 3) { // Activity#3 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(TheftAlarm.this,
									DashboardActivity.class);
							TheftAlarm.this.startActivity(myIntent);
						}

					} else {

					}
					return true;

				}

			};

			actions.setListNavigationCallbacks(adapter1, callback);
		}
		// radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener()
		// {
		// @Override
		// public void onCheckedChanged(RadioGroup group, int checkedId) {
		// switch (checkedId) {
		// case R.id.radiosubject:
		// new insertTheftAlarm().execute();
		// System.out.println("on clicked");
		// break;
		// case R.id.radiocourse:
		// new updateTheftAlarm().execute();
		//
		// System.out.println("off clicked");
		// break;
		//
		// default:
		// break;
		// }
		// }
		// });

	}

	class CheckTheftAlarm extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(TheftAlarm.this);
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("vechicle_reg_no",
					LiveTrack.vehicle_reg_no));
			params1.add(new BasicNameValuePair("org_id", LoginActivity.orgid));
			System.out.println("parameters" + params1);

			JSONObject json = ((JsonParser) jsonParser).makeHttpRequest(
					Config.ServerUrl + "TheftAlarm.php?service=presentstatus",
					"POST", params1);

			Log.i("tagconvertstr", "[" + jobject + "]");

			if (json != null) {
				try {
					if (json != null) {
						System.out.println("json value::" + json);

						JSONObject jUser = json.getJSONObject(TAG_SRES);
						status = jUser.getString(TAG_STATUS);

						System.out.println("status value" + status);

					}

				}

				catch (JSONException e) {
					e.printStackTrace();

				}
			} else {

			}

			pDialog.dismiss();
			return null;
		}

		@Override
		protected void onPostExecute(String file_url) {

			super.onPostExecute(file_url);

			if (status.equalsIgnoreCase("1")) {
				on.setChecked(true);
				// radioGroup1.check(R.id.radiosubject);
			} else if (status.equalsIgnoreCase("0")) {
				off.setChecked(true);
				// radioGroup1.check(R.id.radiocourse);
			} else {
				off.setChecked(true);
			}

		}

	}

	class updateTheftAlarm extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(TheftAlarm.this);
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("vechicle_reg_no",
					LiveTrack.vehicle_reg_no));
			params1.add(new BasicNameValuePair("org_id", LoginActivity.orgid));
			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl
					+ "TheftAlarm.php?service=updatealarm", "POST", params1);

			if (json != null) {
				try {
					if (json != null) {

						JSONObject jUser = json.getJSONObject(TAG_SRES);

					}

				}

				catch (JSONException e) {
					e.printStackTrace();

				}
			} else {

			}
			return null;
		}

		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);
			pDialog.dismiss();
			AlertDialog alertDialog = new AlertDialog.Builder(TheftAlarm.this)
					.create();

			// Setting Dialog Title
			alertDialog.setTitle("INFO!");

			// Setting Dialog Message
			alertDialog.setMessage("Theft alarm turned off.");

			// Setting Icon to Dialog
			alertDialog.setIcon(R.drawable.delete);

			// Setting OK Button
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

				public void onClick(final DialogInterface dialog,
						final int which) {
					// Write your code here to execute after dialog
					// closed

				}
			});

			// Showing Alert Message
			alertDialog.show();

		}
	}

	class insertTheftAlarm extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(TheftAlarm.this);
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			JsonParser jLogin = new JsonParser();
			params1.add(new BasicNameValuePair("vechicle_reg_no",
					LiveTrack.vehicle_reg_no));
			params1.add(new BasicNameValuePair("org_id", LoginActivity.orgid));
			JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl
					+ "TheftAlarm.php?service=insertalarm", "POST", params1);

			System.out.println("value for json::" + json);
			if (json != null) {
				try {
					if (json != null) {
						System.out.println("json value::" + json);

						JSONObject jUser = json.getJSONObject(TAG_SRES);
						succ = jUser.getString(TAG_SUCCESS);

						System.out.println("success value" + succ);

					}

				}

				catch (JSONException e) {
					e.printStackTrace();

				}
			} else {

			}

			return null;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);
			pDialog.dismiss();
			if (succ.equalsIgnoreCase("No")) {
				AlertDialog alertDialog = new AlertDialog.Builder(
						TheftAlarm.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("Can't reachable!");

				// Setting Dialog Message
				alertDialog
						.setMessage("Make sure the device is turned on. Try again!");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);

				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {
								// Write your code here to execute after dialog
								// closed

							}
						});

				// Showing Alert Message
				alertDialog.show();
			} else {
				AlertDialog alertDialog = new AlertDialog.Builder(
						TheftAlarm.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("INFO!");

				// Setting Dialog Message
				alertDialog.setMessage("Theft alarm turned on.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.delete);

				// Setting OK Button
				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {
								// Write your code here to execute after dialog
								// closed

							}
						});

				// Showing Alert Message
				alertDialog.show();
			}
		}
	}
}