package com.progetto.gestore.repositories;

import com.progetto.gestore.enties.Stanza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StanzaRepository extends JpaRepository<Stanza,Long> {
    List<Stanza> findAll();

    Stanza getStanzaByNome(String nome);

    @Modifying
    @Query("update Stanza stanza set stanza.temperatura = ?1 where stanza.nome = ?2")
    void setTemperaturaForNomeStanza(int temp, String nome);

    @Modifying
    @Query("update Stanza stanza set stanza.contPersone = ?1 where stanza.nome = ?2")
    void setTContForNomeStanza(int contPersone,String nome);
}
