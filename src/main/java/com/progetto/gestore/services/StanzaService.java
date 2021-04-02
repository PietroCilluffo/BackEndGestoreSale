package com.progetto.gestore.services;

import com.progetto.gestore.dto.StanzaDto;
import com.progetto.gestore.enties.Stanza;

import java.util.List;

public interface StanzaService {

    List<StanzaDto> getAllStanze();

    void aggiungiStanza( Stanza s);

    void setTemperaturaPerStanza(double temp,String nome);

    void setContPerStanza(int p, String nome);

    String getNomeStanzaByArduinoId(String arduinoID);
}
