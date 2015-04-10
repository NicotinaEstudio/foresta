package com.nicotinaestudio.forestamovil.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class SpinnerSimpleAdapter extends BaseItemAdapter{

	public SpinnerSimpleAdapter(Context context,
			final ArrayList<ContentAdaptable> list) {
		super(context, list);		
	}
	
	public SpinnerSimpleAdapter(Context context, int resourceId,
			final ArrayList<ContentAdaptable> list){
		super(context, resourceId, list);
	}

	public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
		return getCustomeView(position, cnvtView, prnt);
	}
		
		

}
