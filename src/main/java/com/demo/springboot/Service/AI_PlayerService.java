package com.demo.springboot.Service;

import com.demo.springboot.Model.Plansza;
import com.demo.springboot.Model.Pole;
import com.demo.springboot.Model.Statek;

import java.util.List;

public interface AI_PlayerService {
    public void inicjalizujPlansze();
    public void ustawFlote() throws Exception;
    public void ustawStatek(Statek statek) throws Exception;
    public int atakuj() throws Exception;
    public Plansza getPlanszaAI();
    public void setLicznik_ataku(int licznik_ataku);
    public int getLicznik_ataku();
    public void setKierunek_ataku(char kierunek_ataku);
    public char getKierunek_ataku();
    public void czyscDostepneStrzaly();
    public void usunStrzal(int id);
    public void liczTrafienia(int id);
    public int[] getTrafione_id();
    public void setPole(Pole pole);
    public Pole getPole();
    public void dodajKierunki(int id, List<Integer> lista, String operacja);
    public List<Integer> getDostepneStrzaly();
    public void dodajZKierunku(char kierunek, int id, List<Integer> lista, String operacja);
    public List<Integer> getWyjatkowe_strzaly();
    public void ustawNazweGracza(String nazwa);
    public String getNazwaGracza();
    public void ustawTryb(Integer tryb);
    public Integer getTryb();
}
