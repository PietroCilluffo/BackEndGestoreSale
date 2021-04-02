package com.progetto.gestore.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PrenotazioneDto {

    public String anagrafica;
    public String email;
    public String descrizione;
    public Date data;
    public Date oraInizio;
    public Date oraFine;
    public String cellulare;

    public StanzaDto stanzaDto = new StanzaDto();
}
