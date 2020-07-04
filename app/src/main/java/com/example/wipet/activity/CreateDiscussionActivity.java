package com.example.wipet.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wipet.GlobalFunc;
import com.example.wipet.GlobalVar;
import com.example.wipet.R;
import com.example.wipet.adapter.PhotoDiscussionAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CreateDiscussionActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btnClose;
    private TextView tvPublish;
    private Button btnAddImage;
    private TextInputEditText etTitle, etContent;
    private TextInputLayout etlTitle, etlContent;
    private Spinner spinAnimal, spinTypeDisc;
    private ViewPager2 vpPhoto;
    private ArrayList<Bitmap> bitmapArrayList;
    private ClipData clipDataPhoto ;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_discussion);

        init();

    }

    private void init() {

        btnClose = findViewById(R.id.iv_close_createdisc);
        tvPublish = findViewById(R.id.tv_btn_publish_createdisc);
        vpPhoto = findViewById(R.id.vp_photo_createdisc);
        btnAddImage = findViewById(R.id.btn_addimage_createdisc);
        spinAnimal = findViewById(R.id.spin_animal_createdisc);
        spinTypeDisc = findViewById(R.id.spin_typedisc_createdisc);
        etlContent = findViewById(R.id.etl_content_createdisc);
        etContent = findViewById(R.id.et_content_createdisc);
        etlTitle = findViewById(R.id.etl_title_createdisc);
        etTitle = findViewById(R.id.et_title_createdisc);
        toolbar = findViewById(R.id.toolbar_creatdisc);

        setSupportActionBar(toolbar);


        btnAddImage.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        tvPublish.setOnClickListener(this);

    }

    private void photoSetup() {
        ArrayList<String> stringPhotoArrList = new ArrayList<>();
        for (int i = 0; i < bitmapArrayList.size(); i++){
            stringPhotoArrList.add(GlobalFunc.bitmapToString(bitmapArrayList.get(i)));
        }
        PhotoDiscussionAdapter adapter = new PhotoDiscussionAdapter(this, stringPhotoArrList,GlobalVar.BITMAP_FORMAT);
        vpPhoto.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_createdisc:
                finish();
                break;

            case R.id.btn_addimage_createdisc:
                pickImage();

                break;

            case R.id.tv_btn_publish_createdisc:
                finish();
                break;
        }

    }

    private void pickImage() {

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CreateDiscussionActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, GlobalVar.MULTIPLE_IMAGE_CODE);


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
                        Bitmap bitPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(),photoUri);
                        bitmapArrayList.add(bitPhoto);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }

                photoSetup();
            } else {
                Uri photoUri = data.getData();
                try {
                    Bitmap bitPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(),photoUri);
                    bitmapArrayList.add(bitPhoto);

                    photoSetup();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}