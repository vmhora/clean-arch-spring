package br.com.victor.cleanarch.domain.ports;

import java.util.List;

import br.com.victor.cleanarch.domain.Usuario;

public interface UsuarioRepositoryService {

	public Usuario save(Usuario usuario);
	
	public List<Usuario> getAll();
	
	public Usuario getByLogin(String login);

	public Usuario getById(Long id);
}
