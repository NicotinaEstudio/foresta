package com.nicotinaestudio.forestamovil.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nicotinaestudio.forestamovil.R;
import com.nicotinaestudio.forestamovil.Utils;
import com.nicotinaestudio.forestamovil.context.AuthContext;
import com.nicotinaestudio.forestamovil.dal.CATDao;
import com.nicotinaestudio.forestamovil.model.CAT;
import com.nicotinaestudio.forestamovil.model.Remision;
import com.nicotinaestudio.forestamovil.model.Transporte;
import com.nicotinaestudio.forestamovil.model.Transportista;

/**
 * Realiza las peticiones a los servicios para sincronizar con la 
 * BD.
 * 
 * @author Ramiro Cázares.
 *
 */
public class HttpRequest {

	/**
	 * Representa el estado final de la petición http
	 * Los posibles valores son: 
	 * OK la peticion se llevo acabo y retorno resultados.
	 * Error la petición se llevo a cabo pero no retorno resultados
	 * y esto puede resultar de los status 404, 500, etc. del protocolo HTTP. 
	 * @author Ramiro Cázares
	 * 
	 */
	public enum HttpStatus{
		OK,
		ERROR
	}
	
	/**
	 * Interfaz responsable de notificar cuando una petición Http
	 * es finalizada ejecutando onTerminate.
	 * 
	 * @author Ramiro Cázares
	 *
	 */
	public interface HttpCallback{
		
		/**
		 * Es disparado cuando la petición http es finalizada
		 * pasando como argumento su estado.
		 * @param status
		 */
		void onTerminate(HttpStatus status);
	}
	
	private HttpCallback mCallback;
	
	private static HttpCallback mDummyCallback = new HttpCallback() {
		
		@Override
		public void onTerminate(HttpStatus status) {
			
		}
	};
	
	
	/**
	 * Obtiene una lista de todos los CAT
	 */
	public void obtenerCAT(final Context ctx, final HttpCallback callback) {

		JsonArrayRequest request = new JsonArrayRequest(String.format("%s/cats", ctx.getString(R.string.server)),
				new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray data) {
						
						
						CATDao cDao = new CATDao(ctx);

						try {
							int j = data.length();

							for (int i = 0; i < j; i++) {
								
								JSONObject object = data.getJSONObject(i);
								
								CAT c = new CAT();
								c.uuid = object.getString("uuid");
								c.denominacionORazonSocial = object.getString("denominacionORazonSocial");
								c.estado = object.getString("estado");
								c.municipio = object.getString("municipio");
								c.domicilio = object.getString("domicilio");
								c.lat = object.getString("lat");
								c.lon = object.getString("lon");
								c.responsable = object.getString("responsable");
								c.almacena = object.getBoolean("almacena") ? 1 : 0;
								c.transforma = object.getBoolean("transforma") ? 1 : 0;
								c.capacidadAlmacenamiento = object.getInt("capacidadAlmacenamiento");
								c.capacidadTransformacionInstalada = object.getInt("capacidadTransformacionInstalada");
								c.capacidadTransformacionReal = object.getInt("capacidadTransformacionReal");
								c.tipo = "";//object.getString("denominacionORazonSocial");
								c.fechaRegistro = object.getString("creado");
								
								cDao.guardar(c);
							}
							
							mCallback = callback;
							mCallback.onTerminate(HttpStatus.OK);
							
						} catch (Exception e) {
							mCallback = callback;
							mCallback.onTerminate(HttpStatus.ERROR);
						} finally {
							cDao.close();							
						}
						
						mCallback = mDummyCallback;
						
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError err) {
						Utils.Log("On onErrorResponse");						
					}
				});

		RequestQueue queue = Volley.newRequestQueue(ctx);
		queue.add(request);

	}

	
	/**
	 * Realiza la autenticación de usuario.
	 * @param ctx
	 * @param email
	 * @param password
	 */
	public void login(final Context ctx, final String email,
			final String password) {

		JSONObject json = new JSONObject();

		try {
			json.put("correoElectronico", email);
			json.put("contrasena", password);

		} catch (Exception ex) {
			ex.printStackTrace();
			Utils.Log(ex.toString());
		}

		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				"http://foresta.herokuapp.com/usuarios/api/autenticacion",
				json, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject arg0) {

						try {
							Log.i("Info", arg0.getString("nombre"));
						} catch (JSONException e) {

							e.printStackTrace();
						}

					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError err) {
						Log.e("Error", err.getMessage());
					}
				});

		RequestQueue queue = Volley.newRequestQueue(ctx);
		queue.add(request);

	}


	/**
	 * Guarda el transportista en el servicio.
	 * @param ctx
	 * @param t
	 * @param callback
	 */
	public void enviarTransporte(final Context ctx, final Transporte t, final HttpCallback callback){
		JSONObject json = new JSONObject();
		JSONObject tJson = new JSONObject();
		
		try {
			json.put("id", t.id);
			json.put("uuid", t.uuid);
			json.put("medioDeTransporte", t.medio);
			json.put("propietario", t.propietario);
			json.put("tipo", t.tipo);
			json.put("marca", t.marca);
			json.put("modelo", t.modelo);
			json.put("capacidad", t.capacidad);
			json.put("placasOMatricula", t.matricula);
			json.put("fotografia", t.fotografia);
			json.put("fechaRegistro", t.fechaRegistro);
			json.put("esActivo", true);
			
			tJson.put("transportes", json);
			

		} catch (Exception ex) {
			ex.printStackTrace();
			Utils.Log(ex.toString());
		}

		mCallback = callback;
		
		JsonObjectRequest request = new JsonObjectRequest(Method.PUT,
				String.format("%s/usuarios/%s",ctx.getString(R.string.server), AuthContext.buildAuth().getUsuario().uuid),
				tJson, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject object) {

						try {
							
							if(object.getString("mensaje").equals("Actualizado"))							
								mCallback.onTerminate(HttpStatus.OK);
							else
								mCallback.onTerminate(HttpStatus.ERROR);
						} catch (Exception e) {
							mCallback.onTerminate(HttpStatus.ERROR);
							e.printStackTrace();
						}
						finally{
							mCallback = mDummyCallback;							
						}

					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError err) {
						Utils.Log(err.toString());
					}
				});

		RequestQueue queue = Volley.newRequestQueue(ctx);
		queue.add(request);
	}

	
	public void enviarTransportista(final Context ctx, final Transportista t, final HttpCallback callback){
		JSONObject json = new JSONObject();
		JSONObject tJson = new JSONObject();
		
		try {
			json.put("id", t.id);
			json.put("uuid", t.uuid);
			json.put("fotografia", t.fotografia);
			json.put("colonia", t.colonia);
			json.put("numero", t.numero);
			json.put("calle", t.calle);
			json.put("codigoPostal", t.cp);
			json.put("municipio", t.municipio);
			json.put("estado", t.estado);
			json.put("apellidoMaterno", t.apellidoMaterno);
			json.put("apellidoPaterno", t.apellidoPaterno);
			json.put("Nombre", t.nombre);
			json.put("fechaRegistro", t.fechaRegistro);
			json.put("esActivo", true);
			
			tJson.put("transportistas", json);

		} catch (Exception ex) {
			ex.printStackTrace();
			Utils.Log(ex.toString());
		}

		mCallback = callback;
		
		JsonObjectRequest request = new JsonObjectRequest(Method.PUT,
				String.format("%s/usuarios/%s",
						ctx.getString(R.string.server), AuthContext.buildAuth().getUsuario().uuid),
						tJson, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject object) {

						try {
							
							if(object.getString("mensaje").equals("Actualizado"))							
								mCallback.onTerminate(HttpStatus.OK);
							else
								mCallback.onTerminate(HttpStatus.ERROR);
						} catch (Exception e) {
							mCallback.onTerminate(HttpStatus.ERROR);
							e.printStackTrace();
						}
						finally{
							mCallback = mDummyCallback;							
						}

					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError err) {
						Utils.Log(err.toString());
					}
				});

		RequestQueue queue = Volley.newRequestQueue(ctx);
		queue.add(request);
	}
	
	
	public void enviarRemision(final Context ctx, final Transportista transportista, final Transporte transporte, Remision r, final HttpCallback callback){
		
		JSONObject jsonTransportista = new JSONObject();
		JSONObject jsonRemision = new JSONObject();
		JSONObject jsonTransporte = new JSONObject();
		JSONObject jsonMateria = new JSONObject();
		JSONObject jsonSaldos = new JSONObject();
		JSONObject jsonDestinatario = new JSONObject();
		JSONObject jsonRemitente = new JSONObject();
		
		try {
			jsonTransportista.put("id", transportista.id);
			jsonTransportista.put("uuid", transportista.uuid);
			jsonTransportista.put("fotografia", transportista.fotografia);
			jsonTransportista.put("colonia", transportista.colonia);
			jsonTransportista.put("numero", transportista.numero);
			jsonTransportista.put("calle", transportista.calle);
			jsonTransportista.put("codigoPostal", transportista.cp);
			jsonTransportista.put("municipio", transportista.municipio);
			jsonTransportista.put("estado", transportista.estado);
			jsonTransportista.put("apellidoMaterno", transportista.apellidoMaterno);
			jsonTransportista.put("apellidoPaterno", transportista.apellidoPaterno);
			jsonTransportista.put("Nombre", transportista.nombre);
			jsonTransportista.put("fechaRegistro", transportista.fechaRegistro);
			jsonTransportista.put("esActivo", true);
			
			jsonTransporte.put("id", transporte.id);
			jsonTransporte.put("uuid", transporte.uuid);
			jsonTransporte.put("medioDeTransporte", transporte.medio);
			jsonTransporte.put("propietario", transporte.propietario);
			jsonTransporte.put("tipo", transporte.tipo);
			jsonTransporte.put("marca", transporte.marca);
			jsonTransporte.put("modelo", transporte.modelo);
			jsonTransporte.put("capacidad", transporte.capacidad);
			jsonTransporte.put("placasOMatricula", transporte.matricula);
			jsonTransporte.put("fotografia", transporte.fotografia);
			jsonTransporte.put("fechaRegistro", transporte.fechaRegistro);
			jsonTransporte.put("esActivo", true);
			
			jsonMateria.put("numeroYOCantidad", r.cantidad);
			jsonMateria.put("descripcion", r.materia);
			jsonMateria.put("volumenYOPesoAmparado", 100000);
			jsonMateria.put("unidadDeMedida", "m3");
			
			jsonSaldos.put("observaciones", "");
			jsonSaldos.put("saldoDisponible", 5000);
			jsonSaldos.put("cantidadQueAmpara", 100000);
			
			jsonDestinatario.put("uuid", r.cat);
			jsonRemitente.put("uuid", AuthContext.buildAuth().getUsuario().uuid);
			
			
			jsonRemision.put("uuid", r.uuid);
			jsonRemision.put("estatus", "Enviada");
			jsonRemision.put("comentarios", "");
			jsonRemision.put("tipo", "Remisión");
			jsonRemision.put("folioProgresivo", 12343);
			jsonRemision.put("folioDeRemision", r.id);			
			jsonRemision.put("transportista", jsonTransportista);
			jsonRemision.put("transporte", jsonTransporte);
			jsonRemision.put("saldos", jsonSaldos);
			jsonRemision.put("materiaPrima", jsonMateria);
			jsonRemision.put("destinatario", jsonDestinatario);
			jsonRemision.put("remitente", jsonRemitente);			
			jsonRemision.put("fechaRegistro", r.fechaRegistro);
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			Utils.Log(ex.toString());
		}

		mCallback = callback;
		
		JsonObjectRequest request = new JsonObjectRequest(Method.POST,
				String.format("%s/remisiones",
						ctx.getString(R.string.server)),
						jsonRemision, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject object) {

						try {
							
							if(object.getString("mensaje").equals("Creado"))							
								mCallback.onTerminate(HttpStatus.OK);
							else
								mCallback.onTerminate(HttpStatus.ERROR);
						} catch (Exception e) {
							mCallback.onTerminate(HttpStatus.ERROR);
							e.printStackTrace();
						}
						finally{
							mCallback = mDummyCallback;							
						}

					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError err) {
						Utils.Log(err.toString());
					}
				});

		RequestQueue queue = Volley.newRequestQueue(ctx);
		queue.add(request);
	}

}
