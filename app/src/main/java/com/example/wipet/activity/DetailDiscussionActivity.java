package com.example.wipet.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
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
import com.example.wipet.adapter.CommentAdapter;
import com.example.wipet.adapter.PhotoDiscussionAdapter;
import com.example.wipet.object.Comment;
import com.example.wipet.object.Discussion;
import com.example.wipet.object.User;
import com.google.android.material.textfield.TextInputEditText;

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
    private ImageView ivBack, ivLike;
    private TextView tvUsername, tvKota, tvDate, tvTitle, tvContent;
    private CircleImageView ivUserPhoto;
    private ViewPager2 vpPhoto;
    private ProgressDialog dialog;
    private RecyclerView rvComment;
    private ImageButton btnSend;
    private TextInputEditText etComment;
    private int idDiscussion;
    private ArrayList<Comment> commentArrayList = new ArrayList<>();
    private ScrollView svLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_discussion);
        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar_detaildisc);
        ivBack = findViewById(R.id.iv_back_detaildisc);
        ivLike = findViewById(R.id.iv_like_detaildisc);
        ivUserPhoto = findViewById(R.id.iv_userphoto_detaildisc);
        tvContent = findViewById(R.id.tv_content_detaildisc);
        tvDate = findViewById(R.id.tv_create_at_detaildisc);
        tvKota = findViewById(R.id.tv_kota_detaildisc);
        tvTitle = findViewById(R.id.tv_title_detaildisc);
        tvUsername = findViewById(R.id.tv_username_detaildisc);
        vpPhoto = findViewById(R.id.vp_photo_detaildisc);
        rvComment = findViewById(R.id.rv_comment_detaildisc);
        etComment = findViewById(R.id.et_comment_detaildisc);
        btnSend = findViewById(R.id.iv_sendcomment_detaildisc);
        svLayout = findViewById(R.id.sv_detaildisc);

        rvComment.setHasFixedSize(true);
        rvComment.setLayoutManager(new LinearLayoutManager(this));

        Discussion paketDiscussion = getIntent().getParcelableExtra("discussion");
        idDiscussion = paketDiscussion.getId();
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        setSupportActionBar(toolbar);
        loadDiskusi(idDiscussion);

        ivBack.setOnClickListener(v -> finish());
        btnSend.setOnClickListener(v -> sendComment());
        ivLike.setOnClickListener(v -> likeDiscussion());


    }

    private void likeDiscussion() {
        dialog.setMessage("Liking...");
        dialog.show();

        StringRequest request = new StringRequest(StringRequest.Method.POST, Api.CREATE_LIKE, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    if (object.getString("message").equals("Liked")){
                        ivLike.setImageResource(R.drawable.ic_favorite);
                    } else {
                        ivLike.setImageResource(R.drawable.ic_favorite_black);
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
                map.put("id_diskusi", idDiscussion+"" );
                return map;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);

    }

    private void sendComment() {
        dialog.setMessage("Sending...");
        dialog.show();

        StringRequest request = new StringRequest(StringRequest.Method.POST, Api.CREATE_COMMENT, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONObject commentObj = object.getJSONObject("data");
                    JSONObject userObj = commentObj.getJSONObject("user");
                    Comment comment = new Comment();
                    User user = new User();
                    user.setId(userObj.getInt("id"));
                    user.setUsername(userObj.getString("username"));
                    user.setPhoto(userObj.getString("photo"));

                    comment.setId(commentObj.getInt("id"));
                    comment.setComment(commentObj.getString("comment"));
                    comment.setCreated_at(commentObj.getString("created_at"));
                    comment.setUser(user);

                    commentArrayList.add(comment);
                    rvComment.getAdapter().notifyDataSetChanged();

                    rvComment.scrollToPosition(commentArrayList.size()-1);
                    etComment.setText("");
                    svLayout.fullScroll(ScrollView.FOCUS_DOWN);
                    Toast.makeText(getApplicationContext(), "Comment success", Toast.LENGTH_SHORT).show();
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
                map.put("comment", etComment.getText().toString().trim());
                map.put("id_diskusi", idDiscussion+"" );
                return map;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);


    }

    private void loadDiskusi(int idDiscussion) {
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

                    //Comment
                    if (discussion.getJSONArray("comment").length() > 0){
                        JSONArray commentJsonArr = new JSONArray(discussion.getString("comment"));
                        for (int i = 0; i < commentJsonArr.length(); i++){
                            JSONObject commentObj = commentJsonArr.getJSONObject(i);
                            JSONObject userCommentObj = commentObj.getJSONObject("user");
                            Comment comment = new Comment();
                            User userComment = new User();
                            userComment.setId(userCommentObj.getInt("id"));
                            userComment.setUsername(userCommentObj.getString("username"));
                            userComment.setPhoto(userCommentObj.getString("photo"));
                            comment.setId(commentObj.getInt("id"));
                            comment.setComment(commentObj.getString("comment"));
                            comment.setCreated_at(commentObj.getString("created_at"));
                            comment.setUser(userComment);
                            commentArrayList.add(comment);
                        }
                        CommentAdapter commentAdapter = new CommentAdapter(getApplicationContext(),commentArrayList);
                        rvComment.setAdapter(commentAdapter);

                    }


                }
            } catch (JSONException e) {
                e.printStackTrace();

                dialog.dismiss();
            }
            dialog.dismiss();
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
                map.put("id",idDiscussion+"");
                return map;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(request);


    }
}