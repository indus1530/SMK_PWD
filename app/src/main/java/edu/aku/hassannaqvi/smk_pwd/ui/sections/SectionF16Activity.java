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
import edu.aku.hassannaqvi.smk_pwd.databinding.ActivitySectionF16Binding;
import edu.aku.hassannaqvi.smk_pwd.ui.other.SectionMainActivity;

import static edu.aku.hassannaqvi.smk_pwd.utils.UtilKt.openSectionMainActivity;

public class SectionF16Activity extends AppCompatActivity {

    ActivitySectionF16Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_f16);
        bi.setCallback(this);
        setupSkips();
    }

    private void setupSkips() {
        radioGroupListener(bi.fp01a, bi.fp01ab, new ViewGroup[]{bi.cvfp01b, bi.cvfp01c, bi.cvfp01d});
        radioGroupListener(bi.fp01c, bi.fp01cb, new ViewGroup[]{bi.cvfp01d});
        radioGroupListener(bi.fp02a, bi.fp02ab, new ViewGroup[]{bi.cvfp02b, bi.cvfp02c, bi.cvfp02d});
        radioGroupListener(bi.fp02c, bi.fp02cb, new ViewGroup[]{bi.cvfp02d});
        radioGroupListener(bi.fp03a, bi.fp03ab, new ViewGroup[]{bi.cvfp03b, bi.cvfp03c, bi.cvfp03d});
        radioGroupListener(bi.fp03c, bi.fp03cb, new ViewGroup[]{bi.cvfp03d});
        radioGroupListener(bi.fp04a, bi.fp04ab, new ViewGroup[]{bi.cvfp04b, bi.cvfp04c, bi.cvfp04d});
        radioGroupListener(bi.fp04c, bi.fp04cb, new ViewGroup[]{bi.cvfp04d});
        radioGroupListener(bi.fp05a, bi.fp05ab, new ViewGroup[]{bi.cvfp05b, bi.cvfp05c, bi.cvfp05d});
        radioGroupListener(bi.fp05c, bi.fp05cb, new ViewGroup[]{bi.cvfp05d});
    }


    public void radioGroupListener(@NotNull RadioGroup rg, RadioButton rb, ViewGroup[] vgArray) {
        rg.setOnCheckedChangeListener((radioGroup, i) -> {
            for (ViewGroup vg : vgArray) {
                Clear.clearAllFields(vg);
                vg.setVisibility(View.VISIBLE);
                if (i == rb.getId()) vg.setVisibility(View.GONE);
            }
        });
    }


    private boolean UpdateDB() {
       /* DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SF, fc.getsF());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }


    private void SaveDraft() throws JSONException {

        /*JSONObject json = new JSONObject();
        json.put("fp01a", bi.fp01aa.isChecked() ? "1"
                : bi.fp01ab.isChecked() ? "2"
                : "-1");

        json.put("fp01b", bi.fp01ba.isChecked() ? "1"
                : bi.fp01bb.isChecked() ? "2"
                : "-1");

        json.put("fp01c", bi.fp01ca.isChecked() ? "1"
                : bi.fp01cb.isChecked() ? "2"
                : "-1");

        json.put("fp01dd", bi.fp01dd.getText().toString());

        json.put("fp01dm", bi.fp01dm.getText().toString());

        json.put("fp02a", bi.fp02aa.isChecked() ? "1"
                : bi.fp02ab.isChecked() ? "2"
                : "-1");

        json.put("fp02b", bi.fp02ba.isChecked() ? "1"
                : bi.fp02bb.isChecked() ? "2"
                : "-1");

        json.put("fp02c", bi.fp02ca.isChecked() ? "1"
                : bi.fp02cb.isChecked() ? "2"
                : "-1");

        json.put("fp02dd", bi.fp02dd.getText().toString());

        json.put("fp02dm", bi.fp02dm.getText().toString());

        json.put("fp03a", bi.fp03aa.isChecked() ? "1"
                : bi.fp03ab.isChecked() ? "2"
                : "-1");

        json.put("fp03b", bi.fp03ba.isChecked() ? "1"
                : bi.fp03bb.isChecked() ? "2"
                : "-1");

        json.put("fp03c", bi.fp03ca.isChecked() ? "1"
                : bi.fp03cb.isChecked() ? "2"
                : "-1");

        json.put("fp03dd", bi.fp03dd.getText().toString());

        json.put("fp03dm", bi.fp03dm.getText().toString());

        json.put("fp04a", bi.fp04aa.isChecked() ? "1"
                : bi.fp04ab.isChecked() ? "2"
                : "-1");

        json.put("fp04b", bi.fp04ba.isChecked() ? "1"
                : bi.fp04bb.isChecked() ? "2"
                : "-1");

        json.put("fp04c", bi.fp04ca.isChecked() ? "1"
                : bi.fp04cb.isChecked() ? "2"
                : "-1");

        json.put("fp04dd", bi.fp04dd.getText().toString());

        json.put("fp04dm", bi.fp04dm.getText().toString());

        json.put("fp05a", bi.fp05aa.isChecked() ? "1"
                : bi.fp05ab.isChecked() ? "2"
                : "-1");

        json.put("fp05b", bi.fp05ba.isChecked() ? "1"
                : bi.fp05bb.isChecked() ? "2"
                : "-1");

        json.put("fp05c", bi.fp05ca.isChecked() ? "1"
                : bi.fp05cb.isChecked() ? "2"
                : "-1");

        json.put("fp05dd", bi.fp05dd.getText().toString());

        json.put("fp05dm", bi.fp05dm.getText().toString());


        try {
            JSONObject json_merge = JSONUtils.mergeJSONObjects(new JSONObject(MainApp.fc.getsF()), json);

            MainApp.fc.setsF(String.valueOf(json_merge));

        } catch (JSONException e) {
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
            startActivity(new Intent(this, SectionMainActivity.class));
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