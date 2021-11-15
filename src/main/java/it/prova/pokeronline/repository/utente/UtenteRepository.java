package it.prova.pokeronline.repository.utente;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Utente;


public interface UtenteRepository extends CrudRepository<Utente, Long>, CustomUtenteRepository {
	
	@Query("from Utente u left join fetch u.ruoli where u.id = ?1")
	Optional<Utente> findByIdConRuoli(Long id);
	
	@EntityGraph(attributePaths = { "ruoli" })
	Optional<Utente> findByUsername(String username);
	
	@Query("from Utente u left join fetch u.ruoli where u.username like ?1")
	Optional<Utente> findByUsernameConRuoli(String user);
	
	List<Utente> findByCognomeIgnoreCaseContainingOrNomeIgnoreCaseContainingOrderByNomeAsc(String cognome, String nome);
	
}
