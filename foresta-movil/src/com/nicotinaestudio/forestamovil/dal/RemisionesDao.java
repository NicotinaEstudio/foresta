package com.nicotinaestudio.forestamovil.dal;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.nicotinaestudio.forestamovil.context.DbContext;
import com.nicotinaestudio.forestamovil.model.Remision;
import com.nicotinaestudio.forestamovil.model.Transporte;

public class RemisionesDao {

	private DbContext db;
	private Context ctx;
	
	public RemisionesDao(Context ctx){
		this.ctx = ctx;
		db = DbContext.getInstance(this.ctx);
	}
	
	public ArrayList<Remision> obtenerTodos() {

		ArrayList<Remision> remisiones = null;

		Cursor c = db.getReadableDatabase().rawQuery(
				"select remisiones.*, cats.razsoc from remisiones, cats where remisiones.cat_id = cats.uuid", null);

		try {
			if (c.moveToFirst()) {
				remisiones = new ArrayList<Remision>();

				do {
					
					Remision r = new Remision();
					
					r.uuid =  c.getString(c.getColumnIndex("uuid"));
					r.cat =  c.getString(c.getColumnIndex("cat_id"));
					r.transportista =   c.getString(c.getColumnIndex("tista_id"));
					r.transporte =  c.getString(c.getColumnIndex("trans_id"));
					r.cantidad = c.getDouble(c.getColumnIndex("cant"));
					r.fechaRegistro = c.getString(c.getColumnIndex("f_registro"));
					r.materia = c.getString(c.getColumnIndex("desc_mat"));
					r.estatus = c.getString(c.getColumnIndex("estatus"));
					r.nombreCAT = c.getString(c.getColumnIndex("razsoc"));
					

					
					
					remisiones.add(r);

				} while (c.moveToNext());
			}
		} catch (Exception e) {

		} finally {
			c.close();
			c = null;
		}

		return remisiones;
	}
	
	public long guardar(Remision r) {
		
		ContentValues contentValues = new ContentValues();
		long rowId = 0;

		try {
			contentValues.put("uuid", r.uuid);
			contentValues.put("cat_id", r.cat);
			contentValues.put("tista_id", r.transportista);
			contentValues.put("trans_id", r.transporte);
			contentValues.put("cant", r.cantidad);
			contentValues.put("f_registro", new Date().toString());
			contentValues.put("desc_mat", r.materia);
			contentValues.put("estatus", "ENVIADO");

			rowId = db.getWritableDatabase().insert("remisiones", "cat_id",
					contentValues);

		} catch (Exception e) {
			e.printStackTrace();			
		}

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
