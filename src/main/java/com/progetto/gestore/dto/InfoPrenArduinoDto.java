package com.progetto.gestore.dto;

import lombok.Data;

import java.util.Date;

@Data
public class InfoPrenArduinoDto {

    public String descrizione;
    public Date oraInizio;
    public  Date oraFine;
    public String anagrafica;
}
