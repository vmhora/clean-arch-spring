package br.com.victor.cleanarch.infra.entrypoint;

import static org.hamcrest.CoreMatchers.containsString;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.victor.cleanarch.domain.Usuario;
import br.com.victor.cleanarch.domain.exception.LoginExistenteException;
import br.com.victor.cleanarch.domain.exception.UsuarioNotFoudException;
import br.com.victor.cleanarch.domain.usecase.CreateUsuarioUseCase;
import br.com.victor.cleanarch.domain.usecase.GetAllUsuariosUseCase;
import br.com.victor.cleanarch.domain.usecase.UpdateUsuarioUseCase;
import br.com.victor.cleanarch.infra.entrypoint.resource.UsuarioCreateRequest;
import br.com.victor.cleanarch.infra.entrypoint.resource.UsuarioUpdateRequest;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
class UsuarioControllerTest {

	static String USUARIO_API = "/api/v1/usuarios";
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	CreateUsuarioUseCase createUsuarioUseCase;
	
	@MockBean
	UpdateUsuarioUseCase updateUsuarioUseCase;
	
	@MockBean
	GetAllUsuariosUseCase getAllUsuariosUseCase;
	
	@Test
	@DisplayName("deve criar um usuário")
	public void createUsuarioTest() throws Exception {
		
		LocalDate dtCadastro = LocalDate.now();
		UsuarioCreateRequest usuarioRequest = new UsuarioCreateRequest("victor", "vhora", "vmshora@gmail.com");
		Usuario usuarioCriado = new Usuario(1L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);

		BDDMockito.given(createUsuarioUseCase.execute(Mockito.any(Usuario.class))).willReturn(usuarioCriado);
		
		String json = new ObjectMapper().writeValueAsString(usuarioRequest);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(USUARIO_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);

		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
			.andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
			.andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuarioCriado.getNome()))
			.andExpect(MockMvcResultMatchers.jsonPath("login").value(usuarioCriado.getLogin()))
			.andExpect(MockMvcResultMatchers.jsonPath("email").value(usuarioCriado.getEmail()))
			.andExpect(MockMvcResultMatchers.jsonPath("ativo").value(true))
			.andExpect(MockMvcResultMatchers.jsonPath("dtCadastro").isNotEmpty());
		
	}
	
	@Test
	@DisplayName("deve lançar um erro ao tentar cadastrar um usuário com login existente")
	public void createUsuarioComLoginExistenteTest() throws Exception {
		
		UsuarioCreateRequest usuarioRequest = new UsuarioCreateRequest("victor", "vhora", "vmshora@gmail.com");

		BDDMockito.given(createUsuarioUseCase.execute(Mockito.any(Usuario.class))).willThrow(new LoginExistenteException("login já está em uso"));
		
		String json = new ObjectMapper().writeValueAsString(usuarioRequest);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(USUARIO_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);

		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("details", containsString("login já está em uso")));
			
		
	}
	
	@Test
	@DisplayName("deve atualizar um usuário")
	public void updateUsuarioTest() throws Exception {
		
		LocalDate dtCadastro = LocalDate.now();
		Long usuarioId = 1L;
		UsuarioUpdateRequest usuarioUpdateRequest = new UsuarioUpdateRequest("victor", "vhora", "vmshora@gmail.com", false);
		Usuario usuarioAtualizado = new Usuario(1L, "victor", "vhora", "vmshora@gmail.com", false, dtCadastro);

		BDDMockito.given(updateUsuarioUseCase.execute(usuarioId,usuarioUpdateRequest.toDomain())).willReturn(usuarioAtualizado);
		
		String json = new ObjectMapper().writeValueAsString(usuarioUpdateRequest);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.put(USUARIO_API + "/{id}", usuarioId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);

		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("id").value(usuarioId))
			.andExpect(MockMvcResultMatchers.jsonPath("nome").value(usuarioUpdateRequest.getNome()))
			.andExpect(MockMvcResultMatchers.jsonPath("login").value(usuarioUpdateRequest.getLogin()))
			.andExpect(MockMvcResultMatchers.jsonPath("email").value(usuarioUpdateRequest.getEmail()))
			.andExpect(MockMvcResultMatchers.jsonPath("ativo").value(usuarioUpdateRequest.isAtivo()))
			.andExpect(MockMvcResultMatchers.jsonPath("dtCadastro").isNotEmpty());
		
	}
	
	@Test
	@DisplayName("deve lançar um erro ao tentar atualizar um usuário com login existente")
	public void updateUsuarioComLoginExistenteTest() throws Exception {
		
		Long usuarioId = 1L;
		UsuarioUpdateRequest usuarioUpdateRequest = new UsuarioUpdateRequest("victor", "vhora", "vmshora@gmail.com", false);

		BDDMockito.given(updateUsuarioUseCase.execute(usuarioId,usuarioUpdateRequest.toDomain())).willThrow(new LoginExistenteException("login já está em uso"));
		
		String json = new ObjectMapper().writeValueAsString(usuarioUpdateRequest);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.put(USUARIO_API + "/{id}", usuarioId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);

		mvc
		.perform(request)
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.jsonPath("details", containsString("login já está em uso")));
		
	}
	
	@Test
	@DisplayName("deve lançar um erro ao tentar atualizar um usuário inexistente na base")
	public void updateUsuarioComUsuarioInexistenteTest() throws Exception {
		
		Long usuarioId = 1L;
		UsuarioUpdateRequest usuarioUpdateRequest = new UsuarioUpdateRequest("victor", "vhora", "vmshora@gmail.com", false);

		BDDMockito.given(updateUsuarioUseCase.execute(usuarioId,usuarioUpdateRequest.toDomain())).willThrow(new UsuarioNotFoudException("usuario não encontrado"));
		
		String json = new ObjectMapper().writeValueAsString(usuarioUpdateRequest);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.put(USUARIO_API + "/{id}", usuarioId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json);

		mvc
		.perform(request)
		.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andExpect(MockMvcResultMatchers.jsonPath("details", containsString("usuario não encontrado")));
		
	}
	
	@Test
	@DisplayName("deve retornar todos os usuários cadastrados na base")
	public void getAllUsuariosTest() throws Exception {
		
		LocalDate dtCadastro = LocalDate.now();
		Usuario usuarioCadastrado = new Usuario(1L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		List<Usuario> usuariosCadastrados = List.of(usuarioCadastrado);

		BDDMockito.given(getAllUsuariosUseCase.execute()).willReturn(usuariosCadastrados);
		
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get(USUARIO_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				

		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0]id").value(usuarioCadastrado.getId()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0]nome").value(usuarioCadastrado.getNome()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0]login").value(usuarioCadastrado.getLogin()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0]email").value(usuarioCadastrado.getEmail()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0]ativo").value(usuarioCadastrado.isAtivo()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.[0]dtCadastro").isNotEmpty());
		
	}
	
	@Test
	@DisplayName("deve retornar um UsuarioNotFoudException ao tentar buscar todos os usuários da base")
	public void getAllUsuariosInexistentesTest() throws Exception {
		
		BDDMockito.given(getAllUsuariosUseCase.execute()).willThrow(new UsuarioNotFoudException("nenhum usuário encontrado"));
		
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get(USUARIO_API)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
				

		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("details", containsString("nenhum usuário encontrado")));
		
	}
}
