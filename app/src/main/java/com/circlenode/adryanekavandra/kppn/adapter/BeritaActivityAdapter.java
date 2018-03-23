package com.circlenode.adryanekavandra.kppn.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.activities.DetailBeritaActivity;
import com.circlenode.adryanekavandra.kppn.config.ServerConfig;
import com.circlenode.adryanekavandra.kppn.models.Berita;
import com.circlenode.adryanekavandra.kppn.models.DetailBerita;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Adryan Eka Vandra on 3/23/2018.
 */

public class BeritaActivityAdapter extends RecyclerView.Adapter<BeritaActivityAdapter.MyViewHolder> {

    private String TAG = this.getClass().getSimpleName();
    private Context context;
    private List<Berita> beritaList;
    public BeritaActivityAdapter(List<Berita>beritaList,Context context){

        this.context = context;
        this.beritaList = beritaList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearCardView;
        ImageView gambarBerita;
        TextView judulBerita,tanggal;
        public View view;
        Berita currentItem;
        public MyViewHolder(View itemView) {
            super(itemView);
            linearCardView = (LinearLayout) itemView.findViewById(R.id.linear_card);
            gambarBerita = (ImageView) itemView.findViewById(R.id.gambar_berita);
            judulBerita = (TextView) itemView.findViewById(R.id.judul_berita);
            tanggal = (TextView) itemView.findViewById(R.id.tanggal_berita);
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DetailBeritaActivity.class);
                    i.putExtra("idBerita",currentItem.getArID());
                    Log.i(TAG,"Send Intent with idBerita = "+currentItem.getArID());
                    context.startActivity(i);
                }
            });

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_berita,parent,false);
        Log.i(TAG,"Berhasil Meload Card Berita.");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Berita berita = beritaList.get(position);
        holder.currentItem = beritaList.get(position);
        //load gambar
        Picasso.get().load(ServerConfig.IMAGE_FOLDER+berita.getArImage()).into(holder.gambarBerita);

        //load judul
        holder.judulBerita.setText(berita.getArJudul());
        //load tanggal
        holder.tanggal.setText(berita.getArTanggal());
    }

    @Override
    public int getItemCount() {
        return beritaList.size();
    }


}
