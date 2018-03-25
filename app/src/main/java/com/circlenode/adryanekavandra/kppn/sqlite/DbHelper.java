package com.circlenode.adryanekavandra.kppn.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.circlenode.adryanekavandra.kppn.models.Notif;
import com.circlenode.adryanekavandra.kppn.models.NotifStake;
import com.facebook.stetho.inspector.database.SqliteDatabaseDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adryan Eka Vandra on 3/25/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    //log
    private static final String LOG = "DbHelper";

    //versi database
    private static final Integer DATABASE_VERSION = 3;

    //nama database
    private static final String DATABASE_NAME = "kppn";

    //nama-nama tabel
    private static final String TABLE_NOTIF = "notif";
    private static final String TABLE_NOTIF_STAKE = "notifstake";

    //field tabel notif
    private static final String KEY_NOTIF_ID = "notif_id";
    private static final String KEY_NOTIF_NAMA = "notif_nama";
    private static final String KEY_NOTIF_STAKE_HOLDER = "notif_stake_holder";
    private static final String KEY_NOTIF_START_END = "notif_start_end";
    private static final String KEY_NOTIF_PENGIRIM = "notif_pengirim";
    private static final String KEY_NOTIF_PESAN = "notif_pesan";

    //field tabel notif stake

    private static final String KEY_NOTIF_ID_STAKE = "notifID";
    private static final String KEY_NAMA_STAKE = "nama_stake";
    private static final String KEY_PESAN = "pesan";
    private static final String KEY_PENGIRIM = "pengirim";
    private static final String KEY_TANGGAL = "tanggal";


    private static final String CREATE_TABLE_NOTIF = "CREATE TABLE "+TABLE_NOTIF+" (" +
            KEY_NOTIF_ID+" INTEGER PRIMARY KEY," +
            KEY_NOTIF_NAMA+" TEXT," +
            KEY_NOTIF_STAKE_HOLDER+" TEXT," +
            KEY_NOTIF_START_END+" TEXT," +
            KEY_NOTIF_PENGIRIM+" TEXT," +
            KEY_NOTIF_PESAN+" TEXT)";

    private static final String CREATE_TABLE_NOTIF_STAKE = "CREATE TABLE "+TABLE_NOTIF_STAKE+" (" +
            KEY_NOTIF_ID_STAKE+" INTEGER PRIMARY KEY," +
            KEY_NAMA_STAKE+" TEXT," +
            KEY_PESAN+" TEXT," +
            KEY_PENGIRIM+" TEXT," +
            KEY_TANGGAL+" TEXT)";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE_NOTIF);
            sqLiteDatabase.execSQL(CREATE_TABLE_NOTIF_STAKE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NOTIF);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NOTIF_STAKE);

        onCreate(sqLiteDatabase);
    }

    public Long insertNotif(Notif notif){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NOTIF_ID,Integer.parseInt(notif.getNotifID()));
        cv.put(KEY_NOTIF_NAMA,notif.getNotifNama());
        cv.put(KEY_NOTIF_PENGIRIM,notif.getNotifPengirim());
        cv.put(KEY_NOTIF_STAKE_HOLDER,notif.getNotifStakeHolder());
        cv.put(KEY_NOTIF_START_END,notif.getNotifStartEnd());
        cv.put(KEY_NOTIF_PESAN,notif.getNotifPesan());
        Long idNotif = db.insert(TABLE_NOTIF,null,cv);
        return idNotif;
    }


    public Long insertNotifStake(NotifStake notifStake){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NOTIF_ID_STAKE, Integer.parseInt(notifStake.getNotifID()));
        cv.put(KEY_NAMA_STAKE,notifStake.getNamaStake());
        cv.put(KEY_PESAN,notifStake.getPesan());
        cv.put(KEY_PENGIRIM,notifStake.getPengirim());
        cv.put(KEY_TANGGAL,notifStake.getTanggal());
        Long idNotifStake = db.insert(TABLE_NOTIF_STAKE,null,cv);
        return idNotifStake;
    }

    public List<Notif> getValidNotif(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Notif> list = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_NOTIF+" WHERE "+KEY_NOTIF_START_END+" > date('now') ORDER BY "+KEY_NOTIF_START_END+" DESC";
        Cursor c = db.rawQuery(query,null);
        if(c.moveToFirst()){
            do{
                Notif notif = new Notif();

                notif.setNotifID(String.valueOf(c.getString(c.getColumnIndex(KEY_NOTIF_ID))));
                notif.setNotifNama(c.getString(c.getColumnIndex(KEY_NOTIF_NAMA)));
                notif.setNotifPesan(c.getString(c.getColumnIndex(KEY_NOTIF_PESAN)));
                notif.setNotifPengirim(c.getString(c.getColumnIndex(KEY_NOTIF_PENGIRIM)));
                notif.setNotifStakeHolder(c.getString(c.getColumnIndex(KEY_NOTIF_STAKE_HOLDER)));
                notif.setNotifStartEnd(c.getString(c.getColumnIndex(KEY_NOTIF_START_END)));
                list.add(notif);
            }while (c.moveToNext());
        }
        return list;
    }

    public List<NotifStake> getAllValidNotifStake(Integer idStake){
        List<NotifStake> notifStakeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NOTIF_STAKE+" WHERE "+KEY_NAMA_STAKE+" = "+idStake+" AND "+KEY_TANGGAL+" > date('now') ORDER BY "+KEY_TANGGAL+" DESC";
        Cursor c= db.rawQuery(query,null);

        if(c.moveToFirst()){
            do{
                NotifStake notifStake = new NotifStake();
                notifStake.setNotifID(String.valueOf(c.getInt(c.getColumnIndex(KEY_NOTIF_ID_STAKE))));
                notifStake.setNamaStake(c.getString(c.getColumnIndex(KEY_NAMA_STAKE)));
                notifStake.setPengirim(c.getString(c.getColumnIndex(KEY_PENGIRIM)));
                notifStake.setTanggal(c.getString(c.getColumnIndex(KEY_TANGGAL)));
                notifStake.setPesan(c.getString(c.getColumnIndex(KEY_PESAN)));

                notifStakeList.add(notifStake);
            }while (c.moveToNext());
        }

        return notifStakeList;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public void truncateTable(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName,null,null);

    }
}
