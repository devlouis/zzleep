package com.gowil.zzleep.zzleep;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class TrackService extends Service implements LocationListener
{
	private LocationManager lm;
	double radius = 1e9;
	private Boolean EnCamino=false;
	private int posicion=-1;
	private LatLng paradero;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
        lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        Toast.makeText(this, "He sido creado - meeeeeeeeeee:", Toast.LENGTH_SHORT).show();
        EnCamino = true;
        Bundle extras = intent.getExtras();
        if(extras!=null) {
			posicion = extras.getInt("posicion");
			radius = extras.getDouble("radius");
			paradero = new LatLng(extras.getDouble("latitude"), extras.getDouble("longitude"));
		}
        startLocation();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Toast.makeText(this, "He sido destrudio pero volveré :|", Toast.LENGTH_SHORT).show();
		EnCamino=false;
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		Toast.makeText(this, "Intentaron bidearme!!!", Toast.LENGTH_SHORT).show();
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public void startLocation()
	{
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
		{
			Criteria criteria = new Criteria();
			String bestProvider = lm.getBestProvider(criteria, false);
			if (lm.getLastKnownLocation(bestProvider) != null)
				Toast.makeText(this, "Estas en camino!", Toast.LENGTH_SHORT).show();

			lm.requestLocationUpdates(bestProvider, 1000, 1F, this);
		} else {
			Toast.makeText(this, "No tengo permisos", Toast.LENGTH_SHORT).show();
		}
	}

	//Location
	@Override
	public void onLocationChanged(Location location)
	{
		LatLng miposicion = new LatLng(location.getLatitude(), location.getLongitude());
		if(EnCamino && posicion!=-1) {
			Location location1 = new Location("");
			location1.setLatitude(miposicion.latitude);
			location1.setLongitude(miposicion.longitude);
			Location location2 = new Location("");
			location2.setLatitude(paradero.latitude);
			location2.setLongitude(paradero.longitude);
			Double distanceInMeters = (double) location1.distanceTo(location2);
			if (distanceInMeters < radius+100){
				Intent dialogIntent = new Intent(this, MainActivity.class);
				dialogIntent.putExtra("posicion",posicion);
				dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(dialogIntent);
			}
		}

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{

	}

	@Override
	public void onProviderEnabled(String provider)
	{

	}

	@Override
	public void onProviderDisabled(String provider)
	{

	}
}
