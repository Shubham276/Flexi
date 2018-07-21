package india.com.flexi.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import india.com.flexi.Activity.RechargeActivity;
import india.com.flexi.MyDbAdapter;
import india.com.flexi.R;


public class AnotherRecharge extends Fragment implements View.OnClickListener {

    Button ao;
    MyDbAdapter myDbAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_another_recharge, container, false);
        myDbAdapter=new MyDbAdapter(getContext());

        // Inflate the layout for this fragment
        TextView success=v.findViewById(R.id.txtSuccess);
        ao=v.findViewById(R.id.aobtn);

        Bundle bundle=getArguments();
        if(!bundle.isEmpty())
        {
            String first=bundle.getString("nam","default");
            String second=bundle.getString("mobi","default");

            String data=myDbAdapter.mobileData(first,second);
            if(data!=null)
            success.setText("Successfully Recharge on Number:\n"+data);
        }
        ao.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.aobtn:
                Intent intent=new Intent(getActivity(),RechargeActivity.class);
                startActivity(intent);
                break;
        }

    }
}
