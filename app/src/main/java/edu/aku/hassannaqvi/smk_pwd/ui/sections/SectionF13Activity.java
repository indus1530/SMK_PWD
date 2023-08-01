package edu.aku.hassannaqvi.smk_pwd.ui.sections;

import static edu.aku.hassannaqvi.smk_pwd.core.MainApp.form;
import static edu.aku.hassannaqvi.smk_pwd.utils.UtilKt.openSectionMainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.jetbrains.annotations.NotNull;

import edu.aku.hassannaqvi.smk_pwd.R;
import edu.aku.hassannaqvi.smk_pwd.contracts.FormsContract;
import edu.aku.hassannaqvi.smk_pwd.core.DatabaseHelper;
import edu.aku.hassannaqvi.smk_pwd.core.MainApp;
import edu.aku.hassannaqvi.smk_pwd.databinding.ActivitySectionF13Binding;

public class SectionF13Activity extends AppCompatActivity {

    ActivitySectionF13Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_f13);
        bi.setCallback(this);
        setupSkips();
    }


    private void setupSkips() {
        radioGroupListener(bi.fm01, bi.fm01b, bi.fldGrpF1302);
        radioGroupListener(bi.fm02, bi.fm02b, bi.fldGrpF1303);
    }


    public void radioGroupListener(@NotNull RadioGroup rg, RadioButton rb, ViewGroup vg) {
        rg.setOnCheckedChangeListener((radioGroup, i) -> {
            Clear.clearAllFields(vg);
            vg.setVisibility(View.VISIBLE);
            if (i == rb.getId()) {
                vg.setVisibility(View.GONE);
            }
        });
    }


    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SF, form.sFtoString());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "SORRY! Failed to update DB", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void SaveDraft() {

        form.setFm01(bi.fm01a.isChecked() ? "1"
                : bi.fm01b.isChecked() ? "2"
                : "-1");

        form.setFm02(bi.fm02a.isChecked() ? "1"
                : bi.fm02b.isChecked() ? "2"
                : "-1");

        form.setFm03(bi.fm03a.isChecked() ? "1"
                : bi.fm03b.isChecked() ? "2"
                : "-1");

        form.setFm04(bi.fm04a.isChecked() ? "1"
                : bi.fm04b.isChecked() ? "2"
                : "-1");

        form.setFm05(bi.fm05a.isChecked() ? "1"
                : bi.fm05b.isChecked() ? "2"
                : "-1");

        form.setFm06(bi.fm06a.isChecked() ? "1"
                : bi.fm06b.isChecked() ? "2"
                : "-1");

        form.setFm07(bi.fm07a.isChecked() ? "1"
                : bi.fm07b.isChecked() ? "2"
                : "-1");

        form.setFm08(bi.fm08a.isChecked() ? "1"
                : bi.fm08b.isChecked() ? "2"
                : "-1");

        /*try {
            JSONObject json_merge = JSONUtils.mergeJSONObjects(new JSONObject(MainApp.fc.getsF()), json);

            MainApp.fc.setsF(String.valueOf(json_merge));

        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }


    public void BtnContinue(View view) {
        if (!formValidation()) return;
        SaveDraft();
        if (UpdateDB()) {
            finish();
            startActivity(new Intent(this, SectionF14Activity.class));
        }
    }


    public void BtnEnd(View view) {
        openSectionMainActivity(this, "F");
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Press Not Allowed", Toast.LENGTH_SHORT).show();
    }


}