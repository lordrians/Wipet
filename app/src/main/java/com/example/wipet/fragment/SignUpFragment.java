package com.example.wipet.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wipet.Api;
import com.example.wipet.GlobalFunc;
import com.example.wipet.GlobalVar;
import com.example.wipet.R;
import com.example.wipet.activity.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView btnSignIn;
    private TextInputLayout etlUsername, etlEmail, etlPassword, etlConfPassword, etlFullname;
    private TextInputEditText etUsername, etEmail, etPassword, etConfPassword, etFullname;
    private Button btnSignup;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_sign_up, container, false);
         init();
         return view;
    }

    private void init() {
        btnSignIn = view.findViewById(R.id.tv_signin_signupfrag);
        btnSignup = view.findViewById(R.id.btn_signup_signup);
        etlUsername = view.findViewById(R.id.etl_username_signup);
        etlEmail = view.findViewById(R.id.etl_email_signup);
        etlPassword = view.findViewById(R.id.etl_password_signup);
        etlConfPassword = view.findViewById(R.id.etl_conf_password_signup);
        etlFullname = view.findViewById(R.id.etl_fullname_signup);
        etUsername = view.findViewById(R.id.et_username_signup);
        etEmail = view.findViewById(R.id.et_email_signup);
        etPassword = view.findViewById(R.id.et_password_signup);
        etConfPassword = view.findViewById(R.id.et_conf_password_signup);
        etFullname = view.findViewById(R.id.et_fullname_signup);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        btnSignup.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etUsername.getText().toString().isEmpty()){
                    etlUsername.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etFullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etFullname.getText().toString().isEmpty()){
                    etlFullname.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!etEmail.getText().toString().isEmpty()){
                    etlEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPassword.getText().toString().length() > 7){
                    etlPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etConfPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Objects.equals(etConfPassword.getText(), etPassword.getText())){
                    etlConfPassword.setErrorEnabled(false);
                }
                if (Objects.equals(etPassword.getText().toString(),etConfPassword.getText().toString())){
                    etlConfPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_signin_signupfrag:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_auth_activity, new SignInFragment())
                        .commit();
                break;
            case R.id.btn_signup_signup:
                if (validate()){
                    register();
                }
        }

    }

    private void register() {
        dialog.setMessage(getString(R.string.registering));
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Api.REGISTER, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    //save to shared preference
                    SharedPreferences UserInfo = Objects.requireNonNull(getActivity()).getApplicationContext().getSharedPreferences(GlobalVar.SP_USER_INFO, 0);
                    SharedPreferences.Editor editor = UserInfo.edit();
                    editor.putString(GlobalVar.VAR_TOKEN, object.getString("token"));
                    editor.putString(GlobalVar.VAR_ID_USER, user.getString("id"));
                    editor.putString(GlobalVar.VAR_FULLNAME, user.getString("name"));
                    editor.putString(GlobalVar.VAR_USERNAME, user.getString("username"));
                    editor.putString(GlobalVar.VAR_EMAIL, user.getString("email"));
                    editor.putBoolean(GlobalVar.VAR_IS_LOGGED_IN, true);
                    editor.apply();

                    startActivity(new Intent(getContext(),MainActivity.class));
                    getActivity().finish();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                GlobalFunc.showToast(e.getMessage() + "(A)", getContext(),GlobalVar.TIME_LONG_TOAST);
            } catch (Exception e){
                GlobalFunc.showToast(e.getMessage()+ "(B)", getContext(),GlobalVar.TIME_LONG_TOAST);
            }
            dialog.dismiss();
        }, error -> {
            error.printStackTrace();
            GlobalFunc.showToast(error.getMessage()+ "(C)", getContext(),GlobalVar.TIME_LONG_TOAST);
            dialog.dismiss();
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", etUsername.getText().toString().trim());
                map.put("email", etEmail.getText().toString().trim());
                map.put("password", etPassword.getText().toString().trim());
                return map;
            }
        };
        Volley.newRequestQueue(Objects.requireNonNull(getActivity()).getApplicationContext()).add(request);

    }

    private boolean validate() {
        if (etUsername.getText().toString().isEmpty()){
            etlUsername.setErrorEnabled(true);
            etlUsername.setError(getString(R.string.this_field_is_required));
            return false;
        }
        if (etFullname.getText().toString().isEmpty()){
            etlFullname.setErrorEnabled(true);
            etlFullname.setError(getString(R.string.this_field_is_required));
            return false;
        }
        if (etEmail.getText().toString().isEmpty()){
            etlEmail.setErrorEnabled(true);
            etlEmail.setError(getString(R.string.this_field_is_required));
            return false;
        }
        if (etPassword.getText().toString().isEmpty()){
            etlPassword.setErrorEnabled(true);
            etlPassword.setError(getString(R.string.this_field_is_required));
            return false;
        }
        if (etConfPassword.getText().toString().isEmpty()){
            etlConfPassword.setErrorEnabled(true);
            etlConfPassword.setError(getString(R.string.this_field_is_required));
            return false;
        }
        if (!Objects.equals(etPassword.getText().toString(),etConfPassword.getText().toString())){
            etlConfPassword.setErrorEnabled(true);
            etlConfPassword.setError(getString(R.string.password_doesnt_match));
            return false;
        }
        if (!emailValidation()){
            etlEmail.setErrorEnabled(true);
            etlEmail.setError(getString(R.string.invalid_email_address));
            return false;
        }
        return true;

    }

    private boolean emailValidation() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!etEmail.getText().toString().trim().matches(emailPattern)) {
            return false;
        } else {
            return true;
        }
    }
}