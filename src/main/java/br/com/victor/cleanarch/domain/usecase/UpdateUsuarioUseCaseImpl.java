package br.com.victor.cleanarch.domain.usecase;

import java.util.Objects;

import br.com.victor.cleanarch.domain.Usuario;
import br.com.victor.cleanarch.domain.exception.LoginExistenteException;
import br.com.victor.cleanarch.domain.exception.UsuarioNotFoudException;
import br.com.victor.cleanarch.domain.ports.UsuarioRepositoryService;

public class UpdateUsuarioUseCaseImpl implements UpdateUsuarioUseCase {

	private final UsuarioRepositoryService usuarioRepositoryService;
	
	public UpdateUsuarioUseCaseImpl(UsuarioRepositoryService usuarioRepositoryService) {
		this.usuarioRepositoryService = usuarioRepositoryService;
	}

	@Override
	public Usuario execute(Long usuarioId, Usuario usuario) {
		Usuario usuarioCadastrado = usuarioRepositoryService.getById(usuarioId);
		if(Objects.isNull(usuarioCadastrado)) {
			throw new UsuarioNotFoudException("usuario não encontrado");
		}
		
		Usuario usuarioExistente = usuarioRepositoryService.getByLogin(usuario.getLogin());
		if(Objects.nonNull(usuarioExistente) && !usuarioExistente.getId().equals(usuarioId)) {
			throw new LoginExistenteException("login já está em uso");
		}

		usuarioCadastrado.setAtivo(usuario.isAtivo());
		usuarioCadastrado.setEmail(usuario.getEmail());
		usuarioCadastrado.setNome(usuario.getNome());
		usuarioCadastrado.setLogin(usuario.getLogin());
		
		return usuarioRepositoryService.save(usuarioCadastrado);
	}

}
