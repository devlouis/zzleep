package com.gowil.zzleep.zzleep;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gowil.zzleep.R;
import com.gowil.zzleep.app.core.utils.LogUtils;
import com.gowil.zzleep.domain.model.ProductsAudio;
import com.gowil.zzleep.domain.model.ProductsVideo;
import com.gowil.zzleep.presenter.ProductsPresenter;
import com.gowil.zzleep.utils.DownloadTask;
import com.gowil.zzleep.utils.JsonTask;
import com.gowil.zzleep.view.Productsview;
import com.gowil.zzleep.zzleep.adapters.ScreenSlidePagerAdapter;
import com.gowil.zzleep.zzleep.adapters.ZzleepAlarm;
import com.gowil.zzleep.zzleep.adapters.ZzleepAlarmAdapter;
import com.gowil.zzleep.zzleep.utils.Callback;
import com.gowil.zzleep.zzleep.utils.PlayerManager;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
/**
 * Main Activity for the IMA plugin demo. {@link ExoPlayer} objects are created by
 * {@link PlayerManager}, which this class instantiates.
 */
public class AlarmsList extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener, Productsview
{
	View mViewGroup;
	VideoView vdoView;
	ArrayList<ZzleepAlarm> arrayList;
	ZzleepAlarm alarmSelected;
	String optionSelected;
	ZzleepAlarmAdapter alarmAdapter;
	ProgressBar spinner;
	ViewPager mViewPager;
	ScreenSlidePagerAdapter mPagerAdapter;
	public String ApiPath = "http://app.zzleep.me/";
	private MediaPlayer mp = null;
	private FirebaseUser user = null;

	private PlayerView playerView;
	private PlayerManager player;
	private ProductsPresenter productsPresenter;

	@Override
	protected void attachBaseContext(Context newBase)
	{
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarms_list);
		playerView = findViewById(R.id.player_view);
		mViewGroup = findViewById(R.id.viewsContainer);
		vdoView = findViewById(R.id.videoViewRec);
		vdoView.setMediaController(new MediaController(this));


		Bundle data = getIntent().getExtras();
		if(data!=null)
			optionSelected =data.getString("opt");
		mViewGroup.setVisibility(View.GONE);
		init();
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();
		player.reset();
	}

	@Override
	public void onDestroy() {
		//player.release();
		super.onDestroy();
	}


	@Override
	protected void onStop()
	{
		super.onStop();
		if(mp != null)
			mp.stop();
	}

	private void init()
	{
		player = new PlayerManager(this);
		spinner = findViewById(R.id.progressBar1);
		spinner.setVisibility(View.VISIBLE);
		findViewById(R.id.btnBack).setOnClickListener(this);
		arrayList = new ArrayList<>();
		user = FirebaseAuth.getInstance().getCurrentUser();
		String url = null;
		if (user != null) {
			url = ApiPath+"api/v1/products?type=alarm&user_id=" + user.getUid();
		}
		JsonTask pedir_alarmas = new JsonTask(AlarmsList.this,5);
		pedir_alarmas.execute(url);
		mViewPager = findViewById(R.id.adsViewPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), Arrays.asList(R.mipmap.alarma_1,R.mipmap.alarma_2));
		mViewPager.setAdapter(mPagerAdapter);
		TabLayout tabLayout = findViewById(R.id.tabDots);
		tabLayout.setupWithViewPager(mViewPager, true);

		productsPresenter = new ProductsPresenter();
		productsPresenter.attachedView(this);
		productsPresenter.getAlarmVideos();
	}


	void iniciarPreview(String videoURL){
		playerView.setVisibility(View.VISIBLE);
		player.init(this, playerView, videoURL);
	}

	void showItemUsageDialog(final AdapterView<?> parent, final int position) {
		Integer status=alarmSelected.status;
		String option="Comprar";
		SharedPreferences prefs = AlarmsList.this.getSharedPreferences(
				"com.gowil.zzleep", Context.MODE_PRIVATE);
		final boolean bought = prefs.getBoolean(""+alarmSelected.id,false);
		if(status==1 || alarmSelected.price.doubleValue()==0.0||bought)
		{
			option="Usar";
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Alarma");
		builder.setMessage("¿Qué deseas hacer?");
		builder.setPositiveButton("Vista previa",new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				try {
					//previewAlarm(alarmSelected.name,alarmSelected.video);
					iniciarPreview(alarmSelected.video);

				} catch (Exception e) {
					e.printStackTrace();
				}
				mViewGroup.setVisibility(View.VISIBLE);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(option,new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				if(alarmSelected.status== 1 || alarmSelected.price.equals(0.0)||bought)
				{
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
	@Override
	public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
		alarmSelected= (ZzleepAlarm) parent.getAdapter().getItem(position);
		Toast.makeText(this,"aaaaa", Toast.LENGTH_LONG);

		showItemUsageDialog(parent,position);
		/**
		 * descarga video audio
 		 */
		/*DownloadTask downloadTask = new DownloadTask(this);
		downloadTask.setCallback(new Callback(){
			@Override
			public void onFinish() {
				showItemUsageDialog(parent,position);
			}
		});
		downloadTask.execute(alarmSelected.video, "preview_" + alarmSelected.name.replace(" ", "_"));*/
	}

	@Override
	public void onBackPressed() {

	/*	if(vdoView.getVisibility()==View.VISIBLE || spinner.getVisibility()==View.VISIBLE){
			vdoView.setVisibility(View.INVISIBLE);
			spinner.setVisibility(View.INVISIBLE);
		}else {
			super.onBackPressed();
		}*/

		if (playerView.getVisibility() == View.VISIBLE){
			playerView.setVisibility(View.GONE);
			player.stop();

		} else {
			super.onBackPressed();
		}
	}

	private void finishWithResult()
	{
		Intent resultData = new Intent();
		resultData.putExtra("alarmName",alarmSelected.name);
		resultData.putExtra("alarmIcon",alarmSelected.icon);
		resultData.putExtra("alarmAudio",alarmSelected.audio);
		resultData.putExtra("alarmVideo",alarmSelected.video);
		resultData.putExtra("alarmId",alarmSelected.id);
		setResult(Activity.RESULT_OK, resultData);
		finish();
	}
	private void previewAlarm(String _nombre,String _video) throws IOException {
		if(mp != null)
			mp.stop();
		if(_video!=null&&!_video.equals(""))
		{
			Uri path =Uri.parse(_video);
			vdoView.setVisibility(View.VISIBLE);
			String auxPath= getFilesDir().getPath()+"/preview_" + _nombre.replace(" ", "_");
			File fileAux= new File(auxPath);
			if(fileAux.exists()){
				vdoView.setVideoPath(auxPath);
			}else{
				vdoView.setVideoURI(path);
				//DownloadTask downloadTask = new DownloadTask(this);
				//downloadTask.execute(_video, "preview_" + _nombre.replace(" ", "_"));
			}
			spinner.setVisibility(View.VISIBLE);
			vdoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mediaPlayer) {
					spinner.setVisibility(View.INVISIBLE);
					//mediaPlayer.start();

				}
			});
			vdoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPreview();
                }
            });
//			vdoView.start();
		}else{
			vdoView.setVisibility(View.INVISIBLE);
		}

	}
    void stopPreview(){
        mViewGroup.setVisibility(View.GONE);
        if(vdoView.isPlaying())
        {
            vdoView.stopPlayback();
        }
        vdoView.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);
    }
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.btnBack:
				finish();
				break;
			case R.id.btnStop:
			    stopPreview();
				break;
		}
	}

	public void obtener_alarmas(String data) throws JSONException {
		JSONObject aux= new JSONObject(data);
		JSONArray alarmas = aux.getJSONArray("data");
		 String name;
		 String icon;
		 Integer status;
		 double price;
		 String video;
		 String audio;
		 Integer id;
		for (int i = 0; i < alarmas.length(); i++) {
			JSONObject auxAlarma = alarmas.getJSONObject(i);
			name=auxAlarma.getString("name");
			icon=auxAlarma.getString("image");
			status=auxAlarma.getInt("is_owned");
			price=auxAlarma.getDouble("amount");
			video=auxAlarma.getString("preview");
			audio=auxAlarma.getString("product_file");
			id=auxAlarma.getInt("id");
			ZzleepAlarm auxAlarm=new ZzleepAlarm(name,icon,status,price,video,audio);
			auxAlarm.id=id;
			arrayList.add(auxAlarm);
		}
		Log.v(" ALARMA-VIDEOS:: ", arrayList.toString());
		alarmAdapter = new ZzleepAlarmAdapter(this,0, arrayList);
		//Asociamos el adaptador a la vista.
		if (arrayList.size()==0 && data.contains("ERROR")){
			Toast.makeText(this, "Error:" + data, Toast.LENGTH_LONG).show();
		}
		GridView lv = findViewById(R.id.gridview);
		lv.setOnItemClickListener(this);
		lv.setAdapter(alarmAdapter);
		findViewById(R.id.btnStop).setOnClickListener(this);
		spinner.setVisibility(View.GONE);
	}
	public void iniciarCompra(){
		Intent alarmScreen = new Intent(this, PaymentActivity.class);
		alarmScreen.putExtra("nombre", alarmSelected.name);
		alarmScreen.putExtra("precio", alarmSelected.price.toString());
		alarmScreen.putExtra("userid",user.getUid());
		String temp= FirebaseInstanceId.getInstance().getToken();
		alarmScreen.putExtra("userToken",temp);
		if(alarmSelected.id==-1){
			Toast.makeText(this, "Error de ID", Toast.LENGTH_LONG).show();
			return;
		}
		alarmScreen.putExtra("id",alarmSelected.id);
		startActivityForResult(alarmScreen,1);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			if(resultCode == Activity.RESULT_OK){
				if("buy".equals(optionSelected)) {
					alarmAdapter.notifyDataSetChanged();
				}else{
					finishWithResult();
				}
			}
			if (resultCode == Activity.RESULT_CANCELED) {
				//Write your code if there's no result
			}
		}
	}

	@Override
	public void getalarmVideos(@NotNull List<ProductsVideo> productsVideos) {
		new LogUtils().v("getalarmVideo", productsVideos.toString());
	}

	@Override
	public void getalarmAudio(@NotNull ProductsAudio productsAudio) {

	}

	@Override
	public void showLoading() {

	}

	@Override
	public void hideLoading() {

	}

	@Override
	public void showMessageError(@NotNull String message, @Nullable Integer type) {

	}

	@NotNull
	@Override
	public Context getContext() {
		return this;
	}
}
