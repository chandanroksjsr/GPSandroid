package com.deemsysinc.gpsmobiletracking;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VehichleArrayAdapter extends ArrayAdapter<Vehicle> {
	private final Context context;
	Vehicle user;
	int layoutResourceId;
	public static ArrayList<Vehicle> data = new ArrayList<Vehicle>();

	public VehichleArrayAdapter(Activity context, List<Vehicle> vehicleall,
			int layoutResourceId) {
		super(context, R.layout.vehiclelist, vehicleall);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		System.out.println("vehicle all value::" + vehicleall);
		data = (ArrayList<Vehicle>) vehicleall;

	}

	class UserHolder {
		TextView drivername;
		TextView vehicleregno, timestamp, address, speed, alarm;

		ImageView driverstatus;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		UserHolder holder = null;

		if (row == null) {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			row = inflater.inflate(R.layout.vehiclelist, parent, false);

			holder = new UserHolder();
			holder.vehicleregno = (TextView) row.findViewById(R.id.veh_reg_no);
			holder.drivername = (TextView) row.findViewById(R.id.label);
			holder.driverstatus = (ImageView) row.findViewById(R.id.logo);
			holder.address = (TextView) row.findViewById(R.id.addrstext);
			holder.speed = (TextView) row.findViewById(R.id.speedtxt);
			holder.alarm = (TextView) row.findViewById(R.id.alarm);
			row.setTag(holder);
		} else {
			holder = (UserHolder) row.getTag();
		}
		System.out.println("vlaue of list::" + data);
		user = data.get(position);

		holder.vehicleregno.setText(user.getvehicle_regno());
		holder.drivername.setText(user.gettimestamp());
		holder.address.setText(user.getaddress());
		holder.speed.setText(user.getspeed());
		System.out.println("value of timestamp" + user.gettimestamp());
		System.out.println("value in textview" + holder.drivername.getText());
		System.out.println("value of driverstatus::" + user.getdriverstatus());
		if (user.getdriverstatus().equals("0")) {
			holder.driverstatus.setImageResource(R.drawable.red_light);

		} else if (user.getdriverstatus().equals("1")) {
			holder.driverstatus.setImageResource(R.drawable.green_light);

		} else if (user.getdriverstatus().equals("2")) {
			holder.driverstatus.setImageResource(R.drawable.yellow_light);

		} else if (user.getdriverstatus().equals("3")) {
			holder.driverstatus.setImageResource(R.drawable.yellow_light);

		} else {
			holder.driverstatus.setImageResource(R.drawable.red_light);
		}
		if (user.getalarm().equals("0")) {
			holder.alarm.setVisibility(View.INVISIBLE);

		} else if (user.getalarm().equals("1")) {
			holder.alarm.setVisibility(View.INVISIBLE);

		} else if (user.getalarm().equals("2")) {
			holder.alarm.setVisibility(View.VISIBLE);
		}
		return row;

	}

}
