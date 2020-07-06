package com.example.wipet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wipet.Api;
import com.example.wipet.GlobalFunc;
import com.example.wipet.R;
import com.example.wipet.adapter.AdoptionAdapter;
import com.example.wipet.object.Adoption;
import com.example.wipet.object.Discussion;
import com.example.wipet.object.User;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class ListAdoptionActivity extends AppCompatActivity {
    private ImageView ivBack, ivSetting;
    private TextInputEditText etSearch;
    private RelativeLayout btnCreate;
    private RecyclerView rvAdoption;
    private ProgressDialog dialog;
    private ArrayList<Adoption> adoptionArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_adoption);
        init();
        
    }

    private void init() {
        ivBack = findViewById(R.id.iv_back_list_adoptoin);
        btnCreate = findViewById(R.id.rl_btn_list_adoption);
        rvAdoption = findViewById(R.id.rv_list_adoption);

        rvAdoption.setHasFixedSize(true);
        rvAdoption.setLayoutManager(new GridLayoutManager(this, GlobalFunc.Utility(getApplicationContext())));

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        loadAdoption();

        btnCreate.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),CreateAdoptionActivity.class));
        });
    }

    private void loadAdoption() {
        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest request = new StringRequest(StringRequest.Method.POST, Api.SHOW_LIST_ADOPTION, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONArray adoptJsonArr = object.getJSONArray("adoption");
                    for (int i = 0; i < adoptJsonArr.length(); i++){
                        JSONObject adoptObj = adoptJsonArr.getJSONObject(i);
                        Adoption adoption = new Adoption();
                        adoption.setId(adoptObj.getInt("id"));
                        adoption.setTitle(adoptObj.getString("title"));
                        adoption.setPrice(adoptObj.getInt("price"));
                        adoption.setStatus(adoptObj.getString("status"));

                        JSONObject categoryObj = adoptObj.getJSONObject("category");
                        adoption.setCategory(categoryObj.getString("name"));

                        JSONArray photoArr = adoptObj.getJSONArray("photo");
                        if (photoArr.length() > 0){
                            ArrayList<String> photoArrayList = new ArrayList<>();
                            for (int j = 0; j < photoArr.length(); j++){
                                JSONObject photo = photoArr.getJSONObject(j);
                                photoArrayList.add(photo.getString("path_photo"));
                            }
                            adoption.setPhoto(photoArrayList);
                        }

                        JSONObject userObj = adoptObj.getJSONObject("user");
                        JSONObject kelurahanObj = userObj.getJSONObject("kelurahan");
                        JSONObject kecamatanObj = kelurahanObj.getJSONObject("kecamatan");
                        JSONObject kotaObj = kecamatanObj.getJSONObject("kota");
                        JSONObject provinsiObj = kotaObj.getJSONObject("provinsi");
                        User user = new User();
                        user.setId(userObj.getInt("id"));
                        user.setUsername(userObj.getString("username"));
                        user.setKelurahan(kelurahanObj.getString("name"));
                        user.setKecamatan(kecamatanObj.getString("name"));
                        user.setKota(kotaObj.getString("name"));
                        user.setProvinsi(provinsiObj.getString("name"));

                        adoption.setUser(user);
                        adoptionArrayList.add(adoption);
                    }
                    AdoptionAdapter adapter = new AdoptionAdapter(getApplicationContext(), adoptionArrayList);
                    rvAdoption.setAdapter(adapter);;
                    dialog.dismiss();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
            }

        }, error -> {
            error.printStackTrace();
            dialog.dismiss();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return GlobalFunc.getHeaders(getApplicationContext());
            }

        };
        Volley.newRequestQueue(getApplicationContext()).add(request);

    }

}