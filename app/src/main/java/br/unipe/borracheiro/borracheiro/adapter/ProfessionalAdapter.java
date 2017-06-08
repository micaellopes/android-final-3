package br.unipe.borracheiro.borracheiro.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.unipe.borracheiro.borracheiro.R;
import br.unipe.borracheiro.borracheiro.fragments.ProfListFragment;
import br.unipe.borracheiro.borracheiro.model.Professional;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by Pontes on 07/06/2017.
 */

public class ProfessionalAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    private List<Professional> professionals;
    private Activity activity;

    public ProfessionalAdapter(List<Professional> professionals, Context context) {
        this.professionals = professionals;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return professionals.size();
    }

    @Override
    public Professional getItem(int position) {
        return professionals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return professionals.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_view, parent, false);
            holder = new ViewHolder();
            holder.txtname = (TextView) convertView.findViewById(R.id.txt_label);
            holder.txtphone = (TextView) convertView.findViewById(R.id.btn_ligar);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtname.setText(professionals.get(position).getName());
//        holder.txtphone.setText(professionals.get(position).getPhone());

        return convertView;
    }


    static class ViewHolder {
        TextView txtname, txtphone;
    }

}