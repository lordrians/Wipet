package com.example.wipet.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.wipet.Api;
import com.example.wipet.GlobalFunc;
import com.example.wipet.GlobalVar;
import com.example.wipet.R;
import com.example.wipet.activity.EditAccountActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {
    private View view;
    private TextView btnEdit, tvName, tvKota, tvProvinsi, tvAbout, tvEmail, tvPhone, tvWarningCv;
    private CircleImageView ivPhoto;
    private Button btnCreate;
    private ImageView ivNotifCv;
    SharedPreferences UserInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        init();
        return view;
    }

    private void init() {
        UserInfo = getActivity().getSharedPreferences(GlobalVar.SP_USER_INFO, 0);

        btnEdit = view.findViewById(R.id.tv_btn_edit_accountfrag);
        tvName = view.findViewById(R.id.tv_name_accountfrag);
        tvKota = view.findViewById(R.id.tv_kota_accountfrag);
        tvProvinsi = view.findViewById(R.id.tv_provinsi_accountfrag);
        tvAbout = view.findViewById(R.id.tv_about_accountfrag);
        tvEmail = view.findViewById(R.id.tv_email_accountfrag);
        tvPhone = view.findViewById(R.id.tv_phone_accountfrag);
        tvWarningCv = view.findViewById(R.id.tv_warning_cv_accountfrag);

        ivNotifCv = view.findViewById(R.id.iv_notif_cv_accountfrag);
        ivPhoto = view.findViewById(R.id.iv_photo_accountfrag);
        btnCreate = view.findViewById(R.id.btn_create_cv_accountfrag);

        checkCV();

        btnEdit.setOnClickListener(v -> startActivity(new Intent(getContext(), EditAccountActivity.class)));
    }

    private void checkCV() {
        Toast.makeText(getContext(),"Fragment",Toast.LENGTH_SHORT).show();
        StringRequest request = new StringRequest(StringRequest.Method.POST, Api.getOwnCv, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    if (object.getBoolean("cv_adoption")){
                        ivNotifCv.setImageResource(R.drawable.ic_ceklis);
                        tvWarningCv.setText(R.string.cv_created);

                    }else {
                        ivNotifCv.setImageResource(R.drawable.ic_warning);
                    }
                    if (!object.getString("name").isEmpty()){
                        tvName.setText(object.getString("name"));
                    }
                    if (!object.getString("nama_kota").isEmpty()){
                        tvKota.setText(object.getString("nama_kota"));
                    }
                    if (!object.getString("name_provinsi").isEmpty()){
                        tvProvinsi.setText(object.getString("name_provinsi"));
                    }
                    if (!object.getString("about").isEmpty()){
                        tvAbout.setText(object.getString("about"));
                    }
                    if (!object.getString("email").isEmpty()){
                        tvEmail.setText(object.getString("email"));
                    }
                    if (!object.getString("phone").isEmpty()){
                        tvPhone.setText(object.getString("phone"));
                    }
                    if (!object.getString("photo").isEmpty()){
                        Glide.with(getContext())
                                .load(Api.DIR_USER_PHOTO + object.getString("photo"))
                                .centerCrop()
                                .into(ivPhoto);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            error.printStackTrace();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return GlobalFunc.getHeaders(getContext());
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
    }


}