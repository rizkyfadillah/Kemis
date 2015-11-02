package com.thousandsunny.kemis.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.thousandsunny.kemis.R;
import com.thousandsunny.kemis.WebServiceHelper;
import com.thousandsunny.kemis.model.Post;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class AddKeluargaMiskinActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TextView infoText;
    private String alamatDariGCL;
    private double latitude, longitude;
    private CardView getCLCardView;

    EditText noKk, nama, alamat, pendapatan, makan, baju, luasLantai, dagingSusu, tabungan, no_rt;

    private Button postButton;

    private ImageButton btnCapture1, btnCapture2;
    private ImageView imgView1, imgView2;

    Spinner listrikSpinner, airSpinner, bahanBakarSpinner, biayaBerobatSpinner, pendidikanKkSpinner, jenisLantaiSpinner,
            jenisDindingSpinner, toiletSpinner;

    int listrik, air, bahanBakar, biayaBerobat, pendidikanKk, jenisLantai, jenisDinding, toilet;

    private final static int REQUEST_CODE = 1;
    private final static int REQUEST_CODE_2 = 2;
    private final static int REQUEST_CODE_3 = 3;

    private final static int SELECT_FILE = 4;
    private final static int SELECT_FILE_2 = 5;
    private final static int SELECT_FILE_3 = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keluarga_miskin);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        postButton = (Button) findViewById(R.id.postButton);

        Bitmap bitmap1, bitmap2;

        imgView1 = (ImageView) findViewById(R.id.imageView);
        imgView2 = (ImageView) findViewById(R.id.imageView2);

        btnCapture1 = (ImageButton) findViewById(R.id.imageButton);
        btnCapture2 = (ImageButton) findViewById(R.id.imageButton2);

        infoText = (TextView) findViewById(R.id.info_text);
        getCLCardView = (CardView) findViewById(R.id.getCLCardView);

        if (savedInstanceState != null){
            System.out.println("masuk savedInstanceState != null");
            alamatDariGCL = savedInstanceState.getString("alamatDariGCL");
            if(alamatDariGCL != null)
                infoText.setText(alamatDariGCL);
            bitmap1 = savedInstanceState.getParcelable("bitmap1");
            bitmap2 = savedInstanceState.getParcelable("bitmap2");
            if (bitmap1 != null){
                imgView1.setVisibility(View.VISIBLE);
                imgView1.setImageBitmap(bitmap1);
            } else {
                imgView1.setVisibility(View.INVISIBLE);
            }
            if (bitmap2 != null) {
                imgView2.setVisibility(View.VISIBLE);
                imgView2.setImageBitmap(bitmap2);
            } else {
                imgView2.setVisibility(View.INVISIBLE);
            }
        } else {
            System.out.println("masuk savedInstanceState == null");
            imgView1.setVisibility(View.GONE);
            imgView2.setVisibility(View.GONE);
        }


        getCLCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddKeluargaMiskinActivity.this, GetCurrentLocationActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        btnCapture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("onClick btnCapture1");
                selectImage(REQUEST_CODE);
            }
        });

        btnCapture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("onClick btnCapture2");
                selectImage(REQUEST_CODE_2);
            }
        });

        handleSpinner();

        handleEditText();

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TypedFile typedFile1 = null, typedFile2 = null;

                if (imgView1.getDrawable() != null) {
                    typedFile1 = changeBitmapToFile(imgView1, 1);
                }

                if (imgView2.getDrawable() != null) {
                    typedFile2 = changeBitmapToFile(imgView2, 2);
                }

                ProgressDialog progressDialog = new ProgressDialog(AddKeluargaMiskinActivity.this);
                progressDialog.setTitle("Loading");
                progressDialog.setCancelable(true);
                progressDialog.show();

                postKeluarga(progressDialog, longitude, latitude, noKk.getText().toString(), nama.getText().toString(),
                        alamat.getText().toString(), Double.valueOf(luasLantai.getText().toString()),
                        jenisLantai, jenisDinding, toilet, listrik, air, bahanBakar, Integer.valueOf(dagingSusu.getText().toString()),
                                Integer.valueOf(baju.getText().toString()), Integer.valueOf(makan.getText().toString()), biayaBerobat,
                                Integer.valueOf(pendapatan.getText().toString()), pendidikanKk, Double.valueOf(tabungan.getText().toString()), typedFile1, typedFile2, Integer.valueOf(no_rt.getText().toString()));



            }
        });

        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        System.out.println("masuk onSaveInstance");
        if (alamatDariGCL != null)
            outState.putString("alamatDariGCL", alamatDariGCL);
        if(imgView1.getDrawable() != null)
            outState.putParcelable("bitmap1", ((BitmapDrawable) imgView1.getDrawable()).getBitmap());
        if(imgView2.getDrawable() != null)
            outState.putParcelable("bitmap2", ((BitmapDrawable) imgView2.getDrawable()).getBitmap());
        super.onSaveInstanceState(outState);
    }

    private TypedFile changeBitmapToFile(ImageView imgView, int i) {
        Bitmap bitmap = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
        File filesDir = getApplicationContext().getFilesDir();
        File file = new File(filesDir, "foto" + i + ".jpg");
        try {
            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85 /*ignored for PNG*/, fos);

            //fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }

        TypedFile typedFile = new TypedFile("multipart/form-data", file);
        return typedFile;
    }

    private void handleEditText(){
        noKk = (EditText) findViewById(R.id.noKkET);
        nama = (EditText) findViewById(R.id.namaET);
        alamat = (EditText) findViewById(R.id.alamatET);
        pendapatan = (EditText) findViewById(R.id.pendapatanET);
        makan = (EditText) findViewById(R.id.makanHarianET);
        baju = (EditText) findViewById(R.id.bajuET);
        luasLantai = (EditText) findViewById(R.id.luasLantaiET);
        dagingSusu = (EditText) findViewById(R.id.dagingSusuET);
        tabungan = (EditText) findViewById(R.id.tabunganET);
        no_rt = (EditText) findViewById(R.id.noRtET);
    }

    private void handleSpinner() {
        listrikSpinner = (Spinner) findViewById(R.id.listrikSpinner);
        airSpinner = (Spinner) findViewById(R.id.airSpinner);
        bahanBakarSpinner = (Spinner) findViewById(R.id.bahanBakarSpinner);
        biayaBerobatSpinner = (Spinner) findViewById(R.id.biayaBerobatSpinner);
        pendidikanKkSpinner = (Spinner) findViewById(R.id.pendidikanSpinner);
        jenisLantaiSpinner = (Spinner) findViewById(R.id.jenisLantaiSpinner);
        jenisDindingSpinner = (Spinner) findViewById(R.id.jenisDindingSpinner);
        toiletSpinner = (Spinner) findViewById(R.id.toiletSpinner);

        ArrayAdapter<CharSequence> listrikSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.listrik_spinner, android.R.layout.simple_spinner_item);
        listrikSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listrikSpinner.setAdapter(listrikSpinnerAdapter);

        ArrayAdapter<CharSequence> airSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.air_spinner, android.R.layout.simple_spinner_item);
        airSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        airSpinner.setAdapter(airSpinnerAdapter);

        ArrayAdapter<CharSequence> bahanBakarSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.bahan_masak_spinner, android.R.layout.simple_spinner_item);
        bahanBakarSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bahanBakarSpinner.setAdapter(bahanBakarSpinnerAdapter);

        ArrayAdapter<CharSequence> biayaBerobatSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.biaya_berobat_spinner, android.R.layout.simple_spinner_item);
        biayaBerobatSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        biayaBerobatSpinner.setAdapter(biayaBerobatSpinnerAdapter);

        ArrayAdapter<CharSequence> pendidikanKkSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.pendidikan_kk_spinner, android.R.layout.simple_spinner_item);
        pendidikanKkSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pendidikanKkSpinner.setAdapter(pendidikanKkSpinnerAdapter);

        ArrayAdapter<CharSequence> jenisLantaiSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.jenis_lantai_spinner, android.R.layout.simple_spinner_item);
        jenisLantaiSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jenisLantaiSpinner.setAdapter(jenisLantaiSpinnerAdapter);

        ArrayAdapter<CharSequence> jenisDindingSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.jenis_dinding_spinner, android.R.layout.simple_spinner_item);
        jenisDindingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jenisDindingSpinner.setAdapter(jenisDindingSpinnerAdapter);

        ArrayAdapter<CharSequence> toiletSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.toilet_spinner, android.R.layout.simple_spinner_item);
        toiletSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toiletSpinner.setAdapter(toiletSpinnerAdapter);

        listrikSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listrik = position;
                enableLaporButtonIfReady();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        airSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                air = position;
                enableLaporButtonIfReady();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bahanBakarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bahanBakar = position;
                enableLaporButtonIfReady();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        biayaBerobatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                biayaBerobat = position;
                enableLaporButtonIfReady();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pendidikanKkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pendidikanKk = position;
                enableLaporButtonIfReady();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        jenisLantaiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jenisLantai = position;
                enableLaporButtonIfReady();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        jenisDindingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jenisDinding = position;
                enableLaporButtonIfReady();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toiletSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toilet = position;
                enableLaporButtonIfReady();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {
        //super.onActivityResult(requestcode, resultcode, data);
        System.out.println("onActivityResult");
        if (resultcode == RESULT_OK) {
            System.out.println("result ok");
            if (requestcode == 10) {
                alamatDariGCL = data.getStringExtra("alamat");
                latitude = data.getDoubleExtra("latitude", 0);
                longitude = data.getDoubleExtra("longitude", 0);
                System.out.println(latitude);
                System.out.println(longitude);
                infoText.setText(alamatDariGCL);
            } else if (requestcode < 4) {
                System.out.println("requestcode < 4");
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                if (requestcode == REQUEST_CODE) {
                    System.out.println("requestcode == REQUEST_CODE");
                    imgView1.setVisibility(View.VISIBLE);
                    imgView2.setVisibility(View.VISIBLE);
                    imgView1.setImageBitmap(thumbnail);
                } else if (requestcode == REQUEST_CODE_2) {
                    System.out.println("requestcode == REQUEST_CODE_2");
                    imgView1.setVisibility(View.VISIBLE);
                    imgView2.setVisibility(View.VISIBLE);
                    imgView2.setImageBitmap(thumbnail);
                } else if (requestcode == REQUEST_CODE_3) {
                    System.out.println("requestcode == REQUEST_CODE_3");
                    imgView1.setVisibility(View.VISIBLE);
                    imgView2.setVisibility(View.VISIBLE);
                }

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (requestcode > 3) {
                System.out.println("requestcode > 3");
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);

                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);

                if (requestcode == SELECT_FILE) {
                    System.out.println("requestcode == SELECT_FILE");
                    imgView1.setVisibility(View.VISIBLE);
                    imgView2.setVisibility(View.VISIBLE);
                    imgView1.setImageBitmap(bm);
                } else if (requestcode == SELECT_FILE_2) {
                    System.out.println("requestcode == SELECT_FILE_2");
                    imgView1.setVisibility(View.VISIBLE);
                    imgView2.setVisibility(View.VISIBLE);
                    imgView2.setImageBitmap(bm);
                } else if (requestcode == SELECT_FILE_3) {
                    System.out.println("requestcode == SELECT_FILE_3");
                    imgView1.setVisibility(View.VISIBLE);
                    imgView2.setVisibility(View.VISIBLE);
                }
            }
            enableLaporButtonIfReady();
        }
    }

    private void selectImage(final int req_code) {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        System.out.println("selectImage");

        AlertDialog.Builder builder = new AlertDialog.Builder(AddKeluargaMiskinActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    System.out.println("Take Photo");
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, req_code);
                    }
                } else if (items[item].equals("Choose from Library")) {
                    System.out.println("choose from library");
                    final int select_code = req_code + 3;
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            select_code);
                } else if (items[item].equals("Cancel")) {
                    System.out.println("cancel");
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private void postKeluarga(final ProgressDialog progressDialog, double longitude, double latitude, String noKk, String namaKepala, String alamat, double luasLantai, int jenisLantai, int jenisDinding,
                              int toilet, int listrik, int air, int bahanBakar, int dagingSusu, int baju, int makan, int bayarObat, double pendapatan, int pendidikan, double tabungan, TypedFile fotoKeluarga,
                              TypedFile fotoKk, int no_rt) {

        WebServiceHelper.getInstance().getServices().postKeluarga(longitude, latitude, noKk, namaKepala, alamat, luasLantai, jenisLantai, jenisDinding, toilet, listrik, air, bahanBakar,
                dagingSusu, baju, makan, bayarObat, pendapatan, pendidikan, tabungan, fotoKeluarga, fotoKk, no_rt, new retrofit.Callback<Post>() {

                    @Override
                    public void success(Post post, Response response) {
                        System.out.println(response.getStatus());
                        System.out.println(response.getBody());
                        System.out.println(response.getUrl());

                        if (response.getStatus() == 200){
                            Toast.makeText(AddKeluargaMiskinActivity.this, "Post keluarga berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddKeluargaMiskinActivity.this, "Post keluarga gagal. Silahkan cek lagi detail laporan yang telah anda inputkan.", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(AddKeluargaMiskinActivity.this, "Post keluarga gagal. Silahkan coba beberapa saat lagi.", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Toast.makeText(AddKeluargaMiskinActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_keluarga_miskin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button_background, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button_background
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void enableLaporButtonIfReady() {
        System.out.println("masuk enableButtonIfReady");
        if (((imgView1.getDrawable() != null) || (imgView2.getDrawable() != null) || (imgView1.getDrawable() != null)) & (latitude != 0) & (latitude != 0) &
                (longitude != 0)){
            postButton.setEnabled(true);
            System.out.println("masuk sini");
        } else {
            postButton.setEnabled(false);
        }
    }

}
