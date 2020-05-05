package com.example.listadefilmes;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class AtorAdapter extends RecyclerView.Adapter implements Serializable {

    private List<Pessoa> listaAtores;
    private int posicaoRemovidoRecentemente;
    private Pessoa atorRemovidoRecentemente;
    private Activity activity;
    private FragmentManager fragmentManager;

    AtorAdapter(Activity activity, FragmentManager fragmentManager, List<Pessoa> listaAtores) {
        this.listaAtores = listaAtores;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ator_card_item, parent, false);
        return new AtorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        AtorViewHolder viewHolder = (AtorViewHolder)holder;
        viewHolder.fotoAtor.setImageResource(listaAtores.get(position).getFotoId());
        viewHolder.nomeAtor.setText(listaAtores.get(position).getNome());
        viewHolder.nascimentoAtor.setText((new SimpleDateFormat("dd/MM/yyyy").format(listaAtores.get(position).getDataNascimento())));
        final AtorAdapter adapter = this;
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AtorFragment atorFragment = new AtorFragment();
                atorFragment.setArguments(((AtorViewHolder) holder).fragmentBundle(listaAtores, adapter));
                fragmentManager.beginTransaction().replace(R.id.fragment_container, atorFragment).addToBackStack("ator").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaAtores.size();
    }

    void remover(int position){
        posicaoRemovidoRecentemente = position;
        atorRemovidoRecentemente = listaAtores.get(position);

        listaAtores.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,this.getItemCount());

        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.relative_layout), "Item deletado",Snackbar.LENGTH_LONG);
        snackbar.setAction("Desfazer?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaAtores.add(posicaoRemovidoRecentemente, atorRemovidoRecentemente);
                notifyItemInserted(posicaoRemovidoRecentemente);
            }
        });
        snackbar.show();
    }

    void inserir(Pessoa ator){
        listaAtores.add(ator);
        notifyItemInserted(getItemCount());
    }

    void mover(int fromPosition, int toPosition){
        if (fromPosition < toPosition)
            for (int i = fromPosition; i < toPosition; i++)
                Collections.swap(listaAtores, i, i+1);
        else
            for (int i = fromPosition; i > toPosition; i--)
                Collections.swap(listaAtores, i, i-1);
        notifyItemMoved(fromPosition,toPosition);
    }

    void editar(Pessoa ator, int position){
        listaAtores.get(position).setNome(ator.getNome());
        listaAtores.get(position).setDataNascimento(ator.getDataNascimento());
        notifyItemChanged(position);
    }

    public static class AtorViewHolder extends RecyclerView.ViewHolder{

        ImageView fotoAtor;
        TextView nomeAtor;
        TextView nascimentoAtor;

        AtorViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            fotoAtor = (ImageView) itemView.findViewById(R.id.fotoAtor);
            nomeAtor = (TextView)itemView.findViewById(R.id.nomeAtor);
            nascimentoAtor = (TextView)itemView.findViewById(R.id.nascimentoAtor);
        }

        public Bundle fragmentBundle(List<Pessoa> listaAtores, AtorAdapter adapter){
            Bundle bundle = new Bundle();
            bundle.putSerializable("adapter", adapter);
            bundle.putParcelable("ator", listaAtores.get(this.getAdapterPosition()));
            bundle.putInt("position",this.getAdapterPosition());
            bundle.putInt("intent", 1);
            return bundle;
        }
    }
}
