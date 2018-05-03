package com.gowil.zzleep.zzleep;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import com.gowil.zzleep.R;
import com.gowil.zzleep.utils.JsonTask;
import com.gowil.zzleep.utils.MusicTask;
import com.gowil.zzleep.utils.SeekBarTask2;
import com.gowil.zzleep.zzleep.adapters.ScreenSlidePagerAdapter;
import com.gowil.zzleep.zzleep.adapters.ZzleepAudio;
import com.gowil.zzleep.zzleep.adapters.ZzleepAudioAdapter;

public class AudioActivity extends AppCompatActivity
{
	ArrayList<ZzleepAudio> audios;
	public ZzleepAudio currentAudio;
	MediaPlayer mp;
	SeekBar currentSeekBar;
    ProgressBar spinner;
	ZzleepAudioAdapter adaptador;
	String optionSelected;
	ScreenSlidePagerAdapter mPagerAdapter;
	private FirebaseUser user = null;
	public String ApiPath = "http://app.zzleep.me/";
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio);
		Bundle data = getIntent().getExtras();
		if(data!=null)
			optionSelected =data.getString("opt");
		ViewPager mViewPager = findViewById(R.id.audioAdsViewPager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), Arrays.asList(R.mipmap.anuncio_1,R.mipmap.anuncio_2,R.mipmap.anuncio_3,R.mipmap.anuncio_4));
		TabLayout tabLayout = findViewById(R.id.audioTabDots);
		tabLayout.setupWithViewPager(mViewPager, true);
		mViewPager.setAdapter(mPagerAdapter);
		init();
	}
	void init(){
		user = FirebaseAuth.getInstance().getCurrentUser();
        spinner = findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);
        JsonTask obtenerAudios=new JsonTask(this,8);
		obtenerAudios.execute(ApiPath+"api/v1/products?type=sound&user_id="+user.getUid());
		findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	public void obtenerAudiosArray(String data) throws JSONException {
		audios = new ArrayList<>();
		Log.v("AUDIOS::: " , audios.toString());

		JSONObject aux= new JSONObject(data);
		JSONArray alarmas = aux.getJSONArray("data");
		String name;
		String icon;
		Integer status;
		Integer company_id;
		Double price;
		String video;
		//String audio;
		String description;
		Integer id;
		Double discount;
		Integer points;
		for (int i = 0; i < alarmas.length(); i++) {
			JSONObject auxAlarma = alarmas.getJSONObject(i);
			name=auxAlarma.getString("name");
			icon=auxAlarma.getString("image");
			description=auxAlarma.getString("description");
			status=auxAlarma.getInt("is_owned");
			company_id=auxAlarma.getInt("company_id");
			price=auxAlarma.getDouble("amount");
			discount=auxAlarma.getDouble("discount");
			points=auxAlarma.getInt("points");
			video=auxAlarma.getString("preview");
			//audio=auxAlarma.getString("product_file");
			id=auxAlarma.getInt("id");
			ZzleepAudio auxAlarm=new ZzleepAudio(id, name, icon, description, status,  price,  discount,  points,  video, company_id);
			auxAlarm.id=id;
			audios.add(auxAlarm);
		}
		adaptador = new ZzleepAudioAdapter(this,0, audios);
		ListView lv = findViewById(R.id.listAudios);
		lv.setAdapter(adaptador);
        spinner.setVisibility(View.GONE);

    }
    void finishWithResult(){
		Intent resultData = new Intent();
		resultData.putExtra("audio_id",currentAudio.id);
		setResult(Activity.RESULT_OK, resultData);
		finish();
	}
	public void usarAudio(final ZzleepAudio audioEnviar){
		currentAudio=audioEnviar;
		SharedPreferences prefs = AudioActivity.this.getSharedPreferences(
				"com.gowil.zzleep", Context.MODE_PRIVATE);
		final boolean bought = prefs.getBoolean(""+audioEnviar.id,false);
		String option="Comprar";
		if(audioEnviar.status==1 || audioEnviar.price==0.0||bought)
			option="Usar";
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Alarma");
		builder.setMessage("¿Qué deseas hacer?");
		builder.setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				dialog.dismiss();
			}
		});
		builder.setPositiveButton(option,new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				if(audioEnviar.status== 1 || audioEnviar.price==0.0 ||bought)
				{
					if(mp!=null && mp.isPlaying())
						mp.stop();
					finishWithResult();
				}
				else{
					iniciarCompra();
				}
				dialog.dismiss();
			}
		});
		AlertDialog alert =builder.create();
		alert.show();
	}

	public void startPreview(ZzleepAudio audioIniciar, SeekBar auxSeekBar){
		try {
			if(audioIniciar!=null && audioIniciar.preview.length()>0)
			{
				if(currentAudio==null){
					currentAudio=audioIniciar;
					this.mp=MusicTask.execute(currentAudio.getPreview());
				}else {
					if(currentAudio.getPreview().equals(audioIniciar.getPreview())){
						if(mp.isPlaying()) {
							mp.pause();
						}else{
							mp.start();
						}
					}else{
						if(mp.isPlaying()) {
							mp.stop();
							mp.release();
						}
						currentAudio=audioIniciar;
						this.mp=MusicTask.execute(currentAudio.getPreview());
					}
				}
				currentSeekBar=auxSeekBar;
				currentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
						try {
							if (fromUser & mp != null)
								mp.seekTo(mp.getDuration() * progress / 100);
						}catch (Exception e){

						}
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {

					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {

					}
				});
				SeekBarTask2 auxSB= new SeekBarTask2(this);
				auxSB.execute();
			}
		}catch (Exception e) {

		}
	}
	public MediaPlayer getMp(){
		return this.mp;
	}
	public void setValueSB(int value){
		currentSeekBar.setProgress(value);
	}
	public boolean getMpPlaying(){
		return mp.isPlaying();
	}
	public int mpPosition(){
		return mp.getCurrentPosition();
	}
	public int mpDuration(){
		return mp.getDuration();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			if(resultCode == Activity.RESULT_OK){
				if("buy".equals(optionSelected)) {
					adaptador.notifyDataSetChanged();
				}else{
					finishWithResult();
				}
			}
			if (resultCode == Activity.RESULT_CANCELED) {
				//Write your code if there's no result
			}
		}
	}//onActivityResult
	public void iniciarCompra(){
		SharedPreferences prefs = AudioActivity.this.getSharedPreferences(
				"com.gowil.zzleep", Context.MODE_PRIVATE);
		Intent alarmScreen = new Intent(this, PaymentActivity.class);
		alarmScreen.putExtra("nombre", currentAudio.name);
		alarmScreen.putExtra("precio", currentAudio.price.toString());
		alarmScreen.putExtra("userid",user.getUid());
		String temp= FirebaseInstanceId.getInstance().getToken();
		alarmScreen.putExtra("userToken",temp);
		if(currentAudio.id==-1){
			Toast.makeText(this, "Error de ID", Toast.LENGTH_LONG).show();
			return;
		}
		alarmScreen.putExtra("id",currentAudio.id);
		startActivityForResult(alarmScreen,1);
	}

	@Override
	protected void onDestroy() {
 		super.onDestroy();
		if(this.mp != null){
			try{
				this.mp.stop();
				this.mp.release();
			} catch (Exception e){

			}
		}
	}
}
