package it.prova.pokeronline.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.tavolo.TavoloService;
import it.prova.pokeronline.service.utente.UtenteService;

@Controller
@RequestMapping(value = "/gioca")
public class GiocaController {

	@Autowired
	TavoloService tavoloService;
	
	@Autowired
	UtenteService utenteService;
	
	@GetMapping
	public ModelAndView listAllTavoli(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		List<Tavolo> tavoli = tavoloService.listAllTavoli();
		mv.addObject("tavolo_list_attribute", tavoli);
		mv.setViewName("gioca/list");
		return mv;
	}
	
	@GetMapping("/search")
	public String searchTavolo(Model model) {
		model.addAttribute("utenti_list_attribute", UtenteDTO.createUtenteDTOListFromModelList(utenteService.listAllUtenti()));
		return "gioca/search";
	}
	
	@PostMapping("/list")
	public String listTavoli(@ModelAttribute("insert_tavolo_attr") TavoloDTO tavoloExample, ModelMap model,  HttpServletRequest request) {

		List<Tavolo> tavoli = tavoloService.findByExample(tavoloExample);
		Utente utente = utenteService.findByUsername(request.getUserPrincipal().getName());
		model.addAttribute("tavolo_list_attribute", TavoloDTO.createTavoloDTOListFromModelList(tavoli));
		model.addAttribute("utente_attribute", utente);
		return "gioca/list";
	}
}
