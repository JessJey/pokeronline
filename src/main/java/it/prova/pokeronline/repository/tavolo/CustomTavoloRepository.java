package it.prova.pokeronline.repository.tavolo;

import java.util.List;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;

public interface CustomTavoloRepository {


	List<Tavolo> findByExampleMyTables(TavoloDTO tavolo, Long id);
	
	List<Tavolo> findByExampleConCreatore(TavoloDTO tavolo);
	
	public List<Tavolo> findByExample(Tavolo example);
	
	public List<Tavolo> findByExample(TavoloDTO example);
	
	
}
