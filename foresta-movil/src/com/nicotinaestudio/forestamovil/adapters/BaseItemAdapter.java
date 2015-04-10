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
public class BaseItemAdapter extends ArrayAdapter<ContentAdaptable>{

	private Context ctx;
	private ArrayList<ContentAdaptable> transportistas;
	private final int layout;
	
	
	public BaseItemAdapter(Context context, ArrayList<ContentAdaptable> list) {		
		super(context, R.layout.item_list, list);		
		ctx = context;
		transportistas = list;
		layout = R.layout.item_list;
	}
	
	public BaseItemAdapter(Context context, int resourceId, ArrayList<ContentAdaptable> list) {		
		super(context, resourceId, list);
		
		ctx = context;
		layout = resourceId;
		transportistas = list;
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

		tvTitulo.setText(transportistas.get(position).getTitle());
		tvDesc.setText(transportistas.get(position).getDesc());

		return rowView;
	}
}
