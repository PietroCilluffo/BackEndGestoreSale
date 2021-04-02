package com.progetto.gestore.controller;

import com.progetto.gestore.dto.CountDto;
import com.progetto.gestore.dto.PrenotazioneDto;
import com.progetto.gestore.dto.StanzaDto;
import com.progetto.gestore.dto.TempDto;
import com.progetto.gestore.enties.Stanza;
import com.progetto.gestore.repositories.StanzaRepository;
import com.progetto.gestore.services.StanzaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/stanza")
public class StanzaController {

    @Autowired private StanzaService stanzaService;
    @Autowired private StanzaRepository stanzaRepo;

    private static final Logger logger = LoggerFactory.getLogger(StanzaController.class);

    @PostMapping("/temperaturaStanza")
    public ResponseEntity<String> setTemperaturaInStanza(@RequestBody TempDto dto)
    {
        Stanza s = new Stanza();
        try
        {

            logger.info("rsjdsaojkdosa");
            String nomeStanza = stanzaService.getNomeStanzaByArduinoId(dto.getArduinoID());
           stanzaService.setTemperaturaPerStanza(dto.getTemp(),nomeStanza);
        }
        catch(Exception e)
        {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Temperatura non inviata");
        }
        return ResponseEntity.ok("Temperatura Inviata");
    }



    @GetMapping("/stanze")
    public ResponseEntity<List<StanzaDto>> getListaStanze() throws Exception
    {
        List<StanzaDto> stanze = new ArrayList();
        try
        {
            stanze = stanzaService.getAllStanze();
        }
        catch(Exception e)
        {
            throw new Exception(e);
        }

        return ResponseEntity.ok(stanze);
    }




    @PostMapping("/counter")
    public ResponseEntity<String> setPersoneInStanza(@RequestBody CountDto dto)
    {
        Stanza s = new Stanza();
        try
        {
            String nomeStanza = stanzaService.getNomeStanzaByArduinoId(dto.getArduinoID());
          stanzaService.setContPerStanza(dto.getCount(),nomeStanza);
        }
        catch(Exception e)
        {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Conteggio non  aggiornato");
        }
        return ResponseEntity.ok("Conteggio aggiornato");
    }



}
