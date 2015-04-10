package com.nicotinaestudio.forestamovil.adapters;

/**
 * Representa los items en las listas layouts de contenidos de la aplicación ej.
 * Transportistas, Transportes. 
 * 
 * @author Ramiro Cázares
 *
 */
public interface ContentAdaptable {

	/**
	 * Obtiene el titulo para el contenido
	 * @return
	 */
	String getTitle();
	
	/**
	 * Obtiene la descripción o contenido.
	 * @return
	 */
	String getDesc();
	
	
}
