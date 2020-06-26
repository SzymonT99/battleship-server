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

        @CrossOrigin
        @GetMapping(value = "/test-serwera")
        public ResponseEntity<Map<String, String>> serverTest() {
            LOGGER.info("### Serwer działa prawidłowo");

            Map<String, String> serverTestMessage = new HashMap<>();
            serverTestMessage.put("server-status", "RUN :-)");

            return new ResponseEntity<>(serverTestMessage, HttpStatus.OK);
        }


        @Autowired
        private AI_PlayerService ai_playerService;
        @CrossOrigin
        @GetMapping(value = "/statki/strzelaj/{id_pola}")
        public ResponseEntity<Pole> odbierzStrzal(@PathVariable ("id_pola") int id_pola) throws Exception {
            LOGGER.info("### Serwer otrzymał id pola: {}", id_pola);



            //ai_playerService.inicjalizujPlansze();
            //ai_playerService.ustawFlote();
            //LOGGER.info("AI");
            Pole poleAI=ai_playerService.getPlanszaAI().getListaPol().get(id_pola-1);
            return new ResponseEntity<>(poleAI,HttpStatus.OK);
        }
        @CrossOrigin
        @GetMapping(value = "/statki/obrona")
        public ResponseEntity<String> wykonajStrzal(){
            LOGGER.info("### Serwer otrzymał zadanie strzalu AI");
            String id = "10";

            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        @CrossOrigin
        @GetMapping(value = "/statki/strzal_AI")
        public ResponseEntity<Integer> strzalAI(){
            LOGGER.info("### Tura strzału AI");



            //ai_playerService.inicjalizujPlansze();
            //ai_playerService.ustawFlote();
            //LOGGER.info("AI");
            int AI_atakuje_tutaj=ai_playerService.atakuj();
            return new ResponseEntity<>(AI_atakuje_tutaj,HttpStatus.OK);
        }

        @CrossOrigin
        @GetMapping(value = "/statki/trafienie_statku_gracza")
        public ResponseEntity<Integer> odbierzOdpowiedz(int odpowiedz_pole, int odpowiedz_stan){
            LOGGER.info("### Statek gracza oberwal");

            if(odpowiedz_stan==11){
                System.out.println("Pudlo");
                // dalsze instrukcje
                //turaObrony();
            }
            else if(odpowiedz_stan>1 && odpowiedz_stan<6){ // TODO: nie jest dokończone!
                System.out.println("Trafiono statek!");
                // dalsze instrukcje
                // TODO: zapisz id_pola oraz id_trafionego na liście - będzie to służyło do tego, że jak trafi dwa statki koło siebie, to przy zatopieniu 1 statku, będzie szukało tego 2?

            }
            else if(odpowiedz_stan==99){ // TODO: Nie ma jeszcze dodawania do listy statków wroga - lista jet pusta.
                System.out.println("Zatopiono statek!");  // ta metoda jest zakończona

            }


            //ai_playerService.inicjalizujPlansze();
            //ai_playerService.ustawFlote();
            //LOGGER.info("AI");
            int AI_atakuje_tutaj=ai_playerService.atakuj();
            return new ResponseEntity<>(AI_atakuje_tutaj,HttpStatus.OK);
        }

    }

