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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FilmeAdapter extends RecyclerView.Adapter implements Serializable {

    private List<Filme> listaFilmes;
    private ArrayList<Pessoa> listaAtores;
    private ArrayList<Pessoa> listaDiretores;
    private int posicaoRemovidoRecentemente;
    private Filme filmeRemovidoRecentemente;
    private Activity activity;
    private FragmentManager fragmentManager;

    FilmeAdapter(Activity activity, FragmentManager fragmentManager, List<Filme> listaFilmes, ArrayList<Pessoa> listaAtores, ArrayList<Pessoa> listaDiretores) {
        this.listaFilmes = listaFilmes;
        this.listaAtores = listaAtores;
        this.listaDiretores = listaDiretores;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filme_card_item, parent, false);
        return new FilmeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        FilmeViewHolder viewHolder = (FilmeViewHolder)holder;
        viewHolder.capaFilme.setImageResource(listaFilmes.get(position).getCapaId());
        viewHolder.tituloFilme.setText(listaFilmes.get(position).getTitulo());
        viewHolder.anoFilme.setText(listaFilmes.get(position).getAnoLancamento());
        viewHolder.generoFilme.setText(listaFilmes.get(position).getGenero());
        viewHolder.diretorFilme.setText(listaFilmes.get(position).getDiretor().getNome());
        viewHolder.protagonistaFilme.setText(listaFilmes.get(position).getProtagonista().getNome());
        final FilmeAdapter adapter = this;
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FilmeFragment filmeFragment = new FilmeFragment();
                filmeFragment.setArguments(((FilmeViewHolder) holder).fragmentBundle(listaFilmes, listaAtores, listaDiretores, adapter));
                fragmentManager.beginTransaction().replace(R.id.fragment_container, filmeFragment).addToBackStack("filme").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaFilmes.size();
    }

    void remover(int position){
        posicaoRemovidoRecentemente = position;
        filmeRemovidoRecentemente = listaFilmes.get(position);

        listaFilmes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,this.getItemCount());

        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.relative_layout), "Item deletado",Snackbar.LENGTH_LONG);
        snackbar.setAction("Desfazer?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaFilmes.add(posicaoRemovidoRecentemente, filmeRemovidoRecentemente);
                notifyItemInserted(posicaoRemovidoRecentemente);
            }
        });
        snackbar.show();
    }

    void inserir(Filme filme){
        listaFilmes.add(filme);
        notifyItemInserted(getItemCount());
    }

    void mover(int fromPosition, int toPosition){
        if (fromPosition < toPosition)
            for (int i = fromPosition; i < toPosition; i++)
                Collections.swap(listaFilmes, i, i+1);
        else
            for (int i = fromPosition; i > toPosition; i--)
                Collections.swap(listaFilmes, i, i-1);
        notifyItemMoved(fromPosition,toPosition);
    }

    void editar(Filme filmes, int position){
        listaFilmes.get(position).setTitulo(filmes.getTitulo());
        listaFilmes.get(position).setAnoLancamento(filmes.getAnoLancamento());
        listaFilmes.get(position).setGenero(filmes.getGenero());
        listaFilmes.get(position).setDiretor(filmes.getDiretor());
        listaFilmes.get(position).setProtagonista(filmes.getProtagonista());
        notifyItemChanged(position);
    }

    public static class FilmeViewHolder extends RecyclerView.ViewHolder{

        ImageView capaFilme;
        TextView tituloFilme;
        TextView anoFilme;
        TextView generoFilme;
        TextView diretorFilme;
        TextView protagonistaFilme;

        FilmeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            capaFilme = (ImageView) itemView.findViewById(R.id.capaFilme);
            tituloFilme = (TextView)itemView.findViewById(R.id.tituloFilme);
            anoFilme = (TextView)itemView.findViewById(R.id.anoFilme);
            generoFilme = (TextView)itemView.findViewById(R.id.generoFilme);
            diretorFilme = (TextView)itemView.findViewById(R.id.diretorFilme);
            protagonistaFilme = (TextView)itemView.findViewById(R.id.protagonistaFilme);
        }

        public Bundle fragmentBundle(List<Filme> listaFilmes, ArrayList<Pessoa> listaAtores, ArrayList<Pessoa> listaDiretores, FilmeAdapter adapter){
            Bundle bundle = new Bundle();
            bundle.putSerializable("adapter", adapter);
            bundle.putSerializable("filme", listaFilmes.get(this.getAdapterPosition()));
            bundle.putParcelableArrayList("listaAtores", listaAtores);
            bundle.putParcelableArrayList("listaDiretores", listaDiretores);
            bundle.putInt("position",this.getAdapterPosition());
            bundle.putInt("intent", 1);
            return bundle;
        }
    }
}
