package com.example.wipet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wipet.Api;
import com.example.wipet.GlobalFunc;
import com.example.wipet.GlobalVar;
import com.example.wipet.R;
import com.example.wipet.adapter.PhotoAdapter;
import com.example.wipet.object.Adoption;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailAdoptionActivity extends AppCompatActivity {
    private TextView tvName, tvPrice, tvUsername, tvKota, tvProvinsi, tvAge, tvCategory, tvReason, tvMedical_notes, tvDesc;
    private ViewPager2 vpPhoto;
    private Button btnApply ;
    private ImageView ivGender;
    private ImageButton  btnChat, btnVaccinated, btnFriendly, btnNeutered, btnLike, btnBack;
    private int idAdopsi;
    private Adoption adopsi;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_adoption);

//        idAdopsi = getIntent().getIntExtra("id",0);
        adopsi = getIntent().getParcelableExtra("adoption");


        init();

        loadData();



    }


    private void init() {

        tvName = findViewById(R.id.tv_name_detailadpot);
        tvPrice = findViewById(R.id.tv_price_detailadpot);
        tvUsername = findViewById(R.id.tv_username_detailadpot);
        tvKota = findViewById(R.id.tv_kota_detailadpot);
        tvProvinsi = findViewById(R.id.tv_provinsi_detailadpot);
        tvAge = findViewById(R.id.tv_age_detailadpot);
        tvCategory = findViewById(R.id.tv_category_detailadpot);
        tvReason = findViewById(R.id.tv_reason_detailadpot);
        tvMedical_notes = findViewById(R.id.tv_medicalnotes_detailadopt);
        tvDesc = findViewById(R.id.tv_desc_detailadopt);
        vpPhoto = findViewById(R.id.vp_photo_detailadopt);
        btnApply = findViewById(R.id.btn_apply_detailadopt);
        btnChat = findViewById(R.id.btn_chat_detailadopt);
        btnVaccinated = findViewById(R.id.iv_vaccinated_detailadopt);
        btnFriendly = findViewById(R.id.iv_friendly_detailadopt);
        btnNeutered = findViewById(R.id.iv_neutered_detailadopt);
        btnLike = findViewById(R.id.iv_like_detailadpot);
        btnBack = findViewById(R.id.iv_back_detailadpot);
        ivGender = findViewById(R.id.iv_gender_detailadpot);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        btnLike.setOnClickListener(v->likeAdoption());
        btnBack.setOnClickListener(v ->finish());

    }

    private void loadData() {
        dialog.setMessage("Load Data...");
        dialog.show();

        StringRequest request = new StringRequest(StringRequest.Method.POST, Api.SHOW_ITEM_ADOPTION, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONObject adoptionObj = object.getJSONObject("adoption");
                    JSONObject userObj = adoptionObj.getJSONObject("user");

                    if (object.getBoolean("liked")){
                        btnLike.setImageResource(R.drawable.ic_favorite);
                    }

                    tvName.setText(adoptionObj.getString("title"));
                    tvPrice.setText("Rp. "+ GlobalFunc.getFormatCurrency(adoptionObj.getLong("price")));
                    tvUsername.setText(userObj.getString("username"));
                    tvKota.setText(adopsi.getUser().getKota());
                    tvProvinsi.setText(adopsi.getUser().getProvinsi());
                    tvAge.setText(adoptionObj.getInt("age")+"");
                    tvCategory.setText(adopsi.getCategory());
                    tvReason.setText(adoptionObj.getString("background_story"));
                    tvMedical_notes.setText(adoptionObj.getString("medical_notes"));
                    tvDesc.setText(adoptionObj.getString("desc"));

                    if (adoptionObj.getString("gender").equals("Female")){
                        ivGender.setImageResource(R.drawable.ic_female_black);
                    }

                    if (adoptionObj.getInt("vaccinated") != 0){
                        btnVaccinated.setBackgroundResource(R.drawable.bg_accent);
                    }
                    if (adoptionObj.getInt("neutered") != 0){
                        btnNeutered.setBackgroundResource(R.drawable.bg_accent);
                    }
                    if (adoptionObj.getInt("friendly") != 0){
                        btnFriendly.setBackgroundResource(R.drawable.bg_accent);
                    }

                    JSONArray photoJsonArr = new JSONArray(adoptionObj.getString("photo"));
                    ArrayList<String> stringPhotoArrList = new ArrayList<>();
                    for (int i = 0; i < photoJsonArr.length(); i++){
                        JSONObject photo = photoJsonArr.getJSONObject(i);
                        stringPhotoArrList.add(photo.getString("path_photo"));
                    }
                    PhotoAdapter adapter = new PhotoAdapter(this, stringPhotoArrList, GlobalVar.STRING_FORMAT,GlobalVar.DETAIL_ADOPTION);
                    vpPhoto.setAdapter(adapter);


                }
                dialog.dismiss();

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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("id", adopsi.getId()+"");
                return map;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);


    }
    private void likeAdoption() {
        dialog.setMessage("Liking...");
        dialog.show();

        StringRequest request = new StringRequest(StringRequest.Method.POST, Api.CREATE_LIKE_ADOPTION, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    if (object.getString("message").equals("Liked")){
                        btnLike.setImageResource(R.drawable.ic_favorite);
                    } else {
                        btnLike.setImageResource(R.drawable.ic_favorite_black);
                    }
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("id", adopsi.getId()+"" );
                return map;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);

    }



}