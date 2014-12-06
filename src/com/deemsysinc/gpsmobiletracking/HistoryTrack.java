package com.deemsysinc.gpsmobiletracking;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.PolylineOptions;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.ActionBar.OnNavigationListener;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.graphics.drawable.BitmapDrawable;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

@SuppressLint("SimpleDateFormat")
public class HistoryTrack extends Activity implements OnMapLongClickListener,
		AnimationListener {
	String[] arr = new String[] { "CC0033", "FF0033", "330066", "990033",
			"660000", "00FFFF", "FFFF00", "FF3300", "99FF66", "FF0099",
			"663399", "000000", "9933CC", "3366CC", "3300FF", "0000CC",
			"009999", "993366", "" };
	String sleep = "no", checksleepflag = "no";
	double distance, totaldistance = 0;
	LinearLayout com;
	RadioButton maprad, satrad;
	private int year;
	private int month;
	private int day;
	String checkdate = "empty";
	String vehicle_reg_numb;
	static final int DATE_PICKER_ID = 1111;
	static final int TIME_PICKER_ID = 100;
	static final int TIME_PICKER_ID1 = 101;
	StringBuilder date;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	public ProgressDialog cDialog;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	JSONArray user = null;
	Marker marker;
	private GoogleMap googleMap;
	TextView welcomeusername, welcome, distancetravelled;
	Button signout, hmey, close;
	ToggleButton tgbutton;
	Button fromtime, totime, submit, datebutton;
	final Context context = this;
	public static ArrayList<HashMap<String, String>> vehiclehistory1 = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map = new HashMap<String, String>();
	HashMap<String, Double> map1 = new HashMap<String, Double>();
	private static final String TAG_SRES = "serviceresponse";
	private static final String TAG_VEHICLE_ARRAY = "VehicleHistory List";
	static final String TAG_Vechicle_REG = "vechicle_reg_no";
	private static final String TAG_Latitude = "latitude";
	private static final String TAG_Longitude = "longitude";
	private static final String TAG_Speed = "speed";
	private static final String TAG_Exceed_Speed = "exceed_speed_limit";
	private static final String TAG_bus_tracking_timestamp = "bus_tracking_timestamp";
	private static final String TAG_address = "address";
	TextView vehicle_own, reg_no;
	static final LatLng TutorialsPoint = new LatLng(22.3512639, 78.9542827);
	String orgid, vehicle_reg_no, speed, exceed_speed_limit,
			bus_tracking_timestamp, address;
	String latitude;
	String longitude;
	double latitude1;
	double longitude1;
	private int pHour;
	private int pMinute;
	LinearLayout linear;
	Button btn, clobtn;
	Animation animSlideUp, animSlideDown;

	private static String vehiclehistorysurll = Config.ServerUrl
			+ "HistoryTrack.php?service=vehiclehistory";

	class MyInfoWindowAdapter implements InfoWindowAdapter {

		private final View myContentsView;

		MyInfoWindowAdapter() {
			myContentsView = getLayoutInflater().inflate(
					R.layout.custom_onfo_window, null);
		}

		@Override
		public View getInfoContents(Marker marker) {
			TextView tvTitle = ((TextView) myContentsView
					.findViewById(R.id.title));
			tvTitle.setText(marker.getTitle());
			TextView tvSnippet = ((TextView) myContentsView
					.findViewById(R.id.snippet));
			tvSnippet.setText(marker.getSnippet());

			return myContentsView;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historytrack);

		linear = (LinearLayout) findViewById(R.id.linear);
		ActionBar actions = getActionBar();
		getActionBar().setBackgroundDrawable(
				new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
						R.drawable.actionbarbg)));

		actions.setIcon(R.drawable.histroyicon);
		actions.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actions.setDisplayShowTitleEnabled(false);

		animSlideUp = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_up);
		animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_down);

		animSlideUp.setAnimationListener(this);
		animSlideDown.setAnimationListener(this);
		com = (LinearLayout) findViewById(R.id.commonlinear);
		maprad = (RadioButton) findViewById(R.id.radiomap);
		satrad = (RadioButton) findViewById(R.id.radiosatellite);
		reg_no = (TextView) findViewById(R.id.vehicle_reg_number);
		vehicle_own = (TextView) findViewById(R.id.vehicleowner);
		distancetravelled = (TextView) findViewById(R.id.distancetravelled);
		reg_no.setText(LiveTrack.vehicle_reg_no);
		vehicle_own.setText(LiveTrack.driver_name);
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioButton rb = (RadioButton) v;
				String selectedid = rb.getText().toString();
				if (selectedid.equalsIgnoreCase("map")) {
					maprad.setEnabled(false);
					satrad.setEnabled(true);
					try {
						if (googleMap == null) {
							googleMap = ((MapFragment) getFragmentManager()
									.findFragmentById(R.id.map)).getMap();
						}
						googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
						// tgbutton.setBackgroundResource(R.drawable.earth);

						marker = googleMap.addMarker(new MarkerOptions()
								.position(TutorialsPoint).title(""));
						CameraPosition cameraPosition = new CameraPosition.Builder()
								.target(TutorialsPoint).zoom(4).build();
						googleMap.animateCamera(CameraUpdateFactory
								.newCameraPosition(cameraPosition));
						marker.remove();
						marker.setVisible(false);
						marker.setVisible(false);

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						if (googleMap == null) {
							googleMap = ((MapFragment) getFragmentManager()
									.findFragmentById(R.id.map)).getMap();
						}
						googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
						// tgbutton.setBackgroundResource(R.drawable.aerial);
						marker = googleMap.addMarker(new MarkerOptions()
								.position(TutorialsPoint).title(""));
						CameraPosition cameraPosition = new CameraPosition.Builder()
								.target(TutorialsPoint).zoom(4).build();
						googleMap.animateCamera(CameraUpdateFactory
								.newCameraPosition(cameraPosition));
						marker.remove();
						marker.setVisible(false);

					} catch (Exception e) {
						e.printStackTrace();
					}
					maprad.setEnabled(true);
					satrad.setEnabled(false);

				}

			}
		};
		close = (Button) findViewById(R.id.close);
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				YoYo.with(Techniques.RollOut).duration(200).playOn(com);
				com.setVisibility(View.GONE);

			}
		});
		maprad.setOnClickListener(listener);
		satrad.setOnClickListener(listener);
		if (Config.role.equalsIgnoreCase("ROLE_FCLIENT")
				|| Config.role.equalsIgnoreCase("ROLE_PCLIENT")) {
			SpinnerAdapter adapter1 = ArrayAdapter.createFromResource(
					getActionBar().getThemedContext(),
					R.array.nav_drawer_items1_withoutalert,
					android.R.layout.simple_spinner_dropdown_item);

			OnNavigationListener callback = new OnNavigationListener() {

				String[] items = getResources().getStringArray(
						R.array.nav_drawer_items); // List items from res

				@Override
				public boolean onNavigationItemSelected(int itemPosition,
						long id) {

					Intent myIntent;
					if (itemPosition != 0) {
						if (itemPosition == 0) { // Activity#1 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(HistoryTrack.this,
									HistoryTrack.class);
							HistoryTrack.this.startActivity(myIntent);
						} else if (itemPosition == 1) { // Activity#2 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(HistoryTrack.this,
									LiveTrack.class);
							myIntent.putExtra("vehicleregnum",
									LiveTrack.vehicle_reg_no);
							myIntent.putExtra("drivername",
									LiveTrack.driver_name);
							myIntent.putExtra("devicestatus",
									LiveTrack.devicestatus);
							myIntent.putExtra("routenum", LiveTrack.routeno);
							HistoryTrack.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						} else if (itemPosition == 2) { // Activity#3 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(HistoryTrack.this,
									TheftAlarm.class);
							HistoryTrack.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						} else if (itemPosition == 3) { // Activity#3 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(HistoryTrack.this,
									OverSpeed.class);
							HistoryTrack.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						} else if (itemPosition == 4) { // Activity#3 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(HistoryTrack.this,
									DashboardActivity.class);
							Config.flag = "alreadyloggedin";
							myIntent.putExtra("isalreadylogged", Config.flag);
							HistoryTrack.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						}

					} else {

					}
					return true;

				}

			};

			actions.setListNavigationCallbacks(adapter1, callback);
		} else {

			SpinnerAdapter adapter = ArrayAdapter.createFromResource(
					getActionBar().getThemedContext(),
					R.array.nav_drawer_items1,
					android.R.layout.simple_spinner_dropdown_item);

			// Callback
			OnNavigationListener callback = new OnNavigationListener() {

				// String[] items = getResources().getStringArray(
				// R.array.nav_drawer_items1); // List items from res

				@Override
				public boolean onNavigationItemSelected(int itemPosition,
						long id) {

					Intent myIntent;
					if (itemPosition != 0) {
						if (itemPosition == 0) { // Activity#1 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(HistoryTrack.this,
									HistoryTrack.class);
							HistoryTrack.this.startActivity(myIntent);
						} else if (itemPosition == 1) { // Activity#2 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(HistoryTrack.this,
									LiveTrack.class);
							myIntent.putExtra("vehicleregnum",
									LiveTrack.vehicle_reg_no);
							myIntent.putExtra("routenum", LiveTrack.routeno);
							myIntent.putExtra("drivername",
									LiveTrack.driver_name);
							myIntent.putExtra("devicestatus",
									LiveTrack.devicestatus);
							HistoryTrack.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						} else if (itemPosition == 2) { // Activity#3 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(HistoryTrack.this,
									AlertMsg.class);
							HistoryTrack.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						} else if (itemPosition == 3) { // Activity#3 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(HistoryTrack.this,
									DashboardActivity.class);
							Config.flag = "alreadyloggedin";
							myIntent.putExtra("isalreadylogged", Config.flag);
							HistoryTrack.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						}

					} else {

					}
					return true;

				}

			};
			actions.setListNavigationCallbacks(adapter, callback);
		}
		datebutton = (Button) findViewById(R.id.date);
		fromtime = (Button) findViewById(R.id.fromtime);
		totime = (Button) findViewById(R.id.totime);
		fromtime.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				showDialog(TIME_PICKER_ID);
			}
		});

		totime.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				showDialog(TIME_PICKER_ID1);
			}
		});
		datebutton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				showDialog(DATE_PICKER_ID);
			}
		});

		submit = (Button) findViewById(R.id.go);
		submit.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				isInternetPresent = cd.isConnectingToInternet();
				System.out.println("check date in go::"
						+ datebutton.getText().toString());
				if (!datebutton.getText().toString().equalsIgnoreCase("Date")) {
					System.out.println("first if");
					if (!fromtime.getText().toString()
							.equalsIgnoreCase("From Time"))// has value 12:12:12
					{
						System.out.println("second if");

						if (!totime.getText().toString()
								.equalsIgnoreCase("To Time")) {
							System.out.println("third if");
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm");
							try {
								Date date1 = sdf.parse(datebutton.getText()
										.toString()
										+ " "
										+ fromtime.getText().toString());
								Date date2 = sdf.parse(datebutton.getText()
										.toString()
										+ " "
										+ totime.getText().toString());

								System.out.println("date values 1" + date1);
								System.out.println("date values 2" + date2);
								if (date1.compareTo(date2) > 0) {
									AlertDialog alertDialog = new AlertDialog.Builder(
											HistoryTrack.this).create();

									// Setting Dialog Title
									alertDialog.setTitle("INFO!");

									// Setting Dialog Message
									alertDialog
											.setMessage("To time must greater than from time.");

									// Setting Icon to Dialog
									alertDialog.setIcon(R.drawable.delete);

									// Setting OK Button
									alertDialog
											.setButton(
													"OK",
													new DialogInterface.OnClickListener() {

														public void onClick(
																final DialogInterface dialog,
																final int which) {
															// Write your code
															// here to execute
															// after dialog
															// closed

														}
													});

									// Showing Alert Message
									alertDialog.show();
								} else if (date1.compareTo(date2) < 0) {
									linear.setVisibility(View.GONE);
									linear.startAnimation(animSlideUp);
									if (isInternetPresent) {
										vehiclehistory1.clear();

										new VehiclePath().execute();
									} else {
										AlertDialog alertDialog = new AlertDialog.Builder(
												HistoryTrack.this).create();

										alertDialog.setTitle("INFO!");

										alertDialog
												.setMessage("No network connection.");

										alertDialog.setIcon(R.drawable.delete);

										alertDialog
												.setButton(
														"OK",
														new DialogInterface.OnClickListener() {

															public void onClick(
																	final DialogInterface dialog,
																	final int which) {

															}
														});

										alertDialog.show();
									}
								} else if (date1.compareTo(date2) == 0) {
									linear.setVisibility(View.GONE);
									linear.startAnimation(animSlideUp);
									if (isInternetPresent) {
										vehiclehistory1.clear();
										new VehiclePath().execute();
									} else {
										AlertDialog alertDialog = new AlertDialog.Builder(
												HistoryTrack.this).create();

										alertDialog.setTitle("INFO!");

										alertDialog
												.setMessage("No network connection.");

										alertDialog.setIcon(R.drawable.delete);

										alertDialog
												.setButton(
														"OK",
														new DialogInterface.OnClickListener() {

															public void onClick(
																	final DialogInterface dialog,
																	final int which) {

															}
														});

										alertDialog.show();
									}
								} else {
									System.out.println("How to get here?");
								}
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							totime.setText(" 23:59:59");
							linear.setVisibility(View.GONE);
							linear.startAnimation(animSlideUp);
							if (isInternetPresent) {
								vehiclehistory1.clear();
								new VehiclePath().execute();
							} else {
								AlertDialog alertDialog = new AlertDialog.Builder(
										HistoryTrack.this).create();

								alertDialog.setTitle("INFO!");

								alertDialog
										.setMessage("No network connection.");

								alertDialog.setIcon(R.drawable.delete);

								alertDialog.setButton("OK",
										new DialogInterface.OnClickListener() {

											public void onClick(
													final DialogInterface dialog,
													final int which) {

											}
										});

								alertDialog.show();
							}
						}
					} else if (!totime.getText().toString()
							.equalsIgnoreCase("To Time")) // has value 12:12:12
					{
						System.out.println("first else if");
						fromtime.setText(" 00:00:00");
						linear.setVisibility(View.GONE);
						linear.startAnimation(animSlideUp);
						if (isInternetPresent) {
							vehiclehistory1.clear();
							new VehiclePath().execute();
						} else {
							AlertDialog alertDialog = new AlertDialog.Builder(
									HistoryTrack.this).create();

							alertDialog.setTitle("INFO!");

							alertDialog.setMessage("No network connection.");

							alertDialog.setIcon(R.drawable.delete);

							alertDialog.setButton("OK",
									new DialogInterface.OnClickListener() {

										public void onClick(
												final DialogInterface dialog,
												final int which) {

										}
									});

							alertDialog.show();
						}
					} else {
						System.out.println("second else if");
						fromtime.setText(" 00:00:00");
						totime.setText(" 23:59:59");
						linear.setVisibility(View.GONE);
						linear.startAnimation(animSlideUp);
						if (isInternetPresent) {
							vehiclehistory1.clear();
							new VehiclePath().execute();
						} else {
							AlertDialog alertDialog = new AlertDialog.Builder(
									HistoryTrack.this).create();

							alertDialog.setTitle("INFO!");

							alertDialog.setMessage("No network connection.");

							alertDialog.setIcon(R.drawable.delete);

							alertDialog.setButton("OK",
									new DialogInterface.OnClickListener() {

										public void onClick(
												final DialogInterface dialog,
												final int which) {

										}
									});

							alertDialog.show();
						}
					}

				} else {
					AlertDialog alertDialog = new AlertDialog.Builder(
							HistoryTrack.this).create();

					alertDialog.setTitle("INFO!");

					alertDialog.setMessage("Select date.");

					alertDialog.setIcon(R.drawable.delete);

					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {

								public void onClick(
										final DialogInterface dialog,
										final int which) {

								}
							});

					// Showing Alert Message
					alertDialog.show();
				}
			}
		});

		// welcome = (TextView) findViewById(R.id.textView1);
		// welcomeusername = (TextView) findViewById(R.id.welcomename);
		// welcomeusername.setText(Config.username + "!");
		// welcomeusername.setTypeface(null, Typeface.BOLD);
		// welcome.setTypeface(null, Typeface.BOLD);

		try {
			if (googleMap == null) {

				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();
			}
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			marker = googleMap.addMarker(new MarkerOptions().position(
					TutorialsPoint).title(""));
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(TutorialsPoint).zoom(4).build();
			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
			googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
			marker.remove();
			marker.setVisible(false);

			googleMap.getUiSettings().setZoomGesturesEnabled(true);
			googleMap.getUiSettings().setRotateGesturesEnabled(true);
			googleMap.getUiSettings().setCompassEnabled(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		vehicle_reg_numb = LiveTrack.vehicle_reg_no;
		// System.out.println("history track veh numb"+vehicle_reg_numb);
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		pHour = c.get(Calendar.HOUR_OF_DAY);
		pMinute = c.get(Calendar.MINUTE);

		initilizeMap();

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_PICKER_ID:

			return new DatePickerDialog(this, pickerListener, year, month, day);
		case TIME_PICKER_ID:

			return new TimePickerDialog(this, mTimeSetListener, pHour, pMinute,
					false);

		case TIME_PICKER_ID1:

			return new TimePickerDialog(this, mTimeSetListener1, pHour,
					pMinute, false);

		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			pHour = hourOfDay;
			pMinute = minute;
			String time = pHour + ":" + pMinute;
			fromtime.setText(time);

		}
	};
	private TimePickerDialog.OnTimeSetListener mTimeSetListener1 = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			pHour = hourOfDay;
			pMinute = minute;
			String time = pHour + ":" + pMinute;
			totime.setText(time);

		}
	};
	private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			// int noOfTimesCalled = 0;
			// if (view.isShown()) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			String date1 = year + "-" + checkDigit(month + 1) + "-"
					+ checkDigit(day);
			// checkdate = date1.toString();
			datebutton.setText(date1.toString());
			System.out.println("check date value::" + date1.toString());

		}

	};

	public String checkDigit(int number) {
		return number <= 9 ? "0" + number : String.valueOf(number);
	}

	class VehiclePath extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			cDialog = new ProgressDialog(HistoryTrack.this);
			cDialog.setMessage("Please wait...");
			cDialog.setIndeterminate(false);
			cDialog.setCancelable(false);
			cDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			ArrayList<HashMap<String, String>> vehiclehistory = new ArrayList<HashMap<String, String>>();
			params1.add(new BasicNameValuePair("org_id", Config.org_id));
			params1.add(new BasicNameValuePair("vechicle_reg_no",
					vehicle_reg_numb));
			params1.add(new BasicNameValuePair("date1", datebutton.getText()
					.toString() + " " + fromtime.getText().toString()));
			params1.add(new BasicNameValuePair("date2", datebutton.getText()
					.toString() + " " + totime.getText().toString()));

			jArray = jsonParser.makeHttpRequest(vehiclehistorysurll, "POST",
					params1);

			try {
				if (jArray != null) {

					JSONObject c = jArray.getJSONObject(TAG_SRES);

					user = c.getJSONArray(TAG_VEHICLE_ARRAY);

					for (int i = 0; i < user.length(); i++) {

						JSONObject c1 = user.getJSONObject(i);
						JSONObject c2 = c1.getJSONObject(TAG_SRES);

						vehicle_reg_numb = c2.getString(TAG_Vechicle_REG);
						latitude = c2.getString(TAG_Latitude);
						longitude = c2.getString(TAG_Longitude);
						speed = c2.getString(TAG_Speed);
						exceed_speed_limit = c2.getString(TAG_Exceed_Speed);
						bus_tracking_timestamp = c2
								.getString(TAG_bus_tracking_timestamp);
						address = c2.getString(TAG_address);
						map.put(TAG_Latitude + i, latitude);
						map.put(TAG_Longitude + i, longitude);
						map.put(TAG_Speed + i, speed);
						map.put(TAG_Exceed_Speed + i, exceed_speed_limit);
						map.put(TAG_address + i, address);
						map.put(TAG_bus_tracking_timestamp + i,
								bus_tracking_timestamp);

						vehiclehistory.add(i, map);

					}

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			vehiclehistory1 = vehiclehistory;
			cDialog.dismiss();
			return null;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {

			super.onPostExecute(file_url);
			cDialog.dismiss();
			try {
				ArrayList<LatLng> points = null;
				PolylineOptions polyLineOptions = null;
				points = new ArrayList<LatLng>();
				polyLineOptions = new PolylineOptions();
				googleMap.clear();
				if (vehiclehistory1.size() == 0) {
					distancetravelled.setText("");
					AlertDialog alertDialog = new AlertDialog.Builder(
							HistoryTrack.this).create();

					alertDialog.setTitle("INFO!");

					alertDialog.setMessage("No location's found.");

					alertDialog.setIcon(R.drawable.delete);

					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {

								public void onClick(
										final DialogInterface dialog,
										final int which) {

								}
							});

					alertDialog.show();

				} else {

					System.out.println("size of list::"
							+ vehiclehistory1.size());
					for (int k = 0; k < vehiclehistory1.size(); k++) {

						LatLng pinLocation = new LatLng(
								Double.parseDouble(vehiclehistory1.get(k).get(
										TAG_Latitude + k)),
								Double.parseDouble(vehiclehistory1.get(k).get(
										TAG_Longitude + k)));

						points.add(pinLocation);
						String titlevalue = "Speed:"
								+ vehiclehistory1.get(k).get(TAG_Speed + k)
								+ " km/hr "
								+ "Date:"
								+ vehiclehistory1.get(k).get(
										TAG_bus_tracking_timestamp + k);
						String snippetval = titlevalue + "\n" + "Address:"
								+ vehiclehistory1.get(k).get(TAG_address + k);

						if (k == 0 || k == vehiclehistory1.size() - 1) {

							System.out.println("in last point");
							marker = googleMap
									.addMarker(new MarkerOptions()
											.position(pinLocation)

											.snippet(snippetval)
											.icon(BitmapDescriptorFactory
													.fromResource(R.drawable.startpoint)));
						}

						else if (vehiclehistory1.get(k)
								.get(TAG_Exceed_Speed + k).equals("1")) {
							marker = googleMap
									.addMarker(new MarkerOptions()
											.position(pinLocation)

											.snippet(snippetval)
											.icon(BitmapDescriptorFactory
													.fromResource(R.drawable.pink_pin)));

						}

						else {
							if (k == vehiclehistory1.size() - 1) {
								if (marker.getPosition().equals(pinLocation)) {

									marker.remove();
									marker = googleMap
											.addMarker(new MarkerOptions()
													.position(pinLocation)

													.snippet(snippetval)
													.icon(BitmapDescriptorFactory
															.fromResource(R.drawable.startpoint)));

								}
							} else {
								LatLng pinLocation1 = new LatLng(
										Double.parseDouble(vehiclehistory1.get(
												k).get(TAG_Latitude + k)),
										Double.parseDouble(vehiclehistory1.get(
												k).get(TAG_Longitude + k)));
								LatLng pinLocation2 = new LatLng(
										Double.parseDouble(vehiclehistory1.get(
												vehiclehistory1.size() - 1)
												.get(TAG_Latitude
														+ (vehiclehistory1
																.size() - 1))),
										Double.parseDouble(vehiclehistory1.get(
												vehiclehistory1.size() - 1)
												.get(TAG_Longitude
														+ (vehiclehistory1
																.size() - 1))));

//								System.out.println("k val::" + k);
//								System.out.println("in red");
								if (!pinLocation1.equals(pinLocation2)) {
									marker = googleMap
											.addMarker(new MarkerOptions()
													.position(pinLocation)

													.snippet(snippetval)
													.icon(BitmapDescriptorFactory
															.fromResource(R.drawable.red_pin)));
								}
							}

						}

						int sizeminusone = vehiclehistory1.size() - 1;
						if (sizeminusone == k) {
							marker = googleMap
									.addMarker(new MarkerOptions()
											.position(pinLocation)

											.snippet(snippetval)
											.icon(BitmapDescriptorFactory
													.fromResource(R.drawable.startpoint)));
							CameraPosition cameraPosition = new CameraPosition.Builder()
									.target(pinLocation).zoom(12).build();
							googleMap.animateCamera(CameraUpdateFactory
									.newCameraPosition(cameraPosition));
						}

					}

					polyLineOptions.addAll(points);
					polyLineOptions.width(2);
					polyLineOptions.color(Color.BLACK);
					googleMap.addPolyline(polyLineOptions);

				}
				for (int o = 0; o < vehiclehistory1.size() - 1; o++) {

					totaldistance += distance(
							Double.parseDouble(vehiclehistory1.get(o).get(
									TAG_Latitude + o)),
							Double.parseDouble(vehiclehistory1.get(o + 1).get(
									TAG_Latitude + (o + 1))),
							Double.parseDouble(vehiclehistory1.get(o).get(
									TAG_Longitude + o)),
							Double.parseDouble(vehiclehistory1.get(o + 1).get(
									TAG_Longitude + (o + 1))));

					DecimalFormat df = new DecimalFormat("#.#####");
					totaldistance = Double.valueOf(df.format(totaldistance));

					distancetravelled.setText(String.valueOf(totaldistance)
							+ "Km (Approx.)");

				}
				totaldistance = 0.0;
			} catch (Exception e) {

			}
			if (vehiclehistory1.size() > 0) {
				LatLng pinLocation = new LatLng(
						Double.parseDouble(vehiclehistory1.get(0).get(
								TAG_Latitude + 0)),
						Double.parseDouble(vehiclehistory1.get(0).get(
								TAG_Longitude + 0)));
				String titlevalue = "Speed:"
						+ vehiclehistory1.get(0).get(TAG_Speed + 0)
						+ " km/hr "
						+ "Date:"
						+ vehiclehistory1.get(0).get(
								TAG_bus_tracking_timestamp + 0);
				String snippetval = titlevalue + "\n" + "Address:"
						+ vehiclehistory1.get(0).get(TAG_address + 0);
				if (marker.getPosition().equals(pinLocation)) {
					System.out.println("in last point");
					marker.remove();
				}
				marker = googleMap.addMarker(new MarkerOptions()
						.position(pinLocation)

						.snippet(snippetval)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.startpoint)));

				LatLng pinLocation1 = new LatLng(
						Double.parseDouble(vehiclehistory1.get(
								vehiclehistory1.size() - 1).get(
								TAG_Latitude + (vehiclehistory1.size() - 1))),
						Double.parseDouble(vehiclehistory1.get(
								vehiclehistory1.size() - 1).get(
								TAG_Longitude + (vehiclehistory1.size() - 1))));
				String titlevalue1 = "Speed:"
						+ vehiclehistory1.get(vehiclehistory1.size() - 1).get(
								TAG_Speed + (vehiclehistory1.size() - 1))
						+ " km/hr "
						+ "Date:"
						+ vehiclehistory1.get(vehiclehistory1.size() - 1).get(
								TAG_bus_tracking_timestamp
										+ (vehiclehistory1.size() - 1));
				String snippetval1 = titlevalue1
						+ "\n"
						+ "Address:"
						+ vehiclehistory1.get(vehiclehistory1.size() - 1).get(
								TAG_address + (vehiclehistory1.size() - 1));
				if (marker.getPosition().equals(pinLocation1)) {

					marker.remove();

				}

				marker = googleMap.addMarker(new MarkerOptions()
						.position(pinLocation1)

						.snippet(snippetval1)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.startpoint)));
			}

		}

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		moveTaskToBack(true);
	}

	@Override
	public void onLowMemory() {

		moveTaskToBack(true);
		// googleMap.onLowMemory();

	}

	private void initilizeMap() {
		try {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();
				Marker marker = googleMap.addMarker(new MarkerOptions()
						.position(TutorialsPoint).title(""));
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(TutorialsPoint).zoom(4).build();
				googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
				marker.remove();
				marker.setVisible(false);
				// check if map is created successfully or not
				if (googleMap == null) {
					Toast.makeText(getApplicationContext(),
							"Sorry! unable to create maps", Toast.LENGTH_SHORT)
							.show();
				}
			}
		} catch (Exception e) {

		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();

		// System.out.println("in on resume ");

	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public void onMapLongClick(LatLng point) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub

		// if (animation == animSlideUp) {
		// }

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.info:

			com.setVisibility(View.VISIBLE);

			YoYo.with(Techniques.RotateIn).duration(200).playOn(com);
			break;
		case R.id.history:

			linear.setVisibility(View.VISIBLE);
			linear.startAnimation(animSlideDown);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}

	private double distance(double lat1, double lat2, double lon1, double lon2) {

		final int R = 6371; // Radius of the earth
		Double latDistance = deg2rad(lat2 - lat1);
		Double lonDistance = deg2rad(lon2 - lon1);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c; // convert the meters
		return distance;

	}

	/*
	 * Helper function for distance calculation
	 */
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
}
