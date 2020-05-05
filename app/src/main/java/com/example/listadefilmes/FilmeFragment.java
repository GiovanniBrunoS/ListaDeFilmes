package com.example.listadefilmes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class FilmeFragment extends Fragment {

    private EditText nomeFilmeEdit;
    private EditText anoFilmeEdit;
    private EditText generoFilmeEdit;
    private Spinner diretorFilmeSpinner;
    private Spinner protagonistaFilmeSpinner;
    private List<Pessoa> listaAtores;
    private List<Pessoa> listaDiretores;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filme,container,false);
        final FilmeAdapter adapter = (FilmeAdapter) getArguments().getSerializable("adapter");
        final int intent = getArguments().getInt("intent");
        listaAtores = getArguments().getParcelableArrayList("listaAtores");
        listaDiretores = getArguments().getParcelableArrayList("listaDiretores");

        SpinAdapter atoresDataAdapter = new SpinAdapter(getActivity(), android.R.layout.simple_spinner_item, listaAtores);
        atoresDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SpinAdapter diretoresDataAdapter = new SpinAdapter(getActivity(), android.R.layout.simple_spinner_item, listaDiretores);
        diretoresDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        nomeFilmeEdit = (EditText) view.findViewById(R.id.nomeFilmeEdit);
        anoFilmeEdit = (EditText) view.findViewById(R.id.anoFilmeEdit);
        generoFilmeEdit = (EditText) view.findViewById(R.id.generoFilmeEdit);
        diretorFilmeSpinner = (Spinner) view.findViewById(R.id.diretorFilmeSpinner);
        protagonistaFilmeSpinner = (Spinner) view.findViewById(R.id.protagonistaFilmeSpinner);
        Button concluirButton = (Button) view.findViewById(R.id.concluirFilmeButton);

        diretorFilmeSpinner.setAdapter(diretoresDataAdapter);
        protagonistaFilmeSpinner.setAdapter(atoresDataAdapter);

        if(intent == 1){
            Filme filme = (Filme) getArguments().getSerializable("filme");
            diretorFilmeSpinner.setSelection(listaDiretores.indexOf(filme.getDiretor()));
            protagonistaFilmeSpinner.setSelection(listaAtores.indexOf(filme.getProtagonista()));
            nomeFilmeEdit.setText(filme.getTitulo());
            anoFilmeEdit.setText(filme.getAnoLancamento());
            generoFilmeEdit.setText(filme.getGenero());
        }

        concluirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intent == 0){
                    Boolean fieldsFilled = Boolean.TRUE;
                    if(nomeFilmeEdit.getText().toString().isEmpty()) {
                        nomeFilmeEdit.setError("Campo vazio!");
                        fieldsFilled = Boolean.FALSE;
                    }
                    if(anoFilmeEdit.getText().toString().isEmpty()) {
                        anoFilmeEdit.setError("Campo vazio!");
                        fieldsFilled = Boolean.FALSE;
                    }
                    if(generoFilmeEdit.getText().toString().isEmpty()) {
                        generoFilmeEdit.setError("Campo vazio!");
                        fieldsFilled = Boolean.FALSE;
                    }
                    if(fieldsFilled) {
                        String nomeFilme = nomeFilmeEdit.getText().toString();
                        String anoFilme = anoFilmeEdit.getText().toString();
                        String generoFilme = generoFilmeEdit.getText().toString();
                        Pessoa diretorFilme = (Pessoa) diretorFilmeSpinner.getSelectedItem();
                        Pessoa protagonistaFilme = (Pessoa) protagonistaFilmeSpinner.getSelectedItem();
                        Filme filme = new Filme(getActivity(), nomeFilme, anoFilme, generoFilme, diretorFilme, protagonistaFilme);
                        adapter.inserir(filme);
                        getFragmentManager().popBackStack();
                    }

                }else if (intent == 1){
                    Boolean fieldsFilled = Boolean.TRUE;
                    if(nomeFilmeEdit.getText().toString().isEmpty()) {
                        nomeFilmeEdit.setError("Campo vazio!");
                        fieldsFilled = Boolean.FALSE;
                    }
                    if(anoFilmeEdit.getText().toString().isEmpty()) {
                        anoFilmeEdit.setError("Campo vazio!");
                        fieldsFilled = Boolean.FALSE;
                    }
                    if(generoFilmeEdit.getText().toString().isEmpty()) {
                        generoFilmeEdit.setError("Campo vazio!");
                        fieldsFilled = Boolean.FALSE;
                    }
                    if(fieldsFilled) {
                        Filme filme = (Filme) getArguments().getSerializable("filme");
                        int position = getArguments().getInt("position");
                        filme.setTitulo(nomeFilmeEdit.getText().toString());
                        filme.setAnoLancamento(anoFilmeEdit.getText().toString());
                        filme.setGenero(generoFilmeEdit.getText().toString());
                        filme.setDiretor((Pessoa) diretorFilmeSpinner.getSelectedItem());
                        filme.setProtagonista((Pessoa) protagonistaFilmeSpinner.getSelectedItem());
                        adapter.editar(filme, position);
                        getFragmentManager().popBackStack();
                    }
                }
            }
        });
        return view;
    }

}
