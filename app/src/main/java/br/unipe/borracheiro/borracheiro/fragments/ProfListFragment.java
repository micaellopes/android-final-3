package br.unipe.borracheiro.borracheiro.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.unipe.borracheiro.borracheiro.Home;
import br.unipe.borracheiro.borracheiro.R;
import br.unipe.borracheiro.borracheiro.adapter.ProfessionalAdapter;
import br.unipe.borracheiro.borracheiro.core.Constants;
import br.unipe.borracheiro.borracheiro.model.Professional;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Andrey on 05/06/2017.
 */

public class ProfListFragment extends Fragment {

    private static ProfListFragment instance;

    public String result;

    List<Professional> profs = new ArrayList<>();

    public ProfListFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        listarProfissionais();

        View rootView = inflater.inflate(R.layout.fragment_proflist, container, false);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ListView ml = (ListView) rootView.findViewById(R.id.lvProfs);
        ml.setAdapter(new ProfessionalAdapter(profs, getActivity()));

        return rootView;
    }

    public synchronized void listarProfissionais() {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Constants.URL_SERVICE)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                result = e.toString();
                Log.i(Constants.LOG_TAG, result);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                result = response.body().string();
                Log.d("RESULT", result);

                try {
                    //JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonarray = new JSONArray(result);

                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject obj = jsonarray.getJSONObject(i);

                        String nom = obj.getString("name");
                        String tel = obj.getString("phone");

                        Professional professional = new Professional(nom, tel);
                        profs.add(professional);

                    }

                } catch (JSONException e) {
                    System.out.print(e);
                }


            }
        });
    }


}