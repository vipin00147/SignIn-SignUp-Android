package com.example.task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class signup extends Fragment {
    private TextInputLayout name,age,email,password;
    private Button signup;
    private TextView alredy_have_account;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private ImageView mcalender;

    private int year,day,month;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);


        //checking user is active or not...
        SharedPreferences pref = getActivity().getSharedPreferences("userStatus",Context.MODE_PRIVATE);


        if(pref.getBoolean("userStatus",false)){
            Log.d("activeStatus","Active");
            Bundle bundle = new Bundle();
            bundle.putString("data", pref.getString("Email", null) + pref.getString("Password", null) + "data");
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            home fragment = new home();
            fragment.setArguments(bundle);
            transaction.replace(R.id.mainContainer, fragment);
            transaction.commit();
        }
        else{
            try {
                Log.d("activeStatus","Not Active");
                name = view.findViewById(R.id.name);
                age = view.findViewById(R.id.age);
                email = view.findViewById(R.id.Email);
                password = view.findViewById(R.id.Password);
                mcalender = view.findViewById(R.id.calender);

                radioSexGroup = (RadioGroup) view.findViewById(R.id.radioSex);

                signup = view.findViewById(R.id.Signup);
                alredy_have_account = view.findViewById(R.id.alredy_have_an_account);

                alredy_have_account.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        signin fragment = new signin();
                        transaction.replace(R.id.mainContainer, fragment);
                        transaction.commit();
                    }
                });

                mcalender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        day = cal.get(Calendar.DAY_OF_MONTH);
                        month = cal.get(Calendar.MONTH);
                        year = cal.get(Calendar.YEAR);

                        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                                age.getEditText().setText(date+"/"+(month+1)+"/"+year);
                            }

                        }, year,month,day);
                        dialog.show();
                    }
                });

                signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int selectedId = radioSexGroup.getCheckedRadioButtonId();
                        radioSexButton = (RadioButton) view.findViewById(selectedId);
                        String gender = "";

                        try {
                            gender = radioSexButton.getText().toString();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        if(TextUtils.isEmpty(name.getEditText().getText())){
                            Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT).show();
                        }
                        else if(gender.equals("")){
                            Toast.makeText(getActivity(), "Select gender", Toast.LENGTH_SHORT).show();
                        }
                        else if(TextUtils.isEmpty(age.getEditText().getText())){
                            Toast.makeText(getActivity(), "Enter Age", Toast.LENGTH_SHORT).show();
                        }
                        else if(TextUtils.isEmpty(email.getEditText().getText())){
                            Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
                        }
                        else if(TextUtils.isEmpty(password.getEditText().getText())){
                            Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            signUp();
                        }

                    }
                });
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        return view;
    }

    private void signUp() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myData", Context.MODE_PRIVATE);
        SharedPreferences.Editor data = sharedPreferences.edit();

        try {
            String Name = name.getEditText().getText().toString();
            String Sex = radioSexButton.getText().toString();
            String Age = age.getEditText().getText().toString();
            String Email = email.getEditText().getText().toString();
            String Password = password.getEditText().getText().toString();

            data.putString(Email + Password + "data", Name + "\n" + Sex + "\n" + Age + "\n" + Email );
            data.commit();

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            signin fragment = new signin();
            transaction.replace(R.id.mainContainer, fragment);
            transaction.commit();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}