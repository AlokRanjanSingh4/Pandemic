package com.alok.pandemic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountryFragment extends Fragment {

    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<CountryCovid> countryCovidList;
    private CovidAdapter adapter;

    private String url = "https://covid19api.herokuapp.com/";

    private int order_list = -1;
    private int data_type = -1;
    private int latest_confirmed = -1;
    private int latest_recovered = -1;
    private int latest_deaths = -1;

    public CountryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_country, container, false);

        mList = root.findViewById(R.id.main_list);

        countryCovidList = new ArrayList<>();
        adapter = new CovidAdapter(getContext(), countryCovidList);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);

        getData(data_type, order_list);

        return root;
    }


    private void getData(final int type, final int order) {

        countryCovidList.clear();

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String type_;

                switch (type) {
                    case 0:
                        type_ = "deaths";
                        break;
                    case 1:
                        type_ = "recovered";
                        break;
                    default:
                        type_ = "confirmed";
                }


                JSONArray jsonArray = null;
                JSONObject jsonTmp = null;

                try {
                    jsonArray = response.getJSONObject(type_).getJSONArray("locations");
                    jsonTmp = response.getJSONObject("latest");
                    latest_confirmed = jsonTmp.getInt("confirmed");
                    latest_recovered = jsonTmp.getInt("recovered");
                    latest_deaths = jsonTmp.getInt("deaths");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        CountryCovid countryCovid = new CountryCovid();
                        countryCovid.setCountry(jsonObject.getString("country"));
                        countryCovid.setCountry_code(jsonObject.getString("country_code"));
                        countryCovid.setLatest(jsonObject.getInt("latest"));
                        countryCovid.setProvince(jsonObject.getString("province"));

                        countryCovidList.add(countryCovid);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }

                if (order_list == -1) {
                    Collections.sort(countryCovidList, new Comparator<CountryCovid>() {
                        @Override
                        public int compare(CountryCovid lhs, CountryCovid rhs) {
                            if (lhs.getLatest() >= rhs.getLatest()) {
                                return -1;
                            } else {
                                return 1;
                            }
                        }
                    });
                } else {
                    Collections.sort(countryCovidList, new Comparator<CountryCovid>() {
                        @Override
                        public int compare(CountryCovid lhs, CountryCovid rhs) {
                            if (lhs.getLatest() < rhs.getLatest()) {
                                return -1;
                            } else {
                                return 1;
                            }
                        }
                    });
                }

                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }
}
