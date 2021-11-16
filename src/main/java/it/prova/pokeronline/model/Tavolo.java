package it.prova.pokeronline.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tavolo")
public class Tavolo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "denominazione")
	private String denominazione;
	@Column(name = "dataCreazione")
	private Date dataCreazione;
	@Column(name = "esperienzaMin")
	private int esperienzaMin;
	@Column(name = "cifraMinima")
	private int cifraMinima;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creatore_id", nullable = false)
	private Utente utenteCreatore;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tavoloGioco")
	private Set<Utente> giocatori = new HashSet<Utente>();

	public Tavolo() {
		super();
	}

	public Tavolo(String denominazione, Date dataCreazione, int esperienzaMin, int cifraMinima) {
		super();
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
	}

	public Tavolo(Long id, String denominazione, Date dataCreazione, int esperienzaMin, int cifraMinima,
			Utente utenteCreatore, Set<Utente> giocatori) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
		this.utenteCreatore = utenteCreatore;
		this.giocatori = giocatori;
	}

	public Tavolo(Long id, String denominazione, Date dataCreazione, int esperienzaMin, int cifraMinima) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMinima = cifraMinima;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public int getEsperienzaMin() {
		return esperienzaMin;
	}

	public void setEsperienzaMin(int esperienzaMin) {
		this.esperienzaMin = esperienzaMin;
	}

	public int getCifraMinima() {
		return cifraMinima;
	}

	public void setCifraMinima(int cifraMinima) {
		this.cifraMinima = cifraMinima;
	}

	public Utente getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(Utente utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public Set<Utente> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(Set<Utente> giocatori) {
		this.giocatori = giocatori;
	}

	@Override
	public String toString() {
		return "Tavolo [id=" + id + ", denominazione=" + denominazione + ", dataCreazione=" + dataCreazione
				+ ", esperienzaMin=" + esperienzaMin + ", cifraMinima=" + cifraMinima + "]";
	}

	

}