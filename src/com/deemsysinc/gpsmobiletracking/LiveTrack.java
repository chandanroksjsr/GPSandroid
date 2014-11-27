package com.deemsysinc.gpsmobiletracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapsInitializer;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class LiveTrack extends Activity implements OnMapLongClickListener {
	RadioButton maprad, satrad;
	public static ArrayList<HashMap<String, String>> vehiclehistory1 = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> vehiclehistory = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> map = new HashMap<String, String>();
	HashMap<String, Double> map1 = new HashMap<String, Double>();
	AlertDialog alertDialog;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	public ProgressDialog cDialog;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	JSONArray user = null;
	String vehicle_reg_numb;
	TextView welcomeusername, drivernametext;
	Button signout, home, close;
	TextView welcome;
	static String driver_name;
	String succy;
	static String vehicle_reg_no1, routeno;
	String userrole;
	ToggleButton tgbutton;
	MarkerOptions marker, expmarker;
	static final LatLng TutorialsPoint = new LatLng(22.3512639, 78.9542827);
	private GoogleMap googleMap;
	public static Timer timer;
	static TimerTask doAsynchronousTask;
	final Context context = this;
	int delaytime;
	private static final String TAG_SRES = "serviceresponse";
	private static final String TAG_VEHICLE_ARRAY = "VehicleHistory List";
	static final String TAG_Vechicle_REG = "vechicle_reg_no";
	private static final String TAG_Latitude = "latitude";
	private static final String TAG_Longitude = "longitude";
	private static final String TAG_Speed = "speed";
	private static final String TAG_Exceed_Speed = "exceed_speed_limit";
	private static final String TAG_bus_tracking_timestamp = "bus_tracking_timestamp";
	private static final String TAG_address = "address";
	String orgid;
	static String vehicle_reg_no;
	String speed;
	String exceed_speed_limit;
	String bus_tracking_timestamp;
	String address;
	String latitude;
	String longitude;
	double latitude1;
	double longitude1;
	Boolean alertcheck;
	TextView timingsec, vehicle_own, reg_no;
	ProgressBar pb;
	LinearLayout lin1;
	RelativeLayout lin2;
	LinearLayout com;
	Animation animSlideUp;
	private static String vehicleliveurl = Config.ServerUrl
			+ "LiveTrack.php?service=livetrack";

	/** Called when the activity is first created. */
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
		setContentView(R.layout.livetrack);

		ActionBar actions = getActionBar();

		actions.setIcon(R.drawable.liveicon);
		getActionBar().setBackgroundDrawable(
				new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
						R.drawable.actionbarbg)));

		actions.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actions.setDisplayShowTitleEnabled(false);
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		lin1 = (LinearLayout) findViewById(R.id.livelin1);

		reg_no = (TextView) findViewById(R.id.vehicle_reg_number);
		vehicle_own = (TextView) findViewById(R.id.vehicleowner);
		lin2 = (RelativeLayout) findViewById(R.id.livelin2);
		com = (LinearLayout) findViewById(R.id.commonlinear);
		close = (Button) findViewById(R.id.close);

		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				YoYo.with(Techniques.RollOut).duration(200).playOn(com);
				com.setVisibility(View.GONE);

			}
		});

		alertDialog = new AlertDialog.Builder(LiveTrack.this).create();
		maprad = (RadioButton) findViewById(R.id.radiomap);
		satrad = (RadioButton) findViewById(R.id.radiosatellite);
		pb = (ProgressBar) findViewById(R.id.progressBarToday);
		pb.setMax(30);

		timingsec = (TextView) findViewById(R.id.timingsecs);

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

						Marker marker = googleMap.addMarker(new MarkerOptions()
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
						Marker marker = googleMap.addMarker(new MarkerOptions()
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
		maprad.setOnClickListener(listener);
		satrad.setOnClickListener(listener);

		if (Config.role.equalsIgnoreCase("ROLE_FCLIENT")) {
			SpinnerAdapter adapter1 = ArrayAdapter.createFromResource(
					getActionBar().getThemedContext(),
					R.array.nav_drawer_items_withoutalert,
					android.R.layout.simple_spinner_dropdown_item);

			// Callback
			OnNavigationListener callback = new OnNavigationListener() {

				// String[] items = getResources().getStringArray(
				// R.array.nav_drawer_items); // List items from res

				@Override
				public boolean onNavigationItemSelected(int itemPosition,
						long id) {

					Intent myIntent;
					if (itemPosition != 0) {
						if (itemPosition == 0) { // Activity#1 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(LiveTrack.this,
									LiveTrack.class);
							LiveTrack.this.startActivity(myIntent);
						} else if (itemPosition == 1) {
							LiveTrack.timer.cancel();
							vehiclehistory.clear();
							vehiclehistory1.clear();
							LiveTrack.doAsynchronousTask.cancel();// Activity#2
																	// Selected
							myIntent = new Intent(LiveTrack.this,
									HistoryTrack.class);
							LiveTrack.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						} else if (itemPosition == 2) {
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							VehichleArrayAdapter.data.clear();
							DashboardActivity.vehicleall.clear();
							vehiclehistory1.clear();
							vehiclehistory.clear();
							HistoryTrack.vehiclehistory1.clear();
							myIntent = new Intent(LiveTrack.this,
									TheftAlarm.class);
							LiveTrack.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						} else if (itemPosition == 3) {
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							VehichleArrayAdapter.data.clear();
							DashboardActivity.vehicleall.clear();
							vehiclehistory1.clear();
							vehiclehistory.clear();
							HistoryTrack.vehiclehistory1.clear();
							myIntent = new Intent(LiveTrack.this,
									OverSpeed.class);
							LiveTrack.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						} else if (itemPosition == 4) {
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							VehichleArrayAdapter.data.clear();
							DashboardActivity.vehicleall.clear();
							vehiclehistory1.clear();
							vehiclehistory.clear();
							HistoryTrack.vehiclehistory1.clear();
							myIntent = new Intent(LiveTrack.this,
									DashboardActivity.class);
							Config.flag = "alreadyloggedin";
							myIntent.putExtra("isalreadylogged", Config.flag);
							LiveTrack.this.startActivity(myIntent);
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
					R.array.nav_drawer_items,
					android.R.layout.simple_spinner_dropdown_item);
			alertDialog = new AlertDialog.Builder(LiveTrack.this).create();
			// Callback
			OnNavigationListener callback = new OnNavigationListener() {
				//
				// String[] items = getResources().getStringArray(
				// R.array.nav_drawer_items); // List items from res

				@Override
				public boolean onNavigationItemSelected(int itemPosition,
						long id) {

					Intent myIntent;
					if (itemPosition != 0) {
						if (itemPosition == 0) { // Activity#1 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(LiveTrack.this,
									LiveTrack.class);
							LiveTrack.this.startActivity(myIntent);
						} else if (itemPosition == 1) {
							LiveTrack.timer.cancel();
							vehiclehistory.clear();
							vehiclehistory1.clear();
							LiveTrack.doAsynchronousTask.cancel();// Activity#2
																	// Selected
							myIntent = new Intent(LiveTrack.this,
									HistoryTrack.class);
							LiveTrack.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						} else if (itemPosition == 2) {
							LiveTrack.timer.cancel();
							vehiclehistory.clear();
							vehiclehistory1.clear();
							LiveTrack.doAsynchronousTask.cancel();// Activity#3
																	// Selected
							myIntent = new Intent(LiveTrack.this,
									AlertMsg.class);
							LiveTrack.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						} else if (itemPosition == 3) { // Activity#3 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							VehichleArrayAdapter.data.clear();
							DashboardActivity.vehicleall.clear();
							vehiclehistory1.clear();
							vehiclehistory.clear();
							HistoryTrack.vehiclehistory1.clear();
							myIntent = new Intent(LiveTrack.this,
									DashboardActivity.class);
							Config.flag = "alreadyloggedin";
							myIntent.putExtra("isalreadylogged", Config.flag);
							LiveTrack.this.startActivity(myIntent);
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
		// signout = (Button) findViewById(R.id.signutty);
		// welcome = (TextView) findViewById(R.id.textView1);
		// welcomeusername = (TextView) findViewById(R.id.welcmename);
		// welcomeusername.setText(Config.username + "!");
		// welcomeusername.setTypeface(null, Typeface.BOLD);
		// welcome.setTypeface(null, Typeface.BOLD);
		try {
			MapsInitializer.initialize(getApplicationContext());
		} catch (GooglePlayServicesNotAvailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (googleMap == null) {

				googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();
			}
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			// googleMap.setOnMarkerClickListener(this);
			googleMap.getUiSettings().setRotateGesturesEnabled(true);
			googleMap.getUiSettings().setCompassEnabled(true);
			googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
			Marker marker = googleMap.addMarker(new MarkerOptions().position(
					TutorialsPoint).title(""));
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(TutorialsPoint).zoom(4).build();
			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
			marker.remove();
			marker.setVisible(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
		userrole = Config.role;

		// tgbutton = (ToggleButton) findViewById(R.id.showmap);
		// tgbutton.setSelected(true);
		// tgbutton.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (!tgbutton.isChecked()) {
		// try {
		// if (googleMap == null) {
		// googleMap = ((MapFragment) getFragmentManager()
		// .findFragmentById(R.id.map)).getMap();
		// }
		// googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		// tgbutton.setBackgroundResource(R.drawable.earth);
		//
		// Marker marker = googleMap.addMarker(new MarkerOptions()
		// .position(TutorialsPoint).title(""));
		// CameraPosition cameraPosition = new CameraPosition.Builder()
		// .target(TutorialsPoint).zoom(4).build();
		// googleMap.animateCamera(CameraUpdateFactory
		// .newCameraPosition(cameraPosition));
		// marker.remove();
		// marker.setVisible(false);
		// marker.setVisible(false);
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// } else {
		// try {
		// if (googleMap == null) {
		// googleMap = ((MapFragment) getFragmentManager()
		// .findFragmentById(R.id.map)).getMap();
		// }
		// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		// tgbutton.setBackgroundResource(R.drawable.aerial);
		// Marker marker = googleMap.addMarker(new MarkerOptions()
		// .position(TutorialsPoint).title(""));
		// CameraPosition cameraPosition = new CameraPosition.Builder()
		// .target(TutorialsPoint).zoom(4).build();
		// googleMap.animateCamera(CameraUpdateFactory
		// .newCameraPosition(cameraPosition));
		// marker.remove();
		// marker.setVisible(false);
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// }
		// }
		// });

		timercalling();

	}

	// private void initilizeMap() {
	// if (googleMap == null) {
	// googleMap = ((MapFragment) getFragmentManager().findFragmentById(
	// R.id.map)).getMap();
	//
	// // check if map is created successfully or not
	// if (googleMap == null) {
	// Toast.makeText(getApplicationContext(),
	// "Sorry! unable to create maps", Toast.LENGTH_SHORT)
	// .show();
	// }
	// }
	// }

	public void timercalling() {
		// final Handler handler;
		// handler = new Handler();
		timer = new Timer();
		doAsynchronousTask = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@SuppressWarnings("deprecation")
					public void run() {
						try {
							if (isInternetPresent) {
								new CountDownTimer(30000, 1000) {

									public void onTick(long millisUntilFinished) {
										// long seconds = millisUntilFinished /
										// 1000;
										int barVal = ((int) (millisUntilFinished) / 1000);
										pb.setProgress(barVal);

										timingsec.setText(""
												+ millisUntilFinished / 1000);

									}

									public void onFinish() {
										timingsec.setText("");
									}
								}.start();
								new VehiclePath().execute();
							} else {
								alertcheck = alertDialog.isShowing();

								if (alertcheck.booleanValue() == true) {

									alertDialog.dismiss();
								}
								alertDialog = new AlertDialog.Builder(
										LiveTrack.this).create();

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

						} catch (Exception e) {

						}
					}
				});
			}
		};

		timer.schedule(doAsynchronousTask, 0, 30000);
	}

	class VehiclePath extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			cDialog = new ProgressDialog(LiveTrack.this);
			cDialog.setMessage("Please wait...");
			cDialog.setIndeterminate(false);
			// cDialog.setCancelable(true);
			cDialog.show();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("org_id", Config.org_id));
			params1.add(new BasicNameValuePair("vechicle_reg_no",
					vehicle_reg_no));

			jArray = jsonParser
					.makeHttpRequest(vehicleliveurl, "POST", params1);

			Log.i("tagconvertstr", "[" + jArray + "]");
			succy = "fail";
			try {
				if (jArray != null) {

					JSONObject c = jArray.getJSONObject(TAG_SRES);

					user = c.getJSONArray(TAG_VEHICLE_ARRAY);

					if (user.length() == 0) {
						succy = "fail";
					} else {
						succy = "true";
					}
					for (int i = 0; i < user.length(); i++) {
						int p = vehiclehistory.size();

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
						map.put(TAG_Latitude + p, latitude);
						map.put(TAG_Longitude + p, longitude);
						map.put(TAG_Speed + p, speed);
						map.put(TAG_Exceed_Speed + p, exceed_speed_limit);
						map.put(TAG_address + p, address);
						map.put(TAG_bus_tracking_timestamp + p,
								bus_tracking_timestamp);

						vehiclehistory.add(p, map);

					}

				}

			} catch (JSONException e) {

				alertcheck = alertDialog.isShowing();

				if (alertcheck.booleanValue() == true) {

					alertDialog.dismiss();
				}
				alertDialog = new AlertDialog.Builder(LiveTrack.this).create();

				alertDialog.setTitle("INFO!");

				alertDialog.setMessage("No location's found.");

				alertDialog.setIcon(R.drawable.delete);

				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {

							}
						});

				alertDialog.show();
				e.printStackTrace();
			}

			cDialog.dismiss();
			return null;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {

			super.onPostExecute(file_url);
			ArrayList<LatLng> points = null;
			PolylineOptions polyLineOptions = null;
			points = new ArrayList<LatLng>();
			googleMap.clear();
			polyLineOptions = new PolylineOptions();

			cDialog.dismiss();
			int sizeminusone;
			sizeminusone = vehiclehistory.size() - 1;

			for (int k = 0; k < vehiclehistory.size(); k++) {

				LatLng pinLocation = new LatLng(
						Double.parseDouble(vehiclehistory.get(k).get(
								TAG_Latitude + k)),
						Double.parseDouble(vehiclehistory.get(k).get(
								TAG_Longitude + k)));

				points.add(pinLocation);
				String titlevalue = "Speed:"
						+ vehiclehistory.get(k).get(TAG_Speed + k)
						+ " km/hr "
						+ "Date:"
						+ vehiclehistory.get(k).get(
								TAG_bus_tracking_timestamp + k);
				String snippetval = titlevalue + "\n" + "Address:"
						+ vehiclehistory.get(k).get(TAG_address + k);

				if (sizeminusone != k) {

					marker = new MarkerOptions().position(pinLocation).snippet(
							snippetval);
					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.click));
					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.red_pin));

					googleMap.addMarker(marker);
					// callone();

				} else if (sizeminusone == k) {

					marker = new MarkerOptions().position(pinLocation).snippet(
							snippetval);

					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.green_pin));
					googleMap.addMarker(marker);

					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(pinLocation).zoom(18).build();

					googleMap.animateCamera(CameraUpdateFactory
							.newCameraPosition(cameraPosition));

				}

				if (vehiclehistory.get(k).get(TAG_Exceed_Speed + k).equals("1")) {
					marker = new MarkerOptions().position(pinLocation).snippet(
							snippetval);

					marker.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.pink_pin));
					googleMap.addMarker(marker);
				}

			}
			polyLineOptions.addAll(points);
			polyLineOptions.width(2);
			polyLineOptions.color(Color.BLACK);
			googleMap.addPolyline(polyLineOptions);

			if (succy.equalsIgnoreCase("fail")) {
				alertcheck = alertDialog.isShowing();

				if (alertcheck.booleanValue() == true) {
					System.out.println("alert check value::" + alertcheck);
					alertDialog.dismiss();
				}
				alertDialog = new AlertDialog.Builder(LiveTrack.this).create();

				alertDialog.setTitle("INFO!");

				alertDialog.setMessage("No location's found.");

				alertDialog.setIcon(R.drawable.delete);

				alertDialog.setButton("OK",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									final int which) {

							}
						});

				alertDialog.show();

			}

		}

	}

	@Override
	protected void onResume() {
		super.onResume();

		vehicle_reg_no = getIntent().getExtras().getString("vehicleregnum");
		routeno = getIntent().getExtras().getString("routenum");
		driver_name = getIntent().getExtras().getString("drivername");

		reg_no.setText(vehicle_reg_no);
		vehicle_own.setText(driver_name);
		System.out.println("value of driver name:::" + driver_name);

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		System.out.println("in destroy");
		timer.cancel();
		doAsynchronousTask.cancel();
	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public void onMapLongClick(LatLng arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.info:

			com.setVisibility(View.VISIBLE);

			YoYo.with(Techniques.RotateIn).duration(200).playOn(com);

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}
}
