package com.progetto.gestore.init;

import com.progetto.gestore.enties.Stanza;
import com.progetto.gestore.repositories.StanzaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BootInitializer implements ApplicationRunner {
    private StanzaRepository stanzaRepository;

   @Autowired
    private Environment env;

    @Autowired
    public BootInitializer(StanzaRepository stanzaRepository) {
        this.stanzaRepository = stanzaRepository;
    }

    public void run(ApplicationArguments args) {



        List<Stanza> stanze = stanzaRepository.findAll();
        if(!stanze.isEmpty()){

        }else{


            Stanza stanza1 = new Stanza();
            stanza1.setArduinoId(env.getProperty("stanza1.arduino"));
            stanza1.setNome(env.getProperty("stanza1.nome"));
            stanza1.setCapienzaMax(Integer.parseInt(env.getProperty("stanza1.capienzaMax")));
            stanza1.setContPersone(Integer.parseInt(env.getProperty("stanza1.cont")));
            stanzaRepository.save(stanza1);
            Stanza stanza2 = new Stanza();
            stanza2.setArduinoId(env.getProperty("stanza2.arduino"));
            stanza2.setNome(env.getProperty("stanza2.nome"));
            stanza2.setCapienzaMax(Integer.parseInt(env.getProperty("stanza2.capienzaMax")));
            stanza2.setContPersone(Integer.parseInt(env.getProperty("stanza2.cont")));
            stanzaRepository.save(stanza2);
        }




    }
}
