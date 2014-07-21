package com.deemsysinc.gpsmobiletracking;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;

import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.util.Log;
import android.view.View;
import android.view.Window;
public class DashboardActivity extends Activity{
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	JsonParser jsonParser = new JsonParser();
	JSONObject jArray;
	public static ArrayList<String> vehiclelist= new ArrayList<String>();
	public static List<Vehicle> vehicleall= new ArrayList<Vehicle>();
    JSONArray user = null;
    private static final String TAG_VEHICLE_ARRAY = "Vehicle List";
	//private static final String TAG_SUCCESS = "success";
	private static final String TAG_SRES= "serviceresponse";
	private static final String TAG_ORGID= "org_id1";
	private static final String TAG_Vehicle_regno= "vechicle_reg_no";
	private static final String TAG_Device_imei= "device_imei_number";
	private static final String TAG_drivername= "driver_name";
	private static final String TAG_driver_license_no= "driver_licence_number";
	private static final String TAG_licence_expdate= "driver_licence_exp_date";
	private static final String TAG_route_no= "route_no";
	private static final String TAG_driver_status= "device_status";
	public ProgressDialog cDialog;
	String org;
	static String vehicle_regno;
	String device_ime;
	String drivername;
	String driver_license;
	String license_expiry;
	String route_num;
	String driverstatus;
	ListView lstvw;
	Context context=this;
	ListView list2;
	TextView welcomeusername;
	Button aboutus,contactus,signout;
	private static String vehicledetailsurl = "http://192.168.1.71:8080/gpsandroid/service/VehicleDetails.php?service=vehicledetails1"; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		    getActionBar().hide();
		setContentView(R.layout.dashboard);
		 cd = new ConnectionDetector(getApplicationContext());
		 isInternetPresent = cd.isConnectingToInternet();
		 if (isInternetPresent) {
		        new VehicleDetails().execute(); 
				 }

		 list2= (ListView) findViewById(R.id.list);
				aboutus=(Button)findViewById(R.id.aboutus);
				contactus=(Button)findViewById(R.id.contactus);
				signout=(Button)findViewById(R.id.button1);
				welcomeusername=(TextView)findViewById(R.id.welcomeusername);
				welcomeusername.setText(LoginActivity.usernamepassed+"!");
	
		 signout.setOnClickListener(new View.OnClickListener() {
				
	            
	        	public void onClick(View v) {
	        		LoginActivity.usernamepassed="";
	       VehichleArrayAdapter.data.clear();
	       vehicleall.clear();
	        		Intent intentSignUP=new Intent(getApplicationContext(),LoginActivity.class);
     			startActivity(intentSignUP);
	        	}
		 });
		 
		 aboutus.setOnClickListener(new View.OnClickListener() {
				
	            
	        	public void onClick(View v) {
	        		Intent intentSignUP=new Intent(getApplicationContext(),Aboutus.class);
        			startActivity(intentSignUP);
	        	}
		 });
		 
		 contactus.setOnClickListener(new View.OnClickListener() {
				
	            
	        	public void onClick(View v) {
	        		Intent intentSignUP=new Intent(getApplicationContext(),ContactUs.class);
     			startActivity(intentSignUP);
	        	}
		 });
	}
	 public void onItemClick(int mPosition)
     {
		
     
       
         Intent intent = new Intent(DashboardActivity.this, TrackingActivity.class);
         Bundle b=new Bundle();
         b.putString("vehicleregnum",vehicle_regno);
        b.putString("routenum", route_num);
         System.out.println("Position passed from dashboard activity:::"+vehicle_regno);
         System.out.println("Position passed from dashboard activity:::"+route_num);
         intent.putExtras(b);
        
      
      startActivity(intent);
       
     }

	class VehicleDetails extends AsyncTask<String,String,String>{
		@Override
	    protected void onPreExecute() {
			  cDialog = new ProgressDialog(DashboardActivity.this);
	          cDialog.setMessage("Fetching Vehicle Details");
	          cDialog.setIndeterminate(false);
	          cDialog.setCancelable(false);
	          cDialog.show();
		}
			
			@Override
			protected void onPostExecute(String file_url) {
		   
				 super.onPostExecute(file_url);
				 list2.setAdapter(new VehichleArrayAdapter(DashboardActivity.this, vehicleall,R.layout.vehiclelist));
				 list2.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
					 

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long arg3) {
							System.out.println("in item click"+arg2);
							String item = vehicleall.get(arg2).getvehicle_regno(); 
							
					        
					        
					         System.out.println("Position passed from dashboard activity:::"+item);
							 Intent i= new Intent(DashboardActivity.this,TrackingActivity.class);
							  i.putExtra("vehicleregnum", item);
							 startActivity(i);
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
	             
	             params1.add(new BasicNameValuePair("org_id", LoginActivity.orgid));
	           
	             jArray = jsonParser.makeHttpRequest(vehicledetailsurl, "POST", params1);
			
			    Log.i("tagconvertstr", "["+jArray+"]");
			    
			    try
			    {
			    	if(jArray != null){
			    	
			    	JSONObject c = jArray.getJSONObject(TAG_SRES);
			    	Log.i("tagconvertstr", "["+c+"]");
			    	user = c.getJSONArray(TAG_VEHICLE_ARRAY);
			    	Log.i("tagconvertstr1", "["+user+"]");
			    	
			    	for(int i=0;i<user.length();i++)
			    	{
			    		System.out.println("forloop1");
			    		JSONObject c1 = user.getJSONObject(i);
			    		JSONObject c2 = c1.getJSONObject(TAG_SRES);
			    	    org = c2.getString(TAG_ORGID);
			    		
			            vehicle_regno = c2.getString(TAG_Vehicle_regno);
			            device_ime= c2.getString(TAG_Device_imei);
			        	drivername = c2.getString(TAG_drivername);
			        	driver_license = c2.getString(TAG_driver_license_no);
			        	license_expiry=c2.getString(TAG_licence_expdate);
			        	route_num = c2.getString(TAG_route_no);
			        	driverstatus=c2.getString(TAG_driver_status);
			        	
			        	vehiclelist.add(vehicle_regno);
			        	//vehiclelist.add(device_ime);
			        	vehiclelist.add(drivername);
			        	//vehiclelist.add(driver_license);
			        	//vehiclelist.add(license_expiry);
			        	//vehiclelist.add(route_num);
			        	vehiclelist.add(driverstatus);
			        	 Vehicle cnt = new Vehicle(vehicle_regno, device_ime, drivername, driver_license, license_expiry, route_num, driverstatus);
						    cnt.setvehicle_regno(vehicle_regno);
						    cnt.setdevice_ime(device_ime);
						    cnt.setdrivername(drivername);
						    cnt.setdriver_license(driver_license);
						    cnt.setroute_num(route_num);
						    cnt.setdate(license_expiry);
						    cnt.setdriverstatus(driverstatus);
			               vehicleall.add(cnt);
			    		//int a=vehiclelist.size();
			    		System.out.println("size of aray list"+vehicleall);
			    		
			    	}
			    	
			    	}
			    	
			    	}catch (JSONException e) {
			        e.printStackTrace();
			    }
			    cDialog.dismiss();
				return null;
			}
			
			
			
		}
	 @Override
	   public void onBackPressed() {
	   }


		

}