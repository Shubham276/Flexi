package india.com.flexi.Activity;

;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;


import india.com.flexi.Fragment.ConfirmFrag;
import india.com.flexi.MyDbAdapter;
import india.com.flexi.R;

import india.com.flexi.Utils.AndroidUtils;

public class RechargeActivity extends AppCompatActivity {

    private Button recharge;
    EditText name,mobile,amount;
    private Spinner txtOperatorName, txtCircleName;
    Spinner operator;
    private String operators[]={"Select an operator","AIRTEL","IDEA","VODAFONE","RELIANCE","JIO","TELENOR","DOCOMO"};
    String tname,tmobile,tamount,toperator,ttype;
    MyDbAdapter myDbAdapter;
    RadioGroup radioGroup;
    LinearLayout Layout;
    TableLayout tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        Layout=(LinearLayout) findViewById(R.id.mainLay);
        tablayout=(TableLayout) findViewById(R.id.tabMain);
        name= (EditText) findViewById(R.id.userName);
        txtOperatorName = (Spinner) findViewById(R.id.operatorSelect);
        txtCircleName = (Spinner) findViewById(R.id.circleSelect);
        mobile= (EditText) findViewById(R.id.phone_edit_txt);
        operator= (Spinner) findViewById(R.id.operatorSelect);


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,operators);
        operator.setAdapter(adapter);

        amount= (EditText) findViewById(R.id.userAmount);
        recharge= (Button) findViewById(R.id.rechargeBtn);
        radioGroup=(RadioGroup) findViewById(R.id.radio_grp_prepost);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                AndroidUtils.hideKeyBoard(RechargeActivity.this);
                int id=radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton= (RadioButton) findViewById(id);
                ttype = radioButton.getText().toString();

            }
        });

        myDbAdapter=new MyDbAdapter(this);

        operator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toperator=operators[i];
                AndroidUtils.hideKeyBoard(RechargeActivity.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidUtils.hideKeyBoard(RechargeActivity.this);

                tname = name.getText().toString();
                tmobile = mobile.getText().toString();
                tamount = amount.getText().toString();
                if (tname.isEmpty() || tmobile.isEmpty() || tamount.isEmpty() || ttype.isEmpty() || toperator.isEmpty()) {

                    Toast.makeText(RechargeActivity.this, "please fill all of the fields correctly", Toast.LENGTH_SHORT).show();
                } else {
                    if(toperator.equals("Select an operator"))
                    {
                        Toast.makeText(RechargeActivity.this, "please select an operator", Toast.LENGTH_SHORT).show();
                    }
                    else if (tmobile.length() > 10 || tmobile.length() < 10) {
                        mobile.setError("Invalid Number");
                    } else
                        {
                            long i=myDbAdapter.insertData(tname, tmobile,toperator,tamount,ttype);
                            if(i<0) {
                                Toast.makeText(RechargeActivity.this, "Something Wrong with the data", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                tablayout.setVisibility(View.GONE);
                                Layout.setVisibility(View.VISIBLE);

                                Bundle bundle=new Bundle();
                                bundle.putString("mob",tmobile);
                                bundle.putString("name",tname);

                                ConfirmFrag frag=new ConfirmFrag();
                                frag.setArguments(bundle);
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.add(R.id.mainLay,frag, "ConfirmFrag");
                                fragmentTransaction.addToBackStack("ConfirmFrag");
                                fragmentTransaction.commit();
                            }
                        }

                }
            }
        });
    }



}
