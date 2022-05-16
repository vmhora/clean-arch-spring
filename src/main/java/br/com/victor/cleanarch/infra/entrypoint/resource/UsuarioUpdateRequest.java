package br.com.victor.cleanarch.infra.entrypoint.resource;

import br.com.victor.cleanarch.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateRequest {

	private String nome;
	private String login;
	private String email;
	private boolean ativo;
	
	public Usuario toDomain() {
		Usuario usuario = new Usuario();
		usuario.setNome(this.nome);
		usuario.setLogin(this.login);
		usuario.setEmail(this.email);
		usuario.setAtivo(this.ativo);
		
		return usuario;
	}
}
