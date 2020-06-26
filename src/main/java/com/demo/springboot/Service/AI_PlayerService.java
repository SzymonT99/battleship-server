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
}
