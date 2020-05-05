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


public class DiretorAdapter extends RecyclerView.Adapter implements Serializable {

    private List<Pessoa> listaDiretores;
    private int posicaoRemovidoRecentemente;
    private Pessoa diretorRemovidoRecentemente;
    private Activity activity;
    private FragmentManager fragmentManager;

    DiretorAdapter(Activity activity, FragmentManager fragmentManager, List<Pessoa> listaDiretores) {
        this.listaDiretores = listaDiretores;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diretor_card_item, parent, false);
        return new DiretorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        DiretorViewHolder viewHolder = (DiretorViewHolder)holder;
        viewHolder.fotoDiretor.setImageResource(listaDiretores.get(position).getFotoId());
        viewHolder.nomeDiretor.setText(listaDiretores.get(position).getNome());
        viewHolder.nascimentoDiretor.setText((new SimpleDateFormat("dd/MM/yyyy").format(listaDiretores.get(position).getDataNascimento())));
        final DiretorAdapter adapter = this;
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DiretorFragment diretorFragment = new DiretorFragment();
                diretorFragment.setArguments(((DiretorViewHolder) holder).fragmentBundle(listaDiretores, adapter));
                fragmentManager.beginTransaction().replace(R.id.fragment_container, diretorFragment).addToBackStack("diretor").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDiretores.size();
    }

    void remover(int position){
        posicaoRemovidoRecentemente = position;
        diretorRemovidoRecentemente = listaDiretores.get(position);

        listaDiretores.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,this.getItemCount());

        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.relative_layout), "Item deletado",Snackbar.LENGTH_LONG);
        snackbar.setAction("Desfazer?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaDiretores.add(posicaoRemovidoRecentemente, diretorRemovidoRecentemente);
                notifyItemInserted(posicaoRemovidoRecentemente);
            }
        });
        snackbar.show();
    }

    void inserir(Pessoa diretor){
        listaDiretores.add(diretor);
        notifyItemInserted(getItemCount());
    }

    void mover(int fromPosition, int toPosition){
        if (fromPosition < toPosition)
            for (int i = fromPosition; i < toPosition; i++)
                Collections.swap(listaDiretores, i, i+1);
        else
            for (int i = fromPosition; i > toPosition; i--)
                Collections.swap(listaDiretores, i, i-1);
        notifyItemMoved(fromPosition,toPosition);
    }

    void editar(Pessoa diretor, int position){
        listaDiretores.get(position).setNome(diretor.getNome());
        listaDiretores.get(position).setDataNascimento(diretor.getDataNascimento());
        notifyItemChanged(position);
    }

    public static class DiretorViewHolder extends RecyclerView.ViewHolder{

        ImageView fotoDiretor;
        TextView nomeDiretor;
        TextView nascimentoDiretor;

        DiretorViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            fotoDiretor = (ImageView) itemView.findViewById(R.id.fotoDiretor);
            nomeDiretor = (TextView)itemView.findViewById(R.id.nomeDiretor);
            nascimentoDiretor = (TextView)itemView.findViewById(R.id.nascimentoDiretor);
        }

        public Bundle fragmentBundle(List<Pessoa> listaDiretores, DiretorAdapter adapter){
            Bundle bundle = new Bundle();
            bundle.putSerializable("adapter", adapter);
            bundle.putParcelable("diretor", listaDiretores.get(this.getAdapterPosition()));
            bundle.putInt("position",this.getAdapterPosition());
            bundle.putInt("intent", 1);
            return bundle;
        }
    }
}
