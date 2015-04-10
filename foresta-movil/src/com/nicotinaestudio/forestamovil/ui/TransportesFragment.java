package com.nicotinaestudio.forestamovil.ui;

import java.util.ArrayList;

import com.nicotinaestudio.forestamovil.R;
import com.nicotinaestudio.forestamovil.Utils;
import com.nicotinaestudio.forestamovil.adapters.ContentAdaptable;
import com.nicotinaestudio.forestamovil.adapters.BaseItemAdapter;
import com.nicotinaestudio.forestamovil.dal.TransportesDao;
import com.nicotinaestudio.forestamovil.model.Transporte;
import com.nicotinaestudio.forestamovil.model.Transportista;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TransportesFragment extends Fragment{

	private ArrayList transportes;
	private ListView mList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View v = inflater.inflate(R.layout.fragment_transportes, container,
				false);
		
		TransportesDao tDao = new TransportesDao(getActivity()
				.getApplicationContext());
		
		transportes = tDao.obtenerTodos();
		
		if(transportes != null){
			mList = (ListView) v.findViewById(R.id.lv_transportes);
			mList.setAdapter(new BaseItemAdapter(getActivity(),
					transportes)); 
		}
		
		return v;
	}
	
}
