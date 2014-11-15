package com.deemsysinc.gpsmobiletracking;

public class Vehicle {
	public String _driverstatus;
	public int driver_licensedriverstatus;
	String vehicle_regno;
	String device_ime;
	String drivername;
	String driver_license;
	String license_expiry;
	String route_num;
	String timestamp;
	String address;
	String speed;
	String alarm;

	public String gettimestamp() {
		return this.timestamp;
	}

	public void settimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getaddress() {
		return this.address;
	}

	public void setaddress(String address) {
		this.address = address;
	}

	public String getspeed() {
		return this.speed;
	}

	public void setspeed(String speed) {
		this.speed = speed;
	}

	public String getdriverstatus() {
		return this._driverstatus;
	}

	public void setdriverstatus(String driverstatus) {
		this._driverstatus = driverstatus;
	}

	public String getvehicle_regno() {
		return vehicle_regno;
	}

	public void setvehicle_regno(String vehicle_regno) {
		this.vehicle_regno = vehicle_regno;
	}

	public String getdevice_ime() {
		return device_ime;
	}

	public void setdevice_ime(String device_ime) {
		this.device_ime = device_ime;
	}

	public String getdriver_license() {
		return driver_license;
	}

	public void setdriver_license(String driver_license) {
		this.driver_license = driver_license;
	}

	public String getdate() {
		return license_expiry;
	}

	public void setdate(String date) {
		this.license_expiry = date;
	}

	public void setroute_num(String route_num) {
		this.route_num = route_num;
	}

	public String getroute_num() {
		return route_num;
	}

	public void setdrivername(String drivername) {
		this.drivername = drivername;
	}

	public String getdrivername() {
		return drivername;
	}

	public void setalarm(String alarm) {
		this.alarm = alarm;
	}

	public String getalarm() {
		return alarm;
	}

	public Vehicle(int driverstatus, String name, String drivernamenum,
			String body, String date, String route_num) {

		this.device_ime = name;
		this.drivername = drivernamenum;
		this.driver_license = body;
		this.license_expiry = date;
		this.route_num = route_num;

	}

	public Vehicle(String vehicle_regno, String name, String drivernamenum,
			String body, String date, String route_num) {

		this.vehicle_regno = vehicle_regno;
		this.device_ime = name;
		this.drivername = drivernamenum;
		this.driver_license = body;
		this.license_expiry = date;
		this.route_num = route_num;

	}

	public Vehicle(String vehicle_regno, String name, String drivernamenum,
			String body, String date, String route_num, String driverstatus,
			String timestamp, String address, String speed) {

		this._driverstatus = driverstatus;
		this.vehicle_regno = vehicle_regno;
		this.device_ime = name;
		this.drivername = drivernamenum;
		this.driver_license = body;
		this.license_expiry = date;
		this.route_num = route_num;

	}

}
