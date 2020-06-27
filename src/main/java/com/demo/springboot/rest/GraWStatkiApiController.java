package com.demo.springboot.rest;

import com.demo.springboot.Model.Pole;
import com.demo.springboot.Service.AI_PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
public class GraWStatkiApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.demo.springboot.rest.GraWStatkiApiController.class);

    // wstrzykiwanie
    @Autowired
    private AI_PlayerService ai_playerService;

    // testowanie działania serwera
    @CrossOrigin
    @GetMapping(value = "/test-serwera")
    public ResponseEntity<Map<String, String>> serverTest() {
        LOGGER.info("### Serwer działa prawidłowo");

        Map<String, String> serverTestMessage = new HashMap<>();
        serverTestMessage.put("server-status", "RUN :-)");

        return new ResponseEntity<>(serverTestMessage, HttpStatus.OK);
    }

    // Gracz wysyła id_pola na które strzela a serwer wysyła w odpowiedzi pole AI/przeciwnika o takim id
    @CrossOrigin
    @GetMapping(value = "/statki/strzelaj/{id_pola}")
    public ResponseEntity<Pole> odbierzStrzal(@PathVariable("id_pola") int id_pola) throws Exception {
        LOGGER.info("### Serwer otrzymał id pola: {}", id_pola);

        Pole poleAI = ai_playerService.getPlanszaAI().getListaPol().get(id_pola - 1);
        //if(poleAI.getStan()>0 && poleAI.getStan()<6) poleAI.setStan(11);   narazie, to działa w wypadku kliknięcia na wrogi statek i ustawia 11 i gra nie zalicza trafienia, tylko pudło
        return new ResponseEntity<>(poleAI, HttpStatus.OK);
    }

    // serwer wysyła id_pola na ktore strzela do klienta
    @CrossOrigin
    @GetMapping(value = "/statki/obrona")
    public ResponseEntity<String> wykonajStrzal() throws Exception {
        LOGGER.info("### Serwer otrzymał żądanie strzału AI");

        int AI_atakuje_tutaj = ai_playerService.atakuj();     //przeniesione - ok ok :D
        ai_playerService.usunStrzal(AI_atakuje_tutaj);
        String id = String.valueOf(AI_atakuje_tutaj);
        //String id = "10";      //dodanie do listy oddanych strzałów następuje wcześniej
        System.out.println(id + "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    // Klient wysyła metodą POST pole na które serwer(AI/przeciwnik) oddał strzał - wysłanie id w powyższym kontrollerze
    @CrossOrigin
    @PostMapping(value = "/statki/odpowiedz_na_strzal_przeciwnika")
    public ResponseEntity<Void> odbierzOdpowiedz(@RequestBody Pole poleGracza) {

        LOGGER.info("### Serwer otrzymal pole Gracza na ktore strzelilo AI");
        LOGGER.info("### Id pola gracza: {}", poleGracza.getId());
        LOGGER.info("### Stan pola gracza: {}", poleGracza.getStan());

        if (poleGracza.getStan() == 0) {
            System.out.println("Pudlo");
            System.out.println("licznik ataku: " + ai_playerService.getLicznik_ataku());
            //if(ai_playerService.getLicznik_ataku()==1) {
            if(ai_playerService.getLicznik_ataku()==0) {
            //    ai_playerService.setLicznik_ataku(0);
            //    ai_playerService.getDostepneStrzaly().clear();
                System.out.println("PUDLO TOTALNE - RESETUJE LICZNIK!!!");
            }

            //turaObrony();

        } else if (poleGracza.getStan() > 0 && poleGracza.getStan() < 6) {
            ai_playerService.getWyjatkowe_strzaly().add(poleGracza.getId());
            System.out.println("Trafiono statek!");
            ai_playerService.setLicznik_ataku(ai_playerService.getLicznik_ataku()+1);
            if(ai_playerService.getLicznik_ataku()==1) {
                ai_playerService.setPole(poleGracza);
                //dodajKierunki();
                ai_playerService.dodajKierunki(poleGracza.getId(), ai_playerService.getDostepneStrzaly(), "atakuj");
            }
            ai_playerService.liczTrafienia(poleGracza.getStan());
            System.out.println("licznik " + ai_playerService.getTrafione_id()[poleGracza.getStan()-1]); //działa
            System.out.println("id statku: " + poleGracza.getStan() + " jego dlugosc: " + ai_playerService.getPlanszaAI().getListaStatkowAI().get(poleGracza.getStan()-1).getDlugosc()); //działa
            if(ai_playerService.getLicznik_ataku()==2) { //ustawiam kierunek
                if (poleGracza.getWsp_x() == ai_playerService.getPole().getWsp_x()) {
                    ai_playerService.setKierunek_ataku('x');
                    System.out.println("Ustawiam kierunek ataku: " + ai_playerService.getKierunek_ataku());
                    ai_playerService.usunStrzal(ai_playerService.getPole().getId()-1);
                    ai_playerService.usunStrzal(ai_playerService.getPole().getId()+1);
                } else if (poleGracza.getWsp_y() == ai_playerService.getPole().getWsp_y()) {
                    ai_playerService.setKierunek_ataku('y');
                    System.out.println("Ustawiam kierunek ataku: " + ai_playerService.getKierunek_ataku());
                    ai_playerService.usunStrzal(ai_playerService.getPole().getId()-10);
                    ai_playerService.usunStrzal(ai_playerService.getPole().getId()+10);
                }
            }
            if(ai_playerService.getLicznik_ataku()>1){
                ai_playerService.dodajZKierunku(ai_playerService.getKierunek_ataku(), poleGracza.getId(), ai_playerService.getDostepneStrzaly(), "atakuj");
                System.out.println("HALO, SLYSZY MNIE KTO?????????????????????????????????????");
            }
            if(ai_playerService.getTrafione_id()[poleGracza.getStan()-1]==ai_playerService.getPlanszaAI().getListaStatkowAI().get(poleGracza.getStan()-1).getDlugosc()){ //działa
                System.out.println("Zatopiono statek! ");
                ai_playerService.setLicznik_ataku(0);
                ai_playerService.setKierunek_ataku('0');
                ai_playerService.czyscDostepneStrzaly();
                System.out.println(ai_playerService.getLicznik_ataku() + ai_playerService.getKierunek_ataku());
            }
            // nie wiem, czy AI potrzebuje cokolwiek robić z tą informacją? Wszystko chyba obsługuję w metodzie atakuj.
            // dodawanie trafionych pól, żeby w razie pustej listy móc dodać otoczenie.
        }

        //int AI_atakuje_tutaj = ai_playerService.atakuj();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}