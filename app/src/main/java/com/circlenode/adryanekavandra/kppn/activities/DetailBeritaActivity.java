package com.circlenode.adryanekavandra.kppn.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.config.ServerConfig;
import com.circlenode.adryanekavandra.kppn.models.Berita;
import com.circlenode.adryanekavandra.kppn.models.DetailBerita;
import com.circlenode.adryanekavandra.kppn.responses.ResponseDetailBerita;
import com.circlenode.adryanekavandra.kppn.rest.ApiClient;
import com.circlenode.adryanekavandra.kppn.rest.ApiInterface;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBeritaActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    TextView judul, isi, tanggal;
    ImageView gambar;
    ProgressDialog loading;
    ApiInterface apiService;
    Context context;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        judul = (TextView) findViewById(R.id.detail_judul);
        isi = (TextView) findViewById(R.id.detail_isi);
        tanggal = (TextView) findViewById(R.id.detail_tanggal);
        gambar = (ImageView) findViewById(R.id.detail_gambar);
        context = this;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        loading = ProgressDialog.show(context,null,"Sedang mendapatkan berita",true,false);
        MobileAds.initialize(this,
                "ca-app-pub-4489313737581214~9623154793");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Intent i = getIntent();
        final Integer id = Integer.parseInt(i.getStringExtra("idBerita"));

        apiService.getDetailBerita(id).enqueue(new Callback<ResponseDetailBerita>() {
            @Override
            public void onResponse(Call<ResponseDetailBerita> call, Response<ResponseDetailBerita> response) {
                if(response.isSuccessful()){
                    Log.i(TAG,"Succes Mendapatkan Berita idBerita = "+id.toString());
                    loading.dismiss();
                    List<DetailBerita> detailList = response.body().getData();
                    DetailBerita detailBerita = detailList.get(0);
                    Picasso.get().load(ServerConfig.IMAGE_FOLDER+detailBerita.getArImage()).into(gambar);
                    judul.setText(detailBerita.getArJudul());
                    tanggal.setText(detailBerita.getArTanggal());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        isi.setText(Html.fromHtml(detailBerita.getArContent(),Html.FROM_HTML_MODE_LEGACY));
                    }else{
                       isi.setText(Html.fromHtml(detailBerita.getArContent()));
                    }


                }else{
                    Toast.makeText(context,"Silahkan cek koneksi anda.",Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailBerita> call, Throwable t) {
                Log.e(TAG,"Gagal mengambil berita, "+t.getLocalizedMessage());
                Toast.makeText(context,"Silahkan cek koneksi anda.",Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Berita");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
