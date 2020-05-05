package com.example.listadefilmes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AtorFragment extends Fragment {

    private EditText nomeAtorEdit;
    private EditText anoAtorEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ator,container,false);
        final AtorAdapter adapter = (AtorAdapter) getArguments().getSerializable("adapter");
        final int intent = getArguments().getInt("intent");

        nomeAtorEdit = (EditText) view.findViewById(R.id.nomeAtorEdit);
        anoAtorEdit = (EditText) view.findViewById(R.id.anoAtorEdit);
        Button concluirButton = (Button) view.findViewById(R.id.concluirAtorButton);

        if(intent == 1){
            Pessoa ator = (Pessoa) getArguments().getParcelable("ator");
            nomeAtorEdit.setText(ator.getNome());
            anoAtorEdit.setText(ator.getDataNascimento().toString());
            anoAtorEdit.setText((new SimpleDateFormat("dd/MM/yyyy").format(ator.getDataNascimento())));
        }

        concluirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intent == 0){
                    Boolean fieldsFilled = Boolean.TRUE;
                    if(nomeAtorEdit.getText().toString().isEmpty()) {
                        nomeAtorEdit.setError("Campo vazio!");
                        fieldsFilled = Boolean.FALSE;
                    }
                    if(anoAtorEdit.getText().toString().isEmpty()) {
                        anoAtorEdit.setError("Campo vazio!");
                        fieldsFilled = Boolean.FALSE;
                    }
                    if(fieldsFilled) {
                        String nomeAtor = nomeAtorEdit.getText().toString();
                        String anoAtor = anoAtorEdit.getText().toString();
                        try {
                            Pessoa ator = new Pessoa(getActivity(), nomeAtor, new SimpleDateFormat("dd/MM/yyyy").parse(anoAtor));
                            adapter.inserir(ator);
                        } catch (ParseException e) {
                        }
                        getFragmentManager().popBackStack();
                    }

                }else if (intent == 1){
                    Boolean fieldsFilled = Boolean.TRUE;
                    if(nomeAtorEdit.getText().toString().isEmpty()) {
                        nomeAtorEdit.setError("Campo vazio!");
                        fieldsFilled = Boolean.FALSE;
                    }
                    if(anoAtorEdit.getText().toString().isEmpty()) {
                        anoAtorEdit.setError("Campo vazio!");
                        fieldsFilled = Boolean.FALSE;
                    }
                    if(fieldsFilled) {
                        Pessoa ator = (Pessoa) getArguments().getParcelable("ator");
                        int position = getArguments().getInt("position");
                        ator.setNome(nomeAtorEdit.getText().toString());
                        try {
                            ator.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(anoAtorEdit.getText().toString()));
                            adapter.editar(ator, position);
                        } catch (ParseException e) {
                        }
                        getFragmentManager().popBackStack();
                    }
                }
            }
        });
        return view;
    }
}
