package br.com.david.os.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.david.os.domain.Tecnico;
import br.com.david.os.dtos.TecnicoDto;
import br.com.david.os.service.TecnicoService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResources {

	@Autowired
	private TecnicoService service;

	@RequestMapping(value = "/{id}")
	public ResponseEntity<TecnicoDto> findById(@PathVariable Long id) {
		TecnicoDto objDTO = new TecnicoDto(service.findById(id));
		return ResponseEntity.ok().body(objDTO);

	}

	@GetMapping
	public ResponseEntity<List<TecnicoDto>> findAll() {
		List<TecnicoDto> listDTO = service.findAll().stream().map(obj -> new TecnicoDto(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);

	}

	@PostMapping
	public ResponseEntity<TecnicoDto> create(@Valid @RequestBody TecnicoDto objDTO) {
		Tecnico newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDto> update(@PathVariable Long id, @Valid @RequestBody TecnicoDto objDTO) {
		TecnicoDto newObj = new TecnicoDto(service.update(id, objDTO));
		return ResponseEntity.ok().body(newObj);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
		
		
	}

}
