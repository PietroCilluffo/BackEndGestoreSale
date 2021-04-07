package com.progetto.gestore.services.impl;

import com.progetto.gestore.dto.StanzaDto;
import com.progetto.gestore.enties.Stanza;
import com.progetto.gestore.repositories.StanzaRepository;
import com.progetto.gestore.services.StanzaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class StanzaServiceImpl implements StanzaService {
   @Autowired
   private StanzaRepository stanzaRepository;
    @Autowired private ModelMapper modelMapper;
    @Override
    public List<StanzaDto> getAllStanze() {
        List<StanzaDto> stanzaDtos = new ArrayList<>();
        List<Stanza> stanzaList = stanzaRepository.findAll();
        for(Stanza s : stanzaList){
            StanzaDto dto = modelMapper.map(s,StanzaDto.class);
            stanzaDtos.add(dto);
        }
        return stanzaDtos;
    }

    @Override
    public void aggiungiStanza(Stanza s) {
       stanzaRepository.save( s);
    }

    @Override
    public void setTemperaturaPerStanza(double temp, String nome) {

        stanzaRepository.setTemperaturaForNomeStanza(temp,nome);
    }

    @Override
    public void setContPerStanza(int p,String nome) {

        int count = stanzaRepository.getCountByNome(nome) + p;
        if(count <0 ){

        }else{
            stanzaRepository.setTContForNomeStanza(count,nome);
        }

    }
    @Override
    public String getNomeStanzaByArduinoId(String arduinoId){
        return stanzaRepository.getStanzaNomeByArduinoId(arduinoId);
    }
}
