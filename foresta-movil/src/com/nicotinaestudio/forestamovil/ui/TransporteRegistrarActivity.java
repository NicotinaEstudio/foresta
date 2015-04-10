package com.nicotinaestudio.forestamovil.ui;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.nicotinaestudio.forestamovil.R;
import com.nicotinaestudio.forestamovil.Utils;
import com.nicotinaestudio.forestamovil.dal.TransportesDao;
import com.nicotinaestudio.forestamovil.http.HttpRequest;
import com.nicotinaestudio.forestamovil.http.HttpRequest.HttpCallback;
import com.nicotinaestudio.forestamovil.http.HttpRequest.HttpStatus;
import com.nicotinaestudio.forestamovil.model.Transporte;
import com.squareup.picasso.Picasso;

public class TransporteRegistrarActivity extends BaseActivity {

	private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 2;

	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;

	private Bitmap mBitmap;
	private File file;
	private String imagenEvidencia = ""; // Almacenara la url de la imagen una
											// vez,
	// subida a S3.
	private ImageView imgFoto;
	private Spinner spMedio;
	private EditText etPropietario;
	private Spinner spTipo;
	private EditText etMarca;
	private EditText etModelo;
	private EditText etCapacidad;
	private EditText etMatricula;
	
	ProgressBar progress;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transportes_registro);

		final ActionBar actionBar = getActionBar();

		mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawer, /* DrawerLayout object */
		R.drawable.icon_menu_principal, /* nav drawer icon to replace 'Up' caret */
		R.string.app_name, /* "open drawer" description */
		R.string.app_name /* "close drawer" description */
		) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(getTitle());
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(getTitle());
			}

		};

		mDrawer.setDrawerListener(mDrawerToggle);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		init();
	}

	private void init() {

		spMedio = (Spinner) findViewById(R.id.sp_medio);
		etPropietario = (EditText) findViewById(R.id.et_propietario);
		spTipo = (Spinner) findViewById(R.id.sp_tipo);
		etMarca = (EditText) findViewById(R.id.et_marca);
		etModelo = (EditText) findViewById(R.id.et_modelo);
		etCapacidad = (EditText) findViewById(R.id.et_capacidad);
		etMatricula = (EditText) findViewById(R.id.et_matricula);
		imgFoto = (ImageView) findViewById(R.id.img_ic_transporte);
		
		progress = (ProgressBar) findViewById(R.id.progressBar1);
	}

	public void capturarFoto(View v) {
		
		UUID nombreImagen = UUID.randomUUID();

		imagenEvidencia = String.format("%s.jpg", nombreImagen.toString());

		file = new File(Environment.getExternalStorageDirectory(),
				imagenEvidencia);
		Uri uri = Uri.fromFile(file);

		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(MediaStore.EXTRA_OUTPUT, uri);

		startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

			if (resultCode == RESULT_OK) {

				if (data != null) {

					Toast.makeText(this, "GUARDADA EN: \n" + data.getData(),
							Toast.LENGTH_SHORT).show();

					try {

						Bundle bundle = data.getExtras();
						mBitmap = (Bitmap) bundle.get("data");
						imgFoto.setImageBitmap(mBitmap);

					} catch (Exception e) {
						Utils.Log(e.toString());
					}
				} else {
					
					Picasso.with(getApplicationContext()).load(file)
							.resize(100, 100).into(imgFoto);

				}

			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Ha cancelado la captura de imagen.",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this,
						"Algo salio mal al intentar  tomar la imagen.",
						Toast.LENGTH_SHORT).show();
			}

		}

	}
	
		
	public void btnGuardar_Click(View v){
		
		Transporte t = new Transporte();
		t.uuid = UUID.randomUUID().toString();
		t.propietario = etPropietario.getText().toString();
		t.capacidad = etCapacidad.getText().toString();
		t.medio = spMedio.getSelectedItem().toString();
		t.tipo = spTipo.getSelectedItem().toString();
		t.marca = etMarca.getText().toString();
		t.modelo = etModelo.getText().toString();
		t.matricula = etMatricula.getText().toString();
		t.fotografia = imagenEvidencia;		
		t.fechaRegistro = new Date().toString();
		
		final TransportesDao tdao = new TransportesDao(getApplicationContext());
		
		if(tdao.guardar(t) > 0){
							
			if (Utils.isNetworkAvailable(getApplicationContext())) {

				HttpRequest http = new HttpRequest();
				http.enviarTransporte(getApplicationContext(), t,
						new HttpCallback() {

							@Override
							public void onTerminate(HttpStatus status) {

								if (status == HttpStatus.OK) {

									// Si existe archivo se intenta subir al
									// servidor.
									if (file != null) {
										SubidaArchivoTask subida = new SubidaArchivoTask();
										subida.execute();
									} else {
										Toast.makeText(
												getApplicationContext(),
												"Datos sincronizados correctamente.",
												Toast.LENGTH_SHORT).show();
										setResult(RESULT_OK);
										tdao.close();
										TransporteRegistrarActivity.this.finish();
									}
								} else {
									Toast.makeText(
											getApplicationContext(),
											"Error de Red, No es posible sincronizar el registro ",
											Toast.LENGTH_SHORT).show();
									TransporteRegistrarActivity.this.finish();
								}

							}
						});
			} else {
				Toast.makeText(
						getApplicationContext(),
						"Los datos se sincronizarán cuando se conecte a internet.",
						Toast.LENGTH_SHORT).show();
				setResult(RESULT_OK);
				tdao.close();
				finish();
			}
		
		}			
		
	}
	
	private class SubidaArchivoTask extends AsyncTask<Void, Integer, Void>{

		@Override
		protected Void doInBackground(Void... p) {
			
			CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
				    getApplicationContext(), /* Current Activity Context */
				    Utils.s3IdentityPoolID, /* Identity Pool ID */
				    Regions.US_EAST_1 /* Region */
				);
			
			
			TransferManager transferManager = new TransferManager(credentialsProvider);
			transferManager.getAmazonS3Client().setRegion(Region.getRegion(Regions.US_EAST_1));
					
			Upload upload = transferManager.upload(Utils.s3BucketName, imagenEvidencia, file);
			
			int porcent = 0;
			
			while(!upload.isDone()){
				porcent = (int) upload.getProgress().getPercentTransferred();
				
				Utils.Log(String.valueOf(porcent));
				progress.setProgress(porcent);
				
			}
			
//			if(porcent >= 100)
//				Toast.makeText(getApplicationContext(), "Registro completado.", Toast.LENGTH_SHORT).show();
//			else
//				Toast.makeText(getApplicationContext(), "No es posible almacenar la imagén.", Toast.LENGTH_SHORT).show();
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void v) {
			TransporteRegistrarActivity.this.finish();
			Toast.makeText(getApplicationContext(), "Registro completado.", Toast.LENGTH_SHORT).show();
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
		    super.onProgressUpdate(values);

		}
		
	}
	
	
	
	
	

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		
		onBaseOptionsItemSelected(item.getItemId());
		
		return super.onOptionsItemSelected(item);
	}
}
