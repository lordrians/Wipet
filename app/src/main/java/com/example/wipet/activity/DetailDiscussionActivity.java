package com.example.wipet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.wipet.Api;
import com.example.wipet.GlobalFunc;
import com.example.wipet.GlobalVar;
import com.example.wipet.R;
import com.example.wipet.adapter.PhotoDiscussionAdapter;
import com.example.wipet.object.Discussion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailDiscussionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvUsername, tvKota, tvDate, tvTitle, tvContent;
    private CircleImageView ivUserPhoto;
    private ViewPager2 vpPhoto;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_discussion);
        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar_detaildisc);
        ivBack = findViewById(R.id.iv_back_detaildisc);
        ivUserPhoto = findViewById(R.id.iv_userphoto_detaildisc);
        tvContent = findViewById(R.id.tv_content_detaildisc);
        tvDate = findViewById(R.id.tv_create_at_detaildisc);
        tvKota = findViewById(R.id.tv_kota_detaildisc);
        tvTitle = findViewById(R.id.tv_title_detaildisc);
        tvUsername = findViewById(R.id.tv_username_detaildisc);
        vpPhoto = findViewById(R.id.vp_photo_detaildisc);

        Discussion paketDiscussion = getIntent().getParcelableExtra("discussion");
        int idDiscussion = paketDiscussion.getId();
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        setSupportActionBar(toolbar);
        Toast.makeText(getApplicationContext(),"A",Toast.LENGTH_SHORT).show();
        loadDiskusi(idDiscussion);
        ivBack.setOnClickListener(v -> finish());


    }
    private void loadDiskusi(int idDiscussion) {
        Toast.makeText(getApplicationContext(),"B",Toast.LENGTH_SHORT).show();
        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest request = new StringRequest(StringRequest.Method.POST, Api.DETAIL_DISCUSSION , response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){

                    Toast.makeText(getApplicationContext(),"C",Toast.LENGTH_SHORT).show();
                    JSONObject discussion = object.getJSONObject("discussion");
                    tvTitle.setText(discussion.getString("title"));
                    tvContent.setText(discussion.getString("content"));
                    tvDate.setText(discussion.getString("created_at"));
                    tvTitle.setText(discussion.getString("title"));

                    JSONObject user = discussion.getJSONObject("user");
                    tvUsername.setText(user.getString("username"));
                    if (!user.getString("photo").isEmpty()){
                        Glide.with(getApplicationContext())
                                .load(Api.DIR_USER_PHOTO + user.getString("photo"))
                                .centerCrop()
                                .into(ivUserPhoto);
                    }
                    JSONObject kelurahan = user.getJSONObject("kelurahan");
                    JSONObject kecamatan = kelurahan.getJSONObject("kecamatan");
                    JSONObject kota = kecamatan.getJSONObject("kota");
                    if (!kota.getString("name").isEmpty()){
                        tvKota.setText(kota.getString("name"));
                    } else {tvKota.setText("");}

                    JSONArray photoJsonArr = new JSONArray(discussion.getString("photo"));
                    ArrayList<String> stringPhotoArrList = new ArrayList<>();
                    for (int i = 0; i < photoJsonArr.length(); i++){
                        JSONObject photo = photoJsonArr.getJSONObject(i);
                        stringPhotoArrList.add(photo.getString("path_photo"));
                    }
                    PhotoDiscussionAdapter adapter = new PhotoDiscussionAdapter(this, stringPhotoArrList, GlobalVar.STRING_FORMAT);
                    vpPhoto.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();

                Toast.makeText(getApplicationContext(),"D",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
            dialog.dismiss();
        }, error -> {
            error.printStackTrace();

            Toast.makeText(getApplicationContext(),"E",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return GlobalFunc.getHeaders(getApplicationContext());
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("id",idDiscussion+"");
                return map;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);


    }
}