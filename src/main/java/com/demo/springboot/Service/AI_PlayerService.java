package com.demo.springboot.Service;

import com.demo.springboot.Model.Statek;

public interface AI_PlayerService {
    public void inicjalizujPlansze();
    public void ustawFlote() throws Exception;
    public void ustawStatek(Statek statek) throws Exception;
    public void atakuj();
    public int strzelaj(int id_pola);
    public void czekaj();
    public void wykryj();
}
