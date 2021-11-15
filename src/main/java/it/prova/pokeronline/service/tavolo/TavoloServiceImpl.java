package it.prova.pokeronline.service.tavolo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;

@Service
public class TavoloServiceImpl implements TavoloService  {

	@Autowired
	TavoloRepository repository;

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
}
