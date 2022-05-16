package br.com.victor.cleanarch.domain.usecase;

import br.com.victor.cleanarch.domain.Usuario;

public interface UpdateUsuarioUseCase {

	public Usuario execute(Long usuarioId, Usuario usuario);

}
