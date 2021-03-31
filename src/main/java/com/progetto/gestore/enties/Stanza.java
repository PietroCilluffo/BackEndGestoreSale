package com.progetto.gestore.enties;



import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "stanza")
public class Stanza implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "nome", unique = true)
    private String nome;

    @Column(name = "capMax")
    private int capienzaMax;

    @Column (name = "cont")
    private int contPersone;

    @Column (name = "temp")
    private double temperatura;

    @Column(name = "arduinoId")
    private String arduinoId;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stanza", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Prenotazione> prenotazioneList;

    public Stanza(){

    }
    public List<Prenotazione> getPrenotazioneList() {
        return prenotazioneList;
    }

    public void setPrenotazioneList(List<Prenotazione> prenotazioneList) {
        this.prenotazioneList = prenotazioneList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapienzaMax() {
        return capienzaMax;
    }

    public void setCapienzaMax(int capienzaMax) {
        this.capienzaMax = capienzaMax;
    }

    public int getContPersone() {
        return contPersone;
    }

    public void setContPersone(int contPersone) {
        this.contPersone = contPersone;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getArduinoId() {
        return arduinoId;
    }

    public void setArduinoId(String arduinoId) {
        this.arduinoId = arduinoId;
    }
}
