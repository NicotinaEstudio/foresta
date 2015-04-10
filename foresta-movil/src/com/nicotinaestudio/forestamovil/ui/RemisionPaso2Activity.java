package com.nicotinaestudio.forestamovil.ui;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nicotinaestudio.forestamovil.R;
import com.nicotinaestudio.forestamovil.adapters.SpinnerSimpleAdapter;
import com.nicotinaestudio.forestamovil.dal.CATDao;
import com.nicotinaestudio.forestamovil.dal.TransportesDao;
import com.nicotinaestudio.forestamovil.dal.TransportistaDao;
import com.nicotinaestudio.forestamovil.model.CAT;
import com.nicotinaestudio.forestamovil.model.Transportista;
import com.nicotinaestudio.forestamovil.model.Transporte;

public class RemisionPaso2Activity extends BaseActivity {

	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;

	private Spinner spTransporte, spTransportistas, spCats, spMateria;
	private EditText etCantidad;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivity_remision_paso2);
		setDrawer();

		init();
	}

	private void init() {

		spTransporte = (Spinner) findViewById(R.id.sp_transporte);
		spTransportistas = (Spinner) findViewById(R.id.sp_transportista);
		spCats = (Spinner) findViewById(R.id.sp_cat);
		etCantidad = (EditText) findViewById(R.id.et_cantidad);
		spMateria = (Spinner) findViewById(R.id.sp_materia);
		
		// Se establece los transportes
		final TransportesDao tdao = new TransportesDao(getApplicationContext());
		final ArrayList transportes = tdao.obtenerTodos();
		if(transportes != null)
		{
			final SpinnerSimpleAdapter adapter = new SpinnerSimpleAdapter(
					getApplicationContext(), R.layout.item_transportes_list, transportes);
			spTransporte.setAdapter(adapter);
		}
		tdao.close();

		// Se establece los transportistas.
		TransportistaDao trDao = new TransportistaDao(getApplicationContext());
		final ArrayList transportistas = trDao.obtenerTodos();
		
		if(transportistas !=null){
			final SpinnerSimpleAdapter tAdapter = new SpinnerSimpleAdapter(
					getApplicationContext(), R.layout.item_list, transportistas);
		
			spTransportistas.setAdapter(tAdapter);
		}		
		trDao.close();
		
		// Se establece los cats
		CATDao cdao = new CATDao(getApplicationContext());
		final ArrayList cats = cdao.obtenerTodos();
		
		if(cats !=null){
			final SpinnerSimpleAdapter cAdapter = new SpinnerSimpleAdapter(
					getApplicationContext(), cats);
		
			spCats.setAdapter(cAdapter);
		}		
		trDao.close();
		
	}

	private void setDrawer() {

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
	}

	public void siguiente(View v) {
		startActivity(new Intent(RemisionPaso2Activity.this.getApplicationContext(), RemisionPaso3Activity.class));
		
//		double cantidad = Double.parseDouble(etCantidad.getText()
//				.toString());
//		
//		String cat = ((CAT) spCats.getSelectedItem()).uuid;
//		String transporte = ((Transporte) spTransporte.getSelectedItem()).uuid;
//		String transportista = ((Transportista) spTransportistas.getSelectedItem()).uuid;
//		
//		
//		RemisionesDao rdao = new RemisionesDao(getApplicationContext());
//		
//		Remision r = new Remision();
//		r.cantidad = cantidad;
//		r.cat = cat;
//		r.transporte = transporte;
//		r.transportista = transportista;
//		
//		rdao.guardar(r);
//		
//		rdao.close();
		
		String cat = ((CAT) spCats.getSelectedItem()).uuid;
		String transporte = ((Transporte) spTransporte.getSelectedItem()).uuid;
		String transportista = ((Transportista) spTransportistas.getSelectedItem()).uuid;
		String materia = spMateria.getSelectedItem().toString();
		
		double cantidad;
		try {
			cantidad = Double.parseDouble(etCantidad.getText().toString());
			
			Intent i = new Intent(getApplicationContext(),
					RemisionPaso3Activity.class);
			i.putExtra("cat", cat);
			i.putExtra("transporte", transporte);
			i.putExtra("transportista", transportista);
			i.putExtra("cantidad", cantidad);
			i.putExtra("materia", materia);
			
			startActivity(i);
			finish();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Ingrese una cantidad válida", Toast.LENGTH_SHORT).show();
		}
		
		
		
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
