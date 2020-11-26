package edu.aku.hassannaqvi.smk_pwd.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.smk_pwd.R;
import edu.aku.hassannaqvi.smk_pwd.contracts.FormsContract;
import edu.aku.hassannaqvi.smk_pwd.core.DatabaseHelper;
import edu.aku.hassannaqvi.smk_pwd.core.MainApp;
import edu.aku.hassannaqvi.smk_pwd.databinding.ActivitySectionGBinding;

import static edu.aku.hassannaqvi.smk_pwd.core.MainApp.fc;

public class SectionGActivity extends AppCompatActivity {

    ActivitySectionGBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_g);
        bi.setCallback(this);
        setupSkips();

    }


    private void setupSkips() {

       /* bi.g01110b.setOnCheckedChangeListener(((radioGroup, i) -> {
            Clear.clearAllFields(bi.llGrpsecg01);
        }));*/
        bi.ga03.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i != bi.ga03b.getId()) {
                Clear.clearAllFields(bi.fldGrpCVga04);
            }
        }));
    }


    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SG, fc.getsG());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void SaveDraft() throws JSONException {


       /* form.setGa01( bi.ga01a.isChecked() ? "1"
                : bi.ga01b.isChecked() ? "2"
                :  "-1");

        form.setGa02d(bi.ga02d.getText().toString());
        form.setGa02m(bi.ga02m.getText().toString());
        form.setGa02y(bi.ga02y.getText().toString());
        form.setGa02h(bi.ga02h.getText().toString());
        form.setGa02mi(bi.ga02mi.getText().toString());
        form.setGa02s(bi.ga02s.getText().toString());
        form.setGa03( bi.ga03a.isChecked() ? "1"
                : bi.ga03b.isChecked() ? "2"
                :  "-1");

        form.setGa04( bi.ga04a.isChecked() ? "1"
                : bi.ga04b.isChecked() ? "2"
                :  "-1");

        form.setGa05( bi.ga05a.isChecked() ? "1"
                : bi.ga05b.isChecked() ? "2"
                :  "-1");

        form.setGa06y(bi.ga06y.getText().toString());
        form.setGa06m(bi.ga06m.getText().toString());
        form.setGa07( bi.ga07a.isChecked() ? "1"
                : bi.ga07b.isChecked() ? "2"
                :  "-1");

        form.setGa08a( bi.ga08aa.isChecked() ? "1"
                : bi.ga08ab.isChecked() ? "2"
                : bi.ga08ac.isChecked() ? "3"
                : bi.ga08ad.isChecked() ? "4"
                : bi.ga08ae.isChecked() ? "5"
                :  "-1");

        form.setGa08b( bi.ga08ba.isChecked() ? "1"
                : bi.ga08bb.isChecked() ? "2"
                : bi.ga08bc.isChecked() ? "3"
                : bi.ga08bd.isChecked() ? "4"
                : bi.ga08be.isChecked() ? "5"
                :  "-1");

        form.setGa08c( bi.ga08ca.isChecked() ? "1"
                : bi.ga08cb.isChecked() ? "2"
                : bi.ga08cc.isChecked() ? "3"
                : bi.ga08cd.isChecked() ? "4"
                : bi.ga08ce.isChecked() ? "5"
                :  "-1");

        form.setGa08d( bi.ga08da.isChecked() ? "1"
                : bi.ga08db.isChecked() ? "2"
                : bi.ga08dc.isChecked() ? "3"
                : bi.ga08dd.isChecked() ? "4"
                : bi.ga08de.isChecked() ? "5"
                :  "-1");

        form.setGa08e( bi.ga08ea.isChecked() ? "1"
                : bi.ga08eb.isChecked() ? "2"
                : bi.ga08ec.isChecked() ? "3"
                : bi.ga08ed.isChecked() ? "4"
                : bi.ga08ee.isChecked() ? "5"
                :  "-1");

        form.setGa08f( bi.ga08fa.isChecked() ? "1"
                : bi.ga08fb.isChecked() ? "2"
                : bi.ga08fc.isChecked() ? "3"
                : bi.ga08fd.isChecked() ? "4"
                : bi.ga08fe.isChecked() ? "5"
                :  "-1");

        form.setGa09( bi.ga09a.isChecked() ? "1"
                : bi.ga09b.isChecked() ? "2"
                : bi.ga09c.isChecked() ? "3"
                :  "-1");

        form.setGa09bx(bi.ga09bx.getText().toString());
        form.setGa09cx(bi.ga09cx.getText().toString());
        form.setGa10( bi.ga10a.isChecked() ? "1"
                : bi.ga10b.isChecked() ? "2"
                : bi.ga10c.isChecked() ? "3"
                :  "-1");

        form.setGa10bx(bi.ga10bx.getText().toString());
        form.setGa10cx(bi.ga10cx.getText().toString());
        form.setGa11( bi.ga11a.isChecked() ? "1"
                : bi.ga11b.isChecked() ? "2"
                :  "-1");

        form.setGa11ax(bi.ga11ax.getText().toString());
        form.setGa11bx(bi.ga11bx.getText().toString());
        form.setGa12( bi.ga12a.isChecked() ? "1"
                : bi.ga12b.isChecked() ? "2"
                :  "-1");

        form.setGa13( bi.ga13a.isChecked() ? "1"
                : bi.ga13b.isChecked() ? "2"
                :  "-1");

        form.setGa14( bi.ga14a.isChecked() ? "1"
                : bi.ga14b.isChecked() ? "2"
                : bi.ga14c.isChecked() ? "3"
                :  "-1");

        form.setGa14ax(bi.ga14ax.getText().toString());
        form.setGa14bx(bi.ga14bx.getText().toString());
        form.setGa14cx(bi.ga14cx.getText().toString());
        form.setGa15( bi.ga15a.isChecked() ? "1"
                : bi.ga15b.isChecked() ? "2"
                : bi.ga15c.isChecked() ? "3"
                : bi.ga15d.isChecked() ? "4"
                : bi.ga15e.isChecked() ? "5"
                : bi.ga15f.isChecked() ? "6"
                : bi.ga1596.isChecked() ? "96"
                :  "-1");

        form.setGa1596x(bi.ga1596x.getText().toString());
        form.setGa16( bi.ga16a.isChecked() ? "1"
                : bi.ga16b.isChecked() ? "2"
                :  "-1");
*/


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
            startActivity(new Intent(this, SectionG2Activity.class));
        }
    }

}
