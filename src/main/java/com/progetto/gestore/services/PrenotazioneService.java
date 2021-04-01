package com.progetto.gestore.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.progetto.gestore.dto.InfoPrenArduinoDto;
import com.progetto.gestore.dto.PrenotazioneDto;
import com.progetto.gestore.enties.Prenotazione;
import com.progetto.gestore.enties.Stanza;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface PrenotazioneService {
	
	List<Prenotazione> getAllPrenotazioni();
	
	void AggiungiPrenotazione(PrenotazioneDto p)  throws AddressException, MessagingException, IOException;
	
	void DelByToken(String token);

	PrenotazioneDto getByToken(String token);
	
	InfoPrenArduinoDto getPrenotazioneAttuale(Date giorno, Date ora, String stanza);

	InfoPrenArduinoDto getPrenotazioneSuccessiva(Date giorno, Date ora, String stanza);
	
	List<PrenotazioneDto> getPrenotazioniBySettimanaStanza(String nome);
	
	List<PrenotazioneDto> getPrenotazioniByGiornoStanza(String nome, Date giorno);
	

}
