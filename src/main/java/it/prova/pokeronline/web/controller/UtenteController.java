package it.prova.pokeronline.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.pokeronline.dto.RuoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.ruolo.RuoloService;
import it.prova.pokeronline.service.utente.UtenteService;
import it.prova.pokeronline.utility.UtilityForm;
import it.prova.pokeronline.validation.ValidationNoPassword;
import it.prova.pokeronline.validation.ValidationWithPassword;

@Controller
@RequestMapping(value = "/utente")
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private RuoloService ruoloService;

	@GetMapping
	public ModelAndView listAllUtenti() {
		ModelAndView mv = new ModelAndView();
		List<Utente> utenti = utenteService.listAllUtenti();
		mv.addObject("utente_list_attribute", utenti);
		mv.setViewName("utente/list");
		return mv;
	}
	
	@GetMapping("/search")
	public String searchUtente(Model model) {
		model.addAttribute("role_attr", ruoloService.listAll());
		return "utente/search";
	}

	@PostMapping("/list")
	public String listUtenti(Utente utenteExample, ModelMap model, HttpServletRequest request) {
		String[] ruoliSelezionati = request.getParameterValues("ruoloInput");
		List<Utente> utenti = utenteService.findByExample(utenteExample, ruoliSelezionati);
		model.addAttribute("utente_list_attribute", utenti);
		return "utente/list";
	}

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm
				.buildCheckedRolesForPages(RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), null));
		model.addAttribute("insert_utente_attr", new UtenteDTO());
		return "utente/insert";
	}
	
	@PostMapping("/save")
	public String save(
			@Validated({ ValidationWithPassword.class,
					ValidationNoPassword.class }) @ModelAttribute("insert_utente_attr") UtenteDTO utenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {

		if (!result.hasFieldErrors("password") && !utenteDTO.getPassword().equals(utenteDTO.getConfermaPassword()))
			result.rejectValue("confermaPassword", "password.diverse");

		if (result.hasErrors()) {
			model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm.buildCheckedRolesForPages(
					RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), utenteDTO.getRuoliIds()));
			return "utente/insert";
		}
		utenteService.inserisciNuovo(utenteDTO.buildUtenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";
	}

	@GetMapping("/show/{idUtente}")
	public String showFilm(@PathVariable(required = true) Long idUtente, Model model) {
		model.addAttribute("show_utente_attr", utenteService.caricaSingoloUtenteConRuoli(idUtente));
		return "utente/show";
	}

	@PostMapping("/resetpass/{idUtente}")
	public String resetpass(@RequestParam Long idUtente, RedirectAttributes redirectAttrs) {

		utenteService.aggiornaPassword(utenteService.caricaSingoloUtente(idUtente));
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";
	}

	@GetMapping("/resetuserpassword")
	public String resetUserPassword(Model model) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		model.addAttribute("resetpw_utente_attr",
				UtenteDTO.buildUtenteDTOFromModel(utenteService.findByUsername(userDetails.getUsername())));
		return "utente/resetpassword";
	}

	@PostMapping("/saveresetuserpw")
	public String saveResetUserPw(RedirectAttributes redirectAttrs, HttpServletRequest request) {

		String vecchiaPassword = request.getParameter("oldpassword");
		String nuovaPassword = request.getParameter("password");
		String confermaPassword = request.getParameter("confermaPassword");
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String usernameUtenteSessione = userDetails.getUsername();

		Utente utenteInSessione = utenteService.findByUsername(usernameUtenteSessione);

		utenteService.cambiaPassword(nuovaPassword, vecchiaPassword, confermaPassword, utenteInSessione);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/logout";
	}


	@GetMapping("/edit/{idUtente}")
	public String edit(@PathVariable(required = true) Long idUtente, Model model) {
		Utente utenteModel = utenteService.caricaSingoloUtenteConRuoli(idUtente);
		model.addAttribute("edit_utente_attr", UtenteDTO.buildUtenteDTOFromModel(utenteModel));
		model.addAttribute("mappaRuoliConSelezionati_attr",
				UtilityForm.buildCheckedRolesFromRolesAlreadyInUtente(
						RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()),
						RuoloDTO.createRuoloDTOListFromModelSet(utenteModel.getRuoli())));
		return "utente/edit";
	}

	@PostMapping("/update")
	public String update(@Validated(ValidationNoPassword.class) @ModelAttribute("edit_utente_attr") UtenteDTO utenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm.buildCheckedRolesForPages(
					RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), utenteDTO.getRuoliIds()));
			return "utente/edit";
		}
		utenteService.aggiorna(utenteDTO.buildUtenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";
	}

	@PostMapping("/cambiaStato")
	public String cambiaStato(@RequestParam(name = "idUtenteForChangingStato", required = true) Long idUtente) {
		utenteService.changeUserAbilitation(idUtente);
		return "redirect:/utente";
	}
	
}
