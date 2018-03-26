package com.circlenode.adryanekavandra.kppn.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.fragments.NotifFragment;
import com.circlenode.adryanekavandra.kppn.models.NotifStake;
import com.circlenode.adryanekavandra.kppn.rest.ApiInterface;
import com.circlenode.adryanekavandra.kppn.sqlite.DbHelper;

import java.util.List;

/**
 * Created by Adryan Eka Vandra on 3/25/2018.
 */

public class NotifStakeAdapter extends RecyclerView.Adapter<NotifStakeAdapter.NotifStakeViewHolder> {
    List<NotifStake> notifStakeList;
    Context context;
    private String TAG = this.getClass().getSimpleName();

    @Override
    public NotifStakeAdapter.NotifStakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_notification,parent,false);
        Log.i(TAG,"Berhasil Meload Card NotifStake.");
        return new NotifStakeAdapter.NotifStakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotifStakeAdapter.NotifStakeViewHolder holder, int position) {
        NotifStake notifStake = notifStakeList.get(position);
        holder.judul.setText(notifStake.getPengirim());
        holder.tanggal.setText(notifStake.getTanggal());
        holder.isi.setText(notifStake.getPesan());
    }

    @Override
    public int getItemCount() {
        return notifStakeList.size();
    }

    public class NotifStakeViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayoutCard;
        TextView judul, tanggal, isi;


        public NotifStakeViewHolder(View itemView) {
            super(itemView);
            linearLayoutCard = (LinearLayout) itemView.findViewById(R.id.linear_card_notif);
            judul = (TextView) itemView.findViewById(R.id.judul_notif);
            tanggal = (TextView) itemView.findViewById(R.id.tanggal_notif);
            isi = (TextView) itemView.findViewById(R.id.isi_notif);
        }
    }

    public NotifStakeAdapter(List<NotifStake>notifStakeList, Context context){
        this.notifStakeList = notifStakeList;
        this.context = context;
    }
}
