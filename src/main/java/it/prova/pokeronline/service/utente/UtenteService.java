package it.prova.pokeronline.service.utente;

import java.util.List;

import it.prova.pokeronline.model.Utente;

public interface UtenteService {

	public List<Utente> listAllUtenti() ;

	public Utente caricaSingoloUtente(Long id);
	
	public Utente caricaSingoloUtenteConRuoli(Long id);

	public void aggiorna(Utente utenteInstance);

	public void inserisciNuovo(Utente utenteInstance);

	public void rimuovi(Utente utenteInstance);

	public List<Utente> findByExample(Utente example);
	
	public Utente findByUsername(String username);
	
	public void cambiaPassword(String nuova, String vecchia, String conferma, Utente utenteInstance);
	
	public void changeUserAbilitation(Long utenteInstanceId);
	
	public void aggiornaPassword (Utente utenteInstance);
	
	public List<Utente> cercaByCognomeENomeILike(String term);
	
	public void addCredito(Utente utenteInstance, int creditoDaAggiungere);
}
