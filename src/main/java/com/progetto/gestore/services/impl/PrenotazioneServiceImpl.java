package com.progetto.gestore.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progetto.gestore.enties.Prenotazione;
import com.progetto.gestore.enties.Stanza;
import com.progetto.gestore.repositories.PrenotazioneRepository;
import com.progetto.gestore.services.PrenotazioneService;

@Service
@Transactional
public class PrenotazioneServiceImpl implements PrenotazioneService {

	@Autowired private PrenotazioneRepository pRepo;

	@Override
	public List<Prenotazione> getAllPrenotazioni() {
		return pRepo.findAll();
	}

	@Override
	public void AggiungiPrenotazione(Prenotazione p) {
		pRepo.save(p);
		
	}

	@Override
	public void DelByToken(String token) {
		pRepo.delByToken(token);
		
	}

	@Override
	public Prenotazione getByToken(String token) {
		return pRepo.getByToken(token);
	}

	@Override
	public Prenotazione getPrenotazioneAttuale(Date giorno, Date ora, String stanza) {
		return pRepo.getPrenotazioneAttualeByStanza(giorno, ora, stanza);
	}

	@Override
	public Prenotazione getPrenotazioneSuccessiva(Date giorno, Date ora, String stanza) {
		return pRepo.getPrenotazioniSuccessiveByStanza(giorno, ora, stanza).get(0);
	}

	@Override
	public List<Prenotazione> getPrenotazioniBySettimanaStanza(Stanza stanza) {
		 List<Prenotazione> settimana = new ArrayList();
		 return settimana;
		
	}

	@Override
	public List<Prenotazione> getPrenotazioniByGiornoStanza(String nome, Date giorno) {
		return pRepo.getByStanzaGiorno(nome, giorno);
	}
	

}
