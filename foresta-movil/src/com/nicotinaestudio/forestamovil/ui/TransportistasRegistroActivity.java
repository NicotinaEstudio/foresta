package com.nicotinaestudio.forestamovil.ui;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.nicotinaestudio.forestamovil.R;
import com.nicotinaestudio.forestamovil.Utils;
import com.nicotinaestudio.forestamovil.dal.TransportistaDao;
import com.nicotinaestudio.forestamovil.http.HttpRequest;
import com.nicotinaestudio.forestamovil.http.HttpRequest.HttpCallback;
import com.nicotinaestudio.forestamovil.http.HttpRequest.HttpStatus;
import com.nicotinaestudio.forestamovil.model.Transportista;
import com.squareup.picasso.Picasso;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Permite el registro de nuevos transportistas. Al ser registrandos estos
 * son sincronizados con el servicio de foresta.
 * 
 * @author Ramiro Cázares
 *
 */
public class TransportistasRegistroActivity extends BaseActivity {

	private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 2;
	
	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;

	private EditText etNombre;
	private EditText etApPa;
	private EditText etApMa;
	private EditText etLicencia;
	private EditText etEstado;
	private EditText etMpio;
	private EditText etCp;
	private EditText etCalle;
	private EditText etColonia;
	private EditText etNumero;
	
	
	private Bitmap mBitmap;
	private File file;
	private String imagenEvidencia = ""; // Almacenara la url de la imagen una
											// vez,
	// subida a S3.
	private ImageView imgFoto;
	ProgressBar progress;
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transportista_registro);

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
	
	private void init(){
		
		imgFoto = (ImageView) findViewById(R.id.img_ic_usuario);
		
		progress = (ProgressBar) findViewById(R.id.progressBar1);
		
		etNombre = (EditText) findViewById(R.id.et_nombre);
		etApPa = (EditText) findViewById(R.id.et_paterno);
		etApMa = (EditText) findViewById(R.id.et_materno);
		etLicencia = (EditText) findViewById(R.id.et_licencia);
		etEstado = (EditText) findViewById(R.id.et_estado);
		etMpio = (EditText) findViewById(R.id.et_municipio);
		etCp = (EditText) findViewById(R.id.et_cp);
		etCalle = (EditText) findViewById(R.id.et_calle);
		etNumero = (EditText) findViewById(R.id.et_numero);
		etColonia = (EditText) findViewById(R.id.et_colonia);
		
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
		
		final TransportistaDao transportistaDao = new TransportistaDao(
				getApplicationContext());
		
		final Transportista t = new Transportista();
		
		t.uuid = UUID.randomUUID().toString();
		t.nombre = etNombre.getText().toString();
		t.apellidoPaterno = etApPa.getText().toString();
		t.apellidoMaterno = etApMa.getText().toString();
		t.licencia = etLicencia.getText().toString();
		t.calle = etCalle.getText().toString();
		t.colonia = etColonia.getText().toString();
		t.numero = etNumero.getText().toString();
		t.cp = etCp.getText().toString();
		t.municipio = etMpio.getText().toString();
		t.estado = etEstado.getText().toString();
		t.fotografia = "face.jpg";
				
		if(transportistaDao.guardar(t)>0){
			
			if(Utils.isNetworkAvailable(getApplicationContext())){
				
				HttpRequest http = new HttpRequest();
				http.enviarTransportista(getApplicationContext(), t, new HttpCallback() {
					
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
								transportistaDao.close();
								TransportistasRegistroActivity.this.finish();
							}
						} else {
							Toast.makeText(
									getApplicationContext(),
									"Error de Red, No es posible sincronizar el registro ",
									Toast.LENGTH_SHORT).show();
							TransportistasRegistroActivity.this.finish();
						}

					}
				});
			}
			else{
				Toast.makeText(getApplicationContext(), "Los datos se sincronizarán cuando se conecte a internet.", Toast.LENGTH_SHORT).show();
				transportistaDao.close();		 
				setResult(RESULT_OK);
				finish();
			}
			
		}else{
			Toast.makeText(getApplicationContext(),
					"No es posible registrar el transportista",
					Toast.LENGTH_SHORT).show();
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
			TransportistasRegistroActivity.this.finish();
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
