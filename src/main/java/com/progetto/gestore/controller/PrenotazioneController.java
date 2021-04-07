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
        boolean b = true;
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseNode = mapper.createObjectNode();
        try{
           b = prenotazioneService.AggiungiPrenotazione(dto);

        }catch(Exception e) {
            System.out.println(e);
        }

        if(b == true){
            responseNode.put("code", HttpStatus.OK.toString());
            responseNode.put("message", String.format("Inserimento prenotazione Eseguito Con Successo"));

            return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.CREATED);
        }else{
            responseNode.put("code", HttpStatus.CONFLICT.toString());
            responseNode.put("message", String.format("La prenotazione inserita coincide con uno slot già occupato"));

            return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.CONFLICT);
        }

    }

    @GetMapping(value = "/findPrenotazioniSettimanaByStanza/{nome}", produces = "application/json")
    public ResponseEntity<?> findPrenotazioniSettimanaByStanza(@PathVariable("nome")String nome){
        logger.info("***** Find " + " ******");
        List<PrenotazioneDto> list = prenotazioneService.getPrenotazioniBySettimanaStanza(nome);
        if(list.isEmpty()){
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseNode = mapper.createObjectNode();
            responseNode.put("code", HttpStatus.OK.toString());
            responseNode.put("message", String.format("Nessuna prenotazione trovata"));

            return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<PrenotazioneDto>>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/findPrenotazioniGiornoByStanza/{nome}", produces = "application/json")
    public ResponseEntity<?> findPrenotazioniGiornoByStanza(@PathVariable("nome")String nome){
        logger.info("***** Find " + " ******");
        LocalDate now = LocalDate.now();
        List<PrenotazioneDto> list = prenotazioneService.getPrenotazioniByGiornoStanza(nome,now);
        if(list.isEmpty()){
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseNode = mapper.createObjectNode();
            responseNode.put("code", HttpStatus.OK.toString());
            responseNode.put("message", String.format("Nessuna prenotazione trovata"));

            return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<PrenotazioneDto>>(list, HttpStatus.OK);
    }
    @GetMapping(value = "/findPrenotazioneByToken/{nome}", produces = "application/json")
    public ResponseEntity<PrenotazioneDto> findPrenotazioneByToken(@PathVariable("nome")String nome){
        logger.info("***** Find " + " ******");
        LocalDate now = LocalDate.now();
        PrenotazioneDto dto = prenotazioneService.getByToken(nome);
        return new ResponseEntity<PrenotazioneDto>(dto, HttpStatus.OK);
    }

    //arduino
    @GetMapping(value = "/findPrenotazioneAttuale/{nome}", produces = "application/json")
    public ResponseEntity<?> findPrenotazioneAttualeByStanza(@PathVariable("nome")String nome){
        logger.info("***** Find " + " ******");


        LocalDate dt2 = LocalDate.now();
        LocalTime attuale= LocalTime.now();

        logger.info(dt2.toString());
        logger.info(attuale.toString());
        InfoPrenArduinoDto dto = prenotazioneService.getPrenotazioneAttuale(dt2,attuale, nome);
        if(dto.getOraFine() == null){
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseNode = mapper.createObjectNode();
            responseNode.put("code", HttpStatus.OK.toString());
            responseNode.put("message", String.format("Nessuna prenotazione trovata"));

            return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<InfoPrenArduinoDto>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/findPrenotazioneSuccessiva/{nome}", produces = "application/json")
    public ResponseEntity<?> findPrenotazioneSuccessivaByStanza(@PathVariable("nome")String nome){
        logger.info("***** Find " + " ******");
        LocalDate dt = LocalDate.now();
        LocalTime attuale= LocalTime.now();
        InfoPrenArduinoDto dto = prenotazioneService.getPrenotazioneSuccessiva(dt,attuale, nome);
        if(dto.getOraFine() == null){
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseNode = mapper.createObjectNode();
            responseNode.put("code", HttpStatus.NOT_FOUND.toString());
            responseNode.put("message", String.format("Nessuna prenotazione trovata in giornata odierna"));

            return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<InfoPrenArduinoDto>(dto, HttpStatus.OK);
    }
}
