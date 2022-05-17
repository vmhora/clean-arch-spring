package br.com.victor.cleanarch.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.victor.cleanarch.domain.ports.UsuarioRepositoryService;
import br.com.victor.cleanarch.domain.usecase.CreateUsuarioUseCase;
import br.com.victor.cleanarch.domain.usecase.CreateUsuarioUseCaseImpl;
import br.com.victor.cleanarch.domain.usecase.GetAllUsuariosUseCase;
import br.com.victor.cleanarch.domain.usecase.GetAllUsuariosUseCaseImpl;
import br.com.victor.cleanarch.domain.usecase.UpdateUsuarioUseCase;
import br.com.victor.cleanarch.domain.usecase.UpdateUsuarioUseCaseImpl;

@Configuration
public class Config {

	@Bean
	CreateUsuarioUseCase createUsuarioService(UsuarioRepositoryService usuarioRepositoryService) {
		return new CreateUsuarioUseCaseImpl(usuarioRepositoryService);
	}
	
	@Bean
	UpdateUsuarioUseCase updateUsuarioService(UsuarioRepositoryService usuarioRepositoryService) {
		return new UpdateUsuarioUseCaseImpl(usuarioRepositoryService);
	}
	
	@Bean
	GetAllUsuariosUseCase getAllUsuariosService(UsuarioRepositoryService usuarioRepositoryService) {
		return new GetAllUsuariosUseCaseImpl(usuarioRepositoryService);
	}
}
