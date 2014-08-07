package com.deemsysinc.gpsmobiletracking;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class TermsAndConditionTab extends Activity{
	TextView warantydis,expect,deemgpsreturn,deemgpsrefund,deemgpswarranty,inwarantyrepairs,inwarantyservice,outofwaranty,brokendisplay,shippingins,shipins,deemsindianaddrs;
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.termsandcondition);
	      deemgpsreturn=(TextView)findViewById(R.id.deemsysreturn);
	      deemgpsrefund=(TextView)findViewById(R.id.refundpolicy);
	      deemgpswarranty=(TextView)findViewById(R.id.deemgpswaranty);
	      inwarantyrepairs=(TextView)findViewById(R.id.warantyrepair);
	      inwarantyservice=(TextView)findViewById(R.id.proofofpurchase);
	      outofwaranty=(TextView)findViewById(R.id.outofwaranty);
	      brokendisplay=(TextView)findViewById(R.id.brokerndisplay);
	      shippingins=(TextView)findViewById(R.id.productreturnship);
	      shipins=(TextView)findViewById(R.id.shipinstruc);
	      deemsindianaddrs=(TextView)findViewById(R.id.deemsysindianaddrs);
	      warantydis=(TextView)findViewById(R.id.warantydisclaimera);
	      expect=(TextView)findViewById(R.id.warantydisclaimerturn);
	      
	      deemgpsreturn.setTypeface(null, Typeface.BOLD);
	      deemgpsrefund.setTypeface(null, Typeface.BOLD);
	      deemgpswarranty.setTypeface(null, Typeface.BOLD);
	      inwarantyrepairs.setTypeface(null, Typeface.BOLD);
	      inwarantyservice.setTypeface(null, Typeface.BOLD);
	      outofwaranty.setTypeface(null, Typeface.BOLD);
	      brokendisplay.setTypeface(null, Typeface.BOLD);
	      shippingins.setTypeface(null, Typeface.BOLD);
	      shipins.setTypeface(null, Typeface.BOLD);
	      deemsindianaddrs.setTypeface(null, Typeface.BOLD);
	      warantydis.setTypeface(null, Typeface.BOLD);
	      expect.setTypeface(null, Typeface.BOLD);
	    }
}
