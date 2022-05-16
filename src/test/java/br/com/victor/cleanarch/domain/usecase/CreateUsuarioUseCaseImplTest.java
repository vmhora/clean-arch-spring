package br.com.victor.cleanarch.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.victor.cleanarch.domain.Usuario;
import br.com.victor.cleanarch.domain.exception.LoginExistenteException;
import br.com.victor.cleanarch.domain.ports.UsuarioRepositoryService;

class CreateUsuarioUseCaseImplTest {

	UsuarioRepositoryService usuarioRepositoryService = mock(UsuarioRepositoryService.class);
	
	private CreateUsuarioUseCase createUsuarioUseCase = new CreateUsuarioUseCaseImpl(usuarioRepositoryService);
	
	@Test
	@DisplayName("deve criar um usuário")
	void createUsuarioTest() {
		LocalDate dtCadastro = LocalDate.now();
		Usuario usuario = new Usuario("victor", "vhora", "vmshora@gmail.com");
		Usuario usuarioEsperado = new Usuario(1L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		
		when(usuarioRepositoryService.getByLogin("vhora")).thenReturn(null);
		when(usuarioRepositoryService.save(usuario)).thenReturn(usuarioEsperado);
		
		Usuario usuarioCriado = createUsuarioUseCase.execute(usuario);
		
		 assertEquals(usuarioEsperado, usuarioCriado);
	}
	
	@Test
	@DisplayName("deve retornar um LoginExistenteException ao tentar cadastrar um usuário com um login já existente")
	public void createUsuarioComLoginExistenteTest() {
		Usuario usuario = new Usuario("victor", "vhora", "vmshora@gmail.com");
		Usuario usuarioExistente = new Usuario(1L, "victor", "vhora", "vmshora@gmail.com", true, LocalDate.now());
		when(usuarioRepositoryService.getByLogin("vhora")).thenReturn(usuarioExistente);
		
		LoginExistenteException exception = assertThrows(LoginExistenteException.class, () -> createUsuarioUseCase.execute(usuario));
		
		 assertEquals("login já está em uso", exception.getMessage());
		 verify(usuarioRepositoryService, never()).save(usuario);
	}

}
