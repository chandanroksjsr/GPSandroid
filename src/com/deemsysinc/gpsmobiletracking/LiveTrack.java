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
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;


import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.android.gms.maps.model.MarkerOptions;


public class LiveTrack extends Activity implements OnMapLongClickListener{
	public static ArrayList<HashMap<String, String>> vehiclehistory1= new ArrayList<HashMap<String,String>>();
	 ArrayList<HashMap<String,String>> vehiclehistory= new ArrayList<HashMap<String,String>>();
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
	TextView welcomeusername;
	Button signout,home;
	TextView welcome;
	String succy;
	   static String vehicle_reg_no1,routeno;
	    String userrole;
	ToggleButton tgbutton;
	 MarkerOptions marker,expmarker;
	static final LatLng TutorialsPoint = new LatLng(22.3512639,78.9542827);
	private GoogleMap googleMap;
	 public static Timer timer;
	 static TimerTask doAsynchronousTask ;
	    final Context context=this;
	    private static final String TAG_SRES= "serviceresponse";
		private static final String TAG_VEHICLE_ARRAY = "VehicleHistory List";
		static final String TAG_Vechicle_REG= "vechicle_reg_no";
		private static final String TAG_Latitude= "latitude";
		private static final String TAG_Longitude= "longitude";
		private static final String TAG_Speed= "speed";
		private static final String TAG_Exceed_Speed= "exceed_speed_limit";
		private static final String TAG_bus_tracking_timestamp= "bus_tracking_timestamp";
		private static final String TAG_address= "address";
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
		//private static String vehicleliveurl = "http://192.168.1.71:8080/gpsandroid/service/HistoryTrack.php?service=vehiclehistory"; 
	//  private static String vehicleliveurl = "http://192.168.1.158:8888/gpsandroid/service/LiveTrack.php?service=livetrack"; 
	// private static String vehicleliveurl = "http://192.168.1.71:8080/gpsandroid/service/LiveTrack.php?service=livetrack"; 
	 private static String vehicleliveurl = "http://208.109.248.89:80/gpsandroid/service/LiveTrack.php?service=livetrack"; 
	/** Called when the activity is first created. */
	 class MyInfoWindowAdapter implements InfoWindowAdapter{

	        private final View myContentsView;
	  
	  MyInfoWindowAdapter(){
	   myContentsView = getLayoutInflater().inflate(R.layout.custom_onfo_window, null);
	  }
	  
	  @Override
	  public View getInfoContents(Marker marker) {
	   TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
	   tvTitle.setText(marker.getTitle());
	   TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
	   tvSnippet.setText(marker.getSnippet());
	            
	
	   
	   return myContentsView;
	  }
	  @Override
	  public View getInfoWindow(Marker marker) {
	   // TODO Auto-generated method stub
	   return null;
	  }
	 } 

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.livetrack);
	     
	        ActionBar actions = getActionBar();
	       int height= actions.getHeight();
	       System.out.println("height value::"+height);
	       actions.setIcon(R.drawable.liveicon);
	        actions.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#93aac3")));
	        actions.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	        actions.setDisplayShowTitleEnabled(false);
	        SpinnerAdapter adapter = ArrayAdapter.createFromResource(getActionBar().getThemedContext(), R.array.nav_drawer_items,
	                android.R.layout.simple_spinner_dropdown_item);
	        alertDialog = new AlertDialog.Builder(
					LiveTrack.this).create();
	        // Callback
	        OnNavigationListener callback = new OnNavigationListener() {

	            String[] items = getResources().getStringArray(R.array.nav_drawer_items); // List items from res

	            @Override
			    public boolean onNavigationItemSelected(int itemPosition, long id) {

			        // Do stuff when navigation item is selected
//System.out.println("item position value"+itemPosition);
			        //Log.d("NavigationItemSelected", items[position]); // Debug
			        Intent myIntent;
			        if(itemPosition!=0){
			            if(itemPosition == 0){ //Activity#1 Selected
			            	LiveTrack.timer.cancel();
					    	LiveTrack.doAsynchronousTask.cancel();
			                myIntent = new Intent(LiveTrack.this, LiveTrack.class);
			                LiveTrack.this.startActivity(myIntent);
			            } else if (itemPosition == 1){
			            	LiveTrack.timer.cancel();
			            	 vehiclehistory.clear();
			            	 vehiclehistory1.clear();
					    	LiveTrack.doAsynchronousTask.cancel();//Activity#2 Selected
			                myIntent = new Intent(LiveTrack.this, HistoryTrack.class);
			                LiveTrack.this.startActivity(myIntent);
			            } else if (itemPosition == 2){
			            	LiveTrack.timer.cancel();
			            	 vehiclehistory.clear();
			            	 vehiclehistory1.clear();
					    	LiveTrack.doAsynchronousTask.cancel();//Activity#3 Selected
			                myIntent = new Intent(LiveTrack.this, AlertMsg.class);
			                LiveTrack.this.startActivity(myIntent);
			            }
			            else if (itemPosition == 3){ //Activity#3 Selected
			            	LiveTrack.timer.cancel();
					    	LiveTrack.doAsynchronousTask.cancel();
			            	  VehichleArrayAdapter.data.clear();
			       	       DashboardActivity.vehicleall.clear();
			       	       vehiclehistory1.clear();
			       	       vehiclehistory.clear();
			       	       HistoryTrack.vehiclehistory1.clear();
			                myIntent = new Intent(LiveTrack.this, DashboardActivity.class);
			                LiveTrack.this.startActivity(myIntent);
			            }
			          
			        }
			        else
			        {
			        	
			        }
			        return true;

			    }


	        };

	        actions.setListNavigationCallbacks(adapter, callback);
	      
	      signout=(Button)findViewById(R.id.signutty);
	      welcome=(TextView)findViewById(R.id.textView1);
			welcomeusername=(TextView)findViewById(R.id.welcmename);
			welcomeusername.setText(LoginActivity.usernamepassed+"!");
			 welcomeusername.setTypeface(null, Typeface.BOLD);
		      welcome.setTypeface(null, Typeface.BOLD);
	      try {
			MapsInitializer.initialize(getApplicationContext());
		} catch (GooglePlayServicesNotAvailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	      try { 
	            if (googleMap == null) {
	            	
	               googleMap = ((MapFragment) getFragmentManager().
	               findFragmentById(R.id.map)).getMap();
	            }
	         googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	       //  googleMap.setOnMarkerClickListener(this);
	         googleMap.getUiSettings().setRotateGesturesEnabled(true);
	         googleMap.getUiSettings().setCompassEnabled(true);
	         googleMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
	         Marker marker = googleMap.addMarker(new MarkerOptions().
	    	         position(TutorialsPoint).title(""));
	         CameraPosition cameraPosition = new CameraPosition.Builder().target(
		    		  TutorialsPoint).zoom(4).build();
	         googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	         marker.remove();
	         marker.setVisible(false);

	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      userrole=LoginActivity.role;
	      
	     
	    
	      tgbutton = (ToggleButton) findViewById(R.id.showmap);
	    //  tgbutton.setSelected(true);
	        tgbutton.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	                // TODO Auto-generated method stub
	                 if (!tgbutton.isChecked()) {
	                	 try { 
	         	            if (googleMap == null) {
	         	               googleMap = ((MapFragment) getFragmentManager().
	         	               findFragmentById(R.id.map)).getMap();
	         	            }
	         	         googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	         	         tgbutton.setBackgroundResource(R.drawable.earth);
	         	      
	         	        Marker marker = googleMap.addMarker(new MarkerOptions().
	       	    	         position(TutorialsPoint).title(""));
	       	         CameraPosition cameraPosition = new CameraPosition.Builder().target(
	       		    		  TutorialsPoint).zoom(4).build();
	       	         googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	       	         marker.remove();
	       	         marker.setVisible(false);
	       	         marker.setVisible(false);


	         	      } catch (Exception e) {
	         	         e.printStackTrace();
	         	      }
	                       
	                    } else {
	                    	 try { 
	             	            if (googleMap == null) {
	             	               googleMap = ((MapFragment) getFragmentManager().
	             	               findFragmentById(R.id.map)).getMap();
	             	            }
	             	         googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	             	        tgbutton.setBackgroundResource(R.drawable.aerial);
	             	       Marker marker = googleMap.addMarker(new MarkerOptions().
	          	    	         position(TutorialsPoint).title(""));
	          	         CameraPosition cameraPosition = new CameraPosition.Builder().target(
	          		    		  TutorialsPoint).zoom(4).build();
	          	         googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	          	         marker.remove();
	          	         marker.setVisible(false);

	             	      } catch (Exception e) {
	             	         e.printStackTrace();
	             	      }
	                       
	                    }
	            }
	        });
	      
	     signout.setOnClickListener(new View.OnClickListener() {
				
	            
	        	public void onClick(View v) {
	        		timer.cancel();
	        		LiveTrack.doAsynchronousTask.cancel();
	        		LoginActivity.usernamepassed="";
	       VehichleArrayAdapter.data.clear();
	       DashboardActivity.vehicleall.clear();
	       vehiclehistory1.clear();
	       vehiclehistory.clear();
	       HistoryTrack.vehiclehistory1.clear();
	       LoginActivity.usernamepassed="";
	        		Intent intentSignUP=new Intent(getApplicationContext(),LoginActivity.class);
   			startActivity(intentSignUP);
	        	}
		 });
	    
	     
	   //  timercalling();
	    }
	 
	    /**
	     * function to load map. If map is not created it will create it for you
	     * */
	    private void initilizeMap() {
	        if (googleMap == null) {
	            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
	                    R.id.map)).getMap();
	 
	            // check if map is created successfully or not
	            if (googleMap == null) {
	                Toast.makeText(getApplicationContext(),
	                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
	                        .show();
	            }
	        }
	    }
	    
	    public void timercalling()
	    {
	    //	final Handler handler;
	    //	 handler = new Handler();
		       timer = new Timer();
		       doAsynchronousTask = new TimerTask()
		      {       
		          @Override
		          public void run() {
		        	  runOnUiThread(new Runnable() {
		                  public void run() {       
		                      try
		                      {
		                    	  new VehiclePath().execute();
		                    	
		                    	  
		                    

		                      } 
		                      catch (Exception e) 
		                      {

		                      }
		                  }
		              });
		          }
		      };
		      timer.schedule(doAsynchronousTask, 0, 30000);
	    }

	 
	  
 
	    class VehiclePath extends AsyncTask<String,String,String>{
    		@Override
    	    protected void onPreExecute() {
    			  cDialog = new ProgressDialog(LiveTrack.this);
    	          cDialog.setMessage("Please wait...");
    	          cDialog.setIndeterminate(false);
    	          //cDialog.setCancelable(true);
    	          cDialog.show();
    		}
    			
    			

    			@SuppressWarnings("deprecation")
				@Override
    			protected String doInBackground(String... args) {
    				// TODO Auto-generated method stub
    			
    				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
    				
    	             params1.add(new BasicNameValuePair("org_id", LoginActivity.orgid));
    	             params1.add(new BasicNameValuePair("vechicle_reg_no", vehicle_reg_no));
    	          
    	           
    	             jArray = jsonParser.makeHttpRequest(vehicleliveurl, "POST", params1);
    			
    			   Log.i("tagconvertstr", "["+jArray+"]");
    			   succy="fail";
    			    try
    			    {
    			    	if(jArray != null){
    			    	
    			    	JSONObject c = jArray.getJSONObject(TAG_SRES);
    			    	//Log.i("tagconvertstr", "["+c+"]");
    			    	user = c.getJSONArray(TAG_VEHICLE_ARRAY);
    			    	//Log.i("tagconvertstr1", "["+user+"]");
    			    	//System.out.println("size of user lenght"+user.length());
    			    	if(user.length()==0)
    			    	{
    			    		succy="fail";
    			    	}
    			    	else
    			    	{
    			    		succy="true";
    			    	}
    			    	for(int i=0;i<user.length();i++)
    			    	{
    			    		int p=vehiclehistory.size();
    			    	//	System.out.println("value of p"+p);
    			    		JSONObject c1 = user.getJSONObject(i);
    			    		JSONObject c2 = c1.getJSONObject(TAG_SRES);
    			    		
    			    	  
    			    		
    			           
    			            vehicle_reg_numb = c2.getString(TAG_Vechicle_REG);
    			            latitude= c2.getString(TAG_Latitude);
    			        	 longitude = c2.getString(TAG_Longitude);
    			        	speed = c2.getString(TAG_Speed);
    			        	exceed_speed_limit=c2.getString(TAG_Exceed_Speed);
    			        	bus_tracking_timestamp = c2.getString(TAG_bus_tracking_timestamp);
    			        	address=c2.getString(TAG_address);
    			        	 map.put(TAG_Latitude+p,latitude);
    			        	 map.put(TAG_Longitude+p,longitude);
    			        	 map.put(TAG_Speed+p, speed);
    			        	 map.put(TAG_Exceed_Speed+p, exceed_speed_limit);
    			        	 map.put(TAG_address+p, address);
    			        	 map.put(TAG_bus_tracking_timestamp+p, bus_tracking_timestamp);
    			        	
    			        	
    			        	vehiclehistory.add(p,map);
    			        	// vehiclehistory1=vehiclehistory;
    			        	
    			       // 	System.out.println("map values"+map);
    			    	//	System.out.println("Values for vehiclehistory list"+vehiclehistory1);
    			    		// System.out.println("size of arraylist::"+vehiclehistory1.size());
    			    		
    			    	}
    			    	
    			    	}
    			    	
    			    	}catch (JSONException e) {
    			    		
    			    		alertcheck=alertDialog.isShowing();
        					
    			    		if(alertcheck.booleanValue()==true)
    			    		{
    			    			System.out.println("alert check value::"+alertcheck);
    			    			alertDialog.dismiss();
    			    		}
    			    		alertDialog = new AlertDialog.Builder(
    								LiveTrack.this).create();
    						// Setting Dialog Title
    						
    						alertDialog.setTitle("INFO!");

    						// Setting Dialog Message
    						alertDialog.setMessage("No location's found.");

    						// Setting Icon to Dialog
    						alertDialog.setIcon(R.drawable.delete);
    						

    						// Setting OK Button
    						alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

    									public void onClick(final DialogInterface dialog,
    											final int which) {
    										// Write your code here to execute after dialog
    										// closed
    										
    									}
    								});

    						// Showing Alert Message
    						alertDialog.show();
    			        e.printStackTrace();
    			    }
    			   
    			    cDialog.dismiss();
    				return null;
    			}
    			//@SuppressWarnings("deprecation")
				@Override
    			protected void onPostExecute(String file_url) {
				
					
    				 super.onPostExecute(file_url);
    				 ArrayList<LatLng> points = null;
      			      PolylineOptions polyLineOptions = null;
      			      points = new ArrayList<LatLng>();
      			     googleMap.clear();
      			        polyLineOptions = new PolylineOptions(); 
       			//	System.out.println("vehicle size"+vehiclehistory1.size());
    				// System.out.println("size of vehicle history in post execute"+vehiclehistory.size());
    				 cDialog.dismiss();
    				int sizeminusone;
    				sizeminusone=vehiclehistory.size()-1;
    			//	System.out.println("size of vehicle history in post execute"+vehiclehistory.size());
    				for (int k = 0; k < vehiclehistory.size(); k++)
    				{
   					// System.out.println("k value"+k);
   					// System.out.println("value of index::"+vehiclehistory.get(k));
   					 LatLng pinLocation = new LatLng(Double.parseDouble(vehiclehistory.get(k).get(TAG_Latitude+k)), Double.parseDouble(vehiclehistory.get(k).get(TAG_Longitude+k)));
   					// System.out.println("pin location"+pinLocation);
   					 points.add(pinLocation);
   					 String titlevalue="Speed:"+vehiclehistory.get(k).get(TAG_Speed+k)+" km/hr "+"Date:"+vehiclehistory.get(k).get(TAG_bus_tracking_timestamp+k);
   					 String snippetval=titlevalue+"\n"+"Address:"+vehiclehistory.get(k).get(TAG_address+k);
   					 String date="Date:"+vehiclehistory.get(k).get(TAG_bus_tracking_timestamp+k);
   				
   					if(sizeminusone!=k)
   					{
   						
   					// System.out.println("if index and size is not same");
   	   					  marker = new MarkerOptions().position(pinLocation).snippet(snippetval);
   	   				 marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.click));
   	   					  marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin));
   	   				// marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.click));
   	   				      googleMap.addMarker(marker);
   	   				      //callone();
   	   				      
   	   					 
   					}
   					 else if(sizeminusone==k)
   					 {
   						 
   						// System.out.println("k value"+k);
   						// System.out.println("if index and size is same asc");
   						
   						  marker= new MarkerOptions().position(pinLocation).snippet(snippetval);
   						
   						  marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin));
       				      googleMap.addMarker(marker);
       				     
       				
       				      CameraPosition cameraPosition = new CameraPosition.Builder().target(
       				    		  pinLocation).zoom(18).build();
       				 
       				googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

   					 }
   					 
   					 if(vehiclehistory.get(k).get(TAG_Exceed_Speed+k).equals("1"))
   					 {
   						 marker = new MarkerOptions().position(pinLocation).snippet(snippetval);
   						
      					  marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pink_pin));
      				      googleMap.addMarker(marker); 
   					 }
                        
   				 }
    				 polyLineOptions.addAll(points);
				        polyLineOptions.width(2);
				        polyLineOptions.color(Color.BLACK);
	    				 googleMap.addPolyline(polyLineOptions);
				
    				
    				 
    				if(succy.equalsIgnoreCase("fail"))
    				{
    					alertcheck=alertDialog.isShowing();
    					
			    		if(alertcheck.booleanValue()==true)
			    		{
			    			System.out.println("alert check value::"+alertcheck);
			    			alertDialog.dismiss();
			    		}
			    		alertDialog = new AlertDialog.Builder(
								LiveTrack.this).create();
						// Setting Dialog Title
						alertDialog.setTitle("INFO!");

						// Setting Dialog Message
						alertDialog.setMessage("No location's found.");

						// Setting Icon to Dialog
						alertDialog.setIcon(R.drawable.delete);
						

						// Setting OK Button
						alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

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
	    protected void onResume() {
	        super.onResume();
	        //googleMap.clear();
	        vehicle_reg_no = getIntent().getExtras().getString("vehicleregnum");
		      routeno= getIntent().getExtras().getString("routenum");
		    //  System.out.println("vehicle reg num from dashboard::"+vehicle_reg_no);
		    //  System.out.println("vehicle route num from dashboard::"+routeno);
	       // System.out.println("in resume");
	       // initilizeMap();
	        timercalling();
	    }
	   
	    
	    @Override
	    protected void onDestroy()
	    {

	    super.onDestroy();  
	  //  System.out.println("in destroy");
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

	
	
	}
