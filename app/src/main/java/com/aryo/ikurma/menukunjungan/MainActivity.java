package com.aryo.ikurma.menukunjungan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button submitnBtn;

    EditText namaNasabah, lokasiDikunjungi, orangDitemui, hasilKunjunganUsaha, hasilKunjunganAgunan;

    //location
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView tanggalKunjungan, alamatUsaha, zipcode, city, country, alamatAgunan;
    Button locationBtn, locationBtnAgunan;
    private final static int REQUEST_CODE = 100;

    //upload Photo
    String path;
    Uri uri;
    private ImageView fotoUsaha, fotoAgunan;
    private Button btnFotoUsaha, btnFotoAgunan;
    private static final int REQUEST_CODE_IMAGE_PICKER = 101;

    //datePicker
    DatePickerDialog.OnDateSetListener setListener;

    //dropdown menu
    Spinner spinner,  hubunganNasabah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitnBtn = findViewById(R.id.submit_btn);
        namaNasabah = findViewById(R.id.field_nama_nasabah);
        lokasiDikunjungi = findViewById(R.id.field_lokasi_dikunjungi);
        orangDitemui = findViewById(R.id.field_orang_ditemui);
        hasilKunjunganUsaha = findViewById(R.id.field_hasil_kunjungan_usaha);
        hasilKunjunganAgunan = findViewById(R.id.field_hasil_kunjungan_agunan);
        hubunganNasabah = findViewById(R.id.field_hubungan_nasabah);

        submitnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String nama = namaNasabah.getText().toString();
            String lokasi = lokasiDikunjungi.getText().toString();
            String tanggal = tanggalKunjungan.getText().toString();
            String alamat_usaha = alamatUsaha.getText().toString();
            String kodepos = zipcode.getText().toString();
            String kota = city.getText().toString();
            String negara = country.getText().toString();
            String orang_ditemui = orangDitemui.getText().toString();
            String hubungan_nasabah = spinner.getSelectedItem().toString();
            String hasil_kunjungan_usaha = hasilKunjunganUsaha.getText().toString();
            String alamat_agunan = alamatAgunan.getText().toString();
            String hasil_kunjungan_agunan = hasilKunjunganAgunan.getText().toString();
            String foto_usaha = (uri !=null) ? uri.toString() : "";
            String foto_agunan = (uri !=null) ? uri.toString() : "";

            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("keynama", nama);
                intent.putExtra("keylokasi", lokasi);
                intent.putExtra("keytanggal", tanggal);
                intent.putExtra("keyalamatUsaha", alamat_usaha);
                intent.putExtra("keykodepos", kodepos);
                intent.putExtra("keykota", kota);
                intent.putExtra("keynegara", negara);
                intent.putExtra("keyorangDitemui", orang_ditemui);
                intent.putExtra("keyhubunganNasabah", hubungan_nasabah);
                intent.putExtra("keyhasilKunjunganUsaha", hasil_kunjungan_usaha);
                intent.putExtra("keyalamatAgunan", alamat_agunan);
                intent.putExtra("keyhasilKunjunganAgunan", hasil_kunjungan_agunan);
                intent.putExtra("keyfotoUsaha", foto_usaha);
                intent.putExtra("keyfotoAgunan", foto_agunan);
                startActivity(intent);
            }
        });

        //date picker
        tanggalKunjungan = findViewById(R.id.field_tanggal_kunjungan);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tanggalKunjungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        tanggalKunjungan.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //location
        alamatUsaha = findViewById(R.id.field_alamat_usaha);
        zipcode = findViewById(R.id.field_kodepos);
        city = findViewById(R.id.field_kota);
        country = findViewById(R.id.field_negara);
        alamatAgunan = findViewById(R.id.field_alamat_agunan_2);
        locationBtn = findViewById(R.id.btn_tag_lokasi);
        locationBtnAgunan = findViewById(R.id.btn_tag_lokasi_agunan);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getLastLocation();
            }
        });

        locationBtnAgunan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getLastLocationAgunan();
            }
        });

        //Upload Photo
                btnFotoUsaha = findViewById(R.id.btn_foto_usaha);
                fotoUsaha = findViewById(R.id.foto_kunjungan_usaha);
                btnFotoAgunan = findViewById(R.id.btn_foto_agunan);
                fotoAgunan = findViewById(R.id.foto_kunjungan_agunan);

        btnFotoUsaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryUsaha();
            }
        });

        btnFotoAgunan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryAgunan();
            }
        });

        //dropdown Menu
        spinner = findViewById(R.id.field_hubungan_nasabah);
        String[] value = {"Sendiri", "Pasangan", "Keluarga", "Karyawan"};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(value));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.style_spinner, arrayList);
        spinner.setAdapter(arrayAdapter);
    }

    private void openGalleryUsaha() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        openGalleryUsaha.launch(intent);
    }
    private void openGalleryAgunan() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        openGalleryAgunan.launch(intent);
    }

    ActivityResultLauncher<Intent> openGalleryUsaha = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Uri uriImage = result.getData().getData();
            fotoUsaha.setImageURI(uriImage);
        }
    });
    ActivityResultLauncher<Intent> openGalleryAgunan = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Uri uriImage = result.getData().getData();
            fotoAgunan.setImageURI(uriImage);
        }
    });




    //location
    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            alamatUsaha.setText(addresses.get(0).getAddressLine(0));
                            zipcode.setText(addresses.get(0).getPostalCode());
                            city.setText(addresses.get(0).getLocality());
                            country.setText(addresses.get(0).getCountryName());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            });
        } else {
            askPermission();
        }
    }

    private void getLastLocationAgunan() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            alamatAgunan.setText(addresses.get(0).getAddressLine(0));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            });
        } else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(MainActivity.this, "Please provide the required permission", Toast.LENGTH_SHORT).show();
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}