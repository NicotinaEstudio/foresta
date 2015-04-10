package com.nicotinaestudio.forestamovil.dal;

import com.nicotinaestudio.forestamovil.R;
import com.nicotinaestudio.forestamovil.model.Usuario;

public class UsuariosDao {
	
	public static Usuario login(String username, String password){
		
		Usuario u = null;
		
		if(username.equals("ta") && password.equals("123456")){
			u = new Usuario();
			u.nombre = "Titular de Aprovechamiento";
			u.foto = R.drawable.user_1;
			u.uuid = "11111111-1111-1111-1111-111111111111";
			u.roll = "Titular de Aprovechamiento";
			
			
		}else if(username.equals("is") && password.equals("123456")){
			u = new Usuario();
			u.nombre = "Insperctor de seguridad";
			u.foto = R.drawable.user_1;
			u.uuid = "11111111-1111-1111-1111-111111111112";
			u.roll = "Inspector de Seguridad";
			
		}else if(username.equals("ca") && password.equals("123456")){
			u = new Usuario();
			u.nombre = "CAT";
			u.foto = R.drawable.user_1;
			u.uuid = "CAT";
			
		}
		
		return u;
		
	}
	
}
