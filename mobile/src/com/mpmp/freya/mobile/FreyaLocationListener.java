package com.mpmp.freya.mobile;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class FreyaLocationListener implements LocationListener {

	private MainActivity mainActivit;

	public FreyaLocationListener(MainActivity mainActivity) {
		this.mainActivit = mainActivity;
	}

	@Override
	public void onLocationChanged(Location location) {
		mainActivit.setLocation(location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

}
