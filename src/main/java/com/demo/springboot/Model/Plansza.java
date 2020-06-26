package com.demo.springboot.Model;

import java.util.ArrayList;

public class Plansza {
    private Boolean turaWalki;
    private Integer liczbaStrzalow;
    private ArrayList<Pole> listaPol = new ArrayList<>();
    public ArrayList<Statek> listaStatkowAI = new ArrayList<>();

    public Plansza() {
    }

    public Plansza(ArrayList<Pole> listaPol) {
        this.listaPol = listaPol;
        this.liczbaStrzalow = 0;
    }

    public Boolean getTuraWalki() {
        return turaWalki;
    }

    public Integer getLiczbaStrzalow() {
        return liczbaStrzalow;
    }

    public ArrayList<Pole> getListaPol() {
        return listaPol;
    }

    public void setTuraWalki(Boolean turaWalki) {
        this.turaWalki = turaWalki;
    }

    public void setLiczbaStrzalow(Integer liczbaStrzalow) {
        this.liczbaStrzalow = liczbaStrzalow;
    }

    public ArrayList<Statek> getListaStatkowAI() {
        return listaStatkowAI;
    }

    public void dodajStatek(Statek statek) {
        listaStatkowAI.add(statek);
    }
}
