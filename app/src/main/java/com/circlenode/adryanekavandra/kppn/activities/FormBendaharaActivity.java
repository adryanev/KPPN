package com.circlenode.adryanekavandra.kppn.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.models.Bendahara;
import com.circlenode.adryanekavandra.kppn.models.Stakeholder;
import com.circlenode.adryanekavandra.kppn.responses.ResponsePembendaharaan;
import com.circlenode.adryanekavandra.kppn.responses.ResponseStake;
import com.circlenode.adryanekavandra.kppn.rest.ApiClient;
import com.circlenode.adryanekavandra.kppn.rest.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormBendaharaActivity extends AppCompatActivity {
    private static final String TAG = "FormBendaharaActivity";
    TextInputEditText etNama, etAlamat, etEmail, etNoHp, etStakeholder;
    AppCompatAutoCompleteTextView etJabatan;
    Button btnSave;
    List<Stakeholder> list;
    ArrayList<String> arrayList;
    HashMap<String, String> kvStake;
    ApiInterface apiService;
    SpinnerDialog spinnerDialog;
    ProgressDialog pd;
    Integer idBendahara;
    Intent i;
    String selection;
    String nama, jabatan,  email, alamat, noHp, stakeholder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pembendaharaan);
        Log.d(TAG, "onCreate: load form");
        setTitle("Form Bendahara");
        apiService = ApiClient.getClient().create(ApiInterface.class);
        arrayList = new ArrayList<>();
        kvStake = new HashMap<>();
        i  = getIntent();
        idBendahara = i.getIntExtra("id_bendahara",0);
        Log.i(TAG, "onCreate: idBendahara "+idBendahara);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.jabatanList));

        etJabatan.setAdapter(arrayAdapter);
        etJabatan.setCursorVisible(false);
        etJabatan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etJabatan.showDropDown();
                selection = (String) parent.getItemAtPosition(position);
            }
        });

        prepareList();
        if(idBendahara !=0){
            prepareisiForm();
        }
        etNama = (TextInputEditText) findViewById(R.id.etNamaBendahara);
        etJabatan = (AppCompatAutoCompleteTextView) findViewById(R.id.jabatan_perbendaharaan);
        etEmail = (TextInputEditText) findViewById(R.id.etEmail);
        etAlamat = (TextInputEditText) findViewById(R.id.etAlamatBendahara);
        etNoHp = (TextInputEditText) findViewById(R.id.etNoHp);
        etStakeholder = (TextInputEditText) findViewById(R.id.etStakeHolder);
        btnSave = (Button) findViewById(R.id.saveBendahara);
        Log.d(TAG, "onCreate: load form");

        etJabatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etJabatan.showDropDown();
            }
        });
        spinnerDialog = new SpinnerDialog(FormBendaharaActivity.this,arrayList,"Pilih stakeholder");


        etStakeholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
            }
        });
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String s, int i) {
                updateET(s,i);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanDiServer();
            }
        });

    }

    private void prepareisiForm() {

        apiService.getBendahara(idBendahara).enqueue(new Callback<ResponsePembendaharaan>() {
            @Override
            public void onResponse(Call<ResponsePembendaharaan> call, Response<ResponsePembendaharaan> response) {
                Log.d(TAG, "onResponse: Mengisi data form");
                List<Bendahara> bendahara = response.body().getBendaharaList();
                Bendahara data = bendahara.get(0);
                etNama.setText(data.getNama());
                etJabatan.setText(data.getJabatan());
                etEmail.setText(data.getEmail());
                etAlamat.setText(data.getAlamat());
                etNoHp.setText(data.getNoHp());
                etStakeholder.setText(data.getIdStakeHolder());
                Log.d(TAG, "onResponse() berhasil set data: " );
            }

            @Override
            public void onFailure(Call<ResponsePembendaharaan> call, Throwable t) {
                Log.e(TAG, "onFailure: Gagal mendapat data"+t.getLocalizedMessage() );
                Toast.makeText(FormBendaharaActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateET(String s, int i) {
        String id = kvStake.get(s);
        etStakeholder.setText(id);
    }

    private void prepareList() {
        Log.d(TAG, "prepareList: menjalankan prepare list");
        apiService.getAllStakeholder().enqueue(new Callback<ResponseStake>() {
            @Override
            public void onResponse(Call<ResponseStake> call, Response<ResponseStake> response) {
                Log.d(TAG, "onResponse: Mendapatkan data stakeholder");    
                list = response.body().getStakeHolder();
                initItem();

            }

            @Override
            public void onFailure(Call<ResponseStake> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
                Toast.makeText(FormBendaharaActivity.this, "Gagal mendapatkan data stakeholder", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initItem() {
        Log.d(TAG, "initItem: Menjalankan init item");
        for (Stakeholder stake: list) {
            kvStake.put(stake.getNamaStake(),stake.getStakeID());
            arrayList.add(stake.getNamaStake());
        }

    }

    private void simpanDiServer() {
        nama = etNama.getText().toString();
        jabatan = etJabatan.getText().toString();
        email = etEmail.getText().toString();
        alamat = etAlamat.getText().toString();
        noHp = etNoHp.getText().toString();
        stakeholder = etStakeholder.getText().toString();
        pd = ProgressDialog.show(this,null,"Mengirim data ke server",true,false);
        if(idBendahara == 0){
            createBendahara();
        }else{
          updateBendahara(idBendahara);
        }
    }

    private void updateBendahara(Integer idBendahara) {
        apiService.updatePembendaharaan(idBendahara,nama,jabatan,alamat,email,noHp,Integer.parseInt(stakeholder)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    pd.dismiss();
                    Log.d(TAG, "onResponse: Berhasil mengupdate bendahara");
                    Toast.makeText(FormBendaharaActivity.this, "Berhasil Mengupdate bendahara", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FormBendaharaActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: "+t.getMessage() );
                Toast.makeText(FormBendaharaActivity.this, "Gagal terhubung dengan server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createBendahara() {
        apiService.setPembendaharaan(nama,jabatan,alamat,email,noHp,Integer.parseInt(stakeholder)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    pd.dismiss();
                    Log.d(TAG, "onResponse: Berhasil Menambah bendahara");
                    Toast.makeText(FormBendaharaActivity.this, "Berhasil menambah bendahara", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FormBendaharaActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG, "onFailure: "+t.getMessage() );
                Toast.makeText(FormBendaharaActivity.this, "Gagal terhubung dengan server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
