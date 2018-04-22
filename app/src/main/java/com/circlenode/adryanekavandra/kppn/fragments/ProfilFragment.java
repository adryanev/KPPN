package com.circlenode.adryanekavandra.kppn.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.activities.EditProfileAcitvity;
import com.circlenode.adryanekavandra.kppn.activities.FormBendaharaActivity;
import com.circlenode.adryanekavandra.kppn.adapter.BendaharaAdapter;
import com.circlenode.adryanekavandra.kppn.models.Bendahara;
import com.circlenode.adryanekavandra.kppn.models.Stakeholder;
import com.circlenode.adryanekavandra.kppn.responses.ResponsePembendaharaan;
import com.circlenode.adryanekavandra.kppn.rest.ApiClient;
import com.circlenode.adryanekavandra.kppn.rest.ApiInterface;
import com.circlenode.adryanekavandra.kppn.utils.SessionManager;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Adryan Eka Vandra on 3/23/2018.
 */

public class ProfilFragment extends Fragment {


    private String TAG = this.getClass().getSimpleName();
    ApiInterface apiService;
    TextView idStake, namaStake, kodeStake, email,tvBendahara;
    SessionManager sessionManager;
    Button editProfile;
    RecyclerView recyclerView;
    List<Bendahara> bendaharaList;
    LinearLayout layout;
    FloatingActionButton floatingActionButton;
    HashMap<String,String> user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        floatingActionButton  = (FloatingActionButton) view.findViewById(R.id.fab_tambah_bendahara);
        idStake = (TextView) view.findViewById(R.id.placeholder_id_stake);
        namaStake = (TextView) view.findViewById(R.id.placeholder_nama_stake);
        kodeStake = (TextView) view.findViewById(R.id.placeholder_kode_stake);
        email = (TextView) view.findViewById(R.id.placeholder_email_stake);
        editProfile = (Button)view.findViewById(R.id.btn_edit_profile);
       tvBendahara = (TextView)view.findViewById(R.id.tvBendahara);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycler_view_bendahara);
        sessionManager = new SessionManager(getContext());
        apiService = ApiClient.getClient().create(ApiInterface.class);

        Log.i(TAG,"Berhasil memuat Fragment Profil");
        getActivity().setTitle("KPPN - Profil");
        layout = (LinearLayout) view.findViewById(R.id.llpembendaharaan);
        user = sessionManager.getUserDetail();
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        String mail = user.get("stakeEmail") == null ? "Tidak Ada": user.get("stakeEmail");
        idStake.setText(user.get("stakeID"));
        kodeStake.setText(user.get("stakeKode"));
        namaStake.setText(user.get("stakeName"));
        email.setText(mail);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), FormBendaharaActivity.class);
                startActivity(i);
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EditProfileAcitvity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        retrieveBendahara();

        return view;
    }

    private void retrieveBendahara() {
        apiService.getPembendaharaan(Integer.parseInt(user.get("stakeID"))).enqueue(new Callback<ResponsePembendaharaan>() {
            @Override
            public void onResponse(Call<ResponsePembendaharaan> call, Response<ResponsePembendaharaan> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: berhasil mendapat response");
                    if(response.body().getBendaharaList() !=null){
                        bendaharaList = response.body().getBendaharaList();
                        recyclerView.setAdapter(new BendaharaAdapter(getContext(),bendaharaList));
                    }
                    else{

                        recyclerView.setVisibility(RecyclerView.GONE);
                        tvBendahara.setText("Tidak ada Bendahara");
                    }


                }
            }

            @Override
            public void onFailure(Call<ResponsePembendaharaan> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
                Toast.makeText(getContext(), "Gagal mendapatkan bendahara", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
