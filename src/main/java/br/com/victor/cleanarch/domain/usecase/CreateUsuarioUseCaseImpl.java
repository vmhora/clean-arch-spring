package br.com.victor.cleanarch.domain.usecase;

import java.time.LocalDate;
import java.util.Objects;

import br.com.victor.cleanarch.domain.Usuario;
import br.com.victor.cleanarch.domain.exception.LoginExistenteException;
import br.com.victor.cleanarch.domain.ports.UsuarioRepositoryService;

public class CreateUsuarioUseCaseImpl implements CreateUsuarioUseCase {

	private final UsuarioRepositoryService usuarioRepositoryService;
	
	public CreateUsuarioUseCaseImpl(UsuarioRepositoryService usuarioRepositoryService) {
		this.usuarioRepositoryService = usuarioRepositoryService;
	}

	@Override
	public Usuario execute(Usuario usuario) {
		
		if(Objects.nonNull(usuarioRepositoryService.getByLogin(usuario.getLogin()))) {
			throw new LoginExistenteException("login já está em uso");
		}
		usuario.setAtivo(true);
		usuario.setDtCadastro(LocalDate.now());
		
		return usuarioRepositoryService.save(usuario);
	}

}
