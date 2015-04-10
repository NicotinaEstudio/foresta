package com.nicotinaestudio.forestamovil.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nicotinaestudio.forestamovil.R;
import com.nicotinaestudio.forestamovil.adapters.BaseItemAdapter;
import com.nicotinaestudio.forestamovil.dal.TransportistaDao;
import com.nicotinaestudio.forestamovil.model.Transportista;

/**
 * Despliega una lista de transportistas registrados.
 * @author Ramiro Cázares
 */
public class TransportistasFragment extends Fragment {

	private ArrayList transportistas;
	private ListView mList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_transportistas, container,
				false);

		mList = (ListView) v.findViewById(R.id.listView1);
		actualizar();
		
		return v;
	}
	
	public void actualizar(){
		TransportistaDao tDao = new TransportistaDao(getActivity()
				.getApplicationContext());

		transportistas = tDao.obtenerTodos();

		if(transportistas != null)
		{
			
			mList.setAdapter(new BaseItemAdapter(getActivity(),
					transportistas));
		}
		tDao.close();
	}

}
