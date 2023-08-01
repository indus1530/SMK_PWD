package edu.aku.hassannaqvi.smk_pwd.core;

import static edu.aku.hassannaqvi.smk_pwd.contracts.StaffingContract.StaffingTable;
import static edu.aku.hassannaqvi.smk_pwd.core.MainApp.IBAHC;
import static edu.aku.hassannaqvi.smk_pwd.utils.CreateTable.DATABASE_NAME;
import static edu.aku.hassannaqvi.smk_pwd.utils.CreateTable.DATABASE_VERSION;
import static edu.aku.hassannaqvi.smk_pwd.utils.CreateTable.SQL_CREATE_DISTRICTS;
import static edu.aku.hassannaqvi.smk_pwd.utils.CreateTable.SQL_CREATE_FORMS;
import static edu.aku.hassannaqvi.smk_pwd.utils.CreateTable.SQL_CREATE_HF;
import static edu.aku.hassannaqvi.smk_pwd.utils.CreateTable.SQL_CREATE_PSCONTRACT;
import static edu.aku.hassannaqvi.smk_pwd.utils.CreateTable.SQL_CREATE_TEHSILS;
import static edu.aku.hassannaqvi.smk_pwd.utils.CreateTable.SQL_CREATE_UCS;
import static edu.aku.hassannaqvi.smk_pwd.utils.CreateTable.SQL_CREATE_USERS;
import static edu.aku.hassannaqvi.smk_pwd.utils.CreateTable.SQL_CREATE_VERSIONAPP;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.util.Log;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;
import net.sqlcipher.database.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import edu.aku.hassannaqvi.smk_pwd.contracts.DistrictContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.FormsContract.FormsTable;
import edu.aku.hassannaqvi.smk_pwd.contracts.HFContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.PatientsContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.PatientsContract.PatientsTable;
import edu.aku.hassannaqvi.smk_pwd.contracts.StaffingContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.TableContracts;
import edu.aku.hassannaqvi.smk_pwd.contracts.TehsilsContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.UCsContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.UsersContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.VersionAppContract;
import edu.aku.hassannaqvi.smk_pwd.models.EntryLog;
import edu.aku.hassannaqvi.smk_pwd.models.Forms;
import edu.aku.hassannaqvi.smk_pwd.models.Patients;
import edu.aku.hassannaqvi.smk_pwd.models.Users;


/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private final String TAG = "DatabaseHelper";
    public static final String DATABASE_PASSWORD = IBAHC;
    private final Context mContext;

    public String spDateT = new SimpleDateFormat("dd-MM-yy").format(new Date().getTime());

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_FORMS);
        db.execSQL(SQL_CREATE_PSCONTRACT);
        db.execSQL(SQL_CREATE_VERSIONAPP);
        db.execSQL(SQL_CREATE_DISTRICTS);
        db.execSQL(SQL_CREATE_TEHSILS);
        db.execSQL(SQL_CREATE_UCS);
        db.execSQL(SQL_CREATE_HF);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }


    public void syncUser(JSONArray userlist) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(UsersContract.singleUser.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = userlist;
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObjectUser = jsonArray.getJSONObject(i);

                UsersContract user = new UsersContract();
                user.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(UsersContract.singleUser.ROW_USERNAME, user.getUserName());
                values.put(UsersContract.singleUser.ROW_PASSWORD, user.getPassword());
                values.put(UsersContract.singleUser.DESIGNATION, user.getDESIGNATION());
                values.put(UsersContract.singleUser.DIST_ID, user.getDIST_ID());
                db.insert(UsersContract.singleUser.TABLE_NAME, null, values);
            }

        } catch (Exception e) {
            Log.d(TAG, "syncUser(e): " + e);
        } finally {
            db.close();
        }
    }


    public void syncDistricts(JSONArray Districtslist) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(DistrictContract.singleDistrict.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = Districtslist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCC = jsonArray.getJSONObject(i);

                DistrictContract Vc = new DistrictContract();
                Vc.Sync(jsonObjectCC);

                ContentValues values = new ContentValues();

                values.put(DistrictContract.singleDistrict.COLUMN_DISTRICT_CODE, Vc.getDistrictCode());
                values.put(DistrictContract.singleDistrict.COLUMN_DISTRICT_NAME, Vc.getDistrictName());
                values.put(DistrictContract.singleDistrict.COLUMN_DISTRICT_TYPE, Vc.getDistrictType());

                db.insert(DistrictContract.singleDistrict.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
        } finally {
            db.close();
        }
    }


    public void syncTehsils(JSONArray Tehsilslist) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(TehsilsContract.singleTehsil.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = Tehsilslist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCC = jsonArray.getJSONObject(i);

                TehsilsContract Vc = new TehsilsContract();
                Vc.Sync(jsonObjectCC);

                ContentValues values = new ContentValues();

                values.put(TehsilsContract.singleTehsil.COLUMN_TEHSIL_CODE, Vc.getTehsilCode());
                values.put(TehsilsContract.singleTehsil.COLUMN_TEHSIL_NAME, Vc.getTehsilName());
                values.put(TehsilsContract.singleTehsil.COLUMN_DISTRICT_CODE, Vc.getDistrictcode());

                db.insert(TehsilsContract.singleTehsil.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
        } finally {
            db.close();
        }
    }


    public void syncUCs(JSONArray UCslist) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(UCsContract.singleUCs.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = UCslist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCC = jsonArray.getJSONObject(i);

                UCsContract Vc = new UCsContract();
                Vc.Sync(jsonObjectCC);

                ContentValues values = new ContentValues();

                values.put(UCsContract.singleUCs.COLUMN_UC_CODE, Vc.getUc_code());
                values.put(UCsContract.singleUCs.COLUMN_UC_NAME, Vc.getUc_name());
                values.put(UCsContract.singleUCs.COLUMN_TEHSIL_CODE, Vc.getTehsil_code());

                db.insert(UCsContract.singleUCs.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
        } finally {
            db.close();
        }
    }


    public void syncHF(JSONArray HFlist) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(HFContract.singleHF.TABLE_NAME, null, null);
        try {
            JSONArray jsonArray = HFlist;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectCC = jsonArray.getJSONObject(i);

                HFContract Vc = new HFContract();
                Vc.Sync(jsonObjectCC);

                ContentValues values = new ContentValues();

                values.put(HFContract.singleHF.COLUMN_PROVINCE, Vc.getProvince());
                values.put(HFContract.singleHF.COLUMN_PRO_ID, Vc.getPro_id());
                values.put(HFContract.singleHF.COLUMN_DISTRICT, Vc.getDistrict());
                values.put(HFContract.singleHF.COLUMN_DIST_ID, Vc.getDist_id());
                values.put(HFContract.singleHF.COLUMN_TEHSIL, Vc.getTehsil());
                values.put(HFContract.singleHF.COLUMN_TEHSIL_ID, Vc.getTehsil_id());
                values.put(HFContract.singleHF.COLUMN_UC_NAME, Vc.getUc_name());
                values.put(HFContract.singleHF.COLUMN_UC_ID, Vc.getUc_id());
                values.put(HFContract.singleHF.COLUMN_HF_NAME, Vc.getHf_name());
                values.put(HFContract.singleHF.COLUMN_HFCODE, Vc.getHfcode());

                db.insert(HFContract.singleHF.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
        } finally {
            db.close();
        }
    }


    public Collection<DistrictContract> getAllDistricts() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                DistrictContract.singleDistrict.COLUMN_DISTRICT_CODE,
                DistrictContract.singleDistrict.COLUMN_DISTRICT_NAME,
                DistrictContract.singleDistrict.COLUMN_DISTRICT_TYPE
        };

        String whereClause = null;
        String[] whereArgs = null;

        if (MainApp.DIST_ID != null) {
            whereClause = DistrictContract.singleDistrict.COLUMN_DISTRICT_CODE + "=?";
            whereArgs = new String[]{MainApp.DIST_ID};
        }

        String groupBy = null;
        String having = null;

        String orderBy =
                DistrictContract.singleDistrict.COLUMN_DISTRICT_NAME + " ASC";

        Collection<DistrictContract> allDC = new ArrayList<>();
        try {
            c = db.query(
                    DistrictContract.singleDistrict.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                DistrictContract dc = new DistrictContract();
                allDC.add(dc.HydrateDistrict(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDC;
    }


    public Collection<TehsilsContract> getAllTehsils() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                TehsilsContract.singleTehsil.COLUMN_TEHSIL_CODE,
                TehsilsContract.singleTehsil.COLUMN_TEHSIL_NAME,
                TehsilsContract.singleTehsil.COLUMN_DISTRICT_CODE
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                TehsilsContract.singleTehsil.COLUMN_TEHSIL_NAME + " ASC";

        Collection<TehsilsContract> allDC = new ArrayList<>();
        try {
            c = db.query(
                    TehsilsContract.singleTehsil.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                TehsilsContract dc = new TehsilsContract();
                allDC.add(dc.HydrateTehsils(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDC;
    }


    public Collection<HFContract> getAllTehsils(String districtCode) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                "DISTINCT " + HFContract.singleHF.COLUMN_TEHSIL,
                HFContract.singleHF.COLUMN_TEHSIL_ID
        };

        String whereClause = HFContract.singleHF.COLUMN_DIST_ID + "=? AND " + HFContract.singleHF.COLUMN_HF_NAME + " NOT LIKE ?";
        String[] whereArgs = new String[]{districtCode, "%Test%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                HFContract.singleHF.COLUMN_TEHSIL + " ASC";

        Collection<HFContract> allDC = new ArrayList<>();
        try {
            c = db.query(
                    HFContract.singleHF.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                HFContract dc = new HFContract();
                allDC.add(dc.HydrateTehsil(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDC;
    }


    public Collection<HFContract> getAllTehsil(String districtCode) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                "DISTINCT " + HFContract.singleHF.COLUMN_TEHSIL,
                HFContract.singleHF.COLUMN_TEHSIL_ID
        };

        String whereClause = HFContract.singleHF.COLUMN_DIST_ID + "=? AND " + HFContract.singleHF.COLUMN_HF_NAME + " LIKE ?";
        String[] whereArgs = new String[]{districtCode, "%Test%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                HFContract.singleHF.COLUMN_TEHSIL + " ASC";

        Collection<HFContract> allDC = new ArrayList<>();
        try {
            c = db.query(
                    HFContract.singleHF.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                HFContract dc = new HFContract();
                allDC.add(dc.HydrateTehsil(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDC;
    }


    public Collection<HFContract> getAllUCs(String tehsilCode) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                "DISTINCT " + HFContract.singleHF.COLUMN_UC_NAME,
                HFContract.singleHF.COLUMN_UC_ID
        };

        String whereClause = HFContract.singleHF.COLUMN_TEHSIL_ID + "=? AND " + HFContract.singleHF.COLUMN_HF_NAME + " NOT LIKE ?";
        String[] whereArgs = new String[]{tehsilCode, "%Test%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                HFContract.singleHF.COLUMN_UC_NAME + " ASC";

        Collection<HFContract> allDC = new ArrayList<>();
        try {
            c = db.query(
                    HFContract.singleHF.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                HFContract dc = new HFContract();
                allDC.add(dc.HydrateUcs(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDC;
    }


    public Collection<HFContract> getAllUC(String tehsilCode) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                "DISTINCT " + HFContract.singleHF.COLUMN_UC_NAME,
                HFContract.singleHF.COLUMN_UC_ID
        };

        String whereClause = HFContract.singleHF.COLUMN_TEHSIL_ID + "=? AND " + HFContract.singleHF.COLUMN_HF_NAME + " LIKE ?";
        String[] whereArgs = new String[]{tehsilCode, "%Test%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                HFContract.singleHF.COLUMN_UC_NAME + " ASC";

        Collection<HFContract> allDC = new ArrayList<>();
        try {
            c = db.query(
                    HFContract.singleHF.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                HFContract dc = new HFContract();
                allDC.add(dc.HydrateUcs(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allDC;
    }


    public Collection<HFContract> getAllHFs(String uc_id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                "DISTINCT " + HFContract.singleHF.COLUMN_HF_NAME,
                HFContract.singleHF.COLUMN_HFCODE
        };

        String whereClause = HFContract.singleHF.COLUMN_UC_ID + "=? AND " + HFContract.singleHF.COLUMN_HF_NAME + " NOT LIKE ?";
        String[] whereArgs = new String[]{uc_id, "%Test%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                HFContract.singleHF.COLUMN_UC_ID + " ASC";

        Collection<HFContract> allHF = new ArrayList<>();
        try {
            c = db.query(
                    HFContract.singleHF.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                HFContract hf = new HFContract();
                allHF.add(hf.HydrateHFs(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allHF;
    }


    public Collection<HFContract> getAllHF(String uc_id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                "DISTINCT " + HFContract.singleHF.COLUMN_HF_NAME,
                HFContract.singleHF.COLUMN_HFCODE
        };

        String whereClause = HFContract.singleHF.COLUMN_UC_ID + "=? AND " + HFContract.singleHF.COLUMN_HF_NAME + " LIKE ?";
        String[] whereArgs = new String[]{uc_id, "%Test%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                HFContract.singleHF.COLUMN_UC_ID + " ASC";

        Collection<HFContract> allHF = new ArrayList<>();
        try {
            c = db.query(
                    HFContract.singleHF.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                HFContract hf = new HFContract();
                allHF.add(hf.HydrateHFs(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allHF;
    }


    public Collection<HFContract> getHFbyDist(String dist_id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                HFContract.singleHF.COLUMN_PROVINCE,
                HFContract.singleHF.COLUMN_PRO_ID,
                HFContract.singleHF.COLUMN_DISTRICT,
                HFContract.singleHF.COLUMN_DIST_ID,
                HFContract.singleHF.COLUMN_TEHSIL,
                HFContract.singleHF.COLUMN_TEHSIL_ID,
                HFContract.singleHF.COLUMN_UC_NAME,
                HFContract.singleHF.COLUMN_UC_ID,
                HFContract.singleHF.COLUMN_HF_NAME,
                HFContract.singleHF.COLUMN_HFCODE
        };

        String whereClause = HFContract.singleHF.COLUMN_DIST_ID + "=?";
        String[] whereArgs = new String[]{dist_id};
        String groupBy = null;
        String having = null;

        String orderBy =
                HFContract.singleHF.COLUMN_DISTRICT + " ASC";

        Collection<HFContract> allHF = new ArrayList<>();
        try {
            c = db.query(
                    HFContract.singleHF.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                HFContract hf = new HFContract();
                allHF.add(hf.HydrateHF(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allHF;
    }


    public Collection<UsersContract> getUserByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                UsersContract.singleUser.ROW_USERNAME,
                UsersContract.singleUser.ROW_PASSWORD,
                UsersContract.singleUser.DESIGNATION,
                UsersContract.singleUser.DIST_ID
        };

        String whereClause = UsersContract.singleUser.ROW_USERNAME + "=?";
        String[] whereArgs = new String[]{name};
        String groupBy = null;
        String having = null;

        String orderBy =
                UsersContract.singleUser.DIST_ID + " ASC";

        Collection<UsersContract> all = new ArrayList<>();
        try {
            c = db.query(
                    UsersContract.singleUser.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                UsersContract us = new UsersContract();
                all.add(us.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return all;
    }


    public Collection<UsersContract> getUserByName(String name, String desig) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                UsersContract.singleUser.ROW_USERNAME,
                UsersContract.singleUser.ROW_PASSWORD,
                UsersContract.singleUser.DESIGNATION,
                UsersContract.singleUser.DIST_ID
        };

        String whereClause = UsersContract.singleUser.ROW_USERNAME + "=?" + "AND" + UsersContract.singleUser.DESIGNATION + "LIKE '%Test User%'";
        String[] whereArgs = new String[]{name};
        String groupBy = null;
        String having = null;

        String orderBy =
                UsersContract.singleUser.DIST_ID + " ASC";

        Collection<UsersContract> all = new ArrayList<>();
        try {
            c = db.query(
                    UsersContract.singleUser.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                UsersContract us = new UsersContract();
                all.add(us.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return all;
    }


    public int getDistIDbyName(String name) {
        /*String countQuery = "SELECT  * FROM " + UsersContract.singleUser.TABLE_NAME + " WHERE " + UsersContract.singleUser.ROW_USERNAME + " = '" + name +"'";
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;*/


        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        Cursor mCursor = db.rawQuery("SELECT  * FROM " + UsersContract.singleUser.TABLE_NAME + " WHERE " + UsersContract.singleUser.ROW_USERNAME + " =?", new String[]{name});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {

                if (mCursor.moveToFirst()) {
                    MainApp.DIST_ID = mCursor.getString(mCursor.getColumnIndex(UsersContract.singleUser.DIST_ID));
                    MainApp.Designation = mCursor.getString(mCursor.getColumnIndex(UsersContract.singleUser.DESIGNATION));
                    mCursor.close();
                }
                //return true;
            }
        }

        return 0;
    }


    public UsersContract getDistByUserName() {

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                UsersContract.singleUser.DIST_ID
        };

        String whereClause = UsersContract.singleUser.ROW_USERNAME + " Like ? ";
        String[] whereArgs = new String[]{"%" + MainApp.userName + "%"};
        String groupBy = null;
        String having = null;

        String orderBy = null;

        UsersContract allVC = new UsersContract();
        try {
            c = db.query(
                    UsersContract.singleUser.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allVC.Hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVC;
    }


    public VersionAppContract getVersionApp() {

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                VersionAppContract.VersionAppTable._ID,
                VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE,
                VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME,
                VersionAppContract.VersionAppTable.COLUMN_PATH_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = null;

        VersionAppContract allVC = new VersionAppContract();
        try {
            c = db.query(
                    VersionAppContract.VersionAppTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allVC.hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVC;
    }


    public boolean Login(String username, String password) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        Cursor mCursor = db.rawQuery("SELECT * FROM " + UsersContract.singleUser.TABLE_NAME + " WHERE " + UsersContract.singleUser.ROW_USERNAME + "=? AND " + UsersContract.singleUser.ROW_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {

            if (mCursor.moveToFirst()) {
                MainApp.DIST_ID = mCursor.getString(mCursor.getColumnIndex(UsersContract.singleUser.DIST_ID));
            }
            return mCursor.getCount() > 0;
        }
        return false;
    }


    public boolean CheckHF(String hfCode) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        Cursor mCursor = db.rawQuery("SELECT * FROM " + FormsTable.TABLE_NAME + " WHERE " + FormsTable.COLUMN_HF_CODE + "=? AND " + FormsTable.COLUMN_ISTATUS + "=?", new String[]{hfCode, "1"});
        if (mCursor != null) {

            if (mCursor.moveToFirst()) {
//                MainApp.DIST_ID = mCursor.getString(mCursor.getColumnIndex(UsersContract.singleUser.DIST_ID));
            }
            return mCursor.getCount() > 0;
        }
        return false;
    }


    public Forms CheckHF(String hfCode, String status) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Forms form = null;
        Cursor mCursor = db.rawQuery("SELECT * FROM " + FormsTable.TABLE_NAME + " WHERE " + FormsTable.COLUMN_HF_CODE + "=? AND " + FormsTable.COLUMN_ISTATUS + " != ?", new String[]{hfCode, status});
        if (mCursor != null) {
            if (mCursor.getCount() == 0) return new Forms();
            if (mCursor.moveToFirst()) {
                form = new Forms().Hydrate(mCursor);
            }
        }
        return form;
    }

    public Long addEntryLog(EntryLog entryLog) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(TableContracts.EntryLogTable.COLUMN_PROJECT_NAME, entryLog.getProjectName());
        values.put(TableContracts.EntryLogTable.COLUMN_UUID, entryLog.getUuid());
        values.put(TableContracts.EntryLogTable.COLUMN_PSU_CODE, entryLog.getPsuCode());
        values.put(TableContracts.EntryLogTable.COLUMN_HHID, entryLog.getHhid());
        values.put(TableContracts.EntryLogTable.COLUMN_USERNAME, entryLog.getUserName());
        values.put(TableContracts.EntryLogTable.COLUMN_SYSDATE, entryLog.getSysDate());
        values.put(TableContracts.EntryLogTable.COLUMN_ISTATUS, entryLog.getiStatus());
        values.put(TableContracts.EntryLogTable.COLUMN_ISTATUS96x, entryLog.getiStatus96x());
        values.put(TableContracts.EntryLogTable.COLUMN_ENTRY_TYPE, entryLog.getEntryType());
        values.put(TableContracts.EntryLogTable.COLUMN_ENTRY_DATE, entryLog.getEntryDate());
        values.put(TableContracts.EntryLogTable.COLUMN_DEVICEID, entryLog.getDeviceId());
        values.put(TableContracts.EntryLogTable.COLUMN_SYNCED, entryLog.getSynced());
        values.put(TableContracts.EntryLogTable.COLUMN_SYNC_DATE, entryLog.getSyncDate());
        values.put(TableContracts.EntryLogTable.COLUMN_APPVERSION, entryLog.getAppver());

        long newRowId;
        newRowId = db.insertOrThrow(
                TableContracts.EntryLogTable.TABLE_NAME,
                TableContracts.EntryLogTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }


    public Long addForm(Forms form) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_PROJECT_NAME, form.getProjectName());
        values.put(FormsTable.COLUMN_UID, form.get_UID());
        values.put(FormsTable.COLUMN_USERNAME, form.getUsername());
        values.put(FormsTable.COLUMN_SYSDATE, form.getSysdate());
        values.put(FormsTable.COLUMN_PROVINCE, form.getProvince());
        values.put(FormsTable.COLUMN_PROVINCE_CODE, form.getProvinceCode());
        values.put(FormsTable.COLUMN_DISTRICT, form.getDistrict());
        values.put(FormsTable.COLUMN_DISTRICT_CODE, form.getDistrictCode());
        values.put(FormsTable.COLUMN_TEHSIL, form.getTehsil());
        values.put(FormsTable.COLUMN_TEHSIL_CODE, form.getTehsilCode());
        values.put(FormsTable.COLUMN_UC, form.getUc());
        values.put(FormsTable.COLUMN_UC_CODE, form.getUcCode());
        values.put(FormsTable.COLUMN_HF, form.getHf());
        values.put(FormsTable.COLUMN_HF_CODE, form.getHfCode());
        values.put(FormsTable.COLUMN_SA, form.getsA());
        values.put(FormsTable.COLUMN_SB, form.getsB());
        values.put(FormsTable.COLUMN_SC, form.getsC());
        values.put(FormsTable.COLUMN_SD, form.getsD());
        values.put(FormsTable.COLUMN_SE, form.getsE());
        values.put(FormsTable.COLUMN_SF, form.getsF());
        values.put(FormsTable.COLUMN_SG, form.getsG());
        values.put(FormsTable.COLUMN_SH, form.getsH());
        values.put(FormsTable.COLUMN_SI, form.getsI());
        values.put(FormsTable.COLUMN_ISTATUS, form.getIstatus());
        values.put(FormsTable.COLUMN_ISTATUS96x, form.getIstatus96x());
        values.put(FormsTable.COLUMN_ENDINGDATETIME, form.getEndingdatetime());
        values.put(FormsTable.COLUMN_GPSLAT, form.getGpsLat());
        values.put(FormsTable.COLUMN_GPSLNG, form.getGpsLng());
        values.put(FormsTable.COLUMN_GPSDATE, form.getGpsDT());
        values.put(FormsTable.COLUMN_GPSACC, form.getGpsAcc());
        values.put(FormsTable.COLUMN_DEVICETAGID, form.getDevicetagID());
        values.put(FormsTable.COLUMN_DEVICEID, form.getDeviceID());
        values.put(FormsTable.COLUMN_APPVERSION, form.getAppversion());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FormsTable.TABLE_NAME,
                FormsTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }


    public Long addTSC(StaffingContract tsc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(StaffingTable.COLUMN_UID, tsc.get_UID());
        values.put(StaffingTable.COLUMN_UUID, tsc.get_UUID());
        values.put(StaffingTable.COLUMN_SYSDATE, tsc.getSysdate());
        values.put(StaffingTable.COLUMN_USERNAME, tsc.getUserName());
        values.put(StaffingTable.COLUMN_SERIALNO, tsc.getSerialno());
        values.put(StaffingTable.COLUMN_DISTRICT_CODE, tsc.getDistrictCode());
        values.put(StaffingTable.COLUMN_DISTRICT_TYPE, tsc.getDistrictType());
        values.put(StaffingTable.COLUMN_TEHSIL_CODE, tsc.getTehsilCode());
        values.put(StaffingTable.COLUMN_UC_CODE, tsc.getUcCode());
        values.put(StaffingTable.COLUMN_HF_CODE, tsc.getHfCode());
        values.put(StaffingTable.COLUMN_SC2, tsc.getsC2());
        values.put(StaffingTable.COLUMN_DEVICEID, tsc.getDeviceID());
        values.put(StaffingTable.COLUMN_DEVICETAGID, tsc.getDevicetagID());
        values.put(StaffingTable.COLUMN_STATUS, tsc.getStatus());
        values.put(StaffingTable.COLUMN_SYNCED, tsc.getSynced());
        values.put(StaffingTable.COLUMN_SYNCED_DATE, tsc.getSynced_date());
        values.put(StaffingTable.COLUMN_APPVERSION, tsc.getAppversion());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                StaffingContract.StaffingTable.TABLE_NAME,
                StaffingContract.StaffingTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }


    public Long addPSC(Patients psc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PatientsTable.COLUMN_UID, psc.get_UID());
        values.put(PatientsTable.COLUMN_UUID, psc.get_UUID());
        values.put(PatientsTable.COLUMN_SYSDATE, psc.getSysdate());
        values.put(PatientsTable.COLUMN_USERNAME, psc.getUsername());
        values.put(PatientsTable.COLUMN_SERIALNO, psc.getSerialno());
        values.put(PatientsTable.COLUMN_SG, psc.getsG());
        values.put(PatientsTable.COLUMN_DEVICEID, psc.getDeviceID());
        values.put(PatientsTable.COLUMN_DEVICETAGID, psc.getDevicetagID());
        values.put(PatientsTable.COLUMN_STATUS, psc.getStatus());
        values.put(PatientsTable.COLUMN_SYNCED, psc.getSynced());
        values.put(PatientsTable.COLUMN_SYNCED_DATE, psc.getSynced_date());
        values.put(PatientsTable.COLUMN_APPVERSION, psc.getAppversion());
        values.put(PatientsTable.COLUMN_PROVINCE, psc.getProvince());
        values.put(PatientsTable.COLUMN_PROVINCE_CODE, psc.getProvinceCode());
        values.put(PatientsTable.COLUMN_DISTRICT, psc.getDistrict());
        values.put(PatientsTable.COLUMN_DISTRICT_CODE, psc.getDistrictCode());
        values.put(PatientsTable.COLUMN_TEHSIL, psc.getTehsil());
        values.put(PatientsTable.COLUMN_TEHSIL_CODE, psc.getTehsilCode());
        values.put(PatientsTable.COLUMN_UC, psc.getUc());
        values.put(PatientsTable.COLUMN_UC_CODE, psc.getUcCode());
        values.put(PatientsTable.COLUMN_HF, psc.getHf());
        values.put(PatientsTable.COLUMN_HF_CODE, psc.getHfCode());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                PatientsContract.PatientsTable.TABLE_NAME,
                PatientsContract.PatientsTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }


    public Forms isDataExists(String id) {
        SQLiteDatabase db = getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;

// New value for one column
        String[] columns = {
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_ISTATUS,

        };

// Which row to update, based on the ID
        String selection = FormsTable.COLUMN_UID + " = ? AND "
                + FormsTable.COLUMN_ISTATUS + " = ?";
        String[] selectionArgs = new String[]{id, "1"};

        Forms allform = new Forms();
        try {
            c = db.query(FormsTable.TABLE_NAME, //Table to query
                    columns,                    //columns to return
                    selection,                  //columns for the WHERE clause
                    selectionArgs,              //The values for the WHERE clause
                    null,                       //group the rows
                    null,                       //filter by row groups
                    null);                   // The sort order

            while (c.moveToNext()) {
                allform.set_UID(c.getString(c.getColumnIndex(FormsTable.COLUMN_UID)));
                allform.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allform;
    }


    //FORMS  UPDATE SYNCED TABLES
    public void updateSyncedForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SYNCED, true);
        values.put(FormsTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormsTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    //Staffing
    public void updateSyncedStaffing(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(StaffingTable.COLUMN_SYNCED, true);
        values.put(StaffingTable.COLUMN_SYNCED_DATE, new Date().toString());
        String where = StaffingTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};
        int count = db.update(
                StaffingTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    //Staffing
    public void updateSyncedPatients(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(PatientsTable.COLUMN_SYNCED, true);
        values.put(PatientsTable.COLUMN_SYNCED_DATE, new Date().toString());
        String where = PatientsTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};
        int count = db.update(
                PatientsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    public int updatesEntryLogColumn(String column, String value, String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = TableContracts.EntryLogTable._ID + " =? ";
        String[] selectionArgs = {id};

        return db.update(TableContracts.EntryLogTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    public int updatePassword(String hashedPassword) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(UsersContract.UsersTable.COLUMN_PASSWORD, hashedPassword);
        values.put(UsersContract.UsersTable.COLUMN_ISNEW_USER, "");

        String selection = UsersContract.UsersTable.COLUMN_USERNAME + " =? ";
        String[] selectionArgs = {MainApp.user.getUserName()};

        return db.update(UsersContract.UsersTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    public int updateFormID() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_UID, MainApp.form.get_UID());

// Which row to update, based on the ID
        String selection = FormsTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(MainApp.form.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }


    public Collection<Forms> getAllForms() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_USERNAME,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_PROVINCE,
                FormsTable.COLUMN_PROVINCE_CODE,
                FormsTable.COLUMN_DISTRICT,
                FormsTable.COLUMN_DISTRICT_CODE,
                FormsTable.COLUMN_TEHSIL,
                FormsTable.COLUMN_TEHSIL_CODE,
                FormsTable.COLUMN_UC,
                FormsTable.COLUMN_UC_CODE,
                FormsTable.COLUMN_HF,
                FormsTable.COLUMN_HF_CODE,
                FormsTable.COLUMN_SA,
                FormsTable.COLUMN_SB,
                FormsTable.COLUMN_SC,
                FormsTable.COLUMN_SD,
                FormsTable.COLUMN_SE,
                FormsTable.COLUMN_SF,
                FormsTable.COLUMN_SG,
                FormsTable.COLUMN_SH,
                FormsTable.COLUMN_SI,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_ISTATUS96x,
                FormsTable.COLUMN_ENDINGDATETIME,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Forms> allFC = new ArrayList<Forms>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Forms fc = new Forms();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }


    public Collection<Forms> checkFormExist() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_USERNAME,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_PROVINCE,
                FormsTable.COLUMN_PROVINCE_CODE,
                FormsTable.COLUMN_DISTRICT,
                FormsTable.COLUMN_DISTRICT_CODE,
                FormsTable.COLUMN_TEHSIL,
                FormsTable.COLUMN_TEHSIL_CODE,
                FormsTable.COLUMN_UC,
                FormsTable.COLUMN_UC_CODE,
                FormsTable.COLUMN_HF,
                FormsTable.COLUMN_HF_CODE,
                FormsTable.COLUMN_SA,
                FormsTable.COLUMN_SB,
                FormsTable.COLUMN_SC,
                FormsTable.COLUMN_SD,
                FormsTable.COLUMN_SE,
                FormsTable.COLUMN_SF,
                FormsTable.COLUMN_SG,
                FormsTable.COLUMN_SH,
                FormsTable.COLUMN_SI,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_ISTATUS96x,
                FormsTable.COLUMN_ENDINGDATETIME,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Forms> allFC = new ArrayList<Forms>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Forms fc = new Forms();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }


    //FORMS  COLLECT UNSYNCED TABLES
    public JSONArray getUnsyncedForms() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause;
        whereClause = FormsTable.COLUMN_SYNCED + " ='' AND " +
                FormsTable.COLUMN_ISTATUS + "= '1'";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = FormsTable.COLUMN_ID + " ASC";
        JSONArray allForms = new JSONArray();
        c = db.query(
                FormsTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            /** WorkManager Upload
             /*form fc = new form();
             allFC.add(fc.Hydrate(c));*/
            Log.d(TAG, "getUnsyncedForms: " + c.getCount());
            Forms form = new Forms();
            allForms.put(form.Hydrate(c).toJSONObject());
        }
        db.close();
        Log.d(TAG, "getUnsyncedForms: " + allForms.toString().length());
        Log.d(TAG, "getUnsyncedForms: " + allForms);
        return allForms;
    }


    public Collection<Forms> getUnsyncedForms(int formType) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_USERNAME,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_PROVINCE,
                FormsTable.COLUMN_PROVINCE_CODE,
                FormsTable.COLUMN_DISTRICT,
                FormsTable.COLUMN_DISTRICT_CODE,
                FormsTable.COLUMN_TEHSIL,
                FormsTable.COLUMN_TEHSIL_CODE,
                FormsTable.COLUMN_UC,
                FormsTable.COLUMN_UC_CODE,
                FormsTable.COLUMN_HF,
                FormsTable.COLUMN_HF_CODE,
                FormsTable.COLUMN_SA,
                FormsTable.COLUMN_SB,
                FormsTable.COLUMN_SC,
                FormsTable.COLUMN_SD,
                FormsTable.COLUMN_SE,
                FormsTable.COLUMN_SF,
                FormsTable.COLUMN_SG,
                FormsTable.COLUMN_SH,
                FormsTable.COLUMN_SI,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_ISTATUS96x,
                FormsTable.COLUMN_ENDINGDATETIME,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION
        };

        String whereClause;
        whereClause = FormsTable.COLUMN_SYNCED + " is null AND " + FormsTable.COLUMN_ISTATUS + "=?";

        String[] whereArgs = new String[]{"1"};

        String groupBy = null;
        String having = null;

        String orderBy = FormsTable.COLUMN_ID + " ASC";

        Collection<Forms> allFC = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Forms fc = new Forms();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    //Staffing
    public JSONArray getUnsyncedStaffing() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause;
        whereClause = StaffingTable.COLUMN_SYNCED + " ='' AND " +
                StaffingTable.COLUMN_STATUS + "= '1'";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = StaffingTable.COLUMN_ID + " ASC";
        JSONArray all = new JSONArray();
        c = db.query(
                StaffingTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            Log.d(TAG, "getUnsyncedStaffing: " + c.getCount());
            StaffingContract staf = new StaffingContract();
            all.put(staf.hydrate(c).toJSONObject());
        }
        db.close();
        Log.d(TAG, "getUnsyncedStaffing: " + all.toString().length());
        Log.d(TAG, "getUnsyncedStaffing: " + all);
        return all;
    }

    //Staffing
    public JSONArray getUnsyncedPatients() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause;
        whereClause = PatientsTable.COLUMN_SYNCED + " ='' AND " +
                PatientsTable.COLUMN_STATUS + "= '1'";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = PatientsTable.COLUMN_ID + " ASC";
        JSONArray all = new JSONArray();
        c = db.query(
                PatientsTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            Log.d(TAG, "getUnsyncedPatients: " + c.getCount());
            Patients patients = new Patients();
            all.put(patients.Hydrate(c).toJSONObject());
        }
        db.close();
        Log.d(TAG, "getUnsyncedPatients: " + all.toString().length());
        Log.d(TAG, "getUnsyncedPatients: " + all);
        return all;
    }

    //ENTRYLOG
    public JSONArray getUnsyncedEntryLog() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause;
        whereClause = TableContracts.EntryLogTable.COLUMN_SYNCED + " = '' ";

        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = TableContracts.EntryLogTable.COLUMN_ID + " ASC";

        JSONArray all = new JSONArray();
        c = db.query(
                TableContracts.EntryLogTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            Log.d(TAG, "getUnsyncedEntryLog: " + c.getCount());
            EntryLog entryLog = new EntryLog();
            all.put(entryLog.Hydrate(c).toJSONObject());
        }
        Log.d(TAG, "getUnsyncedEntryLog: " + all.toString().length());
        Log.d(TAG, "getUnsyncedEntryLog: " + all);
        return all;
    }


    public Collection<Forms> getTodayForms() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_SYSDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Forms> allFC = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Forms fc = new Forms();
                fc.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
/*
                fc.setA3(c.getString(c.getColumnIndex(FormsTable.COLUMN_A3)));
*/
                fc.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                fc.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allFC.add(fc);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }


    public Collection<Forms> getHfs() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_HF_CODE,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_HF_CODE + " Like ? ";
        String[] whereArgs = new String[]{/*"%" + spDateT.substring(0, 8).trim() + "%"*/};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Forms> allFC = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Forms fc = new Forms();
                fc.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
/*
                fc.setA12(c.getString(c.getColumnIndex(FormsTable.COLUMN_A12)));
*/
                fc.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                fc.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allFC.add(fc);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }


    public Collection<Forms> getTodayForms(String sysdate) {

        // String sysdate =  spDateT.substring(0, 8).trim()
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_HF,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_SYSDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + sysdate + " %"};
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Forms> allFC = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Forms fc = new Forms();
                fc.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                fc.set_UID(c.getString(c.getColumnIndex(FormsTable.COLUMN_UID)));
                fc.setSysdate(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYSDATE)));
                fc.setHf(c.getString(c.getColumnIndex(FormsTable.COLUMN_HF)));
                fc.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                fc.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allFC.add(fc);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }


    public int updateEnding() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_ISTATUS, MainApp.form.getIstatus());
        values.put(FormsTable.COLUMN_ISTATUS96x, MainApp.form.getIstatus96x());
//        values.put(FormsTable.COLUMN_SE, MainApp.form.getsE());
//        values.put(FormsTable.COLUMN_STATUS, MainApp.form.getStatus());
        values.put(FormsTable.COLUMN_ENDINGDATETIME, MainApp.form.getEndingdatetime());


// Which row to update, based on the ID
        String selection = FormsTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.form.get_ID())};

        int count = db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;
    }


    //Generic update FormColumn
    public int updatesFormColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = FormsTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.form.get_ID())};

        return db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    //Generic update TSCColumn
    public int updatesTSCColumn(StaffingContract tsc, String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = StaffingContract.StaffingTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(tsc.get_ID())};

        return db.update(StaffingContract.StaffingTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    //Generic update PSCColumn
    public int updatesPSCColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = PatientsContract.PatientsTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.psc.get_ID())};

        return db.update(PatientsContract.PatientsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    public int getStaffingsByUUID(String UUID) {
        String countQuery = "SELECT  * FROM " + StaffingTable.TABLE_NAME + " WHERE " + StaffingTable.COLUMN_UUID + " = '" + UUID + "' AND " + StaffingTable.COLUMN_STATUS + " = '1'";
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    public int getPatientsByUUID(String UUID) {
        String countQuery = "SELECT  * FROM " + PatientsTable.TABLE_NAME + " WHERE " + PatientsTable.COLUMN_UUID + " = '" + UUID + "' AND " + PatientsTable.COLUMN_STATUS + " = '1'";
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    // ANDROID DATABASE MANAGER
    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase(DATABASE_PASSWORD);
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }



    public ArrayList<Forms> getUnclosedForms() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_HF,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SYNCED,
        };
        String whereClause = FormsTable.COLUMN_ISTATUS + " != '1'";
        String[] whereArgs = null;
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;
        String orderBy = FormsTable.COLUMN_ID + " ASC";
        ArrayList<Forms> allFC = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Forms fc = new Forms();
                fc.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                fc.set_UID(c.getString(c.getColumnIndex(FormsTable.COLUMN_UID)));
                fc.setSysdate(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYSDATE)));
                fc.setHf(c.getString(c.getColumnIndex(FormsTable.COLUMN_HF)));
                fc.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                fc.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allFC.add(fc);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }


    /*public List<StaffingContract> getAllStaff(String uuid, String uid) {
        List<StaffingContract> posts = new ArrayList<>();

        // SELECT * FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s LEFT INNER JOIN %s ON %s.%s = %s.%s",
                        StaffingContract.StaffingTable,
                        FormsTable,
                        StaffingTable, uuid,
                        FormsTable, uid);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    FormsContract form = new FormsContract();
                    cursor.getString(cursor.getColumnIndex(uid));

                    StaffingContract newPost = new StaffingContract();
                    cursor.getString(cursor.getColumnIndex(uuid));
                    newPost. = newUser;
                    posts.add(newPost);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return posts;
    }*/


    public boolean CheckHFs(String hfCode) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        Cursor mCursor = db.rawQuery("SELECT "
                + FormsTable.COLUMN_SB + ", "
                + FormsTable.COLUMN_SC + ", "
                + FormsTable.COLUMN_SD + ", "
                + FormsTable.COLUMN_SE + ", "
                + FormsTable.COLUMN_SF + ", "
                + FormsTable.COLUMN_SG + ", "
                + FormsTable.COLUMN_SH + ", "
                + FormsTable.COLUMN_SI

                + " FROM " + FormsTable.TABLE_NAME + " WHERE " + FormsTable.COLUMN_HF_CODE + "=? AND " + FormsTable.COLUMN_ISTATUS + "=?", new String[]{hfCode, "1"});
        if (mCursor != null) {

            if (mCursor.moveToFirst()) {
//                MainApp.DIST_ID = mCursor.getString(mCursor.getColumnIndex(UsersContract.singleUser.DIST_ID));
            }
            return mCursor.getCount() > 0;
        }
        return false;
    }


    //DATA DOWNLOAD TABLES
    //APP VERSION
    public int syncVersionApp(JSONObject VersionList) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(VersionAppContract.VersionAppTable.TABLE_NAME, null, null);
        long count = 0;
        JSONObject jsonObjectCC = ((JSONArray) VersionList.get(VersionAppContract.VersionAppTable.COLUMN_VERSION_PATH)).getJSONObject(0);
        VersionAppContract Vc = new VersionAppContract();
        Vc.Sync(jsonObjectCC);

        ContentValues values = new ContentValues();

        values.put(VersionAppContract.VersionAppTable.COLUMN_PATH_NAME, Vc.getPathname());
        values.put(VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE, Vc.getVersioncode());
        values.put(VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME, Vc.getVersionname());

        count = db.insert(VersionAppContract.VersionAppTable.TABLE_NAME, null, values);
        if (count > 0) count = 1;


        db.close();


        return (int) count;
    }


    //USERS
    public int syncAppUser(JSONArray userList) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(UsersContract.UsersTable.TABLE_NAME, null, null);
        int insertCount = 0;
        for (int i = 0; i < userList.length(); i++) {

            JSONObject jsonObjectUser = userList.getJSONObject(i);

            Users user = new Users();
            user.sync(jsonObjectUser);
            ContentValues values = new ContentValues();

            values.put(UsersContract.UsersTable.COLUMN_USERNAME, user.getUserName());
            values.put(UsersContract.UsersTable.COLUMN_PASSWORD, user.getPassword());
            values.put(UsersContract.UsersTable.COLUMN_FULLNAME, user.getFullname());
            values.put(UsersContract.UsersTable.COLUMN_ENABLED, user.getEnabled());
            values.put(UsersContract.UsersTable.COLUMN_ISNEW_USER, user.getNewUser());
            values.put(UsersContract.UsersTable.COLUMN_PWD_EXPIRY, user.getPwdExpiry());
            values.put(UsersContract.UsersTable.COLUMN_DESIGNATION, user.getDesignation());
            values.put(UsersContract.UsersTable.COLUMN_DIST_ID, user.getDist_id());
            long rowID = db.insert(UsersContract.UsersTable.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }
        db.close();
        return insertCount;
    }


    //HEALTH FACILITY
    public int syncHealthFacility(JSONArray healthfacilities) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(HFContract.singleHF.TABLE_NAME, null, null);
        int insertCount = 0;

        for (int i = 0; i < healthfacilities.length(); i++) {
            JSONObject json = healthfacilities.getJSONObject(i);
            HFContract hf = new HFContract();
            hf.Sync(json);
            ContentValues values = new ContentValues();

            values.put(HFContract.singleHF.COLUMN_PRO_ID, hf.getPro_id());
            values.put(HFContract.singleHF.COLUMN_PROVINCE, hf.getProvince());
            values.put(HFContract.singleHF.COLUMN_DIST_ID, hf.getDist_id());
            values.put(HFContract.singleHF.COLUMN_DISTRICT, hf.getDistrict());
            values.put(HFContract.singleHF.COLUMN_TEHSIL_ID, hf.getTehsil_id());
            values.put(HFContract.singleHF.COLUMN_TEHSIL, hf.getTehsil());
//            values.put(TableHealthFacilities.COLUMN_UC_CODE, hf.getUcCode());
//            values.put(TableHealthFacilities.COLUMN_UC_NAME, hf.getUcName());
            values.put(HFContract.singleHF.COLUMN_HFCODE, hf.getHfcode());
            values.put(HFContract.singleHF.COLUMN_HF_NAME, hf.getHf_name());

            long rowID = db.insert(HFContract.singleHF.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }
        db.close();
        return insertCount;
    }


}