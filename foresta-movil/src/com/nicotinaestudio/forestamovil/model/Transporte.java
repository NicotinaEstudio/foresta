package com.nicotinaestudio.forestamovil.model;

import com.nicotinaestudio.forestamovil.adapters.ContentAdaptable;

public class Transporte implements ContentAdaptable {

	public int id;
	public String uuid;
	public String medio;
	public String propietario;
	public String tipo;
	public String marca;
	public String modelo;
	public String capacidad;
	public String matricula;
	public String fotografia;
	public String fechaRegistro;
	
	@Override
	public String getTitle() {
		return String.format("%s %s - %s", marca, modelo, matricula);
	}
	@Override
	public String getDesc() {
		return String.format("Capacidad %s ", capacidad);
	}
	
}
