package com.example.listadefilmes;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadefilmes.FilmeAdapter;

public class TouchHelp extends ItemTouchHelper.SimpleCallback{

    private FilmeAdapter filmeAdapter;
    private AtorAdapter atorAdapter;
    private DiretorAdapter diretorAdapter;

    public TouchHelp(FilmeAdapter adapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT);
        this.filmeAdapter = adapter;
    }

    public TouchHelp(AtorAdapter adapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT);
        this.atorAdapter = adapter;
    }

    public TouchHelp(DiretorAdapter adapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT);
        this.diretorAdapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        if(filmeAdapter != null) {
            filmeAdapter.mover(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }else if (atorAdapter != null){
            atorAdapter.mover(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }else if (diretorAdapter != null){
            diretorAdapter.mover(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if(filmeAdapter != null) {
            filmeAdapter.remover(viewHolder.getAdapterPosition());
        }else if (atorAdapter != null){
            atorAdapter.remover(viewHolder.getAdapterPosition());
        }else if (diretorAdapter != null){
            diretorAdapter.remover(viewHolder.getAdapterPosition());
        }
    }
}
