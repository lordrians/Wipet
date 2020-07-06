package com.example.wipet.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wipet.GlobalFunc;
import com.example.wipet.GlobalVar;
import com.example.wipet.R;
import com.example.wipet.fragment.AccountFragment;
import com.example.wipet.fragment.DiscussionListFragment;
import com.example.wipet.fragment.HomeFragment;
import com.example.wipet.fragment.MoreAdoptionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Handler handler;
    private boolean isLoggedIn;
    private FragmentManager fragmentManager;
    private BottomNavigationView botNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handlerAuth();
        init();




    }

    private void init() {
        botNav = findViewById(R.id.botnav_view_main);
        boolean createDisc = getIntent().getBooleanExtra(GlobalVar.FROM_CREATE_DISCUSSION,false);
        Toast.makeText(this, String.valueOf(createDisc), Toast.LENGTH_SHORT).show();
        if (createDisc){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_main_activity, new DiscussionListFragment())
                    .commit();
        }

        botNav.setOnNavigationItemSelectedListener(this);
    }

    private void handlerAuth() {
        handler = new Handler();
        handler.postAtTime(new Runnable() {
            @Override
            public void run() {

                SharedPreferences UserInfo = getSharedPreferences(GlobalVar.SP_USER_INFO,0);
                isLoggedIn = UserInfo.getBoolean(GlobalVar.VAR_IS_LOGGED_IN, false);

                if (isLoggedIn){
                    loadFragment(new HomeFragment());
                } else {
                    sendToLogin();
                }

            }
        },0);
    }

    private void sendToLogin() {
        startActivity(new Intent(getApplicationContext(), AuthActivity.class));
        finish();
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_main_activity, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.botnav_home:
                fragment = new HomeFragment();
                break;

            case R.id.botnav_discussion:
                fragment = new DiscussionListFragment();
                break;

            case R.id.botnav_adoption:
                fragment = new MoreAdoptionFragment();
                break;

            case R.id.botnav_notification:
                GlobalFunc.showToast("Coming Soon", getApplicationContext(), GlobalVar.TIME_SHORT_TOAST);
                break;

            case R.id.botnav_account:
                fragment = new AccountFragment();
                break;
        }

        return loadFragment(fragment);
    }
}
