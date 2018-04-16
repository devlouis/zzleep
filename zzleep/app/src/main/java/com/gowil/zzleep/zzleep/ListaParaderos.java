package com.gowil.zzleep.zzleep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.gowil.zzleep.R;
import com.gowil.zzleep.utils.JsonTask;
import com.gowil.zzleep.zzleep.adapters.StopItemAdapter;
import com.gowil.zzleep.zzleep.adapters.ZzleepAlarm;
import com.gowil.zzleep.zzleep.adapters.ZzleepAudio;
import com.gowil.zzleep.zzleep.adapters.ZzleepPlace;

/**
 * Created by YaguarRuna on 19/06/2017.
 */

public class ListaParaderos extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    ArrayList<ZzleepPlace> arrayList;
    private ArrayList<ZzleepPlace> destinies;
    private StopItemAdapter destinyadapter;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user = null;
    public String ApiPath = "http://app.zzleep.me/";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        init();
    }
    void init(){
        arrayList = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        StopItemAdapter adaptador = new StopItemAdapter(this,0, arrayList);
        adaptador.deshabilitarActivador();
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        JsonTask peticionParaderos = new JsonTask(ListaParaderos.this, 6);
        String aux_url=ApiPath+"api/v1/places?user_id=" + user.getUid();
        peticionParaderos.execute(aux_url);
        ListView lv = findViewById(R.id.listaPlaces);
        lv.setOnItemClickListener(this);
        lv.setAdapter(adaptador);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //ZzleepPlace place= (ZzleepPlace) parent.getAdapter().getItem(position);
    }

    public void getArrayStopItem(String result) {
        try {
            destinies = new ArrayList<>();
            JSONObject auxStop = new JSONObject(result);

            JSONArray parentArray = auxStop.getJSONArray("data");
            //se recorre el json de paraderos
            for (int i = 0; i < parentArray.length(); i++) {
                JSONObject finalObject = parentArray.getJSONObject(i);
                String name = finalObject.getString("name");
                String lat = finalObject.getString("lat");
                String lng = finalObject.getString("lng");
                String address = finalObject.getString("address");
                String image = finalObject.getString("image");

                Double range = 100D;
                if (!finalObject.isNull("range")) {
                    range = Double.parseDouble(finalObject.getString("range"));
                }
                ZzleepPlace destiny;
                //posible for para guardar alarma y audio
                destiny = new ZzleepPlace(name, address, lat, lng, range, image);
                if(!finalObject.isNull("alarm")){
                    JSONObject auxAlarma=finalObject.getJSONObject("alarm");
                    destiny.alarma= new ZzleepAlarm(auxAlarma.getString("name"),auxAlarma.getString("image"),0,0,"","");
                }
                if(!finalObject.isNull("sound")){
                    JSONObject auxSound=finalObject.getJSONObject("sound");
                    destiny.audio= new ZzleepAudio(0,auxSound.getString("name"),auxSound.getString("image"),"",0,0.0,0.0,0,"",0);
                }
                if (!finalObject.isNull("id")) {
                    int id = finalObject.getInt("id");
                    destiny.setId(id);
                }
                destinies.add(destiny);
            }
            destinyadapter = new StopItemAdapter(this, 0, destinies);
            destinyadapter.deshabilitarActivador();

            //Asociamos  el adaptador a la vista.
            ListView lv = findViewById(R.id.listaPlaces);
            lv.setOnItemClickListener(this);
            lv.setAdapter(destinyadapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
