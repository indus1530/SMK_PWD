package edu.aku.hassannaqvi.smk_pwd.get;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import edu.aku.hassannaqvi.smk_pwd.adapter.SyncListAdapter;
import edu.aku.hassannaqvi.smk_pwd.contracts.HFContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.UsersContract;
import edu.aku.hassannaqvi.smk_pwd.contracts.VersionAppContract;
import edu.aku.hassannaqvi.smk_pwd.core.DatabaseHelper;
import edu.aku.hassannaqvi.smk_pwd.core.MainApp;
import edu.aku.hassannaqvi.smk_pwd.models.SyncModel;

/**
 * Created by ali.azaz on 7/14/2017.
 */

public class GetAllData extends AsyncTask<String, String, String> {

    private HttpURLConnection urlConnection;
    private SyncListAdapter adapter;
    private List<edu.aku.hassannaqvi.smk_pwd.models.SyncModel> list;
    private int position;
    private String TAG = "";
    private final Context mContext;
    private ProgressDialog pd;
    private final String syncClass;


    public GetAllData(Context context, String syncClass) {
        mContext = context;
        this.syncClass = syncClass;
        TAG = "Get" + syncClass;
    }

    public GetAllData(Context context, String syncClass, SyncListAdapter adapter, List<SyncModel> list) {
        mContext = context;
        this.syncClass = syncClass;
        this.adapter = adapter;
        this.list = list;
        TAG = "Get" + syncClass;
        switch (syncClass) {
            case "User":
                position = 0;
                break;
            case "VersionApp":
                position = 1;
                break;
            /*case "Districts":
                position = 2;
                break;
            case "Tehsils":
                position = 3;
                break;
            case "UCs":
                position = 4;
                break;*/
            case "HealthFacilities":
                position = 2;
                break;
        }
        list.get(position).settableName(syncClass);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(mContext);
        pd.setTitle("Syncing " + syncClass);
        pd.setMessage("Getting connected to server...");
//        pd.show();
        list.get(position).setstatus("Getting connected to server...");
        list.get(position).setstatusID(2);
        list.get(position).setmessage("");
        adapter.updatesyncList(list);

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        switch (values[0]) {
            case "User":
                position = 0;
                break;
            case "VersionApp":
                position = 1;
                break;
            /*case "Districts":
                position = 2;
                break;
            case "Tehsils":
                position = 3;
                break;
            case "UCs":
                position = 4;
                break;*/
            case "HealthFacilities":
                position = 2;
                break;
        }
        list.get(position).setstatus("Syncing");
        list.get(position).setstatusID(2);
        list.get(position).setmessage("");
        adapter.updatesyncList(list);
    }

    @Override
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();
        String tableName = "";

        URL url = null;
        try {
            switch (syncClass) {
                case "User":
                    url = new URL(MainApp._HOST_URL + MainApp._SERVER_GET_URL);
                    tableName = UsersContract.singleUser.TABLE_NAME;
                    position = 0;
                    break;
                case "VersionApp":
                    url = new URL(MainApp._UPDATE_URL + VersionAppContract.VersionAppTable._URI);
                    position = 1;
                    break;
                /*case "Districts":
                    url = new URL(MainApp._HOST_URL + MainApp._SERVER_GET_URL);
                    tableName = DistrictContract.singleDistrict.TABLE_NAME;
                    position = 2;
                    break;
                case "Tehsils":
                    url = new URL(MainApp._HOST_URL + MainApp._SERVER_GET_URL);
                    tableName = TehsilsContract.singleTehsil.TABLE_NAME;
                    position = 3;
                    break;
                case "UCs":
                    url = new URL(MainApp._HOST_URL + MainApp._SERVER_GET_URL);
                    tableName = UCsContract.singleUCs.TABLE_NAME;
                    position = 4;
                    break;*/
                case "HealthFacilities":
                    url = new URL(MainApp._HOST_URL + MainApp._SERVER_GET_URL);
                    tableName = HFContract.singleHF.TABLE_NAME;
                    position = 2;
                    break;
            }
            Log.d(TAG, "doInBackground: " + url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(100000 /* milliseconds */);
            urlConnection.setConnectTimeout(150000 /* milliseconds */);
            switch (syncClass) {
                case "User":
                /*case "Districts":
                case "Tehsils":
                case "UCs":*/
                case "HealthFacilities":
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("charset", "utf-8");
                    urlConnection.setUseCaches(false);

                    // Starts the query
                    urlConnection.connect();
                    DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                    JSONObject json = new JSONObject();
                    try {
                        json.put("table", tableName);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    Log.d(TAG, "downloadUrl: " + json);
                    wr.writeBytes(json.toString());
                    wr.flush();
                    wr.close();
                    break;
            }

            Log.d(TAG, "doInBackground: " + urlConnection.getResponseCode());
            publishProgress(syncClass);
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                publishProgress("In Progress");

                String line;
                while ((line = reader.readLine()) != null) {
                    Log.i(TAG, syncClass + " In: " + line);
                    result.append(line);
                }
            }
        } catch (IOException e) {
            return null;
        } finally {
            urlConnection.disconnect();
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        //Do something with the JSON string
        if (result != null) {
            if (result.length() > 0) {
                DatabaseHelper db = new DatabaseHelper(mContext);
                try {
                    JSONArray jsonArray = new JSONArray();

                    switch (syncClass) {
                        case "User":
                            jsonArray = new JSONArray(result);
                            db.syncUser(jsonArray);
                            position = 0;
                            break;
                        case "VersionApp":
                            int insertCount = db.syncVersionApp(new JSONObject(result));
                            if (insertCount == 1) jsonArray.put("1");
                            position = 1;
                            break;
                        /*case "Districts":
                            jsonArray = new JSONArray(result);
                            position = 2;
                            db.syncDistricts(jsonArray);
                            break;
                        case "Tehsils":
                            jsonArray = new JSONArray(result);
                            position = 3;
                            db.syncTehsils(jsonArray);
                            break;
                        case "UCs":
                            jsonArray = new JSONArray(result);
                            position = 4;
                            db.syncUCs(jsonArray);
                            break;*/
                        case "HealthFacilities":
                            jsonArray = new JSONArray(result);
                            db.syncHF(jsonArray);
                            position = 2;
                            break;
                    }

                    pd.setMessage("Received: " + jsonArray.length());
                    list.get(position).setmessage("Received: " + jsonArray.length());
                    list.get(position).setstatus("Successfull");
                    list.get(position).setstatusID(3);
                    adapter.updatesyncList(list);
//                    pd.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                pd.setMessage("Received: " + result.length() + "");
                list.get(position).setmessage("Received: " + result.length() + "");
                list.get(position).setstatus("Successfull");
                list.get(position).setstatusID(3);
                adapter.updatesyncList(list);
//                pd.show();
            }
        } else {
            pd.setTitle("Connection Error");
            pd.setMessage("Server not found!");
            list.get(position).setstatus("Failed");
            list.get(position).setstatusID(1);
            list.get(position).setmessage("Server not found!");
            adapter.updatesyncList(list);
//            pd.show();
        }
    }

}
