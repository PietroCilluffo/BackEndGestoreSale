package com.progetto.gestore.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class InfoPrenArduinoDto {

    public String descrizione;
    public LocalTime oraInizio;
    public  LocalTime oraFine;
    public String anagrafica;
}
