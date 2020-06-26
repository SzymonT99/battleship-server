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



    }

