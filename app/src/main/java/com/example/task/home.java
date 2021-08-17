package com.example.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class home extends Fragment {

    private TextView info;
    private Button logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        info = view.findViewById(R.id.information);
        logout = view.findViewById(R.id.logout);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myData", Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("showData","");

        info.setText(data);

        if(info.getText().equals("Wrong Credentials")){
            logout.setVisibility(View.INVISIBLE);
        }
        else {
            logout.setVisibility(View.VISIBLE);
        }
        Bundle bundle = getArguments();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myData", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();

                try {
                    edit.remove(bundle.getString("data"));
                    edit.commit();

                    //setting current user is not active..
                    SharedPreferences sharedPref = getActivity().getSharedPreferences("userStatus", Context.MODE_PRIVATE);
                    SharedPreferences.Editor user = sharedPref.edit();
                    user.putBoolean("userStatus",false);
                    user.putString("Email",null);
                    user.putString("Password",null);
                    user.commit();

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    signin fragment = new signin();
                    transaction.replace(R.id.mainContainer, fragment);
                    transaction.commit();

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
}