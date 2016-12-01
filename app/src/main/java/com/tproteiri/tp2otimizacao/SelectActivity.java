package com.tproteiri.tp2otimizacao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.tproteiri.tp2otimizacao.adapters.SelectAdapter;
import com.tproteiri.tp2otimizacao.models.City;
import com.tproteiri.tp2otimizacao.utils.CacheMemory;

public class SelectActivity extends AppCompatActivity implements OnSelectLinkListener {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        City cp = CacheMemory.getParam();
        RecyclerView.Adapter mAdapter = new SelectAdapter(cp, CacheMemory.getCities(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSelectLink(City city) {
        City cp = CacheMemory.getParam();
        CacheMemory.addLink(cp, city);
        CacheMemory.setParam(null);
        finish();
    }
}
