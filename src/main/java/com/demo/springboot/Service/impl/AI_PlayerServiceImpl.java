package com.demo.springboot.Service.impl;

import com.demo.springboot.Model.Plansza;
import com.demo.springboot.Model.Statek;
import com.demo.springboot.Service.AI_PlayerService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class AI_PlayerServiceImpl implements AI_PlayerService {
    @Override
    public void ustawFlote(){
        Plansza planszaAI;
        Statek statek2 = new Statek(1, 2);
        Statek statek3_1 = new Statek(2, 3);
        Statek statek3_2 = new Statek(3, 3);
        Statek statek4 = new Statek(4, 4);
        Statek statek5 = new Statek(5, 5);
        while (statek2.getListaPol().size() != 2) ustawStatek(statek2);
        while (statek3_1.getListaPol().size() != 3) ustawStatek(statek3_1);
        while (statek3_2.getListaPol().size() != 3) ustawStatek(statek3_2);
        while (statek4.getListaPol().size() != 4) ustawStatek(statek4);
        while (statek5.getListaPol().size() != 5) ustawStatek(statek5);
        // oczekuj na dalsze rozkazy
    }

    @Override
    public void ustawStatek(Statek statek){
        statek.dodajPole(pole);
        int czubek_id = ThreadLocalRandom.current().nextInt(0, 99 + 1);
        int kierunek = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        // pętla losująca pola statku
    }

    @Override
    public void atakuj(){
        int slepak = ThreadLocalRandom.current().nextInt(0, 99 + 1);
        strzelaj(slepak);
    }

    @Override
    public int strzelaj(int id_pola){
        return 0;
    }

    @Override
    public void czekaj(){

    }

    @Override
    public void wykryj(){

    }
}
