package projeto.api.rest.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import projeto.api.rest.ApplicationContextLoad;
import projeto.api.rest.model.Usuario;
import projeto.api.rest.repository.UsuarioRepository;

@Service
@Component
public class JWTTokenAutenticacaoService {
	
	//tempo de validade do token 2 dias
	private static final long EXPIRATION_TIME = 172800000;
	
	// Uma senha unica para compor a autenticação e ajudar na segurança
	private static final String SECRET = "SenhaExtremamenteSecreta";

	//Prefixo padrão de token
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	// Gerando token de autenticação e adicionando ao cabeçalho e resposta http
	
	public void addAuthentication(HttpServletResponse response, String username) throws IOException {
		
		// montagem do token
		String JWT = Jwts.builder() // chama o gerador de token
				.setSubject(username)//adiciona o usuario 
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME )) //tempo de expiração
				.signWith(SignatureAlgorithm.HS512, SECRET).compact(); //compactação e algoritmos de geração de senha
		
		//Junta o token com o prefixo
		String token = TOKEN_PREFIX + " " + JWT; //Bearer 121721819217uhww81
		
		//adiciona no cabeçalho http
		response.addHeader(HEADER_STRING, token); // Authorization: Bearer 121721819217uhww81
		ApplicationContextLoad
		.getApplicationContext().getBean(UsuarioRepository.class).atualizaTokenUser(JWT, username);

		//liberando resposta para portas diferentes que usam api, ou caso cliente web
			LiberacaoCors(response);
			
		// liberando resposta para porta diferente, para o angular
			//response.addHeader("Access-Control-Allow-Origin", "*");
			
			
			
		// Escreve token com resposta no corpo http 
		response.getWriter().write("{\"Authorization\": \""+token+"\"}");
		
	}
		
		//retorna o usuario validado com token ou caso não seja valido retorna null
	public	Authentication  getAuthentication(HttpServletRequest request, HttpServletResponse response) {
			
			//pega o token enviado no cabeçalho http
			String token = request.getHeader(HEADER_STRING);
			try {
			if(token != null) {
				
				String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
				 
				
				//faz a validação do token do usuario na requisição
				String user = Jwts.parser().setSigningKey(SECRET). //Bearer 121721819217uhww81
						parseClaimsJws(tokenLimpo) // 121721819217uhww81
						.getBody().getSubject(); // usuario joao silva
				
				
				
				if(user != null) {
					
					Usuario usuario =  ApplicationContextLoad
										.getApplicationContext().getBean(UsuarioRepository.class).findUserByLogin(user);
					
					if(usuario != null) {
						
						if(tokenLimpo.equalsIgnoreCase(usuario.getToken())) {
						
						return new UsernamePasswordAuthenticationToken
								(usuario.getLogin(), 
								usuario.getSenha(), 
								usuario.getAuthorities());
						}
					}
				
					
				}
				
			} // fim da condição token	
			}catch (io.jsonwebtoken.ExpiredJwtException e) {
				try {
					response.getOutputStream().println("Seu TOKEN está expirado, faça o login ou informe um novo Token para autenticação ");
				} catch (IOException e1) {}
			}
			
			
			LiberacaoCors(response); 
			
			return null; // não autorizado
			
		}

		//libera o cliente a acessar e  ter a resposta da api
		private void LiberacaoCors(HttpServletResponse response) {
			
			if(response.getHeader("Access-Control-Allow-Origin") == null) {
				response.addHeader("Access-Control-Allow-Origin", "*");
			
			}
			
			if(response.getHeader("Access-Control-Allow-Headers") == null) {
				response.addHeader("Access-Control-Allow-Headers", "*");
				
			}
			
			
			if(response.getHeader("Access-Control-Request-Headers") == null){
				response.addHeader("Access-Control-Request-Headers", "*");
			}
			
			if(response.getHeader("Access-Control-Allow-Methods") == null) {
				response.addHeader("Access-Control-Allow-Methods", "*");
			}
			
		}
		
}
