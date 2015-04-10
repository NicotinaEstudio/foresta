package com.nicotinaestudio.forestamovil.adapters;

import java.util.ArrayList;

import com.nicotinaestudio.forestamovil.R;
import com.nicotinaestudio.forestamovil.Utils;
import com.nicotinaestudio.forestamovil.model.Transportista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adaptador base para las listas desplegables y colecciones.
 * @author Ramiro Cázares
 *
 */
public class RemisionAdapter extends ArrayAdapter<RemisionAdaptable>{

	private Context ctx;
	private ArrayList<RemisionAdaptable> remisiones;
	private final int layout;
	
	
	public RemisionAdapter(Context context, ArrayList<RemisionAdaptable> list) {		
		super(context, R.layout.item_remision, list);		
		ctx = context;
		remisiones = list;
		layout = R.layout.item_remision;
	}
	
	public RemisionAdapter(Context context, int resourceId, ArrayList<RemisionAdaptable> list) {		
		super(context, resourceId, list);
		
		ctx = context;
		layout = resourceId;
		remisiones = list;
	}
	
	
	@Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
 
        return getCustomeView(position, convertView, parent);
    }
	
	protected View getCustomeView(int position, View convertView,
			ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		final View rowView = inflater.inflate(layout, parent, false);

		TextView tvTitulo = (TextView) rowView
				.findViewById(R.id.item_tv_titulo);
		TextView tvDesc = (TextView) rowView.findViewById(R.id.item_tv_desc);
		TextView tvFecha = (TextView) rowView.findViewById(R.id.item_tv_fecha);

		tvTitulo.setText(remisiones.get(position).getTitle());
		tvDesc.setText(remisiones.get(position).getDesc());
		tvFecha.setText(remisiones.get(position).getDate());
		
		

		return rowView;
	}
}
