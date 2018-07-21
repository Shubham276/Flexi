package india.com.flexi.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

;import india.com.flexi.MyDbAdapter;
import india.com.flexi.R;

public class ConfirmFrag extends Fragment implements View.OnClickListener {
    TextView name, mobile, operator, amount, type;
    Button edit, proceed;
    String bundlesname="PU",bundlesmobi="PU";
    EditText tamount;
    Spinner toperator;
    MyDbAdapter myDbAdapter;
    LinearLayout Layout;
    TableLayout tablayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_confirm, container, false);
        name = v.findViewById(R.id.tname);
        mobile = v.findViewById(R.id.tmobile);
        operator = v.findViewById(R.id.toperator);
        amount = v.findViewById(R.id.tamount);
        type = v.findViewById(R.id.ttype);
        edit = v.findViewById(R.id.editbtn);
        proceed = v.findViewById(R.id.proceedbtn);

        //RecchargeActivity
        tamount=getActivity().findViewById(R.id.userAmount);
        toperator=getActivity().findViewById(R.id.operatorSelect);
        Layout=getActivity().findViewById(R.id.mainLay);
        Layout.setVisibility(View.VISIBLE);
        tablayout= getActivity().findViewById(R.id.tabMain);
        tablayout.setVisibility(View.GONE);


        edit.setOnClickListener(this);
        proceed.setOnClickListener(this);

        myDbAdapter = new MyDbAdapter(getContext());

        //confirmation page information logic

        Bundle bundle = getArguments();
        if (!bundle.isEmpty()) {
            String first = bundle.getString("name", "default");
            String second = bundle.getString("mob", "default");
            String data = myDbAdapter.confirmData(first, second);
            String[] confirmationData = data.split(" ");
            bundlesname=confirmationData[0];
            bundlesmobi=confirmationData[1];

            name.setText(confirmationData[0]);
            mobile.setText(confirmationData[1]);
            operator.setText(confirmationData[2]);
            amount.setText(confirmationData[3]);
            type.setText(confirmationData[4]);
        }


        return v;
    }


    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (view.getId()) {
            case R.id.editbtn:
                int count=myDbAdapter.deleteData(bundlesname,bundlesmobi);
                if(count>0)
                {
                    myDbAdapter.deleteData(bundlesname,bundlesmobi);
                }
                tamount.setText("");
                toperator.setSelection(0);
                tablayout.setVisibility(View.VISIBLE);
                Layout.setVisibility(View.GONE);
                fragmentManager.popBackStack();
                break;
            case R.id.proceedbtn:

                Bundle bundle=new Bundle();
                bundle.putString("mobi",bundlesmobi);
                bundle.putString("nam",bundlesname);
                AnotherRecharge frag=new AnotherRecharge();
                frag.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainLay, frag, "AnotherRecharge");
                fragmentTransaction.addToBackStack("AnotherRecharge");
                fragmentTransaction.commit();
                break;
        }
    }
}


