package com.progetto.gestore.services;

import java.util.Date;
import java.util.List;

import com.progetto.gestore.enties.Prenotazione;
import com.progetto.gestore.enties.Stanza;

public interface PrenotazioneService {
	
	List<Prenotazione> getAllPrenotazioni();
	
	void AggiungiPrenotazione(Prenotazione p);
	
	void DelByToken(String token);
	
	Prenotazione getByToken(String token);
	
	Prenotazione getPrenotazioneAttuale(Date giorno, Date ora, String stanza);
	
	Prenotazione getPrenotazioneSuccessiva(Date giorno, Date ora, String stanza);
	
	List<Prenotazione> getPrenotazioniBySettimanaStanza(Stanza stanza);
	
	List<Prenotazione> getPrenotazioniByGiornoStanza(String nome, Date giorno);
	

}
