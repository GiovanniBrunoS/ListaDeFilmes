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


public class DiretorListFragment extends Fragment {

    FloatingActionButton novoDiretorButton;

    public static DiretorListFragment newInstance(DiretorAdapter adapter) {
        DiretorListFragment myFragment = new DiretorListFragment();
        Bundle args = new Bundle();
        args.putSerializable("adapter", adapter);
        myFragment.setArguments(args);
        return myFragment;
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diretor_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewDiretor);
        DiretorAdapter adapter = (DiretorAdapter) getArguments().getSerializable("adapter");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelp(adapter));
        touchHelper.attachToRecyclerView(recyclerView);
        novoDiretorButton = (FloatingActionButton) view.findViewById(R.id.buttonNovoDiretor);
        novoDiretorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiretorFragment diretorFragment = new DiretorFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("adapter", (DiretorAdapter) getArguments().getSerializable("adapter"));
                bundle.putInt("intent", 0);
                diretorFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, diretorFragment).addToBackStack("diretor").commit();
            }
        });
        return view;
    }
}
