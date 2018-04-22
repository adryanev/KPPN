package com.circlenode.adryanekavandra.kppn.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.activities.FormBendaharaActivity;
import com.circlenode.adryanekavandra.kppn.activities.MainActivity;
import com.circlenode.adryanekavandra.kppn.models.Bendahara;
import com.circlenode.adryanekavandra.kppn.responses.ResponsePembendaharaan;
import com.circlenode.adryanekavandra.kppn.rest.ApiClient;
import com.circlenode.adryanekavandra.kppn.rest.ApiInterface;

import java.text.ParseException;
import java.util.List;
import java.util.zip.Inflater;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BendaharaAdapter extends RecyclerView.Adapter<BendaharaAdapter.MyViewHolder> {

    private static final String TAG = "BendaharaAdapter";
    private Context context;
    private List<Bendahara> bendaharaList;
    ApiInterface apiService;
    private ProgressDialog progressDialog;

    public BendaharaAdapter(Context context, List<Bendahara> bendaharaList){
        this.context = context;
        this.bendaharaList = bendaharaList;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout constraintLayoutIsiCard;
        TextView tvNama, tvTTL, tvAlamat, tvNohp,tvStakeholder,tvEmail;
        ImageButton ibEdit, ibDelete;
        View separator;
        public MyViewHolder(View itemView) {
            super(itemView);

            constraintLayoutIsiCard = (ConstraintLayout) itemView.findViewById(R.id.constraint_isi_card_bendahara);
            tvNama = (TextView) itemView.findViewById(R.id.nama_bendahara);
            tvTTL = (TextView) itemView.findViewById(R.id.ttl_bendahara);
            tvAlamat = (TextView) itemView.findViewById(R.id.alamat_bendahara);
            tvNohp = (TextView) itemView.findViewById(R.id.nohp_bendahara);
            tvStakeholder = (TextView) itemView.findViewById(R.id.stakeholder_bendahara);
            tvEmail = (TextView) itemView.findViewById(R.id.email_bendahara);
            ibEdit = (ImageButton) itemView.findViewById(R.id.btn_edit_bendahara);
            ibDelete= (ImageButton) itemView.findViewById(R.id.btn_hapus_bendahara);
            separator = (View) itemView.findViewById(R.id.separator);
        }
    }
    @Override
    public BendaharaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_bendahara,parent,false);
        Log.i(TAG, "onCreateViewHolder: Set Layout card bendahara");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BendaharaAdapter.MyViewHolder holder, int position) {
        final Bendahara bendahara = bendaharaList.get(position);

        holder.tvNama.setText(bendahara.getNama());
        try {
            holder.tvTTL.setText(bendahara.getTempatAndTanggalLahir());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvAlamat.setText(bendahara.getAlamat());
        holder.tvStakeholder.setText(bendahara.getIdStakeHolder());
        holder.tvEmail.setText(bendahara.getEmail());
        holder.tvNohp.setText(bendahara.getNoHp());
        holder.ibEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FormBendaharaActivity.class);
                i.putExtra("id_bendahara",Integer.parseInt(bendahara.getId()));
                Log.d(TAG, "onClick: idBendahara: "+bendahara.getId());
                context.startActivity(i);
            }
        });

        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Menekan button delete");
                AlertDialog.Builder b = new AlertDialog.Builder(context)
                        .setTitle("Hapus bendahara")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                progressDialog = ProgressDialog.show(context,null,"Menghapus Data",true,false);
                                apiService.deletePembendaharaan(Integer.parseInt(bendahara.getId())).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            progressDialog.dismiss();
                                        Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                                            Toast.makeText(context, "Berhasil menghapus bendahara", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(context, MainActivity.class);
                                            context.startActivity(i);
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                       progressDialog.dismiss();
                                        Log.e(TAG, "onFailure: "+t.getMessage());
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setMessage("Apakah anda yakin menghapus item ini?");

                        b.create().show();

            }
        });
    }

    @Override
    public int getItemCount() {
        if(bendaharaList.size()!=0){
            return bendaharaList.size();
        }
        return 0;
    }
}
