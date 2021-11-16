package it.prova.pokeronline.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		model.addAttribute("utenti_list_attribute",
				UtenteDTO.createUtenteDTOListFromModelList(utenteService.listAllUtenti()));
		return "gioca/search";
	}

	@PostMapping("/list")
	public String listTavoli(@ModelAttribute("insert_tavolo_attr") TavoloDTO tavoloExample, ModelMap model,
			HttpServletRequest request) {
		List<Tavolo> tavoli = tavoloService.findByExample(tavoloExample);
		Utente utente = utenteService.findByUsername(request.getUserPrincipal().getName());
		model.addAttribute("tavolo_list_attribute", TavoloDTO.createTavoloDTOListFromModelList(tavoli));
		model.addAttribute("utente_attribute", utente);
		return "gioca/list";
	}

	@GetMapping("/eseguigioca/{idTavolo}")
	public String showTavoloGiocato(@PathVariable(required = true) Long idTavolo, Model model) {
		model.addAttribute("show_tavolo_attr", tavoloService.caricaSingoloTavolo(idTavolo));
		return "gioca/gioca";
	}

	@PostMapping("/partita/{idTavolo}")
	public String giocata(@PathVariable(required = true) Long idTavolo, Model model, HttpServletRequest request) {

		Utente utenteInSessione = utenteService.findByUsername(request.getUserPrincipal().getName());

		utenteInSessione = utenteService.caricaSingoloUtente(utenteInSessione.getId());
		Tavolo tavoloPerGiocare = tavoloService.caricaSingoloTavolo(idTavolo);

		utenteInSessione
				.setCreditoAccumulato(utenteInSessione.getCreditoAccumulato() + tavoloPerGiocare.getCifraMinima());
		utenteInSessione.setTavoloGioco(tavoloPerGiocare);
		utenteService.aggiorna(utenteInSessione);
		tavoloPerGiocare.getGiocatori().add(utenteInSessione);
		tavoloService.aggiorna(tavoloPerGiocare);

		model.addAttribute("show_tavolo_attr", tavoloPerGiocare);
		model.addAttribute("successMessage", "Hai Vinto!");
		return "gioca/partita";
	}

	@PostMapping("/exit/{idTavolo}")
	public String exitPartita(@PathVariable(required = true) Long idTavolo, Model model,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		Utente utenteInSessione = utenteService.findByUsername(request.getUserPrincipal().getName());
		utenteInSessione = utenteService.caricaSingoloUtente(utenteInSessione.getId());

		Tavolo tavoloPerGiocare = tavoloService.caricaSingoloTavolo(idTavolo);

		utenteInSessione.setEsperienzaAccumulata(utenteInSessione.getEsperienzaAccumulata() + 1);

		utenteInSessione.setTavoloGioco(null);
		utenteService.aggiorna(utenteInSessione);

		tavoloPerGiocare.getGiocatori().remove(utenteInSessione);
		tavoloService.aggiorna(tavoloPerGiocare);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/home";
	}

	@GetMapping("/lastgame")
	public String goToMyLastGame(Model model, HttpServletRequest request) {

		Utente utente = utenteService.findByUsername(request.getUserPrincipal().getName());
		Tavolo tavoloPerGiocare = utente.getTavoloGioco();

		if (utente.getTavoloGioco() == null) {
			model.addAttribute("errorMessage", "Non sei presente in alcun tavolo!");
			return "index";
		}

		model.addAttribute("show_tavolo_attr", tavoloPerGiocare);
		return "gioca/partita";
	}

}
