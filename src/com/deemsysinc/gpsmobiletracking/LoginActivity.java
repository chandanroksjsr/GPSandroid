package com.deemsysinc.gpsmobiletracking;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

   
	private static String loginurl = "http://192.168.1.71:8080/gpsandroid/service/Login.php?service=login"; 

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
        		usrname.setText("");
        		paswd.setText("");
        	}
        	});
		signin.setOnClickListener(new View.OnClickListener() {
				
	            
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
	        			final Dialog dialog = new Dialog(context);
	        		 	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		       			 dialog.setContentView(R.layout.custom_dialog);
		       			 dialog.setTitle("Login Failed");
		       			 TextView txt = (TextView) dialog.findViewById(R.id.errorlog);
		       			  txt.setText("No Network Connenction.");
		       			  Button dialogButton = (Button) dialog.findViewById(R.id.release);
		       			  dialogButton.setOnClickListener(new OnClickListener() {
		       				  public void onClick(View vd) {
		       					   dialog.dismiss();
		    				
		    				}
		       			});
		       			  dialog.show();
	        		}
	        		}
	        		else if(!password.equalsIgnoreCase(""))
	        		{
	        			final Dialog dialog = new Dialog(context);
	        		 	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		       			 dialog.setContentView(R.layout.custom_dialog);
		       			 dialog.setTitle("Login Failed");
		       			 TextView txt = (TextView) dialog.findViewById(R.id.errorlog);
		       			  txt.setText("Please enter username.");
		       			  Button dialogButton = (Button) dialog.findViewById(R.id.release);
		       			  dialogButton.setOnClickListener(new OnClickListener() {
		       				  public void onClick(View vd) {
		       					   dialog.dismiss();
		    				
		    				}
		       			});
		       			  dialog.show();
	        		}
	        		else if(!username.equalsIgnoreCase(""))
	        		{
	        			final Dialog dialog = new Dialog(context);
	        		 	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		       			 dialog.setContentView(R.layout.custom_dialog);
		       			 dialog.setTitle("Login Failed");
		       			 TextView txt = (TextView) dialog.findViewById(R.id.errorlog);
		       			  txt.setText("Please enter password.");
		       			  Button dialogButton = (Button) dialog.findViewById(R.id.release);
		       			  dialogButton.setOnClickListener(new OnClickListener() {
		       				  public void onClick(View vd) {
		       					   dialog.dismiss();
		    				
		    				}
		       			});
		       			  dialog.show();
	        		}
	    			else
	    			{
	    			final Dialog dialog = new Dialog(context);
	    		 	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	       			 dialog.setContentView(R.layout.custom_dialog);
	       			 dialog.setTitle("Login Failed");
	       			 TextView txt = (TextView) dialog.findViewById(R.id.errorlog);
	       			  txt.setText("Enter login credentials.");
	       			  Button dialogButton = (Button) dialog.findViewById(R.id.release);
	       			  dialogButton.setOnClickListener(new OnClickListener() {
	       				  public void onClick(View vd) {
	       					   dialog.dismiss();
	    				
	    				}
	       			});
	       			  dialog.show();
	    			  }
	        	}
	        	
	});


	}

	 class AttemptLogin extends AsyncTask<String, String, String> {
	    	@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(LoginActivity.this);
	            pDialog.setMessage("Authenticating");
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
	                	 
	                	
		    			  } 
	                	
	                 
	    			
	    			return null;
	    		}
			@Override
			 protected void onPostExecute(String file_url) {
		    	   super.onPostExecute(file_url);
		        System.out.println("in post execute");
		    	   pDialog.dismiss();
		          if(JsonParser.jss.equals("empty"))
		          {
		       	   System.out.println("json null value");
		       	 final Dialog dialog = new Dialog(context);
		     	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						// final Dialog dialog = new Dialog(context);
						 dialog.setContentView(R.layout.custom_dialog);
						// dialog.setTitle("INFO!");
						 dialog.setCancelable(false);
						 dialog.setCanceledOnTouchOutside(false);
						 TextView txt = (TextView) dialog.findViewById(R.id.errorlog);
						  txt.setText("Server not connected.");
						  Button dialogButton = (Button) dialog.findViewById(R.id.release);
						  dialogButton.setOnClickListener(new OnClickListener() {
							  public void onClick(View vd) {
								   dialog.dismiss();
							
							}
						});
						  dialog.show();
						  pDialog.dismiss();
		          }
		          else if(successL.equalsIgnoreCase("No")){
		        	   final Dialog dialog = new Dialog(context);
		           	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  			 dialog.setContentView(R.layout.custom_dialog);
		  			// dialog.setTitle("INFO!");
		  			 dialog.setCancelable(false);
		  			 dialog.setCanceledOnTouchOutside(false);
		  			 TextView txt = (TextView) dialog.findViewById(R.id.errorlog);
		  			  txt.setText("Invalid username and password.");
		  			  Button dialogButton = (Button) dialog.findViewById(R.id.release);
		  			  dialogButton.setOnClickListener(new OnClickListener() {
		  				  public void onClick(View vd) {
		  					 LoginActivity.usrname.setText("");
		   	       			  LoginActivity.paswd.setText("");
		  					   dialog.dismiss();
						
						}
		  			});
		  			  dialog.show();
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
}
