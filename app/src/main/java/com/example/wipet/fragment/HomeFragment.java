package com.example.wipet.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wipet.Api;
import com.example.wipet.GlobalFunc;
import com.example.wipet.GlobalVar;
import com.example.wipet.R;
import com.example.wipet.activity.ListAdoptionActivity;
import com.example.wipet.adapter.DiscussionHorizontalAdapter;
import com.example.wipet.adapter.DiscussionListAdapter;
import com.example.wipet.object.Discussion;
import com.example.wipet.object.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class HomeFragment extends Fragment {
    private View view;
    private LinearLayout btnCat;
    private TextView tvViewAllDiscussion;
    private RecyclerView rvDiscussion;
    private ProgressDialog dialog;
    private ArrayList<Discussion> discussionArrayList = new ArrayList<>();
    private DiscussionHorizontalAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();

        return view;

    }

    private void init() {
        tvViewAllDiscussion = view.findViewById(R.id.tv_viewall_homefragment);
        rvDiscussion = view.findViewById(R.id.rv_item_dischorizontal);
        btnCat = view.findViewById(R.id.ll_cat_homefrag);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        tvViewAllDiscussion.setOnClickListener(v -> {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_main_activity, new DiscussionListFragment())
                    .commit();
        });

        setupRV();

        btnCat.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ListAdoptionActivity.class);
            intent.putExtra("pets_category", GlobalVar.CAT);
            startActivity(intent);
        });

    }

    private void setupRV() {
        rvDiscussion.setHasFixedSize(true);
        rvDiscussion.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        loadData();
    }

    private void loadData() {
        dialog.setMessage("Load discussion...");
        dialog.show();

        StringRequest request = new StringRequest(StringRequest.Method.POST, Api.SHOW_LIST_DISCUSSION, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    JSONArray discussionJsonArr = object.getJSONArray("discussion");
                    for (int i = 0; i < discussionJsonArr.length(); i++){
                        JSONObject discussionObj = discussionJsonArr.getJSONObject(i);
                        Discussion discussion = new Discussion();
                        discussion.setId(discussionObj.getInt("id"));
                        discussion.setTitle(discussionObj.getString("title"));
                        discussion.setCreated_at(discussionObj.getString("created_at"));
                        discussion.setCommentsCount(discussionObj.getInt("commentsCount"));
                        discussion.setLikesCount(discussionObj.getInt("likesCount"));

                        JSONArray photoArr = discussionObj.getJSONArray("photo");
                        if (photoArr.length() > 0){
                            ArrayList<String> photoArrayList = new ArrayList<>();
                            for (int j = 0; j < photoArr.length(); j++){
                                JSONObject photo = photoArr.getJSONObject(j);
                                photoArrayList.add(photo.getString("path_photo"));
                            }
                            discussion.setPhoto(photoArrayList);
                        }

                        JSONObject userObj = discussionObj.getJSONObject("user");
                        JSONObject kelurahanObj = userObj.getJSONObject("kelurahan");
                        JSONObject kecamatanObj = kelurahanObj.getJSONObject("kecamatan");
                        JSONObject kotaObj = kecamatanObj.getJSONObject("kota");
                        JSONObject provinsiObj = kotaObj.getJSONObject("provinsi");
                        User user = new User();
                        user.setUsername(userObj.getString("username"));
                        user.setKelurahan(kelurahanObj.getString("name"));
                        user.setKecamatan(kecamatanObj.getString("name"));
                        user.setKota(kotaObj.getString("name"));
                        user.setProvinsi(provinsiObj.getString("name"));

                        discussion.setUser(user);
                        discussionArrayList.add(discussion);



                    }
                    adapter = new DiscussionHorizontalAdapter(getContext(),discussionArrayList);
                    rvDiscussion.setAdapter(adapter);
                    dialog.dismiss();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
            }
        },error -> {
            error.printStackTrace();
            dialog.dismiss();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return GlobalFunc.getHeaders(getContext());
            }
        };
        Volley.newRequestQueue(getContext()).add(request);

    }

}