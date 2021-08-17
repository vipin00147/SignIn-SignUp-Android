package com.example.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class signin extends Fragment {
    private TextInputLayout email, password;
    private Button signin;
    private TextView dont_have_an_account;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        signin = view.findViewById(R.id.Signin);
        dont_have_an_account = view.findViewById(R.id.dont_have_an_account);

        dont_have_an_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                signup fragment = new signup();
                transaction.replace(R.id.mainContainer, fragment);
                transaction.commit();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getEditText().getText())){
                    Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password.getEditText().getText())){
                    Toast.makeText(getActivity(), "Select Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Login();
                }
            }
        });
        return view;
    }

    private void Login() {

        try {
            String Email = email.getEditText().getText().toString();
            String Password = password.getEditText().getText().toString();

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myData", Context.MODE_PRIVATE);

            String credential = sharedPreferences.getString(Email+Password+"data",null);

            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("showData",credential);
            edit.commit();

            Bundle bundle = new Bundle();
            bundle.putString("data",Email+Password+"data");

            if(credential.equals(null)){
                Toast.makeText(getActivity(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
            }
            else {
                //setting prefer for user whether the user is active or not.
                SharedPreferences pref = getActivity().getSharedPreferences("userStatus",Context.MODE_PRIVATE);
                SharedPreferences.Editor user = pref.edit();
                user.putBoolean("userStatus",true);
                user.putString("Email",Email);
                user.putString("Password",Password);
                user.commit();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                home fragment = new home();
                fragment.setArguments(bundle);
                transaction.replace(R.id.mainContainer, fragment);
                transaction.commit();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}