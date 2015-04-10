package com.nicotinaestudio.forestamovil.dal;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.nicotinaestudio.forestamovil.context.DbContext;
import com.nicotinaestudio.forestamovil.model.CAT;
import com.nicotinaestudio.forestamovil.model.Transporte;

public class CATDao {

	private DbContext db;
	private Context ctx;
	
	
	public CATDao(final Context ctx){
		this.ctx = ctx;
		db = DbContext.getInstance(this.ctx);
	}
	
	
	public CAT obtenerPorUUID(String uuid) {

		CAT cat = null;

		Cursor c = db.getReadableDatabase().rawQuery(
				"SELECT * From cats WHERE uuid = ?", new String[]{ uuid });

		try {
			if (c.moveToFirst()) {
				cat = new CAT();
				
				cat.id = c.getInt(0);
				cat.uuid = c.getString(1);
				cat.denominacionORazonSocial = c.getString(2);
				cat.estado = c.getString(3);
				cat.municipio= c.getString(4);
				cat.domicilio= c.getString(5);
				cat.lat = c.getString(6);
				cat.lon = c.getString(7);
				cat.responsable = c.getString(8);
				cat.tipo = c.getString(9);
				cat.almacena = c.getInt(10);
				cat.transforma = c.getInt(11);
				cat.fechaRegistro = c.getString(12);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cat;

	}
	
	public long guardar(CAT c) {

		ContentValues contentValues = new ContentValues();

		contentValues.put("uuid", c.uuid);
		contentValues.put("razsoc", c.denominacionORazonSocial);
		contentValues.put("edo", c.estado);
		contentValues.put("mpio", c.municipio);
		contentValues.put("domi", c.domicilio);
		contentValues.put("lat", c.lat);
		contentValues.put("lon", c.lon);
		contentValues.put("resp", c.responsable);
		contentValues.put("tipo", c.tipo);
		contentValues.put("almac", c.almacena);
		contentValues.put("transf", c.transforma);
		contentValues.put("calmac", c.capacidadAlmacenamiento);
		contentValues.put("cinst", c.capacidadTransformacionInstalada);
		contentValues.put("creal", c.capacidadTransformacionReal);
		contentValues.put("f_registro", c.fechaRegistro);

		final long affect = db.getWritableDatabase().insert("cats",
				"razsoc", contentValues);

		return affect;
	}
	
	
	public ArrayList<CAT> obtenerTodos() {

		ArrayList<CAT> data = null;

		Cursor c = db.getReadableDatabase().rawQuery(
				"SELECT * From cats", null);

		try {
			if (c.moveToFirst()) {
				data = new ArrayList<CAT>();

				do {
					
					CAT cat = new CAT();
					
					cat.id = c.getInt(0);
					cat.uuid = c.getString(1);
					cat.denominacionORazonSocial = c.getString(2);
					cat.estado = c.getString(3);
					cat.municipio= c.getString(4);
					cat.domicilio= c.getString(5);
					cat.lat = c.getString(6);
					cat.lon = c.getString(7);
					cat.responsable = c.getString(8);
					cat.tipo = c.getString(9);
					cat.almacena = c.getInt(10);
					cat.transforma = c.getInt(11);
					cat.fechaRegistro = c.getString(12);
					
					data.add(cat);

				} while (c.moveToNext());
			}
		} catch (Exception e) {

		} finally {
			c.close();
			c = null;
		}

		return data;
	}
	
	public int totalRows(){
		Cursor c = db.getReadableDatabase().rawQuery(
				"SELECT count(id) From cats", null);
		int count = 0;
		try {
			if (c.moveToFirst()) {
				
				do {
					
					
					count = c.getInt(0);
					

				} while (c.moveToNext());
			}
		} catch (Exception e) {

		} finally {
			c.close();
			c = null;
		}
		
		return count;

	}
	
	/**
	 * Libera los recursos utilizados por la clase.
	 */
	public void close() {
		db = null;
		ctx = null;
	}
	
	
	
}
