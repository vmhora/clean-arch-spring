package br.com.victor.cleanarch.Integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.victor.cleanarch.domain.Usuario;
import br.com.victor.cleanarch.domain.ports.UsuarioRepositoryService;
import br.com.victor.cleanarch.infra.entrypoint.resource.UsuarioCreateRequest;
import br.com.victor.cleanarch.infra.entrypoint.resource.UsuarioResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class UsuarioControllerIT {

	static String USUARIO_API = "/api/v1/usuarios";
	
	@Autowired
    private TestRestTemplate testRestTemplate;
   
	@LocalServerPort
    private int port;
   
	@Autowired
    private UsuarioRepositoryService usuarioRepositoryService;
    
	@Test
	@DisplayName("deve criar um usuário")
	void createUsuarioTest() {
		UsuarioCreateRequest usuarioRequest = new UsuarioCreateRequest("victor", "vhora", "vmshora@gmail.com");

		ResponseEntity<UsuarioResponse> usuarioResponse = this.testRestTemplate.exchange(USUARIO_API, HttpMethod.POST,
				new HttpEntity<>(usuarioRequest), UsuarioResponse.class);

		Long usuarioId = usuarioResponse.getBody().getId();

		Usuario usuario = usuarioRepositoryService.getById(usuarioId);

		assertThat(usuarioResponse).isNotNull();
		assertEquals(usuarioResponse.getStatusCode(), HttpStatus.CREATED);
		assertEquals(usuarioRequest.getNome(), usuario.getNome());
		assertEquals(usuarioRequest.getLogin(), usuario.getLogin());
		assertEquals(usuarioRequest.getEmail(), usuario.getEmail());
		
	}
	
	@Test
	@DisplayName("deve retornar um bad request ao tentar cadastrar um usuário com login existente")
	void createUsuarioComLoginExistenteTest() {
		UsuarioCreateRequest usuarioRequest = new UsuarioCreateRequest("victor", "victor.hora", "vmshora@gmail.com");
		UsuarioCreateRequest usuarioRequestComLoginExistente = new UsuarioCreateRequest("fulano", "victor.hora", "fulano@gmail.com");

		ResponseEntity<UsuarioResponse> usuarioResponse = this.testRestTemplate.exchange(USUARIO_API, HttpMethod.POST,
				new HttpEntity<>(usuarioRequest), UsuarioResponse.class);
		
		ResponseEntity<UsuarioResponse> usuarioResponseError = this.testRestTemplate.exchange(USUARIO_API, HttpMethod.POST,
				new HttpEntity<>(usuarioRequestComLoginExistente), UsuarioResponse.class);

		assertThat(usuarioResponse).isNotNull();
		assertEquals(usuarioResponse.getStatusCode(), HttpStatus.CREATED);
		assertEquals(usuarioRequest.getNome(), usuarioResponse.getBody().getNome());
		assertEquals(usuarioRequest.getLogin(), usuarioResponse.getBody().getLogin());
		assertEquals(usuarioRequest.getEmail(), usuarioResponse.getBody().getEmail());
		assertEquals(usuarioResponseError.getStatusCode(), HttpStatus.BAD_REQUEST);

	}

}
