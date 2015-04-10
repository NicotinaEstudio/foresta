
package com.nicotinaestudio.forestamovil.context;

import com.nicotinaestudio.forestamovil.model.Usuario;

/**
 * Clase responsable de retener las credenciales y exponerlas para
 * la aplicación.
 *
 */
public class AuthContext {

	public enum Roll{
		TITULAR,
		INSPECTOR,
		CAT
	}
	
	private String nombre;
	private Roll roll;
	
	
	private static AuthContext auth;
	
	
	private AuthContext(){};
	
	private Usuario usuario;
	
	
	public static synchronized AuthContext buildAuth(){
		
		if(auth == null){
			auth = new AuthContext();				
		}
		
		return auth;
	}

	public Roll getRoll() {
		return roll;
	}


	private void setRoll(Roll roll) {
		this.roll = roll;
	}


	public String getNombre() {
		return nombre;
	}


	private void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setUsuario(Usuario u){
		usuario = u;
	}
	
	public Usuario getUsuario(){
		return usuario;
	}
	
	
}
