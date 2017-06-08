package br.unipe.borracheiro.borracheiro.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import br.unipe.borracheiro.borracheiro.R;

/**
 * Created by Andrey on 05/06/2017.
 */

public class GmapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng jp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gmaps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        jp = new LatLng(-7.119779, -34.857697);
        mMap.addMarker(createMarker(jp, "João Pessoa", "Av. Epitácio Pessoa"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(jp, 16.0f));
    }

    public MarkerOptions createMarker(LatLng latLng, String title, String snipet){
        MarkerOptions marker = new MarkerOptions();
        marker.title(title);
        marker.position(latLng);
        marker.snippet(snipet);
        return marker;
    }

}
