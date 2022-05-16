package br.com.victor.cleanarch.infra.persistense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.victor.cleanarch.infra.persistense.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

	Optional<UsuarioEntity> findByLogin(String login);

}
