package com.alok.pandemic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private ChipNavigationBar chipNavigationBar;
    private HomeFragment homeFragment;
    private CountryFragment countryFragment;
    private ProfileFragment profileFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fragments
        homeFragment = new HomeFragment();
        countryFragment = new CountryFragment();
        profileFragment = new ProfileFragment();

        //Tabs
        frameLayout = findViewById(R.id.main_frame);
        chipNavigationBar = findViewById(R.id.buttonPanel);
        setFragment(homeFragment);
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        setFragment(homeFragment);
                        break;
                    case R.id.profile:
                        setFragment(profileFragment);
                        break;
                    case R.id.countries:
                        setFragment(countryFragment);
                        break;
                }
            }
        });

    }


    private void setFragment(Fragment homeFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, homeFragment);
        fragmentTransaction.commit();
    }


}