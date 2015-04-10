package com.nicotinaestudio.forestamovil.model;

import com.nicotinaestudio.forestamovil.adapters.ContentAdaptable;

public class CAT implements ContentAdaptable{

	public int id;
	public String uuid;
	public String denominacionORazonSocial;
	public String estado;
	public String municipio;
	public String domicilio;
	public String lat;
	public String lon;
	public String responsable;
	public String tipo;
	public int almacena;
	public int transforma;
	public double capacidadAlmacenamiento;
	public double capacidadTransformacionInstalada;
	public double capacidadTransformacionReal;
	public String fechaRegistro;
	
	@Override
	public String getTitle() {
		return denominacionORazonSocial;
	}
	@Override
	public String getDesc() {
		return String.format("%s %s %s", municipio, estado, domicilio);
	}
}
