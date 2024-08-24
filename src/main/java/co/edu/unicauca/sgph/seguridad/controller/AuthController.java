package co.edu.unicauca.sgph.seguridad.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import co.edu.unicauca.sgph.seguridad.dto.JwtDto;
import co.edu.unicauca.sgph.seguridad.dto.LoginUsuario;
import co.edu.unicauca.sgph.seguridad.dto.TokenDto;
import co.edu.unicauca.sgph.seguridad.entity.UsuarioPrincipal;
import co.edu.unicauca.sgph.seguridad.jwt.JwtProvider;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	private final String GOOGLE_TOKEN_INFO_URL = "https://oauth2.googleapis.com/tokeninfo?access_token=";

	@Autowired
	private AuthenticationManager authenticationManager;

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private UserDetailsService userDetailsService;

	private ResponseEntity<String> consultarInfoTokenGoogle(String token) {
		String url = GOOGLE_TOKEN_INFO_URL + token;
		try {
			return restTemplate.exchange(url, HttpMethod.GET, null, String.class);
		} catch (Exception e) {
			return null;
		}
	}

	@PostMapping("/loginGoogle")
	public ResponseEntity<TokenDto> loginGoogle(@RequestBody TokenDto tokenDto) throws IOException {

		ResponseEntity<String> response = this.consultarInfoTokenGoogle(tokenDto.getValue());
		// Se valida el token de google
		if (response.getStatusCode().is2xxSuccessful()) {
			JSONObject jsonObject = new JSONObject(response.getBody());
			String email = jsonObject.getString("email");
			UserDetails userDetails = userDetailsService.loadUserByUsername(email.split("@")[0]);
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
					userDetails.getAuthorities());

			return new ResponseEntity(this.autenticarYGenerarJwt(authentication), HttpStatus.OK);
		}

		return new ResponseEntity("Token invalido", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity("campos mal puestos", HttpStatus.BAD_REQUEST);
		}
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
		return new ResponseEntity(this.autenticarYGenerarJwt(authentication), HttpStatus.OK);
	}

	private JwtDto autenticarYGenerarJwt(Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities(),
				((UsuarioPrincipal) userDetails).getProgramas().stream().map(obj -> obj.getIdPrograma()).toList());
	}

}