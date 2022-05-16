package br.com.victor.cleanarch.infra.entrypoint.resource;

import java.time.LocalDate;

import br.com.victor.cleanarch.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioResponse {

	private Long id;
	private String nome;
	private String login;
	private String email;
	private boolean ativo;
	private LocalDate dtCadastro;
	
	public UsuarioResponse(final Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.login = usuario.getLogin();
		this.email = usuario.getEmail();
		this.ativo = usuario.isAtivo(); 
		this.dtCadastro = usuario.getDtCadastro();
	  }
	
}
