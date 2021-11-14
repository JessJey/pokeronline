package it.prova.pokeronline.repository.utente;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Utente;


public interface UtenteRepository extends CrudRepository<Utente, Long>, CustomUtenteRepository {
	
	@Query("from Utente u left join fetch u.ruoli where u.id = ?1")
	Optional<Utente> findByIdConRuoli(Long id);
}
