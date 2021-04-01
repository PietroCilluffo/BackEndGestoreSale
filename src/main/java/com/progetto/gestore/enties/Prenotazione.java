package com.progetto.gestore.enties;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "prenotazione")
public class Prenotazione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column (name = "anagrafica")
    private String anagrafica;



    @Column (name = "email")
    private String email;

    @Column (name = "descrizione")
    private String descrizione;

    @Column ( name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;

    @Column ( name = "oraInizio")
    @Temporal(TemporalType.TIME)
    private Date oraInizio;

    @Column ( name = "oraFine")
    @Temporal(TemporalType.TIME)
    private Date oraFine;

    @Column(name = "deleteToken")
    private String deleteToken;

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    @Column(name = "cellulare")
    private String cellulare;

    @ManyToOne
    @JoinColumn(name = "idStanza", referencedColumnName = "id")
    @JsonBackReference
    public Stanza stanza;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(String anagrafica) {
        this.anagrafica = anagrafica;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(Date oraInizio) {
        this.oraInizio = oraInizio;
    }

    public Date getOraFine() {
        return oraFine;
    }

    public void setOraFine(Date oraFine) {
        this.oraFine = oraFine;
    }

    public String getDeleteToken() {
        return deleteToken;
    }

    public void setDeleteToken(String deleteToken) {
        this.deleteToken = deleteToken;
    }

    public Stanza getStanza() {
        return stanza;
    }

    public void setStanza(Stanza stanza) {
        this.stanza = stanza;
    }
}
