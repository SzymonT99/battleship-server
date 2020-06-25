package com.demo.springboot.Service.impl;

import com.demo.springboot.Model.Plansza;
import com.demo.springboot.Model.Pole;
import com.demo.springboot.Model.Statek;
import com.demo.springboot.Service.AI_PlayerService;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AI_PlayerServiceImpl implements AI_PlayerService {
    private Plansza planszaGracza;
    @Override
    public void inicjalizujPlansze(){
        ArrayList<Pole> polaPlanszy = new ArrayList<>();
        int i = 0;
        for (int z=0;z<100;z++) {
            if (i < 10) {
                for (int j = 1; j < 10; j++) {
                    String tmp = String.valueOf(i) + String.valueOf(j);
                    polaPlanszy.add(new Pole(Integer.parseInt(tmp), 0));
                }
                polaPlanszy.add(new Pole(Integer.parseInt(String.valueOf(i + 1) + String.valueOf(0)), 0));

            }
            i++;
            System.out.println("id: " + polaPlanszy.get(i - 1).getId() + " x: " + polaPlanszy.get(i - 1).getWsp_x() + " y: " + polaPlanszy.get(i - 1).getWsp_y());
        }
        planszaGracza = new Plansza(polaPlanszy);
    }

    @Override
    public void ustawFlote(){
        Plansza planszaAI;
        Statek statek2 = new Statek(1, 2);
        Statek statek3_1 = new Statek(2, 3);
        Statek statek3_2 = new Statek(3, 3);
        Statek statek4 = new Statek(4, 4);
        Statek statek5 = new Statek(5, 5);
        System.out.println("Ustawiam statek 2");
        ustawStatek(statek2);


        System.out.println("Ustawiam statek 3-1");
        ustawStatek(statek3_1);
        System.out.println("Ustawiam statek 3-2");
        ustawStatek(statek3_2);    // pomyślimy, czy to jakoś usprawnić? średnio to wygląda
        System.out.println("Ustawiam statek 4");
        ustawStatek(statek4);
        System.out.println("Ustawiam statek 5");
        ustawStatek(statek5);
        // oczekuj na dalsze rozkazy
    }

    @Override
    public void ustawStatek(Statek statek){
        int id = ThreadLocalRandom.current().nextInt(1, 101);    // czubek statku
        System.out.println("wylosowane id: " + id);
        Pole pole = planszaGracza.getListaPol().get(id-1);
        statek.dodajPole(pole);

        List<Integer> dostepne = new ArrayList<>();
        dostepne.add(id-1);   //y    kierunek
        dostepne.add(id+1);   //y    kierunek
        dostepne.add(id-10);  //x    kierunek
        dostepne.add(id+10);  //x    kierunek
        //int[] dostepne = {id-1, id+1, id-10, id+10};
        //id = ThreadLocalRandom.current().nextInt(dostepne.length);

        id = dostepne.get(ThreadLocalRandom.current().nextInt(dostepne.size()));
        Pole drugie_pole = planszaGracza.getListaPol().get(id);
        //Pole drugie_pole = planszaGracza.getListaPol().get(dostepne[id]);
        System.out.println("wylosowane id kierunku: " + id);
        if (drugie_pole.getWsp_x() == pole.getWsp_x()) {
            statek.setKierunek('x');
            dostepne.remove(0);
            dostepne.remove(0);
            //return pierwsze.getWsp_y() + 1 == drugie_pole.getWsp_y() || pierwsze.getWsp_y() - 1 == drugie_pole.getWsp_y();
        } else if (drugie_pole.getWsp_y() == pole.getWsp_y()) {
            statek.setKierunek('y');
            dostepne.remove(3);
            dostepne.remove(2);  // zerowy znika, więc 1 wskakuje na jego miejsce
            //return pierwsze.getWsp_x() + 1 == pole.getWsp_x() || pierwsze.getWsp_x() - 1 == pole.getWsp_x();
        }
        statek.dodajPole(drugie_pole);
        dostepne.remove(Integer.valueOf(id));
        System.out.println("wylosowany kierunek : " + statek.getKierunek());
        while (statek.getListaPol().size() != statek.getDlugosc()){
            if(statek.getKierunek()=='x'){
                dostepne.add(id-10);
                dostepne.add(id+10);
            }
            else if (statek.getKierunek()=='y'){
                dostepne.add(id-1);
                dostepne.add(id+1);
            }
            id = dostepne.get(ThreadLocalRandom.current().nextInt(dostepne.size()));
            pole = planszaGracza.getListaPol().get(id);
            if(pole.getStan()==0)  statek.dodajPole(pole);
            dostepne.remove(Integer.valueOf(id));
        }
        // pętla losująca pola statku
        // wypisuję pola statku dla sprawdzenia
        ArrayList<Pole> lista=statek.getListaPol();
        for(int i=0;i<lista.size();i++){
            System.out.println("Statek id: " + statek.getId() + "id: " + lista.get(i).getId() + "x: "+ lista.get(i).getWsp_x() + " y: " + lista.get(i).getWsp_y());

        }
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
