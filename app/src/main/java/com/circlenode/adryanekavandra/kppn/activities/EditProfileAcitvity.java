package com.circlenode.adryanekavandra.kppn.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.rest.ApiClient;
import com.circlenode.adryanekavandra.kppn.rest.ApiInterface;
import com.circlenode.adryanekavandra.kppn.utils.SessionManager;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileAcitvity extends AppCompatActivity {

    private static String TAG = EditProfileAcitvity.class.getSimpleName();
    SessionManager sessionManager;
    String stakeKode, namaStake, passwordStake, emailStake, stakeID;
    TextView tvKodeStake;
    TextInputEditText etNamaStake, etPasswordStake, etEmailStake;
    Button actionEditProfil;
    ApiInterface apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_acitvity);
        sessionManager = new SessionManager(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        tvKodeStake = (TextView) findViewById(R.id.placeholderKodeStake);
        etNamaStake = (TextInputEditText) findViewById(R.id.etNamaStake);
        etPasswordStake = (TextInputEditText) findViewById(R.id.etPasswordStake);
        etEmailStake = (TextInputEditText) findViewById(R.id.etEmailStake);
        actionEditProfil = (Button) findViewById(R.id.btn_action_edit_profil);
        HashMap<String,String> userData = sessionManager.getUserDetail();
        stakeKode = userData.get(SessionManager.KEY_KODE_STAKE);
        namaStake = userData.get(SessionManager.KEY_NAMA_STAKE);
        passwordStake = userData.get(SessionManager.KEY_PASSWORD);
        emailStake = userData.get(SessionManager.KEY_EMAIL) == null ? " ": emailStake;
        stakeID = userData.get(SessionManager.KEY_STAKE_ID);
        setTitle("KPPN - Edit Profil");
        tvKodeStake.setText(stakeKode);
        etNamaStake.setText(namaStake);
        etEmailStake.setText(emailStake);

        actionEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordStake.isEmpty()){
                    Log.d(TAG,"Password Kosong");
                    Toast.makeText(EditProfileAcitvity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d(TAG,"Sukses Onclick Listener");
                    String nama = etNamaStake.getText().toString();
                    String password = etPasswordStake.getText().toString();
                    String email = etEmailStake.getText().toString();
                    ubahDataServer(nama,password,email);


                }

            }
        });

    }


    private void ubahDataServer(final String nama, final String password, final String email){
        Log.d(TAG,"Masuk ke Ubah Server()");
        apiService.editProfil(stakeKode,nama,password,email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Sukses Mengubah Profil",Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Sukses Mengambil JSON");
                    ubahSharedPreference(nama,password,email);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditProfileAcitvity.this, "Gagal Mengubah Profil, Cek Internet Anda", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Gagal Mengambil JSON");
            }
        });
    }

    private void ubahSharedPreference(String nama, String password, String email){
        sessionManager.createLoginSession(stakeID,stakeKode,password,nama,email);
        Log.d(TAG, "Sukses Mengubah Shared Preferences");
        Intent i = new Intent(EditProfileAcitvity.this, MainActivity.class);
        EditProfileAcitvity.this.startActivity(i);
        finish();
    }
}
