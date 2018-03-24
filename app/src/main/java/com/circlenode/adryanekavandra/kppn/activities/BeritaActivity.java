package com.circlenode.adryanekavandra.kppn.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.adapter.BeritaActivityAdapter;
import com.circlenode.adryanekavandra.kppn.models.Berita;
import com.circlenode.adryanekavandra.kppn.responses.ResponseBerita;
import com.circlenode.adryanekavandra.kppn.rest.ApiClient;
import com.circlenode.adryanekavandra.kppn.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeritaActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    ProgressDialog loading;
    ApiInterface apiService;
    Context context;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        context = this;
        loading = ProgressDialog.show(context,null,"Sedang mendapatkan berita",true,false);
        recyclerView = (RecyclerView) findViewById(R.id.activity_recycler_view_berita);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_swipe_refresh_berita);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getAllBerita();
        setTitle(R.string.app_name);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // loading = ProgressDialog.show(context,null,"Sedang mendapatkan berita",true,false);
                getAllBerita();
                swipeRefreshLayout.setRefreshing(false);
            }
        });



    }

    private void getAllBerita() {

        apiService.getAllBerita().enqueue(new Callback<ResponseBerita>() {
            @Override
            public void onResponse(Call<ResponseBerita> call, Response<ResponseBerita> response) {
                if(response.isSuccessful()){
                    loading.dismiss();
                    Log.i(TAG,"Success Mengambil Berita");
                    List<Berita> beritaList = response.body().getData();

                    recyclerView.setAdapter(new BeritaActivityAdapter(beritaList,context));
                }
                else{
                    Toast.makeText(context,"Silahkan cek koneksi anda.",Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBerita> call, Throwable t) {
                Log.e(TAG,"Gagal mengambil berita, "+t.getLocalizedMessage());
                Toast.makeText(context,"Silahkan cek koneksi anda.",Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.berita, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Integer id = item.getItemId();
        if(id==R.id.actionLogin){
            Log.i(TAG,"Menu login dipilih");
            Intent i = new Intent(BeritaActivity.this,LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
