package br.com.victor.cleanarch.domain.usecase;

import java.util.List;

import br.com.victor.cleanarch.domain.Usuario;

public interface GetAllUsuariosUseCase {

	public List<Usuario> execute();
	
}
