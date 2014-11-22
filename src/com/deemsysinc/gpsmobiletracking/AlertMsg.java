package com.deemsysinc.gpsmobiletracking;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class AlertMsg extends Activity {

	/** Called when the activity is first created. */

	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	public ProgressDialog pDialog;
	static EditText msgtxt;
	String sendmsg;
	JsonParser jsonParser = new JsonParser();
	JSONObject jobject;
	Context context = this;
	String msg;
	JSONArray number = null;
	JSONArray mobile = null;
	Button home, signout;
	TextView welcomeusername, welcome;
	public static ArrayList<String> mobilenumber = new ArrayList<String>();

	// private static String url =
	// "http://192.168.1.158:8888/gpsandroid/service/message.php?service=select";
	// private static String url =
	// "http://192.168.1.71:8080/gpsandroid/service/message.php?service=select";
	private static String url = Config.ServerUrl + "message.php?service=select";

	private static final String TAG_VEHICLE_ARRAY = "mobilenumber";

	private static final String TAG_SRES = "serviceresponse";

	private static final String TAG_Parent_mobile1 = "parent_mobile1";

	private static String parent_mobile1;

	String route;
	String orgid;
	int a;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alertmsg);

		ActionBar actions = getActionBar();
		getActionBar().setTitle(
				Html.fromHtml("<font color='#ffffff'>Deemgps </font>"));
		getActionBar().setBackgroundDrawable(
				new BitmapDrawable(BitmapFactory.decodeResource(getResources(),
						R.drawable.actionbarbg)));

		actions.setIcon(R.drawable.alerticon);
		actions.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actions.setDisplayShowTitleEnabled(false);
		SpinnerAdapter adapter = ArrayAdapter.createFromResource(getActionBar()
				.getThemedContext(), R.array.nav_drawer_items2,
				android.R.layout.simple_spinner_dropdown_item);

		// Callback
		OnNavigationListener callback = new OnNavigationListener() {

			String[] items = getResources().getStringArray(
					R.array.nav_drawer_items2); // List items from res

			@Override
			public boolean onNavigationItemSelected(int itemPosition, long id) {

				// Do stuff when navigation item is selected
				System.out.println("item position value" + itemPosition);
				// Log.d("NavigationItemSelected", items[position]); // Debug
				Intent myIntent;
				if (itemPosition != 0) {
					if (itemPosition == 0) { // Activity#1 Selected
						LiveTrack.timer.cancel();
						LiveTrack.doAsynchronousTask.cancel();
						myIntent = new Intent(AlertMsg.this, AlertMsg.class);
						AlertMsg.this.startActivity(myIntent);
					} else if (itemPosition == 1) { // Activity#2 Selected
						LiveTrack.timer.cancel();
						LiveTrack.doAsynchronousTask.cancel();
						myIntent = new Intent(AlertMsg.this, LiveTrack.class);
						myIntent.putExtra("vehicleregnum",
								LiveTrack.vehicle_reg_no);
						myIntent.putExtra("routenum", LiveTrack.routeno);
						AlertMsg.this.startActivity(myIntent);
						overridePendingTransition(R.anim.slide_in,
								R.anim.slide_out);
					} else if (itemPosition == 2) { // Activity#3 Selected
						LiveTrack.timer.cancel();
						LiveTrack.doAsynchronousTask.cancel();
						myIntent = new Intent(AlertMsg.this, HistoryTrack.class);
						AlertMsg.this.startActivity(myIntent);
						overridePendingTransition(R.anim.slide_in,
								R.anim.slide_out);
					} else if (itemPosition == 3) { // Activity#3 Selected
						LiveTrack.timer.cancel();
						LiveTrack.doAsynchronousTask.cancel();
						VehichleArrayAdapter.data.clear();
						DashboardActivity.vehicleall.clear();
						HistoryTrack.vehiclehistory1.clear();
						HistoryTrack.vehiclehistory1.clear();
						myIntent = new Intent(AlertMsg.this,
								DashboardActivity.class);
						AlertMsg.this.startActivity(myIntent);
						overridePendingTransition(R.anim.slide_in,
								R.anim.slide_out);
					}

				} else {

				}
				return true;

			}

		};
		actions.setListNavigationCallbacks(adapter, callback);
		route = LiveTrack.routeno;
		System.out.println("alert route numb track veh numb" + route);
		orgid = Config.org_id;
		LinearLayout layout = (LinearLayout) findViewById(R.id.alertlayout);
		layout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent ev) {
				hideKeyboard(view);
				return false;
			}

		});
		cd = new ConnectionDetector(getApplicationContext());
		Button btnsend, btnclr;

		btnsend = (Button) findViewById(R.id.fromtime);
		btnclr = (Button) findViewById(R.id.date);
		msgtxt = (EditText) findViewById(R.id.e6);

		// signout = (Button) findViewById(R.id.logingout);
		// welcome = (TextView) findViewById(R.id.textView1);
		// welcomeusername = (TextView) findViewById(R.id.username);
		// welcomeusername.setText(Config.username + "!");
		// welcomeusername.setTypeface(null, Typeface.BOLD);
		// welcome.setTypeface(null, Typeface.BOLD);
		new SendMessage().execute();
		// signout.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// LiveTrack.doAsynchronousTask.cancel();
		// LiveTrack.timer.cancel();
		// Config.username = "";
		// VehichleArrayAdapter.data.clear();
		// DashboardActivity.vehicleall.clear();
		// HistoryTrack.vehiclehistory1.clear();
		// SharedPreferences settings = getApplicationContext()
		// .getSharedPreferences("MyPrefs0",
		// getApplicationContext().MODE_PRIVATE);
		// settings.edit().clear().commit();
		// Intent ii = new Intent(AlertMsg.this, BackgroundService.class);
		// ii.putExtra("name", "SurvivingwithAndroid");
		// AlertMsg.this.stopService(ii);
		// Intent intentSignUP = new Intent(getApplicationContext(),
		// LoginActivity.class);
		// startActivity(intentSignUP);
		// }
		// });

		btnclr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				msgtxt.setText("");

			}
		});

		btnsend.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {

				isInternetPresent = cd.isConnectingToInternet();
				System.out
						.println("is internet present:::" + isInternetPresent);

				if (isInternetPresent) {

					msg = msgtxt.getText().toString();

					if (msg.length() > 0) {

						try {
							new messaging().execute();

						}

						catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {
						AlertDialog alertDialog = new AlertDialog.Builder(
								AlertMsg.this).create();

						// Setting Dialog Title
						alertDialog.setTitle("INFO!");

						// Setting Dialog Message
						alertDialog.setMessage("Message should not be empty.");

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

									}
								});

						// Showing Alert Message
						alertDialog.show();

					}

				}

				else {
					AlertDialog alertDialog = new AlertDialog.Builder(
							AlertMsg.this).create();

					// Setting Dialog Title
					alertDialog.setTitle("INFO!");

					// Setting Dialog Message
					alertDialog.setMessage("No network connection.");

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

								}
							});

					// Showing Alert Message
					alertDialog.show();

				}
			}

		});

	}

	class messaging extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub

			int l;

			System.out
					.println("size of mobile nunber:::" + mobilenumber.size());
			try {
				System.out.println("json value::" + jobject);
				JSONObject c = jobject.getJSONObject(TAG_SRES);

				Log.i("tagconvertstr", "[" + c + "]");
				mobile = c.getJSONArray(TAG_VEHICLE_ARRAY);
				Log.i("tagconvertstr1", "[" + mobile + "]");

				for (l = 0; l < mobilenumber.size(); l++) {
					System.out.println("forloop1");
					System.out.println("size of mobile nunber:::"
							+ mobilenumber.size());
					parent_mobile1 = mobilenumber.get(l);

					System.out.println("mobile number list" + parent_mobile1);

					String username = "info@holycrossengineeringcollege.com";
					String password = "tSE4A7qY";

					String message = msg + "\n";
					String resultString = message.replaceAll(" ", "%20");
					resultString = resultString.replaceAll("\n", "%20");
					String trinstring = resultString.trim();
					System.out.println("string afet trim" + trinstring);
					System.out.println("msg value after append" + resultString);
					String number = parent_mobile1;

					String url1 = "http://api.cutesms.in/sms.aspx?a=submit&un="
							+ username + "&pw=" + password + "&to=" + number
							+ "&msg=" + resultString + "";
					System.out.println("url printing::" + url1);
					System.out.println("url:" + url1);
					System.out.println("url length" + url1.length());
					DefaultHttpClient mClient = new DefaultHttpClient();
					HttpGet get = new HttpGet(url1);

					try {
						System.out.println("enter1");

						System.out.println("enter2");
						HttpResponse res = mClient.execute(get);
						System.out.println("enter3");
						System.out.println("responset" + res);
					} catch (Exception e) {

					}

					System.out.println("i value" + l);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {

			super.onPostExecute(file_url);
			AlertMsg.msgtxt.setText("");
			AlertDialog alertDialog = new AlertDialog.Builder(AlertMsg.this)
					.create();

			// Setting Dialog Title
			alertDialog.setTitle("INFO!");
			alertDialog.setCancelable(false);
			// Setting Dialog Message
			alertDialog.setMessage("Message sent successfully.");

			// Setting Icon to Dialog
			alertDialog.setIcon(R.drawable.tick);

			// Setting OK Button
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

				public void onClick(final DialogInterface dialog,
						final int which) {

					// Write your code here to execute after dialog
					// closed
					pDialog.dismiss();
				}
			});

			// Showing Alert Message
			alertDialog.show();

		}

	}

	class SendMessage extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(AlertMsg.this);
			pDialog.setMessage("Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("org_id", Config.username));

			params1.add(new BasicNameValuePair("routeno", route));
			System.out.println("parameters" + params1);

			jobject = ((JsonParser) jsonParser).makeHttpRequest(url, "POST",
					params1);

			Log.i("tagconvertstr", "[" + jobject + "]");

			try {
				if (jobject != null) {

					System.out.println("json value::" + jobject);
					JSONObject c = jobject.getJSONObject(TAG_SRES);

					Log.i("tagconvertstr", "[" + c + "]");
					number = c.getJSONArray(TAG_VEHICLE_ARRAY);
					Log.i("tagconvertstr1", "[" + number + "]");

					for (int i = 0; i < number.length(); i++) {
						System.out.println("forloop1");
						JSONObject c1 = number.getJSONObject(i);
						JSONObject c2 = c1.getJSONObject(TAG_SRES);

						parent_mobile1 = c2.getString(TAG_Parent_mobile1);

						System.out.println("mobile number list"
								+ parent_mobile1);

						mobilenumber.clear();
						mobilenumber.add(parent_mobile1);

					}

				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
			pDialog.dismiss();
			return null;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {

			super.onPostExecute(file_url);
			pDialog.dismiss();
			if (mobilenumber.size() == 0) {
				AlertDialog alertDialog = new AlertDialog.Builder(AlertMsg.this)
						.create();

				// Setting Dialog Title
				alertDialog.setTitle("INFO!");

				// Setting Dialog Message
				alertDialog.setMessage("No mobile numbers available.");

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

	@Override
	public void onBackPressed() {
	}

	protected void hideKeyboard(View view) {
		InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
