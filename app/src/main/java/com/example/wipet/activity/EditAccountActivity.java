package com.example.wipet.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wipet.Api;
import com.example.wipet.GlobalFunc;
import com.example.wipet.GlobalVar;
import com.example.wipet.R;
import com.example.wipet.object.Cities;
import com.example.wipet.object.District;
import com.example.wipet.object.Province;
import com.example.wipet.object.Village;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditAccountActivity extends AppCompatActivity {
    private Spinner spinProvince,spinCities, spinDistrict, spinVillage;
    private JSONArray result;
    private ArrayList<Province> provinceArrayList ;
    private ArrayList<Cities> citiesArrayList;
    private ArrayList<District> districtArrayList;
    private ArrayList<Village> villageArrayList;
    private ArrayList<String> namesProv, namesCities,namesDistrict, namesVillage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        spinProvince = findViewById(R.id.spin_province_edit_account);
        spinCities = findViewById(R.id.spin_cities_edit_account);
        spinDistrict = findViewById(R.id.spin_distric_edit_account);
        spinVillage = findViewById(R.id.spin_village_edit_account);



        spinDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int idDistrict = districtArrayList.get(position).getId();
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
                loadKota(idProvinsi);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        loadProvinsi();


    }

    private void loadKelurahan(int idDistrict) {
        StringRequest request = new StringRequest(Request.Method.POST, Api.getKelurahan, response -> {
            namesVillage = new ArrayList<>();
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    villageArrayList = new ArrayList<>();
                    JSONArray dataArray = object.getJSONArray("kelurahan");

                    for (int i=0; i<dataArray.length(); i++){
                        Village village = new Village();
                        JSONObject dataObj = dataArray.getJSONObject(i);

                        village.setName(dataObj.getString("name"));
                        village.setId(dataObj.getInt("id"));

                        villageArrayList.add(village);
                    }
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
                    provinceArrayList = new ArrayList<>();
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
                    spinProvince.setSelection(-2);
                    spinProvince.setSelected(false);

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

}