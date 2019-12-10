package com.example.familymap.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.familymap.Models.User_Model;
import com.example.familymap.R;
import com.example.familymap.Requests.Login_Request;

public class LoginFragment extends Fragment {

    private EditText host;
    private EditText port;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText username;
    private RadioButton male;
    private RadioButton female;
    private Button signIn;
    private Button registerB;

    public LoginFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);

        password = view.findViewById(R.id.PasswordEditText);
        username = view.findViewById(R.id.UsernameEditText);
        host = view.findViewById(R.id.ServerHostEditText);
        port = view.findViewById(R.id.PortEditText);
        firstName = view.findViewById(R.id.FirstNameEditText);
        lastName = view.findViewById(R.id.LastNameEditText);
        email = view.findViewById(R.id.EmailEditText);
        male = view.findViewById(R.id.MaleRadioButton);
        female = view.findViewById(R.id.FemaleRadioButton);
        signIn = view.findViewById(R.id.SignInButton);
        registerB = view.findViewById(R.id.RegisterButton);

        signIn.setEnabled(false);
        registerB.setEnabled(false);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(host.getText().toString().equals("") || port.getText().toString().equals("") || username.getText().toString().equals("") || password.getText().toString().equals("")){
                    signIn.setEnabled(false);
                } else {
                    signIn.setEnabled(true);
                }
                if(host.getText().toString().equals("") || port.getText().toString().equals("") || username.getText().toString().equals("") || password.getText().toString().equals("") || firstName.getText().toString().equals("") || lastName.getText().toString().equals("") || email.getText().toString().equals("")){
                    registerB.setEnabled(false);
                } else {
                    registerB.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        host.addTextChangedListener(textWatcher);
        port.addTextChangedListener(textWatcher);
        firstName.addTextChangedListener(textWatcher);
        lastName.addTextChangedListener(textWatcher);
        email.addTextChangedListener(textWatcher);
        male.addTextChangedListener(textWatcher);
        female.addTextChangedListener(textWatcher);

        signIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String hostSubmit = host.getText().toString();
                String portSubmit = port.getText().toString();

                ServerConnection connection = ServerConnection.getConnection();
                connection.setHost(hostSubmit);
                connection.setPort(Integer.valueOf(portSubmit));

                String usernameSubmit = username.getText().toString();
                String passwordSubmit = password.getText().toString();

                Login_Request loginRequest = new Login_Request();
                loginRequest.setUserName(usernameSubmit);
                loginRequest.setPassword(passwordSubmit);

                User_Model user = new User_Model(loginRequest);

                SignIn signIn = new SignIn();
                signIn.execute(user);

                System.out.println("got here");
            }
        });

        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hostSubmit = host.getText().toString();
                String portSubmit = port.getText().toString();

                ServerConnection connection = ServerConnection.getConnection();
                connection.setHost(hostSubmit);
                connection.setPort(Integer.valueOf(portSubmit));

                String usernameSubmit = username.getText().toString();
                String passwordSubmit = password.getText().toString();
                String emailSubmit = email.getText().toString();
                String firstNameSubmit = firstName.getText().toString();
                String lastNameSubmit = lastName.getText().toString();
                String genderSubmit = "";
                if(male.isActivated()){
                    genderSubmit = "m";
                } else {
                    genderSubmit = "f";
                }
                User_Model user = new User_Model(null, usernameSubmit, passwordSubmit, emailSubmit, firstNameSubmit, lastNameSubmit, genderSubmit);

                Register register = new Register();
                register.execute(user);

                registerB.setEnabled(true);
                Toast.makeText(getContext(), "registering", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private class SignIn extends AsyncTask<User_Model, Void, Object[]> {
        @Override
        protected void onPostExecute(Object[] objects) {

            Toast.makeText(getContext(), objects[1].toString(), Toast.LENGTH_SHORT).show();
            FragmentManager  fragmentManager = getActivity().getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.mainFrame);
            Fragment mapFragment = new MapsFragment();
            fragmentManager.beginTransaction().replace(R.id.mainFrame, mapFragment).commit();
        }

        @Override
        protected Object[] doInBackground(User_Model... user_models) {
            return ServerConnection.Login(user_models[0]);
        }
    }
    private class Register extends AsyncTask<User_Model, Void, Object[]>{
        @Override
        protected void onPostExecute(Object[] objects) {
            Toast.makeText(getContext(), objects[1].toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Object[] doInBackground(User_Model... user_models) {
            return ServerConnection.Register(user_models[0]);
        }
    }
}
