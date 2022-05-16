package br.com.victor.cleanarch.Integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
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
import br.com.victor.cleanarch.infra.entrypoint.resource.UsuarioUpdateRequest;

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
	
	Usuario usuario;
	
	@BeforeEach
    public void iniciar() {
        this.usuario = new Usuario(1L, "victor", "vhora", "vmshora@gmail.com", true, LocalDate.now());
    }
    
	@Test
	@DisplayName("deve criar um usuário")
	void createUsuarioTest() {
		UsuarioCreateRequest usuarioRequest = new UsuarioCreateRequest("victor", "victor", "vmshora@gmail.com");

		ResponseEntity<UsuarioResponse> usuarioResponse = this.testRestTemplate.exchange(USUARIO_API, HttpMethod.POST,
				new HttpEntity<>(usuarioRequest), UsuarioResponse.class);

		Long usuarioId = usuarioResponse.getBody().getId();

		Usuario usuarioSalvo = usuarioRepositoryService.getById(usuarioId);

		assertThat(usuarioResponse).isNotNull();
		assertEquals(usuarioResponse.getStatusCode(), HttpStatus.CREATED);
		assertEquals(usuarioRequest.getNome(), usuarioSalvo.getNome());
		assertEquals(usuarioRequest.getLogin(), usuarioSalvo.getLogin());
		assertEquals(usuarioRequest.getEmail(), usuarioSalvo.getEmail());
		
	}
	
	@Test
	@DisplayName("deve retornar um bad request ao tentar cadastrar um usuário com login existente")
	void createUsuarioComLoginExistenteTest() {
		usuarioRepositoryService.save(usuario);
		UsuarioCreateRequest usuarioRequestComLoginExistente = new UsuarioCreateRequest("fulano", "vhora", "fulano@gmail.com");

		ResponseEntity<UsuarioResponse> usuarioResponseError = this.testRestTemplate.exchange(USUARIO_API, HttpMethod.POST,
				new HttpEntity<>(usuarioRequestComLoginExistente), UsuarioResponse.class);

		assertThat(usuarioResponseError.getBody().getId()).isNull();
		assertEquals(usuarioResponseError.getStatusCode(), HttpStatus.BAD_REQUEST);

	}
	
	@Test
	@DisplayName("deve atualizar um usuário")
	void updateUsuarioTest() {
		Usuario usuarioCadastrado = usuarioRepositoryService.save(usuario);
		UsuarioUpdateRequest usuarioUpdateRequest = new UsuarioUpdateRequest("victor medrado", "victor.hora", "victor.hora@gmail.com", false);
		
		ResponseEntity<UsuarioResponse> usuarioResponse = this.testRestTemplate.exchange(USUARIO_API + "/" + usuarioCadastrado.getId(), HttpMethod.PUT,
				new HttpEntity<>(usuarioUpdateRequest), UsuarioResponse.class);

		Usuario usuarioAtualizado = usuarioRepositoryService.getById(usuarioResponse.getBody().getId());

		assertThat(usuarioResponse).isNotNull();
		assertEquals(usuarioResponse.getStatusCode(), HttpStatus.OK);
		assertEquals(usuarioUpdateRequest.getNome(), usuarioAtualizado.getNome());
		assertEquals(usuarioUpdateRequest.getLogin(), usuarioAtualizado.getLogin());
		assertEquals(usuarioUpdateRequest.getEmail(), usuarioAtualizado.getEmail());
		assertEquals(usuarioUpdateRequest.isAtivo(), usuarioAtualizado.isAtivo());
		
	}

	@Test
	@DisplayName("deve retornar um bad request ao tentar atualizar um usuário com login existente")
	void updateUsuarioComLoginExistenteTest() {
		usuarioRepositoryService.save(usuario);
		Usuario usuarioCadastrado = usuarioRepositoryService.save(new Usuario(2L, "victor", "victor.hora", "vmshora@gmail.com", true, LocalDate.now()));
		UsuarioUpdateRequest usuarioUpdateRequest = new UsuarioUpdateRequest("victor medrado", "vhora", "victor.hora@gmail.com", false);

		ResponseEntity<UsuarioResponse> usuarioResponseError = this.testRestTemplate.exchange(USUARIO_API + "/" + usuarioCadastrado.getId(), HttpMethod.PUT,
				new HttpEntity<>(usuarioUpdateRequest), UsuarioResponse.class);

		assertThat(usuarioResponseError.getBody().getId()).isNull();
		assertEquals(usuarioResponseError.getStatusCode(), HttpStatus.BAD_REQUEST);

	}
	
	@Test
	@DisplayName("deve retornar um UsuarioNotFoudException ao tentar atualizar um usuário inexistente na base")
	void updateUsuarioInexistenteTest() {
		UsuarioUpdateRequest usuarioUpdateRequest = new UsuarioUpdateRequest("victor medrado", "vhora", "victor.hora@gmail.com", false);

		ResponseEntity<UsuarioResponse> usuarioResponseError = this.testRestTemplate.exchange(USUARIO_API + "/" + 1L, HttpMethod.PUT,
				new HttpEntity<>(usuarioUpdateRequest), UsuarioResponse.class);

		assertThat(usuarioResponseError.getBody().getId()).isNull();
		assertEquals(usuarioResponseError.getStatusCode(), HttpStatus.NOT_FOUND);

	}
}
