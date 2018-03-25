package com.circlenode.adryanekavandra.kppn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.models.Berita;
import com.circlenode.adryanekavandra.kppn.models.Notif;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Adryan Eka Vandra on 3/25/2018.
 */

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifViewHolder> {


    private String TAG = this.getClass().getSimpleName();
    private Context context;
    private List<Notif> notifListGlobal;

    public NotifAdapter(List<Notif> notifListGlobal, Context context){
        this.context = context;
        this.notifListGlobal = notifListGlobal;
    }
    @Override
    public NotifAdapter.NotifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_notification,parent,false);
        Log.i(TAG,"Berhasil Meload Card Berita.");
        return new NotifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotifAdapter.NotifViewHolder holder, int position) {
        Notif notif = notifListGlobal.get(position);
        holder.judul.setText(notif.getNotifPengirim());
        holder.tanggal.setText(notif.getNotifStartEnd());
        holder.isi.setText(notif.getNotifPesan());
    }

    @Override
    public int getItemCount() {
        return notifListGlobal.size();
    }

    public class NotifViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayoutCard;
        TextView judul, tanggal, isi;

        public NotifViewHolder(View itemView) {
            super(itemView);

            linearLayoutCard = (LinearLayout) itemView.findViewById(R.id.linear_card_notif);
            judul = (TextView) itemView.findViewById(R.id.judul_notif);
            tanggal = (TextView) itemView.findViewById(R.id.tanggal_notif);
            isi = (TextView) itemView.findViewById(R.id.isi_notif);
        }
    }
}
