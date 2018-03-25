package com.circlenode.adryanekavandra.kppn.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.models.Stakeholder;
import com.circlenode.adryanekavandra.kppn.utils.SessionManager;

import java.util.HashMap;


/**
 * Created by Adryan Eka Vandra on 3/23/2018.
 */

public class ProfilFragment extends Fragment {


    private String TAG = this.getClass().getSimpleName();
    TextView idStake, namaStake, kodeStake, email;
    SessionManager sessionManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        idStake = (TextView) view.findViewById(R.id.placeholder_id_stake);
        namaStake = (TextView) view.findViewById(R.id.placeholder_nama_stake);
        kodeStake = (TextView) view.findViewById(R.id.placeholder_kode_stake);
        email = (TextView) view.findViewById(R.id.placeholder_email_stake);
        sessionManager = new SessionManager(getContext());

        Log.i(TAG,"Berhasil memuat Fragment Profil");
        getActivity().setTitle("KPPN - Profil");
        HashMap<String,String> user = sessionManager.getUserDetail();

        String mail = user.get("stakeEmail") == null ? "Tidak Ada": user.get("stakeEmail");
        idStake.setText(user.get("stakeID"));
        kodeStake.setText(user.get("stakeKode"));
        namaStake.setText(user.get("stakeName"));
        email.setText(mail);


        return view;
    }
}
