package br.com.victor.cleanarch.infra.persistense.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.victor.cleanarch.domain.Usuario;
import br.com.victor.cleanarch.domain.ports.UsuarioRepositoryService;
import br.com.victor.cleanarch.infra.persistense.converter.UsuarioRepositoryConverter;
import br.com.victor.cleanarch.infra.persistense.entity.UsuarioEntity;
import br.com.victor.cleanarch.infra.persistense.repository.UsuarioRepository;

@Component
@Transactional
public class UsuarioRepositoryServiceImpl implements UsuarioRepositoryService{
	
	private final UsuarioRepository usuarioRepository;
	private final UsuarioRepositoryConverter usuarioRepositoryConverter;
	
	public UsuarioRepositoryServiceImpl(UsuarioRepository usuarioRepository, UsuarioRepositoryConverter usuarioRepositoryConverter) {
		this.usuarioRepository = usuarioRepository;
		this.usuarioRepositoryConverter = usuarioRepositoryConverter;
	}

	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
		UsuarioEntity usuarioEntity = usuarioRepositoryConverter.mapToTable(usuario);
		return usuarioRepositoryConverter.mapToEntity(usuarioRepository.save(usuarioEntity));
	}

	@Override
	public List<Usuario> getAll() {
		return usuarioRepository.findAll().stream().map(usuarioRepositoryConverter::mapToEntity).collect(Collectors.toList());
	}

	@Override
	public Usuario getByLogin(String login) {
		return usuarioRepository.findByLogin(login).map(usuarioRepositoryConverter::mapToEntity).orElse(null);
	}
	
	@Override
	public Usuario getById(Long id) {
		return usuarioRepository.findById(id).map(usuarioRepositoryConverter::mapToEntity).orElse(null);
	}


}
