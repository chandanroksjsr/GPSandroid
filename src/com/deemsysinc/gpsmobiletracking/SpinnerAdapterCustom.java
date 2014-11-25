package com.deemsysinc.gpsmobiletracking;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapterCustom extends ArrayAdapter<String>
    {
        private Activity context;
        ArrayList<String> data = null;

        public SpinnerAdapterCustom(Activity context, int resource, ArrayList<String> data)
        {
            super(context, resource, data);
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) 
        {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
            return super.getView(position, convertView, parent);   
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent)
        {   // This view starts when we click the spinner.
            View row = convertView;
            if(row == null)
            {
                LayoutInflater inflater = context.getLayoutInflater();
                row = inflater.inflate(R.layout.spinnerlayout, parent, false);
            }

            String item = data.get(position);

            if(item != null)
            {   // Parse the data from each object and set it.
              
                TextView myCountry = (TextView) row.findViewById(R.id.timing);
               
             
                    myCountry.setText(item);

            }

            return row;
        }
    
}