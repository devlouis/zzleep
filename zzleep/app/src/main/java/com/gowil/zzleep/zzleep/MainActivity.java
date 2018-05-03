package com.gowil.zzleep.zzleep;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import com.google.firebase.auth.GetTokenResult;
import com.gowil.zzleep.R;
import com.gowil.zzleep.app.core.utils.LogUtils;
import com.gowil.zzleep.utils.ApiDelete;
import com.gowil.zzleep.utils.ApiImg;
import com.gowil.zzleep.utils.ApiPost;
import com.gowil.zzleep.utils.DownloadTask;
import com.gowil.zzleep.utils.JsonTask;
import com.gowil.zzleep.utils.MusicTask;
import com.gowil.zzleep.zzleep.adapters.StopItemAdapter;
import com.gowil.zzleep.zzleep.adapters.ZzleepAlarm;
import com.gowil.zzleep.zzleep.adapters.ZzleepAudio;
import com.gowil.zzleep.zzleep.adapters.ZzleepPlace;
import com.gowil.zzleep.zzleep.components.MainMenuView;
import com.gowil.zzleep.zzleep.components.StopCreate;
import com.gowil.zzleep.zzleep.components.StopMenuEvents;
import com.gowil.zzleep.zzleep.components.StopMenuView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends FragmentActivity implements
		SeekBar.OnSeekBarChangeListener,
		AdapterView.OnItemClickListener,
        RecargarParaderosListener,
        OnMapReadyCallback,
		ActivityCompat.OnRequestPermissionsResultCallback,
		GoogleMap.OnMapLongClickListener,
		GoogleMap.OnMapClickListener,
		View.OnClickListener,
		StopMenuEvents,
		GoogleApiClient.OnConnectionFailedListener,
		GoogleApiClient.ConnectionCallbacks,
		AdapterView.OnItemLongClickListener,
		com.google.android.gms.location.LocationListener {

    private static final int REQUEST_LOCATION_PERMISSIONS = 1;
    private static final String DEFAULT_LAT = "-12.00";
    private static final String DEFAULT_LNG = "-77.00";
    ImageView alarmIcon;
    ImageView audioIcon;
    ZzleepAlarm alarmaSelected;
    ZzleepAlarm alarmaDefault;
    public String ApiPath = "http://app.zzleep.me/";
    private GoogleMap mMap;
    private Marker userMarker = null;
    private Marker targetMarker = null;
    private Circle targetArea = null;
    private EditText nameParadero;
    private static ArrayList<ZzleepPlace> destinies;
    private static ArrayList<ZzleepAlarm> alarmas;
    private ZzleepPlace placeSelected;
    private ZzleepPlace placeTransaccional;
    private StopMenuView menuStops;
    private MainMenuView menuZzleep;
    private StopCreate menuCreateStop;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user = null;
    private StopItemAdapter destinyAdapter;
    private MediaPlayer audioSelected = null;
    private MediaPlayer alarmSelected = null;
    private TextView radioStopMenu = null;

    //Luis Canales
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private final static String TAG = MainActivity.class.getSimpleName();

    private Location location;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    /*Mis variables Esteban*/
    private Button btnGuardar;
    private ImageButton btAgregarParadero;
    public String urlRemove = "";
    public VideoView vdoView;
    public TextView resetLocation;
    public Intent servicioTrack;
    public static int posicionParadero = -1;
    private PowerManager.WakeLock wakeLock;
    private ImageView iconGuardar;
    private PowerManager.WakeLock mWakeLock;
    private Vibrator vibrador;
    private ImageButton btStopVideo;

    public void gotoLogin() {
        startActivity(new Intent(this, WelcomeActivity.class));
    }

    private ArrayList<ZzleepAudio> audiosArray;
    private ZzleepAudio currentAudio;
    private MediaPlayer audioMP;
    private GridLayout menuTipo;
    private int datoTipoNuevo = 99;
    String tipoPlace = "";

    public void logOut() throws IOException {
        mAuth.signOut();
        user = null;
        destinies = new ArrayList<>();
        destinyAdapter = new StopItemAdapter(this, 0, destinies);
        ListView lv = findViewById(R.id.listUserStops);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        lv.setAdapter(destinyAdapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        gotoLogin();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        ((TextView) findViewById(R.id.km)).setText(R.string.llegaste);
        super.onNewIntent(intent);
        Bundle data = intent.getExtras();
        if(data == null||destinies==null||destinies.size()==0) {
            reloadData();
            return;
        }

        user = FirebaseAuth.getInstance().getCurrentUser();
        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            new LogUtils().v(TAG, " getIdToken:: " + idToken);
                            // Send token to your backend via HTTPS
                            // ...
                        } else {
                            // Handle error -> task.getException();

                        }
                    }
                });

        int posicion = data.getInt("posicion");
        startAlarm();
        destinies.get(posicion).setStatus(0);
        recargarParaderos(posicion);
        stopAudio();
    }
    void reloadData(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            new LogUtils().v(TAG, " getIdToken:: " + idToken);
                            // Send token to your backend via HTTPS
                            // ...
                        } else {
                            // Handle error -> task.getException();

                        }
                    }
                });

        //Verificar sesión
        if (user != null) {
            //String idToken = task.getResult().getToken();
            JsonTask peticionAlarmas = new JsonTask(MainActivity.this, 7);
            JsonTask peticionParaderos = new JsonTask(MainActivity.this, 2);
            JsonTask peticionAudios = new JsonTask(MainActivity.this, 9);

            if (user.isAnonymous()) {
                peticionParaderos.execute(ApiPath+"api/v1/places");
            } else {
                String aux_url2 = ApiPath+"api/v1/products?user_id=" + user.getUid();
                peticionAlarmas.execute(aux_url2);

                String aux_url3 = ApiPath+"api/v1/products?type=sound&user_id=" + user.getUid();
                peticionAudios.execute(aux_url3);

                String aux_url = ApiPath+"api/v1/places?user_id=" + user.getUid();
                peticionParaderos.execute(aux_url);
            }
            Log.v("GA-ZZ", "Usuario ingreso: " + user.getDisplayName());

            init();
        } else {
            gotoLogin();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PowerManager mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (mPowerManager != null) {
            mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "Service");
        }
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
            }
        };
        reloadData();
        unlockScreen();
        //stopService(new Intent(MainActivity.this, TrackService.class));
    }

    public void init() {
        ((TextView) findViewById(R.id.track)).setText(String.format("Hola %s", user.getDisplayName()));
        alarmaDefault = new ZzleepAlarm("Gallo", "icon_alarma3", 3, 0, "", "mono");
        AppConfig config = new AppConfig(getApplicationContext());
        menuStops = findViewById(R.id.menuStops);
        menuZzleep =  findViewById(R.id.menuZzleep);
        menuCreateStop =  findViewById(R.id.menuCreateStop);
        nameParadero = findViewById(R.id.textParadero);
        iconGuardar = findViewById(R.id.iconStopCreateMenu);
        SeekBar seekBar = findViewById(R.id.seekBar);
        alarmIcon =  findViewById(R.id.iconAlarm);
        btnGuardar =  findViewById(R.id.btnSaveStop);
        audioIcon =  findViewById(R.id.iconSoundAgregar);
        btAgregarParadero =  findViewById(R.id.btAgregar);
        resetLocation =  findViewById(R.id.btnResetLocation);
        btStopVideo =  findViewById(R.id.btStopVideo);
        vdoView =  findViewById(R.id.videoAlarma);
        LinearLayout btnMedioMain = findViewById(R.id.btnCentralMain);
        Button btnBack4 =  findViewById(R.id.btnBack4);
        Button btnBack3 =  findViewById(R.id.btnBack3);
        radioStopMenu =  findViewById(R.id.tvRadio);
        ImageView btLogout = findViewById(R.id.btLogout);
        servicioTrack = new Intent(this, TrackService.class);
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        if (powerManager != null) {
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag");
        }
        menuTipo =  findViewById(R.id.menuTipo);
        menuTipo.setVisibility(View.INVISIBLE);
        btnMedioMain.setOnClickListener(this);
        findViewById(R.id.btnKms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), KilometrosActivity.class), 11);
            }
        });
        findViewById(R.id.imageButton1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datoTipoNuevo = 1;
                placeSelected.setImage(Integer.toString(datoTipoNuevo));
                try {
                    guardarParadero(placeSelected);
                    getLocation();
                    updateMapPoint();
                    recargarParaderos(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                menuTipo.setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datoTipoNuevo = 2;
                placeSelected.setImage(Integer.toString(datoTipoNuevo));
                try {
                    guardarParadero(placeSelected);
                    getLocation();
                    updateMapPoint();
                    recargarParaderos(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                menuTipo.setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.imageButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datoTipoNuevo = 3;
                placeSelected.setImage(Integer.toString(datoTipoNuevo));
                try {
                    guardarParadero(placeSelected);
                    getLocation();
                    updateMapPoint();
                    recargarParaderos(2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                menuTipo.setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.imageButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datoTipoNuevo = 4;
                placeSelected.setImage("");
                try {
                    guardarParadero(placeSelected);
                    getLocation();
                    updateMapPoint();
                    recargarParaderos(destinies.size() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                menuTipo.setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.imageButton5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    alarmIcon.setImageResource(R.mipmap.btn_gallo_cuadro);
                    audioIcon.setImageResource(R.mipmap.btn_audio_cuadro);
                    menuCreateStop.setVisibility(View.INVISIBLE);
                    placeTransaccional = placeSelected;
                    addStopToMenu(placeTransaccional);
                    getLocation();
                    updateMapPoint();
                    recargarParaderos(destinies.size() - 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                menuTipo.setVisibility(View.INVISIBLE);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        inicializarGoogleApi();

        findViewById(R.id.btnShowStops).setOnClickListener(this);
        findViewById(R.id.findPlace).setOnClickListener(this);
        findViewById(R.id.btnShowMenuZzleep).setOnClickListener(this);
        findViewById(R.id.btnPerfil).setOnClickListener(this);
        findViewById(R.id.btnAlarmList).setOnClickListener(this);
        findViewById(R.id.btnAudio).setOnClickListener(this);
        findViewById(R.id.iconAlarm).setOnClickListener(this);

        btAgregarParadero.setOnClickListener(this);
        btLogout.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        btnBack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuZzleep.setVisibility(View.INVISIBLE);
            }
        });
        btnBack4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuStops.setVisibility(View.INVISIBLE);
            }
        });
        audioIcon.setOnClickListener(this);
        btStopVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btStopVideo.setVisibility(View.INVISIBLE);
                vibrador.cancel();
                if (vdoView.isPlaying()) {
                    vdoView.stopPlayback();
                }
                if(alarmSelected.isPlaying()){
                    alarmSelected.stop();
                }
                if (vdoView.getVisibility() == View.VISIBLE) {
                    vdoView.setVisibility(View.INVISIBLE);
                }
            }
        });
        findViewById(R.id.btnSaveStop).setOnClickListener(this);

        //Show tutorial
        if (userMarker != null) {
            userMarker.setAlpha(1);
            dropPinEffect(userMarker);
        }
        /*
        if (!config.tutorialTaken()) {
            findViewById(R.id.tutorial).setVisibility(View.VISIBLE);
            findViewById(R.id.tutorial).setOnClickListener(this);
            config.saveTutorial(true);
        } else {
            //TODO: Poner esto en una función de inicialización y sacar lo que está en el listener
            if (userMarker != null) {
                userMarker.setAlpha(1);
                dropPinEffect(userMarker);
            }
        }*/
        destinies = new ArrayList<>();
        ZzleepPlace home = new ZzleepPlace("Casa", "Tocar para Añadir", "0", "0", 0.0, "1");
        ZzleepPlace school = new ZzleepPlace("Oficina", "Tocar para Añadir", "0", "0", 0.0,"2");
        ZzleepPlace work = new ZzleepPlace("Universidad", "Tocar para Añadir", "0", "0", 0.0, "3");
        destinies.add(home);
        destinies.add(school);
        destinies.add(work);
        destinyAdapter = new StopItemAdapter(this, 0, destinies);

        //Asociamos el adaptador a la vista.
        ListView lv = findViewById(R.id.listUserStops);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        lv.setAdapter(destinyAdapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    private void inicializarGoogleApi() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be
                        // fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling
                            // startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have
                        // no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
        resetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserMarker();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        if (mGoogleApiClient != null)
            if (!mGoogleApiClient.isConnected())
                mGoogleApiClient.connect();


    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        if (audioSelected != null)
            audioSelected.stop();
        if (alarmSelected != null)
            alarmSelected.stop();

        if (mGoogleApiClient != null)
            if (mGoogleApiClient.isConnected())
                mGoogleApiClient.disconnect();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.getApplicationContext();
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                startLocation();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
            }
        }
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                if (targetMarker!=null) {
                    LatLng auxPos = mMap.getCameraPosition().target;
                    targetMarker.setPosition(auxPos);
                    placeSelected.setLatitude(Double.toString(auxPos.latitude));
                    placeSelected.setLongitude(Double.toString(auxPos.longitude));

                }
            }
        });
    }

    public void startLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            location = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (location != null) {
                updateMapPoint();
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    public void getArrayStopItem(String result) {
        try {
            destinies = new ArrayList<>();
            ZzleepPlace home = new ZzleepPlace("Casa", "Tocar para Añadir", "0", "0", 0.0, "1");
            ZzleepPlace work = new ZzleepPlace("Oficina", "Tocar para Añadir", "0", "0", 0.0, "2");
            ZzleepPlace school = new ZzleepPlace("Universidad", "Tocar para Añadir", "0", "0", 0.0, "3");
            destinies.add(home);
            destinies.add(work);
            destinies.add(school);

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

                if (finalObject.get("range")!=null) {
                    range = Double.parseDouble(finalObject.getString("range"));
                }
                ZzleepPlace destiny;
                destiny = new ZzleepPlace(name, address, lat, lng, range, image);
                //posible for para guardar alarma y audio
                if (finalObject.has("alarma_id")&&!finalObject.isNull("alarma_id")) {
                    int alarmid = finalObject.getInt("alarma_id");
                    if (alarmas != null) {
                        for (int k = 0; k < alarmas.size(); k++) {
                            if (alarmas.get(k).id == alarmid) {
                                destiny.alarma = alarmas.get(k);
                                DownloadTask downloadTask = new DownloadTask(this);
                                downloadTask.execute(destiny.alarma.video, "preview_" + destiny.alarma.name.replace(" ", "_"));
                                break;
                            }
                        }
                    }
                }
                if (finalObject.has("sound_id")&&!finalObject.isNull("sound_id")) {
                    int idAudio = finalObject.getInt("sound_id");

                    for (int k = 0; k < audiosArray.size(); k++) {
                        if (audiosArray.get(k).id == idAudio) {
                            destiny.audio = audiosArray.get(k);
                            break;
                        }
                    }
                }
                if (finalObject.has("id")&&!finalObject.isNull("id")) {
                    int id = finalObject.getInt("id");
                    destiny.setId(id);
                }
                switch (image){
                    case "1":
                        destinies.set(0, destiny);
                        break;
                    case "2":
                        destinies.set(1, destiny);
                        break;
                    case "3":
                        destinies.set(2, destiny);
                        break;
                    default:
                        destinies.add(destiny);
                        break;
                }
                createNewStop(destiny);

            }
            destinyAdapter = new StopItemAdapter(this, 0, destinies);

            //Asociamos el adaptador a la vista.
            ListView lv =  findViewById(R.id.listUserStops);
            lv.setOnItemClickListener(this);
            lv.setOnItemLongClickListener(this);
            lv.setAdapter(destinyAdapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        ZzleepPlace aux = (ZzleepPlace) parent.getAdapter().getItem(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Paradero");
        builder.setMessage("¿Estás seguro que quieres eliminar el paradero?");

        urlRemove = ApiPath+"api/v1/places/" + aux.getId();

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ApiDelete apiDelete = new ApiDelete();
                apiDelete.execute(urlRemove);
                eliminarParadero(position);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        placeSelected = (ZzleepPlace) parent.getAdapter().getItem(position);
        if (position <= 2 && placeSelected.getLatitude().equals("0") && placeSelected.getLongitude().equals("0")) {
            Integer aux = position + 1;
            tipoPlace = aux.toString();
            openFindPlace();
            return;
        }
        LatLng latLng = new LatLng(Double.parseDouble(placeSelected.getLatitude()), Double.parseDouble(placeSelected.getLongitude()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, mMap.getCameraPosition().zoom));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSIONS) {
            if (permissions.length == 1 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG,"Permisos aceptados");
                //startLocation();
            } else {
                Log.v("GA-ZZ", "Pedir permisos");
            }
        }
        else if (requestCode == 2) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                //resume tasks needing this permission
            }
        }
        else if (requestCode == 3){
            if(grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Es necesario permisos para poder usar la aplicación",Toast.LENGTH_LONG).show();
            }

        }
    }

    public void updateMapPoint() {
        if(location==null) return;
        LatLng p = new LatLng(location.getLatitude(),location.getLongitude());
        if (userMarker == null) {
            userMarker = mMap.addMarker(new MarkerOptions().position(p).flat(true).title("Estás aquí").icon(BitmapDescriptorFactory.fromResource(R.mipmap.user_pin)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p, 15F));
        } else {
            userMarker.setPosition(p);
            mMap.animateCamera(CameraUpdateFactory.newLatLng(p), 2000, null);
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON, WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }


    @Override
    public void onMapLongClick(LatLng latLng) {
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (menuCreateStop.getVisibility() == View.VISIBLE) {
            menuCreateStop.setVisibility(View.INVISIBLE);
            getLocationRequestUpdates();
            updateMapPoint();
            targetMarker.remove();
            targetMarker = null;
        }
        menuStops.setVisibility(View.INVISIBLE);
        menuZzleep.setVisibility(View.INVISIBLE);
        if (targetMarker!=null) {
            targetMarker.setPosition(latLng);
            placeSelected.setLatitude(Double.toString(latLng.latitude));
            placeSelected.setLongitude(Double.toString(latLng.longitude));
        }
        if (vdoView.isPlaying()) {
            vdoView.stopPlayback();
        }
        if (vdoView.getVisibility() == View.VISIBLE) {
            vdoView.setVisibility(View.INVISIBLE);
        }
    }

    public void toggleMenuStop() {
        menuStops.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        ObjectAnimator rotateAnim;

        if (menuStops.getVisibility() == View.VISIBLE) {
            rotateAnim = ObjectAnimator.ofFloat(menuStops, "alpha", 1, 0);
            ObjectAnimator rotateAnim5 = ObjectAnimator.ofFloat(menuStops, "translationY", 0, 100F);

            rotateAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.v("GA-ZZ", "Animación terminada");
                    menuStops.setVisibility(View.INVISIBLE);
                    menuStops.setLayerType(View.LAYER_TYPE_NONE, null);
                }
            });

            AnimatorSet as = new AnimatorSet();
            as.play(rotateAnim).with(rotateAnim5);
            as.setInterpolator(new AccelerateInterpolator(0.9F));
            as.setDuration(200);
            as.start();

        } else {
            menuStops.setVisibility(View.VISIBLE);

            ObjectAnimator rotateAnim2 = ObjectAnimator.ofFloat(menuStops, "alpha", 0, 1);
            rotateAnim = ObjectAnimator.ofFloat(menuStops, "translationY", 100F, 0);

            rotateAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.v("GA-ZZ", "Animación terminada");
                    menuStops.setLayerType(View.LAYER_TYPE_NONE, null);
                }
            });

            AnimatorSet as = new AnimatorSet();
            as.play(rotateAnim).with(rotateAnim2);
            as.setInterpolator(new AccelerateInterpolator(0.12F));
            as.setDuration(200);
            as.start();
        }
    }

    public void toggleMenuZzleep() {
        menuZzleep.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        ObjectAnimator rotateAnim;

        if (menuZzleep.getVisibility() == View.VISIBLE) {
            rotateAnim = ObjectAnimator.ofFloat(menuZzleep, "alpha", 1, 0);
            ObjectAnimator rotateAnim5 = ObjectAnimator.ofFloat(menuZzleep, "translationY", 0, 100F);

            rotateAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.v("GA-ZZ", "Animación terminada");
                    menuZzleep.setVisibility(View.INVISIBLE);
                    menuZzleep.setLayerType(View.LAYER_TYPE_NONE, null);
                }
            });

            AnimatorSet as = new AnimatorSet();
            as.play(rotateAnim).with(rotateAnim5);
            as.setInterpolator(new AccelerateInterpolator(0.9F));
            as.setDuration(200);
            as.start();

        } else {
            menuZzleep.setVisibility(View.VISIBLE);

            ObjectAnimator rotateAnim2 = ObjectAnimator.ofFloat(menuZzleep, "alpha", 0, 1);
            rotateAnim = ObjectAnimator.ofFloat(menuZzleep, "translationY", 100F, 0);

            rotateAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.v("GA-ZZ", "Animación terminada");
                    menuZzleep.setLayerType(View.LAYER_TYPE_NONE, null);
                }
            });

            AnimatorSet as = new AnimatorSet();
            as.play(rotateAnim).with(rotateAnim2);
            as.setInterpolator(new AccelerateInterpolator(0.12F));
            as.setDuration(200);
            as.start();
        }
    }


    /*ONCLICKGLOBAL*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.findPlace:
                tipoPlace = "";
                openFindPlace();
                break;
            case R.id.btnShowStops:
                toggleMenuStop();
                if (menuZzleep.getVisibility() == View.VISIBLE)
                    toggleMenuZzleep();
                break;
            case R.id.btnShowMenuZzleep:
                toggleMenuZzleep();

                if (menuStops.getVisibility() == View.VISIBLE)
                    toggleMenuStop();
                break;
            case R.id.tutorial:
                findViewById(R.id.tutorial).setVisibility(View.INVISIBLE);
                if (userMarker != null) {
                    userMarker.setAlpha(1);
                    dropPinEffect(userMarker);
                }
                break;
            case R.id.btnPerfil:
                openPerfil();
                break;
            case R.id.btnAlarmList:
                openAlarmList();
                break;
            case R.id.btnAudio:
                openAudioList();
                break;
            case R.id.btnSaveStop:
                try {

                    placeSelected.setRadio((double) ((SeekBar) findViewById(R.id.seekBar)).getProgress() + 100D);
                    placeSelected.setLatitude(Double.toString(targetMarker.getPosition().latitude));
                    placeSelected.setLongitude(Double.toString(targetMarker.getPosition().longitude));
                    placeSelected.setName(nameParadero.getText().toString());
                    placeSelected.setAlarma(alarmaSelected);
                    placeSelected.setAudio(currentAudio);
                    ZzleepPlace placeTemporal = placeSelected;
                    obtenerTipo(placeTemporal);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iconAlarm:
                openGetAlarmList();
                break;
            case R.id.btAgregar:
                setParadero();
                view.setVisibility(View.INVISIBLE);
                break;
            case R.id.btLogout:
                try {
                    logOut();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnCentralMain:
                startActivityForResult(new Intent(getApplicationContext(), KilometrosActivity.class), 11);
                break;
            case R.id.iconSoundAgregar:
                openGetAudioList();
                break;
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        Double radioSelected = Double.parseDouble(String.valueOf(progress));
        int aux = (radioSelected.intValue() + 100);
        radioStopMenu.setText(String.format("Radio: %s metros", String.valueOf(aux)));
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void openPerfil() {
        startActivity(new Intent(getApplicationContext(), PerfilZzleeperActivity.class));
    }

    private void openAlarmList() {
        Intent alarmScreen = new Intent(this, AlarmsList.class);
        alarmScreen.putExtra("opt", "buy");
        startActivityForResult(alarmScreen, 4);
    }

    private void openGetAlarmList() {
        Intent alarmScreen = new Intent(this, AlarmsList.class);
        alarmScreen.putExtra("opt", "create");
        startActivityForResult(alarmScreen, 4);
    }

    private void openAudioList() {
        Intent audioScreen = new Intent(this, AudioActivity.class);
        audioScreen.putExtra("opt", "buy");
        startActivityForResult(audioScreen, 5);
    }

    private void openGetAudioList() {
        Intent audioScreen = new Intent(this, AudioActivity.class);
        audioScreen.putExtra("opt", "create");
        startActivityForResult(audioScreen, 5);
    }

    private void openFindPlace() {

        menuStops.setVisibility(View.INVISIBLE);
        menuZzleep.setVisibility(View.INVISIBLE);
        Intent i = new Intent(this, PlaceActivity.class);
        String auxLati,auxLngt;
        if(userMarker!=null){
            auxLati = Double.toString(userMarker.getPosition().latitude);
            auxLngt = Double.toString(userMarker.getPosition().longitude);
        }else{
            auxLati = DEFAULT_LAT;
            auxLngt = DEFAULT_LNG;
        }
        i.putExtra("lati", auxLati);
        i.putExtra("lngt", auxLngt);

        i.putExtra("img", tipoPlace);
        startActivityForResult(i, 3);
    }

    private void dropPinEffect(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;
        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(1 - interpolator.getInterpolation((float) elapsed / duration), 0);
                marker.setAnchor(0.5f, 1.0f + 14 * t);
                if (t > 0.0) {
                    // Post again 15ms later.
                    handler.postDelayed(this, 15);
                } else {
                    marker.showInfoWindow();

                }
            }
        });
    }

    @Override
    public void onInteraction(View v, int action) {
        Log.d(TAG, "action : " + action);
        switch (action) {
            case 1: //CLICK ON icono alarma
//				openAlarmList();
                menuCreateStop.setVisibility(View.VISIBLE);
                break;
            case 2: // click en texto de busqueda

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            String nameAct = data.getStringExtra("name");
            menuCreateStop.setVisibility(View.VISIBLE);
            nameParadero.setText(nameAct, TextView.BufferType.EDITABLE);
        } else if (requestCode == 3 && resultCode == 20) {
            String nameAct = data.getStringExtra("name");
            String latitud = (data.getStringExtra("lat"));
            String longitud = (data.getStringExtra("lng"));
            String addressAct = data.getStringExtra("address");
            btAgregarParadero.setVisibility(View.VISIBLE);
            ZzleepPlace nuevo = new ZzleepPlace(nameAct, addressAct, latitud, longitud, 1000D, tipoPlace);
            placeSelected = nuevo;
            setMap(nuevo);
        } else if (requestCode == 4 && resultCode == Activity.RESULT_OK) {
            alarmaSelected = new ZzleepAlarm();
            alarmaSelected.name = data.getStringExtra("alarmName");
            alarmaSelected.icon = data.getStringExtra("alarmIcon");
            alarmaSelected.audio = data.getStringExtra("alarmAudio");
            alarmaSelected.video = data.getStringExtra("alarmVideo");
            alarmaSelected.id = data.getIntExtra("alarmId", 0);
            alarmaSelected.status = 3;
            alarmaSelected.price = 0;
            if (placeSelected != null) {
                Glide.with(this).load(alarmaSelected.icon).into(alarmIcon);
            }
            try {
                alarmSelected = new MediaPlayer();
                alarmSelected.setDataSource(alarmaSelected.video);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == 5 && resultCode == Activity.RESULT_OK) {
            int id = data.getIntExtra("audio_id", 0);
            for (ZzleepAudio e :
                    audiosArray) {
                if (e.id == id) {
                    currentAudio = e;
                }
            }
            ApiImg auxApi = new ApiImg(audioIcon);
            auxApi.execute(currentAudio.getIcon());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void obtenerTipo(ZzleepPlace nuevo) throws JSONException {
        switch (tipoPlace) {
            case "1":
            case "2":
            case "3":
                try {
                    guardarParadero(nuevo);
                    getLocation();
                    if(location!=null)
                        updateMapPoint();
                    recargarParaderos(Integer.parseInt(tipoPlace) - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                menuCreateStop.setVisibility(View.INVISIBLE);
                break;
            default:
                menuTipo.setVisibility(View.VISIBLE);
                menuTipo.bringToFront();
                menuCreateStop.setVisibility(View.INVISIBLE);
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (location == null)
            getLocation();
        if(location != null) {
            if (placeSelected == null) {
                placeSelected = new ZzleepPlace("Estas aquí", "Home", Double.toString(location.getLatitude()), Double.toString(location.getLongitude()), 100D, "");
            }
            if (targetMarker!=null) {
                setMap(placeSelected);
            } else {
                updateMapPoint();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }


    public void setParadero() {
        String nameAct = placeSelected.getName();
        menuCreateStop.setVisibility(View.VISIBLE);
        nameParadero.setText(nameAct, TextView.BufferType.EDITABLE);
        iconGuardar.setVisibility(View.VISIBLE);
        btnGuardar.setBackgroundResource(R.mipmap.icon_save);
        switch (tipoPlace) {
            case "1":
                iconGuardar.setImageResource(R.mipmap.icon_house);
                break;
            case "2":
                iconGuardar.setImageResource(R.mipmap.icon_work);
                break;
            case "3":
                iconGuardar.setImageResource(R.mipmap.icon_college);
                break;
            default:
                iconGuardar.setVisibility(View.GONE);
                btnGuardar.setBackgroundResource(R.mipmap.go);
                break;
        }
    }

    public void setMap(ZzleepPlace placeAux) {
        placeSelected = placeAux;
        if (placeSelected.getStatus() == 1) {
            Double latitud = Double.parseDouble(placeSelected.getLatitude());
            Double longitud = Double.parseDouble(placeSelected.getLongitude());
            LatLng latLng = new LatLng(latitud, longitud);
            if (targetMarker == null)
                targetMarker = mMap.addMarker(new MarkerOptions().draggable(true).position(latLng).title(placeSelected.getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.target_pin)));
            else
                targetMarker.setPosition(latLng);
            CameraUpdate center = CameraUpdateFactory.newLatLng(latLng);
            mMap.moveCamera(center);
        } else {
            tipoPlace = "";
            openFindPlace();
        }
    }

    private void guardarParadero(ZzleepPlace placeGuardar) throws JSONException {
        alarmIcon.setImageResource(R.mipmap.btn_gallo);
        audioIcon.setImageResource(R.mipmap.btn_audio);
        menuCreateStop.setVisibility(View.INVISIBLE);
        placeTransaccional = placeGuardar;
        if (!user.isAnonymous()) {
            String url = ApiPath+"api/v1/places";
            ApiPost apiPost = new ApiPost();
            try {
                if (user.isAnonymous()) {
                    apiPost.execute(url, placeTransaccional.getJSON("stop", user.getUid()));
                } else {
                    apiPost.execute(url, placeTransaccional.getJSON("user", user.getUid()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        getLocation();
        addStopToMenu(placeGuardar);
        updateMapPoint();
    }
    private void createNewStop(ZzleepPlace destiny) {
        if(targetMarker != null){
            targetMarker.remove();
            targetMarker = null;
        }
        LatLng latLng = new LatLng(Double.parseDouble(destiny.getLatitude()), Double.parseDouble(destiny.getLongitude()));
        MarkerOptions markerOptions =
                new MarkerOptions()
                        .draggable(true)
                        .position(latLng)
                        .title(destiny.getName());
        String type = destiny.getImage();
        switch (type){
            case "1":
                destiny.setMarker(mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_house))));
                break;
            case "2":
                destiny.setMarker(mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_work))));
                break;
            case "3":
                destiny.setMarker(mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_college))));
                break;
            default:
                destiny.setMarker(mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.target_pin))));
                break;
        }
        destiny.setCircle(mMap.addCircle(new CircleOptions().center(latLng).radius(0d).fillColor(0x33FF0000).strokeWidth(5F).strokeColor(0x99770000)));
        targetArea = mMap.addCircle(new CircleOptions().center(latLng).radius(0).fillColor(0x33FF0000).strokeWidth(0F).strokeColor(0x99770000));
    }

    private void addStopToMenu(ZzleepPlace destiny) {
        if (destinies == null) {
            destinies = new ArrayList<>();
        }
        if (!destiny.getImage().equals("")) {
            String aux = destiny.getImage();
            destinies.set(Integer.parseInt(aux) - 1, destiny);
        } else {
            destinies.add(destiny);
        }
        destinyAdapter = new StopItemAdapter(this, 0, destinies);
        ListView lv = findViewById(R.id.listUserStops);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        lv.setOnItemLongClickListener(this);
        lv.setAdapter(destinyAdapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        createNewStop(destiny);
    }

    public void recargarParaderos(int position) {
        double latitud = Double.parseDouble(destinies.get(position).getLatitude());
        double longitud = Double.parseDouble(destinies.get(position).getLongitude());
        double radio = destinies.get(position).getRadio();
        int estado = destinies.get(position).getStatus();

        LatLng latLng = new LatLng(latitud, longitud);
        for (int index = 0; index < destinies.size(); index++) {
            if (index == position) {
                continue;
            }
            ZzleepPlace elemento = destinies.get(index);
            if (elemento.getLatitude().equals("0") && elemento.getLongitude().equals("0")) {
                continue;
            }

            elemento.getCircle().remove();
            elemento.setCircle(mMap.addCircle(new CircleOptions().center(latLng).radius(0d).fillColor(0x33FF0000).strokeWidth(5F).strokeColor(0x99770000)));
            elemento.setStatus(1);
        }
        stopAudio();
        if (estado == 1) {
            if (destinies.get(position).getCircle() != null) {
                destinies.get(position).getCircle().remove();
            }
            destinies.get(position).setCircle(mMap.addCircle(new CircleOptions().center(latLng).radius(radio).fillColor(0x33FF0F00).strokeWidth(5F).strokeColor(0x99000000)));
            destinies.get(position).setStatus(0);
            posicionParadero = position;
//VAS X KMS ZZLEEP
            Double distanceInMeters = 1e9;
            if(location!=null) {
                Location location1 = location;
                Location location2 = new Location("");
                location2.setLatitude(latLng.latitude);
                location2.setLongitude(latLng.longitude);
                distanceInMeters = (double) location1.distanceTo(location2);
                ((TextView) findViewById(R.id.km)).setText(String.format("VAS %s KMS ZZLEEP", Integer.toString(distanceInMeters.intValue() / 1000)));
            }
            alarmaSelected = destinies.get(position).getAlarma();
            currentAudio = destinies.get(position).getAudio();
            if (distanceInMeters < destinies.get(position).getRadio() + 100) {
                ((TextView) findViewById(R.id.km)).setText(R.string.llegaste);
                startAlarm();
                destinies.get(position).setStatus(0);
                recargarParaderos(position);
            } else {
                servicioTrack.putExtra("posicion",position);
                servicioTrack.putExtra("radius",destinies.get(position).getRadio());
                servicioTrack.putExtra("latitude",Double.valueOf(destinies.get(position).getLatitude()));
                servicioTrack.putExtra("longitude",Double.valueOf(destinies.get(position).getLongitude()));
                startAudio();
                startService(servicioTrack);
                wakeLock.acquire(10*60*1000L /*10 minutes*/);
            }
        } else {
            destinies.get(position).getCircle().remove();
            destinies.get(position).setCircle(mMap.addCircle(new CircleOptions().center(latLng).radius(0d).fillColor(0x33FF0000).strokeWidth(5F).strokeColor(0x99770000)));
            destinies.get(position).setStatus(1);
            stopService(servicioTrack);
        }

        ListView lv = findViewById(R.id.listUserStops);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        lv.setAdapter(destinyAdapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    public void eliminarParadero(int position) {
        destinies.get(position).getCircle().remove();
        destinies.get(position).getMarker().remove();

        ZzleepPlace home = new ZzleepPlace("Casa", "Tocar para Añadir", "0", "0", 0.0, "1");
        ZzleepPlace school = new ZzleepPlace("Oficina", "Tocar para Añadir", "0", "0", 0.0, "2");
        ZzleepPlace work = new ZzleepPlace("Universidad", "Tocar para Añadir", "0", "0", 0.0,"3");
        switch (position) {
            case 0:
                destinies.set(position, home);
                break;
            case 1:
                destinies.set(position, school);
                break;
            case 2:
                destinies.set(position, work);
                break;
            default:
                destinies.remove(position);
                break;
        }
        ListView lv = findViewById(R.id.listUserStops);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        lv.setOnItemLongClickListener(this);
        lv.setAdapter(destinyAdapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null)
            if (!mGoogleApiClient.isConnected())
                mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        try {
            stopService(servicioTrack);
            wakeLock.release();
        } catch (Exception e) {
            Log.d(TAG,"Error al detener el servicio y soltar el wakeLock");
        }
        super.onDestroy();
    }

    public void updateUserMarker() {
        getLocation();
        if(location!=null)
            updateMapPoint();
    }

    public void setUserMarker(LatLng posicion) {
            userMarker.setPosition(posicion);
        if(location!=null) {
            location.setLongitude(posicion.longitude);
            location.setLatitude(posicion.latitude);
        }
    }

    public void startAlarm() {
        try {
            if (alarmaSelected.video != null && !vdoView.isPlaying()) {
                vdoView.setVisibility(View.VISIBLE);
                vdoView.setMediaController(new MediaController(this));
                vdoView.setVideoPath(getFilesDir().getPath()+"/preview_" + alarmaSelected.name.replace(" ", "_"));
                if (audioMP != null && audioMP.isPlaying()) {
                    audioMP.stop();
                }
                vdoView.setMediaController(null);
                vdoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.seekTo(alarmSelected.getCurrentPosition());
                        mediaPlayer.setLooping(true);
                        alarmSelected.stop();
                    }
                });
                vdoView.start();
                btStopVideo.setVisibility(View.VISIBLE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            } else {
                alarmSelected = MediaPlayer.create(this, R.raw.gallo);
                alarmSelected.start();
            }
        } catch (Exception e) {
            alarmSelected = MediaPlayer.create(this, R.raw.gallo);
            alarmSelected.start();
        }
        vibrador = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 500, 500};
        if(vibrador!=null)
            vibrador.vibrate(pattern,0);
        else{
            Log.d(TAG,"No se pudo ejecutar la vibración de la alarma");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        setUserMarker(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onBackPressed() {
        if (menuCreateStop.getVisibility() == View.VISIBLE) {
            menuCreateStop.setVisibility(View.INVISIBLE);
            getLocationRequestUpdates();
            updateMapPoint();
            targetMarker.remove();
            targetMarker = null;
        }
        menuStops.setVisibility(View.INVISIBLE);
        menuZzleep.setVisibility(View.INVISIBLE);

        if (vdoView.isPlaying()) {
            vdoView.stopPlayback();
        }
        if (vdoView.getVisibility() == View.VISIBLE) {
            vdoView.setVisibility(View.INVISIBLE);
        }
        super.onBackPressed();
    }

    void getLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.v("GA-ZZ", "Pedir permisos");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSIONS);
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }
    private void getLocationRequestUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.v("GA-ZZ", "Pedir permisos");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSIONS);
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,  this);
    }

    public void obtenerAlarmas(String result) {
        try {
            alarmas = new ArrayList<>();
            JSONObject objJson = new JSONObject(result);
            JSONArray auxJson = objJson.getJSONArray("data");
            for (int i = 0; i < auxJson.length(); i++) {
                JSONObject auxObj2 = auxJson.getJSONObject(i);

                String name = auxObj2.getString("name");
                String icon = auxObj2.getString("image");
                int status = auxObj2.getInt("is_owned");
                double price = auxObj2.getDouble("amount");
                String video = auxObj2.getString("preview");
                String audio = auxObj2.getString("preview");
                int id = auxObj2.getInt("id");
                ZzleepAlarm tmpAlarm = new ZzleepAlarm(name, icon, status, price, video, audio);
                tmpAlarm.id = id;
                alarmas.add(tmpAlarm);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void obtenerAudios(String result) throws JSONException {
        audiosArray = new ArrayList<>();

        JSONObject aux = new JSONObject(result);
        JSONArray alarmas = aux.getJSONArray("data");
        String name;
        String icon;
        Integer status;
        Integer company_id;
        Double price;
        String preview;
        //String audio;
        String description;
        Integer id;
        Double discount;
        Integer points;
        for (int i = 0; i < alarmas.length(); i++) {
            JSONObject auxAlarma = alarmas.getJSONObject(i);
            name = auxAlarma.getString("name");
            icon = auxAlarma.getString("image");
            description = auxAlarma.getString("description");
            status = auxAlarma.getInt("is_owned");
            company_id = auxAlarma.getInt("company_id");
            price = auxAlarma.getDouble("amount");
            discount = auxAlarma.getDouble("discount");
            points = auxAlarma.getInt("points");
            preview = auxAlarma.getString("preview");
            //audio = auxAlarma.getString("product_file");
            id = auxAlarma.getInt("id");
            ZzleepAudio auxAlarm = new ZzleepAudio(id, name, icon, description, status, price, discount, points, preview, company_id);
            auxAlarm.id = id;
            audiosArray.add(auxAlarm);
        }

    }

    public void startAudio() {
        if (currentAudio != null && currentAudio.getPreview().length() > 0)
            this.audioMP=MusicTask.execute(currentAudio.getPreview());
    }

    public void stopAudio() {
        try {
            if (audioMP != null && audioMP.isPlaying())
                audioMP.stop();
        } catch (Exception e) {
            Log.d(TAG,"No se pudo detener el audio");
        }
    }
    private void unlockScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                |WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);

        mWakeLock.acquire(10*60*1000L /*10 minutes*/);
        int flags = WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON;
        getWindow().addFlags(flags);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

    }
}