package com.progetto.gestore.services.impl;

import com.progetto.gestore.enties.Stanza;
import com.progetto.gestore.repositories.StanzaRepository;
import com.progetto.gestore.services.StanzaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StanzaServiceImpl implements StanzaService {
   @Autowired
   private StanzaRepository stanzaRepository;

    @Override
    public List<Stanza> getAllStanze() {
        return stanzaRepository.findAll();
    }

    @Override
    public void aggiungiStanza(Stanza s) {
       stanzaRepository.save( s);
    }

    @Override
    public void setTemperaturaPerStanza(int temp, String nome) {
        stanzaRepository.setTemperaturaForNomeStanza(temp,nome);
    }

    @Override
    public void setContPerStanza(int p,String nome) {
        stanzaRepository.setTContForNomeStanza(p,nome);
    }
}
