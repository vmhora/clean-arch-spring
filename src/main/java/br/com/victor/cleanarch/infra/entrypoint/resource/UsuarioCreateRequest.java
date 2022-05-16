package br.com.victor.cleanarch.infra.entrypoint.resource;

import br.com.victor.cleanarch.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateRequest {

	private String nome;
	private String login;
	private String email;
	
	public Usuario toDomain() {
		Usuario usuario = new Usuario();
		usuario.setNome(this.nome);
		usuario.setLogin(this.login);
		usuario.setEmail(this.email);
		
		return usuario;
	}
}
