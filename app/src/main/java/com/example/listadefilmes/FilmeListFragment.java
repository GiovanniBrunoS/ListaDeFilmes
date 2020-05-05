package com.example.listadefilmes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class FilmeListFragment extends Fragment {

    FloatingActionButton novoFilmeButton;

    public static FilmeListFragment newInstance(FilmeAdapter adapter, ArrayList<Pessoa> listaAtores, ArrayList<Pessoa> listaDiretores) {
        FilmeListFragment myFragment = new FilmeListFragment();
        Bundle args = new Bundle();
        args.putSerializable("adapter", adapter);
        args.putParcelableArrayList("listaAtores", listaAtores);
        args.putParcelableArrayList("listaDiretores", listaDiretores);
        myFragment.setArguments(args);
        return myFragment;
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filme_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewFilme);
        FilmeAdapter adapter = (FilmeAdapter) getArguments().getSerializable("adapter");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelp(adapter));
        touchHelper.attachToRecyclerView(recyclerView);
        novoFilmeButton = (FloatingActionButton) view.findViewById(R.id.buttonNovoFilme);
        novoFilmeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilmeFragment filmeFragment = new FilmeFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("adapter", (FilmeAdapter) getArguments().getSerializable("adapter"));
                bundle.putParcelableArrayList("listaAtores", getArguments().getParcelableArrayList("listaAtores"));
                bundle.putParcelableArrayList("listaDiretores", getArguments().getParcelableArrayList("listaDiretores"));
                bundle.putInt("intent", 0);
                filmeFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, filmeFragment).addToBackStack("filme").commit();
            }
        });
        return view;
    }
}
