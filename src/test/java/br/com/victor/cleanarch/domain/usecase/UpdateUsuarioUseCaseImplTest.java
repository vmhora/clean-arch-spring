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
import org.mockito.Mockito;

import br.com.victor.cleanarch.domain.Usuario;
import br.com.victor.cleanarch.domain.exception.LoginExistenteException;
import br.com.victor.cleanarch.domain.exception.UsuarioNotFoudException;
import br.com.victor.cleanarch.domain.ports.UsuarioRepositoryService;
import br.com.victor.cleanarch.infra.entrypoint.resource.UsuarioUpdateRequest;

class UpdateUsuarioUseCaseImplTest {

UsuarioRepositoryService usuarioRepositoryService = mock(UsuarioRepositoryService.class);
	
	private UpdateUsuarioUseCase updateUsuarioUseCase = new UpdateUsuarioUseCaseImpl(usuarioRepositoryService);
	
	@Test
	@DisplayName("deve atualizar um usuário")
	void updateUsuarioTest() {
		Long usuarioId = 1L;
		LocalDate dtCadastro = LocalDate.now();
		UsuarioUpdateRequest usuarioUpdateRequest = new UsuarioUpdateRequest("victor", "vhora", "vmshora@gmail.com", false);
		
		Usuario usuarioExistente = new Usuario(1L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		Usuario usuarioModificado = new Usuario(1L, "victor", "vhora", "vmshora@gmail.com", false, dtCadastro);
		
		when(usuarioRepositoryService.getById(usuarioId)).thenReturn(usuarioExistente);
		when(usuarioRepositoryService.getByLogin("vhora")).thenReturn(null);
		when(usuarioRepositoryService.save(usuarioModificado)).thenReturn(usuarioModificado);
		
		Usuario usuarioAtualizado = updateUsuarioUseCase.execute(usuarioId, usuarioUpdateRequest.toDomain());
		
		 assertEquals(usuarioModificado, usuarioAtualizado);
	}
	
	@Test
	@DisplayName("deve atualizar um usuário")
	void updateUsuarioParaInativarTest() {
		Long usuarioId = 1L;
		LocalDate dtCadastro = LocalDate.now();
		UsuarioUpdateRequest usuarioUpdateRequest = new UsuarioUpdateRequest("victor", "vhora", "vmshora@gmail.com", false);
		
		Usuario usuarioExistente = new Usuario(1L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		Usuario usuarioModificado = new Usuario(1L, "victor", "vhora", "vmshora@gmail.com", false, dtCadastro);
		
		when(usuarioRepositoryService.getById(usuarioId)).thenReturn(usuarioExistente);
		when(usuarioRepositoryService.getByLogin("vhora")).thenReturn(usuarioExistente);
		when(usuarioRepositoryService.save(usuarioModificado)).thenReturn(usuarioModificado);
		
		Usuario usuarioAtualizado = updateUsuarioUseCase.execute(usuarioId, usuarioUpdateRequest.toDomain());
		
		 assertEquals(usuarioModificado, usuarioAtualizado);
	}
	
	@Test
	@DisplayName("deve retornar um LoginExistenteException ao tentar atualizar um usuário com um login já existente")
	void updateUsuarioComLoginExistenteTest() {
		Long usuarioId = 2L;
		LocalDate dtCadastro = LocalDate.now();
		UsuarioUpdateRequest usuarioUpdateRequest = new UsuarioUpdateRequest("victor medrado", "vhora", "victor.hora", true);
		
		Usuario usuarioExistente = new Usuario(2L, "victor Medrado", "victor.hora", "victor.hora@gmail.com", true, dtCadastro);
		Usuario usuariLoginExistente = new Usuario(1L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		
		when(usuarioRepositoryService.getById(usuarioId)).thenReturn(usuarioExistente);
		when(usuarioRepositoryService.getByLogin(usuarioUpdateRequest.getLogin())).thenReturn(usuariLoginExistente);
		
		LoginExistenteException exception = assertThrows(LoginExistenteException.class, () -> updateUsuarioUseCase.execute(usuarioId, usuarioUpdateRequest.toDomain()));
		
		 assertEquals("login já está em uso", exception.getMessage());
		 verify(usuarioRepositoryService, never()).save(Mockito.any(Usuario.class));
		
	}
	
	@Test
	@DisplayName("deve retornar um UsuarioNotFoudException ao tentar atualizar um usuário inexistente na base")
	void updateUsuarioInexistenteTest() {
		Long usuarioId = 2L;
		
		UsuarioUpdateRequest usuarioUpdateRequest = new UsuarioUpdateRequest("victor medrado", "vhora", "victor.hora", true);
		
		
		when(usuarioRepositoryService.getById(usuarioId)).thenReturn(null);
		
		UsuarioNotFoudException exception = assertThrows(UsuarioNotFoudException.class, () -> updateUsuarioUseCase.execute(usuarioId, usuarioUpdateRequest.toDomain()));
		
		 assertEquals("usuario não encontrado", exception.getMessage());
		 verify(usuarioRepositoryService, never()).getByLogin(usuarioUpdateRequest.getLogin());
		 verify(usuarioRepositoryService, never()).save(Mockito.any(Usuario.class));
		
	}


}
