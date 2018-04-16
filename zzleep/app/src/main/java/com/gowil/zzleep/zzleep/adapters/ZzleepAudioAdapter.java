package com.gowil.zzleep.zzleep.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.gowil.zzleep.R;
import com.gowil.zzleep.zzleep.AudioActivity;


/**
 * Created by gerson on 18/12/16.
 */

public class ZzleepAudioAdapter extends ArrayAdapter<ZzleepAudio>
{

	private AudioActivity context;


	public ZzleepAudioAdapter(AudioActivity context, int resource, List<ZzleepAudio> objects)
	{
		super(context, resource, objects);
		this.context=context;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		// Get the data item for this position
		final ZzleepAudio audio = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.audio_item, parent, false);
		}
		// Lookup view for data population
		TextView tvName = convertView.findViewById(R.id.audioName);
		TextView tvPrecio = convertView.findViewById(R.id.audioPrice);
		final ImageView audioButton = convertView.findViewById(R.id.iconPlayAudio);
		ImageView iconAudio = convertView.findViewById(R.id.iconAudio);
		final SeekBar seekBar = convertView.findViewById(R.id.seekbartimeline);
		seekBar.setMax(100);
		iconAudio.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				context.usarAudio(audio);
			}
		});
		Glide.with(context).load(audio.icon).into(iconAudio);
		audioButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(audio.getSonando()==0) {

					for (int i =0 ; i < parent.getChildCount();i++){
						getItem(i).setSonando(0);
						ImageView test = parent.getChildAt(i).findViewById(R.id.iconPlayAudio);
						test.setImageResource(R.mipmap.playb);
					}
					audio.setSonando(1);
					audioButton.setImageResource(R.mipmap.stopb);
				}else{
					audio.setSonando(0);
					audioButton.setImageResource(R.mipmap.playb);
				}
				SeekBar aux = seekBar;
				context.startPreview(audio,aux);
			}
		});
		//TextView tvHome = (TextView) convertView.findViewById(R.id.placeAddress);

		// Populate the data into the template view using the data object
		tvName.setText(audio.name);
		//tvHome.setText(audio.address);
		// Return the completed view to render on screen
		String audioPrice= audio.price.toString();
		SharedPreferences prefs = context.getSharedPreferences(
				"com.gowil.zzleep", Context.MODE_PRIVATE);
		final boolean bought = prefs.getBoolean(""+audio.id,false);
		if(bought){
			tvPrecio.setText("Comprado");
		}else if(audioPrice.equals("0.0")) {
			tvPrecio.setText("Gratis");
		}else{
			tvPrecio.setText("S./"+audioPrice);
		}
		return convertView;
	}
}
