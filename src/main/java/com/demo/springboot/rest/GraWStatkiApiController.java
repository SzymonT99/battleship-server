package com.demo.springboot.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        @CrossOrigin
        @GetMapping(value = "/statki/strzelaj/{id_pola}")
        public ResponseEntity<Void> odbierzStrzal(@PathVariable ("id_pola") int id_pola) {
            LOGGER.info("### Serwer otrzymał id pola: {}", id_pola);

            return new ResponseEntity<>(HttpStatus.OK);
        }




    }

