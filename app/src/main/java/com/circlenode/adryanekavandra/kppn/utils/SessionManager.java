package com.circlenode.adryanekavandra.kppn.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

/**
 * Created by Adryan Eka Vandra on 3/23/2018.
 */

public class SessionManager {
    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LearnArabicPref";

    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_STAKE_ID = "stakeID";
    private static final String KEY_KODE_STAKE = "stakeKode";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAMA_STAKE = "stakeName";
    private static final String KEY_EMAIL = "stakeEmail";



    //constuctor

    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        //sharedPreferences = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String stakeID, String stakeKode, String password, String stakeNama, String email){
        editor.putBoolean(IS_LOGGED_IN,true);
        editor.putString(KEY_STAKE_ID,stakeID);
        editor.putString(KEY_KODE_STAKE,stakeKode);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_NAMA_STAKE,stakeNama);
        editor.putString(KEY_EMAIL,email);

        editor.commit();
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(KEY_STAKE_ID,sharedPreferences.getString(KEY_STAKE_ID,null));
        user.put(KEY_KODE_STAKE, sharedPreferences.getString(KEY_KODE_STAKE,null));
        user.put(KEY_PASSWORD, sharedPreferences.getString(KEY_PASSWORD,null));
        user.put(KEY_NAMA_STAKE, sharedPreferences.getString(KEY_NAMA_STAKE,null));
        user.put(KEY_EMAIL, sharedPreferences.getString(KEY_EMAIL,null));
        return user;
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN,false);
    }
}
