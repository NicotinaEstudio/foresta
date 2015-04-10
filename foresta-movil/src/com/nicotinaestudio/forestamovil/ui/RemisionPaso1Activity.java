package com.nicotinaestudio.forestamovil.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nicotinaestudio.forestamovil.R;

public class RemisionPaso1Activity extends BaseActivity {

	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivity_remision_paso1);
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
	}
	
	public void siguiente(View v){
		startActivity(new Intent(getApplicationContext(), RemisionPaso2Activity.class));
		finish();
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
