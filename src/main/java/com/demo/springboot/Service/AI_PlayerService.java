package com.demo.springboot.Service;

import com.demo.springboot.Model.Plansza;
import com.demo.springboot.Model.Statek;

public interface AI_PlayerService {
    public void inicjalizujPlansze();
    public void ustawFlote() throws Exception;
    public void ustawStatek(Statek statek) throws Exception;
    public int atakuj();
    public int strzelaj(int id_pola);
    public void czekaj();
    public void wykryj();
    public void obslugaOdpowiedzi(int odpowiedz_pole, int odpowiedz_stan);
    public Plansza getPlanszaAI();
    public void setLicznik_ataku(int licznik_ataku);
    public void setKierunek_ataku(char kierunek_ataku);
    public void setIds(int ids);
    public void setIdsTMP(int idsTMP);
}
