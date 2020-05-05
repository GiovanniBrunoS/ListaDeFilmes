package com.example.listadefilmes;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinAdapter extends ArrayAdapter<Pessoa> {

    private Context context;
    private List<Pessoa> listaPessoas;

    public SpinAdapter(Context context, int textViewResourceId, List<Pessoa> listaPessoas) {
        super(context, textViewResourceId, listaPessoas);
        this.context = context;
        this.listaPessoas = listaPessoas;
    }

    @Override
    public int getCount(){
        return listaPessoas.size();
    }

    @Override
    public Pessoa getItem(int position){
        return listaPessoas.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(listaPessoas.get(position).getNome());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(listaPessoas.get(position).getNome());

        return label;
    }
}
