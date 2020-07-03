package com.example.wipet.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class SignInFragment extends Fragment implements View.OnClickListener, TextWatcher {
    private View view;
    private TextInputLayout etlUsername, etlPassword;
    private TextInputEditText etUsername, etPassword;
    private Button btnSignin;
    private LinearLayout btnGoogle, btnFacebook;
    private TextView btnSignUp;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        init();
        return view;
    }

    private void init() {
        btnSignUp = view.findViewById(R.id.tv_btn_signup_signin);
        btnGoogle = view.findViewById(R.id.ll_btn_google_signin);
        btnFacebook = view.findViewById(R.id.ll_btn_facebook_signin);
        btnSignin = view.findViewById(R.id.btn_signin_signin);
        etlUsername = view.findViewById(R.id.etl_username_signin);
        etUsername = view.findViewById(R.id.et_username_signin);
        etPassword = view.findViewById(R.id.et_password_signin);
        etlPassword = view.findViewById(R.id.etl_password_signin);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        btnSignUp.setOnClickListener(this);
        btnSignin.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);

        etUsername.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
    }



    private void login() {
        dialog.setMessage(getString(R.string.logging_in));
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Api.LOGIN, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    //save to shared preference
                    SharedPreferences userInfo = getContext().getSharedPreferences(GlobalVar.SP_USER_INFO, 0);
                    SharedPreferences.Editor editor = userInfo.edit();
                    editor.putString(GlobalVar.VAR_TOKEN, object.getString("token"));
                    editor.putInt(GlobalVar.VAR_ID_USER, user.getInt("id"));
                    editor.putString(GlobalVar.VAR_FULLNAME, user.getString("name"));
                    editor.putString(GlobalVar.VAR_USERNAME, user.getString("username"));
                    editor.putString(GlobalVar.VAR_EMAIL, user.getString("email"));
                    editor.putBoolean(GlobalVar.VAR_IS_LOGGED_IN, true);
                    editor.apply();

                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                GlobalFunc.showToast(e.getMessage() , getContext(),GlobalVar.TIME_LONG_TOAST);
            }
        },error -> {
            GlobalFunc.showToast(error.getMessage() , getContext(),GlobalVar.TIME_LONG_TOAST);
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", etUsername.getText().toString().trim());
                map.put("password", etPassword.getText().toString().trim());
                return map;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);

    }

    private boolean validate() {
        if (etUsername.getText().toString().isEmpty()){
            etlUsername.setErrorEnabled(true);
            etlUsername.setError(getString(R.string.this_field_is_required));
            return false;
        }
        if (etPassword.getText().toString().isEmpty()){
            etlPassword.setErrorEnabled(true);
            etlPassword.setError(getString(R.string.this_field_is_required));
            return false;
        }
        if (etUsername.getText().length() <= 7){
            etlUsername.setErrorEnabled(true);
            etlUsername.setError(getString(R.string.this_field_min_char));
            return false;
        }
        if (etPassword.getText().length() <= 7){
            etlPassword.setErrorEnabled(true);
            etlPassword.setError(getString(R.string.this_field_min_char));
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_btn_signup_signin:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_auth_activity, new SignUpFragment())
                        .commit();
                break;

            case R.id.btn_signin_signin:
                if (validate()){
                    login();
                }

        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!etUsername.getText().toString().isEmpty()){
            etlUsername.setErrorEnabled(false);
        }
        if (!etPassword.getText().toString().isEmpty()){
            etlPassword.setErrorEnabled(false);
        }
        if (etUsername.getText().length() > 7 ){
            etlUsername.setErrorEnabled(false);
        }
        if (etPassword.getText().length() > 7 ){
            etlPassword.setErrorEnabled(false);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}