package com.nicotinaestudio.forestamovil.dal;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.nicotinaestudio.forestamovil.context.DbContext;
import com.nicotinaestudio.forestamovil.model.Transporte;
import com.nicotinaestudio.forestamovil.model.Transportista;

/**
 * Administra los transportistas sobre la bd local permitiendo su actualización.
 */
public class TransportistaDao {

	private DbContext db;
	private Context ctx;

	public TransportistaDao(final Context ctx) {
		this.ctx = ctx;
		db = DbContext.getInstance(this.ctx);
	}
	
	
	public Transportista obtenerPorUUID(String uuid) {

		Transportista t = null;

		Cursor c = db.getReadableDatabase().rawQuery(
				"SELECT * From transportistas WHERE uuid = ?", new String[]{ uuid });

		try {
			if (c.moveToFirst()) {
				t = new Transportista();
				t.id = c.getInt(0);
				t.uuid = c.getString(1);
				t.nombre = c.getString(2);
				t.apellidoPaterno = c.getString(3);
				t.apellidoMaterno = c.getString(4);
				t.licencia = c.getString(5);
				t.estado = c.getString(6);
				t.municipio = c.getString(7);
				t.cp = c.getString(8);
				t.calle = c.getString(9);
				t.numero = c.getString(10);
				t.colonia = c.getString(11);
				t.fotografia = c.getString(12);					
				t.fechaRegistro = c.getString(13);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return t;

	}
	

	/**
	 * Obtiene todos los transportistas.
	 */
	public ArrayList<Transportista> obtenerTodos() {

		ArrayList<Transportista> transportistas = null;

		Cursor c = db.getReadableDatabase().rawQuery(
				"SELECT * From transportistas", null);

		try {
			if (c.moveToFirst()) {
				transportistas = new ArrayList<Transportista>();

				do {
					
					Transportista t = new Transportista();
					
					t.id = c.getInt(0);
					t.uuid = c.getString(1);
					t.nombre = c.getString(2);
					t.apellidoPaterno = c.getString(3);
					t.apellidoMaterno = c.getString(4);
					t.licencia = c.getString(5);
					t.estado = c.getString(6);
					t.municipio = c.getString(7);
					t.cp = c.getString(8);
					t.calle = c.getString(9);
					t.numero = c.getString(10);
					t.colonia = c.getString(11);
					t.fotografia = c.getString(12);					
					t.fechaRegistro = c.getString(13);
					
					transportistas.add(t);

				} while (c.moveToNext());
			}
		} catch (Exception e) {

		} finally {
			c.close();
			c = null;
		}

		return transportistas;
	}

	/**
	 * Registra un nuevo transportista en la BD.
	 * 
	 * @param t
	 * @return true si es guardado correctamente de lo contrario false.
	 */
	public long guardar(Transportista t) {
		
		ContentValues contentValues = new ContentValues();
		
		contentValues.put("uuid", t.uuid);
		contentValues.put("nom", t.nombre);
		contentValues.put("ap_pa", t.apellidoPaterno);
		contentValues.put("ap_ma", t.apellidoMaterno);
		contentValues.put("lic", t.licencia);
		contentValues.put("edo", t.estado);
		contentValues.put("mpio", t.municipio);
		contentValues.put("cp", t.cp);
		contentValues.put("calle", t.calle);
		contentValues.put("num", t.numero);
		contentValues.put("col", t.colonia);
		contentValues.put("foto", t.fotografia);
		contentValues.put("f_registro", new Date().toString());

		final long rowId = db.getWritableDatabase().insert("transportistas",
				"nombre",
				contentValues);
		
		
		return rowId;
	}

	/**
	 * Libera los recursos utilizados por la clase.
	 */
	public void close() {
		db = null;
		ctx = null;
	}

}
