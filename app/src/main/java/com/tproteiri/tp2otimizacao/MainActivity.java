package com.tproteiri.tp2otimizacao;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tproteiri.tp2otimizacao.adapters.CitiesAdapter;
import com.tproteiri.tp2otimizacao.models.City;
import com.tproteiri.tp2otimizacao.models.Constraint;
import com.tproteiri.tp2otimizacao.models.Problem;
import com.tproteiri.tp2otimizacao.models.Variable;
import com.tproteiri.tp2otimizacao.rest.ApiService;
import com.tproteiri.tp2otimizacao.utils.CacheMemory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnCitySelectListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFab;
    private Retrofit mRetrofit;
    private HashMap<City, String> mTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupRestClient();

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new CitiesAdapter(CacheMemory.getCities(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onClick(City city) {
        CacheMemory.setParam(city);
        startActivity(new Intent(this, SelectActivity.class));
    }

    public void onCalculate(View view) {

        if (CacheMemory.getCities().size() <= 1) {
            Toast.makeText(getApplicationContext(),
                    "Não há cidades o suficiente", Toast.LENGTH_SHORT).show();
            return;
        }

        Problem p = buildProblem();
        Call<JsonObject> requestSolver = mRetrofit.create(ApiService.class).solve(p);
        requestSolver.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(retrofit.Response<JsonObject> response, Retrofit retrofit) {
//                for (Map.Entry<String, JsonElement> s : response.body().entrySet()) {
//                    Log.d("Net", s.getKey() + "->" + s.getValue().toString());
//                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));
//                }
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                Log.d("Net", response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("Net", t.getMessage());
            }
        });
    }

    private void setupRestClient() {
        OkHttpClient client = new OkHttpClient();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiService.END)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private List<City> testData() {
        City c1 = new City("São Paulo", -23.542487, -46.647531);
        City c2 = new City("Belo Horizonte", -19.913297, -43.929425);
        City c3 = new City("Rio de Janeiro", -22.905928, -43.202562);

        c1.addLink(c2);
        c2.addLink(c3);

        List<City> cities = new ArrayList<>();
        cities.add(c1);
        cities.add(c2);
        cities.add(c3);

        return cities;
    }

    private String sorted(String str) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    private Problem buildProblem() {

        String vars[] = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I" , "J",
                "K", "L","M", "N", "O", "P", "Q","R", "S","T","U","V", "X", "Z"};

        mTable = new HashMap<>();

        List<City> cities = CacheMemory.getCities();
        //List<City> cities = testData();

        for (int i = 0; i < cities.size(); i++) {
            mTable.put(cities.get(i), vars[i]);
        }

        List<Variable> objective = new ArrayList<>();
        for (City c : cities) {
            if (c.getLinks() == null) continue;
            for (City c2 : c.getLinks().keySet()) {
                String varName = String.format("%s%s", mTable.get(c),mTable.get(c2));
                Variable v = new Variable(sorted(varName), c.getLinks().get(c2));
                objective.add(v);
            }
        }

        List<Constraint> constraints = new ArrayList<>();
        int count = 1;
        for (City c : cities) {
            List<Variable> constraintVars = new ArrayList<>();

            for (City c2 : cities) {
                if (c2.getLinks() != null && !c2.getLocality().equals(c.getLocality()))  {
                    for (City c3 : c2.getLinks().keySet()) {
                        if (c3.getLocality().equals(c.getLocality())) {
                            String varName = String.format("%s%s", mTable.get(c),mTable.get(c2));
                            constraintVars.add(new Variable(sorted(varName), 1.0));
                        }
                    }
                }
            }

            if (c.getLinks() != null)  {
                for (City c2 : c.getLinks().keySet()) {
                    String varName = String.format("%s%s", mTable.get(c), mTable.get(c2));
                    Variable v = new Variable(sorted(varName), 1.0);
                    constraintVars.add(v);
                }
            }

            Constraint constraint;
            if (count == 1) {
                constraint = new Constraint("c"+count, "=", -1.0, constraintVars);
            } else if (count == cities.size()) {
                constraint = new Constraint("c"+count, "=", 1.0, constraintVars);
            } else {
                constraint = new Constraint("c"+count, "=", 0.0, constraintVars);
            }
            constraints.add(constraint);

            count++;
        }

        return new Problem("MIN", objective, constraints);

//        Variable x1 = new Variable("x1", 1.0);
//        Variable x2 = new Variable("x2", 2.0);
//
//        Variable c1x1 = new Variable("x1", 8.0);
//        Variable c1x2 = new Variable("x2", 2.0);
//
//        Variable c2x1 = new Variable("x1", 1.0);
//        Variable c2x2 = new Variable("x2", 1.0);
//
//        Variable c3x1 = new Variable("x1", 2.0);
//        Variable c3x2 = new Variable("x2", 7.0);
//
//        ArrayList<Variable> objective = new ArrayList();
//        objective.add(x1);
//        objective.add(x2);
//
//        ArrayList<Variable> c1Vars = new ArrayList();
//        c1Vars.add(c1x1);
//        c1Vars.add(c1x2);
//        Constraint c1 = new Constraint("c1", ">=", 16.0, c1Vars);
//
//        ArrayList<Variable> c2Vars = new ArrayList();
//        c2Vars.add(c2x1);
//        c2Vars.add(c2x2);
//        Constraint c2 = new Constraint("c2", "<=", 6.0, c2Vars);
//
//        ArrayList<Variable> c3Vars = new ArrayList();
//        c3Vars.add(c3x1);
//        c3Vars.add(c3x2);
//        Constraint c3 = new Constraint("c3", ">=", 28.0, c3Vars);
//
//        ArrayList<Constraint> constraints = new ArrayList();
//        constraints.add(c1);
//        constraints.add(c2);
//        constraints.add(c3);
//
//        return new Problem("MIN", objective, constraints);
    }

    static class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            System.out.println(
                    String.format("Sending request %s on %s%n%s", request.url(), chain.connection(),
                            request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            System.out.println(
                    String.format("Received response for %s in %.1fms%n%s", response.request().url(),
                            (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }
}


