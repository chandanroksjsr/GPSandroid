package com.deemsysinc.gpsmobiletracking;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.deemsysinc.gpsmobiletracking.LiveTrack.VehiclePath;
import com.google.android.gms.internal.s;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class BackgroundService extends Service {
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs0";
	private static final String TAG_SRES = "serviceresponse";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_STATUS = "status";
	static final String TAG_Vechicle_REG = "vechicle_reg_no";
	private static final String TAG = "com.deemsysinc.gpsmobileTracking";
	public static Timer timer;
	Handler handler;
	JsonParser jsonParser = new JsonParser();
	JSONObject jobject;
	Context context = this;
	String status;
	static TimerTask doAsynchronousTask;
	MediaPlayer mPlayer;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		sharedpreferences = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);
		handler = new Handler();
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "Service onStartCommand");
		timercalling();
		return Service.START_STICKY;

	}

	public void timercalling() {
		// final Handler handler;
		// handler = new Handler();
		timer = new Timer();
		doAsynchronousTask = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						try {
							System.out.println("i am in on start");
							new CheckTheftAlarm().execute();
							// Uri notification = RingtoneManager
							// .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
							// Ringtone r = RingtoneManager.getRingtone(
							// getApplicationContext(), notification);
							// r.play();
						} catch (Exception e) {

						}
					}
				});
			}
		};
		timer.scheduleAtFixedRate(doAsynchronousTask, 100, 120000);
	}

	private void runOnUiThread(Runnable runnable) {
		handler.post(runnable);
	}

	class CheckTheftAlarm extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// pDialog = new ProgressDialog(TheftAlarm.this);
			// pDialog.setMessage("Please wait...");
			// pDialog.setIndeterminate(false);
			// pDialog.setCancelable(false);
			// pDialog.show();

		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			params1.add(new BasicNameValuePair("org_id", sharedpreferences
					.getString("org_id", "")));
			System.out.println("parameters" + params1);

			JSONObject json = ((JsonParser) jsonParser).makeHttpRequest(
					Config.ServerUrl
							+ "BackgroundService.php?service=backservice",
					"POST", params1);

			Log.i("tagconvertstr", "[" + jobject + "]");
			status="0";
			if (json != null) {
				try {
					if (json != null) {
						System.out.println("json value::" + json);

						JSONObject jUser = json.getJSONObject(TAG_SRES);
						status = jUser.getString(TAG_STATUS);

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
			
				if (Integer.parseInt(status) > 0) {

					try {
						
						
						mPlayer = MediaPlayer.create(getApplicationContext(),
								R.raw.alarmtone);// Create MediaPlayer object with
													// MP3 file under res/raw folder
						mPlayer.start();
						
//						Uri notification = RingtoneManager
//								.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//						Ringtone r = RingtoneManager.getRingtone(
//								getApplicationContext(), notification);
//						r.play();
					} catch (Exception e) {
						e.printStackTrace();
					}

			
			}

		}

	}

}
