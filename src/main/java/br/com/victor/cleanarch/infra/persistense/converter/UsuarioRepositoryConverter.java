package br.com.victor.cleanarch.infra.persistense.converter;

import org.springframework.stereotype.Component;

import br.com.victor.cleanarch.domain.Usuario;
import br.com.victor.cleanarch.infra.persistense.entity.UsuarioEntity;

@Component
public class UsuarioRepositoryConverter {

	public UsuarioEntity mapToTable(final Usuario persistenceObject) {
		return new UsuarioEntity(persistenceObject.getId(), persistenceObject.getNome(), persistenceObject.getLogin(), persistenceObject.getEmail(), persistenceObject.isAtivo(), persistenceObject.getDtCadastro());
	}

	public Usuario mapToEntity(final UsuarioEntity entityObject) {
		return new Usuario(entityObject.getId(), entityObject.getNome(), entityObject.getLogin(), entityObject.getEmail(), entityObject.isAtivo(), entityObject.getDtCadastro());
	}
}
