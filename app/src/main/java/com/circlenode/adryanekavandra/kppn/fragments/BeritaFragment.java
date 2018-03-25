package com.circlenode.adryanekavandra.kppn.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Adryan Eka Vandra on 3/23/2018.
 */

public class BeritaFragment extends Fragment {

    ProgressDialog loading;
    RecyclerView rv;
    SwipeRefreshLayout swipeRefreshLayout;
    private  static final String TAG = BeritaFragment.class.getSimpleName();
    ApiInterface apiService;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_berita,container,false);
        getActivity().setTitle("KPPN - Berita");
        rv = (RecyclerView) view.findViewById(R.id.fragment_recycler_view_berita);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_swipe_refresh_berita);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        apiService = ApiClient.getClient().create(ApiInterface.class);
        loading = ProgressDialog.show(getContext(),null,"Sedang mendapatkan berita",true,false);
        retrieveBerita();

        Log.i(TAG,"Berhasil memuat Berita Fragment");
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveBerita();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;
    }

    private void retrieveBerita() {

        apiService.getAllBerita().enqueue(new Callback<ResponseBerita>() {
            @Override
            public void onResponse(Call<ResponseBerita> call, Response<ResponseBerita> response) {
                if(response.isSuccessful()){
                    loading.dismiss();
                    Log.i(TAG,"Success Mengambil Berita");
                    List<Berita> beritaList = response.body().getData();

                    rv.setAdapter(new BeritaActivityAdapter(beritaList,getContext()));
                }
                else{
                    Toast.makeText(getContext(),"Silahkan cek koneksi anda.",Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBerita> call, Throwable t) {
                Log.e(TAG,"Gagal mengambil berita, "+t.getLocalizedMessage());
                Toast.makeText(getContext(),"Silahkan cek koneksi anda.",Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }
}
