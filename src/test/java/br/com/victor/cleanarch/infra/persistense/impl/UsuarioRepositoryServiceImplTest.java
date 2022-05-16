package br.com.victor.cleanarch.infra.persistense.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.victor.cleanarch.domain.Usuario;
import br.com.victor.cleanarch.domain.ports.UsuarioRepositoryService;
import br.com.victor.cleanarch.infra.persistense.converter.UsuarioRepositoryConverter;
import br.com.victor.cleanarch.infra.persistense.entity.UsuarioEntity;
import br.com.victor.cleanarch.infra.persistense.repository.UsuarioRepository;

class UsuarioRepositoryServiceImplTest {

	UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
	UsuarioRepositoryConverter usuarioRepositoryConverter = mock(UsuarioRepositoryConverter.class);
	
	private UsuarioRepositoryService usuarioRepositoryService = new UsuarioRepositoryServiceImpl(usuarioRepository, usuarioRepositoryConverter);
	
	@Test
	@DisplayName("deve criar um usuário")
	void saveUsuarioTest() {
		LocalDate dtCadastro = LocalDate.now();
		Usuario usuario = new Usuario(null, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		UsuarioEntity usuarioEntity = new UsuarioEntity(null, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		
		UsuarioEntity usuarioEntityEsperado = new UsuarioEntity(1L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		
		Usuario usuarioEsperado = new Usuario(1L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		
		when(usuarioRepositoryConverter.mapToTable(usuario)).thenReturn(usuarioEntity);
		when(usuarioRepositoryConverter.mapToEntity(usuarioEntityEsperado)).thenReturn(usuarioEsperado);
		when(usuarioRepository.save(usuarioEntity)).thenReturn(usuarioEntityEsperado);
		
		Usuario usuarioCriado = usuarioRepositoryService.save(usuario);
		
		 assertEquals(usuarioEsperado, usuarioCriado);
	}
}
