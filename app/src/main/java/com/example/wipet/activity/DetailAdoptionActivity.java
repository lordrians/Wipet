package com.example.wipet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.wipet.Api;
import com.example.wipet.GlobalFunc;
import com.example.wipet.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailAdoptionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvUsername, tvKota, tvDate, tvTitle, tvContent;
    private CircleImageView ivUserPhoto;
    private ViewPager2 vpPhoto;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_adoption);
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

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        setSupportActionBar(toolbar);

        loadDiskusi();


    }

    private void loadDiskusi() {
        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest request = new StringRequest(StringRequest.Method.POST, Api.DETAIL_DISCUSSION , response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONObject discussion = object.getJSONObject("discussion");
                    tvTitle.setText(discussion.getString("title"));
                    tvContent.setText(discussion.getString("content"));
                    tvTitle.setText(discussion.getString("title"));
                    tvTitle.setText(discussion.getString("title"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return GlobalFunc.getHeaders(getApplicationContext());
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("id",28+"");
                return map;
            }
        };


    }
}