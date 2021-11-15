package it.prova.pokeronline.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.utente.UtenteRepository;

@Component
public class CustomAuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Autowired
	private UtenteRepository utenteRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		Utente utenteFromDb = utenteRepository.findByUsername(authentication.getName()).orElseThrow(
				() -> new UsernameNotFoundException("Username " + authentication.getName() + " not found"));
		Utente utenteParziale = new Utente();
		utenteParziale.setNome(utenteFromDb.getNome());
		utenteParziale.setCognome(utenteFromDb.getCognome());
		request.getSession().setAttribute("userInfo", utenteParziale);
		response.sendRedirect("home");
	}
}