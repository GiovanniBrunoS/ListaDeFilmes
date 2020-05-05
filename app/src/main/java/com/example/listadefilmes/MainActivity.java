package com.example.listadefilmes;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FilmeAdapter filmeAdapter;
    private AtorAdapter atorAdapter;
    private DiretorAdapter diretorAdapter;
    private NavigationView navigationView;
    private List<Filme> listaFilmes;
    private ArrayList<Pessoa> listaAtores;
    private ArrayList<Pessoa> listaDiretores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setTitle("Zetflix");
        if (savedInstanceState == null) {
            criarNovasListas();
            filmeAdapter = new FilmeAdapter(this, getSupportFragmentManager(), listaFilmes, listaAtores, listaDiretores);
            atorAdapter = new AtorAdapter(this, getSupportFragmentManager(), listaAtores);
            diretorAdapter = new DiretorAdapter(this, getSupportFragmentManager(), listaDiretores);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FilmeListFragment.newInstance(filmeAdapter, listaAtores, listaDiretores)).commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_filmes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FilmeListFragment.newInstance(filmeAdapter, listaAtores, listaDiretores)).commit();
                break;
            case R.id.nav_atores:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, AtorListFragment.newInstance(atorAdapter)).commit();
                break;
            case R.id.nav_diretores:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, DiretorListFragment.newInstance(diretorAdapter)).commit();
                break;
            }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("SimpleDateFormat")
    private void criarNovasListas(){
        listaFilmes = new ArrayList<>();
        listaAtores = new ArrayList<>();
        listaDiretores = new ArrayList<>();
        try {
            listaAtores.add(new Pessoa(this, "John Travolta", new SimpleDateFormat("dd/MM/yyyy").parse("18/02/1954"), "john_travolta"));
            listaDiretores.add(new Pessoa(this, "Quentin Tarantino", new SimpleDateFormat("dd/MM/yyyy").parse("27/03/1965"), "quentin_tarantino"));
            listaFilmes.add(new Filme(this,"Pulp Fiction", "1994", "Crime", listaDiretores.get(0), listaAtores.get(0), "pulp_fiction"));

            listaAtores.add(new Pessoa(this, "Marlon Brando",new SimpleDateFormat("dd/MM/yyyy").parse("03/04/1924"), "marlon_brando"));
            listaDiretores.add(new Pessoa(this, "Francis Ford Coppola",new SimpleDateFormat("dd/MM/yyyy").parse("07/04/1939"), "francis_ford_coppola"));
            listaFilmes.add(new Filme(this,"O Poderoso Chefão", "1972", "Crime", listaDiretores.get(1), listaAtores.get(1), "o_poderoso_chefao"));

            listaAtores.add(new Pessoa(this, "Christian Bale", new SimpleDateFormat("dd/MM/yyyy").parse("30/01/1974"), "christian_bale"));
            listaDiretores.add(new Pessoa(this, "Cristopher Nolan", new SimpleDateFormat("dd/MM/yyyy").parse("30/07/1970"), "cristopher_nolan"));
            listaFilmes.add(new Filme(this,"Batman: O Cavaleiro das Trevas", "2008", "Ação", listaDiretores.get(2), listaAtores.get(2), "batman_o_cavaleiro_das_trevas"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}