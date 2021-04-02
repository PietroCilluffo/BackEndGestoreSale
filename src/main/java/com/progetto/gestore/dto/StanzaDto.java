package com.progetto.gestore.dto;

import lombok.Data;

@Data
public class StanzaDto {
    public long id;
    public double temperatura;
    public  int contPersone;
    public int capienzaMax;
    public String nome;

}
