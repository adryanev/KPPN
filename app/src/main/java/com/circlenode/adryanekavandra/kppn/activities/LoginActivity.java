package com.circlenode.adryanekavandra.kppn.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.models.Stakeholder;
import com.circlenode.adryanekavandra.kppn.responses.ResponseStakeholder;
import com.circlenode.adryanekavandra.kppn.rest.ApiClient;
import com.circlenode.adryanekavandra.kppn.rest.ApiInterface;
import com.circlenode.adryanekavandra.kppn.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Adryan Eka Vandra on 3/22/2018.
 */

public class LoginActivity extends AppCompatActivity{

    public static String TAG = "LoginActivity";
    TextInputEditText username,password;
    TextView skip;
    Button loginButton;
    ProgressDialog loading;
    ApiInterface apiService;
    Context context;
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        username = (TextInputEditText) findViewById(R.id.username);
        password = (TextInputEditText) findViewById(R.id.password);
        skip = (TextView) findViewById(R.id.skip);
        loginButton = (Button) findViewById(R.id.login_button);
        context = this;

        apiService = ApiClient.getClient().create(ApiInterface.class);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"Menyimpan nilai false pada isLoggedIn");
                startActivity(new Intent(LoginActivity.this, BeritaActivity.class));
                Log.i(TAG,"Memulai Berita Activity");
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(view.getContext(),null,"Sedang login...",true,false);
                login();
            }
        });

    }

    private void login() {
        final String stringUsername = username.getText().toString();
        final String stringPassword = password.getText().toString();

        apiService.requestLogin(stringUsername,stringPassword).enqueue(new Callback<ResponseStakeholder>() {
            @Override
            public void onResponse(Call<ResponseStakeholder> call, Response<ResponseStakeholder> response) {
                if(response.isSuccessful()){
                    Log.i(TAG,"Sukses Mendapatkan JSON");
                    loading.dismiss();
                    Log.d(TAG,"Login dengan akun: "+stringUsername+", password: "+stringPassword);
                    Boolean success = response.body().getSuccess();
                    if(success){
                        Log.i(TAG,"Status request: "+success.toString());

                        List<Stakeholder> stakeholders = response.body().getData();
                        if(!stakeholders.isEmpty()){
                            sessionManager.createLoginSession(stakeholders.get(0).getStakeID(),stakeholders.get(0).getKodeStake(),
                                    stakeholders.get(0).getPassword(),stakeholders.get(0).getNamaStake(),stakeholders.get(0).getEmailStake());

                        }
                        else{
                            Toast.makeText(context,"Username atau password salah.",Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(context,"Sukses Login "+ stakeholders.get(0).getKodeStake(),Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        LoginActivity.this.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();
                    }
                    else{
                        Toast.makeText(context,"Username/password salah",Toast.LENGTH_SHORT).show();
                        Log.e(TAG,"Username/Password Salah");
                    }


                }
                else{
                    Log.e(TAG, "onResponse: GA BERHASIL");
                    Toast.makeText(context,"Maaf login tidak berhasil, silahkan diulang.",Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseStakeholder> call, Throwable t) {
                Log.e(TAG,"Gagal Mendapatkan JSON");
                Log.e(TAG,t.getMessage());
                Toast.makeText(context, "Silahkan cek koneksi internet anda.", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });

    }
}
