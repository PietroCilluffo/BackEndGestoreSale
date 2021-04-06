package com.progetto.gestore.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
public class PrenotazioneDto {

    public String anagrafica;
    public String email;
    public String descrizione;
    public LocalDate data;
    public LocalTime oraInizio;
    public LocalTime oraFine;
    public String cellulare;

    public StanzaDto stanzaDto = new StanzaDto();
}
