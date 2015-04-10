package com.nicotinaestudio.forestamovil.dal;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.nicotinaestudio.forestamovil.context.DbContext;
import com.nicotinaestudio.forestamovil.model.Transporte;

public class TransportesDao {

	private DbContext db;
	private Context ctx;
	
	
	public TransportesDao(final Context ctx){
		this.ctx = ctx;
		db = DbContext.getInstance(this.ctx);
	}
	
	public ArrayList<Transporte> obtenerTodos() {

		ArrayList<Transporte> transportes = null;

		Cursor c = db.getReadableDatabase().rawQuery(
				"SELECT * From transportes", null);

		try {
			if (c.moveToFirst()) {
				transportes = new ArrayList<Transporte>();

				do {
					
					Transporte t = new Transporte();
					
					t.id = c.getInt(0);
					t.uuid = c.getString(1);
					t.propietario = c.getString(2);
					t.tipo = c.getString(3);
					t.marca = c.getString(4);
					t.modelo = c.getString(5);
					t.capacidad = c.getString(6);
					t.matricula = c.getString(7);
					t.fotografia = c.getString(8);		
					t.fechaRegistro = c.getString(9);
					t.medio = c.getString(10);
					
					transportes.add(t);

				} while (c.moveToNext());
			}
		} catch (Exception e) {

		} finally {
			c.close();
			c = null;
		}

		return transportes;
	}
	
	public Transporte obtenerPorUUID(String uuid) {

		Transporte t = null;

		Cursor c = db.getReadableDatabase().rawQuery(
				"SELECT * From transportes WHERE uuid = ?", new String[]{ uuid });

		try {
			if (c.moveToFirst()) {
				t = new Transporte();
				t.id = c.getInt(0);
				t.uuid = c.getString(1);
				t.propietario = c.getString(2);
				t.tipo = c.getString(3);
				t.marca = c.getString(4);
				t.modelo = c.getString(5);
				t.capacidad = c.getString(6);
				t.matricula = c.getString(7);
				t.fotografia = c.getString(8);
				t.fechaRegistro = c.getString(9);
				t.medio = c.getString(10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return t;

	}
	
	
	public long guardar(Transporte t) {

		ContentValues contentValues = new ContentValues();

		contentValues.put("uuid", t.uuid);
		contentValues.put("propietario", t.propietario);
		contentValues.put("tipo", t.tipo);
		contentValues.put("marca", t.marca);
		contentValues.put("modelo", t.modelo);
		contentValues.put("cap", t.capacidad);
		contentValues.put("matricula", t.matricula);
		contentValues.put("foto", t.fotografia);
		contentValues.put("f_registro", t.fechaRegistro);
		contentValues.put("m_transporte", t.medio);

		final long rowId = db.getWritableDatabase().insert("transportes",
				"propietario", contentValues);

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
