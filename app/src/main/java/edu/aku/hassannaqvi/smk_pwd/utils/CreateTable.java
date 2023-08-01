package edu.aku.hassannaqvi.smk_pwd.utils;

import static edu.aku.hassannaqvi.smk_pwd.core.MainApp.PROJECT_NAME;

import edu.aku.hassannaqvi.smk_pwd.contracts.DistrictContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.FormsContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.HFContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.PatientsContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.TehsilsContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.UCsContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.UsersContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.VersionAppContract;

public final class CreateTable {
    public static final String DATABASE_NAME = PROJECT_NAME + ".db";
    public static final String DB_NAME = PROJECT_NAME + "_copy.db";
    public static final int DATABASE_VERSION = 1;

    public static final String SQL_CREATE_FORMS = "CREATE TABLE "
            + FormsContract.FormsTable.TABLE_NAME + "("
            + FormsContract.FormsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FormsContract.FormsTable.COLUMN_PROJECT_NAME + " TEXT,"
            + FormsContract.FormsTable.COLUMN_UID + " TEXT,"
            + FormsContract.FormsTable.COLUMN_USERNAME + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SYSDATE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_ISTATUS + " TEXT,"
            + FormsContract.FormsTable.COLUMN_ISTATUS96x + " TEXT,"
            + FormsContract.FormsTable.COLUMN_ENDINGDATETIME + " TEXT,"
            + FormsContract.FormsTable.COLUMN_GPSLAT + " TEXT,"
            + FormsContract.FormsTable.COLUMN_GPSLNG + " TEXT,"
            + FormsContract.FormsTable.COLUMN_GPSDATE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_GPSACC + " TEXT,"
            + FormsContract.FormsTable.COLUMN_DEVICEID + " TEXT,"
            + FormsContract.FormsTable.COLUMN_DEVICETAGID + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SYNCED + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SYNCED_DATE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_APPVERSION + " TEXT,"
            + FormsContract.FormsTable.COLUMN_PROVINCE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_PROVINCE_CODE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_DISTRICT + " TEXT,"
            + FormsContract.FormsTable.COLUMN_DISTRICT_CODE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_TEHSIL + " TEXT,"
            + FormsContract.FormsTable.COLUMN_TEHSIL_CODE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_UC + " TEXT,"
            + FormsContract.FormsTable.COLUMN_UC_CODE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_HF + " TEXT,"
            + FormsContract.FormsTable.COLUMN_HF_CODE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SA + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SB + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SC + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SD + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SE + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SF + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SG + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SH + " TEXT,"
            + FormsContract.FormsTable.COLUMN_SI + " TEXT"
            + " );";


    public static final String SQL_CREATE_PSCONTRACT = "CREATE TABLE " + PatientsContract.PatientsTable.TABLE_NAME + "("
            + PatientsContract.PatientsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PatientsContract.PatientsTable.COLUMN_UID + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_UUID + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_SYSDATE + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_USERNAME + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_SERIALNO + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_PROVINCE + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_PROVINCE_CODE + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_DISTRICT + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_DISTRICT_CODE + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_TEHSIL + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_TEHSIL_CODE + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_UC + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_UC_CODE + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_HF + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_HF_CODE + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_DEVICEID + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_DEVICETAGID + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_SYNCED + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_SYNCED_DATE + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_STATUS + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_APPVERSION + " TEXT,"
            + PatientsContract.PatientsTable.COLUMN_SG + " TEXT"
            + " );";


    public static final String SQL_CREATE_USERS = "CREATE TABLE " + UsersContract.singleUser.TABLE_NAME + "("
            + UsersContract.singleUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UsersContract.singleUser.ROW_USERNAME + " TEXT,"
            + UsersContract.singleUser.ROW_PASSWORD + " TEXT,"
            + UsersContract.singleUser.DESIGNATION + " TEXT,"
            + UsersContract.singleUser.DIST_ID + " TEXT"
            + " );";


    public static final String SQL_CREATE_VERSIONAPP = "CREATE TABLE " + VersionAppContract.VersionAppTable.TABLE_NAME + " (" +
            VersionAppContract.VersionAppTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE + " TEXT, " +
            VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME + " TEXT, " +
            VersionAppContract.VersionAppTable.COLUMN_PATH_NAME + " TEXT " +
            ");";


    public static final String SQL_CREATE_DISTRICTS = "CREATE TABLE " + DistrictContract.singleDistrict.TABLE_NAME + "("
            + DistrictContract.singleDistrict._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DistrictContract.singleDistrict.COLUMN_DISTRICT_CODE + " TEXT,"
            + DistrictContract.singleDistrict.COLUMN_DISTRICT_NAME + " TEXT,"
            + DistrictContract.singleDistrict.COLUMN_DISTRICT_TYPE + " TEXT );";


    public static final String SQL_CREATE_TEHSILS = "CREATE TABLE " + TehsilsContract.singleTehsil.TABLE_NAME + "("
            + TehsilsContract.singleTehsil._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TehsilsContract.singleTehsil.COLUMN_TEHSIL_CODE + " TEXT,"
            + TehsilsContract.singleTehsil.COLUMN_TEHSIL_NAME + " TEXT,"
            + TehsilsContract.singleTehsil.COLUMN_DISTRICT_CODE + " TEXT );";


    public static final String SQL_CREATE_UCS = "CREATE TABLE " + UCsContract.singleUCs.TABLE_NAME + "("
            + UCsContract.singleUCs._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UCsContract.singleUCs.COLUMN_UC_CODE + " TEXT,"
            + UCsContract.singleUCs.COLUMN_TEHSIL_CODE + " TEXT,"
            + UCsContract.singleUCs.COLUMN_UC_NAME + " TEXT );";


    public static final String SQL_CREATE_HF = "CREATE TABLE " + HFContract.singleHF.TABLE_NAME + "("
            + HFContract.singleHF._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + HFContract.singleHF.COLUMN_PROVINCE + " TEXT,"
            + HFContract.singleHF.COLUMN_PRO_ID + " TEXT,"
            + HFContract.singleHF.COLUMN_DISTRICT + " TEXT,"
            + HFContract.singleHF.COLUMN_DIST_ID + " TEXT,"
            + HFContract.singleHF.COLUMN_TEHSIL + " TEXT,"
            + HFContract.singleHF.COLUMN_TEHSIL_ID + " TEXT,"
            + HFContract.singleHF.COLUMN_UC_NAME + " TEXT,"
            + HFContract.singleHF.COLUMN_UC_ID + " TEXT,"
            + HFContract.singleHF.COLUMN_HF_NAME + " TEXT,"
            + HFContract.singleHF.COLUMN_HFCODE + " TEXT );";

}
