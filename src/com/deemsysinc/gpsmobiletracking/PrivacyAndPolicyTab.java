package com.deemsysinc.gpsmobiletracking;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;


public class PrivacyAndPolicyTab extends Activity {
	TextView useof,storage,weuse,howwe,access,thirdparty,change;
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.privacytab);
	      useof=(TextView)findViewById(R.id.useof);
	      storage=(TextView)findViewById(R.id.storage);
	      weuse=(TextView)findViewById(R.id.weuse);
	      howwe=(TextView)findViewById(R.id.howwe);
	      access=(TextView)findViewById(R.id.access);
	      thirdparty=(TextView)findViewById(R.id.thirdparty);
	      change=(TextView)findViewById(R.id.changesto);
	      useof.setTypeface(null, Typeface.BOLD);
	      storage.setTypeface(null, Typeface.BOLD);
	      weuse.setTypeface(null, Typeface.BOLD);
	      howwe.setTypeface(null, Typeface.BOLD);
	      access.setTypeface(null, Typeface.BOLD);
	      thirdparty.setTypeface(null, Typeface.BOLD);
	      change.setTypeface(null, Typeface.BOLD);
	    }
}
