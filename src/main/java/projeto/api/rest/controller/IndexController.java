package projeto.api.rest.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import projeto.api.rest.model.Usuario;
import projeto.api.rest.model.UsuarioDTO;
import projeto.api.rest.repository.UsuarioRepository;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/usuario")
public class IndexController {
	 
	@Autowired // se fosse cdi seria @Inject
	private UsuarioRepository usuarioRepository;
	
	@GetMapping(value = "/{id}/codigovenda/(venda)", produces = "application/json")
	public ResponseEntity<Usuario>  relatorio(@PathVariable(value="id") Long id, @PathVariable(value = "venda") Long venda) {
		
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		//retorno seria um relatorio.
		
		
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	}
	
	//serviço restful
	@GetMapping(value = "/{id}", produces = "application/json", headers = "X-API-Version=v1")
	public ResponseEntity<Usuario>  initV1(@PathVariable(value="id") Long id) {
		
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		System.out.println("Executando verrsão 1");
		
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	}
	
	//serviço restful
		@GetMapping(value = "/{id}", produces = "application/json", headers = "X-API-Version=v2")
		@Cacheable("cacheuser")
		@CacheEvict(value = "cacheuser")
		@CachePut("cacheuser")
		public ResponseEntity<UsuarioDTO>  init(@PathVariable(value="id") Long id) {
			
			Optional<Usuario> usuario = usuarioRepository.findById(id);
			
			//System.out.println("Executando verrsão 2");
			
			return new ResponseEntity<UsuarioDTO>(new UsuarioDTO(usuario.get()), HttpStatus.OK);
		}
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public String delete (@PathVariable("id") Long id) {
		usuarioRepository.deleteById(id);
		
		return "ok";
	}
	
	@DeleteMapping(value = "/{id}/venda", produces = "application/json")
	public String deletevenda(@PathVariable("id") Long id) {
		usuarioRepository.deleteById(id); //deletar todas as vendas do usuario
		
		return "ok";
	}
	
	//exeplo para um usuario que seja um processo lento, correção usando cache para agilizar
	@GetMapping(value = "/", produces = "application/json")
	@CachePut("cacheusuarios")
	public ResponseEntity<List<Usuario>> usuarios () throws InterruptedException{
		
		List<Usuario> list = (List<Usuario>)usuarioRepository.findAll();
		
		
		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
	}
	
	
	//END-POINT CONSULTA DE USUARIO POR NOME.
	@GetMapping(value = "/usuarioPorNome/{nome}", produces = "application/json")
	@CachePut("cacheusuarios")
	public ResponseEntity<List<Usuario>> usuarioPorNome (@PathVariable("nome") String nome) throws InterruptedException{
		
		List<Usuario> list = (List<Usuario>)usuarioRepository.findUserByNome(nome);
		
		
		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
	}
	
	
	
	@PostMapping(value="/", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) throws Exception{
		
		// para amarrar o telefone ao pai. ao usuario
		for (int pos = 0; pos < usuario.getTelefones().size(); pos ++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}
		
		// Consumindo API publica externa
		URL url = new URL("https://viacep.com.br/ws/"+usuario.getCep()+"/json/");	
		URLConnection connection = url.openConnection();
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		String cep = "";
		StringBuilder jsonCep = new StringBuilder();
		
		while((cep = br.readLine()) != null) {
			jsonCep.append(cep);
		}
		
		System.out.println(jsonCep.toString());
		
		Usuario userAux = new Gson().fromJson(jsonCep.toString(), Usuario.class);
		usuario.setCep(userAux.getCep());
		usuario.setLogradouro(userAux.getLogradouro());
		usuario.setComplemento(userAux.getComplemento());
		usuario.setBairro(userAux.getBairro());
		usuario.setLocalidade(userAux.getLocalidade());
		usuario.setUf(userAux.getUf());
		
		
		// Consumindo API publica externa
		
		
		// gera a senha criptografada quando cadastra.
		String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhacriptografada);
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK); 
		
	}
	
	@PutMapping(value="/", produces = "application/json")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario){
		
		//outras rotinas antes de atualizar
		
		// para amarrar o telefone ao pai. ao usuario
		for (int pos = 0; pos < usuario.getTelefones().size(); pos ++) {
					usuario.getTelefones().get(pos).setUsuario(usuario);
		}
		
		//rotina para atualizar a senha cadastrada. 
		Usuario userTemporario = usuarioRepository.findUserByLogin(usuario.getLogin());
		//rotina para atualizar a senha cadastrada. 
		if(!userTemporario.getSenha().equals(usuario.getSenha())) { // sennhas diferentes
			String senhacriptografada = new BCryptPasswordEncoder().encode(usuario.getSenha());
			usuario.setSenha(senhacriptografada);
		}
		
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK); 
		
	}
	
	@PutMapping(value = "/{iduser}/idvenda/{idvenda}", produces = "application/json")
	public ResponseEntity updateVenda(@PathVariable Long iduser, @PathVariable Long idvenda) {
		
		
		return new ResponseEntity("Venda atualizada", HttpStatus.OK);
	}
	
	@PostMapping(value="/vendausuario", produces = "application/json")
	public ResponseEntity<Usuario> cadastrarvenda(@RequestBody Usuario usuario){
	
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK); 
		
	}

}
