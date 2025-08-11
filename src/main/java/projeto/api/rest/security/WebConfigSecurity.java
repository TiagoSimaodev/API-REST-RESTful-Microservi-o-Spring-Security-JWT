package projeto.api.rest.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import projeto.api.rest.service.ImplementacaoUserDetailsService;

//Essa classe serve para mapear, url, endereços, autoriza ou bloqueia acessos a urls

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
	
	// Configura as solicitações de acesso por Http
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// ativando a proteção contra usuarios que não estão validados por token
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		
		//Ativando a permissão para acessso a pagina ou partes  inicial do sistema. ex:
		.disable().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		
		
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		
		// Url de logout - Redireciona após o user deslogar do sistema
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		//Mapeia url de logout e invalida o usuário.
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		// filtra as requisições de login para autenticação
		.and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), 
				UsernamePasswordAuthenticationFilter.class)	
		
		// filtra demais requisições para verificar a presença do token JWT no header HTTP
		.addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	
	
	@Autowired
	private ImplementacaoUserDetailsService  implementacaoUserDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// Service que irá consultar o usuario no banco de dados
		auth.userDetailsService(implementacaoUserDetailsService)
		
		// padrão de codificação de senha
		.passwordEncoder(new BCryptPasswordEncoder());
		
		
	}
	
	
}
