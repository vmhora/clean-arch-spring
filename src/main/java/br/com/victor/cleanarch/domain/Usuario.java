package br.com.victor.cleanarch.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Usuario implements Serializable{

	private static final long serialVersionUID = -9213190278248676086L;

	private Long id;
	private String nome;
	private String login;
	private String email;
	private boolean ativo;
	private LocalDate dtCadastro;
	
	
	public Usuario() {
	}


	public Usuario(Long id, String nome, String login, String email, boolean ativo, LocalDate dtCadastro) {
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.email = email;
		this.ativo = ativo;
		this.dtCadastro = dtCadastro;
	}

	public Usuario(String nome, String login, String email) {
		this.nome = nome;
		this.login = login;
		this.email = email;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public boolean isAtivo() {
		return ativo;
	}


	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


	public LocalDate getDtCadastro() {
		return dtCadastro;
	}


	public void setDtCadastro(LocalDate dtCadastro) {
		this.dtCadastro = dtCadastro;
	}


	@Override
	public int hashCode() {
		return Objects.hash(ativo, dtCadastro, email, id, login, nome);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return ativo == other.ativo && Objects.equals(dtCadastro, other.dtCadastro)
				&& Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(login, other.login) && Objects.equals(nome, other.nome);
	}


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", login=" + login + ", email=" + email + ", ativo=" + ativo
				+ ", dtCadastro=" + dtCadastro + "]";
	}

}
