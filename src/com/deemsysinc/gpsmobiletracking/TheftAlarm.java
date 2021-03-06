package com.deemsysinc.gpsmobiletracking;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
import android.graphics.BitmapFactory;

import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;

import android.media.MediaPlayer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SpinnerAdapter;

import android.widget.TextView;

public class TheftAlarm extends Activity {

	/** Called when the activity is first created. */
	MediaPlayer mPlayer;
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
	Button signout;
	Runnable runnable;
	TextView welcomeusername, welcome, txt, devicestatus, ownername,drivername, reg_number;
	String succ;
	Animation anim;
	private static final String TAG_SRES = "serviceresponse";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_STATUS = "status";
	static final String TAG_Vechicle_REG = "vechicle_reg_no";
	final Handler handler = new Handler();
	public static Timer timer;
	static TimerTask doAsynchronousTask;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theftalarm);
		ActionBar actions = getActionBar();
		getActionBar().setBackgroundDrawable(
				new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
						R.drawable.actionbarbg)));

		actions.setIcon(R.drawable.alarmicon);
		actions.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actions.setDisplayShowTitleEnabled(false);
		cd = new ConnectionDetector(getApplicationContext());
		txt = (TextView) findViewById(R.id.alerttext);
		txt.setTypeface(null, Typeface.BOLD);
		txt.setVisibility(View.INVISIBLE);
		on = (RadioButton) findViewById(R.id.radiosubject);
		off = (RadioButton) findViewById(R.id.radiocourse);
		drivername = (TextView) findViewById(R.id.drivername);
		reg_number = (TextView) findViewById(R.id.veg_reg_no);
		ownername = (TextView) findViewById(R.id.ownername);
		devicestatus = (TextView) findViewById(R.id.device_status);
		reg_number.setText(LiveTrack.vehicle_reg_no);
		drivername.setText(LiveTrack.driver_name);
		ownername.setText(Config.username);

		if (LiveTrack.devicestatus.equalsIgnoreCase("0")) {
			devicestatus.setText("Switched Off");
		} else if (LiveTrack.devicestatus.equalsIgnoreCase("1")) {
			devicestatus.setText("Active");
		} else if (LiveTrack.devicestatus.equalsIgnoreCase("2")) {
			devicestatus.setText("No GPS signal");
		} else if (LiveTrack.devicestatus.equalsIgnoreCase("3")) {
			devicestatus.setText("Sleep mode");
		} else {
			devicestatus.setText("Switched Off");
		}
		
		// mPlayer.reset();
		
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			new CheckTheftAlarm().execute();
		} else {
			on.setVisibility(View.INVISIBLE);
			off.setVisibility(View.INVISIBLE);
			AlertDialog alertDialog = new AlertDialog.Builder(TheftAlarm.this)
					.create();

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
		anim = new AlphaAnimation(0.0f, 1.0f);

		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				isInternetPresent = cd.isConnectingToInternet();
				RadioButton rb = (RadioButton) v;
				String selectedid = rb.getText().toString();
				if (selectedid.equalsIgnoreCase("on")) {
					on.setEnabled(false);
					off.setEnabled(true);

					// handler.post(timedTask);
					if (isInternetPresent) {
						new insertTheftAlarm().execute();
					} else {
						off.setChecked(true);
						on.setEnabled(true);
						off.setEnabled(false);
						AlertDialog alertDialog = new AlertDialog.Builder(
								TheftAlarm.this).create();

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
					// handler.removeCallbacks(timedTask);
					if (mPlayer != null && mPlayer.isPlaying()) {
						mPlayer.stop();
					}
					if (TheftAlarm.timer != null) {
						TheftAlarm.timer.cancel();
						TheftAlarm.doAsynchronousTask.cancel();
					}
					on.setEnabled(true);
					off.setEnabled(false);
					anim.cancel();
					anim.reset();

					if (isInternetPresent) {
						new updateTheftAlarm().execute();
					} else {
						on.setChecked(true);
						on.setEnabled(false);
						off.setEnabled(true);
						AlertDialog alertDialog = new AlertDialog.Builder(
								TheftAlarm.this).create();

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

			}
		};
		on.setOnClickListener(listener);
		off.setOnClickListener(listener);

		if (Config.role.equalsIgnoreCase("ROLE_FCLIENT")||Config.role.equalsIgnoreCase("ROLE_PCLIENT")) {
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
							if (TheftAlarm.timer != null) {
								System.out.println("is not null");
								TheftAlarm.timer.cancel();
								TheftAlarm.doAsynchronousTask.cancel();

							}
							if (mPlayer != null && mPlayer.isPlaying()) {
								mPlayer.stop();
							}
							
							myIntent = new Intent(TheftAlarm.this,
									LiveTrack.class);
							myIntent.putExtra("devicestatus",
									LiveTrack.devicestatus);
							myIntent.putExtra("vehicleregnum",
									LiveTrack.vehicle_reg_no);
							myIntent.putExtra("drivername",
									LiveTrack.driver_name);
							myIntent.putExtra("routenum", LiveTrack.routeno);
							TheftAlarm.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						} else if (itemPosition == 2) { // Activity#3 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(TheftAlarm.this,
									HistoryTrack.class);
							if (mPlayer != null && mPlayer.isPlaying()) {
								mPlayer.stop();
							}
							if (TheftAlarm.timer != null) {
								System.out.println("is not null");
								TheftAlarm.timer.cancel();
								TheftAlarm.doAsynchronousTask.cancel();
							}
							TheftAlarm.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						} else if (itemPosition == 3) { // Activity#3 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(TheftAlarm.this,
									OverSpeed.class);
							if (TheftAlarm.timer != null) {
								System.out.println("is not null");
								TheftAlarm.timer.cancel();
								TheftAlarm.doAsynchronousTask.cancel();
							}
							if (mPlayer != null && mPlayer.isPlaying()) {
								mPlayer.stop();
							}
							TheftAlarm.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
						} else if (itemPosition == 4) { // Activity#3 Selected
							LiveTrack.timer.cancel();
							LiveTrack.doAsynchronousTask.cancel();
							myIntent = new Intent(TheftAlarm.this,
									DashboardActivity.class);
							if (TheftAlarm.timer != null) {
								System.out.println("is not null");
								TheftAlarm.timer.cancel();
								TheftAlarm.doAsynchronousTask.cancel();
							}
							if (mPlayer != null && mPlayer.isPlaying()) {
								mPlayer.stop();
							}
							Config.flag = "alreadyloggedin";
							myIntent.putExtra("isalreadylogged", Config.flag);
							TheftAlarm.this.startActivity(myIntent);
							overridePendingTransition(R.anim.slide_in,
									R.anim.slide_out);
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
			params1.add(new BasicNameValuePair("org_id", Config.org_id));
			System.out.println("parameters" + params1);

			JSONObject json = ((JsonParser) jsonParser).makeHttpRequest(
					Config.ServerUrl + "TheftAlarm.php?service=presentstatus",
					"POST", params1);

			Log.i("tagconvertstr", "[" + jobject + "]");
			status = "0";
			try {
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
			} catch (Exception e) {

			}
			pDialog.dismiss();
			return null;
		}

		@Override
		protected void onPostExecute(String file_url) {

			super.onPostExecute(file_url);
			try {
				if (status.equalsIgnoreCase("1")) {
					on.setChecked(true);
					on.setEnabled(false);
					timercalling();
					// handler.post(timedTask);
					txt.setVisibility(View.INVISIBLE);

				} else if (status.equalsIgnoreCase("0")) {
					off.setChecked(true);
					txt.setVisibility(View.INVISIBLE);
					off.setEnabled(false);

				} else if (status.equalsIgnoreCase("2")) {
					on.setChecked(true);
					on.setEnabled(false);
					try {
						if (Config.alarmsoundtype != null) {
							if (Config.alarmsoundtype
									.equalsIgnoreCase("Nuclear Alert")) {
								mPlayer = MediaPlayer.create(
										getApplicationContext(), R.raw.type1);
								mPlayer.start();
							} else if (Config.alarmsoundtype
									.equalsIgnoreCase("Car Alert")) {
								mPlayer = MediaPlayer.create(
										getApplicationContext(), R.raw.type2);
								mPlayer.start();
							} else if (Config.alarmsoundtype
									.equalsIgnoreCase("Extreme Alert")) {
								mPlayer = MediaPlayer.create(
										getApplicationContext(), R.raw.type3);
								mPlayer.start();
							} else if (Config.alarmsoundtype
									.equalsIgnoreCase("Handy Alert")) {
								mPlayer = MediaPlayer.create(
										getApplicationContext(), R.raw.type4);
								mPlayer.start();
							} else if (Config.alarmsoundtype
									.equalsIgnoreCase("Red Alert")) {
								mPlayer = MediaPlayer.create(
										getApplicationContext(),
										R.raw.alarmtone);
								mPlayer.start();
							}

						} else {

							mPlayer = MediaPlayer.create(
									getApplicationContext(), R.raw.alarmtone);
							mPlayer.start();
						}

						// Uri notification = RingtoneManager
						// .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
						// Ringtone r = RingtoneManager.getRingtone(
						// getApplicationContext(), notification);
						// r.play();
					} catch (Exception e) {
						e.printStackTrace();
					}
					// handler.post(timedTask);
					timercalling();
					blink();
				} else {
					off.setChecked(true);
					txt.setVisibility(View.INVISIBLE);
					off.setEnabled(false);
				}

				// } else {
				// txt.setVisibility(View.INVISIBLE);
				// off.setChecked(true);
				// off.setEnabled(false);
				// }
			} catch (Exception e) {

			}
		}

	}

	private void blink() {
		// System.out.println("in blink meth");

		anim.setDuration(50); // You can manage the time of the blink with this
								// parameter
		anim.setStartOffset(20);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		txt.startAnimation(anim);

	}

	class updateTheftAlarm extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(TheftAlarm.this);
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("vechicle_reg_no",
					LiveTrack.vehicle_reg_no));
			params1.add(new BasicNameValuePair("org_id", Config.org_id));
			JsonParser jLogin = new JsonParser();

			JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl
					+ "TheftAlarm.php?service=updatealarm", "POST", params1);
			try {
				if (json != null) {
					try {
						if (json != null) {

							JSONObject jUser = json.getJSONObject(TAG_SRES);
							System.out.println("value of juser:" + jUser);

						}

					}

					catch (JSONException e) {
						e.printStackTrace();

					}
				} else {

				}
			} catch (Exception e) {

			}
			return null;
		}

		@SuppressWarnings("deprecation")
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
			alertDialog.setIcon(R.drawable.tick);

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
			pDialog.setCancelable(true);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			JsonParser jLogin = new JsonParser();
			params1.add(new BasicNameValuePair("vechicle_reg_no",
					LiveTrack.vehicle_reg_no));
			params1.add(new BasicNameValuePair("org_id", Config.org_id));
			JSONObject json = jLogin.makeHttpRequest(Config.ServerUrl
					+ "TheftAlarm.php?service=insertalarm", "POST", params1);

			System.out.println("value for json::" + json);
			succ = "no";
			try {
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
			} catch (Exception e) {

			}
			return null;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {
			super.onPostExecute(file_url);
			pDialog.dismiss();
			try {
				timercalling();
				if (succ.equalsIgnoreCase("No")) {
					off.setChecked(true);
					off.setEnabled(false);
					on.setEnabled(true);
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

								public void onClick(
										final DialogInterface dialog,
										final int which) {
									// Write your code here to execute after
									// dialog
									// closed
									off.setChecked(true);
									off.setEnabled(false);
									on.setEnabled(true);
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
					alertDialog.setIcon(R.drawable.tick);

					// Setting OK Button
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {

								public void onClick(
										final DialogInterface dialog,
										final int which) {
									// Write your code here to execute after
									// dialog
									// closed

								}
							});

					// Showing Alert Message
					alertDialog.show();
				}
			} catch (Exception e) {

			}
		}
	}

	private Runnable timedTask = new Runnable() {

		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			try {
				isInternetPresent = cd.isConnectingToInternet();
				if (isInternetPresent) {
					new CheckTheftAlarm().execute();
				} else {
					AlertDialog alertDialog = new AlertDialog.Builder(
							TheftAlarm.this).create();

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

			} catch (Exception e) {

			}

			handler.postDelayed(timedTask, 300000);
		}
	};

	public void timercalling() {

		TheftAlarm.timer = new Timer();

		TheftAlarm.doAsynchronousTask = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@SuppressWarnings("deprecation")
					public void run() {
						try {
							if (isInternetPresent) {
								new CheckTheftAlarm().execute();
							} else {
								AlertDialog alertDialog = new AlertDialog.Builder(
										TheftAlarm.this).create();

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
		TheftAlarm.timer.schedule(TheftAlarm.doAsynchronousTask, 120000);
	}

	@Override
	public void onBackPressed() {
	}
}