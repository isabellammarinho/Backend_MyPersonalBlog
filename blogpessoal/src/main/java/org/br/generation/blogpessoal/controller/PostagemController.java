package org.br.generation.blogpessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.br.generation.blogpessoal.model.Postagem;
import org.br.generation.blogpessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  A @RestController responsavel por responder às requisições http enviadas para um endpoint 
 *  (endereço) definido na anotação @RequestMapping.
 * 
 *  A @RequestMapping("/postagens"): indica o endpoint (endereço) 
 * que a controladora responderá as requisições.
 * 
 *  A @CrossOrigin("*"): indica que a classe controladora permitirá o recebimento de requisições 
 *  realizadas de fora do domínio (localhost).
 *  
 *  A @CrossOrigin(origins = "*", allowedHeaders = "*") 
 *  Ela libera as origens e os cabeçalhos das requisições.
 */

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {
/**
 * A @Autowired implementação utilizada pelo  Spring  Framework  de  aplicar  a  Inversão  de  Controle  quando  for necessário.
 * 
 * 
 */
	@Autowired
	private PostagemRepository postagemRepository;
	/**  
	 * @GetMapping: indica que o método abaixo responderá todaas as 
	 * requisições do tipo GET que forem enviadas no endpoint /postagens.
	 * 
	 * 
	 * O Método getAll() será do tipo ResponseEntity porque ele responderá a requisição (Request),
	 * com uma HTTP Response (Resposta http), neste caso Response Status 200 => OK
	 * 
	 * <List<Postagem>>: Como o Método listará todos os registros da nossa tabela, o método retornará 
	 * dentro da resposta um objeto do tipo List (Collection) preenchido com objetos do tipo Postagem,
	 * que são os dados da tabela.
	 * 
	 * return ResponseEntity.ok(postagemRepository.findAll());: Executa o método findAll(), que é um
	 * método padrão da interface JpaRepository e retorna o status OK = 200
	 * 
	 * Como o Método sempre irá criar a List independente ter ou não valores na tabela, ele sempre
	 * retornará 200.
	 */
	

	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() {
		return ResponseEntity.ok(postagemRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable long id) {
		return postagemRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@PostMapping
	public ResponseEntity<Postagem> postPostagem(@Valid @RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}

	@PutMapping
	public ResponseEntity<Postagem> putPostagem(@Valid @RequestBody Postagem postagem) {
		return postagemRepository.findById(postagem.getId())
			.map(resp -> ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem)))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePostagem(@PathVariable long id) {
		return postagemRepository.findById(id)
			.map(resposta -> {
				postagemRepository.deleteById(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			})
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
}