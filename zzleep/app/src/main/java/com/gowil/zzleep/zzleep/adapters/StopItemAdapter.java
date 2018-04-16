package com.gowil.zzleep.zzleep.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.gowil.zzleep.R;
import com.gowil.zzleep.zzleep.RecargarParaderosListener;

public class StopItemAdapter extends ArrayAdapter<ZzleepPlace>
{

    public Double radio;
	public Integer status;
    private boolean activador = true;
    private Context context;

	public void deshabilitarActivador() {
		this.activador = false;
	}

	public StopItemAdapter(Context context, int resource, List<ZzleepPlace> objects)
	{
		super(context, resource, objects);
	    this.context = context;
	}

	@NonNull
    @Override
	public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
		ZzleepPlace place = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_stop_item, parent, false);
		}
        if(place==null) return convertView;
		TextView tvName = convertView.findViewById(R.id.stopTitle);
		TextView tvHome = convertView.findViewById(R.id.stopAddress);
        ImageButton stopSwitch = convertView.findViewById(R.id.stopActive);
        ImageView iconAlarm = convertView.findViewById(R.id.iconAlarm);
        ImageView iconAudio = convertView.findViewById(R.id.ibAudio);

		stopSwitch.setAlpha(0.5F);
		stopSwitch.setImageResource(R.mipmap.switch_off);

		tvName.setText(place.getName());
		tvHome.setText(place.getAddress());
        String latitude = place.getLatitude();
        String longitude = place.getLongitude();
		radio = place.getRadio();
		status = place.getStatus();

		if(status==1)
		{
			stopSwitch.setAlpha(0.5F);
			stopSwitch.setImageResource(R.mipmap.switch_off);
		} else{
			stopSwitch.setAlpha(1F);
			stopSwitch.setImageResource(R.mipmap.switch_on);
		}


        ImageView imageType = convertView.findViewById(R.id.iconStop);
		stopSwitch.setVisibility(View.INVISIBLE);
		imageType.setVisibility(View.VISIBLE);
		switch (position) {
			case 0:
				imageType.setImageResource(R.mipmap.icon_house);
				break;
			case 1:
				imageType.setImageResource(R.mipmap.icon_work);
				break;
			case 2:
				imageType.setImageResource(R.mipmap.icon_college);
				break;
			default:
				imageType.setImageResource(R.mipmap.icon_targets);
				break;
		}
		if(!latitude.equals("0")&&!longitude.equals("0")&&activador){
			stopSwitch.setVisibility(View.VISIBLE);
            stopSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((RecargarParaderosListener)context).recargarParaderos(position);
                }
            });
		}
		if (place.alarma != null) {
            Glide.with(context).load(place.alarma.icon).into(iconAlarm);
        }

		if (place.audio != null) {
            Glide.with(context).load(place.audio.icon).into(iconAudio);
		}else{
			iconAudio.setVisibility(View.GONE);
		}
		return convertView;
	}
}
