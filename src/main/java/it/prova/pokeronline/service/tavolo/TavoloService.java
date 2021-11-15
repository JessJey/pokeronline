package it.prova.pokeronline.service.tavolo;

import java.util.List;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public interface TavoloService {

	public List<Tavolo> listAllTavoli() ;

	public Tavolo caricaSingoloTavolo(Long id);
	
	public void aggiorna(Tavolo tavoloInstance);

	public void inserisciNuovo(Tavolo tavoloInstance);

	public void rimuovi(Tavolo tavoloInstance);
	
	public List<Tavolo> findByExample(Tavolo example);
	
	public List<Tavolo> findAllMyTavoli(Utente example);
}
