package com.circlenode.adryanekavandra.kppn.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.fragments.BeritaFragment;
import com.circlenode.adryanekavandra.kppn.fragments.NotifFragment;
import com.circlenode.adryanekavandra.kppn.fragments.NotifStakeFragment;
import com.circlenode.adryanekavandra.kppn.fragments.ProfilFragment;
import com.circlenode.adryanekavandra.kppn.utils.SessionManager;
import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Fragment beritaFragment, profilFragment, notifikasiFragment, notifikasiStakeFragment;
    FrameLayout frameLayout;
    SessionManager sessionManager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(beritaFragment);
                    return true;
                case R.id.navigation_profile:
                    setFragment(profilFragment);
                    return true;
                case R.id.navigation_notifications:
                    setFragment(notifikasiFragment);
                    return true;
                case R.id.navigation_notifications_stake:
                    setFragment(notifikasiStakeFragment);
                    return true;
                default:
                    return false;
            }


        }
    };

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,fragment);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Stetho.initializeWithDefaults(this);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        this.setTitle("KPPN");
        beritaFragment = new BeritaFragment();
        profilFragment = new ProfilFragment();
        notifikasiFragment = new NotifFragment();
        notifikasiStakeFragment = new NotifStakeFragment();
        setFragment(beritaFragment);

        if(!sessionManager.isLoggedIn()){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Integer id = item.getItemId();
        if(id == R.id.actionLogout){
            final Intent i = new Intent(this, LoginActivity.class);
            android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            sessionManager.logoutUser();

                            // Closing all the Activities
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();

                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    })
                    .setMessage("Apakah anda ingin logout?");
            b.create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
