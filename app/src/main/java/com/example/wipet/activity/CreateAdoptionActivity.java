package com.example.wipet.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wipet.Api;
import com.example.wipet.GlobalFunc;
import com.example.wipet.GlobalVar;
import com.example.wipet.R;
import com.example.wipet.adapter.PhotoDiscussionAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateAdoptionActivity extends AppCompatActivity {
    private Spinner spinPetsCategory, spinCategory, spinSize, spinSex, spinStory;
    private ViewPager2 vpPhoto;
    private Toolbar toolbar;
    private TextInputEditText etName, etAge, etDesc, etMedNotes, etPrice;
    private CheckBox cbVaccinated, cbNeutered, cbFriendly;
    private Button btnAddImage, btnUpload;
    private ArrayList<Bitmap> bitmapArrayList;
    private ClipData clipDataPhoto ;
    private ProgressDialog dialog;
    private ArrayList<String> stringPhotoArrList = new ArrayList<>();
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_adoption);
        
        init();
        
    }

    private void init() {
        spinCategory = findViewById(R.id.spin_category_createadopt);
        spinPetsCategory = findViewById(R.id.spin_petcategory_createadopt);
        spinSize = findViewById(R.id.spin_size_createadopt);
        spinSex = findViewById(R.id.spin_sex_createadopt);
        spinStory = findViewById(R.id.spin_backgroundstory_detailadopt);
        vpPhoto = findViewById(R.id.vp_photo_createadopt);
        toolbar = findViewById(R.id.toolbar_createadopt);
        etName = findViewById(R.id.et_name_createadopt);
        etAge = findViewById(R.id.et_age_createadopt);
        etPrice = findViewById(R.id.et_price_createadopt);
        etDesc = findViewById(R.id.et_desc_detailadopt);
        etMedNotes = findViewById(R.id.et_medicalnotes_detailadopt);
        cbVaccinated = findViewById(R.id.cb_vaccinated_detailadopt);
        cbNeutered = findViewById(R.id.cb_neutered_detailadopt);
        cbFriendly = findViewById(R.id.cb_friendly_detailadopt);
        btnAddImage = findViewById(R.id.btn_addimage_createadopt);
        btnUpload = findViewById(R.id.btn_upload_detailadopt);
        toolbar = findViewById(R.id.toolbar_list_adoption);

        setSupportActionBar(toolbar);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        
        btnUpload.setOnClickListener(v -> {
            upload();

        });
        btnAddImage.setOnClickListener(v -> pickImage());


        
    }

    private void pickImage() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CreateAdoptionActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, GlobalVar.MULTIPLE_IMAGE_CODE);
    }

    private void upload() {
        dialog.setMessage("Uploading...");
        dialog.show();


        StringRequest request = new StringRequest(StringRequest.Method.POST, Api.CREATE_ADOTION,response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    finish();
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
                String data = new Gson().toJson(stringPhotoArrList);
                map.put("title", etName.getText().toString().trim());
                map.put("age", etAge.getText().toString().trim());
                map.put("price", String.valueOf(etPrice.getText()));
                map.put("status", "false");
                map.put("size", getAnimalSize()+"");
                map.put("gender", spinSex.getSelectedItem().toString());
                map.put("background_story", spinStory.getSelectedItem().toString());
                map.put("desc", etDesc.getText().toString().trim());
                map.put("medical_notes", etMedNotes.getText().toString().trim());
                map.put("vaccinated", getResultCheckBox(cbVaccinated)+"");
                map.put("neutered", getResultCheckBox(cbNeutered)+"");
                map.put("friendly", getResultCheckBox(cbFriendly)+"");
                map.put("photo", data);

                return map;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);

    }

    private int getResultCheckBox(CheckBox checkBox) {
        if (checkBox.isChecked()){
            return 1;
        } else {
            return 0;
        }
    }

    private int getAnimalSize() {
        String item = spinSize.getSelectedItem().toString();
        if (item.equals("Small")){
            return 0;
        } else if (item.equals("Medium")){
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GlobalVar.MULTIPLE_IMAGE_CODE && resultCode == RESULT_OK){
            bitmapArrayList = new ArrayList<>();
            clipDataPhoto = data.getClipData();

            if (clipDataPhoto != null){
                for (int i = 0; i < clipDataPhoto.getItemCount(); i++){
                    Uri photoUri = clipDataPhoto.getItemAt(i).getUri();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(photoUri);
                        Bitmap bitPhoto = BitmapFactory.decodeStream(inputStream);
                        bitmapArrayList.add(bitPhoto);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }

                photoSetup();
            } else {
                Uri photoUri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(photoUri);
                    Bitmap bitPhoto = BitmapFactory.decodeStream(inputStream);
                    bitmapArrayList.add(bitPhoto);

                    photoSetup();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    private void photoSetup() {
        for (int i = 0; i < bitmapArrayList.size(); i++){
            stringPhotoArrList.add(GlobalFunc.bitmapToString(bitmapArrayList.get(i)));
        }
        PhotoDiscussionAdapter adapter = new PhotoDiscussionAdapter(this, stringPhotoArrList,GlobalVar.BITMAP_FORMAT);
        vpPhoto.setAdapter(adapter);
    }
}