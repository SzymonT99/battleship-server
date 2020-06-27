package com.demo.springboot.Service.impl;

import com.demo.springboot.Model.Plansza;
import com.demo.springboot.Model.Pole;
import com.demo.springboot.Model.Statek;
import com.demo.springboot.Service.AI_PlayerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AI_PlayerServiceImpl implements AI_PlayerService {
    private Plansza planszaAI;
    public List<Integer> dostepne_strzaly = new ArrayList<>();
    public List<Integer> oddane_strzaly = new ArrayList<>();
    private int[] trafione_id = {0, 0, 0, 0, 0};
    public List<Integer> wyjatkowe_strzaly = new ArrayList<>();
    private int licznik_ataku=0;
    private char kierunek_ataku='0';
    private int ids=0;
    private int idsTMP=0;
    private Pole pole;

    public AI_PlayerServiceImpl() throws Exception {
        inicjalizujPlansze();
        ustawFlote();
    }
    @Override
    public void inicjalizujPlansze(){
        System.out.println("Inicjalizacja planszy");
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
            //System.out.println("id: " + polaPlanszy.get(i - 1).getId() + " x: " + polaPlanszy.get(i - 1).getWsp_x() + " y: " + polaPlanszy.get(i - 1).getWsp_y());
        }
        this.planszaAI = new Plansza(polaPlanszy);
    }

    @Override
    public void ustawFlote() throws Exception {
        Statek statek2 = new Statek(1, 2);
        Statek statek3_1 = new Statek(2, 3);
        Statek statek3_2 = new Statek(3, 3);
        Statek statek4 = new Statek(4, 4);
        Statek statek5 = new Statek(5, 5);
        // zmieniłem kolejność na odwrotną, mniejsza szansa wyjątku, to mniej obliczneń.
        System.out.println("Ustawiam statek 2");
        planszaAI.dodajStatek(statek2);
        ustawStatek(statek2);
        System.out.println("Ustawiam statek 3-1");
        planszaAI.dodajStatek(statek3_1);
        ustawStatek(statek3_1);
        System.out.println("Ustawiam statek 3-2");
        planszaAI.dodajStatek(statek3_2);
        ustawStatek(statek3_2);
        System.out.println("Ustawiam statek 4");
        planszaAI.dodajStatek(statek4);
        ustawStatek(statek4);
        System.out.println("Ustawiam statek 5");
        ustawStatek(statek5);
        planszaAI.dodajStatek(statek5);
    }

    @Override
    public void ustawStatek(Statek statek) throws Exception {
        try {
            Pole pole = planszaAI.getListaPol().get(ThreadLocalRandom.current().nextInt(1, 101) - 1); // czubek statku
            while(pole.getStan()!=0){
                pole = planszaAI.getListaPol().get(ThreadLocalRandom.current().nextInt(1, 101) - 1);
            }
            int id=pole.getId();
            System.out.println("wylosowane id: " + id);
            statek.dodajPole(pole);
            pole.setStan(statek.getId());

            List<Integer> dostepne = new ArrayList<>();
                dodajKierunki(id,dostepne,"ustaw");
            int tmpID = id;
            Pole drugie_pole = planszaAI.getListaPol().get(dostepne.get(ThreadLocalRandom.current().nextInt(dostepne.size())) - 1);
            while(drugie_pole.getStan()!=0){
                drugie_pole = planszaAI.getListaPol().get(dostepne.get(ThreadLocalRandom.current().nextInt(dostepne.size())) - 1);
            }
            id=drugie_pole.getId();
            System.out.println("wylosowane id kierunku: " + id);
            //Pole drugie_pole = planszaGracza.getListaPol().get(dostepne[id]);
            if (drugie_pole.getWsp_x() == pole.getWsp_x()) {
                statek.setKierunek('x');
                dostepne.remove(Integer.valueOf(tmpID - 1));
                dostepne.remove(Integer.valueOf(tmpID + 1));
            } else if (drugie_pole.getWsp_y() == pole.getWsp_y()) {
                statek.setKierunek('y');
                dostepne.remove(Integer.valueOf(tmpID - 10));
                dostepne.remove(Integer.valueOf(tmpID + 10));
            }
            statek.dodajPole(drugie_pole);
            drugie_pole.setStan(statek.getId());
            dostepne.remove(Integer.valueOf(id));
            System.out.println("wylosowany kierunek : " + statek.getKierunek());
            while (statek.getListaPol().size() != statek.getDlugosc()) {
                    dodajZKierunku(statek.getKierunek(),id,dostepne,"ustaw");
                id = dostepne.get(ThreadLocalRandom.current().nextInt(dostepne.size()));   //IllegalArgumentException
                System.out.println("wylosowane kolejne id : " + id);
                pole = planszaAI.getListaPol().get(id - 1);
                if (pole.getStan() == 0) {
                    statek.dodajPole(pole);
                    pole.setStan(statek.getId());
                }
                dostepne.remove(Integer.valueOf(id));
            }
        }
        catch (Exception e){
            usunStatek(statek,0);
            statek.getListaPol().clear();
            System.out.println("Statek ma usterke - reset. --------------------------------------------------------------------------------------------------");
            ustawStatek(statek);
        }

        ArrayList<Pole> lista=statek.getListaPol();
        for(int i=0;i<lista.size();i++){
            System.out.println("Statek id: " + statek.getId() + "id: " + lista.get(i).getId() + "x: "+ lista.get(i).getWsp_x() + " y: " + lista.get(i).getWsp_y());
        }
    }

    public void dodaj(int id, List<Integer> dostepne, String operacja){
        if(operacja=="ustaw") {
            if (planszaAI.getListaPol().get(id - 1).getStan() == 0)
                dostepne.add(id); //przy strzelaniu tutaj musi być warunek stan>0 && stan<6 !!!!!!
            else {
                System.out.println("Tu juz jest jakis statek: " + id);
            }
        }
        else{
            if (!oddane_strzaly.contains(id)) dostepne.add(id);
        }
    }
    public void usunStatek(Statek statek, int stan) {
        for (Pole pole : statek.getListaPol()) {
            planszaAI.getListaPol().get(pole.getId() - 1).setStan(stan);
        }
    }
    @Override
    public void dodajKierunki(int id,List<Integer> lista, String operacja){
        if (id > 1 && (id - 1) % 10 != 0) dodaj(id-1,lista,operacja);       //y    kierunek
        if (id < 100 && id % 10 != 0) dodaj(id+1,lista,operacja);           //y    kierunek
        if (id > 10) dodaj(id-10,lista,operacja);                           //x    kierunek
        if (id < 90) dodaj(id+10,lista,operacja);                           //x    kierunek
    }
    @Override
    public void dodajZKierunku(char kierunek, int id, List<Integer> lista, String operacja){
        if (kierunek == 'x') {
            if (id - 10 < 1)
                dodaj(id + 10, lista,operacja);
            else if (id + 10 >= 101)
                dodaj(id - 10, lista,operacja);
            else {
                dodaj(id + 10, lista,operacja);
                dodaj(id - 10, lista,operacja);
            }
        } else if (kierunek == 'y') {
            if (id == 1 || id == 11 || id == 21 || id == 31 || id == 41 || id == 51 || id == 61 || id == 71 || id == 81 || id == 91)
                dodaj(id + 1, lista,operacja);
            else if (id == 10 || id == 20 || id == 30 || id == 40 || id == 50 || id == 60 || id == 70 || id == 80 || id == 90 || id == 100)
                dodaj(id - 1, lista,operacja);
            else {
                dodaj(id - 1, lista,operacja);
                dodaj(id + 1, lista,operacja);
            }
        }
    }

    @Override
    public int atakuj() throws Exception{
        try {
            if (licznik_ataku == 0) {
                ids = ThreadLocalRandom.current().nextInt(1, 101);
                idsTMP=ids;
            } else {
                if (licznik_ataku==2) { // 2 strzał z kolei
                    //dodajKierunki(ids, dostepne_strzaly, "atakuj");
                    ids=dostepne_strzaly.get(ThreadLocalRandom.current().nextInt(dostepne_strzaly.size()));   //IllegalArgumentException
                    /*Pole drugie_pole = planszaAI.getListaPol().get(ids - 1);
                    if (drugie_pole.getWsp_x() == planszaAI.getListaPol().get(idsTMP - 1).getWsp_x()) {
                        setKierunek_ataku('x');
                        System.out.println("Ustawiam kierunek ataku: " + kierunek_ataku);
                        dostepne_strzaly.remove(Integer.valueOf(idsTMP - 1));
                        dostepne_strzaly.remove(Integer.valueOf(idsTMP + 1));
                    } else if (drugie_pole.getWsp_y() == planszaAI.getListaPol().get(idsTMP - 1).getWsp_y()) {
                        setKierunek_ataku('y');
                        System.out.println("Ustawiam kierunek ataku: " + kierunek_ataku);
                        dostepne_strzaly.remove(Integer.valueOf(idsTMP - 10));
                        dostepne_strzaly.remove(Integer.valueOf(idsTMP + 10));
                    }*/
                } else {
                    ids=dostepne_strzaly.get(ThreadLocalRandom.current().nextInt(dostepne_strzaly.size()));
                }
                dostepne_strzaly.remove(Integer.valueOf(ids));
            }
            setLicznik_ataku(licznik_ataku + 1);
        }catch(Exception e) {
            // pobierz otoczenie ostatnich trafień
            switch(kierunek_ataku) { // odwróć kierunek strzału
                case 'x':
                    setKierunek_ataku('y');
                    break;
                case 'y':
                    setKierunek_ataku('x');
                    break;
                default:
                    setKierunek_ataku('0');
            }
            System.out.println("PUSTA LISTA+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }

        oddane_strzaly.add(Integer.valueOf(ids));
        return ids;
    }

    @Override
    public Plansza getPlanszaAI(){
        return planszaAI;
    }
    @Override
    public int getLicznik_ataku() {
        return licznik_ataku;
    }
    @Override
    public char getKierunek_ataku() {
        return kierunek_ataku;
    }
    @Override
    public void czyscDostepneStrzaly() {
        dostepne_strzaly.clear();
    }
    @Override
    public void usunStrzal(int id) {
        dostepne_strzaly.remove(Integer.valueOf(id));
    }
    @Override
    public void liczTrafienia(int id) {
        trafione_id[id-1] += 1;
    }
    @Override
    public int[] getTrafione_id() {
        return trafione_id;
    }
    @Override
    public void setKierunek_ataku(char kierunek_ataku) {
        this.kierunek_ataku = kierunek_ataku;
    }
    @Override
    public void setLicznik_ataku(int licznik_ataku) {
        this.licznik_ataku = licznik_ataku;
    }
    @Override
    public Pole getPole() {
        return pole;
    }
    @Override
    public void setPole(Pole pole) {
        this.pole = pole;
    }

    @Override
    public List<Integer> getDostepneStrzaly() {
        return dostepne_strzaly;
    }
}
