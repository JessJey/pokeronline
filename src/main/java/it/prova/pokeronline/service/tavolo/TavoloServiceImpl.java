package it.prova.pokeronline.service.tavolo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;
import it.prova.pokeronline.repository.utente.UtenteRepository;

@Service
public class TavoloServiceImpl implements TavoloService {

	@Autowired
	TavoloRepository repository;
	
	@Autowired
	UtenteRepository utenteRepository;

	@Transactional(readOnly = true)
	public List<Tavolo> listAllTavoli() {
		return (List<Tavolo>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavolo(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional
	public void aggiorna(Tavolo tavoloInstance) {
		repository.save(tavoloInstance);
	}

	@Transactional
	public void inserisciNuovo(Tavolo tavoloInstance) {
		repository.save(tavoloInstance);
	}

	@Transactional
	public void rimuovi(Tavolo tavoloInstance) {
		repository.delete(tavoloInstance);
	}

	@Transactional(readOnly = true)
	public List<Tavolo> findByExample(Tavolo example) {
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Tavolo> findAllMyTavoli(Utente example) {
		return repository.findAllByUtenteCreatore_IdIs(example.getId());
	}

	@Transactional(readOnly = true)
	@Override
	public Tavolo caricaTavoloConGiocatori(Long id) {
		return repository.findByIdConGiocatori(id);
	}

	@Transactional
	@Override
	public void rimuoviById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<Tavolo> findByExampleMyTables(TavoloDTO tavolo, String username) {

		Utente utente = utenteRepository.findByUsernameConRuoli(username).get();

		if (utente.isAdmin()) {
			return repository.findByExampleConCreatore(tavolo);
		}

		return repository.findByExampleMyTables(tavolo, utente.getId());
	}

	@Override
	public List<Tavolo> findByExample(TavoloDTO example) {
		return repository.findByExample(example);
	}
}
