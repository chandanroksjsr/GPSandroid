package com.deemsysinc.gpsmobiletracking;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;;
public class AboutusTab extends Activity
{
	final Context context=this;
	TextView ourdevice,vehtrac,perstrack,watchtrack,mygps,support,indianad,usadd;
	 @Override
	  public void onCreate(Bundle savedInstanceState)
	    {
	      super.onCreate(savedInstanceState);
	    
	      setContentView(R.layout.about);
	      ourdevice=(TextView)findViewById(R.id.boldtext);
	      vehtrac=(TextView)findViewById(R.id.boldtext1);
	      perstrack=(TextView)findViewById(R.id.boldtext2);
	      watchtrack=(TextView)findViewById(R.id.boldtext3);
	      mygps=(TextView)findViewById(R.id.weblink);
	      support=(TextView)findViewById(R.id.emailid);
	      usadd=(TextView)findViewById(R.id.usdeemsys1);
	      indianad=(TextView)findViewById(R.id.usdeemsys);
	      ourdevice.setTypeface(null, Typeface.BOLD);
	      vehtrac.setTypeface(null, Typeface.BOLD);
	      perstrack.setTypeface(null, Typeface.BOLD);
	      watchtrack.setTypeface(null, Typeface.BOLD);
	      usadd.setTypeface(null, Typeface.BOLD);
	      indianad.setTypeface(null, Typeface.BOLD);
	      
	      mygps.setClickable(true);
	      mygps.setMovementMethod(LinkMovementMethod.getInstance());
	      String text = "<a href='http://www.mygps.technology'> www.mygps.technology </a>";
	      mygps.setText(Html.fromHtml(text));
	      
	      support.setTextColor(Color.parseColor("#fda74a"));
	             
	      support.setOnClickListener(new OnClickListener() {               
	          public void onClick(View arg0) {
	              sendEmail(context, new String[]{"support@deemsysinc.com"}, "Sending Email",
	                        "GPS V_1.0 on Android", "");
	          }
	      });
	    
	    }
	 public static void sendEmail(Context context, String[] recipientList,
	            String title, String subject, String body) {
	    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);    
	    emailIntent.setType("plain/text");    
	    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipientList);
	    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);   
	    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
	    context.startActivity(Intent.createChooser(emailIntent, title));
	}
}
