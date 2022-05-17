package br.com.victor.cleanarch.domain.usecase;

import java.util.List;

import br.com.victor.cleanarch.domain.Usuario;
import br.com.victor.cleanarch.domain.exception.UsuarioNotFoudException;
import br.com.victor.cleanarch.domain.ports.UsuarioRepositoryService;

public class GetAllUsuariosUseCaseImpl implements GetAllUsuariosUseCase {

private final UsuarioRepositoryService usuarioRepositoryService;
	
	public GetAllUsuariosUseCaseImpl(UsuarioRepositoryService usuarioRepositoryService) {
		this.usuarioRepositoryService = usuarioRepositoryService;
	}
	
	@Override
	public List<Usuario> execute() {
		List<Usuario> usuarios = usuarioRepositoryService.getAll();
		if(usuarios.isEmpty())
			throw new UsuarioNotFoudException("nenhum usuário encontrado");
		
		return usuarios;
	}

}
