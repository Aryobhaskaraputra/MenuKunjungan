package com.aryo.ikurma.menukunjungan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView namaNasabah, lokasiDikunjungi, orangDitemui, hasilKunjunganUsaha, hasilKunjunganAgunan,
            tanggalKunjungan, alamatUsaha, zipcode, city, country, alamatAgunan, hubunganNasabah;

    ImageView fotoUsaha, fotoAgunan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        namaNasabah = findViewById(R.id.field_nama_nasabah);
        lokasiDikunjungi = findViewById(R.id.field_lokasi_dikunjungi);
        orangDitemui = findViewById(R.id.field_orang_ditemui);
        hasilKunjunganUsaha = findViewById(R.id.field_hasil_kunjungan_usaha);
        hasilKunjunganAgunan = findViewById(R.id.field_hasil_kunjungan_agunan);
        tanggalKunjungan = findViewById(R.id.field_tanggal_kunjungan);
        alamatUsaha = findViewById(R.id.field_alamat_usaha);
        zipcode = findViewById(R.id.field_kodepos);
        city = findViewById(R.id.field_kota);
        country = findViewById(R.id.field_negara);
        alamatAgunan = findViewById(R.id.field_alamat_agunan_2);
        hubunganNasabah = findViewById(R.id.field_hubungan_nasabah);
        fotoUsaha = findViewById(R.id.foto_kunjungan_usaha);
        fotoAgunan = findViewById(R.id.foto_kunjungan_agunan);

        // Ambil nilai dari Intent
        Intent intent = getIntent();
        if (intent != null) {
            String nama = intent.getStringExtra("keynama");
            String lokasi = intent.getStringExtra("keylokasi");
            String tanggal = intent.getStringExtra("keytanggal");
            String alamat_usaha = intent.getStringExtra("keyalamatUsaha");
            String kodepos = intent.getStringExtra("keykodepos");
            String kota = intent.getStringExtra("keykota");
            String negara = intent.getStringExtra("keynegara");
            String orang_ditemui = intent.getStringExtra("keyorangDitemui");
            String hasil_kunjungan_usaha = intent.getStringExtra("keyhasilKunjunganUsaha");
            String alamat_agunan = intent.getStringExtra("keyalamatAgunan");
            String hasil_kunjungan_agunan = intent.getStringExtra("keyhasilKunjunganAgunan");
            String foto_usaha = intent.getStringExtra("keyfotoUsaha");
            String foto_agunan = intent.getStringExtra("keyfotoAgunan");
            String hubungan_nasabah = intent.getStringExtra("keyhubunganNasabah");

            // Set nilai ke TextViews
            namaNasabah.setText(nama);
            lokasiDikunjungi.setText(lokasi);
            tanggalKunjungan.setText(tanggal);
            alamatUsaha.setText(alamat_usaha);
            zipcode.setText(kodepos);
            city.setText(kota);
            country.setText(negara);
            alamatAgunan.setText(alamat_agunan);
            orangDitemui.setText(orang_ditemui);
            hasilKunjunganUsaha.setText(hasil_kunjungan_usaha);
            hasilKunjunganAgunan.setText(hasil_kunjungan_agunan);
            hubunganNasabah.setText(hubungan_nasabah);

            // Set gambar dari URI
            fotoUsaha.setImageURI(Uri.parse(foto_usaha));
            fotoAgunan.setImageURI(Uri.parse(foto_agunan));

        }



    }
}