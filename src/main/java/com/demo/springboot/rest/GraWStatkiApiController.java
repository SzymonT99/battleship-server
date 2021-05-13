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
        return new ResponseEntity<>(poleAI, HttpStatus.OK);
    }

    // serwer wysyła id_pola na ktore strzela do klienta
    @CrossOrigin
    @GetMapping(value = "/statki/obrona")
    public ResponseEntity<Pole> wykonajStrzal() throws Exception {
        LOGGER.info("### Serwer otrzymał żądanie strzału AI");

        int AI_atakuje_tutaj = ai_playerService.atakuj();
        ai_playerService.usunStrzal(AI_atakuje_tutaj);

        Pole pomPole = new Pole(AI_atakuje_tutaj, 0);
                                                                //dodanie do listy oddanych strzałów następuje wcześniej
        return new ResponseEntity<>(pomPole, HttpStatus.OK);
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

            if(ai_playerService.getLicznik_ataku()==0) {

                System.out.println("PUDLO TOTALNE - RESETUJE LICZNIK!!!");
            }


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
            }
            if(ai_playerService.getTrafione_id()[poleGracza.getStan()-1]==ai_playerService.getPlanszaAI().getListaStatkowAI().get(poleGracza.getStan()-1).getDlugosc()){ //działa
                System.out.println("Zatopiono statek! ");
                ai_playerService.setLicznik_ataku(0);
                ai_playerService.setKierunek_ataku('0');
                ai_playerService.czyscDostepneStrzaly();
                System.out.println(ai_playerService.getLicznik_ataku() + ai_playerService.getKierunek_ataku());
            }
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping(value = "/statki/nazwa/{nazwa}")
    public ResponseEntity<Void> otrzymajNazwe(@PathVariable("nazwa") String nazwaGracza){
        LOGGER.info("### Serwer otrzymał nazwe gracza {}", nazwaGracza);

        ai_playerService.ustawNazweGracza(nazwaGracza);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/statki/pobierz_nazwe")
    public ResponseEntity<String> pobierzNazwe(){
        LOGGER.info("### zazadano nazwy Gracza ");

        String nazwaGracza;
        if (!ai_playerService.getNazwaGracza().equals("")) nazwaGracza = ai_playerService.getNazwaGracza();
        else nazwaGracza = "Nie podano nazwy gracza";

        return new ResponseEntity<>(nazwaGracza, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/statki/sprawdz_tryb")
    public ResponseEntity<String> wyslijTryb() {

        LOGGER.info("### Serwer otrzymal prosbe podania aktualnego trybu");
        String aktualnyTryb = String.valueOf(ai_playerService.getTryb());

        return new ResponseEntity<>(aktualnyTryb, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/statki/wybor_trybu/{tryb}")
    public ResponseEntity<String> okreslTryb(@PathVariable("tryb") Integer tryb) {

        LOGGER.info("### Serwer otrzymal probe rozpoczecia danego trybu");

        ai_playerService.ustawTryb(tryb);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}