package com.deemsysinc.gpsmobiletracking;


import javax.mail.MessagingException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactUs extends Activity {
    
   
    Boolean isInternetPresent;

	
   
    static String firstname;
    static String lastname;
    static String email;
    static String organisation;
    static  String mobile;
    static  String address1;
    static String address2;
    static  String city;
    static  String state;
    String sende_mail;
    String sender_mail;
	  String secondmail;
	  String part_id;
	 public static String sendmailoption;
    
    
	  final Context context=this;
	    JSONObject jsonE;
   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		    getActionBar().hide();
		setContentView(R.layout.contactus);
		LinearLayout layout = (LinearLayout) findViewById(R.id.layoutt);
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent= cd.isConnectingToInternet();
     
        layout.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }

			
        });
     final EditText fstname = (EditText)findViewById(R.id.e1);
     
    
       
     final   EditText lstname = (EditText)findViewById(R.id.e2);
       
     final   EditText email1 = (EditText)findViewById(R.id.e3);
        
     final   EditText organistn = (EditText)findViewById(R.id.e4);
       
     final   EditText mob = (EditText)findViewById(R.id.e5);
      
     final   EditText add1 = (EditText)findViewById(R.id.e6);
    
     final  EditText city1 = (EditText)findViewById(R.id.e8);
     
     final   EditText state1 = (EditText)findViewById(R.id.e9);
       
   
		
		final	Button btn2=(Button)findViewById(R.id.btn2);
		final	Button btn3=(Button)findViewById(R.id.btn3);
		

	
	btn3.setOnClickListener(new OnClickListener() {
		 
		@Override
		public void onClick(View v) {

		    fstname.setText("");
		    lstname.setText("");
		    email1.setText("");
		    organistn.setText("");
		    mob.setText("");
		    add1.setText("");
		  
		    city1.setText("");
		    state1.setText("");
		   firstname="1";
		    
		}
	});
	

        
	
         btn2.setOnClickListener(new View.OnClickListener()
           {
             
                  int a;
			
				public void onClick(View view)
                    {
                        
                       // ended.setEnabled(false);

         
					if(isInternetPresent)
        			{
					
						  firstname = fstname.getText().toString();
						  System.out.println("first name value::"+firstname);
						     lastname = lstname.getText().toString();
						     System.out.println("last name value::"+lastname);
						    email = email1.getText().toString(); 
						    System.out.println("email  value::"+email);
						    
						    organisation = organistn.getText().toString(); 
						    System.out.println("organisation  value::"+organisation);
						    mobile = mob.getText().toString(); 
						    System.out.println("first name value::"+mobile);
						    address1 = add1.getText().toString(); 
						    System.out.println("address  value::"+address1);
						    
						    city = city1.getText().toString(); 
						    System.out.println("city name value::"+city);
						    
						     state = state1.getText().toString(); 
						     System.out.println("state name value::"+state);
						   
						    if(fstname.length()>0 && lstname.length()>0 && email1.length()>0&& organistn.length()>0&& mob.length()>0&&add1.length()>0&&city1.length()>0&&state1.length()>0){
						    
						    a=1;
						    if(fstname.length()>0){
							    if (isValidName(firstname)) {
							    	//a=1;
									
								}
							    else{
							    	
							    	a=0;
							    	fstname.setError("Invalid Firstname");
							    	
							    }}
						    if(lstname.length()>0){
							    if (isValidName(lastname)) {
							    	//a=1;
									
								}
							    else{
							    	
							    	a=0;
							    	lstname.setError("Invalid lastname");
							    	
							    }}
						    
						    if(email1.length()>0){
						    if (isValidEmail(email)) {
						    	//a=1;
								
							}
						    else{
						    	
						    	a=0;
						    	email1.setError("Invalid Email");
						    	
						    }}
						    
						    if(organistn.length()>0){
							    if (isValidOther(organisation)) {
							    	//a=1;
									
								}
							    else{
							    	
							    	a=0;
							    	organistn.setError("Invalid organisation");
							    	
							    }}
						    
						    if(mob.length()>0){
							    if (isValidNumber(mobile)) {
							    	//a=1;
									
								}
							    else{
							    	
							    	a=0;
							    	mob.setError("Invalid mobile");
							    	
							    }}
						    
						    if(add1.length()>0){
							    if (isValidOther(address1)) {
							    	//a=1;
									
								}
							    else{
							    	
							    	a=0;
							    	add1.setError("Invalid address");
							    	
							    }}
						    
						    if(city1.length()>0){
							    if (isValidOther(city)) {
							    	//a=1;
									
								}
							    else{
							    	
							    	a=0;
							    	city1.setError("Invalid city");
							    	
							    }}
						    if(state1.length()>0){
							    if (isValidOther(state)) {
							    	//a=1;
									
								}
							    else{
							    	
							    	a=0;
							    	state1.setError("Invalid state");
							    	
							    }}}
						    else{
						    	a=0;
						    	final Dialog dialog = new Dialog(context);
				       			 dialog.setContentView(R.layout.custom_dialog);
				       			 dialog.setTitle("Enter all the required fields");
				       			 TextView txt = (TextView) dialog.findViewById(R.id.errorlog);
				       			  txt.setText("");
				       			  Button dialogButton = (Button) dialog.findViewById(R.id.release);
				       			  dialogButton.setOnClickListener(new OnClickListener()		
									 {
				       				  public void onClick(View vd) 
										{
				       					   dialog.dismiss();
				    				
				    						}
				       					});
				       			  dialog.show();
						    	
						    	
						    }
						  
						     
						if(a==1){
        				new AttemptLogin().execute();
						}
        				
        			}
					
					
					
        			else
        			{
        			final Dialog dialog = new Dialog(context);
	       			 dialog.setContentView(R.layout.custom_dialog);
	       			 dialog.setTitle("Connection Failed");
	       			 TextView txt = (TextView) dialog.findViewById(R.id.errorlog);
	       			  txt.setText("No Network Connenction.");
	       			  Button dialogButton = (Button) dialog.findViewById(R.id.release);
	       			  dialogButton.setOnClickListener(new OnClickListener()		
						 {
	       				  public void onClick(View vd) 
							{
	       					   dialog.dismiss();
	    				
	    						}
	       					});
	       			  dialog.show();
        			}


        		

					
					
                          
                          
//                          EditText e1 = (EditText)findViewById(R.id.e1);
//                          String id = e1.getText().toString();
                          
        
                    }
				
				
				private boolean isValidEmail(String email) {
					// TODO Auto-generated method stub
					
					
						String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
								+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

						Pattern pattern = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(email);
						return matcher.matches();
					}
					
				private boolean isValidName(String names) {
					// TODO Auto-generated method stub
					
					
						String EMAIL_PATTERN = "^[a-zA-Z]*$";

						Pattern pattern = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(names);
						return matcher.matches();
					}
				private boolean isValidNumber(String number) {
					// TODO Auto-generated method stub
					
					
						String EMAIL_PATTERN = "[7-9]{1}+\\-[0-9]{10}";

						Pattern pattern = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(number);
						return matcher.matches();
					}
				private boolean isValidOther(String other) {
					// TODO Auto-generated method stub
					
					
						String EMAIL_PATTERN = "^[a-zA-Z0-9@_.,-/]*$";

						Pattern pattern = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(other);
						return matcher.matches();
					}
				
				
                    });
         
       
         
           }
	
	
	
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	     switch (item.getItemId()) {
	         case android.R.id.home:
	            finish();
	      }
	     return true;
	 }
	 
	 class SendEmailAsyncTask extends AsyncTask <Void, Void, Boolean> {
		    GMailSender sender = new GMailSender("deemgpsapp@gmail.com", "pentagon7");
			private String messageall;
			private String message;
		    
		    public SendEmailAsyncTask() {
		    	}
		    @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	      
	           
		         
	        } 

			@Override
			protected Boolean doInBackground(Void... params) {
				// TODO Auto-generated method stub
				if (BuildConfig.DEBUG) Log.v(SendEmailAsyncTask.class.getName(), "doInBackground()");
		        try {
		        	/*String  firstname = fstname.getText().toString();
		        	String  lastname = lstname.getText().toString();
		        	String  email = email1.getText().toString();
		        	String  organisation = organistn.getText().toString();
		        	String  mobile = mob.getText().toString();
		        	String  address1 = add1.getText().toString();
		        	String  city = city1.getText().toString();
		        	String  state = state1.getText().toString();*/
		        	
		        	System.out.println("first name value::"+firstname);
		        	System.out.println("first name value::"+lastname);
		        	System.out.println("first name value::"+email);
		        	System.out.println("first name value::"+organisation);
		        	System.out.println("first name value::"+mobile);
		        	System.out.println("first name value::"+address1);
		        	System.out.println("first name value::"+city);
		        	System.out.println("first name value::"+state);
		        	
			
		       // String	 ack = String.format("Hi "+"%s,\n\n" +"Welcome To GPS!\n\n"+"Your  Details has been Submitted to your respective Provider Successfully.\n\n"+"Keep on Answering your Weekly Assessments.\n\n"
					    		//+"Thank you!",firstname);
		        	 String part_name=String.format("Hi");
		        	 String part_name1=String.format("Contact Us Information");
		        	//String part_name = null;
		        	//String part_name1 = null;
				    
		        	{
		        		    message=String.format("Hi "+"%s\n\n"+"Thanks for Contacting Us... "+"\n\n"+"Our Sales & Support team will contact you shortly "+"\n\n"+"\n\n",firstname);
		        		    messageall=String.format("Hi "+"\n\n"+"Below are the details of the contacted person "+"\n\n"+"first Name: "+"%s\n\n"+"last name: "+"%s\n\n"+"Email: "+"%s\n\n"+"Organisation: "+"%s\n\n"+"Mobile: "+"%s\n\n"+"address: "+"%s\n\n"+"city: "+"%s\n\n"+"State: "+"%s"+"\n\n"+"\n\n",firstname,lastname,email,organisation,mobile,address1,city,state);
		        		    System.out.println(messageall);
		        		    System.out.println(message);
		        		  System.out.println("both in email");
	 //sender.sendMail("Hello",ack,message, "imans.vijay@gmail.com","amskala76@gmail.com")  ;      		  
	 sender.sendMail(part_name,messageall, "deemgpsapp@gmail.com","udayjc@icloud.com")  ;
     sender.sendMail(part_name1,message, "deemgpsapp@gmail.com",email)  ;
     
}
		        	
			         return true;
		        }
		        catch (AuthenticationException e) {
		            Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
		            e.printStackTrace();
		            return false;
		        } 
		        catch (MessagingException e) {
		        	  try {
					//	sender.sendMail("GPS Report",messageall, "imans.vijay@gmail.com",sender_mail);
						
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            e.printStackTrace();
		            return false;
		        } catch (Exception e) {
		            e.printStackTrace();
		            return false;
		        }
		   
			} 
       }

	




class AttemptLogin extends AsyncTask<String,String,String>{

	   private ProgressDialog pDialog;
	
	public static final String urlE = "http://192.168.1.158:8888/Android/service/view2.php?service=insert";
	 
	  
	    JSONObject jsonE;

	@Override
     protected void onPreExecute() {
         super.onPreExecute();
         pDialog = new ProgressDialog(ContactUs.this);

         pDialog.setMessage("Sending Data.Please wait...");
         
         pDialog.setIndeterminate(false);
         pDialog.setCancelable(true);
         pDialog.show();
         
         
         
	         
     } 
	    
	 @Override
		protected String doInBackground(String... params) {
		
	
			List<NameValuePair> paramsE = new ArrayList<NameValuePair>();
		
			 paramsE.add(new BasicNameValuePair("firstname",ContactUs.firstname));

             paramsE.add(new BasicNameValuePair("lastname", ContactUs.lastname));

             paramsE.add(new BasicNameValuePair("email", ContactUs.email));

             paramsE.add(new BasicNameValuePair("organisation", ContactUs.organisation));

             paramsE.add(new BasicNameValuePair("mobile", ContactUs.mobile));


             paramsE.add(new BasicNameValuePair("address1",ContactUs.address1));

           //  paramsE.add(new BasicNameValuePair("address2", address2));

             paramsE.add(new BasicNameValuePair("city",ContactUs.city));

             paramsE.add(new BasicNameValuePair("state", ContactUs.state));
			
			 JsonParser jLogin = new JsonParser();
			 
			 JSONObject json = jLogin.makeHttpRequest(urlE,"POST", paramsE);
        	 System.out.println("value for json::"+json);

        	 
			return null;
		
     				  }
	 
	 
	@Override
	 protected void onPostExecute(String file_url) {
    	   super.onPostExecute(file_url);
        System.out.println("in post execute");
        new SendEmailAsyncTask().execute();
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
				  txt.setText("Error connecting Database");
				  Button dialogButton = (Button) dialog.findViewById(R.id.release);
				  dialogButton.setOnClickListener(new OnClickListener() {
					  public void onClick(View vd) {
						   dialog.dismiss();
					
					}
				});
				  dialog.show();
				  pDialog.dismiss();
          }
          else{
        	  
        	  final Dialog dialog = new Dialog(context);
           	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      				// final Dialog dialog = new Dialog(context);
      				 dialog.setContentView(R.layout.custom_dialog);
      				// dialog.setTitle("INFO!");
      				 dialog.setCancelable(false);
      				 dialog.setCanceledOnTouchOutside(false);
      				 TextView txt = (TextView) dialog.findViewById(R.id.errorlog);
      				  txt.setText("Msg sent");
      				  Button dialogButton = (Button) dialog.findViewById(R.id.release);
      				  dialogButton.setOnClickListener(new OnClickListener() {
      					  public void onClick(View vd) {
      						   dialog.dismiss();
      					
      					}
      				});
      				  dialog.show();
      				  pDialog.dismiss();
                
        	  
        	  
        	  
          }
        


}
	}
protected void hideKeyboard(View view)
{
    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
}
}
          

