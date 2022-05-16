package br.com.victor.cleanarch.infra.persistense.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
	
	@Test
	@DisplayName("deve retornar todos os usuários cadastrados na base")
	void getAllUsuarioTest() {
		LocalDate dtCadastro = LocalDate.now();
		UsuarioEntity usuarioEntityCadastrado = new UsuarioEntity(1L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		Usuario usuarioCadastrado = new Usuario(1L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		List<UsuarioEntity> usuariosEntityList = List.of(usuarioEntityCadastrado);
		List<Usuario> usuarios = List.of(usuarioCadastrado);
		
		when(usuarioRepositoryConverter.mapToEntity(usuarioEntityCadastrado)).thenReturn(usuarioCadastrado);
		when(usuarioRepository.findAll()).thenReturn(usuariosEntityList);
		
		List<Usuario> usuariosCadastrados = usuarioRepositoryService.getAll();
		
		 assertEquals(usuarios, usuariosCadastrados);
	}
	
	@Test
	@DisplayName("deve retornar o usuário pelo login")
	void getByLoginTest() {
		LocalDate dtCadastro = LocalDate.now();
		UsuarioEntity usuarioEntityCadastrado = new UsuarioEntity(1L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		Usuario usuarioCadastrado = new Usuario(11L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		
		when(usuarioRepositoryConverter.mapToEntity(usuarioEntityCadastrado)).thenReturn(usuarioCadastrado);
		when(usuarioRepository.findByLogin("vhora")).thenReturn(Optional.of(usuarioEntityCadastrado));
		
		Usuario usuarioEncontrado = usuarioRepositoryService.getByLogin("vhora");
		
		 assertEquals(usuarioCadastrado, usuarioEncontrado);
	}
	
	@Test
	@DisplayName("deve retornar o usuário pelo id")
	void getByIdTest() {
		LocalDate dtCadastro = LocalDate.now();
		UsuarioEntity usuarioEntityCadastrado = new UsuarioEntity(1L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		Usuario usuarioCadastrado = new Usuario(11L, "victor", "vhora", "vmshora@gmail.com", true, dtCadastro);
		
		when(usuarioRepositoryConverter.mapToEntity(usuarioEntityCadastrado)).thenReturn(usuarioCadastrado);
		when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntityCadastrado));
		
		Usuario usuarioEncontrado = usuarioRepositoryService.getById(1L);
		
		 assertEquals(usuarioCadastrado, usuarioEncontrado);
	}
}
