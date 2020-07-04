package com.example.wipet.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wipet.Api;
import com.example.wipet.GlobalFunc;
import com.example.wipet.GlobalVar;
import com.example.wipet.R;
import com.example.wipet.object.Cities;
import com.example.wipet.object.District;
import com.example.wipet.object.Province;
import com.example.wipet.object.User;
import com.example.wipet.object.Village;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAccountActivity extends AppCompatActivity implements TextWatcher {
    private Spinner spinProvince,spinCities, spinDistrict, spinVillage;
    private int idKelurahan, idUser;
    private ProgressDialog dialog;
    private SharedPreferences userInfo;
    private Bitmap bitPhoto = null;
    private JSONArray result;
    private Button btnSave;
    private CircleImageView ivPhoto;
    int idDistrict;
    private TextView btnEditPhoto;
    private TextInputEditText etName, etEmail, etPhone, etAbout, etJob;
    private TextInputLayout etlName, etlEmail, etlPhone, etlAbout, etlJob;
    private ArrayList<Province> provinceArrayList = new ArrayList<>(); ;
    private ArrayList<Cities> citiesArrayList;
    private ArrayList<District> districtArrayList;
    private ArrayList<Village> villageArrayList = new ArrayList<>();;
    private ArrayList<String> namesProv, namesCities,namesDistrict, namesVillage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        init();



    }

    private void init() {


        loadProvinsi();
        userInfo = getApplicationContext().getSharedPreferences(GlobalVar.SP_USER_INFO, 0);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        ivPhoto = findViewById(R.id.iv_photo_editaccount);
        btnEditPhoto = findViewById(R.id.tv_btn_edit_photo_editaccount);
        btnSave = findViewById(R.id.btn_save_editaccount);
        etName = findViewById(R.id.et_name_editaccount);
        etEmail = findViewById(R.id.et_email_editaccount);
        etPhone = findViewById(R.id.et_phone_editaccount);
        etAbout = findViewById(R.id.et_about_editaccount);
        etJob = findViewById(R.id.et_job_editaccount);

        etlName = findViewById(R.id.etl_name_editaccount);
        etlEmail = findViewById(R.id.etl_email_editaccount);
        etlPhone = findViewById(R.id.etl_phone_editaccount);
        etlAbout = findViewById(R.id.etl_about_editaccount);
        etlJob = findViewById(R.id.etl_job_editaccount);


        spinProvince = findViewById(R.id.spin_province_edit_account);
        spinCities = findViewById(R.id.spin_cities_edit_account);
        spinDistrict = findViewById(R.id.spin_distric_edit_account);
        spinVillage = findViewById(R.id.spin_village_edit_account);

        btnEditPhoto.setOnClickListener(v -> {
            pickImage();
        });

        btnSave.setOnClickListener(v -> {
//            if (validate()){
//                save();
//            }
            save();
            Log.d("bitphoto" ,"bit photo :" + GlobalFunc.bitmapToString(bitPhoto));
        });

        etName.addTextChangedListener(this);
        etPhone.addTextChangedListener(this);
        etJob.addTextChangedListener(this);
        etAbout.addTextChangedListener(this);

        spinVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idKelurahan = villageArrayList.get(position).getId();
                Toast.makeText(getApplicationContext(),villageArrayList.get(position).getName() + villageArrayList.get(position).getId(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idDistrict = districtArrayList.get(position).getId();
                loadKelurahan(idDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int idCities = citiesArrayList.get(position).getId();
                loadKecamatan(idCities);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int idProvinsi = provinceArrayList.get(position).getId();
                if (idProvinsi != -1){
                    loadKota(idProvinsi);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri uriPhoto = result.getUri();
                if (uriPhoto != null){
                    try {
                        bitPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), uriPhoto);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ivPhoto.setImageURI(uriPhoto);
            }
        }

    }

    private void save() {
        dialog.setMessage("Saving...");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Api.SAVE_PROFILE, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
//                    JSONObject userObj = object.getJSONObject("user");

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    finish();
                    Toast.makeText(this,"Sukses",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this,e.getMessage()+"A",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        } , error -> {
            error.printStackTrace();
            Toast.makeText(this,error.getMessage()+"A",Toast.LENGTH_SHORT).show();
            dialog.dismiss();

        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userInfo.getString(GlobalVar.VAR_TOKEN,"");
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer " + token);
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", etName.getText().toString().trim());
                map.put("phone", etPhone.getText().toString().trim());
                map.put("job", etJob.getText().toString().trim());
                map.put("about", etAbout.getText().toString().trim());
                map.put("id_kelurahan", idKelurahan+"");
                map.put("photo", bitmapToString(bitPhoto));
                return map;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

//    private boolean validate() {
//        if (etName.getText().toString().isEmpty()){
//            etlName.setErrorEnabled(true);
//            etlName.setError(getString(R.string.this_field_is_required));
//            return false;
//        }
//        if (etPhone.getText().toString().isEmpty()){
//            etlPhone.setErrorEnabled(true);
//            etlPhone.setError(getString(R.string.this_field_is_required));
//            return false;
//        }
//        if (etJob.getText().toString().isEmpty()){
//            etlJob.setErrorEnabled(true);
//            etlJob.setError(getString(R.string.this_field_is_required));
//            return false;
//        }
//        if (etAbout.getText().toString().isEmpty()){
//            etlAbout.setErrorEnabled(true);
//            etlAbout.setError(getString(R.string.this_field_is_required));
//            return false;
//        }
//        if (etName.getText().length() < 5){
//            etlName.setErrorEnabled(true);
//            etlName.setError(getString(R.string.this_field_min5_char));
//            return false;
//        }
//        if (etPhone.getText().length() < 8){
//            etlPhone.setErrorEnabled(true);
//            etlPhone.setError(getString(R.string.this_field_min_char));
//            return false;
//        }
//        if (etJob.getText().length() < 5){
//            etlJob.setErrorEnabled(true);
//            etlJob.setError(getString(R.string.this_field_min5_char));
//            return false;
//        }
//        if (etAbout.getText().length() < 5){
//            etlAbout.setErrorEnabled(true);
//            etlAbout.setError(getString(R.string.this_field_min5_char));
//            return false;
//        }
//        return true;
//    }


    private String bitmapToString(Bitmap bitmap) {
        if (bitmap!=null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte [] array = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(array,Base64.DEFAULT);
        }

        return "";
    }

    private void loadKelurahan(int idDistrict) {
        villageArrayList.clear();
        StringRequest request = new StringRequest(Request.Method.POST, Api.getKelurahan, response -> {
            namesVillage = new ArrayList<>();
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){

                    JSONArray dataArray = object.getJSONArray("kelurahan");
                    StringBuilder sb = new StringBuilder();
                    for (int i=0; i<dataArray.length(); i++){
                        Village village = new Village();
                        JSONObject dataObj = dataArray.getJSONObject(i);

                        village.setName(dataObj.getString("name"));
                        village.setId(dataObj.getInt("id"));
                        sb.append(String.valueOf(dataObj.getInt("id")));
                        villageArrayList.add(village);
                    }

//                    GlobalFunc.showToast(sb.toString(),this, GlobalVar.TIME_SHORT_TOAST);

                    for (int i = 0; i < villageArrayList.size(); i++){
                        namesVillage.add(villageArrayList.get(i).getName().toString());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, namesVillage);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinVillage.setAdapter(adapter);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                GlobalFunc.showToast(e.getMessage().toString() + "A",this, GlobalVar.TIME_SHORT_TOAST);
            }

        },error -> {
            GlobalFunc.showToast(error.getMessage().toString() + "B",this, GlobalVar.TIME_SHORT_TOAST);

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id_kecamatan",String.valueOf(idDistrict));
                return map;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    private void loadKecamatan(int idCities) {
        StringRequest request = new StringRequest(Request.Method.POST, Api.getKecamatan, response -> {
            namesDistrict = new ArrayList<>();
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    districtArrayList = new ArrayList<>();
                    JSONArray dataArray = object.getJSONArray("kecamatan");

                    for (int i=0; i<dataArray.length(); i++){
                        District district = new District();
                        JSONObject dataObj = dataArray.getJSONObject(i);

                        district.setName(dataObj.getString("name"));
                        district.setId(dataObj.getInt("id"));

                        districtArrayList.add(district);
                    }
                    for (int i = 0; i < districtArrayList.size(); i++){
                        namesDistrict.add(districtArrayList.get(i).getName().toString());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, namesDistrict);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinDistrict.setAdapter(adapter);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                GlobalFunc.showToast(e.getMessage().toString() + "A",this, GlobalVar.TIME_SHORT_TOAST);
            }

        },error -> {
            GlobalFunc.showToast(error.getMessage().toString() + "B",this, GlobalVar.TIME_SHORT_TOAST);

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id_kota",String.valueOf(idCities));
                return map;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    private void loadKota(int idProvinsi) {
        StringRequest request = new StringRequest(Request.Method.POST, Api.getKota, response -> {
            namesCities = new ArrayList<>();
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    citiesArrayList = new ArrayList<>();
                    JSONArray dataArray = object.getJSONArray("kota");

                    for (int i=0; i<dataArray.length(); i++){
                        Cities cities = new Cities();
                        JSONObject dataObj = dataArray.getJSONObject(i);

                        cities.setName(dataObj.getString("name"));
                        cities.setId(dataObj.getInt("id"));

                        citiesArrayList.add(cities);
                    }
                    for (int i = 0; i < citiesArrayList.size(); i++){
                        namesCities.add(citiesArrayList.get(i).getName().toString());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, namesCities);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinCities.setAdapter(adapter);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                GlobalFunc.showToast(e.getMessage().toString() + "A",this, GlobalVar.TIME_SHORT_TOAST);
            }

        },error -> {
            GlobalFunc.showToast(error.getMessage().toString() + "B",this, GlobalVar.TIME_SHORT_TOAST);

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id_provinsi",String.valueOf(idProvinsi));
                return map;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

    private void loadProvinsi() {

        StringRequest request = new StringRequest(Request.Method.GET, Api.getProvince, response -> {
            namesProv = new ArrayList<>();
            try {

                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONArray dataArray = object.getJSONArray("provinsi");

                    for (int i=0; i<dataArray.length(); i++){
                        Province province = new Province();
                        JSONObject dataObj = dataArray.getJSONObject(i);

                        province.setName(dataObj.getString("name"));
                        province.setId(dataObj.getInt("id"));

                        provinceArrayList.add(province);
                    }
                    for (int i = 0; i < provinceArrayList.size(); i++){
                        namesProv.add(provinceArrayList.get(i).getName().toString());
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, namesProv);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinProvince.setAdapter(spinnerArrayAdapter);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                GlobalFunc.showToast(e.getMessage().toString() + "B",this, GlobalVar.TIME_SHORT_TOAST);
            }
        },error -> {
            error.printStackTrace();
            GlobalFunc.showToast(error.getMessage().toString() + "B",this, GlobalVar.TIME_SHORT_TOAST);
        });

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!etName.getText().toString().isEmpty()){
            etlName.setErrorEnabled(false);
        }
        if (!etPhone.getText().toString().isEmpty()){
            etlPhone.setErrorEnabled(false);
        }
        if (!etJob.getText().toString().isEmpty()){
            etlJob.setErrorEnabled(false);
        }
        if (!etAbout.getText().toString().isEmpty()){
            etlAbout.setErrorEnabled(false);
        }
        if (etName.getText().length() >= 5 ){
            etlName.setErrorEnabled(false);
        }
        if (etPhone.getText().length() >= 8 ){
            etlPhone.setErrorEnabled(false);
        }
        if (etJob.getText().length() >= 5 ){
            etlJob.setErrorEnabled(false);
        }
        if (etAbout.getText().length() >= 5 ){
            etlAbout.setErrorEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}