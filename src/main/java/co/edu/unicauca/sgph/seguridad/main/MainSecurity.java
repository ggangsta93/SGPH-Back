package co.edu.unicauca.sgph.seguridad.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import co.edu.unicauca.sgph.seguridad.jwt.JwtEntryPoint;
import co.edu.unicauca.sgph.seguridad.jwt.JwtTokenFilter;
import co.edu.unicauca.sgph.seguridad.service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity {

	@Autowired
	private UserDetailServiceImpl userDetailsService;

	@Autowired
	private JwtEntryPoint jwtEntryPoint;

	@Bean
	public JwtTokenFilter jwtTokenFilter() {
		return new JwtTokenFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
        .cors().and().csrf().disable()
        .authorizeHttpRequests(authorizeRequests -> 
            authorizeRequests
                .antMatchers("/Autenticacion/**").permitAll()
                .antMatchers("/AdministrarPeriodoAcademico/guardarPeriodoAcademico").hasAnyRole("PLANIFICADOR")
                .antMatchers("/AdministrarAgrupador/guardarGrupo").hasRole("PLANIFICADOR")
                .antMatchers("/AdministrarAgrupador/guardarAsignacion").hasRole("PLANIFICADOR")
                .antMatchers("/PlanificacionManual/consultarFranjasEspacioFisicoPorIdEspacioFisico").hasRole("PRESTAMISTA")
                .antMatchers("/PlanificacionManual/consultarFranjasDocentePorIdPersona").hasRole("PRESTAMISTA")
                .antMatchers("/PlanificacionManual/**").hasRole("PLANIFICADOR")
                .antMatchers("/AdministrarPrograma/consultarProgramasPermitidosPorUsuario").hasRole("PLANIFICADOR")
                .antMatchers("/AdministrarUsuario/**").hasRole("ADMINISTRADOR")
                .anyRequest().authenticated()
        )
        .exceptionHandling(exceptionHandling -> 
            exceptionHandling.authenticationEntryPoint(jwtEntryPoint)
        )
        .sessionManagement(sessionManagement -> 
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

		http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
			public void addCorsMappings(CorsRegistry registry) {                
                registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:4200","http://localhost","http://159.89.37.197","https://sgph-unicauca.netlify.app")
                .allowedMethods("*")         // Aceptar cualquier método HTTP
                .allowedHeaders("*")         // Aceptar cualquier encabezado
                .allowCredentials(true);     // Permitir credenciales
			}
        };
    }
}