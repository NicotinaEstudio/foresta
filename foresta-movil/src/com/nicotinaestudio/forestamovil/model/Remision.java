package com.nicotinaestudio.forestamovil.model;

import com.nicotinaestudio.forestamovil.adapters.RemisionAdaptable;

public class Remision implements RemisionAdaptable{

	public long id;	
	public String uuid;
	public String cat;
	public String transportista;
	public String transporte;
	public double cantidad;
	public String materia;
	public String fechaRegistro;
	public String estatus;
	
	// Solo lectura.
	public String nombreTransportista;
	public String nombreTransporte;
	public String nombreCAT;
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return String.format("%s", materia);
	}

	@Override
	public String getDesc() {
		return String.format("%s m3 de %s, %s", cantidad, materia, nombreCAT);
	}

	@Override
	public String getDate() {
		return fechaRegistro;
	}

	@Override
	public String getId() {
		return String.valueOf(id);
	}

}
