package com.circlenode.adryanekavandra.kppn.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.adapter.NotifAdapter;
import com.circlenode.adryanekavandra.kppn.adapter.NotifStakeAdapter;
import com.circlenode.adryanekavandra.kppn.models.Notif;
import com.circlenode.adryanekavandra.kppn.models.NotifStake;
import com.circlenode.adryanekavandra.kppn.rest.ApiClient;
import com.circlenode.adryanekavandra.kppn.rest.ApiInterface;
import com.circlenode.adryanekavandra.kppn.sqlite.DbHelper;
import com.circlenode.adryanekavandra.kppn.utils.SessionManager;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Adryan Eka Vandra on 3/25/2018.
 */

public class NotifStakeFragment extends Fragment {

    ProgressDialog loading;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private  static final String TAG = NotifFragment.class.getSimpleName();
    ApiInterface apiService;
    DbHelper db;
    List<Notif> notifs ;
    SessionManager sessionManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_stake,container,false);
        getActivity().setTitle("KPPN - Notifikasi Stake");
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycler_view_notification_stake);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_swipe_refresh_notifikasi_stake);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        apiService = ApiClient.getClient().create(ApiInterface.class);
        loading = ProgressDialog.show(getContext(),null,"Sedang mendapatkan notifikasi",true,false);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        db = new DbHelper(getActivity());
        retrieveStakeNotification();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveStakeNotification();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }


    private void retrieveStakeNotification(){
        HashMap<String,String> user = sessionManager.getUserDetail();
        List<NotifStake> notifStakeList = db.getAllValidNotifStake(Integer.parseInt(user.get("stakeKode")));
        recyclerView.setAdapter(new NotifStakeAdapter(notifStakeList,getContext()));
        loading.dismiss();
    }
}
