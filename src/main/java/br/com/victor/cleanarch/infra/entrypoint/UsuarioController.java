package br.com.victor.cleanarch.infra.entrypoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.victor.cleanarch.domain.Usuario;
import br.com.victor.cleanarch.domain.exception.LoginExistenteException;
import br.com.victor.cleanarch.domain.exception.UsuarioNotFoudException;
import br.com.victor.cleanarch.domain.usecase.CreateUsuarioUseCase;
import br.com.victor.cleanarch.domain.usecase.UpdateUsuarioUseCase;
import br.com.victor.cleanarch.infra.entrypoint.exception.BadRequestException;
import br.com.victor.cleanarch.infra.entrypoint.exception.NotFoundException;
import br.com.victor.cleanarch.infra.entrypoint.resource.UsuarioCreateRequest;
import br.com.victor.cleanarch.infra.entrypoint.resource.UsuarioResponse;
import br.com.victor.cleanarch.infra.entrypoint.resource.UsuarioUpdateRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

	private final CreateUsuarioUseCase createUsuarioUseCase;
	private final UpdateUsuarioUseCase updateUsuarioUseCase;
	
	@PostMapping
	public ResponseEntity<UsuarioResponse> create(@RequestBody UsuarioCreateRequest usuarioRequest) {
		Usuario usuario = null;
		try {
			usuario = createUsuarioUseCase.execute(usuarioRequest.toDomain());
		} catch (LoginExistenteException e) {
			throw new BadRequestException(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponse(usuario));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioResponse> update(@PathVariable(value = "id") Long usuarioId, @RequestBody UsuarioUpdateRequest usuarioUpdateRequest) {
		Usuario usuario = null;
		try {
			usuario = updateUsuarioUseCase.execute(usuarioId, usuarioUpdateRequest.toDomain());
		} catch (LoginExistenteException e) {
			throw new BadRequestException(e.getMessage());
		} catch (UsuarioNotFoudException e) {
			throw new NotFoundException(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(new UsuarioResponse(usuario));
	}
}
