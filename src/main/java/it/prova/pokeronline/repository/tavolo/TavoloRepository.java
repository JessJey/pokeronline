package it.prova.pokeronline.repository.tavolo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloRepository extends CrudRepository<Tavolo, Long>, CustomTavoloRepository {
	
	List<Tavolo> findAllByUtenteCreatore_IdIs(Long id);
	
	@Query("from Tavolo t left join fetch t.giocatori where t.id = ?1")
	Tavolo findByIdConGiocatori(Long id);
	
}