package br.com.victor.cleanarch.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.victor.cleanarch.domain.Usuario;
import br.com.victor.cleanarch.domain.exception.UsuarioNotFoudException;
import br.com.victor.cleanarch.domain.ports.UsuarioRepositoryService;

class GetAllUsuariosUseCaseImplTest {

	UsuarioRepositoryService usuarioRepositoryService = mock(UsuarioRepositoryService.class);

	private GetAllUsuariosUseCase getAllUsuariosUseCase = new GetAllUsuariosUseCaseImpl(usuarioRepositoryService);
	
	@Test
	@DisplayName("deve retornar todos os usuários cadastrados na base")
	void getAllUsuariosTest() {
		LocalDate dtCadastro = LocalDate.now();
		Usuario usuarioCadastrado = new Usuario(1L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		List<Usuario> usuariosCadastrados = List.of(usuarioCadastrado);
		
		when(usuarioRepositoryService.getAll()).thenReturn(usuariosCadastrados);
		
		List<Usuario> usuariosRetornados = getAllUsuariosUseCase.execute();
		
		 assertEquals(usuariosCadastrados, usuariosRetornados);
	}
	
	@Test
	@DisplayName("deve retornar um UsuarioNotFoudException ao tentar buscar todos os usuários da base")
	void getAllUsuariosInexistentesTest() {
		
		when(usuarioRepositoryService.getAll()).thenReturn(new ArrayList<>());

		UsuarioNotFoudException exception = assertThrows(UsuarioNotFoudException.class,
				() -> getAllUsuariosUseCase.execute());
		
		assertEquals("nenhum usuário encontrado", exception.getMessage());
	}
	

}
