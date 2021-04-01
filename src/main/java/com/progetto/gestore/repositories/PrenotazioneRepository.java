package com.progetto.gestore.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.progetto.gestore.enties.Prenotazione;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Integer>{

    @Query("SELECT p FROM Prenotazione p WHERE p.deleteToken =?1")
    public Prenotazione getByToken(String token);

    @Query("DELETE FROM Prenotazione p WHERE p.deleteToken =?1")
    public void delByToken(String token);

    @Query("SELECT p FROM Prenotazione p, Stanza s WHERE p.stanza.id = s.id AND s.nome =?1 AND p.data = ?2")
    public List<Prenotazione> getByStanzaGiorno(String nome, Date giorno);


    @Query("SELECT p FROM Prenotazione p WHERE p.data = ?1")
    public List<Prenotazione> getByPrenotazioniByGiorno(Date giorno);

    @Query("SELECT p FROM Prenotazione p,Stanza s WHERE p.data = ?1 AND p.stanza.id = s.id AND s.nome = ?3 AND p.oraInizio>?2 AND p.oraFine<?2")
    public Prenotazione getPrenotazioneAttualeByStanza(Date giorno, Date ora, String stanza);


    @Query("SELECT p FROM Prenotazione p,Stanza s WHERE p.data = ?1 AND p.stanza.id = s.id AND s.nome = ?3 AND p.oraInizio>?2")
    public List<Prenotazione> getPrenotazioniSuccessiveByStanza(Date giorno, Date ora, String stanza);





}

