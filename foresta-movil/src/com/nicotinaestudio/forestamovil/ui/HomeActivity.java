package com.nicotinaestudio.forestamovil.ui;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicotinaestudio.forestamovil.R;
import com.nicotinaestudio.forestamovil.Utils;
import com.nicotinaestudio.forestamovil.adapters.TabsCollectionPagerAdapter;
import com.nicotinaestudio.forestamovil.context.AuthContext;


/**
 * Vista principal de la aplicación para Titulares y CAT
 * @author Ramiro Cázares.
 *
 */
public class HomeActivity extends BaseActivity {

	private AuthContext authContext;
	
	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	private ViewPager mViewPager;	
	private TabsCollectionPagerAdapter mAdapterTabs;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
                        
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
		mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);		
		mAdapterTabs = new TabsCollectionPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
				
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
		
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
				// NA	
				
			}

			@Override
			public void onTabSelected(Tab arg0, FragmentTransaction arg1) {				
				mViewPager.setCurrentItem(arg0.getPosition());
			}

			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
				// NA				
			}
	        
	    };
	    
	    
        mViewPager.setAdapter(mAdapterTabs);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });
        
	    actionBar.addTab(actionBar.newTab().setText("Transportes").setTabListener(tabListener));
	    actionBar.addTab(actionBar.newTab().setText("Transportistas").setTabListener(tabListener));
			    
	    Utils.Log("onCreate Activity");
		
    }
    
    
    
    
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				TransportistasFragment f = (TransportistasFragment) mAdapterTabs
						.getItem(1);
			}
		}
	}
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	
    	mAdapterTabs = null;
    	mViewPager = null;
    	mDrawer = null;
    	
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
