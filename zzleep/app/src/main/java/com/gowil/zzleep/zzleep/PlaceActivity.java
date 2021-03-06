package com.gowil.zzleep.zzleep;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.gowil.zzleep.R;
import com.gowil.zzleep.utils.JsonTask;
import com.gowil.zzleep.zzleep.adapters.ZzleepPlace;
import com.gowil.zzleep.zzleep.adapters.ZzleepPlaceAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PlaceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener , GoogleApiClient.ConnectionCallbacks, View.OnClickListener, View.OnKeyListener {
	private final static String TAG = PlaceActivity.class.getSimpleName();
	ArrayList<ZzleepPlace> arrayList;
	EditText txtSearch;
	private GoogleApiClient mGoogleApiClient;
	String lat, lng;
	JsonTask jsonTask;
	@Override
	protected void attachBaseContext(Context newBase)
	{
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		StrictMode.ThreadPolicy policy = new StrictMode.
				ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place);
		Bundle data = getIntent().getExtras();
		lat =data.getString("lati");
		lng =data.getString("lngt");
		try {
			init();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	void init() throws JSONException {
		txtSearch=  findViewById(R.id.txtSearchPlace);
		findViewById(R.id.txtSearchPlace).setOnKeyListener(this);
		txtSearch.addTextChangedListener(textWatcher);
		findViewById(R.id.btnBack).setOnClickListener(this);
		findViewById(R.id.textView2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					updatePlaces(txtSearch.getText().toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		mGoogleApiClient = new GoogleApiClient
				.Builder(this)
				.addApi(Places.GEO_DATA_API)
				.addApi(Places.PLACE_DETECTION_API)
				.addConnectionCallbacks(this)
				.build();
	}

	public void getPlaceJson(String result){
		try{
			arrayList =new ArrayList<>();
			JSONArray parentArray =new JSONArray(result);
			//se recorre el json de paraderos
			for (int i=0;i<parentArray.length();i++)
			{
				JSONObject finalObject = parentArray.getJSONObject(i);
				String  name =finalObject.getString("name");
				String  lat =finalObject.getString("lat");
				String  lng =finalObject.getString("lng");
				String  address =finalObject.getString("address");
				String  type =finalObject.getString("type");
				ZzleepPlace destiny;

				destiny = new ZzleepPlace(name,address,lat,lng,1000D,"");

				arrayList.add(destiny);
			}
			ListAdapter adaptador = new ZzleepPlaceAdapter(this,0, arrayList);

			ListView lv =  findViewById(R.id.listPlaces);
			lv.setOnItemClickListener(this);
			lv.setAdapter(adaptador);
		}catch (JSONException e){
			e.printStackTrace();
		}
	}
	public void getPosiblePlaces(String result, String result2){
		try{
			arrayList = new ArrayList<>();
			JSONObject jsonObject = new JSONObject(result);
			JSONObject jsonObject2 = new JSONObject(result2);
			JSONArray temp = jsonObject.getJSONArray("results");
			JSONArray temp2 = jsonObject2.getJSONArray("results");
			String auxName;
			String auxDireccion;
			String auxLatitud;
			String auxLongitud;

			int contador =temp.length();
			int contador2= temp2.length();
			ZzleepPlace destiny;

			for (int i = 0; i < contador && i < 3; i++) {
				JSONObject a = temp.getJSONObject(i);
				//auxDireccion = (String) a.getJSONArray("address_components").getJSONObject(0).getString("long_name")+"-";
				auxDireccion = a.getString("formatted_address");
				auxLatitud = a.getJSONObject("geometry").getJSONObject("location").getString("lat");
				auxLongitud = a.getJSONObject("geometry").getJSONObject("location").getString("lng");
				auxName = a.getString("formatted_address");
				destiny = new ZzleepPlace(auxName, auxDireccion, auxLatitud, auxLongitud, 1000D,"");
				arrayList.add(destiny);
			}

			for (int i = 0; i < contador2; i++) {
				JSONObject a = temp2.getJSONObject(i);
				auxName = a.getString("name");
				auxLatitud = a.getJSONObject("geometry").getJSONObject("location").getString("lat");
				auxLongitud = a.getJSONObject("geometry").getJSONObject("location").getString("lng");
				auxDireccion = a.getString("vicinity");
				destiny = new ZzleepPlace(auxName, auxDireccion, auxLatitud, auxLongitud, 1000D,"");
				arrayList.add(destiny);
			}

			ListAdapter adaptador = new ZzleepPlaceAdapter(this, 0, arrayList);
			ListView lv = findViewById(R.id.listPlaces);
			lv.setOnItemClickListener(this);

			lv.setAdapter(adaptador);
		}catch (JSONException e){
			e.printStackTrace();
		}
	}
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ZzleepPlace place= (ZzleepPlace) parent.getAdapter().getItem(position);
		Intent resultData = new Intent();
		resultData.putExtra("name", place.getName());
		resultData.putExtra("address", place.getAddress());
		resultData.putExtra("lat", place.getLatitude());
		resultData.putExtra("lng", place.getLongitude());
		resultData.putExtra("address", place.getAddress());
		resultData.putExtra("type", place.getAddress());
		setResult(20, resultData);
		finish();
	}

	void updatePlaces(String search) throws JSONException {
		String busqueda=search;
		busqueda=busqueda.replace(" ","+");
		String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lng+"&radius=3000000&&name="+busqueda+"&key=AIzaSyDi6_Pk6q_VV6_llPpbvoJuqnBOTbadKas";
		String url2="https://maps.googleapis.com/maps/api/geocode/json?address="+busqueda+"&key=AIzaSyDi6_Pk6q_VV6_llPpbvoJuqnBOTbadKas&components=country:PE";
		if(!busqueda.equals("")) {
			jsonTask = new JsonTask(this, 3);
			jsonTask.execute(url,url2);
		}

	}
	private Timer timer = new Timer();

	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

		}

		@Override
		public void afterTextChanged(final Editable editable) {

			timer.cancel();
			timer = new Timer();
			timer.schedule(
					new TimerTask() {
						@Override
						public void run() {
							try {
								updatePlaces(editable.toString());
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					},
					700
			);

		}
	};
	@Override
	public void onConnected(@Nullable Bundle bundle)
	{

	}

	@Override
	public void onConnectionSuspended(int i)
	{

	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnBack:
				finish();
				break;

		}
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		return false;
	}
}

