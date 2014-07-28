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
import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
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
import android.widget.RelativeLayout;

public class ContactUs extends Activity {
    
   
    Boolean isInternetPresent;

    EditText fstname,organistn,add1,city1,state1;
    EditText lstname,email1;
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
	   EditText mob;
	 public static String sendmailoption;
    
    
	  final Context context=this;
	    JSONObject jsonE;
   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		    getActionBar().hide();
		setContentView(R.layout.contactus);
		    mob = (EditText)findViewById(R.id.e5);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.layoutt);
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent= cd.isConnectingToInternet();
        InputFilter filter = new InputFilter() {

            
    		@Override
    		public CharSequence filter(CharSequence source, int start, int end,
    				Spanned dest, int dstart, int dend) {
    			
    			 if (source.length() > 0) 
    			 {
if(source.length()<10)
{
	
	
	
	
                     if (!Character.isDigit(source.charAt(0)))
                         return "";
                     else {
                        
                         if(dest.toString().length()>13)
                         {
                        	 System.out.println(dest.toString());
                        	 
                        	 System.out.println("in first if");
                        	 System.out.println("in dest condition");
                        	 return "";
                         }
                         else if (dstart == 3) {
                             return source + ") ";
                         } else if (dstart == 0)
                         {
                        	 System.out.println(source.toString());
                        	 if(source.toString().equals("7")||source.toString().equals("8")||source.toString().equals("9"))
                        	 {
                             return "(" + source;
                        	 }
                        	 else
                        	 {
                        		 return "";
                        	 }
                         } else if ((dstart == 9))
                             return "-" + source;
                         else if (dstart >= 14)
                             return "";
                     }
}
else
{
	 System.out.println("in first else");
	return"";
}

                 } else {
                	 System.out.println("in dstart");
                	 return "";
                 }
    			
    			 
    			return null;
    		}
        };
		
            mob.setFilters(new InputFilter[] { filter });
        

        layout.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }

			
        });
     fstname = (EditText)findViewById(R.id.e1);
     lstname = (EditText)findViewById(R.id.e2);
     email1 = (EditText)findViewById(R.id.e3);
      organistn = (EditText)findViewById(R.id.e4);
      add1 = (EditText)findViewById(R.id.e6);
     city1 = (EditText)findViewById(R.id.e8);
     state1 = (EditText)findViewById(R.id.e9);
       
   
		
		final	Button btn2=(Button)findViewById(R.id.btn2);
		final	Button btn3=(Button)findViewById(R.id.btn3);
		
		fstname.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	CharSequence ss = s;
		    	 String mStr = fstname.getText().toString();
		    	 String str = s.toString();
		            if(str.length() > 0 && str.startsWith(" ")){
		                
		            	fstname.setText("");
		            }else{
		                
		            }

		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    	
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		        }

			
		    });
		lstname.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	CharSequence ss = s;
		    	 String mStr = lstname.getText().toString();
		    	 String str = s.toString();
		            if(str.length() > 0 && str.startsWith(" ")){
		                
		            	lstname.setText("");
		            }else{
		                
		            }

		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    	
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		        }

			
		    });
		email1.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	
		    	 String str = s.toString();
		            if(str.length() > 0 && str.startsWith(" ")){
		                
		            	email1.setText("");
		            }else{
		                
		            }

		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    	
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		        }

			
		    });
		
		organistn.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	
		    	 String str = s.toString();
		            if(str.length() > 0 && str.startsWith(" ")){
		                
		            	organistn.setText("");
		            }else{
		               
		            }

		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    	
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		        }

			
		    });
		mob.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	
		    	 String str = s.toString();
		        
		          //  if(str.length()>14)
		         //   {
		            	 
		            	// mob.setSelection(mob.getText().length());
		            /*	System.out.println("sting value"+str);
		            	int edittextstr=str.length();
		            	str.substring(0,13);
		            	System.out.println(str);
		            	System.out.println(edittextstr);
		            	mob.setText(str);*/
		            	//mob.setText(mob.getText().delete(14 ,edittextstr));
		           // }

		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    /*	if (mob.getText().toString().length() > 14) {
		    		System.out.println("sting value");
		    		String subi=mob.getText().toString();
		    		subi=subi.substring(0,13);
		    		System.out.println("sting value"+subi);
		           // mob.setText(mob.getText().toString().subSequence(0, 13));
		            mob.setText(subi);
		            mob.setSelection(mob.getText().length());
		           // mob.setText("Just 10 Number");
		        }else{
		           
		        }*/
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		        }

			
		    });
		add1.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	
		    	 String str = s.toString();
		            if(str.length() > 0 && str.startsWith(" ")){
		                
		            	add1.setText("");
		            }else{
		                
		            }

		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    	
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		        }

			
		    });
		city1.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	
		    	 String str = s.toString();
		            if(str.length() > 0 && str.startsWith(" ")){
		                
		            	city1.setText("");
		            }else{
		                
		            }

		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    	
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		        }

			
		    });
		state1.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start, int before, int count) {
		    	
		    	 String str = s.toString();
		            if(str.length() > 0 && str.startsWith(" ")){
		                
		            	state1.setText("");
		            }else{
		               
		            }

		    }

		    @Override
		    public void afterTextChanged(Editable s) {
		    	
		    }

		    @Override
		    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		    	
		        }

			
		    });
	
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
			
				@SuppressWarnings("deprecation")
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
						    	
								   {
									    if (firstname.length()>3 &&isValidName(firstname)) {
									    	{
											    if (lastname.length()>3&& isValidName(lastname)) {
											    	{
													    if (isValidEmail(email)) {
													    	{
																    if (isValidOther1(organisation)) {
																    	  
																	    {
																		    if (isValidNumber(mobile)) {
																		    	  
																			    {
																				    if (address1.length()>0) {
																				    	 
																					    {
																						    if (isValidOther1(city)) {
																						    	 {
																									    if (isValidOther1(state)) {
																									    	a=1;
																											
																										}
																									    else{
																									    	
																									    	a=0;
																									    	AlertDialog alertDialog = new AlertDialog.Builder(
																													ContactUs.this).create();

																											// Setting Dialog Title
																											alertDialog.setTitle("Invalid state");

																											// Setting Dialog Message
																											alertDialog.setMessage("State should contain only alphabets." );

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
																									    /*	AlertDialog.Builder builder= new AlertDialog.Builder(ContactUs.this,R.style.MyTheme );
																						    		        
																						    	            builder.setMessage("Please enter valid state." )
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
																									    	
																									    }}
																								
																							}
																						    else{
																						    	
																						    	a=0;
																						    	AlertDialog alertDialog = new AlertDialog.Builder(
																										ContactUs.this).create();

																								// Setting Dialog Title
																								alertDialog.setTitle("Invalid city");

																								// Setting Dialog Message
																								alertDialog.setMessage("City should contain only alphabets.");

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
																						  /*  	AlertDialog.Builder builder= new AlertDialog.Builder(ContactUs.this,R.style.MyTheme );
																			    		        
																			    	            builder.setMessage("Please enter valid city." )
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
																						    	
																						    }}
																						
																					}
																				    else{
																				    	
																				    	a=0;
																				    	AlertDialog alertDialog = new AlertDialog.Builder(
																								ContactUs.this).create();

																						// Setting Dialog Title
																						alertDialog.setTitle("Invalid address");

																						// Setting Dialog Message
																						alertDialog.setMessage("Please enter valid address." );

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
																				    	/*AlertDialog.Builder builder= new AlertDialog.Builder(ContactUs.this,R.style.MyTheme );
																	    		        
																	    	            builder.setMessage("Please enter valid address." )
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
																				    	
																				    }}
																				
																			}
																		    else{
																		    	
																		    	a=0;
																		    	AlertDialog alertDialog = new AlertDialog.Builder(
																						ContactUs.this).create();

																				// Setting Dialog Title
																				alertDialog.setTitle("Invalid mobile number");

																				// Setting Dialog Message
																				alertDialog.setMessage("Mobile number should contain only numbers." );

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
																		    /*	AlertDialog.Builder builder= new AlertDialog.Builder(ContactUs.this,R.style.MyTheme );
															    		        
															    	            builder.setMessage("Please enter valid mobile number." )
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
																		    	
																		    }}
																		
																	}
																    else{
																    	
																    	a=0;
																    	AlertDialog alertDialog = new AlertDialog.Builder(
																				ContactUs.this).create();

																		// Setting Dialog Title
																		alertDialog.setTitle("Invalid organization name");

																		// Setting Dialog Message
																		alertDialog.setMessage("Organization name should contain only alphabets." );

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
																    	/*AlertDialog.Builder builder= new AlertDialog.Builder(ContactUs.this,R.style.MyTheme );
													    		        
													    	            builder.setMessage("Please enter valid organisation." )
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
																    }}
															
														}
													    else{
													    	
													    	a=0;
													    	AlertDialog alertDialog = new AlertDialog.Builder(
																	ContactUs.this).create();

															// Setting Dialog Title
															alertDialog.setTitle("Invalid email");

															// Setting Dialog Message
															alertDialog.setMessage("Email should contain alphabets,numbers,@ . _" );

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
													  /*  	AlertDialog.Builder builder= new AlertDialog.Builder(ContactUs.this,R.style.MyTheme );
										    		        
										    	            builder.setMessage("Please enter valid email." )
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
													    }}
													
												}
											    else{
											    	
											    	a=0;
											    	AlertDialog alertDialog = new AlertDialog.Builder(
															ContactUs.this).create();

													// Setting Dialog Title
													alertDialog.setTitle("Invalid lastname");

													// Setting Dialog Message
													alertDialog.setMessage("Lastname should contain only alphabets,4-16 characters." );

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
											    	/*AlertDialog.Builder builder= new AlertDialog.Builder(ContactUs.this,R.style.MyTheme );
								    		        
								    	            builder.setMessage("Please enter valid lastname." )
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
											    	
											    }}
											
										}
									    else{
									    	
									    	a=0;
									    	AlertDialog alertDialog = new AlertDialog.Builder(
													ContactUs.this).create();

											// Setting Dialog Title
											alertDialog.setTitle("Invalid Firstname");

											// Setting Dialog Message
											alertDialog.setMessage("Firstname should contain only alphabets,4-16 characters." );

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
									    	/*AlertDialog.Builder builder= new AlertDialog.Builder(ContactUs.this,R.style.MyTheme );
						    		        
						    	            builder.setMessage("Please enter valid firstname." )
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
									    	
									    }}
								    
								    
								    }
						    else{
						    	
						    	a=0;
						     	AlertDialog alertDialog = new AlertDialog.Builder(
										ContactUs.this).create();

								// Setting Dialog Title
								alertDialog.setTitle("INFO!");

								// Setting Dialog Message
								alertDialog.setMessage("Please enter all fields." );

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
						    /*	AlertDialog.Builder builder= new AlertDialog.Builder(ContactUs.this,R.style.MyTheme );
			    		        
			    	            builder.setMessage("Please enter all the required fields." )
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
						  
						     
						if(a==1){
        				new AttemptLogin().execute();
						}
        				
        			}
					
					
					
        			else
        			{
        			 	AlertDialog alertDialog = new AlertDialog.Builder(
        						ContactUs.this).create();

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
        								
        								
        							}
        						});

        				// Showing Alert Message
        				alertDialog.show();
        				
        				/*AlertDialog.Builder builder= new AlertDialog.Builder(ContactUs.this,R.style.MyTheme );
	    		        
	    	            builder.setMessage("No network connection." )
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
					
					
						String EMAIL_PATTERN = "[a-zA-Z]+[a-zA-Z ]*$";

						Pattern pattern = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(names);
						return matcher.matches();
					}
				private boolean isValidNumber(String number) {
					// TODO Auto-generated method stub
					
					
					String PHONE_REGEX ="\\([1-9]{1}[0-9]{2}\\) [0-9]{3}\\-[0-9]{4}$";

						Pattern pattern = Pattern.compile(PHONE_REGEX);
						Matcher matcher = pattern.matcher(number);
						return matcher.matches();
					}
				private boolean isValidOther(String other) {
					// TODO Auto-generated method stub
					
					
						String EMAIL_PATTERN = "[a-zA-Z0-9]+[a-zA-Z0-9@_.,-/\n ]*$";

						Pattern pattern = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(other);
						return matcher.matches();
					}
				private boolean isValidOther1(String names) {
					// TODO Auto-generated method stub
					
					
						String EMAIL_PATTERN = "[a-zA-Z]+[a-zA-Z ]*$";

						Pattern pattern = Pattern.compile(EMAIL_PATTERN);
						Matcher matcher = pattern.matcher(names);
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
		        
		        	
		        	
		        	
			
		       
		        	 String part_name=String.format("Hi");
		        	 String part_name1=String.format("Contact Us Information");
		        
		        	{
		        		    message=String.format("Hi "+"%s\n\n"+"Thanks for Contacting Us... "+"\n\n"+"Our Sales & Support team will contact you shortly "+"\n\n"+"\n\n",firstname);
		        		    messageall=String.format("Hi "+"\n\n"+"Below are the details of the contacted person "+"\n\n"+"first Name: "+"%s\n\n"+"last name: "+"%s\n\n"+"Email: "+"%s\n\n"+"Organisation: "+"%s\n\n"+"Mobile: "+"%s\n\n"+"address: "+"%s\n\n"+"city: "+"%s\n\n"+"State: "+"%s"+"\n\n"+"\n\n",firstname,lastname,email,organisation,mobile,address1,city,state);
		        		    System.out.println(messageall);
		        		         		  
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
	
	//public static final String urlE = "http://192.168.1.158:8888/gpsandroid/service/Contact.php?service=insert";
	//public static final String urlE = "http://192.168.1.71:8080/gpsandroid/service/Contact.php?service=insert";
	public static final String urlE = "http://208.109.248.89:80/gpsandroid/service/Contact.php?service=insert";
	 
	  
	    JSONObject jsonE;

	@Override
     protected void onPreExecute() {
         super.onPreExecute();
         pDialog = new ProgressDialog(ContactUs.this);

         pDialog.setMessage("Please wait...");
         
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
	 
	 
	@SuppressWarnings("deprecation")
	@Override
	 protected void onPostExecute(String file_url) {
    	   super.onPostExecute(file_url);
        System.out.println("in post execute");
        new SendEmailAsyncTask().execute();
    	   pDialog.dismiss();
          if(JsonParser.jss.equals("empty"))
          {
       	   System.out.println("json null value");
       	AlertDialog alertDialog = new AlertDialog.Builder(
				ContactUs.this).create();

		// Setting Dialog Title
		alertDialog.setTitle("INFO!");

		// Setting Dialog Message
		alertDialog.setMessage("Error connecting database.");

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
       /*	AlertDialog.Builder builder= new AlertDialog.Builder(ContactUs.this,R.style.MyTheme );
	        
          builder.setMessage("Error connecting database." )
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
          }
          else{
        	 
        		AlertDialog alertDialog = new AlertDialog.Builder(
						ContactUs.this).create();

				// Setting Dialog Title
				alertDialog.setTitle("INFO!");

				// Setting Dialog Message
				alertDialog.setMessage("Message sent.");

				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.tick);
				

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
        	 /* AlertDialog.Builder builder= new AlertDialog.Builder(ContactUs.this,R.style.MyTheme );
		        
	            builder.setMessage("Mail sent." )
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
	}
protected void hideKeyboard(View view)
{
    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
}
}
          

