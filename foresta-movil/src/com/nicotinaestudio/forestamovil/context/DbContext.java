package com.nicotinaestudio.forestamovil.context;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase responsable de generar la bd local y mantener la
 * actualización de versiones.
 * @author racanix
 *
 */
public class DbContext extends SQLiteOpenHelper{

	private static final String DB_NAME = "forestamovil.db";
	private static final int SCHEMA_VERSION = 1;
	private static DbContext dbHelper = null;
	private Context ctx;
	
	private static final String CREATE_TABLE_TRANSPORTISTAS = "CREATE TABLE transportistas (id INTEGER PRIMARY KEY, uuid TEXT, nom TEXT, ap_pa TEXT, ap_ma TEXT, lic TEXT, edo TEXT, mpio TEXT, cp TEXT, calle TEXT, num TEXT, col TEXT, foto TEXT, f_registro TEXT)";
	private static final String CREATE_TABLE_TRANSPORTES = "CREATE TABLE transportes (id INTEGER PRIMARY KEY, uuid TEXT, propietario TEXT, tipo TEXT, marca TEXT, modelo TEXT, cap TEXT, matricula TEXT, foto TEXT, f_registro TEXT, m_transporte TEXT)";
	private static final String CREATE_TABLE_CAT = "CREATE TABLE cats (id INTEGER PRIMARY KEY, uuid TEXT, razsoc TEXT, edo TEXT, mpio TEXT, domi TEXT, lat TEXT, lon TEXT, resp TEXT, tipo TEXT, almac INTEGER, transf INTEGER, calmac TEXT, cinst TEXT, creal TEXT, f_registro TEXT)";
	private static final String CREATE_TABLE_REMISIONES = "CREATE TABLE remisiones (id INTEGER PRIMARY KEY, uuid TEXT, cat_id INTEGER, tista_id INTEGER, trans_id INTEGER, cant TEXT, f_registro TEXT, desc_mat TEXT, estatus TEXT)";
	
	public synchronized static DbContext getInstance(Context ctx){
		
		if(dbHelper==null)
			dbHelper = new DbContext(ctx.getApplicationContext());
		
		return dbHelper;
	}
	
	private DbContext(Context ctx){
		super(ctx, DB_NAME, null, SCHEMA_VERSION);
		this.ctx = ctx;
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		try{
			db.beginTransaction();
			db.execSQL(CREATE_TABLE_TRANSPORTISTAS);
			db.execSQL(CREATE_TABLE_TRANSPORTES);
			db.execSQL(CREATE_TABLE_CAT);
			db.execSQL(CREATE_TABLE_REMISIONES);			
			db.setTransactionSuccessful();
		}catch(Exception e){
			
		}finally{
			db.endTransaction();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// NA
	}

}
