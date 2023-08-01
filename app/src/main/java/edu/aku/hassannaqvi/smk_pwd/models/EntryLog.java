package edu.aku.hassannaqvi.smk_pwd.models;

import static edu.aku.hassannaqvi.smk_pwd.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.smk_pwd.core.MainApp._EMPTY_;

import android.database.Cursor;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.smk_pwd.BR;
import edu.aku.hassannaqvi.smk_pwd.contracts.TableContracts;
import edu.aku.hassannaqvi.smk_pwd.core.MainApp;


public class EntryLog extends BaseObservable implements Observable {

    private final String TAG = "EntryLog";
    private final transient PropertyChangeRegistry propertyChangeRegistry = new PropertyChangeRegistry();

    private String id = _EMPTY_;
    private String uid = _EMPTY_;
    // APP VARIABLES
    private String projectName = PROJECT_NAME;
    private String uuid = _EMPTY_;
    private String userName = _EMPTY_;
    private String sysDate = _EMPTY_;
    private String entryDate = _EMPTY_;         //
    private String psuCode = _EMPTY_;
    private String hhid = _EMPTY_;
    private String appver = _EMPTY_;            //
    private String iStatus = _EMPTY_;
    private String iStatus96x = _EMPTY_;        //
    private String entryType = _EMPTY_;
    private String deviceId = _EMPTY_;
    private String synced = _EMPTY_;
    private String syncDate = _EMPTY_;

    public EntryLog() {


    }


    public void populateMetaForm() {

        setProjectName(PROJECT_NAME);
        setUuid(MainApp.form.get_UID());  // not applicable in Form table
        setUserName(MainApp.user.getUserName());
        setSysDate(MainApp.form.getSysdate());
        setEntryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setPsuCode(MainApp.form.getHfCode());
        setHhid(MainApp.form.getHf());
        setiStatus(MainApp.form.getIstatus());
        setiStatus96x(MainApp.form.getIstatus96x());
        setAppver(MainApp.appInfo.getAppVersion());
        setEntryType("PWDForm");
        setDeviceId(MainApp.deviceId);

    }

    /*public void populateMetaForm() {

        setProjectName(PROJECT_NAME);
        setUuid(MainApp.Form.getUid());  // not applicable in Form table
        setUserName(MainApp.user.getUserName());
        setSysDate(MainApp.Form.getSysDate());
        setEntryDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        setPsuCode(MainApp.Form.getCluster());
        //setHhid(MainApp.Form.getHhid());
        setiStatus(MainApp.Form.getiStatus());
        setiStatus96x(MainApp.Form.getiStatus96x());
        setAppver(MainApp.appInfo.getAppVersion());
        setEntryType("Form");
        setDeviceId(MainApp.deviceid);

    }*/


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    @Bindable
    public String getPsuCode() {
        return psuCode;
    }

    public void setPsuCode(String psuCode) {
        this.psuCode = psuCode;
        notifyPropertyChanged(BR.psuCode);
    }

    @Bindable
    public String getHhid() {
        return hhid;
    }

    public void setHhid(String hhid) {
        this.hhid = hhid;
        notifyPropertyChanged(BR.hhid);
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSysDate() {
        return sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }
/*

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }
*/

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getAppver() {
        return appver;
    }

    public void setAppver(String appver) {
        this.appver = appver;
    }

    public String getiStatus() {
        return iStatus;
    }

    public void setiStatus(String iStatus) {
        this.iStatus = iStatus;
    }

    public String getiStatus96x() {
        return iStatus96x;
    }

    public void setiStatus96x(String iStatus96x) {
        this.iStatus96x = iStatus96x;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(String syncDate) {
        this.syncDate = syncDate;
    }

    public EntryLog Hydrate(Cursor cursor) throws JSONException {
        this.id = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_ID));
        this.uid = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_UID));
        this.uuid = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_UUID));
        this.projectName = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_PROJECT_NAME));
        this.psuCode = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_PSU_CODE));
        this.hhid = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_HHID));
        this.userName = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_USERNAME));
        this.sysDate = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_SYSDATE));
        this.entryDate = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_ENTRY_DATE));
        this.entryType = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_ENTRY_TYPE));
        this.deviceId = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_DEVICEID));
        this.appver = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_APPVERSION));
        this.iStatus = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_ISTATUS));
        this.iStatus96x = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_ISTATUS96x));
        this.synced = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_SYNCED));
        this.syncDate = cursor.getString(cursor.getColumnIndexOrThrow(TableContracts.EntryLogTable.COLUMN_SYNC_DATE));

        return this;
    }


    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(TableContracts.EntryLogTable.COLUMN_ID, this.id);
        json.put(TableContracts.EntryLogTable.COLUMN_UID, this.uid);
        json.put(TableContracts.EntryLogTable.COLUMN_UUID, this.uuid);
        json.put(TableContracts.EntryLogTable.COLUMN_PROJECT_NAME, this.projectName);
        json.put(TableContracts.EntryLogTable.COLUMN_PSU_CODE, this.psuCode);
        json.put(TableContracts.EntryLogTable.COLUMN_HHID, this.hhid);
        json.put(TableContracts.EntryLogTable.COLUMN_USERNAME, this.userName);
        json.put(TableContracts.EntryLogTable.COLUMN_SYSDATE, this.sysDate);
        json.put(TableContracts.EntryLogTable.COLUMN_ENTRY_DATE, this.entryDate);
        json.put(TableContracts.EntryLogTable.COLUMN_ENTRY_TYPE, this.entryType);
        json.put(TableContracts.EntryLogTable.COLUMN_DEVICEID, this.deviceId);
        json.put(TableContracts.EntryLogTable.COLUMN_ISTATUS, this.iStatus);
        json.put(TableContracts.EntryLogTable.COLUMN_ISTATUS96x, this.iStatus96x);
        json.put(TableContracts.EntryLogTable.COLUMN_SYNCED, this.synced);
        json.put(TableContracts.EntryLogTable.COLUMN_SYNC_DATE, this.syncDate);
        json.put(TableContracts.EntryLogTable.COLUMN_APPVERSION, this.appver);
        return json;
    }


}
