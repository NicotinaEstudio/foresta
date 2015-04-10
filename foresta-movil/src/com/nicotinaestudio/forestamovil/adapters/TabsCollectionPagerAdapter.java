package com.nicotinaestudio.forestamovil.adapters;

import com.nicotinaestudio.forestamovil.ui.TransportesFragment;
import com.nicotinaestudio.forestamovil.ui.TransportistasFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * 
 * @author rcazares
 *
 */
public class TabsCollectionPagerAdapter  extends FragmentStatePagerAdapter{

	public TabsCollectionPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
    public Fragment getItem(int index) {
        
		switch(index)
		{
		case 0:
			return new TransportesFragment();
		case 1:
			return new TransportistasFragment();
		}
		
		return null;
    }
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	
}
