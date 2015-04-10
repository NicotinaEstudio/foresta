package com.nicotinaestudio.forestamovil.model;

import java.util.Date;

import com.nicotinaestudio.forestamovil.adapters.ContentAdaptable;

/**
 * Representación del modelo Transportista.
 * @author Ramiro Cázares
 *
 */
public class Transportista implements ContentAdaptable {

	public int id;
	public String uuid;
	public String nombre;
	public String apellidoPaterno;
	public String apellidoMaterno;
	public String licencia;
	public String estado;
	public String municipio;
	public String cp;
	public String calle;
	public String numero;
	public String colonia;
	public String fotografia;
	public String fechaRegistro;
	
	@Override
	public String getTitle() { 
		return nombre;
	}
	@Override
	public String getDesc() {
		return String.format("Licencia %s", licencia);	
	}
	
}
