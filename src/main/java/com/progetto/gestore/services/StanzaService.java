package com.progetto.gestore.services;

import com.progetto.gestore.enties.Stanza;

import java.util.List;

public interface StanzaService {

    List<Stanza> getAllStanze();

    void aggiungiStanza( Stanza s);

    void setTemperaturaPerStanza(int temp,String nome);

    void setContPerStanza(int p, String nome);
}
