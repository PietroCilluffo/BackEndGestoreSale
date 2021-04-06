package com.progetto.gestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.progetto.gestore.dto.InfoPrenArduinoDto;
import com.progetto.gestore.dto.PrenotazioneDto;
import com.progetto.gestore.services.PrenotazioneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/prenotazione")
public class PrenotazioneController {
    private static final Logger logger = LoggerFactory.getLogger(PrenotazioneDto.class);
    @Autowired
    private PrenotazioneService prenotazioneService;

    @RequestMapping(value="/deleteByToken/{token}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> deleteByToken(@PathVariable("token") String token)
    {
        if(prenotazioneService.getByToken(token)!= null){
            prenotazioneService.DelByToken(token);
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseNode = mapper.createObjectNode();
            responseNode.put("code", HttpStatus.OK.toString());
            responseNode.put("message", "Eliminazione prenotazione " + " Eseguita Con Successo");
            return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.OK);
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();
        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", "Eliminazione prenotazione " + " non Eseguita Con Successo");
        return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }
    @RequestMapping(value="/add", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> addPrenotazione(@RequestBody PrenotazioneDto dto){


        System.out.println(dto);
        try{
            prenotazioneService.AggiungiPrenotazione(dto);
        }catch(Exception e) {
            System.out.println(e);
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();

        responseNode.put("code", HttpStatus.OK.toString());
        responseNode.put("message", String.format("Inserimento prenotazione Eseguito Con Successo"));

        return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/findPrenotazioniSettimanaByStanza", produces = "application/json")
    public ResponseEntity<List<PrenotazioneDto>> findPrenotazioniSettimanaByStanza(@PathVariable("nome")String nome){
        logger.info("***** Find " + " ******");
        List<PrenotazioneDto> list = prenotazioneService.getPrenotazioniBySettimanaStanza(nome);
        return new ResponseEntity<List<PrenotazioneDto>>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/findPrenotazioniGiornoByStanza", produces = "application/json")
    public ResponseEntity<List<PrenotazioneDto>> findPrenotazioniGiornoByStanza(@PathVariable("nome")String nome){
        logger.info("***** Find " + " ******");
        LocalDate now = LocalDate.now();
        List<PrenotazioneDto> list = prenotazioneService.getPrenotazioniByGiornoStanza(nome,now);
        return new ResponseEntity<List<PrenotazioneDto>>(list, HttpStatus.OK);
    }

    //arduino
    @GetMapping(value = "/findPrenotazioneAttuale/{nome}", produces = "application/json")
    public ResponseEntity<InfoPrenArduinoDto> findPrenotazioneAttualeByStanza(@PathVariable("nome")String nome){
        logger.info("***** Find " + " ******");


        LocalDate dt2 = LocalDate.now();
        LocalTime attuale= LocalTime.now();

        logger.info(dt2.toString());
        logger.info(attuale.toString());
        InfoPrenArduinoDto dto = prenotazioneService.getPrenotazioneAttuale(dt2,attuale, nome);
        return new ResponseEntity<InfoPrenArduinoDto>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/findPrenotazioneSuccessiva", produces = "application/json")
    public ResponseEntity<InfoPrenArduinoDto> findPrenotazioneSuccessivaByStanza(@PathVariable("nome")String nome){
        logger.info("***** Find " + " ******");
        LocalDate dt = LocalDate.now();
        LocalTime attuale= LocalTime.now();
        InfoPrenArduinoDto dto = prenotazioneService.getPrenotazioneSuccessiva(dt,attuale, nome);
        return new ResponseEntity<InfoPrenArduinoDto>(dto, HttpStatus.OK);
    }
}
