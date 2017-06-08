package br.unipe.borracheiro.borracheiro.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.unipe.borracheiro.borracheiro.R;

/**
 * Created by Andrey on 06/06/2017.
 */

public class WelcomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    public void onViewCreate(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
