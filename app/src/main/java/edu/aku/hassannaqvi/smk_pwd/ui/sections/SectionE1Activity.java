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

import edu.aku.hassannaqvi.smk_pwd.R;
import edu.aku.hassannaqvi.smk_pwd.contracts.FormsContract;
import edu.aku.hassannaqvi.smk_pwd.core.DatabaseHelper;
import edu.aku.hassannaqvi.smk_pwd.core.MainApp;
import edu.aku.hassannaqvi.smk_pwd.databinding.ActivitySectionE1Binding;

import static edu.aku.hassannaqvi.smk_pwd.core.MainApp.form;

public class SectionE1Activity extends AppCompatActivity {

    ActivitySectionE1Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_e1);
        bi.setCallback(this);
        setupSkips();
    }


    private void setupSkips() {
        rgListener(bi.ea10, bi.ea10b, bi.cvea11);
    }


    public void rgListener(@NotNull RadioGroup rg, RadioButton rb, ViewGroup vg) {
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
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SE, form.sEtoString());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "SORRY! Failed to update DB", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void SaveDraft() {

        form.setEa01(bi.ea01a.isChecked() ? "1"
                : bi.ea01b.isChecked() ? "2"
                : bi.ea01c.isChecked() ? "3"
                : bi.ea01d.isChecked() ? "4"
                : bi.ea01e.isChecked() ? "5"
                : bi.ea01f.isChecked() ? "6"
                : bi.ea0196.isChecked() ? "96"
                : "-1");

        form.setEa0196x(bi.ea0196x.getText().toString().trim().isEmpty() ? "-1" : bi.ea0196x.getText().toString());

        form.setEa02(bi.ea02a.isChecked() ? "1"
                : bi.ea02b.isChecked() ? "2"
                : "-1");

        form.setEa03(bi.ea03a.isChecked() ? "1"
                : bi.ea0398.isChecked() ? "98"
                : bi.ea0396.isChecked() ? "96"
                : "-1");

        form.setEa0396x(bi.ea0396x.getText().toString().trim().isEmpty() ? "-1" : bi.ea0396x.getText().toString());

        form.setEa04(bi.ea04a.isChecked() ? "1"
                : bi.ea04b.isChecked() ? "2"
                : bi.ea04c.isChecked() ? "3"
                : bi.ea04d.isChecked() ? "4"
                : bi.ea04e.isChecked() ? "5"
                : bi.ea04f.isChecked() ? "6"
                : bi.ea04g.isChecked() ? "7"
                : "-1");

        form.setEa05d(bi.ea05d.getText().toString().trim().isEmpty() ? "-1" : bi.ea05d.getText().toString());
        form.setEa05m(bi.ea05m.getText().toString().trim().isEmpty() ? "-1" : bi.ea05m.getText().toString());

        form.setEa06(bi.ea06a.isChecked() ? "1"
                : bi.ea06b.isChecked() ? "2"
                : bi.ea06c.isChecked() ? "3"
                : bi.ea06d.isChecked() ? "4"
                : bi.ea0696.isChecked() ? "96"
                : "-1");

        form.setEa0696x(bi.ea0696x.getText().toString().trim().isEmpty() ? "-1" : bi.ea0696x.getText().toString());

        form.setEa07(bi.ea07a.isChecked() ? "1"
                : bi.ea07b.isChecked() ? "2"
                : bi.ea07c.isChecked() ? "3"
                : bi.ea0796.isChecked() ? "96"
                : "-1");

        form.setEa0796x(bi.ea0796x.getText().toString().trim().isEmpty() ? "-1" : bi.ea0796x.getText().toString());

        form.setEa08(bi.ea08a.isChecked() ? "1"
                : bi.ea08b.isChecked() ? "2"
                : bi.ea08c.isChecked() ? "3"
                : bi.ea08d.isChecked() ? "4"
                : "-1");

        form.setEa09d(bi.ea09d.getText().toString().trim().isEmpty() ? "-1" : bi.ea09d.getText().toString());
        form.setEa09m(bi.ea09m.getText().toString().trim().isEmpty() ? "-1" : bi.ea09m.getText().toString());

        form.setEa10(bi.ea10a.isChecked() ? "1"
                : bi.ea10b.isChecked() ? "2"
                : "-1");

        form.setEa11(bi.ea11a.isChecked() ? "1"
                : bi.ea11b.isChecked() ? "2"
                : bi.ea11c.isChecked() ? "3"
                : "-1");

        form.setEa12( bi.ea12a.isChecked() ? "1"
                : bi.ea12b.isChecked() ? "2"
                : bi.ea12c.isChecked() ? "3"
                :  "-1");

        form.setEa13( bi.ea13a.isChecked() ? "1"
                : bi.ea13b.isChecked() ? "2"
                :  "-1");

        form.setEa14(bi.ea14a.isChecked() ? "1"
                : bi.ea14b.isChecked() ? "2"
                : bi.ea14c.isChecked() ? "3"
                : bi.ea14d.isChecked() ? "4"
                : bi.ea14e.isChecked() ? "5"
                : "-1");

    }


    public void BtnContinue() {
        if (!formValidation()) return;
        SaveDraft();
        if (UpdateDB()) {
            finish();
            startActivity(new Intent(this, SectionE2Activity.class));
        }
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

}
