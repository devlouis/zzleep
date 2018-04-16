package com.gowil.zzleep.zzleep.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.gowil.zzleep.R;
import com.gowil.zzleep.zzleep.AlarmsList;

/**
 * Created by gerson on 18/12/16.
 */

public class ZzleepAlarmAdapter extends ArrayAdapter<ZzleepAlarm>
{
	ImageView alarmIcon;
	TextView alarmName;
	TextView alarmPrice;
	public String audio,video;
	public Integer status;
	private AlarmsList audioContext;
	public ZzleepAlarmAdapter(AlarmsList context, int resource, List<ZzleepAlarm> objects)
	{
		super(context, resource, objects);
		this.audioContext=context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		ZzleepAlarm alarm = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_item, parent, false);
		}
		// Lookup view for data population
		alarmIcon = convertView.findViewById(R.id.alarmIcon);
		alarmName = convertView.findViewById(R.id.alarmName);
		alarmPrice = convertView.findViewById(R.id.alarmPrice);
		//TextView tvHome = (TextView) convertView.findViewById(R.id.placeAddress);

		// Populate the data into the template view using the data object
		//int resourceId = convertView.getResources().getIdentifier(alarm.icon, "mipmap", "pe.geekadvice.zzleep");
		audio =alarm.audio;
		video= alarm.video;
		status= alarm.status;
		alarmName.setText(alarm.name);

		Glide.with(audioContext).load(alarm.icon).into(alarmIcon);
		SharedPreferences prefs = audioContext.getSharedPreferences(
				"com.gowil.zzleep", Context.MODE_PRIVATE);
		final boolean bought = prefs.getBoolean(""+alarm.id,false);
		if(bought) status=1;
		switch (status)
		{
			case 0://gratis
				if(alarm.price.intValue() == 0) {
					alarmPrice.setText("Gratis");
				}else {
					alarmPrice.setText("S./"+alarm.price.toString());
				}
				break;
			case 1://comprado
				alarmPrice.setText("Comprado");
				break;
		}

		//tvHome.setText(audio.address);
		// Return the completed view to render on screen


		return convertView;
	}
}
