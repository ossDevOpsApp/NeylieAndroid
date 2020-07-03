package com.slt.devops.neylie.activity.gpsupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.slt.devops.neylie.R;
import com.slt.devops.neylie.adapters.gpsupdate.EquipmentAdapter;
import com.slt.devops.neylie.api.ApiClient;
import com.slt.devops.neylie.models.gpsupdate.Equipment;
import com.slt.devops.neylie.models.gpsupdate.EquipmentResponse;


import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GpsUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    String spinnerRtomVal, spinnerTypeVal,value, sid;
    private RecyclerView recyclerView;
    private List<Equipment> equipmentList;
    private EquipmentAdapter adapter;
    private ProgressBar pgsBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_activity_gps_update);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        value = sharedPreferences.getString("RTOM", "");
        sid = sharedPreferences.getString("SID", "");
        String[] rtomval= value.split("#");

        pgsBar = (ProgressBar) findViewById(R.id.loading_indicator);
        Spinner spinnerRtom = (Spinner) findViewById(R.id.spinnerRtom);
        Spinner spinnerType = (Spinner) findViewById(R.id.spinnerType);
        findViewById(R.id.button).setOnClickListener(this);

        List<String> rtomArea = new ArrayList<String>();
        for(int i=1; i< rtomval.length ; i++){
            rtomArea.add(rtomval[i]);
        }

        List<String> eqType = new ArrayList<String>();
        eqType.add("DP");
        eqType.add("FDP");
        eqType.add("MSAN");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, rtomArea);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRtom.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, eqType);
        dataAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(dataAdapterType);

        spinnerRtom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                spinnerRtomVal = spinnerRtom.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                spinnerTypeVal = spinnerType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


    }


    @Override
    public void onClick(View view) {
        pgsBar.setVisibility(view.VISIBLE);
        Call<EquipmentResponse> call = ApiClient
                .getInstance().getApi().getEquipment(spinnerRtomVal,spinnerTypeVal);

        call.enqueue(new Callback<EquipmentResponse>() {
            @Override
            public void onResponse(Call<EquipmentResponse> call, Response<EquipmentResponse> response) {
                EquipmentResponse equpmentResponse = response.body();

                if (!equpmentResponse.isError()) {
                    equipmentList = equpmentResponse.getEquipment();
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(
                            new LinearLayoutManager(GpsUpdateActivity.this,LinearLayoutManager.VERTICAL, false));
                    adapter = new EquipmentAdapter(GpsUpdateActivity.this,equipmentList);
                    recyclerView.setAdapter(adapter);
                    pgsBar.setVisibility(view.GONE);


                    adapter.setOnItemClickListener(new EquipmentAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {


                                Bundle extras = new Bundle();
                                extras.putString("sid", sid);
                                extras.putString("location", equipmentList.get(position).getLocation());
                                // Toast.makeText(GpsUpdateActivity.this, equipmentList.get(position).getLocation(), Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(GpsUpdateActivity.this, MapsActivity.class);
                                intent.putExtras(extras);
                                startActivity(intent);

                        }
                    });



                } else {
                    Toast.makeText(GpsUpdateActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<EquipmentResponse> call, Throwable t) {

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                if (adapter!=null){
                    adapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
    }



}