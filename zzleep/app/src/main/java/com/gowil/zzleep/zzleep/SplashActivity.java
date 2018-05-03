package com.gowil.zzleep.zzleep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gowil.zzleep.app.ui.activity.CulqiActivity;

/**
 * Created by Gerson on 11/02/16.
 */
public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = new Intent(this, MainActivity.class);
		//Intent intent = new Intent(this, CulqiActivity.class);
		startActivity(intent);
		finish();
	}
}
