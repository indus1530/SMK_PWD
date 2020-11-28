package edu.aku.hassannaqvi.smk_pwd.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import edu.aku.hassannaqvi.smk_pwd.R;
import edu.aku.hassannaqvi.smk_pwd.contracts.FormsContract;
import edu.aku.hassannaqvi.smk_pwd.core.DatabaseHelper;
import edu.aku.hassannaqvi.smk_pwd.core.MainApp;
import edu.aku.hassannaqvi.smk_pwd.databinding.ActivitySectionF12Binding;
import edu.aku.hassannaqvi.smk_pwd.utils.JSONUtils;

import static edu.aku.hassannaqvi.smk_pwd.core.MainApp.fc;
import static edu.aku.hassannaqvi.smk_pwd.utils.UtilKt.openSectionMainActivity;

public class SectionF12Activity extends AppCompatActivity {

    ActivitySectionF12Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_f12);
        bi.setCallback(this);
        setupSkips();
    }

    private void setupSkips() {
    }


    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SF, fc.getsF());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();
        json.put("fl01", bi.fl01a.isChecked() ? "1"
                : bi.fl01b.isChecked() ? "2"
                : "-1");

        json.put("fl02", bi.fl02a.isChecked() ? "1"
                : bi.fl02b.isChecked() ? "2"
                : "-1");

        json.put("fl03", bi.fl03a.isChecked() ? "1"
                : bi.fl03b.isChecked() ? "2"
                : bi.fl03c.isChecked() ? "3"
                : bi.fl03d.isChecked() ? "4"
                : bi.fl03e.isChecked() ? "5"
                : bi.fl03f.isChecked() ? "6"
                : "-1");

        json.put("fl04", bi.fl04a.isChecked() ? "1"
                : bi.fl04b.isChecked() ? "2"
                : bi.fl04c.isChecked() ? "3"
                : bi.fl04d.isChecked() ? "4"
                : bi.fl04e.isChecked() ? "5"
                : bi.fl04f.isChecked() ? "6"
                : "-1");

        json.put("fl05", bi.fl05a.isChecked() ? "1"
                : bi.fl05b.isChecked() ? "2"
                : "-1");

        json.put("fl06", bi.fl06a.isChecked() ? "1"
                : bi.fl06b.isChecked() ? "2"
                : "-1");


        try {
            JSONObject json_merge = JSONUtils.mergeJSONObjects(new JSONObject(MainApp.fc.getsF()), json);

            MainApp.fc.setsF(String.valueOf(json_merge));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }


    public void BtnContinue() {
        if (!formValidation()) return;
        try {
            SaveDraft();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            finish();
            startActivity(new Intent(this, SectionF13Activity.class));
        }
    }


    public void BtnEnd() {
        openSectionMainActivity(this, "F");
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Press Not Allowed", Toast.LENGTH_SHORT).show();
    }
}