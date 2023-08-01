package edu.aku.hassannaqvi.smk_pwd.models

import android.database.Cursor
import edu.aku.hassannaqvi.smk_pwd.contracts.UsersContract
import edu.aku.hassannaqvi.smk_pwd.core.MainApp._EMPTY_
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by hassan.naqvi on 11/30/2016.
 */
class Users {
    var newUser: String = _EMPTY_
    var userID: Long = 0
    var userName: String = _EMPTY_
    var password: String = _EMPTY_
    var fullname: String = _EMPTY_
    var designation: String = _EMPTY_
    var enabled: String = _EMPTY_
    var pwdExpiry: String = _EMPTY_
    var dist_id: String = _EMPTY_

    constructor() {
        // Default Constructor
    }

    constructor(username: String, fullname: String) {
        userName = username
        this.fullname = fullname
    }

    @Throws(JSONException::class)
    fun sync(jsonObject: JSONObject): Users {
        userName = jsonObject.getString(UsersContract.UsersTable.COLUMN_USERNAME)
        password = jsonObject.getString(UsersContract.UsersTable.COLUMN_PASSWORD)
        fullname = jsonObject.getString(UsersContract.UsersTable.COLUMN_FULLNAME)
        designation = jsonObject.getString(UsersContract.UsersTable.COLUMN_DESIGNATION)
        newUser = jsonObject.getString(UsersContract.UsersTable.COLUMN_ISNEW_USER)
        pwdExpiry = jsonObject.getString(UsersContract.UsersTable.COLUMN_PWD_EXPIRY)
        enabled = jsonObject.getString(UsersContract.UsersTable.COLUMN_ENABLED)
        dist_id = jsonObject.getString(UsersContract.UsersTable.COLUMN_DIST_ID)
        return this
    }

    fun hydrate(cursor: Cursor): Users {
        userID = cursor.getLong(cursor.getColumnIndexOrThrow(UsersContract.UsersTable.COLUMN_ID))
        userName =
            cursor.getString(cursor.getColumnIndexOrThrow(UsersContract.UsersTable.COLUMN_USERNAME))
        password =
            cursor.getString(cursor.getColumnIndexOrThrow(UsersContract.UsersTable.COLUMN_PASSWORD))
        fullname =
            cursor.getString(cursor.getColumnIndexOrThrow(UsersContract.UsersTable.COLUMN_FULLNAME))
        designation =
            cursor.getString(cursor.getColumnIndexOrThrow(UsersContract.UsersTable.COLUMN_DESIGNATION))
        enabled =
            cursor.getString(cursor.getColumnIndexOrThrow(UsersContract.UsersTable.COLUMN_ENABLED))
        pwdExpiry =
            cursor.getString(cursor.getColumnIndexOrThrow(UsersContract.UsersTable.COLUMN_PWD_EXPIRY))
        newUser =
            cursor.getString(cursor.getColumnIndexOrThrow(UsersContract.UsersTable.COLUMN_ISNEW_USER))
        dist_id =
            cursor.getString(cursor.getColumnIndexOrThrow(UsersContract.UsersTable.COLUMN_DIST_ID))
        return this
    }


}