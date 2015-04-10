package com.nicotinaestudio.forestamovil.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nicotinaestudio.forestamovil.R;
import com.nicotinaestudio.forestamovil.context.AuthContext;
import com.nicotinaestudio.forestamovil.dal.CATDao;
import com.nicotinaestudio.forestamovil.dal.UsuariosDao;
import com.nicotinaestudio.forestamovil.http.HttpRequest;
import com.nicotinaestudio.forestamovil.http.HttpRequest.HttpCallback;
import com.nicotinaestudio.forestamovil.model.Usuario;

/**
 * Muestra la ventana principal para el login de usuarios.
 *
 */
public class AuthenticationActivity extends Activity {

	
	private EditText etUsuario;
	private EditText etPassword;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.authentication_activity);
     
	     etUsuario = (EditText)findViewById(R.id.et_usuario);
	     etPassword = (EditText)findViewById(R.id.et_password);
	     
	     cargarCAT();
	}
	
	public void onClick_Login(View v){
		
		final String usuario = etUsuario.getText().toString();
		final String password = etPassword.getText().toString();
		
		Usuario u = UsuariosDao.login(usuario, password); 
		
		if(u != null){
			
			AuthContext auth = AuthContext.buildAuth();
			auth.setUsuario(u);
			
			Toast.makeText(getApplication(), "Bienvenido", Toast.LENGTH_SHORT).show();
			
			if(!usuario.equals("is"))
				startActivity(new Intent(AuthenticationActivity.this, HomeActivity.class));
			else
				startActivity(new Intent(AuthenticationActivity.this, InspectorHomeActivity.class));
			
			finish();
		}
		else{
			Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	private HttpCallback mCallback = new HttpCallback() {
		
		@Override
		public void onTerminate(HttpRequest.HttpStatus status) {
			
			if(status == HttpRequest.HttpStatus.OK){
				Toast.makeText(AuthenticationActivity.this.getApplicationContext(),
						"OK", Toast.LENGTH_SHORT).show();
			}
			
		}
	};
	
	private void cargarCAT(){
		
		CATDao cdao = new CATDao(getApplicationContext());
		
		if(cdao.totalRows()<=0){		
			HttpRequest http = new HttpRequest();
			http.obtenerCAT(getApplicationContext(), mCallback);
			http = null;
		}
		
		cdao.close();		
		cdao = null;
		
	}
	
	
}
