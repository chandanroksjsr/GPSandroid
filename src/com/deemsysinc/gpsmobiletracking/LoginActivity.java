package com.deemsysinc.gpsmobiletracking;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;

import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {
    static String usernamepassed,orgid;
	String successL;
	Button signin,reset;
	static EditText usrname;
	static EditText paswd;
	final Context context=this;
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	public ProgressDialog pDialog;
	String username,password;
	static String role;
	String enabled;
	String username1;
	String password1;
	
	private static final String TAG_SUCCESS1 = "success";
	private static final String TAG_USERNAME = "username";
	private static final String TAG_PASSWORD = "password";
	private static final String TAG_ROLE = "role";
	private static final String TAG_ENABLED= "enabled";
	private static final String TAG_SRESL= "serviceresponse";
	private static final String TAG_ORGID= "org_id";

   
	//private static String loginurl = "http://192.168.1.158:8888/gpsandroid/service/Login.php?service=login"; 
//	private static String loginurl = "http://192.168.1.71:8080/gpsandroid/service/Login.php?service=login";
	private static String loginurl = "http://208.109.248.89:80/gpsandroid/service/Login.php?service=login";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		    getActionBar().hide();
		setContentView(R.layout.activity_login);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativelay);
		 cd = new ConnectionDetector(getApplicationContext());
        usrname=(EditText)findViewById(R.id.username);
		paswd=(EditText)findViewById(R.id.pswd);
		signin=(Button)findViewById(R.id.signin);
		reset=(Button)findViewById(R.id.reset);
		
		 layout.setOnTouchListener(new OnTouchListener()
	        {
	            @Override
	            public boolean onTouch(View view, MotionEvent ev)
	            {
	                hideKeyboard(view);
	                return false;
	            }

				
	        });
		reset.setOnClickListener(new View.OnClickListener() {
			
            
        	public void onClick(View v) {
        		//Intent intentSignUP=new Intent(getApplicationContext(),Aboutus.class);
    			//startActivity(intentSignUP);
        		usrname.setText("");
        		paswd.setText("");
        		 
        	}
        	});
		signin.setOnClickListener(new View.OnClickListener() {
				
	            
	        	@SuppressWarnings("deprecation")
				public void onClick(View v) {
	        		isInternetPresent = cd.isConnectingToInternet();
System.out.println("is internet present:::"+isInternetPresent);
	        		 String username=usrname.getText().toString();
	        		 String password=paswd.getText().toString();
	        		
	        		if(!username.equals("")&&!password.equals(""))
	        		{
	        			
	        	     
	        			if(isInternetPresent)
	        			{
	        				System.out.println(username);
	        				System.out.println(password);
	        				usernamepassed=username;
	        				System.out.println("inside attempt login");
	        				new AttemptLogin().execute();

	        			
	        			}
	        		else
	        		{
	        			AlertDialog alertDialog = new AlertDialog.Builder(
	    						LoginActivity.this).create();

	    				// Setting Dialog Title
	    				alertDialog.setTitle("INFO!");

	    				// Setting Dialog Message
	    				alertDialog.setMessage("No network connection.");

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
	        			/* AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this,R.style.MyTheme );
		    		        
		    	            builder.setMessage("No network connection." )
		    	                .setTitle( "INFO!" )
		    	                .setIcon( R.drawable.pink_pin )
		    	                .setCancelable( false )
		    	             
		    	                .setPositiveButton( "OK", new DialogInterface.OnClickListener()
		    	                    {
		    	                        public void onClick( DialogInterface dialog, int which )
		    	                           {
		    	                        	usrname.setText("");
		    	                    		paswd.setText("");
		    	                                dialog.dismiss();
		    	                           }
		    	                        } 
		    	                    );
		    	            Dialog dialog = null;
		    	            builder.setInverseBackgroundForced(true);
		    	            
		    	            dialog = builder.create();
		    	            dialog.getWindow().setLayout(600, 400); 
		    	            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		    				dialog.show();
		    				*/
	        		}
	        		}
	        		else if(!password.equalsIgnoreCase(""))
	        		{
	        			AlertDialog alertDialog = new AlertDialog.Builder(
	    						LoginActivity.this).create();

	    				// Setting Dialog Title
	    				alertDialog.setTitle("INFO!");

	    				// Setting Dialog Message
	    				alertDialog.setMessage("Please enter username.");

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
	        			
	        			/* AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this,R.style.MyTheme );
		    		        
		    	            builder.setMessage("Please enter username." )
		    	                .setTitle( "INFO!" )
		    	                .setIcon( R.drawable.pink_pin )
		    	                .setCancelable( false )
		    	             
		    	                .setPositiveButton( "OK", new DialogInterface.OnClickListener()
		    	                    {
		    	                        public void onClick( DialogInterface dialog, int which )
		    	                           {
		    	                                dialog.dismiss();
		    	                           }
		    	                        } 
		    	                    );
		    	            Dialog dialog = null;
		    	            builder.setInverseBackgroundForced(true);
		    	            
		    	            dialog = builder.create();
		    	            dialog.getWindow().setLayout(600, 400); 
		    	            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		    				dialog.show();
		    				*/
	        		}
	        		else if(!username.equalsIgnoreCase(""))
	        		{
	        			AlertDialog alertDialog = new AlertDialog.Builder(
	    						LoginActivity.this).create();

	    				// Setting Dialog Title
	    				alertDialog.setTitle("INFO!");

	    				// Setting Dialog Message
	    				alertDialog.setMessage("Please enter password.");

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

                   /*   AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this,R.style.MyTheme );
	    		        
	    	            builder.setMessage("Please enter password." )
	    	                .setTitle( "INFO!" )
	    	                .setIcon( R.drawable.pink_pin )
	    	                .setCancelable( false )
	    	             
	    	                .setPositiveButton( "OK", new DialogInterface.OnClickListener()
	    	                    {
	    	                        public void onClick( DialogInterface dialog, int which )
	    	                           {
	    	                                dialog.dismiss();
	    	                           }
	    	                        } 
	    	                    );
	    	            Dialog dialog = null;
	    	            builder.setInverseBackgroundForced(true);
	    	            
	    	            dialog = builder.create();
	    	            dialog.getWindow().setLayout(600, 400); 
	    	            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	    				dialog.show();
	        			*/
	        		}
	    			else
	    			{
	    				AlertDialog alertDialog = new AlertDialog.Builder(
	    						LoginActivity.this).create();

	    				// Setting Dialog Title
	    				alertDialog.setTitle("Alert Dialog");

	    				// Setting Dialog Message
	    				alertDialog.setMessage("Enter login credentials.");

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

	    			/*	AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this,R.style.MyTheme );
	    		        
	    	            builder.setMessage("Enter login credentials." )
	    	                .setTitle( "INFO!" )
	    	                .setIcon( R.drawable.pink_pin )
	    	                .setCancelable( false )
	    	             
	    	                .setPositiveButton( "OK", new DialogInterface.OnClickListener()
	    	                    {
	    	                        public void onClick( DialogInterface dialog, int which )
	    	                           {
	    	                                dialog.dismiss();
	    	                           }
	    	                        } 
	    	                    );
	    	            Dialog dialog = null;
	    	            builder.setInverseBackgroundForced(true);
	    	            
	    	            dialog = builder.create();
	    	            dialog.getWindow().setLayout(600, 400); 
	    	            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	    				dialog.show();
	    				 */
	    		
	    			  }
	        	}
	        	
	});


	}

	 class AttemptLogin extends AsyncTask<String, String, String> {
	    	@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(LoginActivity.this);
	            pDialog.setMessage("Please wait...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();

	        }

			@Override
			protected String doInBackground(String... params) {
				
				 List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	             
	             params1.add(new BasicNameValuePair("username", usrname.getText().toString()));
	             params1.add(new BasicNameValuePair("password", paswd.getText().toString()));

	             JsonParser jLogin = new JsonParser();
	             System.out.println(usrname.getText().toString());
	             System.out.println( paswd.getText().toString());
	             JSONObject json = jLogin.makeHttpRequest(loginurl,"POST", params1);
                 System.out.println("value for json::"+json);
	             if(json!=null)
	             {
	                 try
	                 {
	                	 if(json != null)
	                	 {
	                	 System.out.println("json value::"+json);
	                	
	                	 JSONObject jUser = json.getJSONObject(TAG_SRESL);
	                	 successL = jUser.getString(TAG_SUCCESS1);
	                	 username1 = jUser.getString(TAG_USERNAME );
	                	 password1 = jUser.getString(TAG_PASSWORD);
	                	 orgid=jUser.getString(TAG_ORGID);
	                	 role=jUser.getString(TAG_ROLE);
	                	 enabled=jUser.getString(TAG_ENABLED);
	                	 System.out.println("username value:::"+username);
	                	 System.out.println("password value::"+password);
	                	 System.out.println("role value"+role);
	                	 Intent intentSignUP=new Intent(getApplicationContext(),DashboardActivity.class);
		        			startActivity(intentSignUP);
	                	 }
	                	
	                }
	                 
	                 catch(JSONException e)
	                 {
	                	 e.printStackTrace();
	                	 
	                 }
	              }
	             else{
	                	 
	            	 successL ="No"; 
		    			  } 
	                	
	                 
	    			
	    			return null;
	    		}
			@SuppressWarnings("deprecation")
			@Override
			 protected void onPostExecute(String file_url) {
		    	   super.onPostExecute(file_url);
		        System.out.println("in post execute");
		    	   pDialog.dismiss();
		          if(JsonParser.jss.equals("empty"))
		          {
		       	   System.out.println("json null value");
		       	AlertDialog alertDialog = new AlertDialog.Builder(
						LoginActivity.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("INFO!");

				// Setting Dialog Message
				alertDialog.setMessage("Server not connected.");

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
		       /*	 AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this,R.style.MyTheme );
 		        
 	            builder.setMessage("Server not connected." )
 	                .setTitle( "INFO!" )
 	                .setIcon( R.drawable.pink_pin )
 	                .setCancelable( false )
 	             
 	                .setPositiveButton( "OK", new DialogInterface.OnClickListener()
 	                    {
 	                        public void onClick( DialogInterface dialog, int which )
 	                           {
 	                                dialog.dismiss();
 	                           }
 	                        } 
 	                    );
 	            Dialog dialog = null;
 	            builder.setInverseBackgroundForced(true);
 	            
 	            dialog = builder.create();
 	            dialog.getWindow().setLayout(600, 400); 
 	            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
 				dialog.show();*/
						  pDialog.dismiss();
		          }
		          else if(successL.equalsIgnoreCase("No")){
		        	  AlertDialog alertDialog = new AlertDialog.Builder(
	    						LoginActivity.this).create();

	    				// Setting Dialog Title
	    				alertDialog.setTitle("INFO!");

	    				// Setting Dialog Message
	    				alertDialog.setMessage("Invalid username or password.");

	    				// Setting Icon to Dialog
	    				alertDialog.setIcon(R.drawable.delete);
	    				

	    				// Setting OK Button
	    				alertDialog.setButton("OK",	new DialogInterface.OnClickListener() {

	    							public void onClick(final DialogInterface dialog,
	    									final int which) {
	    								// Write your code here to execute after dialog
	    								// closed
	    								usrname.setText("");
	    	                    		paswd.setText("");
	    	                                dialog.dismiss();
	    							}
	    						});

	    				// Showing Alert Message
	    				alertDialog.show();
		        /*	  AlertDialog.Builder builder= new AlertDialog.Builder(LoginActivity.this,R.style.MyTheme );
	    		        
	    	            builder.setMessage("Invalid username and password." )
	    	                .setTitle( "INFO!" )
	    	                .setIcon( R.drawable.pink_pin )
	    	                .setCancelable( false )
	    	             
	    	                .setPositiveButton( "OK", new DialogInterface.OnClickListener()
	    	                    {
	    	                        public void onClick( DialogInterface dialog, int which )
	    	                           {
	    	                        	usrname.setText("");
	    	                    		paswd.setText("");
	    	                                dialog.dismiss();
	    	                           }
	    	                        } 
	    	                    );
	    	            Dialog dialog = null;
	    	            builder.setInverseBackgroundForced(true);
	    	            
	    	            dialog = builder.create();
	    	            dialog.getWindow().setLayout(600, 400); 
	    	            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	    				dialog.show();*/
		  			  pDialog.dismiss();
		           }
		     

			
			 }

	 }
	 @Override
	   public void onBackPressed() {
	   }
	 protected void hideKeyboard(View view)
	 {
	     InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	     in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	 }
	 
	/* @Override
	    protected Dialog onCreateDialog( int id ){
	        Dialog dialog = null;
	        
	        if ( id == ALERT_DIALOG )
	        {
	        	 AlertDialog.Builder builder= new AlertDialog.Builder( this,R.style.MyTheme );
	        
	            builder.setMessage( "Hello World" )
	                .setTitle( "INFO!" )
	                .setIcon( R.drawable.pink_pin )
	                .setCancelable( false )
	             
	                .setPositiveButton( "OK", new DialogInterface.OnClickListener()
	                    {
	                        public void onClick( DialogInterface dialog, int which )
	                           {
	                                dialog.dismiss();
	                           }
	                        } 
	                    );
	          //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	            builder.setInverseBackgroundForced(true);
	            
	            dialog = builder.create();
	            dialog.getWindow().setLayout(600, 400); 
	            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	          
	        }
	        if ( dialog == null )
	        {
	        	
	            dialog = super.onCreateDialog( id );
	            dialog.getWindow().setLayout(600, 400); 
	            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        }
	        return dialog;
	     }*/
	 
	 public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}

}
