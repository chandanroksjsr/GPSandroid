package com.deemsysinc.gpsmobiletracking;






import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
@SuppressWarnings("deprecation")
public class TrackingActivity extends TabActivity implements OnTabChangeListener{
	TabHost tabHost;
	 private int year;
	    private int month;
	    private int day;
	    public static StringBuilder date;
	    static String vehicle_reg_no,routeno;
	    String userrole;
	    static final int DATE_PICKER_ID = 1111; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		    getActionBar().hide();
		setContentView(R.layout.tracking);
		userrole=LoginActivity.role;
	      vehicle_reg_no = getIntent().getExtras().getString("vehicleregnum");
	      routeno= getIntent().getExtras().getString("routenum");
	      System.out.println("value from dashboard for vehicle reg num::"+vehicle_reg_no);
	      System.out.println("value from dashboard for vehicle reg num::"+routeno);
		 final Calendar c = Calendar.getInstance();
	        year  = c.get(Calendar.YEAR);
	        month = c.get(Calendar.MONTH);
	        day   = c.get(Calendar.DAY_OF_MONTH);
		  tabHost = getTabHost();
		  tabHost.setOnTabChangedListener(this);
		  
	     System.out.println("role fo "+userrole);
	      if(!userrole.equalsIgnoreCase("ROLE_PCLIENT")&&!userrole.equalsIgnoreCase("ROLE_FCLIENT"))
	      {
	    	  System.out.println("in condition");
	    	  TabHost.TabSpec spec;
		      Intent intent;
		      intent = new Intent().setClass(this, LiveTrack.class);
		      spec = tabHost.newTabSpec("Live Track").setIndicator("")
		                    .setContent(intent);
		      tabHost.addTab(spec);
		      intent = new Intent().setClass(this, HistoryTrack.class);
		      spec = tabHost.newTabSpec("History Track").setIndicator("")
		                    .setContent(intent);  
		      tabHost.addTab(spec);
		      intent = new Intent().setClass(this, AlertMsg.class);
		      spec = tabHost.newTabSpec("Alert Message").setIndicator("")
		                    .setContent(intent);
		      tabHost.addTab(spec);
		      TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
		        tv.setText("Live Track");
		      tabHost.getTabWidget().getChildAt(0);
		      TextView tv1 = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
		        tv1.setText("History Track");
		      tabHost.getTabWidget().getChildAt(1);
		      TextView tv2 = (TextView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
		        tv2.setText("Alert Message");
			  tabHost.getTabWidget().getChildAt(2);
			  tabHost.getTabWidget().setCurrentTab(0);
		      
	      }
	      else
	      {
	    	  TabHost.TabSpec spec;
		      Intent intent;
		      intent = new Intent().setClass(this, LiveTrack.class);
		      spec = tabHost.newTabSpec("Live Track").setIndicator("")
		                    .setContent(intent);
		      tabHost.addTab(spec);
		      intent = new Intent().setClass(this, HistoryTrack.class);
		      spec = tabHost.newTabSpec("History Track").setIndicator("")
		                    .setContent(intent);  
		      tabHost.addTab(spec);
		      TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
		        tv.setText("Live Track");
		      tabHost.getTabWidget().getChildAt(0);
		      TextView tv1 = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
		        tv1.setText("History Track");
		      tabHost.getTabWidget().getChildAt(1);
			 
			  tabHost.getTabWidget().setCurrentTab(0);
	      }
	   /*   getTabWidget().getChildAt(1).setOnClickListener(new OnClickListener() {

	          @Override
	          public void onClick(View v) {
	              tabHost.setCurrentTab(1);
	            //  Toast.makeText(getApplicationContext(), "Clicked", 35).show();
	              showDialog(DATE_PICKER_ID);
	              // Add pop up code here
	          }
	      });*/
}

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
	    for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
			{
		    	if(i==0)
		    	{
		    		TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
		        tv.setText("Live Track");
		    	    tabHost.getTabWidget().getChildAt(i);
		    	}
		    	else if(i==1)
		    	{
		    		TextView tv = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
			        tv.setText("History Track");
		    		tabHost.getTabWidget().getChildAt(i);
		    	}
		    		
		    	else if(i==2)
		    	{
		    		TextView tv = (TextView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
			        tv.setText("Alert Message");
		    		tabHost.getTabWidget().getChildAt(i);
		    	}
		    		
		    }
		    
		    
		    Log.i("tabs", "CurrentTab: "+tabHost.getCurrentTab());
		    
		    if(tabHost.getCurrentTab()==0){
		    	//LiveTrack.timercalling();
		    	TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
		        tv.setText("Live Track");
		    	tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());
		    }
		    else if(tabHost.getCurrentTab()==1){
		    	LiveTrack.timer.cancel();
		    	LiveTrack.doAsynchronousTask.cancel();
		    	TextView tv = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
		        tv.setText("History Track");
		        System.out.println("timer cancelled");
		    	tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());
	           }
		    else if(tabHost.getCurrentTab()==2){
		    	LiveTrack.timer.cancel();
		    	LiveTrack.doAsynchronousTask.cancel();
		    	TextView tv = (TextView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
		        tv.setText("Alert Message");
		        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab());
		    }
		
	}
	
	  @Override
	    protected Dialog onCreateDialog(int id) {
	        switch (id) {
	        case DATE_PICKER_ID:
	             
	           
	            return new DatePickerDialog(this, pickerListener, year, month,day);
	        }
	        return null;
	    }
	 
	    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
	 
	        // when dialog box is closed, below method will be called.
	        @Override
	        public void onDateSet(DatePicker view, int selectedYear,
	                int selectedMonth, int selectedDay) {
	             
	            year  = selectedYear;
	            month = selectedMonth;
	            day   = selectedDay;
	 
	            // Show selected date 
	          date=(new StringBuilder().append(month + 1)
	                    .append("-").append(day).append("-").append(year)
	                    .append(" "));
	          System.out.println("date in dfgsdfg second tab::"+date);
	     
	           }

			
	        };
	        @Override
			   public void onBackPressed() {
			   }

}