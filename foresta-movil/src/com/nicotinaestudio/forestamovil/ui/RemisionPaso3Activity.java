package com.nicotinaestudio.forestamovil.ui;

import java.util.UUID;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nicotinaestudio.forestamovil.R;
import com.nicotinaestudio.forestamovil.Utils;
import com.nicotinaestudio.forestamovil.dal.CATDao;
import com.nicotinaestudio.forestamovil.dal.RemisionesDao;
import com.nicotinaestudio.forestamovil.dal.TransportesDao;
import com.nicotinaestudio.forestamovil.dal.TransportistaDao;
import com.nicotinaestudio.forestamovil.http.HttpRequest;
import com.nicotinaestudio.forestamovil.http.HttpRequest.HttpCallback;
import com.nicotinaestudio.forestamovil.http.HttpRequest.HttpStatus;
import com.nicotinaestudio.forestamovil.model.CAT;
import com.nicotinaestudio.forestamovil.model.Remision;
import com.nicotinaestudio.forestamovil.model.Transporte;
import com.nicotinaestudio.forestamovil.model.Transportista;

public class RemisionPaso3Activity extends BaseActivity{

	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	
	// Datos pasados por Intent
	String cat;
	String transporte;
	String transportista;
	double cantidad;
	String descMateria;
	
	// Objetos Persistentes
	private Transporte mTransporte;
	private Transportista mTransportista;
	private CAT mCat;
	
	
	// Objetos Visuales.
	// Remitente
	private TextView tvRemOrigen;
	private TextView tvRemRFN; 
	private TextView tvRemMun;
	private TextView tvRemEnt;
	private TextView tvRemResol;
	private TextView tvRemFecha;
	private TextView tvRemVigencia;
	private TextView tvRemVolAut;
	private TextView tvRemAnua;
	private TextView tvRemGenero;	
	// CAT
	private TextView tvCATNombre;
	private TextView tvCATDomicilio;
	private TextView tvCATPoblacion;
	private TextView tvCATMun;
	private TextView tvCATEnt;
	// Transporte
	private TextView tvTranMedio;
	private TextView tvTranProp;
	private TextView tvTranMarca;
	private TextView tvTranModelo;
	private TextView tvTranTipo;
	private TextView tvTranCap;
	private TextView tvTranMatri;
	// Materia Prima
	private TextView tvMatDesc;
	private TextView tvMatCant;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivity_remision_paso3);		
		setDrawer(savedInstanceState);		
	}
	
	private void setDrawer(Bundle savedInstanceState){	

		final ActionBar actionBar = getActionBar();

		mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawer, /* DrawerLayout object */
		R.drawable.icon_menu_principal, /* nav drawer icon to replace 'Up' caret */
		R.string.app_name, /* "open drawer" description */
		R.string.app_name /* "close drawer" description */
		) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(getTitle());
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(getTitle());
			}

		};

		mDrawer.setDrawerListener(mDrawerToggle);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		
		if(getIntent().getExtras() != null){
			// Se obtiene la información pasada en el intent.
			cat = getIntent().getExtras().getString("cat");
			transporte = getIntent().getExtras().getString("transporte");
			transportista = getIntent().getExtras().getString("transportista");		
			cantidad = getIntent().getExtras().getDouble("cantidad");
			descMateria = getIntent().getExtras().getString("materia");		
			init();
			
			cargarInfo();
		}
	}
	
	// Inicializa los textview
	private void init(){
		
		tvCATNombre = (TextView) findViewById(R.id.tv_prev_dest_nombre);
		tvCATDomicilio = (TextView) findViewById(R.id.tv_prev_dest_domicilio);
		tvCATPoblacion = (TextView) findViewById(R.id.tv_prev_dest_pob);
		tvCATMun = (TextView) findViewById(R.id.tv_prev_dest_mun);
		tvCATEnt = (TextView) findViewById(R.id.tv_prev_dest_entidad);
		
		tvTranMedio = (TextView) findViewById(R.id.tv_prev_trans_medio);
		tvTranProp = (TextView) findViewById(R.id.tv_prev_trans_prop);
		tvTranMarca = (TextView) findViewById(R.id.tv_prev_trans_marca);
		tvTranModelo = (TextView) findViewById(R.id.tv_prev_trans_modelo);
		tvTranTipo = (TextView) findViewById(R.id.tv_prev_trans_tipo);
		tvTranCap = (TextView) findViewById(R.id.tv_prev_trans_capacidad);
		tvTranMatri = (TextView) findViewById(R.id.tv_prev_trans_matricula);
		
		tvMatDesc = (TextView) findViewById(R.id.tv_prev_mat_descripcion);
		tvMatCant = (TextView) findViewById(R.id.tv_prev_mat_cantidad);		
		tvMatDesc.setText(descMateria);
		tvMatCant.setText(String.valueOf(cantidad));
	}
	
	
	
	
	private void cargarInfo() {
		cargarTransporte(transporte);
		cargarTransportista(transportista);
		cargarCAT(cat);
	}
	
	// Establece la información del CAT
	private void cargarCAT(String uuid){
		CATDao cdao = new CATDao(getApplicationContext());
		mCat = cdao.obtenerPorUUID(uuid);
		cdao.close();
				
		tvCATNombre.setText(mCat.denominacionORazonSocial);		
		tvCATDomicilio.setText(mCat.domicilio);		
		tvCATPoblacion.setText(mCat.municipio);
		tvCATMun.setText(mCat.municipio);
		tvCATEnt.setText(mCat.estado);
	}
	
	// Estblece la informacion del Transporte
	private void cargarTransporte(String uuid){
		TransportesDao tdao = new TransportesDao(getApplicationContext());
		mTransporte = tdao.obtenerPorUUID(uuid);
		tdao.close();
		
		tvTranMedio.setText(mTransporte.medio);		
		tvTranProp.setText(mTransporte.propietario);
		tvTranMarca.setText(mTransporte.marca);
		tvTranModelo.setText(mTransporte.modelo);
		tvTranTipo.setText(mTransporte.tipo);
		tvTranCap.setText(mTransporte.capacidad);
		tvTranMatri.setText(mTransporte.matricula);
	}
	
	
	// Estblece la informacion del Transportista.
	private void cargarTransportista(String uuid){
		TransportistaDao tdao = new TransportistaDao(getApplicationContext());
		mTransportista = tdao.obtenerPorUUID(uuid);
		tdao.close();
	}

	private void iniciarNFC(Remision r){
		
		String remision_sms = String.format("%s_1_%s_TA_%s_TE_%s_SA_5000_100000_MP_%s_%s_DE_%s_RE_1",
				r.uuid, r.id, mTransporte.id,  mTransportista.id, r.cantidad, r.materia, r.cat);
		
		Intent i = new Intent(RemisionPaso3Activity.this, RemisionEscrituraNFC.class);
		i.putExtra("remision", remision_sms);		
		startActivity(i);
		
		
	}
	
	public void siguiente(View v) {
		
		final RemisionesDao rdao = new RemisionesDao(getApplicationContext());

		final Remision r = new Remision();
		
		r.uuid = UUID.randomUUID().toString();
		r.cantidad = cantidad;
		r.materia = descMateria;
		r.cat = cat;
		r.transporte = transporte;
		r.transportista = transportista;

		long id = rdao.guardar(r);
		
		if(id > 0 ){
			
			r.id = id;
			
			if(Utils.isNetworkAvailable(getApplicationContext())){
				HttpRequest http = new HttpRequest();
				http.enviarRemision(getApplicationContext(), mTransportista, mTransporte, r, new HttpCallback() {
					
					@Override
					public void onTerminate(HttpStatus status) {
						
						if(status == HttpStatus.OK){
							Toast.makeText(getApplicationContext(), "Sincronizado", Toast.LENGTH_SHORT).show();						
						}
						else{
							enviarSMS(r);
						}
						
						rdao.close();	
						
						//iniciarNFC(r);
						String remision_sms = String.format("%s_1_%s_TA_%s_TE_%s_SA_5000_100000_MP_%s_%s_DE_%s_RE_1",
								r.uuid, r.id, mTransporte.id,  mTransportista.id, r.cantidad, r.materia, r.cat);
						
						Intent i = new Intent(RemisionPaso3Activity.this, RemisionEscrituraNFC.class);
						i.putExtra("remision", remision_sms);	
						
						startActivity(i);
						
						RemisionPaso3Activity.this.finish();
							
					}
				});
			}
			else{
						
				// Se intenta enviar por sms.
				enviarSMS(r);
				
				rdao.close();
				
				iniciarNFC(r);
				RemisionPaso3Activity.this.finish();
			}
		}else{
			Toast.makeText(getApplicationContext(),
					"No es posible guardar la remisión.", Toast.LENGTH_SHORT)
					.show();
		}
		
	}
	
	private void enviarSMS(Remision r){
		
		String remision_sms = String.format("%s_1_%s_TA_%s_TE_%s_SA_5000_100000_MP_%s_%s_DE_%s_RE_1",
				r.uuid, r.id, mTransporte.id,  mTransportista.id, r.cantidad, r.materia, r.cat);
		
		SmsManager sms = SmsManager.getDefault();
		//SmsMessage message = new SmsMessage();
		
		
		//sms.
		sms.sendTextMessage(getString(R.string.phoneNumber), null, remision_sms, null, null);
		
		Utils.Log(remision_sms);
		Toast.makeText(getApplicationContext(), "Se ha enviado la remisión por sms. Recibira un mensaje de confirmación en los sig. minutos", Toast.LENGTH_SHORT).show();
	}
	
	
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	if (mDrawerToggle.onOptionsItemSelected(item)) {
	          return true;
	    }    	
    	
    	onBaseOptionsItemSelected(item.getItemId());
        
        return super.onOptionsItemSelected(item);
    }
    
}
