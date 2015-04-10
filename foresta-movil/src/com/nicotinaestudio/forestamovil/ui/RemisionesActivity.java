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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.nicotinaestudio.forestamovil.R;
import com.nicotinaestudio.forestamovil.Utils;
import com.nicotinaestudio.forestamovil.adapters.RemisionAdapter;
import com.nicotinaestudio.forestamovil.dal.RemisionesDao;
import com.nicotinaestudio.forestamovil.model.Remision;

public class RemisionesActivity extends BaseActivity{

	
	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;

	private ListView mList;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remisiones);
		setDrawer();		
	}
	
	private void setDrawer(){	

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
		
		RemisionesDao rDao = new RemisionesDao(getApplicationContext());			
		ArrayList remisiones = rDao.obtenerTodos();
		
		if (remisiones != null) {
			mList = (ListView) findViewById(R.id.lv_remisiones);
			mList.setAdapter(new RemisionAdapter(getApplicationContext(),
					remisiones));
			
			mList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					final Remision r = (Remision) parent.getItemAtPosition(position);
					Intent i = new Intent(getApplicationContext(),
							RemisionPaso3Activity.class);
					
					i.putExtra("cat", r.cat);
					i.putExtra("transporte", r.transporte);
					i.putExtra("transportista", r.transportista);
					i.putExtra("cantidad", r.cantidad);
					i.putExtra("materia", r.materia);
					
					startActivity(i);					
					
				}
				
			});
		}
		
		rDao.close();
		
	}
	
	public void siguiente(View v){
		startActivity(new Intent(getApplicationContext(), RemisionPaso1Activity.class));
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
        getMenuInflater().inflate(R.menu.remisiones, menu);
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
