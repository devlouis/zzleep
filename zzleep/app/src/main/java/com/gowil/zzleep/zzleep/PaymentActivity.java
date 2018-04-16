package com.gowil.zzleep.zzleep;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import com.gowil.zzleep.Culqi.Card;
import com.gowil.zzleep.Culqi.Token;
import com.gowil.zzleep.Culqi.TokenCallback;
import com.gowil.zzleep.R;
import com.gowil.zzleep.utils.ApiPost;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener
{
	ProgressDialog dialog;
	String userid ="";
	String tokenCode="";
	String nombre="";
	String precio="";
	String userToken="";
	Integer id;
	public String ApiPath = "http://app.zzleep.me/";
	@Override
	protected void attachBaseContext(Context newBase)
	{
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

        dialog = new ProgressDialog(this);
		setContentView(R.layout.activity_payment);

		id=(getIntent().getIntExtra("id",0));
		nombre=getIntent().getStringExtra("nombre");
		precio=getIntent().getStringExtra("precio");
		userid=getIntent().getStringExtra("userid");
		userToken=getIntent().getStringExtra("userToken");
		//((TextView)(findViewById(R.id.txTituloPayment))).setText("Comprar Alarma: "+nombre+"(S./ "+precio+")");
		((Button)(findViewById(R.id.btnPay))).setText("PAGAR S./ "+precio);

        findViewById(R.id.btnPay).setOnClickListener(this);
	}

	private void loadToken()
	{
		Card card = new Card("4111111111111111", "123", 9, 2020, "wm@wm.com");
		/*card.setApellido("Doe");
		card.setNombre("Jon");
		card.setCorreo_electronico("jon@culqi.com");
		card.setCvv(123);
		card.setNumero(4111111111111111L);
		card.setA_exp(9);
		card.setM_exp(2020);
		*/
		Token token = new Token("pk_test_QhKHP0LiB6rLOr81");
		Log.v("GA", "Llamando al servidor");
        dialog.setMessage("Comprando...");
        dialog.setCancelable(false);
        dialog.show();

		token.createToken(getApplicationContext(), card, new TokenCallback() {
			@Override
			public void onSuccess(JSONObject token) {
				try {
					Log.v("GA", token.get("id").toString());
					tokenCode=token.get("id").toString();
					procesarCompra();
				} catch (JSONException e) {
                    dialog.dismiss();
					e.printStackTrace();e.printStackTrace();
				}
			}
			@Override
			public void onError(Exception error) {
                dialog.dismiss();
				//Toast.makeText(getApplicationContext(), "Error al generar el token", Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnPay:
				loadToken();
				break;
		}
	}
	public void procesarCompra(){
		String queryJSON="{";
		queryJSON=queryJSON+"user_token:'"+userToken+"',";
		queryJSON=queryJSON+"token_id:'"+tokenCode+"',";
		queryJSON=queryJSON+"user_id:'"+userid+"',product_id:'"+this.id+"'}";
		ApiPost apiPost = new ApiPost(this,2);
		apiPost.execute(ApiPath+"api/v1/products/buy",queryJSON);
	}
	public void exitoCompra(){
		SharedPreferences prefs = this.getSharedPreferences(
				"com.gowil.zzleep", Context.MODE_PRIVATE);
		prefs.edit().putBoolean(""+this.id, true).commit();
		Intent returnIntent = new Intent();
		returnIntent.putExtra("result",true);
		setResult(Activity.RESULT_OK,returnIntent);
        dialog.dismiss();
		finish();
	}
}
