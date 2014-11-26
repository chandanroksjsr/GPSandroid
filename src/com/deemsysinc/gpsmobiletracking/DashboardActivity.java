package com.deemsysinc.gpsmobiletracking;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActivityManager.RunningServiceInfo;

import android.app.ProgressDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;

import android.graphics.drawable.BitmapDrawable;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

public class DashboardActivity extends Activity implements AnimationListener {
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	public static ArrayList<String> vehiclelist = new ArrayList<String>();
	public static List<Vehicle> vehicleall = new ArrayList<Vehicle>();
	JSONArray user = null;
	private static final String TAG_VEHICLE_ARRAY = "Vehicle List";
	private static final String TAG_SRES = "serviceresponse";
	private static final String TAG_ORGID = "org_id1";
	private static final String TAG_Vehicle_regno = "vechicle_reg_no";

	private static final String TAG_drivername = "driver_name";

	private static final String TAG_route_no = "route_no";
	private static final String TAG_driver_status = "device_status";
	private static final String TAG_Date = "bus_tracking_timestamp";
	private static final String TAG_ADDRS = "address";
	private static final String TAG_SPEED = "speed";
	private static final String TAG_CHECK_ALARM = "checkalarm";
	public ProgressDialog cDialog;
	String org, checkalarm;
	TextView welcome;
	static String vehicle_regno;
	String device_ime;
	String drivername;
	String driver_license;
	String license_expiry;
	String route_num;
	String driverstatus;
	String timestamp;
	String address;
	String speed;
	ListView lstvw;
	Context context = this;
	ListView list2;
	TextView welcomeusername;
	Button aboutus, contactus, signout, btnclose;
	String isalready;
	LinearLayout linear;
	Animation animMove;

	private static String vehicledetailsurl = Config.ServerUrl
			+ "VehicleDetails.php?service=vehicledetails1";

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().setTitle(
				Html.fromHtml("<font color='#ffffff'>Dashboard</font>"));

		getActionBar().setBackgroundDrawable(
				new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
						R.drawable.actionbarbg)));

		setContentView(R.layout.dashboard);

		linear = (LinearLayout) findViewById(R.id.lin);

		btnclose = (Button) findViewById(R.id.btnclose);
		welcomeusername = (TextView) findViewById(R.id.welcomeusername);
		welcomeusername.setText("Welcome  "+ Config.username +"!");
		final Animation animBounce1 = AnimationUtils.loadAnimation(this,
				R.anim.newbounce);

		animMove = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.move);

		animMove.setAnimationListener(this);

		animBounce1.setAnimationListener(this);

		isalready = getIntent().getExtras().getString("isalreadylogged");
		System.out.println("value of isalready::" + isalready);
		if (isalready.equalsIgnoreCase("notloggedin")
				&& Config.flag.equalsIgnoreCase("notloggedin")) {
			
			linear.startAnimation(animBounce1);
		}
		else{
			linear.setVisibility(View.INVISIBLE);
		}
		btnclose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				linear.startAnimation(animMove);

			}
		});
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			new VehicleDetails().execute();
		} else {
			AlertDialog alertDialog = new AlertDialog.Builder(
					DashboardActivity.this).create();

			alertDialog.setTitle("INFO!");

			alertDialog.setMessage("No network connection.");

			alertDialog.setIcon(R.drawable.delete);

			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

				public void onClick(final DialogInterface dialog,
						final int which) {

				}
			});

			alertDialog.show();
		}
		boolean a = isMyServiceRunning(BackgroundService.class);
		System.out.println("value of a::" + a);
		if (!a) {
			Intent ii = new Intent(DashboardActivity.this,
					BackgroundService.class);
			ii.putExtra("name", "SurvivingwithAndroid");
			DashboardActivity.this.startService(ii);
		}
		list2 = (ListView) findViewById(R.id.list);
		aboutus = (Button) findViewById(R.id.aboutus);
		contactus = (Button) findViewById(R.id.contactus);

	}

	class VehicleDetails extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			cDialog = new ProgressDialog(DashboardActivity.this);
			cDialog.setMessage("Please wait...");
			cDialog.setIndeterminate(false);
			cDialog.setCancelable(false);
			cDialog.show();
		}

		@Override
		protected void onPostExecute(String file_url) {

			super.onPostExecute(file_url);
			list2.setAdapter(new VehichleArrayAdapter(DashboardActivity.this,
					vehicleall, R.layout.vehiclelist));
			list2.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					System.out.println("in item click" + arg2);
					String item = vehicleall.get(arg2).getvehicle_regno();
					String regno = vehicleall.get(arg2).getroute_num();
					String driver_name = vehicleall.get(arg2).getdrivername();

					System.out
							.println("Position passed from dashboard activity:::"
									+ item);
					System.out
							.println("Position passed from dashboard activity:::"
									+ regno);
					System.out
							.println("Position passed from dashboard activity driver name:::"
									+ driver_name);
					Intent i = new Intent(DashboardActivity.this,
							LiveTrack.class);

					i.putExtra("vehicleregnum", item);
					i.putExtra("routenum", regno);
					i.putExtra("drivername", driver_name);
					startActivity(i);
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
				}
			});

		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			VehichleArrayAdapter.data.clear();
			vehicleall.clear();
			vehicleall.clear();
			vehiclelist.clear();
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("org_id", Config.org_id));

			jArray = jsonParser.makeHttpRequest(vehicledetailsurl, "POST",
					params1);

			Log.i("tagconvertstr", "[" + jArray + "]");

			try {
				if (jArray != null) {

					JSONObject c = jArray.getJSONObject(TAG_SRES);
					Log.i("tagconvertstr", "[" + c + "]");
					user = c.getJSONArray(TAG_VEHICLE_ARRAY);
					Log.i("tagconvertstr1", "[" + user + "]");

					for (int i = 0; i < user.length(); i++) {
						System.out.println("forloop1");
						JSONObject c1 = user.getJSONObject(i);
						JSONObject c2 = c1.getJSONObject(TAG_SRES);
						org = c2.getString(TAG_ORGID);

						vehicle_regno = c2.getString(TAG_Vehicle_regno);
						// device_ime= c2.getString(TAG_Device_imei);
						drivername = c2.getString(TAG_drivername);
						// driver_license = c2.getString(TAG_driver_license_no);
						// license_expiry=c2.getString(TAG_licence_expdate);
						route_num = c2.getString(TAG_route_no);
						driverstatus = c2.getString(TAG_driver_status);
						timestamp = c2.getString(TAG_Date);
						address = c2.getString(TAG_ADDRS);
						speed = c2.getString(TAG_SPEED);
						checkalarm = c2.getString(TAG_CHECK_ALARM);
						vehiclelist.add(vehicle_regno);
						// vehiclelist.add(device_ime);
						vehiclelist.add(drivername);
						// vehiclelist.add(driver_license);
						// vehiclelist.add(license_expiry);
						// vehiclelist.add(route_num);
						vehiclelist.add(driverstatus);
						Vehicle cnt = new Vehicle(vehicle_regno, device_ime,
								drivername, driver_license, license_expiry,
								route_num, driverstatus, timestamp, address,
								speed);
						cnt.setvehicle_regno(vehicle_regno);
						// cnt.setdevice_ime(device_ime);
						cnt.setdrivername(drivername);
						// cnt.setdriver_license(driver_license);
						cnt.setroute_num(route_num);
						// cnt.setdate(license_expiry);
						cnt.setdriverstatus(driverstatus);
						cnt.setaddress(address);
						cnt.settimestamp(timestamp);
						cnt.setalarm(checkalarm);
						cnt.setspeed(speed);
						vehicleall.add(cnt);
						// int a=vehiclelist.size();
						System.out.println("size of aray list" + vehicleall);

					}

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			cDialog.dismiss();
			return null;
		}

	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.profile:
			Intent myIntent0 = new Intent(DashboardActivity.this,
					ProfileActivity.class);
			DashboardActivity.this.startActivity(myIntent0);
			overridePendingTransition(R.anim.pushdown, R.anim.pushup);
			return true;
		case R.id.aboutus:
			Intent myIntent = new Intent(DashboardActivity.this,
					AboutActivity.class);
			DashboardActivity.this.startActivity(myIntent);
			overridePendingTransition(R.anim.pushdown, R.anim.pushup);
			return true;
		case R.id.contactus:
			Intent myIntent1 = new Intent(DashboardActivity.this,
					ContactUs.class);
			DashboardActivity.this.startActivity(myIntent1);
			overridePendingTransition(R.anim.pushdown, R.anim.pushup);
			return true;
		case R.id.settings:
			Intent myIntent11 = new Intent(DashboardActivity.this,
					Settings.class);
			DashboardActivity.this.startActivity(myIntent11);
			overridePendingTransition(R.anim.pushdown, R.anim.pushup);
			return true;
		case R.id.logout:
			Config.username = "";
			VehichleArrayAdapter.data.clear();
			vehicleall.clear();
			SharedPreferences settings = getApplicationContext()
					.getSharedPreferences("MyPrefs0",
							getApplicationContext().MODE_PRIVATE);
			settings.edit().clear().commit();
			Intent ii = new Intent(DashboardActivity.this,
					BackgroundService.class);
			ii.putExtra("name", "SurvivingwithAndroid");
			DashboardActivity.this.stopService(ii);
			Intent myIntent2 = new Intent(DashboardActivity.this,
					LoginActivity.class);
			DashboardActivity.this.startActivity(myIntent2);
			overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
			return true;
		case android.R.id.home:

			finish();

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onAnimationEnd(Animation animation) {

		
		if (animation == animMove) {

		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {




	}

	@Override
	public void onAnimationStart(Animation animation) {



	}
}