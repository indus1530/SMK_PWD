package edu.aku.hassannaqvi.smk_pwd.ui.sections;

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
import org.json.JSONException;

import edu.aku.hassannaqvi.smk_pwd.R;
import edu.aku.hassannaqvi.smk_pwd.contracts.FormsContract;
import edu.aku.hassannaqvi.smk_pwd.core.DatabaseHelper;
import edu.aku.hassannaqvi.smk_pwd.core.MainApp;
import edu.aku.hassannaqvi.smk_pwd.databinding.ActivitySectionC2Binding;

import static edu.aku.hassannaqvi.smk_pwd.core.MainApp.form;
import static edu.aku.hassannaqvi.smk_pwd.utils.UtilKt.openSectionMainActivity;

public class SectionC2Activity extends AppCompatActivity {

    ActivitySectionC2Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_c2);
        bi.setCallback(this);
        setupSkips();

    }


    private void setupSkips() {
        radioGroupListener(bi.cb02, bi.cb02e, bi.cvcb03);
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
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SC, form.sCtoString());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Failed to update DB", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void SaveDraft() throws JSONException {

        form.setCb01(bi.cb01a.isChecked() ? "1"
                : bi.cb01b.isChecked() ? "2"
                : bi.cb01c.isChecked() ? "3"
                : bi.cb01d.isChecked() ? "4"
                : "-1");

        form.setCb02(bi.cb02a.isChecked() ? "1"
                : bi.cb02b.isChecked() ? "2"
                : bi.cb02c.isChecked() ? "3"
                : bi.cb02d.isChecked() ? "4"
                : bi.cb02e.isChecked() ? "5"
                : "-1");

        form.setCb03(bi.cb03a.isChecked() ? "1"
                : bi.cb03b.isChecked() ? "2"
                : "-1");

        form.setCb04(bi.cb04a.isChecked() ? "1"
                : bi.cb04b.isChecked() ? "2"
                : "-1");

        form.setCb05(bi.cb05a.isChecked() ? "1"
                : bi.cb05b.isChecked() ? "2"
                : bi.cb05c.isChecked() ? "3"
                : "-1");

        /*try {
            JSONObject json_merge = JSONUtils.mergeJSONObjects(new JSONObject(form.getsC()), json);

            form.setsC(String.valueOf(json_merge));
        }
        catch (JSONException e){
            e.printStackTrace();
        }*/
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
            startActivity(new Intent(this, SectionC3Activity.class));
        }
    }


    public void BtnEnd() {
        openSectionMainActivity(this, "C");
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Press Not Allowed", Toast.LENGTH_SHORT).show();
    }

}