package com.nicotinaestudio.forestamovil.adapters;

/**
 * Representa los items en las listas layouts de contenidos de la aplicaci�n ej.
 * Transportistas, Transportes. 
 * 
 * @author Ramiro C�zares
 *
 */
public interface ContentAdaptable {

	/**
	 * Obtiene el titulo para el contenido
	 * @return
	 */
	String getTitle();
	
	/**
	 * Obtiene la descripci�n o contenido.
	 * @return
	 */
	String getDesc();
	
	
}
