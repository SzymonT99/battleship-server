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

        //ai_playerService.inicjalizujPlansze();
        //ai_playerService.ustawFlote();
        //LOGGER.info("AI");
        Pole poleAI = ai_playerService.getPlanszaAI().getListaPol().get(id_pola - 1);
        return new ResponseEntity<>(poleAI, HttpStatus.OK);
    }

    // serwer wysyła id_pola na ktore strzela do klienta
    @CrossOrigin
    @GetMapping(value = "/statki/obrona")
    public ResponseEntity<String> wykonajStrzal() {
        LOGGER.info("### Serwer otrzymał żądanie strzału AI");

        int AI_atakuje_tutaj = ai_playerService.atakuj();     //przeniesione
        String id = String.valueOf(AI_atakuje_tutaj);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    // Klient wysyła metodą POST pole na które serwer(AI/przeciwnik) oddał strzał - wysłanie id w powyższym kontrollerze
    @CrossOrigin
    @PostMapping(value = "/statki/odpowiedz_na_strzal_przeciwnika")
    public ResponseEntity<Void> odbierzOdpowiedz(@RequestBody Pole poleGracza) {

        LOGGER.info("### Serwer otrzymal pole Gracza na ktore strzelilo AI");
        LOGGER.info("### Id pola gracza: {}", poleGracza.getId());
        LOGGER.info("### Stan pola gracza: {}", poleGracza.getStan());

//        if (odpowiedz_stan == 11) {
//            System.out.println("Pudlo");
//            // dalsze instrukcje
//            //turaObrony();
//        } else if (odpowiedz_stan > 1 && odpowiedz_stan < 6) { // TODO: nie jest dokończone!
//            System.out.println("Trafiono statek!");
//            // dalsze instrukcje
//            // TODO: zapisz id_pola oraz id_trafionego na liście - będzie to służyło do tego, że jak trafi dwa statki koło siebie, to przy zatopieniu 1 statku, będzie szukało tego 2?
//
//        } else if (odpowiedz_stan == 99) { // TODO: Nie ma jeszcze dodawania do listy statków wroga - lista jet pusta.
//            System.out.println("Zatopiono statek!");  // ta metoda jest zakończona
//
//        }

        //int AI_atakuje_tutaj = ai_playerService.atakuj();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}