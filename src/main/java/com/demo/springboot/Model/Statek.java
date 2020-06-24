package com.demo.springboot.Model;

import java.util.ArrayList;

public class Statek {
    private Integer id;
    private Integer dlugosc;
    private Integer kierunek;
    private ArrayList<Pole> listaPol = new ArrayList<>();

    public Statek(Integer id, Integer dlugosc) {
        this.id = id;
        this.dlugosc = dlugosc;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDlugosc() {
        return dlugosc;
    }

    public void dodajPole(Pole pole){
        listaPol.add(pole);
    }

    public ArrayList<Pole> getListaPol() {
        return listaPol;
    }

    public Integer getKierunek() {
        return kierunek;
    }

    public void setKierunek(Integer kierunek) {
        this.kierunek = kierunek;
    }
}

