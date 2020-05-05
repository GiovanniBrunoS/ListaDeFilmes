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

public class DiretorFragment extends Fragment {

    private EditText nomeDiretorEdit;
    private EditText anoDiretorEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diretor,container,false);
        final DiretorAdapter adapter = (DiretorAdapter) getArguments().getSerializable("adapter");
        final int intent = getArguments().getInt("intent");

        nomeDiretorEdit = (EditText) view.findViewById(R.id.nomeDiretorEdit);
        anoDiretorEdit = (EditText) view.findViewById(R.id.anoDiretorEdit);
        Button concluirButton = (Button) view.findViewById(R.id.concluirDiretorButton);

        if(intent == 1){
            Pessoa diretor = (Pessoa) getArguments().getParcelable("diretor");
            nomeDiretorEdit.setText(diretor.getNome());
            anoDiretorEdit.setText((new SimpleDateFormat("dd/MM/yyyy").format(diretor.getDataNascimento())));
        }

        concluirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(intent == 0){
                    Boolean fieldsFilled = Boolean.TRUE;
                    if(nomeDiretorEdit.getText().toString().isEmpty()) {
                        nomeDiretorEdit.setError("Campo vazio!");
                        fieldsFilled = Boolean.FALSE;
                    }
                    if(anoDiretorEdit.getText().toString().isEmpty()) {
                        anoDiretorEdit.setError("Campo vazio!");
                        fieldsFilled = Boolean.FALSE;
                    }
                    if(fieldsFilled) {
                        String nomeDiretor = nomeDiretorEdit.getText().toString();
                        String anoDiretor = anoDiretorEdit.getText().toString();
                        try {
                            Pessoa diretor = new Pessoa(getActivity(), nomeDiretor, new SimpleDateFormat("dd/MM/yyyy").parse(anoDiretor));
                            adapter.inserir(diretor);
                        } catch (ParseException e) {
                        }
                        getFragmentManager().popBackStack();
                    }

                }else if (intent == 1){
                    Boolean fieldsFilled = Boolean.TRUE;
                    if(nomeDiretorEdit.getText().toString().isEmpty()) {
                        nomeDiretorEdit.setError("Campo vazio!");
                        fieldsFilled = Boolean.FALSE;
                    }
                    if(anoDiretorEdit.getText().toString().isEmpty()) {
                        anoDiretorEdit.setError("Campo vazio!");
                        fieldsFilled = Boolean.FALSE;
                    }
                    if(fieldsFilled) {
                        Pessoa diretor = (Pessoa) getArguments().getParcelable("diretor");
                        int position = getArguments().getInt("position");
                        diretor.setNome(nomeDiretorEdit.getText().toString());
                        try {
                            diretor.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(anoDiretorEdit.getText().toString()));
                            adapter.editar(diretor, position);
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
