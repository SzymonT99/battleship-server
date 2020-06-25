package com.demo.springboot.Model;

import java.util.ArrayList;

public class Statek {
    private Integer id;
    private Integer dlugosc;
    private Integer hp;
    private char kierunek;
    private ArrayList<Pole> listaPol = new ArrayList<>();

    public Statek(Integer id, Integer dlugosc) {
        this.id = id;
        this.dlugosc = dlugosc;
        this.hp = dlugosc;
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

    public char getKierunek() {
        return kierunek;
    }

    public Integer getHp() {
        return hp;
    }

    public void setKierunek(char kierunek) {
        this.kierunek = kierunek;
    }

    public void zmniejszHp() {
        this.hp = this.hp - 1;
    }
}

