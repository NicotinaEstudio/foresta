package com.nicotinaestudio.forestamovil.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicotinaestudio.forestamovil.R;
import com.nicotinaestudio.forestamovil.context.AuthContext;
import com.squareup.picasso.Picasso;

public abstract class BaseActivity extends FragmentActivity {

	protected boolean onBaseOptionsItemSelected(int itemId) {

		int id = itemId;

		switch (id) {
		case R.id.action_settings:
			return true;
		case R.id.action_registro_transportista:
			startActivityForResult(new Intent(getApplicationContext(),
					TransportistasRegistroActivity.class), 1);
			return true;
		case R.id.action_registro_transporte:
			startActivityForResult(new Intent(this,
					TransporteRegistrarActivity.class), 1);
			return true;
		case R.id.action_remisiones:
			startActivity(new Intent(getApplicationContext(),
					RemisionesActivity.class));
			return true;
		case R.id.action_registro_remision:
			startActivity(new Intent(getApplicationContext(),
					RemisionPaso1Activity.class));
			return true;
		default:
			return false;
		}
	}
		
			
	protected void setInformacionUsuario(){
    	
    	AuthContext auth = AuthContext.buildAuth();
    	
    	((TextView) findViewById(R.id.tv_nombre_usuario)).setText(auth.getUsuario().nombre);
    	((ImageView) findViewById(R.id.img_face)).setImageResource(auth.getUsuario().foto);
    	((TextView) findViewById(R.id.tv_roll_usuario)).setText(auth.getUsuario().roll);
    	
    	ImageView i = (ImageView) findViewById(R.id.img_fondo);    	
    	Picasso.with(getApplicationContext()).load(auth.getUsuario().foto).resize(8, 8).into(i);
    	
    }
	
	protected void onStart(){
		super.onStart();
		setInformacionUsuario();
	}

	public void crearRemision(View v) {
		startActivity(new Intent(getApplicationContext(),
				RemisionesActivity.class));
	}
	
	public void cerrarSesion(View v){
		startActivity(new Intent(getApplicationContext(),
				AuthenticationActivity.class));
		finish();
	}
	

}
