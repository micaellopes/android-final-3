package br.unipe.borracheiro.borracheiro;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import br.unipe.borracheiro.borracheiro.adapter.ProfessionalAdapter;
import br.unipe.borracheiro.borracheiro.core.Constants;
import br.unipe.borracheiro.borracheiro.factory.SharedPreferencesActivity;
import br.unipe.borracheiro.borracheiro.fragments.GmapFragment;
import br.unipe.borracheiro.borracheiro.fragments.ProfListFragment;
import br.unipe.borracheiro.borracheiro.fragments.WelcomeFragment;
import br.unipe.borracheiro.borracheiro.model.Professional;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Home home;
    private boolean conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.home = Home.this;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        Inicia Home (Navigation Drawer) com WelcomeFragment
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame,new WelcomeFragment()).commit();

        checkConnection();
        if(!conexao){
            Toast.makeText(this, "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResume(){
        super.onResume();

        checkConnection();
        if(!conexao){
            Toast.makeText(this, "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();
        }else{
            ProfListFragment profList = new ProfListFragment();
            profList.listarProfissionais();
        }
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fm.beginTransaction().replace(R.id.content_frame, new WelcomeFragment()).commit();
        } else if (id == R.id.nav_profList) {
            checkConnection();
            if(!conexao){
                Toast.makeText(this, "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();
            }else{
                fm.beginTransaction().replace(R.id.content_frame, new ProfListFragment()).commit();
            }
        } else if (id == R.id.nav_sair) {
            SharedPreferencesActivity.remove(Home.this, Constants.TOKEN);
            startActivity(new Intent(Home.this, Login.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void exibirProfList(View v){
        checkConnection();
        if(!conexao){
            Toast.makeText(this, "Sem conexão com a internet!", Toast.LENGTH_SHORT).show();
        }else{
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.content_frame, new ProfListFragment()).commit();
        }
    }

    /* Verifica Conexão com a Internet
     */
    public void checkConnection() {
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {

            conexao = true;

        } else {

            conexao = false;

        }
    }

    /*public synchronized void listarProfissionais(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.URL_SERVICE)
                .build();
        client.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                result = e.toString();
                Log.i(Constants.LOG_TAG, result);
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Home.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            result = response.body().string();
//                            responseJson(result);
                            Log.d("CORPO_RESPOSTA -> ", result);
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }*/

//    private void responseJson(String result) {
//
//        Gson gson = new Gson();
//        List<Professional> profs = gson.fromJson(result, new TypeToken<List<Professional>>() {
//        }.getType());
//
//        listView.setAdapter(new ProfessionalAdapter(profs, Home.this));
//
//    }

}